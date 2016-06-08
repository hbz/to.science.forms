package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import models.*;

/**
 * @author Jan Schnasse
 *
 */
public class KatalogController extends Controller {
	@Inject
	play.data.FormFactory formFactory;

	private static final String RESEARCH_DATA = "researchData";

	public CompletionStage<Result> index() {
		CompletableFuture<Result> future = new CompletableFuture<>();
		future.complete(ok(index.render("Katalog")));
		return future;
	}

	public CompletionStage<Result> getForms(String id) {
		return getForm(id);
	}

	public CompletionStage<Result> getForm(String id) {
		CompletableFuture<Result> future = new CompletableFuture<>();
		Result result = null;
		if (id == null)
			result = listForms();
		else {
			switch (id) {
			case RESEARCH_DATA:
				result = getResearchDataForm();
				break;
			default:
				result = listForms();
			}
		}
		future.complete(result);
		return future;
	}

	private Result listForms() {
		List<String> formList = new ArrayList();
		formList.add("/forms?id=" + RESEARCH_DATA);
		return ok(forms.render(formList));
	}

	private Result getResearchDataForm() {
		Form<ResearchData> form = formFactory.form(ResearchData.class);
		if (form.hasErrors()) {
			return badRequest(researchData.render(form));
		} else {
			return ok(researchData.render(form));
		}
	}

	public CompletionStage<Result> getRdf(String id) {
		play.Logger.debug("POST " + id);
		Result result = null;
		CompletableFuture<Result> future = new CompletableFuture<>();
		Form<ResearchData> form =
				formFactory.form(ResearchData.class).bindFromRequest();
		if (form.hasErrors()) {
			result = badRequest(researchData.render(form));
		} else {
			ResearchData u = form.get();
			result = redirect(routes.KatalogController.index());
		}

		future.complete(result);
		return future;
	}
}
