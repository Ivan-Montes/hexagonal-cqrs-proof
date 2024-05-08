package dev.ime.domain.model;

import java.util.Arrays;

public enum MediaClass {
	
	OTHER(1, "Other Media Class"),
	SONG(2,"Audio only"),
	ALBUM(3, "Album"),
	LIVE(4, "Live performance"),
	VIDEOCLIP(5,"Video + Music");
	
	private MediaClass(Integer id, String description) {
		this.id = id;
		this.description = description;
	}	

	public Integer getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}

	private Integer id;
	private String description;	
	
	public static MediaClass findByName(String name) {
		
		return Arrays.asList(MediaClass.values())
				.stream()
				.filter( m -> m.name().equalsIgnoreCase(name))
				.findFirst()
				.orElse(OTHER);
	}
	
}
