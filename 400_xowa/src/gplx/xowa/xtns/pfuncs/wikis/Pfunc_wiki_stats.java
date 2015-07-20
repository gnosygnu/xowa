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
package gplx.xowa.xtns.pfuncs.wikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
public class Pfunc_wiki_stats extends Pf_func_base {
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr rslt_bfr) {
		byte[] argx = Eval_argx(ctx, src, caller, self);
		boolean raw = false;			
		if (argx.length == 1) {
			byte argx_0 = argx[0];
			switch (argx_0) {case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_r: raw = true; break;}
		}
		Xow_wiki_stats stats = ctx.Wiki().Stats();
		int v = 0;
	    switch (id) {
			case Xol_kwd_grp_.Id_num_pages:		v = stats.NumPages(); break;
			case Xol_kwd_grp_.Id_num_articles:	v = stats.NumArticles(); break;
			case Xol_kwd_grp_.Id_num_files:		v = stats.NumFiles(); break;
			case Xol_kwd_grp_.Id_num_edits:		v = stats.NumEdits(); break;
			case Xol_kwd_grp_.Id_num_views:		v = stats.NumViews(); break;
			case Xol_kwd_grp_.Id_num_users:		v = stats.NumUsers(); break;
			case Xol_kwd_grp_.Id_num_users_active: v = stats.NumUsersActive(); break;
			case Xol_kwd_grp_.Id_num_admins:	v = stats.NumAdmins(); break;
			default: throw Err_.new_unhandled(id);
		}
		if (raw)
			rslt_bfr.Add_int_variable(v);
		else
			rslt_bfr.Add(ctx.Cur_page().Lang().Num_mgr().Format_num(v));
	}
	public Pfunc_wiki_stats(int id) {this.id = id;}
	@Override public int Id() {return id;} private int id;
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_wiki_stats(id).Name_(name);}
	public static final Pfunc_wiki_stats _ = new Pfunc_wiki_stats(-1);
}
