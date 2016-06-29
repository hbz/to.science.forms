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
package services;

import play.data.Form;
import play.twirl.api.Content;

/**
 * @author Jan Schnasse
 *
 */
public interface ZettelRegisterEntry {

	/**
	 * @return the id of a provided Html form
	 */
	String getId();

	/**
	 * @return the model that holds the forms data
	 */
	ZettelModel getModel();

	/**
	 * <p>
	 * You can easily inject a form from your running application (e.g. from
	 * controller)
	 * </p>
	 * <code>
	 * Form<?> form = formFactory.form(zettel.getModel().getClass());
	 * </code>
	 * 
	 * @param form the form that must be rendered
	 * @param format ask for certain format. supports xml and json
	 * @param documentId your personal id for the document you want to create form
	 *          data for
	 * @param topicId the topic id is used by our regal-drupal to find the actual
	 *          documentId. You can probably ignore this.
	 * @return a Html rendering
	 */
	Content render(Form<?> form, String format, String documentId,
			String topicId);

}