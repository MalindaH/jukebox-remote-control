package com.example.jukebox;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.json.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class JukeboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(JukeboxApplication.class, args);
	}

	/**
	 * Loads jukebox and setting information from "json/jukes.json" and "json/settings.json" into the database.
	 */
	@Bean
	CommandLineRunner runner(JukeboxRepository jukeboxRepository, SettingRepository settingRepository) {
		return args -> {

			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Jukebox>> jukeboxTypeReference = new TypeReference<>(){};
			TypeReference<List<Setting>> settingTypeReference = new TypeReference<>(){};

			try {
				List<Jukebox> jukes = mapper.readValue(new File("json/jukes.json"), jukeboxTypeReference);
				jukes.forEach(jukeboxRepository::save);

				String settingString = new String(Files.readAllBytes(Paths.get("json/settings.json")));
				JSONObject obj = new JSONObject(settingString);
				JSONArray arr = obj.getJSONArray("settings");
				List<Setting> settings = mapper.readValue(arr.toString(), settingTypeReference);
				settings.forEach(settingRepository::save);
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
	}
}
