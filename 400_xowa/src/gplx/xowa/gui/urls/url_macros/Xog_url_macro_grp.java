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
package gplx.xowa.gui.urls.url_macros; import gplx.*; import gplx.xowa.*; import gplx.xowa.gui.*; import gplx.xowa.gui.urls.*;
public class Xog_url_macro_grp implements GfoInvkAble {
	public ByteTrieMgr_slim Trie() {return trie;} private ByteTrieMgr_slim trie = ByteTrieMgr_slim.cs_();
	public void Del(byte[] abrv) {trie.Del(abrv);}
	public void Set(String abrv, String fmt) {Set(Bry_.new_utf8_(abrv), Bry_.new_utf8_(fmt));}
	public void Set(byte[] abrv, byte[] fmt) {trie.Add(abrv, new Xog_url_macro_itm(abrv, fmt));}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_clear))						trie.Clear();
		else if	(ctx.Match(k, Invk_set))						Set(m.ReadBry("abrv"), m.ReadBry("fmt"));
		else if	(ctx.Match(k, Invk_del))						Del(m.ReadBry("abrv"));
		else return GfoInvkAble_.Rv_unhandled;
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
		return bfr.XtoAryAndClear();
	}
}
