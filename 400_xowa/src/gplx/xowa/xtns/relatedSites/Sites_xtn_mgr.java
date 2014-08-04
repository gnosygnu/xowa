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
import gplx.html.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*;
public class Sites_xtn_mgr extends Xox_mgr_base {
	private Xow_xwiki_mgr xwiki_mgr;
	public Sites_xtn_mgr() {
		html_bldr = new Sites_html_bldr(this);
	}
	@Override public boolean Enabled_default() {return false;}
	@Override public byte[] Xtn_key() {return XTN_KEY;} public static final byte[] XTN_KEY = Bry_.new_ascii_("relatedSites");
	@Override public Xox_mgr Clone_new() {return new Sites_xtn_mgr();}
	@Override public void Xtn_init_by_wiki(Xow_wiki wiki) {
		this.wiki = wiki;
		this.xwiki_mgr = wiki.Xwiki_mgr();
		if (!Enabled()) return;
		msg_related_sites = wiki.Msg_mgr().Val_by_key_obj("relatedarticles-title");
	}
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
	public Sites_html_bldr Html_bldr() {return html_bldr;} private Sites_html_bldr html_bldr;
	public byte[] Msg_related_sites() {return msg_related_sites;} private byte[] msg_related_sites;
	public void Match(Xoa_ttl lnki_ttl, ListAdp list) {
		if (!this.Enabled()) return;
		byte[] xwiki_key = lnki_ttl.Wik_txt();
		Xow_xwiki_itm xwiki_itm = xwiki_mgr.Get_by_key(xwiki_key); if (xwiki_itm == null) return;
		Sites_regy_itm sites_itm = new Sites_regy_itm(xwiki_itm, lnki_ttl);
		list.Add(sites_itm);
	}
}
