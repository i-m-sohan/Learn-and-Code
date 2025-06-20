package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.ExternalNewsSourceDTO;
import com.intimetec.newsportal.dto.ExternalServerDetailDTO;
import com.intimetec.newsportal.dto.ExternalServerStatusDTO;
import com.intimetec.newsportal.dto.ExternalServerUpdateDTO;
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

    @Override
    public List<ExternalNewsSourceDTO> getAllExternalNewsSources(){
        List<ExternalNewsSource> externalNewsSources = externalNewsSourceRepository.findAll();
        List<ExternalNewsSourceDTO> externalNewsSourceDTOList = new ArrayList<>();

        for(ExternalNewsSource externalNewsSource : externalNewsSources){
            ExternalNewsSourceDTO externalNewsSourceDTO = ExternalNewsSourceMapper.toExternalNewsSourceDTO(externalNewsSource);
            externalNewsSourceDTOList.add(externalNewsSourceDTO);
        }
        return externalNewsSourceDTOList;
    }

    @Override
    public List<ExternalServerStatusDTO> getAllExternalServerStatuses() {
        List<ExternalNewsSource> externalNewsSourceList = externalNewsSourceRepository.findAll();
        List<ExternalServerStatusDTO> externalServerStatusDTOList = externalNewsSourceMapper.toStatusDTOList(externalNewsSourceList);
        return externalServerStatusDTOList;
    }

    @Override
    public List<ExternalServerDetailDTO> getAllExternalServerDetails(){
        List<ExternalNewsSource> externalNewsSourceList = externalNewsSourceRepository.findAll();
        List<ExternalServerDetailDTO> ExternalServerDetailDTOList = externalNewsSourceMapper.toDetailDTOList(externalNewsSourceList);
        return ExternalServerDetailDTOList;
    }

    @Override
    public void updateServerDetails(ExternalServerUpdateDTO externalServerUpdateDTO){
        ExternalNewsSource externalNewsSource = externalNewsSourceRepository.getReferenceById(externalServerUpdateDTO.getServerID());
        externalNewsSource.setApiKey(externalServerUpdateDTO.getApiKey());
        externalNewsSourceRepository.save(externalNewsSource);
    }

    @Override
    public List<ExternalNewsSourceDTO> findAllByOrderByLastAccessedAsc(){
        List<ExternalNewsSource> externalNewsSourceList = externalNewsSourceRepository.findAllByOrderByLastAccessedAsc();
        return ExternalNewsSourceMapper.toExternalNewsSourceDTOList(externalNewsSourceList);
    }

    @Override
    public void updateNewsSourceLastAccessed(ExternalNewsSourceDTO externalNewsSourceDTO){
        ExternalNewsSource externalNewsSource = externalNewsSourceMapper.toEntity(externalNewsSourceDTO);
        externalNewsSourceRepository.save(externalNewsSource);
    }
}
