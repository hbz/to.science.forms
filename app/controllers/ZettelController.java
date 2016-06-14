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

	/**
	 * @return the start page
	 */
	public CompletionStage<Result> index() {
		CompletableFuture<Result> future = new CompletableFuture<>();
		future.complete(ok(index.render("Zettel")));
		return future;
	}

	/**
	 * @param id if null list all available forms otherwise render the requested
	 *          form.
	 * @return a list of available forms or if present the form with a certain id.
	 * 
	 */
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

	@SuppressWarnings("static-method")
	private Result listForms() {
		List<String> formList = zettelRegister.getIds();
		return ok(forms.render(formList));
	}

	private Result renderForm(ZettelRegisterEntry zettel) {
		Form<?> form = formFactory.form(zettel.getModel().getClass());
		return ok(zettel.render(form));
	}

	/**
	 * @param id the id of the form the POST data is send to.
	 * @return if posted with accept:application/json return json-ld
	 *         representation of the data. In all other cases return an html
	 *         formular.
	 */
	public CompletionStage<Result> postForm(String id) {
		play.Logger.debug("\n" + request().toString() + "\n\t" + request().body());
		CompletableFuture<Result> future = new CompletableFuture<>();
		Result result = null;
		ZettelRegisterEntry zettel = zettelRegister.get(id);
		Form<?> form =
				formFactory.form(zettel.getModel().getClass()).bindFromRequest();
		play.Logger.debug(form.data() + "");
		if (form.hasErrors()) {
			if (request().accepts("text/html")) {
				result = badRequest(zettel.render(form));
			} else {
				result = badRequest(form.errorsAsJson()).as("application/json");
			}
		} else {
			if (request().accepts("text/html")) {
				result = ok(zettel.render(form));
				play.Logger.debug(form.get().toString());
			} else {
				result = ok(form.get().toString()).as("application/json");
			}
		}
		future.complete(result);
		return future;
	}

}
