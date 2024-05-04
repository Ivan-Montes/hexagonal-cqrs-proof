package dev.ime.application.exception;

import java.util.Map;
import java.util.UUID;

import dev.ime.application.config.ApplicationConstant;

public class EntityAssociatedException  extends BasicException{	

	private static final long serialVersionUID = -4905083516375255960L;

	public EntityAssociatedException( Map<String, String> errors ) {
		super(
				UUID.randomUUID(), 
				ApplicationConstant.EX_ENTITY_ASSOCIATED, 
				ApplicationConstant.EX_ENTITY_ASSOCIATED_DESC,
				errors
				);
	}	
	
}
