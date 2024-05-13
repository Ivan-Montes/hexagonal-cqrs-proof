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

import dev.ime.config.MediaMapper;
import dev.ime.domain.model.Genre;
import dev.ime.domain.model.Media;
import dev.ime.domain.model.MediaClass;
import dev.ime.domain.port.inbound.MediaQueryServicePort;
import dev.ime.infrastructure.dto.MediaDto;

@WebMvcTest(MediaQueryController.class)
@AutoConfigureMockMvc(addFilters = false)
class MediaQueryControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MediaQueryServicePort mediaQueryServicePort;

	@MockBean
	private MediaMapper mediaMapper;	

	@MockBean
	private Logger logger;

	private final String PATH = "/api/medias";
	private List<Media> mediaList;
	private List<MediaDto> mediaDtoList;
	private Media mediaTest;
	private final Long id = 9L;
	private final String name = "Always";
	private final Genre genre = Genre.ROCK;
	private final MediaClass mediaClass = MediaClass.LIVE;
	private final Long artistId = 18L;	
	private MediaDto mediaDtoTest;
	
	@BeforeEach
	private void createObjects() {
		
		mediaTest = new Media.MediaBuilder()
				.setId(id)
				.setName(name)
				.setGenre(genre)
				.setMediaClass(mediaClass)
				.setArtistId(artistId)
				.build();
		
		mediaDtoTest = new MediaDto(id, name, genre.name(),mediaClass.name(),artistId);
		
		mediaList = new ArrayList<>();	
		mediaDtoList = new ArrayList<>();
	}
	
	@Test
	void MediaQueryController_getAll_ReturnResponseEntityListMediaDto() throws Exception {

		mediaList.add(mediaTest);
		mediaDtoList.add(mediaDtoTest);
		Mockito.when(mediaQueryServicePort.getAll()).thenReturn(mediaList);
		Mockito.when(mediaMapper.fromListDomainToListDto(Mockito.anyList())).thenReturn(mediaDtoList);
		
		mockMvc.perform(MockMvcRequestBuilders.get(PATH))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.notNullValue()))
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
		;
	}

	@Test
	void MediaQueryController_getAll_ReturnResponseEntityListEmtpy() throws Exception {
		
		Mockito.when(mediaQueryServicePort.getAll()).thenReturn(mediaList);
		
		mockMvc.perform(MockMvcRequestBuilders.get(PATH))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.notNullValue()))
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.empty()))
		;
	}

	@Test
	void MediaQueryController_getById_ReturnResponseEntityMediaDto() throws Exception {
		
		Mockito.when(mediaQueryServicePort.getById(Mockito.anyLong())).thenReturn(Optional.of(mediaTest));
		Mockito.when(mediaMapper.fromDomainToDto(Mockito.any(Media.class))).thenReturn(mediaDtoTest);

		mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/{id}", id))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.notNullValue()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.mediaId", org.hamcrest.Matchers.equalTo(9)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.mediaName", org.hamcrest.Matchers.equalTo(name)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.mediaGenre", org.hamcrest.Matchers.equalTo(genre.name())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.mediaClass", org.hamcrest.Matchers.equalTo(mediaClass.name())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.artistId", org.hamcrest.Matchers.equalTo(18)))
		;
	}

}
