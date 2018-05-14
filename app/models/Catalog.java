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
package models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import play.data.validation.ValidationError;
import models.ZettelModel;

/**
 * @author Jan Schnasse
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Catalog extends ZettelModel {

	/**
	 * The id under which this model is registered in the ZettelRegister
	 */
	public final static String id = "katalog:catalog";

	@Override
	protected String getType() {
		return "Catalog";
	}

	@Override
	protected List<ValidationError> validate() {
		// TODO Auto-generated method stub
		return null;
	}

}