package com.example.demo.config;

import java.util.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class AuthConfig implements WebMvcConfigurer {
    private final String[] origins;
    public AuthConfig(@Value("${frontend.origins:http://localhost:5173,http://127.0.0.1:5173}") String origins) {
        this.origins=Arrays.stream(origins.split(",")).map(String::trim).toArray(String[]::new);
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(origins).allowCredentials(true)
            .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
            .allowedHeaders("Content-Type");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            private boolean reject(HttpServletResponse response, int code, String message) throws Exception {
                response.setStatus(code); response.setContentType("application/json");
                response.getWriter().write("{\"detail\":\""+message+"\"}");
                return false;
            }
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                response.setHeader("Cache-Control","no-store");
                if (request.getMethod().equals("OPTIONS")) return true;
                String origin=request.getHeader("Origin");
                String ownOrigin=request.getScheme()+"://"+request.getHeader("Host");
                // Browser writes must come from our frontend or the API's own origin.
                if (!Set.of("GET","HEAD").contains(request.getMethod()) && origin!=null
                        && !Arrays.asList(origins).contains(origin) && !origin.equals(ownOrigin)) {
                    return reject(response,403,"Origin not allowed.");
                }
                String path=request.getRequestURI();
                if (Set.of("/user/register","/user/login","/user/logout").contains(path)) return true;
                if (!(path.equals("/user") || path.startsWith("/user/") || path.equals("/cart") || path.startsWith("/cart/") || path.startsWith("/order"))) return true;
                HttpSession session=request.getSession(false);
                Long userId=session==null ? null : (Long)session.getAttribute("userId");
                if (userId==null) return reject(response,401,"Please log in to continue.");
                if (path.equals("/user/me")) return true;
                // A signed-in user may only access their own user/cart resources.
                if (path.startsWith("/cart/") || path.startsWith("/user/")) {
                    String id=path.split("/")[2];
                    if (!id.equals(userId.toString())) return reject(response,403,"Access denied.");
                } else if (path.equals("/user")) return reject(response,403,"Access denied.");
                return true;
            }
        });
    }
}
