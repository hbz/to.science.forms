package services;

import java.io.InputStream;
import java.util.Map;

import org.openrdf.rio.RDFFormat;

public interface ZettelModel {

	public ZettelModel loadRdf(InputStream in, RDFFormat format);

	public Map<String, Object> getJsonLdMap();
}
