# jukebox-remote-control

This is a web application to remote control Jukebox settings. Depending on what components (hardware modules) a jukebox has, it may or may not support a setting (a key-value pair). 

It contains a REST API with a single GET endpoint which returns a paginated list of jukeboxes that suppport a given setting id. It supports following query parameters:

- settingId - setting id
- offset - specifies at what index start the page
- limit - specifies the page size

## Usage:
1. `./mvnw spring-boot:run`, or run the app from an IDE.
2. Then access via a browser at http://localhost:8080/, or `curl localhost:8080`. For example:
```
curl -v localhost:8080/jukeboxes/ | json_pp
curl -v localhost:8080/settings/ | json_pp
```

### Versions
- Spring Boot 2.6.6
- Java 11
