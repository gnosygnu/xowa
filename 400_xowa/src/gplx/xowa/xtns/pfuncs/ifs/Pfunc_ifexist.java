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
package gplx.xowa.xtns.pfuncs.ifs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
public class Pfunc_ifexist extends Pf_func_base {
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bb) {			
		int self_args_len = self.Args_len();
		byte[] val_dat_ary = Eval_argx(ctx, src, caller, self);
		if (Exists(tmp_db_page, ctx, val_dat_ary))
			bb.Add(Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 0));
		else
			bb.Add(Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 1));
	}	private static final Xodb_page tmp_db_page = Xodb_page.tmp_();
	public static int Count = 0;
	public static boolean Exists(Xodb_page rv, Xop_ctx ctx, byte[] val_dat_ary) {
		rv.Clear();
		if (val_dat_ary.length == 0) return false;
		if (val_dat_ary[0] == Byte_ascii.Brack_bgn) return false; // HACK: values like [[Commune (socialism)|Commune]] in [[Template:Infobox Former Country]] for Paris Commune
		Xow_wiki wiki = ctx.Wiki();
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, val_dat_ary);
		if (ttl == null) return false;	// not a valid title; TODO: logme; see [[Dinosaur]]; Template:Taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{{taxonomy/{Placeholder <strong class=\"error\">error message</strong>.\n}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}
		byte[] page_ttl = ttl.Page_db();	// NOTE: must use Page_db; EX: {{#ifexist:File:Peter & Paul fortress in SPB 03.jpg|y|n}}
		if (ttl.Ns().Id() < 0) {
			rv.Exists_(true);
			return true;	// HACK: -2 Media namespace returned for Heart normal.svg in Superficial_temporal_artery
		}
		Object exists_val = ctx.Wiki().If_exists_regy().Fetch(val_dat_ary);
		if (exists_val != null) {
			boolean exists = ((Bool_obj_val)exists_val).Val();
			rv.Exists_(exists);
			return exists;
		}
		boolean found = wiki.Db_mgr().Load_mgr().Load_by_ttl(rv, ttl.Ns(), page_ttl);
		if (	!found
			&&	wiki.Lang().Vnt_mgr().Enabled()) {
			Xodb_page page = wiki.Lang().Vnt_mgr().Convert_ttl(wiki, ttl.Ns(), page_ttl);
			if (page != Xodb_page.Null)
				found = page.Exists();
		}
		rv.Exists_(found);
		++Count;
		ctx.Wiki().If_exists_regy().Add(val_dat_ary, found ? Bool_obj_val.True : Bool_obj_val.False);
		return found;
	}		
	@Override public int Id() {return Xol_kwd_grp_.Id_xtn_iferror;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_ifexist().Name_(name);}
}	
