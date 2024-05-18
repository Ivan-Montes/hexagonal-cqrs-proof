package dev.ime.infrastructure.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import dev.ime.config.ArtistMapper;
import dev.ime.domain.model.Artist;
import dev.ime.domain.port.inbound.ArtistQueryServicePort;
import dev.ime.infrastructure.dto.ArtistDto;


@WebMvcTest(ArtistQueryController.class)
@AutoConfigureMockMvc(addFilters = false)
class ArtistQueryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private Logger logger;

	@MockBean
	private ArtistQueryServicePort artistQueryService;

	@MockBean
	private ArtistMapper artistMapper;	

	private final String PATH = "/api/artists";
	private List<Artist> artistList;
	private List<ArtistDto> artistDtoList;
	private Artist artistTest;
	private final Long id = 18L;
	private final String name = "John Francis";
	private final String surname = "Bongiovi";
	private final String artisticName = "Bon Jovi";
	private ArtistDto artistDtoTest;
	
	@BeforeEach
	private void createObjects() {		

		artistTest = new Artist.ArtistBuilder()
				.setId(id)
				.setName(name)
				.setSurname(surname)
				.setArtisticName(artisticName)
				.build();
		
		artistDtoTest = new ArtistDto(id, name, surname, artisticName);
		
		artistList = new ArrayList<>();
		artistDtoList = new ArrayList<>();
	}
	
	@Test
	void ArtistQueryController_getAll_ResponseListArtistDto() throws Exception  {
	
		artistList.add(artistTest);
		artistDtoList.add(artistDtoTest);
		Mockito.when(artistQueryService.getAll()).thenReturn(artistList);
		Mockito.when(artistMapper.fromListDomainToListDto(Mockito.anyList())).thenReturn(artistDtoList);
		
		mockMvc.perform(MockMvcRequestBuilders.get(PATH))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.notNullValue()))
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
		;		
	}

	@Test
	void ArtistQueryController_getAll_ResponseListEmpty() throws Exception  {
			
		Mockito.when(artistQueryService.getAll()).thenReturn(artistList);
		
		mockMvc.perform(MockMvcRequestBuilders.get(PATH))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.notNullValue()))
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.empty()))
		;		
	}

	@Test
	void ArtistQueryController_getById_ResponseArtistDto() throws Exception  {
		
		Mockito.when(artistQueryService.getById(Mockito.anyLong())).thenReturn(Optional.ofNullable(artistTest));
		Mockito.when(artistMapper.fromDomainToDto(Mockito.any(Artist.class))).thenReturn(artistDtoTest);
		
		mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/{id}", id))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.notNullValue()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artistId", org.hamcrest.Matchers.equalTo(id.intValue())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artistName", org.hamcrest.Matchers.equalTo(name)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artistSurname", org.hamcrest.Matchers.equalTo(surname)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artisticName", org.hamcrest.Matchers.equalTo(artisticName)))
		;		
	}

}
