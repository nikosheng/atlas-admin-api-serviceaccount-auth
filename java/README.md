# Java Spring Atlas Service Account Exampkle 

## Introduction

Example is using [Spring Security OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2)

## Running Application

To run Application follow these steps

### 1. **Set up Maven and Java Environment**

Make sure you have:
- **Java 17** installed.
- **Maven** installed (you can check it with `mvn -v`).
- Your `MONGODB_ATLAS_CLIENT_ID` and `MONGODB_ATLAS_CLIENT_SECRET` environment variables set.

### 2. **Compile the Project**

Navigate to the project directory where your `pom.xml` is located and run the following Maven command to compile the project:

```bash
mvn clean compile
```

### 3. **Run the Application**

Once the compilation is successful, run the Java application using:

```bash
mvn exec:java -Dexec.mainClass="MongoDBAtlasOAuthClient"
```

## Debugging Tips
- If you have issues, check the logs printed by `SLF4J` to pinpoint the problem.
- Use `logger.info` or `logger.error` for debugging within the code.
