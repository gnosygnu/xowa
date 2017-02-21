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
package gplx.xowa.guis.history; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
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
		byte[][] atrs = Bry_split_.Split(raw, Byte_ascii.Pipe);
		byte[] bmk_bry = atrs.length == 6 ? atrs[5] : Bry_.Empty;
		bmk_bry = Bry_.Replace(bmk_bry, Byte_ascii.Tilde, Byte_ascii.Pipe);
		return new Xog_history_itm(atrs[0], atrs[1], atrs[2], atrs[3], atrs[4] == Bool_.Y_bry, String_.new_a7(bmk_bry));
	}
	public static final String Html_doc_pos_toc = "top";
	public static final Xog_history_itm Null = new Xog_history_itm(null, null, null, null, false, null);
}
