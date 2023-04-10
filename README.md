# Story App
## _Playing with Kotlin on the server side_

A small rest api to learn a little bit of Kotlin with the following features 

- Project created with Ktor framework
- database in PostgreSQL
- Integration JWT for Authentication

## Dependencies

- [Status pages](https://ktor.io/docs/status-pages.html) - allows to respond appropriately to any failure state based on a thrown exception.
- [Content negotiation and serialization](https://ktor.io/docs/serialization.html) - Negotiating media types between the client and server
- [Logback](https://github.com/qos-ch/logback) - Handle logs.
- [Authentication and authorization](https://ktor.io/docs/authentication.html) - plugin to handle authentication and authorization
- [Exposed](https://github.com/JetBrains/Exposed) - SQL library
- [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/) - PostgreSQL JDBC Driver Postgresql
- [HikariCP](https://github.com/brettwooldridge/HikariCP) - JDBC connection pool

## Database diagram

![img.png](img.png)