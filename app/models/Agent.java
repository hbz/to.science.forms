package models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import play.data.validation.ValidationError;
import services.ZettelModel;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Agent extends ZettelModel {

	@Override
	protected String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<ValidationError> validate() {
		// TODO Auto-generated method stub
		return null;
	}

}
