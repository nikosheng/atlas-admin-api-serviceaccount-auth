import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

public class MongoDBAtlasOAuthClient {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBAtlasOAuthClient.class);

    private static final String TOKEN_URI = "https://cloud.mongodb.com/api/oauth/token";
    private static final String API_URL = "https://cloud.mongodb.com/api/atlas/v2/";

    public String fetchAccessToken() {
        // Prepare the request body for OAuth2 client credentials flow
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(System.getenv("MONGODB_ATLAS_CLIENT_ID"), System.getenv("MONGODB_ATLAS_CLIENT_SECRET"));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Make the request to get the access token
        var request = new HttpEntity<>(body, headers);
        var restTemplate = new RestTemplate();
        var response = restTemplate.exchange(TOKEN_URI, HttpMethod.POST, request, Map.class);

        // Extract the access token from the response
        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, String> responseBody = response.getBody();
            return responseBody != null ? responseBody.get("access_token") : null;
        } else {
            logger.error("Failed to fetch access token. Status code: {}", response.getStatusCode());
            return null;
        }
    }

    public String fetchData() {
        String accessToken = fetchAccessToken();
        if (accessToken == null) {
            logger.error("Access token is null, cannot fetch data.");
            return null;
        }

        // Make the API request with the access token
        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.set("Accept","application/vnd.atlas.2023-01-01+json");
        headers.set("User-Agent", "SA-Auth-Example");

        HttpEntity<String> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        // Return the response body
        return response.getBody();
    }

    public static void main(String[] args) {
        MongoDBAtlasOAuthClient client = new MongoDBAtlasOAuthClient();
        String data = client.fetchData();
        if (data != null) {
            logger.info("Received data: {}", data);
        } else {
            logger.error("Failed to fetch data.");
        }
    }
}
