package com.example.jukebox;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/jukeboxes")
public class JukeboxController {
    private JukeboxRepository repository;
    private JukeboxService service;

    public JukeboxController(JukeboxRepository repository, JukeboxService service) {
        this.repository = repository;
        this.service = service;
    }

    /**
     * Gets one jukebox by id.
     *
     * @param id the id of the jukebox
     * @return the jukebox with the id. If it does not exist, a JukeboxNotFoundException will be thrown.
     */
    @GetMapping("/{id}")
    Jukebox one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new JukeboxNotFoundException(id));
    }

    /**
     * Returns a paginated list of jukeboxes that have components to support a given setting id. If no setting id
     * is provided, all jukeboxes will be considered as able to support the setting.
     * The page offset and limit can also be specified. The query parameters are all optional.
     *
     * @param settingID the id of the setting in the database (optional)
     * @param offset at what index to start the page (page number) (optional)
     * @param limit page size (optional)
     * @return a paginated list of jukeboxes that have components to support a given setting id
     */
    @GetMapping
    @ResponseBody
    public List<Jukebox> getAllJukeboxes(
            @RequestParam Optional<UUID> settingID,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "5") Integer limit)
    {
        return service.getAllJukeboxes(settingID, offset, limit);
    }
}
