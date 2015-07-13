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
package gplx.xowa.xtns.wdatas; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.html.*; import gplx.xowa.wikis.*; import gplx.xowa.bldrs.langs.*;
public class Wdata_xtn_mgr extends Xox_mgr_base {
	private static final String XTN_KEY_STR = "Wikibase"; public static final byte[] XTN_KEY = Bry_.new_a7(XTN_KEY_STR);
	@Override public boolean Enabled_default() {return false;}
	@Override public byte[] Xtn_key() {return XTN_KEY;} 
	@Override public Xox_mgr Clone_new() {return new Wdata_xtn_mgr();}
	@Override public void Xtn_init_by_wiki(Xowe_wiki wiki) {
		if (!Enabled()) return;
	}
	public void Load_msgs(Xowe_wiki wdata_wiki, Xol_lang lang) {
		wdata_wiki.Msg_mgr().Lang_(lang);
		Xtn_load_i18n(wdata_wiki, XTN_KEY_STR, "lib" , "i18n", lang.Key_str() + ".json");
		Xtn_load_i18n(wdata_wiki, XTN_KEY_STR, "repo", "i18n", lang.Key_str() + ".json");
	}
	private static void Xtn_load_i18n(Xowe_wiki wiki, String... nest_paths) {
		Xoae_app app = wiki.Appe();
		Io_url url = app.Fsys_mgr().Bin_xtns_dir().GenSubFil_nest(nest_paths);
		Xob_i18n_parser.Load_msgs(false, wiki.Lang(), url);
	}
}
