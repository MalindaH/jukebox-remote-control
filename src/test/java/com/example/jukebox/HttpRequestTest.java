package com.example.jukebox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test querying settingId, offset, and limit for jukeboxes. The tests look for the correct jukebox ids to show up
 * on specific pages, and thus these tests can only be used for this current jukes.json file. If the dataset will
 * be updated, the number of jukeboxes returned and the components they have should be compared, instead of ids.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void jukeboxGetPaginatedList1() throws Exception {
        List<String> ids = getIds("/jukeboxes?limit=5&offset=1");
        String[] shouldContain = {"5ca94a8ac470d3e47cd4713c","5ca94a8a77e20d15a7d16d0a","5ca94a8a75c231bb18715112",
                "5ca94a8a20905ffff6f0561c","5ca94a8a3227b0a360f41078"};
        assertThat(ids.equals(Arrays.asList(shouldContain)));
    }

    @Test
    public void jukeboxGetPaginatedList2() throws Exception {
        List<String> ids = getIds("/jukeboxes?limit=3&offset=3");
        String[] shouldContain = {"5ca94a8a13385f0c82aa9f2e","5ca94a8aafb9d8c4e4fddf02","5ca94a8a1639eb9ea30609f0"};
        assertThat(ids.equals(Arrays.asList(shouldContain)));
    }

    @Test
    public void jukeboxGetBySettingId() throws Exception {
        List<String> ids = getIds("/jukeboxes?settingID=2321763c-8e06-4a31-873d-0b5dac2436da");
        String[] shouldContain = {"5ca94a8a77e20d15a7d16d0a","5ca94a8acc046e7aa8040605"};
        assertThat(ids.equals(Arrays.asList(shouldContain)));
    }

    @Test
    public void jukeboxGetBySettingIdNoFound() throws Exception {
        String test = this.restTemplate.getForObject("http://localhost:" + port + "/jukeboxes?settingID=2709ac80-2593-4dcf-ae72-a3c2fac023a5",
                String.class);
        assertEquals(test, "{}");
    }

    @Test
    public void jukeboxGetBySettingIdOffsetLimit() throws Exception {
        List<String> ids = getIds("/jukeboxes?settingID=515ef38b-0529-418f-a93a-7f2347fc5805&limit=3&offset=2");
        String[] shouldContain = {"5ca94a8af0853f96c44fa858","5ca94a8acfdeb5e01e5bdbe8","5ca94a8a1d1bc6d59afb9392"};
        assertThat(ids.equals(Arrays.asList(shouldContain)));
    }

    private List<String> getIds(String url) throws JSONException {
        String test = this.restTemplate.getForObject("http://localhost:" + port + url,
                String.class);
        JSONObject obj = new JSONObject(test);
        JSONArray arr = obj.getJSONObject("_embedded").getJSONArray("jukeboxList");
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            JSONObject entry = arr.getJSONObject(i);
            ids.add((String) entry.get("id"));
        }
        return ids;
    }
}