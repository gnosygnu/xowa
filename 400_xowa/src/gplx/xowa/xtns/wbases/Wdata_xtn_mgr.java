/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.wbases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.langs.htmls.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.bldrs.*;
import gplx.xowa.wikis.*;
public class Wdata_xtn_mgr extends Xox_mgr_base {
	private static final String XTN_KEY_STR = "Wikibase"; public static final    byte[] XTN_KEY = Bry_.new_a7(XTN_KEY_STR);
	@Override public boolean Enabled_default() {return false;}
	@Override public byte[] Xtn_key() {return XTN_KEY;} 
	@Override public Xox_mgr Xtn_clone_new() {return new Wdata_xtn_mgr();}
	@Override public void Xtn_init_by_wiki(Xowe_wiki wiki) {
		if (!Enabled()) return;
	}
	public void Load_msgs(Xowe_wiki wdata_wiki, Xol_lang_itm lang) {
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
