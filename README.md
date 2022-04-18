# jukebox-remote-control

This is a web application to remote control Jukebox settings. Depending on what components (hardware modules) a jukebox has, it may or may not support a setting (a key-value pair). 

It contains a REST API with a single GET endpoint which returns a paginated list of jukeboxes that suppport a given setting id. It supports following query parameters:

- settingId - setting id
- offset - specifies at what index start the page
- limit - specifies the page size

### Versions
- Spring Boot 2.6.6
- Java 11
