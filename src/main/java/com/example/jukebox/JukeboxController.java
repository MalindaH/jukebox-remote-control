package com.example.jukebox;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    EntityModel<Jukebox> one(@PathVariable String id) {

        Jukebox jukebox = repository.findById(id)
                .orElseThrow(() -> new JukeboxNotFoundException(id));

        return EntityModel.of(jukebox,
                linkTo(methodOn(JukeboxController.class).one(jukebox.getId())).withSelfRel(),
                linkTo(methodOn(JukeboxController.class).getAllJukeboxes(Optional.empty(),0,5))
                        .withRel("jukeboxes"));
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
    CollectionModel<EntityModel<Jukebox>> getAllJukeboxes(
            @RequestParam Optional<UUID> settingID,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "5") Integer limit)
    {
        List<Jukebox> jukes = service.getAllJukeboxes(settingID, offset, limit);
        if (jukes.size() > 0) {
            List<EntityModel<Jukebox>> jukesEntity = jukes.stream()
                    .map(jukebox -> EntityModel.of(jukebox)).collect(Collectors.toList());

            if(offset > 0)
                return CollectionModel.of(jukesEntity,
                        linkTo(methodOn(JukeboxController.class).getAllJukeboxes(settingID,offset,limit)).withSelfRel(),
                        linkTo(methodOn(JukeboxController.class).getAllJukeboxes(settingID,offset+1,limit))
                                .withRel("next_page"),
                        linkTo(methodOn(JukeboxController.class).getAllJukeboxes(settingID,offset-1,limit))
                                .withRel("prev_page"));
            else
                return CollectionModel.of(jukesEntity,
                        linkTo(methodOn(JukeboxController.class).getAllJukeboxes(settingID,offset,limit)).withSelfRel(),
                        linkTo(methodOn(JukeboxController.class).getAllJukeboxes(settingID,offset+1,limit))
                                .withRel("next_page"));
        } else {
            return CollectionModel.empty();
        }

    }
}
