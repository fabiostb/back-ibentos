package com.ibento;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.Document;
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

    public List<IbentoDto> toDto(List<Document> document) {
        return emptyIfNull(document).stream().map(bd -> this.toDto(bd)).collect(Collectors.toList());
    }

    public IbentoDto toDto(Document document) {
        IbentoDto dto = new IbentoDto();
        dto.id = ofFailable(() -> ((ObjectId) document.get(ID)).toHexString()).orElse(null);
        dto.name = ofFailable(() -> (String) document.get(NAME)).orElse(null);
        dto.description = ofFailable(() -> (String) document.get(DESCRIPTION)).orElse(null);
        dto.startDate = ofFailable(() -> (Date) document.get(START_DATE)).orElse(null);
        dto.endDate = ofFailable(() -> (Date) document.get(END_DATE)).orElse(null);
        return dto;
    }

    public Document toDocument(IbentoDto dto) {
        Document document = new Document();
        return document
                .append(ID, dto.id)
                .append(NAME, dto.name)
                .append(DESCRIPTION, dto.description)
                .append(START_DATE, dto.startDate)
                .append(END_DATE, dto.endDate);
    }
}
