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
package gplx.xowa.xtns.relatedSites; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.langs.htmls.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*;
public class Sites_xtn_mgr extends Xox_mgr_base {
	public Sites_xtn_mgr() {
		html_bldr = new Sites_html_bldr(this);
		regy_mgr = new Sites_regy_mgr(this);
	}
	@Override public boolean Enabled_default() {return false;}
	@Override public byte[] Xtn_key() {return XTN_KEY;} public static final byte[] XTN_KEY = Bry_.new_a7("RelatedSites");
	@Override public Xox_mgr Clone_new() {return new Sites_xtn_mgr();}
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
