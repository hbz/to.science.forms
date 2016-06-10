/*Copyright (c) 2016 "hbz"

This file is part of zettel.

etikett is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import services.FormRegister;
import services.KatalogForm;
import views.html.*;

/**
 * @author Jan Schnasse
 *
 */
public class KatalogController extends Controller {
	@Inject
	play.data.FormFactory formFactory;

	private static final FormRegister katalogForms = new FormRegister();

	public CompletionStage<Result> index() {
		CompletableFuture<Result> future = new CompletableFuture<>();
		future.complete(ok(index.render("Katalog")));
		return future;
	}

	public CompletionStage<Result> getForms(String id) {
		CompletableFuture<Result> future = new CompletableFuture<>();
		Result result = null;
		if (id == null)
			result = listForms();
		else {
			KatalogForm myKatalogForm = katalogForms.get(id);
			result = renderForm(myKatalogForm);
		}
		future.complete(result);
		return future;
	}

	private Result listForms() {
		List<String> formList = katalogForms.getIds();
		return ok(forms.render(formList));
	}

	private Result renderForm(KatalogForm katalogForm) {
		Form<?> form = formFactory.form(katalogForm.getModel().getClass());
		if (form.hasErrors()) {
			play.Logger.debug(form.globalErrors() + "");
			play.Logger.debug(form.errors() + "");
			return badRequest(katalogForm.render(form));
		}
		return ok(katalogForm.render(form));
	}

	public CompletionStage<Result> getRdf(String id) {
		play.Logger.debug(
				"\n" + request().toString() + "\n\t" + request().body().toString());
		CompletableFuture<Result> future = new CompletableFuture<>();
		Result result = null;
		KatalogForm myKatalogForm = katalogForms.get(id);
		result = convertKatalogForm(myKatalogForm);
		future.complete(result);
		return future;
	}

	private Result convertKatalogForm(KatalogForm katalogForm) {
		Result result = null;
		Form<?> form =
				formFactory.form(katalogForm.getModel().getClass()).bindFromRequest();
		if (form.hasErrors()) {
			play.Logger.debug("POST " + katalogForm.getId() + " form has errors.");
			play.Logger.debug(form.globalErrors() + "");
			play.Logger.debug(form.errors() + "");
			result = badRequest(katalogForm.render(form));
		} else {
			response().setHeader("Content-Type", "application/json");
			result = ok(form.get().toString());
		}
		return result;
	}
}
