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
package gplx.xowa.parsers.tblws; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_tblw_lxr_ws {
	public static int Make(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos, byte wlxr_type, boolean called_from_pre) {
		int rv = Xop_tblw_lxr.Handle_bang(wlxr_type, ctx, ctx.Tkn_mkr(), root, src, src_len, bgn_pos, cur_pos);
		if (rv != Xop_tblw_lxr.Continue) return rv;
		rv = Xop_tblw_lxr.Handle_lnki(wlxr_type, ctx, ctx.Tkn_mkr(), root, src, src_len, bgn_pos, cur_pos);
		if (rv != Xop_tblw_lxr.Continue) return rv;
		if (!called_from_pre) {	// skip if called from pre, else will return text, since pre_lxr has not created \n tkn yet; EX: "\n ! a"; DATE:2014-02-14
			// find first non-ws tkn; check if nl or para
			int root_subs_len = root.Subs_len();
			int tkn_idx = root_subs_len - 1;
			boolean loop = true, nl_found = false;
			while (loop) {
				if (tkn_idx < 0) break;
				Xop_tkn_itm tkn = root.Subs_get(tkn_idx);
				switch (tkn.Tkn_tid()) {
					case Xop_tkn_itm_.Tid_space: case Xop_tkn_itm_.Tid_tab:	// ws: keep moving backwards					
						tkn_idx--;
						break;
					case Xop_tkn_itm_.Tid_newLine:
					case Xop_tkn_itm_.Tid_para:
						loop = false;
						nl_found = true;
						break;
					default:
						loop = false;
						break;
				}
			}
			if (tkn_idx == -1) {								// bos reached; all tkns are ws;
				if (wlxr_type == Xop_tblw_wkr.Tblw_type_tb) {	// wlxr_type is {|;
					root.Subs_del_after(0);						// trim
					return ctx.Tblw().Make_tkn_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, false, wlxr_type, Xop_tblw_wkr.Called_from_general, -1, -1);	// process {|
				}
				else											// wlxr_type is something else, but invalid since no containing {|
					return ctx.Lxr_make_txt_(cur_pos);
			}

			if (!nl_found && wlxr_type == Xop_tblw_wkr.Tblw_type_td)	// | but no nl; return control to pipe_lxr for further processing
				return Tblw_ws_cell_pipe;
			if (nl_found)
				root.Subs_del_after(tkn_idx);
		}
		return ctx.Tblw().Make_tkn_bgn(ctx, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, false, wlxr_type, Xop_tblw_wkr.Called_from_general, -1, -1);
	}
	public static final byte[] Hook_tb = Bry_.new_ascii_("{|"), Hook_te = Bry_.new_ascii_("|}"), Hook_tr = Bry_.new_ascii_("|-")
		, Hook_th = Bry_.new_ascii_("!"), Hook_tc = Bry_.new_ascii_("|+");
	public static final int Tblw_ws_cell_pipe = -1;
}
