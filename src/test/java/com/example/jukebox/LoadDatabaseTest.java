package com.example.jukebox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for correctly loading jukes.json and settings.json into the database.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoadDatabaseTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	private JukeboxController jukeboxController;
	private JukeboxRepository jukeboxRepository = Mockito.mock(JukeboxRepository.class);
	private JukeboxService jukeboxService = Mockito.mock(JukeboxService.class);
	private SettingRepository settingRepository = Mockito.mock(SettingRepository.class);

	@BeforeEach
	void initUseCase() {
		jukeboxController = new JukeboxController(jukeboxRepository, jukeboxService);
	}

	@Test
	public void contextLoads(){
		assertThat(jukeboxController).isNotNull();
	}

	@Test
	public void jukeboxJsonLoads() {
		assertThat(Arrays.toString(jukeboxRepository.findAll().toArray()).contains("5ca94a8a13385f0c82aa9f2e"));
	}

	@Test
	public void jukeboxJsonLoadsHTTP() {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/jukeboxes",
					String.class).contains("5ca94a8a4aeb7ab33a5e1047"));
	}

	@Test
	public void settingJsonLoads() {
		assertThat(Arrays.toString(settingRepository.findAll().toArray()).contains("76b7f0b8-eb8e-4156-a6cb-43c6b421c69e"));
	}

	@Test
	public void settingJsonLoadsHTTP() {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/settings",
				String.class).contains("9ac2d388-0f1b-4137-8415-02b953dd76f7"));
	}
}
