package com.intimetec.newsportal.externalserversource;


import com.intimetec.newsportal.dto.*;
import com.intimetec.newsportal.mapper.ExternalNewsSourceMapper;
import com.intimetec.newsportal.model.ExternalNewsSource;
import com.intimetec.newsportal.repository.ExternalNewsSourceRepository;
import com.intimetec.newsportal.service.serviceImpl.ExternalNewsSourceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExternalNewsSourceServiceImplTest {

    private ExternalNewsSourceRepository repository;
    private ExternalNewsSourceServiceImpl service;

    @BeforeEach
    void setUp() throws Exception {
        repository = mock(ExternalNewsSourceRepository.class);
        service = new ExternalNewsSourceServiceImpl();

        Field repoField = ExternalNewsSourceServiceImpl.class.getDeclaredField("externalNewsSourceRepository");
        repoField.setAccessible(true);
        repoField.set(service, repository);

        Field mapperField = ExternalNewsSourceServiceImpl.class.getDeclaredField("externalNewsSourceMapper");
        mapperField.setAccessible(true);
        mapperField.set(service, new ExternalNewsSourceMapper());
    }

    @Test
    void getAllExternalNewsSources_shouldReturnDTOList() {
        ExternalNewsSource source = new ExternalNewsSource();
        ExternalNewsSourceDTO dto = new ExternalNewsSourceDTO();
        when(repository.findAll()).thenReturn(List.of(source));

        try (MockedStatic<ExternalNewsSourceMapper> mockStatic = mockStatic(ExternalNewsSourceMapper.class)) {
            mockStatic.when(() -> ExternalNewsSourceMapper.toExternalNewsSourceDTO(source)).thenReturn(dto);
            List<ExternalNewsSourceDTO> result = service.getAllExternalNewsSources();
            assertEquals(1, result.size());
            assertEquals(dto, result.get(0));
        }
    }

    @Test
    void updateServerDetails_shouldSaveUpdatedEntity() {
        ExternalNewsSource source = new ExternalNewsSource();
        ExternalServerUpdateDTO dto = new ExternalServerUpdateDTO();
        dto.setServerID(1L);
        dto.setApiKey("key");

        when(repository.getReferenceById(1L)).thenReturn(source);
        service.updateServerDetails(dto);
        verify(repository, times(1)).save(source);
    }

    @Test
    void findAllByOrderByLastAccessedAsc_shouldReturnDTOList() {
        ExternalNewsSource source = new ExternalNewsSource();
        ExternalNewsSourceDTO dto = new ExternalNewsSourceDTO();
        when(repository.findAllByOrderByLastAccessedAsc()).thenReturn(List.of(source));

        try (MockedStatic<ExternalNewsSourceMapper> mockStatic = mockStatic(ExternalNewsSourceMapper.class)) {
            mockStatic.when(() -> ExternalNewsSourceMapper.toExternalNewsSourceDTOList(List.of(source))).thenReturn(List.of(dto));
            List<ExternalNewsSourceDTO> result = service.findAllByOrderByLastAccessedAsc();
            assertEquals(1, result.size());
            assertEquals(dto, result.get(0));
        }
    }

    @Test
    void updateNewsSourceLastAccessed_shouldSaveEntity() {
        ExternalNewsSourceDTO dto = new ExternalNewsSourceDTO();
        ExternalNewsSource source = new ExternalNewsSource();

        try (MockedStatic<ExternalNewsSourceMapper> mockStatic = mockStatic(ExternalNewsSourceMapper.class)) {
            mockStatic.when(() -> ExternalNewsSourceMapper.toEntity(dto)).thenReturn(source);
            service.updateNewsSourceLastAccessed(dto);
            verify(repository, times(1)).save(source);
        }
    }

    @Test
    void getAllAvailableExternalNewsSources_shouldReturnAvailableDTOList() {
        ExternalNewsSource source = new ExternalNewsSource();
        ExternalNewsSourceDTO dto = new ExternalNewsSourceDTO();
        when(repository.findByStatusTrue()).thenReturn(List.of(source));

        try (MockedStatic<ExternalNewsSourceMapper> mockStatic = mockStatic(ExternalNewsSourceMapper.class)) {
            mockStatic.when(() -> ExternalNewsSourceMapper.toExternalNewsSourceDTOList(List.of(source))).thenReturn(List.of(dto));
            List<ExternalNewsSourceDTO> result = service.getAllAvailableExternalNewsSources();
            assertEquals(1, result.size());
            assertEquals(dto, result.get(0));
        }
    }
}
