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
package gplx.xowa.langs.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.langs.parsers.*;
public class Xol_ns_grp implements Gfo_invk {
	public Xol_ns_grp(Xol_lang_itm lang) {this.lang = lang;} private Xol_lang_itm lang;
	public int			Len()						{return ary.length;}
	public Xow_ns		Get_at(int i)				{return ary[i];} private Xow_ns[] ary = Ary_empty;
	public void			Ary_set_(Xow_ns[] v)		{this.ary = v;}
	public void			Ary_add_(Xow_ns[] add_ary) {
		int old_ary_len = ary.length;
		int add_ary_len = add_ary.length;
		Xow_ns[] new_ary = new Xow_ns[old_ary_len + add_ary_len];
		for (int i = 0; i < old_ary_len; i++)
			new_ary[i] = ary[i];
		for (int i = 0; i < add_ary_len; i++)
			new_ary[i + old_ary_len] = add_ary[i];
		this.ary = new_ary;
	}
	private static final    Xow_ns[] Ary_empty = new Xow_ns[0];
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_lang))					return lang;
		else if	(ctx.Match(k, Invk_load_text))				Exec_load_text(m.ReadBry("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_lang = Xol_lang_srl.Invk_lang, Invk_load_text = Xol_lang_srl.Invk_load_text;
	private void Exec_load_text(byte[] bry) {
		ary = (Xow_ns[])Array_.Resize_add(ary, Xol_lang_srl.Load_ns_grps(bry));
	}
}
