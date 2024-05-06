package dev.ime.infrastructure.config;

public class InfrastructureConstant {
	
	private InfrastructureConstant() {
		super();
	}
	
	public static final String NODATA = "No data available";	
	public static final String EX_METHOD_ARGUMENT_INVALID = "MethodArgumentNotValidException";
	public static final String EX_METHOD_ARGUMENT_INVALID_DESC = "MethodArgument Not Valid Exception";
	public static final String EX_METHOD_ARGUMENT_TYPE = "MethodArgumentTypeMismatchException";
	public static final String EX_METHOD_ARGUMENT_TYPE_DESC = "Method Argument Type Mismatch Exception";
	public static final String EX_JAKARTA_VAL = "ConstraintViolationException";
	public static final String EX_JAKARTA_VAL_DESC = "Constraint Violation Exception";	
	public static final String EX_NO_RESOURCE = "NoResourceFoundException";
	public static final String EX_NO_RESOURCE_DESC = "No Resource Found Exception";
	public static final String EX_EX = "Exception";
	public static final String EX_EX_DESC = "Exception because the night is dark and full of terrors";

	public static final String ARTIST_CREATED = "artist.created";
	public static final String ARTIST_UPDATED = "artist.updated";
	public static final String ARTIST_DELETED = "artist.deleted";
	public static final String MEDIA_CREATED = "media.created";
	public static final String MEDIA_UPDATED = "media.updated";
	public static final String MEDIA_DELETED = "media.deleted";
	
}
