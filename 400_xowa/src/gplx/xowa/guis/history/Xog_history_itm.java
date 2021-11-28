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
	public static final String Html_doc_pos_toc = "top";
	public static final Xog_history_itm Null = new Xog_history_itm(null, null, null, null, false, null);
}
