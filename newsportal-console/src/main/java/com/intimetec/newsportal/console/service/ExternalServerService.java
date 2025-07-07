package com.intimetec.newsportal.console.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.newsportal.console.dto.ExternalServerDetailDTO;
import com.intimetec.newsportal.console.dto.ExternalServerStatusDTO;
import com.intimetec.newsportal.console.dto.ExternalServerUpdateDTO;
import com.intimetec.newsportal.console.exception.ExternalServerException;
import com.intimetec.newsportal.console.utils.ApiUtilis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Service
public class ExternalServerService {

    private static final String BASE_URL = "http://localhost:8080/newsportal.intimetec.com/admin";

    @Autowired
    private ObjectMapper objectMapper;

    public List<ExternalServerStatusDTO> getAllServerStatuses() {
        try {
            HttpResponse<String> response = ApiUtilis.get(BASE_URL + "/server-status", null);
            List<ExternalServerStatusDTO> externalServerStatusDTOS =  objectMapper.readValue(response.body(), new TypeReference<List<ExternalServerStatusDTO>>() {});

            int statusCode = response.statusCode();

            if(statusCode!=200){
                throw new ExternalServerException("Unable to fetch Server Statuses");
            }
            return externalServerStatusDTOS;
        }
        catch(ExternalServerException externalServerException){
           throw externalServerException;
        }
        catch (Exception e) {
            System.out.println("Failed to fetch server status: " + e.getMessage());
            return List.of();
        }
    }

    public List<ExternalServerDetailDTO> getAllServerDetails() {
        try {
            HttpResponse<String> response = ApiUtilis.get(BASE_URL + "/server-detail", null);
            int statusCode = response.statusCode();
            if(statusCode!=200){
                throw new ExternalServerException("Unable to fetch Server Statuses");
            }
            List<ExternalServerDetailDTO> externalServerDetailDTOList =  objectMapper.readValue(response.body(), new TypeReference<List<ExternalServerDetailDTO>>() {});
            return externalServerDetailDTOList;
        }
        catch(ExternalServerException externalServerException){
            throw externalServerException;
        }
        catch (Exception e) {
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
            int statusCode = response.statusCode();
            if(statusCode!=200){
                throw new ExternalServerException("Unable to fetch Server Statuses");
            }
        }
        catch(ExternalServerException externalServerException){
            throw externalServerException;
        }
        catch (Exception e) {
            System.out.println("Failed to update API key: " + e.getMessage());
        }
    }

}

