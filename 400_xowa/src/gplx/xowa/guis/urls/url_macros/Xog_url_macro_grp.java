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
package gplx.xowa.guis.urls.url_macros; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.urls.*;
import gplx.core.btries.*; import gplx.core.brys.fmtrs.*;
public class Xog_url_macro_grp implements Gfo_invk {
	public Btrie_slim_mgr Trie() {return trie;} private Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	public void Del(byte[] abrv) {trie.Del(abrv);}
	public void Set(String abrv, String fmt) {Set(Bry_.new_u8(abrv), Bry_.new_u8(fmt));}
	public void Set(byte[] abrv, byte[] fmt) {trie.Add_obj(abrv, new Xog_url_macro_itm(abrv, fmt));}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_clear))						trie.Clear();
		else if	(ctx.Match(k, Invk_set))						Set(m.ReadBry("abrv"), m.ReadBry("fmt"));
		else if	(ctx.Match(k, Invk_del))						Del(m.ReadBry("abrv"));
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_clear = "clear", Invk_set = "set", Invk_del = "del";
}
class Xog_url_macro_itm {
	private Bry_fmtr fmtr;
	public Xog_url_macro_itm(byte[] abrv, byte[] fmt) {this.abrv = abrv; this.fmt = fmt;}
	public byte[] Abrv() {return abrv;} private byte[] abrv;
	public byte[] Fmt() {return fmt;} private byte[] fmt;
	public byte[] Fmtr_exec(Bry_bfr bfr, Object... args) {
		if (fmtr == null) fmtr = new Bry_fmtr().Fmt_(fmt).Compile();
		fmtr.Bld_bfr_many(bfr, args);
		return bfr.To_bry_and_clear();
	}
}
