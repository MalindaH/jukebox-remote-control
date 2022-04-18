# jukebox-remote-control

A web application to remote control Jukebox settings. Depending on what components (hardware modules) a jukebox has, it may or may not support a setting (a key-value pair). 

a REST API with a single GET endpoint which returns a paginated list of jukeboxes that suppport a given setting id. It supports following query parameters:

settingId - setting id
offset - specifies at what index start the page
limit - specifies the page size
