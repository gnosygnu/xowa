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
package gplx.xowa.xtns.relatedSites; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.langs.htmls.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*;
public class Sites_xtn_mgr extends Xox_mgr_base {
	public Sites_xtn_mgr() {
		html_bldr = new Sites_html_bldr(this);
		regy_mgr = new Sites_regy_mgr(this);
	}
	@Override public boolean Enabled_default() {return false;}
	@Override public byte[] Xtn_key() {return XTN_KEY;} public static final    byte[] XTN_KEY = Bry_.new_a7("RelatedSites");
	@Override public Xox_mgr Xtn_clone_new() {return new Sites_xtn_mgr();}
	@Override public void Xtn_init_by_wiki(Xowe_wiki wiki) {
		this.wiki = wiki;
		regy_mgr.Init_by_wiki(wiki);
		if (!Enabled()) return;
		Xox_mgr_base.Xtn_load_i18n(wiki, XTN_KEY);
		msg_related_sites = wiki.Msg_mgr().Val_by_key_obj("relatedsites-title");
	}
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public Sites_regy_mgr Regy_mgr() {return regy_mgr;} private Sites_regy_mgr regy_mgr;
	public Sites_html_bldr Html_bldr() {return html_bldr;} private Sites_html_bldr html_bldr;
	public byte[] Msg_related_sites() {return msg_related_sites;} private byte[] msg_related_sites;
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(String_.Eq(k, Invk_sites))		return regy_mgr;
		else									return super.Invk (ctx, ikey, k, m);
	}
	private static final String Invk_sites = "sites";
}
