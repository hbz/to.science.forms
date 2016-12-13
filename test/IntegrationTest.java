
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
import org.junit.*;
import org.openrdf.rio.RDFFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.html.HtmlEscapers;

import models.JsonMessage;
import models.ResearchData;
import play.api.mvc.Result;
import play.data.Form;
import play.mvc.Http.RequestBuilder;
import services.RdfUtils;
import services.XmlUtils;
import services.ZettelHelper;
import services.ZettelModel;
import services.ZettelRegister;
import services.ZettelRegisterEntry;

import static play.test.Helpers.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * @author Jan Schnasse
 *
 */

@SuppressWarnings("javadoc")
public class IntegrationTest {

	/**
	 * add your integration test here in this example we just check if the welcome
	 * page is being shown
	 */
	@Test
	public void test() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT,
				browser -> {
					browser.goTo("http://localhost:3333");
					assertTrue(browser.pageSource().contains("Zettel"));
				});
	}

	@Test
	public void testJsonLd() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT,
				browser -> {
					try {
						ResearchData testData = new ResearchData();
						testData.setTitle("Test Title");
						List<String> author = new ArrayList<>();
						author.add("http://d-nb.info/gnd/1047170264");
						author.add("http://d-nb.info/gnd/5030229-2");
						List<String> geonames = new ArrayList<>();
						geonames.add("http://www.geonames.org/2886242");
						testData.setRecordingLocation(geonames);
						testData.setCreator(author);
						String jsonldString = testData.toString();
						Map<String, Object> m = testData.serializeToMap();
						m.remove("@context");
						play.Logger.debug(ZettelHelper.objectToString(m));
						String rdfString = RdfUtils.readRdfToString(
								new ByteArrayInputStream(jsonldString.getBytes("utf-8")),
								RDFFormat.JSONLD, RDFFormat.RDFXML, "");
						play.Logger.debug(HtmlEscapers.htmlEscaper().escape(rdfString));
						Assert.assertFalse(
								"{\"message\":\"Cannot create string\"}".equals(jsonldString));
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
	}

	@Test
	public void testJsonLdFromMessage() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT,
				browser -> {
					try {
						ResearchData testData = new ResearchData();
						testData.setTitle("Test Title");
						List<String> author = new ArrayList<>();
						author.add("http://d-nb.info/gnd/1047170264");
						author.add("http://d-nb.info/gnd/5030229-2");
						testData.setCreator(author);

						List<String> geonames = new ArrayList<>();
						geonames.add("http://www.geonames.org/2886242");
						testData.setRecordingLocation(geonames);

						String eventData =
								new JsonMessage(testData.serializeToMap(), 200).toString();
						play.Logger.debug(eventData);

						Map<String, Object> eventDataAsMap =
								new ObjectMapper().readValue(eventData, HashMap.class);

						String jsonldString = new ObjectMapper()
								.writeValueAsString(eventDataAsMap.get("message"));

						play.Logger.debug(jsonldString);
						String rdfString = RdfUtils.readRdfToString(
								new ByteArrayInputStream(jsonldString.getBytes("utf-8")),
								RDFFormat.JSONLD, RDFFormat.RDFXML, "");
						play.Logger.debug(rdfString);
						Assert.assertFalse(
								"{\"message\":\"Cannot create string\"}".equals(jsonldString));

					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
	}

	@Test
	public void testFormReload() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT,
				browser -> {
					try {
						ResearchData testData = new ResearchData();
						testData.setTitle("Test Title");
						List<String> author = new ArrayList<>();
						author.add("http://d-nb.info/gnd/1047170264");
						author.add("http://d-nb.info/gnd/5030229-2");
						author.add("http://orcid.org/0000-0002-9407-1672");
						testData.setCreator(author);

						List<String> geonames = new ArrayList<>();
						geonames.add("http://www.geonames.org/2886242");
						testData.setRecordingLocation(geonames);

						List<String> coords = new ArrayList<>();
						coords.add("http://www.openstreetmap.org/?mlat=50.9&mlon=6.9");
						testData.setRecordingCoordinates(coords);

						String jsonldString = toString(testData);

						play.Logger.debug(jsonldString);
						String rdfString = RdfUtils.readRdfToString(
								new ByteArrayInputStream(jsonldString.getBytes("utf-8")),
								RDFFormat.JSONLD, RDFFormat.RDFXML, "");
						play.Logger.debug(rdfString);

						play.Logger.debug("Send Request");
						RequestBuilder request = new RequestBuilder().method("POST")
								.header("content-type", "application/rdf+xml")
								.header("accept", "application/json").bodyText(rdfString)
								.uri(controllers.routes.ZettelController
										.postForm("katalog:researchData", "json", "test:foo",
												"test:foo.rdf")
										.url());
						play.mvc.Result result = route(request);

						play.Logger.debug(contentAsString(result));

					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
	}

	private String toString(ResearchData testData)
			throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(testData.serializeToMap());
	}

}
