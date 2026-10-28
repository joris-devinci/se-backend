# Install guide

1. Install JDK 21
- https://adoptium.net/download?link=https%3A%2F%2Fgithub.com%2Fadoptium%2Ftemurin21-binaries%2Freleases%2Fdownload%2Fjdk-21.0.12.1%252B1%2FOpenJDK21U-jdk_x64_windows_hotspot_21.0.12.1_1.msi&vendor=Adoptium
---

2. Install Maven (optional when using the included wrapper)
- https://maven.apache.org/download.cgi
Install the binary zip and add it to your you path e.g.(C:\Users\joris\Downloads\apache-maven-3.10.0-bin\apache-maven-3.10.0\bin)

3. Intall Extentions in VS code
- Spring Boot Tools
- Spring Initializer Java Support
---

# How to run

Java 21 is required. The included Maven wrapper downloads Maven and the project
dependencies automatically, so a separate Maven installation is optional.

## macOS

Install Java 21 once:

```sh
brew install --cask temurin@21
```

Open a terminal in the `se-backend` folder and run:

```sh
JAVA_HOME=$(/usr/libexec/java_home -v 21) FRONTEND_ORIGINS=http://localhost:5173 sh ./mvnw spring-boot:run
```

This selects Java 21 for this command, even if another Java version is the default.

## Windows (PowerShell)

Install JDK 21, configure `JAVA_HOME` to its installation folder, and open a new
terminal. Check `java -version` shows Java 21. From the `se-backend` folder, run:

```powershell
$env:FRONTEND_ORIGINS = "http://localhost:5173"
.\mvnw.cmd spring-boot:run
```

## Frontend connection

Leave the backend terminal running. The API runs at `http://localhost:8080`.
Start the frontend in a separate terminal and configure its API base URL to this
address. Frontend API requests must include `credentials: "include"` for login
cookies to work.

The commands above allow a frontend running at `http://localhost:5173`. If your
frontend uses a different port, replace that origin in `FRONTEND_ORIGINS`. Use
`localhost` for both applications rather than mixing it with `127.0.0.1`.

Open `http://localhost:8080/user/me` to check the backend. Before login, the expected
response is HTTP 401 with `{"detail":"Please log in to continue."}`.

Accounts are saved automatically in `data/demo.mv.db` using H2; no separate database
installation is needed. This local database is ignored by Git. The H2 browser
console is disabled. Login sessions expire after 24 hours of inactivity; log in
again if a backend restart clears your session.

Stop the backend with Ctrl+C. If port 8080 is already in use, stop the previous
backend instance before starting another one.

---

# How to develop

1. Create a Feature branch e.g.(Add-Login-Logic)
2. Create a PR from your feature branch to the develop branch
3. Ask someone to review your changes
4. When approved merge your branch to develop