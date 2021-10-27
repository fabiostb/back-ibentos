package com.ibento;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class IbentoService {

    @Autowired
    private IbentoDao dao;

    @Autowired
    private IbentoMapper mapper;

    public IbentoDto getIbento(String id) {
        Document ibentoObj = this.dao.get(id);
        return this.mapper.toDto(ibentoObj);
    }

    public List<IbentoListDto> getIbentos() {
        List<Document> ibentos = this.dao.getAll();
        List<IbentoDto> ibentosDto = this.mapper.toDto(ibentos);
        return ibentosDto.stream().map(this::toList).collect(Collectors.toList());
    }

    public List<IbentoDto> getIbentos(String name) {
        return this.mapper.toDto(this.dao.find(name));
    }

    public IbentoDto createIbento(IbentoDto ibentoToCreate) {
        Document document = this.mapper.toDocument(ibentoToCreate);
        return this.mapper.toDto(this.dao.create(document));
    }

    public IbentoDto updateIbento(IbentoDto ibentoToUpdate) {
        Document document = this.mapper.toDocument(ibentoToUpdate);
        return this.mapper.toDto(this.dao.update(document));
    }

    public void deleteIbento(String id) {
        this.dao.delete(id);
    }

    private IbentoListDto toList(IbentoDto ibentoDto) {
        IbentoListDto list = new IbentoListDto();
        list.ibento = ibentoDto;
        list.minutesBeforeStart = this.calculateMinutesBeforeStart(ibentoDto);
        return list;
    }

    private Long calculateMinutesBeforeStart(IbentoDto ibento) {
        Instant nowUTC = Instant.now();
        if (nonNull(ibento.startDate) && nowUTC.isBefore(ibento.startDate.toInstant())) {
            return Duration.between(nowUTC, ibento.startDate.toInstant()).toMinutes();
        }
        return null;
    }
}
