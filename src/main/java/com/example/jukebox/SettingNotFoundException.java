package com.example.jukebox;

import java.util.UUID;

public class SettingNotFoundException extends RuntimeException{

    /**
     * An Exception indicating no setting with the given id is found.
     *
     * @param id the id of the setting
     */
    SettingNotFoundException(UUID id) {
        super("Could not find setting " + id);
    }
}
