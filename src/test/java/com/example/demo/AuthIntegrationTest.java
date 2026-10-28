package com.example.demo;

import java.net.*;
import java.net.http.*;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT, properties={
    "spring.datasource.url=jdbc:h2:mem:auth_test;DB_CLOSE_DELAY=-1",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "springdoc.api-docs.enabled=false", "springdoc.swagger-ui.enabled=false"
})
class AuthIntegrationTest {
    @Value("${local.server.port}") int port;
    HttpClient client=HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();

    HttpResponse<String> call(String method, String path, String json, String cookie, String origin) throws Exception {
        var request=HttpRequest.newBuilder(URI.create("http://localhost:"+port+path))
            .timeout(Duration.ofSeconds(20)).header("Content-Type","application/json");
        if (cookie!=null) request.header("Cookie",cookie);
        if (origin!=null) request.header("Origin",origin);
        request.method(method,json==null ? HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofString(json));
        return client.send(request.build(),HttpResponse.BodyHandlers.ofString());
    }
    @Test void registrationAndSessionLifecycle() throws Exception {
        String body="""
            {"first_name":"Ada","last_name":"Lovelace","email":"ada@example.com",
             "postcode":"75001","house_number":"12B","password":"student-password"}
            """;
        assertEquals(400,call("POST","/user/register","{}",null,null).statusCode());
        assertEquals(400,call("POST","/user/register",body.replace("Ada"," "),null,null).statusCode());
        var registered=call("POST","/user/register",body,null,null);
        assertEquals(201,registered.statusCode());
        assertFalse(registered.body().contains("password"));
        assertEquals(409,call("POST","/user/register",body.replace("ada@","ADA@"),null,null).statusCode());
        String login="{\"email\":\"ada@example.com\",\"password\":\"student-password\"}";
        assertEquals(401,call("POST","/user/login",login.replace("student-password","wrong"),null,null).statusCode());
        assertEquals(401,call("GET","/user/me",null,null,null).statusCode());
        assertEquals(401,call("GET","/cart/1",null,null,null).statusCode());
        var loggedIn=call("POST","/user/login",login,null,"http://localhost:5173");
        assertEquals(200,loggedIn.statusCode());
        assertEquals("http://localhost:5173",loggedIn.headers().firstValue("Access-Control-Allow-Origin").orElseThrow());
        String setCookie=loggedIn.headers().firstValue("Set-Cookie").orElseThrow();
        assertTrue(setCookie.toLowerCase().contains("httponly"));
        String cookie=setCookie.split(";",2)[0];
        var profile=call("GET","/user/me",null,cookie,null);
        assertEquals(200,profile.statusCode());
        assertTrue(profile.body().contains("first_name"));
        assertFalse(profile.body().contains("password"));
        assertEquals("no-store",profile.headers().firstValue("Cache-Control").orElseThrow());
        assertEquals(403,call("GET","/cart/999999",null,cookie,null).statusCode());
        assertEquals(403,call("GET","/user",null,cookie,null).statusCode());
        assertEquals(403,call("POST","/user/logout",null,cookie,"https://evil.example").statusCode());
        var secondLogin=call("POST","/user/login",login,cookie,null);
        String secondCookie=secondLogin.headers().firstValue("Set-Cookie").orElseThrow().split(";",2)[0];
        assertEquals(401,call("GET","/user/me",null,cookie,null).statusCode());
        assertEquals(204,call("POST","/user/logout",null,secondCookie,null).statusCode());
        assertEquals(401,call("GET","/user/me",null,secondCookie,null).statusCode());
        assertEquals(204,call("POST","/user/logout",null,null,null).statusCode());
    }
    @Test void passwordHashesHaveRandomSalt() {
        var service=new com.example.demo.services.PasswordService();
        String first=service.hash("password"), second=service.hash("password");
        assertNotEquals(first,second);
        assertTrue(service.matches("password",first));
        assertFalse(service.matches("wrong",first));
        assertFalse(service.matches("password","old-plaintext-password"));
    }
}
