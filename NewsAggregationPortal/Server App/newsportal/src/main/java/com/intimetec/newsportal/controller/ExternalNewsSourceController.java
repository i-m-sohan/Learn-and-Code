package com.intimetec.newsportal.controller;

import com.intimetec.newsportal.dto.ExternalServerDetailDTO;
import com.intimetec.newsportal.dto.ExternalServerStatusDTO;
import com.intimetec.newsportal.dto.ExternalServerUpdateDTO;
import com.intimetec.newsportal.service.ExternalNewsSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/newsportal.intimetec.com/admin")
public class ExternalNewsSourceController {

    @Autowired
    private ExternalNewsSourceService externalNewsSourceService;

    @GetMapping("/server-status")
    public ResponseEntity<?> getAllExternalServerStatuses(){
        List<ExternalServerStatusDTO> externalServerStatusDTOList = externalNewsSourceService.getAllExternalServerStatuses();
        return new ResponseEntity<>(externalServerStatusDTOList, HttpStatus.OK);
    }

    @GetMapping("/server-detail")
    public ResponseEntity<?> getAllExternalServerDetail(){
        List<ExternalServerDetailDTO> externalServerDetailDTOList = externalNewsSourceService.getAllExternalServerDetails();
        return new ResponseEntity<>(externalServerDetailDTOList, HttpStatus.OK);
    }

    @PatchMapping("/server-update")
    public ResponseEntity<?> updateServerDetails(@RequestBody ExternalServerUpdateDTO externalServerUpdateDTO){
        externalNewsSourceService.updateServerDetails(externalServerUpdateDTO);
        return new ResponseEntity<>("Api key Updated!", HttpStatus.OK);
    }

}
