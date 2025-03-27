package com.wayup.Fola_Logistics.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wayup.Fola_Logistics.dto.response.GeocodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class GeocodeService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${google.geocoding.base.url}")
    private String geocodingBaseUrl;

    @Value ("${google.api.key}")
    private String apiKey;

    public GeocodeResponse getGeocodeAddress(String address) {
        try {
            String requestUrl = geocodingBaseUrl + "?address=" + address.replace(" ",  "+") + "&key=" + apiKey;

            String response = restTemplate.getForObject(requestUrl, String.class);

            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            JsonObject location = jsonObject.getAsJsonArray("results")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("geometry")
                    .getAsJsonObject("location");
            double lat = location.get("lat").getAsDouble();
            double lng = location.get("lng").getAsDouble();
            return new GeocodeResponse(lat, lng);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

//                            OR
//        URI uri = UriComponentsBuilder.fromHttpUrl(geocodingBaseUrl)
//                .queryParam("address", address)
//                .queryParam("key", apiKey)
//                .build().toUri();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Accept", "application/json");
////            headers.set("Authorization", "Bearer " + apiKey);
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            JsonObject jsonObject = new JsonParser().parse(response.getBody()).getAsJsonObject();
//            JsonObject location = jsonObject.getAsJsonArray("results")
//                    .get(0).getAsJsonObject()
//                    .getAsJsonObject("geometry")
//                    .getAsJsonObject("location");
//            double lat = location.get("lat").getAsDouble();
//            double lng = location.get("lng").getAsDouble();
//            return new GeocodeResponse(lat, lng);
//        }
    }
}
