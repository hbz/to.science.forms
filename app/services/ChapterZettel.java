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

import models.Chapter;
import models.ZettelModel;
import play.data.Form;
import play.twirl.api.Content;
import views.html.chapter;

/**
 * @author Jan Schnasse
 *
 */
public class ChapterZettel implements ZettelRegisterEntry {

	Chapter model = new Chapter();

	@Override
	public String getId() {
		return Chapter.id;
	}

	@Override
	public Chapter getModel() {
		return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Content render(Form<?> form, String format, String documentId,
			String topicId) {
		return chapter.render((Form<ZettelModel>) form, format, documentId, topicId,
				getId());
	}

}
