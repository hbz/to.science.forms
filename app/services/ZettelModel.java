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
package services;

import java.io.InputStream;
import java.util.Map;

import org.openrdf.rio.RDFFormat;

/**
 * @author Jan Schnasse
 *
 */
public interface ZettelModel {

	/**
	 * @param in input stream with rdf data
	 * @param format format of the rdf serialization
	 * @return the rdf data loaded to a ZettelModel
	 */
	public ZettelModel loadRdf(InputStream in, RDFFormat format);

	/**
	 * @return json ld map for this model
	 */
	public Map<String, Object> getJsonLdMap();
}
