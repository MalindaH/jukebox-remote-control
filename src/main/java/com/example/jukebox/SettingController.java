package com.example.jukebox;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/settings")
public class SettingController {
    private SettingRepository repository;

    public SettingController(SettingRepository settingRepository) {
        this.repository = settingRepository;
    }

    /**
     * Gets a list of all settings from the database.
     *
     * @return a list of all settings
     */
    @GetMapping
    List<Setting> all() {
        return repository.findAll();
    }

    /**
     * Gets one setting by id.
     *
     * @param id the id of the setting
     * @return the setting with the id. If it does not exist, a SettingNotFoundException will be thrown.
     */
    @GetMapping("/{id}")
    Setting one(@PathVariable UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new SettingNotFoundException(id));
    }
}
