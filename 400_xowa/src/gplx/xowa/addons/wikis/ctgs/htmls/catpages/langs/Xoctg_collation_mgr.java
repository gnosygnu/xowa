/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.langs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import gplx.core.intls.ucas.*;
public class Xoctg_collation_mgr {
	private final    Xow_wiki wiki;
	private Uca_collator collator;
	private String collation_name = "uppercase";	// REF:https://noc.wikimedia.org/conf/InitialiseSettings.php.txt|wgCategoryCollation|default
	public Xoctg_collation_mgr(Xow_wiki wiki) {
		this.wiki = wiki;
		if (String_.Eq(wiki.Domain_str(), "en.wikipedia.org"))
			collation_name = "uca-default-kn";
	}
	public void Collation_name_(String v) {
		this.collation_name = v;
	}
	public byte[] Get_sortkey(byte[] src) {
		if (String_.Eq(collation_name, "uppercase")) {
			return wiki.Lang().Case_mgr().Case_build_upper(src);
		}
		else {
			if (collator == null) collator = Uca_collator_.New(collation_name, true);
			return collator.Get_sortkey(String_.new_u8(src));
		}
	}
}
