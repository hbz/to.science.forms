
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.html.HtmlEscapers;

import models.JsonMessage;
import models.ResearchData;
import services.RdfUtils;

import static play.test.Helpers.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
						testData.setCreator(author);
						String jsonldString = testData.toString();
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
}
