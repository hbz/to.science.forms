import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.route;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.eclipse.rdf4j.rio.RDFFormat;
import org.junit.Test;

import play.mvc.Http.RequestBuilder;
import services.RdfUtils;

public class TestLargeRequest {

	@Test
	public void testLargeFile() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT,
				browser -> {
					try {
						String rdfString = RdfUtils.readRdfToString(
								play.Environment.simple().resourceAsStream("big-rdf-file.rdf"),
								RDFFormat.RDFXML, RDFFormat.RDFXML, "");
						play.Logger.debug("Send Request");
						RequestBuilder request = new RequestBuilder().method("POST")
								.header("content-type", "application/rdf+xml")
								.header("accept", "application/json").bodyText(rdfString)
								.uri(controllers.routes.ZettelController
										.postForm("katalog:researchData", "json", "frl:6407884",
												"frl:6407884.rdf")
										.url());
						play.mvc.Result result = route(request);
						org.junit.Assert.assertEquals(200, result.status());

					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
	}
}
