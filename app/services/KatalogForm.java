package services;

import models.ResearchData;
import play.data.Form;
import play.twirl.api.Content;

public interface KatalogForm {

	String getId();

	ResearchData getModel();

	Content render(Form<?> form);

}