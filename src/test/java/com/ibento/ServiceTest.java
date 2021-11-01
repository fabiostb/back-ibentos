package com.ibento;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.time.LocalDate.of;
import static java.time.ZoneOffset.UTC;
import static java.util.Arrays.asList;
import static java.util.Date.from;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class ServiceTest {

	private final static Long IBENTO_ID = 1L;

	@InjectMocks
	private IbentoService target;

	@Mock
	private IbentoRepository repository;

	@Mock
	private IbentoMapper mapper;

	@Mock
	private IbentoInstant instant;

	@BeforeEach
	public void init() {
		openMocks(this);
		initRepositoryMock();
	}

	@Test
	public void whenGetIbentos_givenOneIbentoWithStartDateAfterNow_thenReturnsIbentoInListWithMinutesBeforeStart() {

		// ** Given

		// 2021-04-18 at 10h00
		Date starDate = from(of(2021, 4, 18).atTime(10, 0).toInstant(UTC));
		initMapperMock(starDate);

		// Now is 2021-04-18 at 12h00
		Instant now = of(2021, 4, 18).atTime(7, 0).toInstant(UTC);
		initInstantMock(now);

		// ** When
		List<IbentoListDto> ibentos = target.getIbentos();

		// ** Then 12h - 10h = 180minutes
		long minutesBeforeStart = 180L;

		if (nonNull(ibentos) && nonNull(ibentos.get(0))) {
			assertEquals(minutesBeforeStart, ibentos.get(0).minutesBeforeStart);
		} else {
			fail();
		}
	}

	@Test
	public void whenGetIbentos_givenOneIbentoWithStartDateBeforeNow_thenReturnsIbentoInListMinutesBeforeStartNULL() {

		// ** Given

		// 2021-04-18 at 7h00
		Date starDate = from(of(2021, 4, 18).atTime(7, 0).toInstant(UTC));
		initMapperMock(starDate);

		// ** Now is 2021-04-18 at 10h00
		Instant now = of(2021, 4, 18).atTime(10, 0).toInstant(UTC);
		initInstantMock(now);

		// ** When
		List<IbentoListDto> ibentos = target.getIbentos();

		// *** 7h < 10h then NULL  
		if (nonNull(ibentos) && nonNull(ibentos.get(0))) {
			assertNull(ibentos.get(0).minutesBeforeStart);
		} else {
			fail();
		}
	}

	private void initInstantMock(Instant now) {
		when(this.instant.now()).thenReturn(now);
	}

	private void initMapperMock(Date starDate) {
		List<IbentoDto> ibentosDto = new ArrayList<>();
		IbentoDto ibentoDto = new IbentoDto();
		ibentoDto.setStartDate(starDate);
		ibentosDto.add(ibentoDto);
		when(mapper.toDto(asList(new Ibento(IBENTO_ID)))).thenReturn(ibentosDto);
	}

	private void initRepositoryMock() {
		List<Ibento> ibentosModel = new ArrayList<>();
		Ibento ibetoModel = new Ibento();
		ibetoModel.setId(IBENTO_ID);
		ibentosModel.add(ibetoModel);
		when(repository.findAll()).thenReturn(ibentosModel);
	}
}
