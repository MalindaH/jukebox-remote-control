package com.example.jukebox;

public class JukeboxNotFoundException extends RuntimeException{

    /**
     * An Exception indicating no jukebox with the given id is found.
     *
     * @param id the id of the jukebox
     */
    JukeboxNotFoundException(String id) {
        super("Could not find jukebox " + id);
    }
}
