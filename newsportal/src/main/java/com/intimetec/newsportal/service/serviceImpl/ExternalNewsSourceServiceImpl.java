package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.ExternalNewsSourceDTO;
import com.intimetec.newsportal.mapper.ExternalNewsSourceMapper;
import com.intimetec.newsportal.model.ExternalNewsSource;
import com.intimetec.newsportal.repository.ExternalNewsSourceRepository;
import com.intimetec.newsportal.service.ExternalNewsSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExternalNewsSourceServiceImpl implements ExternalNewsSourceService {

    @Autowired
    private ExternalNewsSourceMapper externalNewsSourceMapper;
    @Autowired
    private ExternalNewsSourceRepository externalNewsSourceRepository;

    public List<ExternalNewsSourceDTO> getAllExternalNewsSources(){
        List<ExternalNewsSource> externalNewsSources = externalNewsSourceRepository.findAll();
        System.out.println(externalNewsSources.get(0).toString());

        List<ExternalNewsSourceDTO> externalNewsSourceDTOList = new ArrayList<>();

        for(ExternalNewsSource externalNewsSource : externalNewsSources){
            ExternalNewsSourceDTO externalNewsSourceDTO = ExternalNewsSourceMapper.toDTO(externalNewsSource);
            externalNewsSourceDTOList.add(externalNewsSourceDTO);
        }

        return externalNewsSourceDTOList;
    }
}
