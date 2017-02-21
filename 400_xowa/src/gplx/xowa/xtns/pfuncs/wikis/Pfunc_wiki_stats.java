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
package gplx.xowa.xtns.pfuncs.wikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.wikis.data.site_stats.*;
public class Pfunc_wiki_stats extends Pf_func_base {
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		byte[] argx = Eval_argx(ctx, src, caller, self);
		boolean raw = false;			
		if (argx.length == 1) {
			byte argx_0 = argx[0];
			switch (argx_0) {case Byte_ascii.Ltr_R: case Byte_ascii.Ltr_r: raw = true; break;}
		}
		Xowd_site_stats_mgr stats = ctx.Wiki().Stats();
		long v = 0;
	    switch (id) {
			case Xol_kwd_grp_.Id_num_pages:		v = stats.Num_pages(); break;
			case Xol_kwd_grp_.Id_num_articles:	v = stats.Num_articles(); break;
			case Xol_kwd_grp_.Id_num_files:		v = stats.Num_files(); break;
			case Xol_kwd_grp_.Id_num_edits:		v = stats.Num_edits(); break;
			case Xol_kwd_grp_.Id_num_views:		v = stats.Num_views(); break;
			case Xol_kwd_grp_.Id_num_users:		v = stats.Num_users(); break;
			case Xol_kwd_grp_.Id_num_users_active: v = stats.Num_active(); break;
			case Xol_kwd_grp_.Id_num_admins:	v = stats.Num_admins(); break;
			default: throw Err_.new_unhandled(id);
		}
		if (raw)
			bfr.Add_long_variable(v);
		else
			bfr.Add(ctx.Page().Lang().Num_mgr().Format_num_by_long(v));
	}
	public Pfunc_wiki_stats(int id) {this.id = id;}
	@Override public int Id() {return id;} private int id;
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_wiki_stats(id).Name_(name);}
	public static final    Pfunc_wiki_stats Instance = new Pfunc_wiki_stats(-1);
}
