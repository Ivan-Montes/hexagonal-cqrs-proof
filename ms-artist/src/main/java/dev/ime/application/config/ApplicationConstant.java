package dev.ime.application.config;

public class ApplicationConstant {	
	
	private ApplicationConstant() {
		super();
	}
	
	public static final String ARTISTID = "ArtistId";
	public static final String MEDIAID = "MediaId";

	public static final String EX_RESOURCE_NOT_FOUND = "ResourceNotFoundException";	
	public static final String EX_RESOURCE_NOT_FOUND_DESC = "Exception is coming, the resource has not been found.";	
	public static final String EX_ENTITY_ASSOCIATED = "EntityAssociatedException";	
	public static final String EX_ENTITY_ASSOCIATED_DESC = "Hear me roar, some entity is still associated in the element";	
	public static final String EX_ILLEGAL_ARGUMENT = "IllegalArgumentException";
	public static final String EX_ILLEGAL_ARGUMENT_DES = "Some argument is not supported";

	public static final String MSG_ILLEGAL_COMMAND = "Command not supported";
	public static final String MSG_ILLEGAL_QUERY = "Query not supported";
	
}
