package services;

import models.ResearchData;
import play.data.Form;
import play.twirl.api.Content;
import views.html.researchData;

public class ResearchDataKatalogForm implements KatalogForm {

	final String id = "researchData";
	ResearchData model = new ResearchData();

	@Override
	public String getId() {
		return id;
	}

	@Override
	public ResearchData getModel() {
		return model;
	}

	@Override
	public Content render(Form<?> form) {
		return researchData.render((Form<ResearchData>) form);
	}

}
