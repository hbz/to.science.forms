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
import services.ZettelRegister;
import services.ZettelRegisterEntry;
import views.html.*;

/**
 * @author Jan Schnasse
 *
 */
public class ZettelController extends Controller {
	@Inject
	play.data.FormFactory formFactory;

	private static final ZettelRegister zettelRegister = new ZettelRegister();

	public CompletionStage<Result> index() {
		CompletableFuture<Result> future = new CompletableFuture<>();
		future.complete(ok(index.render("Zettel")));
		return future;
	}

	public CompletionStage<Result> getForms(String id) {
		CompletableFuture<Result> future = new CompletableFuture<>();
		Result result = null;
		if (id == null)
			result = listForms();
		else {
			ZettelRegisterEntry zettel = zettelRegister.get(id);
			result = renderForm(zettel);
		}
		future.complete(result);
		return future;
	}

	private Result listForms() {
		List<String> formList = zettelRegister.getIds();
		return ok(forms.render(formList));
	}

	private Result renderForm(ZettelRegisterEntry zettel) {
		Form<?> form = formFactory.form(zettel.getModel().getClass());
		if (form.hasErrors()) {
			play.Logger.debug(form.globalErrors() + "");
			play.Logger.debug(form.errors() + "");
			return badRequest(zettel.render(form));
		}
		return ok(zettel.render(form));
	}

	public CompletionStage<Result> getRdf(String id) {
		play.Logger.debug(
				"\n" + request().toString() + "\n\t" + request().body().toString());
		CompletableFuture<Result> future = new CompletableFuture<>();
		Result result = null;
		ZettelRegisterEntry myZettel = zettelRegister.get(id);
		result = convert(myZettel);
		future.complete(result);
		return future;
	}

	private Result convert(ZettelRegisterEntry zettel) {
		Result result = null;
		Form<?> form =
				formFactory.form(zettel.getModel().getClass()).bindFromRequest();
		if (form.hasErrors()) {
			play.Logger.debug("POST " + zettel.getId() + " form has errors.");
			play.Logger.debug(form.globalErrors() + "");
			play.Logger.debug(form.errors() + "");
			result = badRequest(zettel.render(form));
		} else {
			response().setHeader("Content-Type", "application/json");
			result = ok(form.get().toString());
		}
		return result;
	}
}
