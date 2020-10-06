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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The ZettelRegister provides access to all available forms.
 * 
 * @author Jan Schnasse
 *
 */
@SuppressWarnings("javadoc")
public class ZettelRegister {
	Map<String, ZettelRegisterEntry> register = new HashMap<>();

	public ZettelRegister() {
		register(new ResearchDataKtblZettel());
		register(new ResearchDataZettel());
		register(new ArticleZettel());
		register(new CatalogZettel());
		/**
		 * register additional forms
		 */
	}

	private void register(ZettelRegisterEntry form) {
		register.put(form.getId(), form);
	}

	public List<String> getIds() {
		return register.keySet().parallelStream().collect(Collectors.toList());
	}

	public ZettelRegisterEntry get(String id) {
		return register.get(id);
	}
}
