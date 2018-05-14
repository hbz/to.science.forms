
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.inject.Inject;

import org.eclipse.rdf4j.rio.RDFFormat;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import akka.stream.Materializer;
import akka.util.ByteString;
import models.Article;
import models.ZettelModel;
import play.core.j.JavaResultExtractor;
import play.data.Form;
import play.http.HttpEntity;
import play.mvc.Http.RequestBuilder;
import play.test.Helpers;
import play.mvc.Result;
import services.RdfUtils;

public class TestLargeRequest {

	@Test
	public void testLargeFile() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT,
				browser -> {
					try {
						ObjectMapper mapper = new ObjectMapper();
						String rdfString = RdfUtils.readRdfToString(
								play.Environment.simple().resourceAsStream("big-rdf-file.rdf"),
								RDFFormat.RDFXML, RDFFormat.RDFXML, "");
						play.Logger.debug("Send Request");
						RequestBuilder request = new RequestBuilder().method("POST")
								.header("content-type", "application/rdf+xml")
								.header("accept", "application/json").bodyText(rdfString)
								.uri(controllers.routes.ZettelController
										.postForm("katalog:article", "json", "frl:6407884",
												"frl:6407884.rdf")
										.url());
						play.mvc.Result result = route(request);
						while (true) {
							org.junit.Assert.assertEquals(200, result.status());
							String jsonString = resultToString(result);
							play.Logger.info(jsonString);
							Article article = mapper.readValue(jsonString, Article.class);
							article.setCreator("One More");
							play.Logger.info(article.getCreator().size() + "creators added");

						}

					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
	}

	public static String resultToString(Result result) {
		return Helpers.contentAsString(result);
	}

	private static String printRdf(Article article) {
		try (InputStream in =
				new ByteArrayInputStream(article.toString().getBytes("utf-8"))) {
			String rdfString =
					RdfUtils.readRdfToString(in, RDFFormat.JSONLD, RDFFormat.RDFXML, "");
			return rdfString;
		} catch (Exception e) {
			play.Logger.debug("", e);
			return "";
		}
	}
}
