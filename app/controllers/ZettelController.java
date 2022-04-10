/*Copyright (c) 2016 "hbz"

This file is part of zettel.

zettel is free software: you can redistribute it and/or modify
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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import org.eclipse.rdf4j.rio.RDFFormat;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import models.Article;
import models.ResearchData;
import models.ZettelModel;
import play.Configuration;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.mvc.Controller;
import play.mvc.Result;
import services.MyURLEncoding;
import services.RdfUtils;
import services.XmlUtils;
import services.ZettelHelper;
import services.ZettelRegister;
import services.ZettelRegisterEntry;
import views.html.client;
import views.html.forms;
import views.html.index;

/**
 * @author Jan Schnasse
 *
 */
public class ZettelController extends Controller {
	@Inject
	play.data.FormFactory formFactory;

	@Inject
	WSClient ws;
	@Inject
	Configuration configuration;

	/**
	 * @return the start page
	 */
	public CompletionStage<Result> index() {
		CompletableFuture<Result> future = new CompletableFuture<>();
		future.complete(ok(index.render("Zettel")));
		return future;
	}

	/**
	 * @param all a path
	 * @return a header for all routes when ask with HTTP OPTIONS
	 */
	public CompletionStage<Result> corsforall(String all) {
		CompletableFuture<Result> future = new CompletableFuture<>();
		response().setHeader("Access-Control-Allow-Origin", "*");
		response().setHeader("Allow", "*");
		response().setHeader("Access-Control-Allow-Methods",
				"POST, GET, PUT, DELETE, OPTIONS");
		response().setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");
		future.complete(ok());
		return future;
	}

	/**
	 * @param id if null list all available forms otherwise render the requested
	 *          form.
	 * @param format ask for certain format. supports xml,ntriples and json
	 * @param documentId your personal id for the document you want to create form
	 *          data for
	 * @param topicId the topic id is used by our regal-drupal to find the actual
	 *          documentId. You can probably ignore this.
	 * @return a list of available forms or if present the form with a certain id.
	 * 
	 */
	public CompletionStage<Result> getForms(String id, String format,
			String documentId, String topicId) {
		setHeaders();
		CompletableFuture<Result> future = new CompletableFuture<>();
		Result result = null;
		if (id == null)
			result = listForms();
		else {
			if (id.equals("katalog:monograph") || id.equals("katalog:journal")
					|| id.equals("katalog:webpage")) {
				id = "katalog:catalog";
			}
			ZettelRegister zettelRegister = new ZettelRegister();
			ZettelRegisterEntry zettel = zettelRegister.get(id);
			result = renderForm(zettel, format, documentId, topicId);
		}
		future.complete(result);
		return future;
	}

	/**
	 * @param id the id of the form the POST data is send to.
	 * @param format ask for certain format. supports xml,ntriples and json
	 * @param documentId your personal id for the document you want to create form
	 *          data for
	 * @param topicId the topic id is used by our regal-drupal to find the actual
	 *          documentId. You can probably ignore this.
	 * @return if posted with accept:application/json return json-ld
	 *         representation of the data. In all other cases return an html
	 *         formular.
	 */
	public CompletionStage<Result> postForm(String id, String format,
			String documentId, String topicId) {
		setHeaders();
		Result result = null;

		// play.Logger.debug("Content of request-------------\n"
		// + ZettelHelper.objectToString(request().body().asText()));
		// play.Logger.debug("Content of model-------------\n"
		// + ZettelHelper.objectToString(zettel.getModel()));
		// play.Logger.debug(String.format("Content of request\n%s\n%s", request(),
		// ZettelHelper.objectToString(request().body().asFormUrlEncoded())));

		ZettelRegister zettelRegister = new ZettelRegister();
		CompletableFuture<Result> future = new CompletableFuture<>();
		ZettelRegisterEntry zettel = zettelRegister.get(id);
		play.Logger.debug("Post data to " + zettel.getId());
		Form<?> form = bindToForm(zettel, documentId, topicId);
		result = renderForm(format, documentId, topicId, zettel, form);
		future.complete(result);
		form = null;
		return future;
	}

	private static String printRdf(Form<?> form) {
		try (InputStream in = new ByteArrayInputStream(
				((ZettelModel) form.get()).toString().getBytes("utf-8"))) {
			String rdfString =
					RdfUtils.readRdfToString(in, RDFFormat.JSONLD, RDFFormat.RDFXML, "");
			return rdfString;
		} catch (Exception e) {
			play.Logger.debug("", e);
			return "";
		}
	}

	/**
	 * @param format ask for certain format. supports xml,ntriples and json
	 * @param id of the formular type
	 * @param documentId your personal id for the document you want to create form
	 *          data for
	 * @param topicId the topic id is used by our regal-drupal to find the actual
	 *          documentId. You can probably ignore this.
	 * @return a client demo
	 */
	public CompletionStage<Result> client(String format, String id,
			String documentId, String topicId) {
		CompletableFuture<Result> future = new CompletableFuture<>();
		future
				.complete(ok(client.render("Zettel", format, id, documentId, topicId)));
		return future;
	}

	private static Result renderForm(String format, String documentId,
			String topicId, ZettelRegisterEntry zettel, Form<?> form) {
		Result result;
		if (form.hasErrors()) {
			if (request().accepts("text/html")) {
				result = badRequest(zettel.render(form, format, documentId, topicId));
			} else {
				result = badRequest(form.errorsAsJson()).as("application/json");
			}
		} else {
			if (request().accepts("text/html")) {
				result = ok(zettel.render(form, format, documentId, topicId));
			} else {
				result = ok(form.get().toString()).as("application/json");
			}
			// play.Logger.debug(String.format("Content of model\n%s",
			// ((ZettelModel) form.get()).print()));
		}
		return result;
	}

	/**
	 * binds a rdf datastream to the appropriate form (via play.data.formFactory).
	 * rdf datastream is acquired either from existing rdf file (loadRdf) or from http request
	 * (bindFromRequest).
	 * Due to form validation bindFromRequest is needed to re-render form with all form data applied from form user    
	 * @param zettel
	 * @param documentId
	 * @param topicId
	 * @return
	 */
	private Form<?> bindToForm(ZettelRegisterEntry zettel, String documentId,
			String topicId) {
		Form<?> form = null;
		if ("application/rdf+xml".equals(request().contentType().get())) {
			play.Logger.debug("Load form from rdf");
			form = loadRdf(XmlUtils.docToString(request().body().asXml()), zettel,
					documentId, topicId);
			form.bindFromRequest();
		} else if ("application/x-www-form-urlencoded"
				.equals(request().contentType().get())) {
			play.Logger.debug("Load form from request " + zettel.getModel().getId()
					+ " ," + zettel.getModel().getClass());
			form = formFactory.form(zettel.getModel().getClass()).bindFromRequest();
		} else {
			play.Logger
					.error("WARN: Can not handle " + request().contentType().get());
		}
		// play.Logger.debug(String
		// .format("Content of model directly after bindToForm\n%s", form.get()));
		play.Logger
				.debug(String.format("Content of rdf result\n%s", printRdf(form)));
		return form;
	}

	/**
	 * loads and deserialize rdf-Datastream from file. Puts values from rdf-Datastream into applicable form.
	 * CAVEAT: Method in current form is only applicable for article or researchData form.  
	 * @param asText
	 * @param zettel
	 * @param documentId
	 * @param topicId
	 * @return
	 */
	// TODO: replace if construct with an more generic way to find the correct form
	private Form<?> loadRdf(String asText, ZettelRegisterEntry zettel,
			String documentId, String topicId) {
		try (InputStream in = new ByteArrayInputStream(asText.getBytes("utf-8"))) {
			String id = zettel.getId();
			if (ResearchData.id.equals(id)) {
				return formFactory.form(ResearchData.class)
						.fill((ResearchData) zettel.getModel().deserializeFromRdf(in,
								RDFFormat.RDFXML, documentId, topicId));
			} else if (Article.id.equals(id)) {
				return formFactory.form(Article.class).fill((Article) zettel.getModel()
						.deserializeFromRdf(in, RDFFormat.RDFXML, documentId, topicId));
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void setHeaders() {
		response().setHeader("Access-Control-Allow-Origin", "*");
		response().setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept");
	}

	private static Result listForms() {
		ZettelRegister zettelRegister = new ZettelRegister();
		List<String> formList = zettelRegister.getIds();
		return ok(forms.render(formList));
	}

	private Result renderForm(ZettelRegisterEntry zettel, String format,
			String documentId, String topicId) {
		Form<?> form = formFactory.form(zettel.getModel().getClass());
		return ok(zettel.render(form, format, documentId, topicId));
	}

	/**
	 * @param q the query will be redirected to geonames
	 * @return the response from api.geonames.org
	 */
	public CompletionStage<Result> geoSearch(String q) {
		String geoNamesUrl = "http://api.geonames.org/searchJSON";
		WSRequest request = ws.url(geoNamesUrl);
		WSRequest complexRequest =
				request.setRequestTimeout(1000).setQueryParameter("q", q)
						.setQueryParameter("username", "epublishinghbz");
		return complexRequest.setFollowRedirects(true).get()
				.thenApply(response -> ok(response.asJson()));
	}

	/**
	 * @param q a query string to find an orcid entry
	 * @return the orcid response as json
	 */
	public CompletionStage<Result> orcidSearch(String q) {
		String orcidUrl = "https://pub.orcid.org/v2.1/search";
		WSRequest request = ws.url(orcidUrl);
		WSRequest complexRequest =
				request.setRequestTimeout(1000).setQueryParameter("q", q);
		return complexRequest.setFollowRedirects(true).get()
				.thenApply(response -> ok(response.asJson()));
	}

	/**
	 * @param q a query string to get suggestion from orcid
	 * @return a list of results from orcid wrapped in a jsonp array
	 */
	public CompletionStage<Result> orcidAutocomplete(String q) {
		final String[] callback =
				request() == null || request().queryString() == null ? null
						: request().queryString().get("callback");
		String orcidUrl = "https://pub.orcid.org/v3.0/expanded-search";
		WSRequest request = ws.url(orcidUrl);
		WSRequest complexRequest = request.setHeader("accept", "application/json")
				.setRequestTimeout(5000).setQueryParameter("q", q);
		return complexRequest.setFollowRedirects(true).get().thenApply(response -> {
			JsonNode hits = response.asJson().at("/expanded-result");
			List<Map<String, String>> result = new ArrayList<>();
			hits.forEach((hit) -> {

				String id = hit.at("/orcid-id").asText();
				String lastName = hit.at("/family-names").asText();
				String firstName = hit.at("/given-names").asText();
				Map<String, String> m = new HashMap<>();
				m.put("label", lastName + ", " + firstName + " | " + id);
				m.put("value", "https://orcid.org/" + id);
				result.add(m);
			});
			String searchResult = ZettelHelper.objectToString(result);
			String myResponse = callback != null
					? String.format("/**/%s(%s)", callback[0], searchResult)
					: searchResult;
			return ok(myResponse);
		});
	}

	public CompletionStage<Result> bookAutocomplete(String q) {
		return typedAutocomplete(q, "Book");
	}

	public CompletionStage<Result> seriesAutocomplete(String q) {
		return typedAutocomplete(q, "Series");
	}

	public CompletionStage<Result> journalAutocomplete(String q) {
		return typedAutocomplete(q, "Periodical");
	}

	public CompletionStage<Result> allAutocomplete(String q) {
		return typedAutocomplete(q, "*");
	}

	/**
	 * @param q a query against lobid
	 * @return a jsonp result
	 */
	public CompletionStage<Result> typedAutocomplete(String q, String type) {
		final String[] callback =
				request() == null || request().queryString() == null ? null
						: request().queryString().get("callback");
		String lobidUrl = "http://lobid.org/resources/search";
		WSRequest request = ws.url(lobidUrl);
		WSRequest complexRequest =
				request.setHeader("accept", "application/json").setRequestTimeout(5000)
						.setQueryParameter("q", q + " AND type:" + type);
		return complexRequest.setFollowRedirects(true).get().thenApply(response -> {
			JsonNode hits = response.asJson().at("/member");
			List<Map<String, String>> result = new ArrayList<>();
			hits.forEach((hit) -> {
				String title = hit.at("/title").asText();
				StringBuilder label = new StringBuilder(title);
				label.append(createLabel(hit));
				String id = hit.at("/id").asText();
				Map<String, String> m = new HashMap<>();
				m.put("label", label.toString());
				m.put("value", id);
				result.add(m);
			});
			String searchResult = ZettelHelper.objectToString(result);
			String myResponse = callback != null
					? String.format("/**/%s(%s)", callback[0], searchResult)
					: searchResult;
			return ok(myResponse);
		});
	}

	private String createLabel(JsonNode hit) {
		StringBuilder label = new StringBuilder();
		String issn = "";

		JsonNode issns = hit.at("/issn");
		if (issns != null) {
			StringBuffer issn_c = new StringBuffer();
			issns.forEach((issn_i) -> {
				issn_c.append(issn_i.asText() + ",");
			});
			if (issn_c.length() != 0) {
				issn = issn_c.substring(0, issn_c.length() - 1);
				if (issn != null && !issn.isEmpty())
					label.insert(0, issn + " - ");
			}
		}
		JsonNode publisher = hit.at("/publication");
		if (publisher != null) {
			label.append(" - Hrsg.: " + publisher.get(0).at("/publishedBy").asText());
		}

		return label.toString();
	}

	public CompletionStage<Result> localAutocomplete(String q) {
		CompletableFuture<Result> future = new CompletableFuture<>();

		final String[] callback =
				request() == null || request().queryString() == null ? null
						: request().queryString().get("callback");
		List<Map<String, String>> result = new ArrayList<>();
		Map<String, String> suggestThisAsNewEntry = new HashMap<>();
		suggestThisAsNewEntry.put("label", q);
		suggestThisAsNewEntry.put("value", configuration.getString("regalApi")
				+ "/adhoc/uri/" + MyURLEncoding.encode(q));
		result.add(suggestThisAsNewEntry);

		String searchResult = ZettelHelper.objectToString(result);
		String myResponse = callback != null
				? String.format("/**/%s(%s)", callback[0], searchResult)
				: searchResult;

		future.complete(ok(myResponse));
		return future;
	}

	/**
	 * @param q a query against lobid
	 * @return a jsonp result
	 */
	public CompletionStage<Result> subjectAutocomplete(String q) {
		String filter = "type:SubjectHeadingSensoStricto";
		return lobidResponse(q, filter);
	}

	/**
	 * @param q a query against lobid
	 * @return a jsonp result
	 */
	public CompletionStage<Result> personAutocomplete(String q) {
		String filter = "type:Person";
		return lobidResponse(q, filter);
	}

	/**
	 * @param q a query against lobid
	 * @return a jsonp result
	 */
	public CompletionStage<Result> corporateBodyAutocomplete(String q) {
		String filter = "type:CorporateBody";
		return lobidResponse(q, filter);
	}

	/**
	 * @param q a query against lobid
	 * @return a jsonp result
	 */
	public CompletionStage<Result> conferenceAutocomplete(String q) {
		String filter =
				"(type:ConferenceOrEvent)OR(type:SeriesOfConferenceOrEvent)";
		return lobidResponse(q, filter);
	}

	private CompletionStage<Result> lobidResponse(String q, String filter) {
		final String[] callback =
				request() == null || request().queryString() == null ? null
						: request().queryString().get("callback");
		String lobidUrl = "https://lobid.org/gnd/search";
		WSRequest request = ws.url(lobidUrl);
		WSRequest complexRequest = request.setQueryParameter("q", q)
				.setQueryParameter("format", "json:suggest")
				.setQueryParameter("filter", filter)
				.setHeader("accept", "application/json").setRequestTimeout(5000);
		play.Logger.trace(
				"GET " + complexRequest.getUrl() + complexRequest.getQueryParameters());
		return complexRequest.setFollowRedirects(true).get().thenApply(response -> {
			JsonNode root = response.asJson();
			List<Map<String, String>> result = new ArrayList<>();
			root.forEach((m) -> {
				StringBuffer label = new StringBuffer();
				label.append(m.at("/label"));
				String id = m.at("/id").asText();
				Map<String, String> map = new HashMap<>();
				label.append(" | " + getGndNumber(id));
				map.put("label", label.toString());
				map.put("value", id);
				result.add(map);
			});
			String searchResult = ZettelHelper.objectToString(result);
			String myResponse = callback != null
					? String.format("/**/%s(%s)", callback[0], searchResult)
					: searchResult;
			return ok(myResponse);
		});
	}

	private static String getGndNumber(String id) {
		try {
			return Iterables.getLast(Splitter.on("/").split(id));
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	/**
	 * @param q a query against lobid
	 * @return a jsonp result
	 */
	public CompletionStage<Result> lobidAutocomplete(String q) {
		final String[] callback =
				request() == null || request().queryString() == null ? null
						: request().queryString().get("callback");
		String lobidUrl = "https://lobid.org/resources/search";
		WSRequest request = ws.url(lobidUrl);
		String queryString = q;
		WSRequest complexRequest = request.setQueryParameter("q", queryString)
				.setQueryParameter("format", "json").setRequestTimeout(5000);
		return complexRequest.setFollowRedirects(true).get().thenApply(response -> {
			JsonNode root = response.asJson();
			List<Map<String, String>> result = new ArrayList<>();
			JsonNode member = root.at("/member");
			member.forEach((m) -> {
				StringBuffer label = new StringBuffer();
				label.append(m.at("/hbzId").asText());
				label.append(" - ");
				JsonNode prefName = m.at("/title");
				if (prefName.isArray()) {
					prefName.forEach((p) -> {
						label.append(p.asText() + ",");
					});
					label.deleteCharAt(label.length() - 1);
				} else {
					label.append(prefName.asText());
				}

				String id = m.at("/id").asText().replaceAll("#!", "");
				Map<String, String> map = new HashMap<>();
				map.put("label", label.toString());
				map.put("value", id);
				result.add(map);
			});
			String searchResult = ZettelHelper.objectToString(result);
			String myResponse = callback != null
					? String.format("/**/%s(%s)", callback[0], searchResult)
					: searchResult;
			return ok(myResponse);
		});
	}

	/**
	 * @param q a query against lobid
	 * @return a jsonp result
	 */
	public CompletionStage<Result> crossrefAutocomplete(String q) {
		final String[] callback =
				request() == null || request().queryString() == null ? null
						: request().queryString().get("callback");
		String crossrefUrl = configuration.getString("crossrefFundersApiUrl");
		int crossrefTimeout = configuration.getInt("crossrefFundersApiTimeout");
		String crossrefUserAgent = configuration.getString("crossrefFundersApiUserAgent");
		WSRequest request = ws.url(crossrefUrl);
		String queryString = q;
		WSRequest complexRequest =
				request.setQueryParameter("query", queryString).setRequestTimeout(crossrefTimeout).setHeader("User-Agent", crossrefUserAgent);
		return complexRequest.setFollowRedirects(true).get().thenApply(response -> {
			JsonNode root = response.asJson();
			List<Map<String, String>> result = new ArrayList<>();
			JsonNode member = root.at("/message/items");
			member.forEach((m) -> {
				StringBuffer label = new StringBuffer();
				label.append(m.at("/name").asText());
				StringBuffer altNamesString = new StringBuffer();

				JsonNode altNames = m.at("/alt-names");
				if (altNames.isArray()) {
					altNames.forEach((p) -> {
						altNamesString.append(p.asText() + ",");
					});
				} else {
					altNamesString.append(altNames.asText());
				}

				String id = m.at("/uri").asText().replaceAll("#!", "");
				Map<String, String> map = new HashMap<>();
				map.put("label", label.toString());
				map.put("value", id);
				map.put("desc", altNamesString.toString());
				result.add(map);
			});
			String searchResult = ZettelHelper.objectToString(result);
			String myResponse = callback != null
					? String.format("/**/%s(%s)", callback[0], searchResult)
					: searchResult;
			return ok(myResponse);
		});
	}

	public CompletionStage<Result> conferenceAlephAutocomplete(String q) {
		final String[] callback =
				request() == null || request().queryString() == null ? null
						: request().queryString().get("callback");
		String lobidUrl = "https://lobid.org/resources/search";
		WSRequest request = ws.url(lobidUrl);
		String queryString = q;
		WSRequest complexRequest = request.setQueryParameter("q", queryString)
				.setQueryParameter("format", "json")
				.setQueryParameter("t", "Proceedings").setRequestTimeout(5000);
		return complexRequest.setFollowRedirects(true).get().thenApply(response -> {
			JsonNode root = response.asJson();
			List<Map<String, String>> result = new ArrayList<>();
			JsonNode member = root.at("/member");
			member.forEach((m) -> {
				StringBuffer label = new StringBuffer();
				label.append(m.at("/hbzId").asText());
				label.append(" - ");
				JsonNode prefName = m.at("/title");
				if (prefName.isArray()) {
					prefName.forEach((p) -> {
						label.append(p.asText() + ",");
					});
					label.deleteCharAt(label.length() - 1);
				} else {
					label.append(prefName.asText());
				}

				String id = m.at("/id").asText().replaceAll("#!", "");
				Map<String, String> map = new HashMap<>();
				map.put("label", label.toString());
				map.put("value", id);
				result.add(map);
			});
			String searchResult = ZettelHelper.objectToString(result);
			String myResponse = callback != null
					? String.format("/**/%s(%s)", callback[0], searchResult)
					: searchResult;
			return ok(myResponse);
		});
	}
}
