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
package gplx.srls.dsvs; import gplx.*; import gplx.srls.*;
public abstract class Dsv_wkr_base implements GfoInvkAble {
	public abstract Dsv_fld_parser[] Fld_parsers();
	public abstract void Commit_itm(Dsv_tbl_parser parser, int pos);
	@gplx.Virtual public boolean Write_bry(Dsv_tbl_parser parser, int fld_idx, byte[] src, int bgn, int end) {return false;}
	@gplx.Virtual public boolean Write_int(Dsv_tbl_parser parser, int fld_idx, int pos, int val_int)			{return false;}
	public void Load_by_bry(byte[] src) {
		Dsv_tbl_parser tbl_parser = new Dsv_tbl_parser();	// NOTE: this proc should only be called once, so don't bother caching tbl_parser
		tbl_parser.Init(this, this.Fld_parsers());
		tbl_parser.Parse(src);
		tbl_parser.Rls();
		Load_by_bry_end();
	}
	@gplx.Virtual public void Load_by_bry_end() {}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_load_by_str))			Load_by_bry(m.ReadBry("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public static final String Invk_load_by_str = "load_by_str";
}
