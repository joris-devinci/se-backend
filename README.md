# Install guide

1. Install JDK 21
- https://adoptium.net/download?link=https%3A%2F%2Fgithub.com%2Fadoptium%2Ftemurin21-binaries%2Freleases%2Fdownload%2Fjdk-21.0.12.1%252B1%2FOpenJDK21U-jdk_x64_windows_hotspot_21.0.12.1_1.msi&vendor=Adoptium
---

2. Install Maven
- https://maven.apache.org/download.cgi
Install the binary zip and add it to your you path e.g.(C:\Users\joris\Downloads\apache-maven-3.10.0-bin\apache-maven-3.10.0\bin)

3. Intall Extentions in VS code
- Spring Boot Tools
- Spring Initializer Java Support
---

# How to run

- Start = 
- - `.\mvnw.cmd spring-boot:run` (If no maven installed)
- - `mvn spring-boot:run`
- Check API = http://localhost:8080/swagger-ui/index.html#/
- Check DB = http://localhost:8080/h2-console
---

# How to develop

1. Create a Feature branch e.g.(Add-Login-Logic)
2. Create a PR from your feature branch to the develop branch
3. Ask someone to review your changes
4. When approved merge your branch to develop