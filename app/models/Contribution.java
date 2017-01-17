package models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import play.data.validation.ValidationError;
import services.ZettelModel;

/**
 * @author Jan Schnasse
 *
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Contribution extends ZettelModel {

	@Override
	protected String getType() {
		// TODO Auto-generated method stub
		return "http://bibframe.org/vocab/Contribution";
	}

	@Override
	protected List<ValidationError> validate() {
		// TODO Auto-generated method stub
		return null;
	}

}
