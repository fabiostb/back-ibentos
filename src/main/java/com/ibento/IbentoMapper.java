package com.ibento;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IbentoMapper {

	Ibento toModel(IbentoDto ibentoDto);

	IbentoDto toDto(Ibento ibento);

	List<IbentoDto> toDto(List<Ibento> models);
}
