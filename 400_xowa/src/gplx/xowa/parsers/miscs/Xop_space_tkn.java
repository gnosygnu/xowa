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
package gplx.xowa.parsers.miscs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*; import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
public class Xop_space_tkn extends Xop_tkn_itm_base {
	public Xop_space_tkn(boolean immutable, int bgn, int end)					{this.Tkn_ini_pos(immutable, bgn, end);}
	@Override public byte Tkn_tid()											{return Xop_tkn_itm_.Tid_space;}
	@Override public Xop_tkn_itm Tkn_clone(Xop_ctx ctx, int bgn, int end)	{return ctx.Tkn_mkr().Space_mutable(bgn, end);}
	@Override public boolean Tmpl_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Bry_bfr bfr) {
		if (this.Tkn_immutable()) {
			bfr.Add_byte(Byte_ascii.Space);
			return true;
		}
		else
			return super.Tmpl_evaluate(ctx, src, caller, bfr);
	}
	@Override public void Html__write(Bry_bfr bfr, Xoh_html_wtr wtr, Xowe_wiki wiki, Xoae_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, Xoh_html_wtr_cfg cfg, Xop_tkn_grp grp, int sub_idx, byte[] src) {
		bfr.Add_byte_repeat(Byte_ascii.Space, this.Src_end_grp(grp, sub_idx) - this.Src_bgn_grp(grp, sub_idx));	// NOTE: lnki.caption will convert \n to \s; see Xop_nl_lxr; PAGE:en.w:Schwarzschild radius
	}
}
