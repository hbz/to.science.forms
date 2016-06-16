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
package models;

import com.fasterxml.jackson.databind.JsonNode;

import services.ZettelHelper;

/**
 * @author Jan Schnasse
 *
 */
@SuppressWarnings("javadoc")
public class JsonMessage {

	JsonNode message;
	String code;

	public JsonMessage(Object object, int i) {
		message = ZettelHelper.objectToJson(object);
		code = Integer.toString(i);
	}

	public JsonNode getMessage() {
		return message;
	}

	public void setMessage(JsonNode message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return ZettelHelper.objectToString(this);
	}
}
