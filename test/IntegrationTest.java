import org.junit.*;
import org.openrdf.rio.RDFFormat;

import play.mvc.*;
import play.test.*;
import services.RdfUtils;

import static play.test.Helpers.*;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

/**
 * @author Jan Schnasse
 *
 */
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

	public void testJsonLd() throws UnsupportedEncodingException {
		String jsonldString;
		String rdfString = RdfUtils.readRdfToString(
				new ByteArrayInputStream(jsonldString.getBytes("utf-8")),
				RDFFormat.JSONLD, RDFFormat.RDFXML, "");
	}
}
