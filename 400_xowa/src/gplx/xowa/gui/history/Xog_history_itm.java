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
	public void Srl_save(Bry_bfr bfr) {
		byte[] bmk_bry = Bry_.Replace(Bry_.new_u8(bmk_pos), Byte_ascii.Pipe, Byte_ascii.Tilde);	// replace | with ~; EX: "0|1|2" -> "0~1~2"
		bfr.Add(key).Add_byte_pipe().Add(bmk_bry).Add_byte_nl();
	}
	public static Xog_history_itm Srl_load(byte[] raw) {
		byte[][] atrs = Bry_.Split(raw, Byte_ascii.Pipe);
		byte[] bmk_bry = atrs.length == 6 ? atrs[5] : Bry_.Empty;
		bmk_bry = Bry_.Replace(bmk_bry, Byte_ascii.Tilde, Byte_ascii.Pipe);
		return new Xog_history_itm(atrs[0], atrs[1], atrs[2], atrs[3], atrs[4] == Bool_.Y_bry, String_.new_a7(bmk_bry));
	}
	public static final String Html_doc_pos_toc = "top";
	public static final Xog_history_itm Null = new Xog_history_itm(null, null, null, null, false, null);
}
