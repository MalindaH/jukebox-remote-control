package com.example.jukebox;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import static java.util.stream.Collectors.toSet;

@Service
public class JukeboxService {
    private JukeboxRepository jukeboxRepository;
    private SettingRepository settingRepository;

    public JukeboxService(JukeboxRepository jukeboxRepository, SettingRepository settingRepository) {
        this.jukeboxRepository = jukeboxRepository;
        this.settingRepository = settingRepository;
    }

    /**
     * Returns a paginated list of jukeboxes that have components to support a given setting id. If no setting id
     * is provided, all jukeboxes will be considered as able to support the setting.
     * The page offset and limit need to be specified.
     *
     * @param settingID the id of the setting in the database (optional)
     * @param offset at what index to start the page (page number)
     * @param limit page size
     * @return a paginated list of jukeboxes that have components to support a given setting id
     */
    public List<Jukebox> getAllJukeboxes(Optional<UUID> settingID, Integer offset, Integer limit)
    {
        if(settingID.isPresent()) {
            Setting setting = settingRepository.findById(settingID.get())
                    .orElseThrow(() -> new SettingNotFoundException(settingID.get()));
            List<Jukebox> jukes = jukeboxRepository.findAll();
            List<Jukebox> selectedJukes = new ArrayList<>();
            List<String> requires = setting.getRequires();

            for(Jukebox j: jukes) {
                List<Component> components = j.getComponents();
                if(components.stream()
                        .map(Component::getName)
                        .collect(toSet()).containsAll(requires)) {
                    selectedJukes.add(j);
                }
            }
            return selectedJukes.subList(offset, Math.min(offset + limit, selectedJukes.size()));
        } else {
            PageRequest paging = PageRequest.of(offset, limit);
            Page<Jukebox> pagedResult = jukeboxRepository.findAll(paging);
            if(pagedResult.hasContent()) {
                return pagedResult.getContent();
            } else {
                return new ArrayList<Jukebox>();
            }
        }
    }
}
