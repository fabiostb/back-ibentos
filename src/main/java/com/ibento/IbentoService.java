package com.ibento;

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
        DBObject ibentoObj = this.dao.get(id);
        return this.mapper.toDto(ibentoObj);
    }

    public List<IbentoListDto> getIbentos() {
        List<DBObject> ibentos = this.dao.getAll();
        List<IbentoDto> ibentosDto = this.mapper.toDto(ibentos);
        return ibentosDto.stream().map(this::toList).collect(Collectors.toList());
    }

    public List<IbentoDto> getIbentos(String name) {
        return this.mapper.toDto(this.dao.find(name));
    }

    public IbentoDto createIbento(IbentoDto ibentoToCreate) {
        DBObject bdObject = this.mapper.toDBObject(ibentoToCreate);
        return this.mapper.toDto(this.dao.create(bdObject));
    }

    public IbentoDto updateIbento(IbentoDto ibentoToUpdate) {
        DBObject bdObject = this.mapper.toDBObject(ibentoToUpdate);
        return this.mapper.toDto(this.dao.update(bdObject));
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
