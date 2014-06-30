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
package gplx.xowa; import gplx.*;
class Xot_prm_wkr implements Xop_arg_wkr {
	private static Arg_bldr arg_bldr = Arg_bldr._;
	public boolean Make_tkn(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int lxr_bgn_pos, int lxr_cur_pos, Xop_curly_bgn_tkn bgn, int keep_curly_bgn) {
		int loop_bgn = bgn.Tkn_sub_idx() + 1;	// +1 to ignore curly_bgn
		int loop_end = root.Subs_len();
		if (loop_bgn == loop_end) {// no tkns; output literal {{{}}}	// 2012.03.27:commented out due to {{{{{{}}}}}}
			root.Subs_del_after(bgn.Tkn_sub_idx());
			ctx.Msg_log().Add_itm_none(Xot_prm_log.Lkp_is_nil, src, lxr_bgn_pos, lxr_cur_pos); 
			ctx.Subs_add(root, tkn_mkr.Txt(bgn.Src_bgn(), lxr_cur_pos));
			return false;
		}
		Xot_prm_tkn prm_tkn = tkn_mkr.Tmpl_prm(bgn.Src_bgn(), lxr_cur_pos);
		arg_bldr.Bld(ctx, tkn_mkr, Xot_prm_wkr._, Xop_arg_wkr_.Typ_prm, root, prm_tkn, lxr_bgn_pos, lxr_cur_pos, loop_bgn, loop_end, src);
		root.Subs_del_after(bgn.Tkn_sub_idx() + keep_curly_bgn);	// NOTE: keep_curly_bgn determines whether or not to delete opening {{{
		root.Subs_add(prm_tkn);
		return true;
	}
	public boolean Args_add(Xop_ctx ctx, byte[] src, Xop_tkn_itm tkn, Arg_nde_tkn arg, int arg_idx) {
		Xot_prm_tkn prm = (Xot_prm_tkn)tkn;
		switch (arg_idx) {
			case 0:			prm.Find_tkn_(arg.Val_tkn()); break;
			case 1:			prm.Dflt_tkn_(arg.Val_tkn()); break;
			default:		ctx.Msg_log().Add_itm_none(Xot_prm_log.Prm_has_2_or_more, src, arg.Src_bgn(), arg.Src_end()); break;
		}
		return true;
	}
	public static final Xot_prm_wkr _ = new Xot_prm_wkr(); Xot_prm_wkr() {}
}
