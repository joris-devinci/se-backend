Install guide

1. Install JDK 21
- https://adoptium.net/download?link=https%3A%2F%2Fgithub.com%2Fadoptium%2Ftemurin21-binaries%2Freleases%2Fdownload%2Fjdk-21.0.12.1%252B1%2FOpenJDK21U-jdk_x64_windows_hotspot_21.0.12.1_1.msi&vendor=Adoptium

2. Intall Extentions
- Spring Boot Tools
- Spring Initializr Java Support

How to run

- Start = .\mvnw.cmd spring-boot:run
- Check API = http://localhost:8080/swagger-ui/index.html#/
- Check DB = http://localhost:8080/h2-console