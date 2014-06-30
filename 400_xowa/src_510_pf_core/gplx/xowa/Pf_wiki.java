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
class Pf_wiki_props extends Pf_func_base {
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bb) {
		Xow_wiki_props props = ctx.Wiki().Props();
	    switch (id) {
			case Xol_kwd_grp_.Id_site_sitename: bb.Add(props.SiteName()); break;
			case Xol_kwd_grp_.Id_site_server: bb.Add(props.Server()); break;
			case Xol_kwd_grp_.Id_site_servername: bb.Add(props.ServerName()); break;
			case Xol_kwd_grp_.Id_site_articlepath: bb.Add(props.ArticlePath()); break;
			case Xol_kwd_grp_.Id_site_scriptpath: bb.Add(props.ScriptPath()); break;
			case Xol_kwd_grp_.Id_site_stylepath: bb.Add(props.StylePath()); break;
			case Xol_kwd_grp_.Id_site_contentlanguage: bb.Add(ctx.Cur_page().Lang().Key_bry()); break;
			case Xol_kwd_grp_.Id_site_directionmark: bb.Add(props.DirectionMark()); break;	// FUTURE: find page that uses it
			case Xol_kwd_grp_.Id_site_currentversion: bb.Add(props.CurrentVersion()); break;
			default: throw Err_.unhandled(id);
		}
	}
	public Pf_wiki_props(int id) {this.id = id;}
	@Override public int Id() {return id;} private int id;
	@Override public Pf_func New(int id, byte[] name) {return new Pf_wiki_props(id).Name_(name);}
	public static final Pf_wiki_props _ = new Pf_wiki_props(-1);
}
class Pf_wiki_stats extends Pf_func_base {
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bb) {
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
			default: throw Err_.unhandled(id);
		}
		if (raw)
			bb.Add_int_variable(v);
		else
			bb.Add_str(Int_.XtoStr_fmt(v, "#,##0"));	// FUTURE.LANG:
	}
	public Pf_wiki_stats(int id) {this.id = id;}
	@Override public int Id() {return id;} private int id;
	@Override public Pf_func New(int id, byte[] name) {return new Pf_wiki_stats(id).Name_(name);}
	public static final Pf_wiki_stats _ = new Pf_wiki_stats(-1);
}
class Pf_rev_props extends Pf_func_base {
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {
		byte[] argx = Eval_argx(ctx, src, caller, self);
		Xoa_revision_data revision_data = ctx.Cur_page().Revision_data();
		switch (id) {
			case Xol_kwd_grp_.Id_page_id:
			case Xol_kwd_grp_.Id_rev_id: bfr.Add_int_variable(ctx.Cur_page().Revision_data().Id()); break;	// NOTE: making rev_id and page_id interchangeable; XOWA does not store rev_id
			case Xol_kwd_grp_.Id_rev_user: bfr.Add(revision_data.User()); break;
			case Xol_kwd_grp_.Id_rev_pagesize:
				if (argx.length > 0) {
					Xoa_ttl argx_ttl = Xoa_ttl.parse_(ctx.Wiki(), argx);
					if (argx_ttl == null) {	// invalid ttl; EX: {{PAGESIZE:{{{bad}}}}}
						bfr.Add_byte(Byte_ascii.Num_0);
						return;
					}
					Xoa_page argx_page = ctx.Wiki().Data_mgr().Get_page(argx_ttl, false);
					if (!argx_page.Missing()) {
						bfr.Add_int_variable(argx_page.Data_raw().length);
						return;
					}
				}
				bfr.Add_byte(Byte_ascii.Num_0);
				break;
			case Xol_kwd_grp_.Id_rev_protectionlevel: bfr.Add(revision_data.Protection_level()); break;
			default: throw Err_.unhandled(id);
		}
	}
	public Pf_rev_props(int id) {this.id = id;}
	@Override public int Id() {return id;} private int id;
	@Override public Pf_func New(int id, byte[] name) {return new Pf_rev_props(id).Name_(name);}
	public static final Pf_rev_props _ = new Pf_rev_props(-1);
}
