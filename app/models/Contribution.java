package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.data.validation.ValidationError;
import services.ZettelHelper;
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

	@Override
	public Map<String, Object> serializeToMap() {
		Map<String, Object> jsonMap =
				new ObjectMapper().convertValue(this, HashMap.class);
		jsonMap.put("@context", ZettelHelper.etikett.getContext().get("@context"));
		return jsonMap;
	}
}
