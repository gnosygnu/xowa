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
package gplx.xowa.gui.history; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*;
public class Xog_history_itm {
	public Xog_history_itm(Xoa_page page) {
		this.wiki_key = page.Wiki().Domain_bry();
		this.page_key = page.Ttl().Full_url();	// get page_name only (no anchor; no query args)
		this.anch_key = page.Url().Anchor_bry();
		this.qarg_key = page.Url().Args_all_as_bry();
		this.redirect_force = page.Url().Redirect_force() ? Bool_.Y_bry : Bool_.N_bry;
		this.key = Xog_history_itm.Build_key(wiki_key, page_key, anch_key, qarg_key, redirect_force);
		this.html_doc_pos = page.Html_data().Bmk_pos();
		if (this.html_doc_pos == null)
			this.html_doc_pos = Html_doc_pos_toc;	// never allow null doc_pos; set to top
	}
	public byte[] Key() {return key;} private byte[] key;
	public byte[] Wiki_key() {return wiki_key;} private byte[] wiki_key;
	public byte[] Page_key() {return page_key;} private byte[] page_key;
	public byte[] Anch_key() {return anch_key;} private byte[] anch_key;
	public byte[] Qarg_key() {return qarg_key;} private byte[] qarg_key;
	public byte[] Redirect_force() {return redirect_force;} private byte[] redirect_force;
	public String Html_doc_pos() {return html_doc_pos;} private String html_doc_pos;
	public void Html_doc_pos_(String v) {html_doc_pos = v;}
	public boolean Eq_except_bmk(Xog_history_itm comp) {
		return	Bry_.Eq(wiki_key, comp.Wiki_key())
			&&	Bry_.Eq(page_key, comp.Page_key())
			&&	Bry_.Eq(anch_key, comp.Anch_key())
			&&	Bry_.Eq(qarg_key, comp.Qarg_key())
			&&	Bry_.Eq(redirect_force, comp.Redirect_force())
			;
	}
	public static byte[] Build_key(byte[] wiki_key, byte[] page_key, byte[] anch_key, byte[] qarg_key, byte[] redirect_force) {
		return Bry_.Add_w_dlm(Byte_ascii.Pipe, wiki_key, page_key, anch_key, qarg_key, redirect_force);
	}
	public static final String Html_doc_pos_toc = "top";
	public static final Xog_history_itm Null = new Xog_history_itm(); private Xog_history_itm() {}
}
