package services;

import play.data.Form;
import play.twirl.api.Content;

public interface KatalogForm {

	String getId();

	Object getModel();

	Content render(Form<?> form);

}