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
	private final boolean redirect_force;
	public Xog_history_itm(byte[] wiki, byte[] page, byte[] anch, byte[] qarg, boolean redirect_force, String bmk_pos) {
		this.key = Bry_.Add_w_dlm(Byte_ascii.Pipe, wiki, page, anch, qarg, redirect_force ? Bool_.Y_bry : Bool_.N_bry);
		this.wiki = wiki; this.page = page; this.anch = anch; this.qarg = qarg;
		this.redirect_force = redirect_force; this.bmk_pos = bmk_pos;
	}
	public byte[] Key() {return key;} private final byte[] key;
	public byte[] Wiki() {return wiki;} private final byte[] wiki;
	public byte[] Page() {return page;} private final byte[] page;
	public byte[] Anch() {return anch;} private final byte[] anch;
	public byte[] Qarg() {return qarg;} private final byte[] qarg;
	public String Bmk_pos() {return bmk_pos;} public void Bmk_pos_(String v) {bmk_pos = v;} private String bmk_pos;
	public boolean Eq_wo_bmk_pos(Xog_history_itm comp) {
		return	Bry_.Eq(wiki, comp.wiki)
			&&	Bry_.Eq(page, comp.page)
			&&	Bry_.Eq(anch, comp.anch)
			&&	Bry_.Eq(qarg, comp.qarg)
			&&	redirect_force == comp.redirect_force
			;
	}
	public static final String Html_doc_pos_toc = "top";
	public static final Xog_history_itm Null = new Xog_history_itm(null, null, null, null, false, null);
}
