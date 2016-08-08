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
package gplx.xowa.addons.bldrs.mass_parses.parses; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.mass_parses.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.cases.*;
public class Xow_wiki_utl_ {
	public static Xowe_wiki Clone_wiki(Xowe_wiki wiki, Io_url wiki_dir) {
		Xol_lang_itm lang = new Xol_lang_itm(wiki.App().Lang_mgr(), wiki.Lang().Key_bry());
		Xol_lang_itm_.Lang_init(lang);
		Xowe_wiki rv = new Xowe_wiki(wiki.Appe(), lang, gplx.xowa.wikis.nss.Xow_ns_mgr_.default_(lang.Case_mgr()), wiki.Domain_itm(), wiki_dir);
		rv.Init_by_wiki();
		rv.File_mgr().Repo_mgr().Clone(wiki.File_mgr().Repo_mgr());
		rv.File__fsdb_mode().Tid__v2__bld__y_();
		return rv;
	}
}
