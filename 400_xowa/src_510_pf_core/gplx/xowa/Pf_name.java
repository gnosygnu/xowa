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
class Pf_name extends Pf_func_base {
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {
		byte[] val_dat_ary = Eval_argx_or_null(ctx, src, caller, self, this.Name()); 
		if (val_dat_ary == Bry_.Empty) return; // if argx is empty, return EX: {{PAGENAME:}}; DATE:2013-02-20
		Xoa_ttl ttl = val_dat_ary == null ? ctx.Cur_page().Ttl() : Xoa_ttl.parse_(ctx.Wiki(), val_dat_ary);
		if (ttl == null) return;
	    switch (id) {
			case Xol_kwd_grp_.Id_ttl_page_txt: bfr.Add(ttl.Page_txt()); break;
			case Xol_kwd_grp_.Id_ttl_page_url: bfr.Add(ttl.Page_url()); break;
			case Xol_kwd_grp_.Id_ttl_full_txt: bfr.Add(ttl.Full_txt()); break;
			case Xol_kwd_grp_.Id_ttl_full_url: bfr.Add(ttl.Full_url()); break;
			case Xol_kwd_grp_.Id_ttl_leaf_txt: bfr.Add(ttl.Leaf_txt()); break;
			case Xol_kwd_grp_.Id_ttl_leaf_url: bfr.Add(ttl.Leaf_url()); break;
			case Xol_kwd_grp_.Id_ttl_base_txt: bfr.Add(ttl.Base_txt()); break;
			case Xol_kwd_grp_.Id_ttl_base_url: bfr.Add(ttl.Base_url()); break;
			case Xol_kwd_grp_.Id_ttl_subj_txt: bfr.Add(ttl.Subj_txt()); break;
			case Xol_kwd_grp_.Id_ttl_subj_url: bfr.Add(ttl.Subj_url()); break;
			case Xol_kwd_grp_.Id_ttl_talk_txt: bfr.Add(ttl.Talk_txt()); break;
			case Xol_kwd_grp_.Id_ttl_talk_url: bfr.Add(ttl.Talk_url()); break;
			case Xol_kwd_grp_.Id_ns_num: bfr.Add_int_variable(ttl.Ns().Id()); break;
			case Xol_kwd_grp_.Id_ns_txt: bfr.Add(ttl.Ns().Name_txt()); break;
			case Xol_kwd_grp_.Id_ns_url: bfr.Add(ttl.Ns().Name_enc()); break;
			case Xol_kwd_grp_.Id_ns_subj_txt: bfr.Add(ctx.Wiki().Ns_mgr().Ords_get_at(ttl.Ns().Ord_subj_id()).Name_txt()); break;
			case Xol_kwd_grp_.Id_ns_subj_url: bfr.Add(ctx.Wiki().Ns_mgr().Ords_get_at(ttl.Ns().Ord_subj_id()).Name_enc()); break;
			case Xol_kwd_grp_.Id_ns_talk_txt: bfr.Add(ctx.Wiki().Ns_mgr().Ords_get_at(ttl.Ns().Ord_talk_id()).Name_txt()); break;
			case Xol_kwd_grp_.Id_ns_talk_url: bfr.Add(ctx.Wiki().Ns_mgr().Ords_get_at(ttl.Ns().Ord_talk_id()).Name_enc()); break;
		}
	}
	public Pf_name(int id) {this.id = id;}
	@Override public int Id() {return id;} private int id;
	@Override public Pf_func New(int id, byte[] name) {return new Pf_name(id).Name_(name);}
	public static final Pf_name _ = new Pf_name(-1);
}
