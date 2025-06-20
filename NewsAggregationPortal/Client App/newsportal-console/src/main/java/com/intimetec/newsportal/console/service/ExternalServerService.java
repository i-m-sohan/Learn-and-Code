package com.intimetec.newsportal.console.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.newsportal.console.dto.ExternalServerDetailDTO;
import com.intimetec.newsportal.console.dto.ExternalServerStatusDTO;
import com.intimetec.newsportal.console.dto.ExternalServerUpdateDTO;
import com.intimetec.newsportal.console.utils.ApiUtilis;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Service
public class ExternalServerService {

    private static final String BASE_URL = "http://localhost:8080/newsportal.intimetec.com/admin";

    public List<ExternalServerStatusDTO> getAllServerStatuses() {
        try {
            HttpResponse<String> response = ApiUtilis.get(BASE_URL + "/server-status", null);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), new TypeReference<List<ExternalServerStatusDTO>>() {});
        } catch (Exception e) {
            System.out.println("Failed to fetch server status: " + e.getMessage());
            return List.of();
        }
    }

    public List<ExternalServerDetailDTO> getAllServerDetails() {
        try {
            HttpResponse<String> response = ApiUtilis.get(BASE_URL + "/server-detail", null);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), new TypeReference<List<ExternalServerDetailDTO>>() {});
        } catch (Exception e) {
            System.out.println(" Failed to fetch server details: " + e.getMessage());
            return List.of();
        }
    }
    public void updateServerApiKey(ExternalServerUpdateDTO dto) {
        try {
            Map<String, String> body = Map.of(
                    "serverID", String.valueOf(dto.getServerID()),
                    "apiKey", dto.getApiKey()
            );

            HttpResponse<String> response = ApiUtilis.patch(BASE_URL + "/server-update", body);
            System.out.println(response.body());

        } catch (Exception e) {
            System.out.println("Failed to update API key: " + e.getMessage());
        }
    }

}

