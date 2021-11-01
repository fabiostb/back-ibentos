package com.ibento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class IbentoService {

	@Autowired
	private IbentoRepository repository;

	@Autowired
	private IbentoMapper mapper;

	@Autowired
	private IbentoInstant instant;

	public IbentoDto getIbento(Long id) {
		Optional<Ibento> ibentoOpt = this.repository.findById(id);
		return ibentoOpt.isPresent() ? this.mapper.toDto(ibentoOpt.get()) : null;
	}

	public List<IbentoListDto> getIbentos() {
		List<IbentoDto> ibentosDto = this.mapper.toDto(repository.findAll());
		return ibentosDto.stream().map(this::toList).collect(Collectors.toList());
	}

	public List<IbentoDto> getIbentos(String name) {
		return this.mapper.toDto(this.repository.findByNameContainingIgnoreCase(name));
	}

	public IbentoDto createIbento(IbentoDto ibentoToCreate) {
		return this.updateIbento(ibentoToCreate);
	}

	public IbentoDto updateIbento(IbentoDto ibentoToUpdate) {
		Ibento savedIbento = this.repository.saveAndFlush(this.mapper.toModel(ibentoToUpdate));
		return this.mapper.toDto(savedIbento);
	}

	public void deleteIbento(Long id) {
		this.repository.delete(new Ibento(id));
	}

	private IbentoListDto toList(IbentoDto ibentoDto) {
		IbentoListDto list = new IbentoListDto();
		list.ibento = ibentoDto;
		list.minutesBeforeStart = this.calculateMinutesBeforeStart(ibentoDto);
		return list;
	}

	private Long calculateMinutesBeforeStart(IbentoDto ibento) {
		Instant nowUTC = this.instant.now();
		if (nonNull(ibento.getStartDate()) && nowUTC.isBefore(ibento.getStartDate().toInstant())) {
			return Duration.between(nowUTC, ibento.getStartDate().toInstant()).toMinutes();
		}
		return null;
	}
}
