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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
public class Xop_tkn_null implements Xop_tkn_itm {
	public byte Tkn_tid() {return Xop_tkn_itm_.Tid_null;}
	public boolean Tkn_immutable() {return true;}
	public Xop_tkn_grp Tkn_grp() {return Xop_tkn_grp_.Null;}
	public Xop_tkn_itm Tkn_ini_pos(boolean immutable, int bgn, int end) {return this;}
	public Xop_tkn_itm Tkn_grp_(Xop_tkn_grp grp, int sub_idx) {return this;}
	public Xop_tkn_itm Tkn_clone(Xop_ctx ctx, int bgn, int end) {return this;}
	public int Tkn_sub_idx() {return -1;}
	public int Src_bgn() {return -1;}
	public int Src_end() {return -1;} 
	public int Src_bgn_grp(Xop_tkn_grp grp, int sub_idx) {return -1;}
	public int Src_end_grp(Xop_tkn_grp grp, int sub_idx) {return -1;}
	public int Subs_src_bgn(int sub_idx) {return -1;}
	public int Subs_src_end(int sub_idx) {return -1;}
	public void Src_end_(int v) {}
	public void Src_end_grp_(Xop_ctx ctx, Xop_tkn_grp grp, int sub_idx, int src_end) {}
	public boolean Ignore() {return false;} public Xop_tkn_itm Ignore_y_() {return this;}
	public int Subs_len() {return 0;}
	public Xop_tkn_itm Subs_get(int i) {return null;}
	public void Subs_add(Xop_tkn_itm sub) {}
	public void Subs_add_grp(Xop_tkn_itm sub, Xop_tkn_grp old_grp, int old_sub_idx) {}
	public void Subs_del_after(int pos_bgn) {}
	public void Subs_clear() {}
	public void Subs_move(Xop_tkn_itm tkn) {}
	public Xop_tkn_itm Immutable_clone(Xop_ctx ctx, Xop_tkn_itm tkn, int sub_idx) {return this;}
	public void Ignore_y_grp_(Xop_ctx ctx, Xop_tkn_grp grp, int sub_idx) {}
	public void Subs_grp_(Xop_ctx ctx, Xop_tkn_itm tkn, Xop_tkn_grp grp, int sub_idx) {}
	public void Subs_src_pos_(int sub_idx, int bgn, int end) {}
	public void Clear() {}
	public void Tmpl_fmt(Xop_ctx ctx, byte[] src, Xot_fmtr fmtr) {}
	public void Tmpl_compile(Xop_ctx ctx, byte[] src, Xot_compile_data prep_data) {}
	public boolean Tmpl_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Bry_bfr bfr) {return true;}
	public void Html__write(Bry_bfr bfr, Xoh_html_wtr wtr, Xowe_wiki wiki, Xoae_page page, Xop_ctx ctx, Xoh_wtr_ctx hctx, Xoh_html_wtr_cfg cfg, Xop_tkn_grp grp, int sub_idx, byte[] src) {}
	public static final Xop_tkn_null Null_tkn = new Xop_tkn_null();
}
