package com.ibento;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import util.Utils;

import static com.jasongoodwin.monads.Try.ofFailable;
import static util.Utils.emptyIfNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IbentoMapper {

    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";

    public List<IbentoDto> toDto(List<DBObject> bdObjects) {
        return emptyIfNull(bdObjects).stream().map(bd -> this.toDto(bd)).collect(Collectors.toList());
    }

    public IbentoDto toDto(DBObject bdObject) {
    	IbentoDto dto = new IbentoDto();
    	dto.id =  ofFailable(() -> ((ObjectId)bdObject.get(ID)).toHexString()).orElse(null);
        dto.name = ofFailable(() -> (String) bdObject.get(NAME)).orElse(null);
        dto.description = ofFailable(() -> (String) bdObject.get(DESCRIPTION)).orElse(null);
        dto.startDate = ofFailable(() -> (Date)bdObject.get(START_DATE)).orElse(null);
        dto.endDate = ofFailable(() -> (Date)bdObject.get(END_DATE)).orElse(null);
    	return dto;
    }

    public DBObject toDBObject(IbentoDto dto) {
        BasicDBObject dbObject = new BasicDBObject();
        return dbObject
                .append(ID, dto.id)
                .append(NAME, dto.name)
                .append(DESCRIPTION, dto.description)
                .append(START_DATE, dto.startDate)
                .append(END_DATE, dto.endDate);
    }
}
