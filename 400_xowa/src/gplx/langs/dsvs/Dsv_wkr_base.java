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
package gplx.langs.dsvs; import gplx.*; import gplx.langs.*;
public abstract class Dsv_wkr_base implements Gfo_invk {
	public abstract Dsv_fld_parser[] Fld_parsers();
	public byte[] Src() {return src;} private byte[] src;
	public abstract void Commit_itm(Dsv_tbl_parser parser, int pos);
	@gplx.Virtual public boolean Write_bry(Dsv_tbl_parser parser, int fld_idx, byte[] src, int bgn, int end) {return false;}
	@gplx.Virtual public boolean Write_int(Dsv_tbl_parser parser, int fld_idx, int pos, int val_int)			{return false;}
	public void Load_by_bry(byte[] src) {
		this.src = src;
		Dsv_tbl_parser tbl_parser = new Dsv_tbl_parser();	// NOTE: this proc should only be called once, so don't bother caching tbl_parser
		tbl_parser.Init(this, this.Fld_parsers());
		Load_by_bry_bgn();
		tbl_parser.Parse(src);
		tbl_parser.Rls();
		Load_by_bry_end();
	}
	@gplx.Virtual public void Load_by_bry_bgn() {}
	@gplx.Virtual public void Load_by_bry_end() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_load_by_str))			Load_by_bry(m.ReadBry("v"));
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	public static final String Invk_load_by_str = "load_by_str";
}
