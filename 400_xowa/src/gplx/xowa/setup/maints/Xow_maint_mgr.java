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
package gplx.xowa.setup.maints; import gplx.*; import gplx.xowa.*; import gplx.xowa.setup.*;
public class Xow_maint_mgr implements GfoInvkAble {
	public Xow_maint_mgr(Xow_wiki wiki) {
		this.wiki = wiki;
		maint_mgr = wiki.App().Setup_mgr().Maint_mgr();
	}	private Xow_wiki wiki; private Xoa_maint_mgr maint_mgr;
	public DateAdp Wmf_dump_date() {
		maint_mgr.Wmf_dump_status_loaded_assert();
		return wmf_dump_date;
	}	public Xow_maint_mgr Wmf_dump_date_(DateAdp v) {this.wmf_dump_date = v; return this;} private DateAdp wmf_dump_date;
	public boolean Wmf_dump_done() {return wmf_dump_done;} public Xow_maint_mgr Wmf_dump_done_(boolean v) {this.wmf_dump_done = v; return this;} private boolean wmf_dump_done;
	public byte[] Wmf_dump_status() {return wmf_dump_status;} public Xow_maint_mgr Wmf_dump_status_(byte[] v) {this.wmf_dump_status = v; return this;} private byte[] wmf_dump_status;
	public DateAdp Wiki_dump_date() {
		if (wiki_dump_date == null)
			wiki_dump_date = wiki.Db_mgr().Dump_date_query();
		return wiki_dump_date;
	}	private DateAdp wiki_dump_date;
	public boolean Wiki_update_needed() {
		if (this.Wiki_dump_date() == null) return false;	// will be null if a custom wiki (i.e.: not on http://dumps.wikimedia.org/backup-index.html)
		return this.Wmf_dump_date().Diff(this.Wiki_dump_date()).Total_days().XtoDouble() > 1;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_wmf_dump_date)) 			return DateAdp_.Xto_str_fmt_or(Wmf_dump_date(), "yyyy-MM-dd", "");
		else if	(ctx.Match(k, Invk_wmf_dump_date_)) 		Wmf_dump_date_(m.ReadDate("v"));
		else if	(ctx.Match(k, Invk_wmf_dump_done)) 			return Yn.X_to_str(wmf_dump_done);
		else if	(ctx.Match(k, Invk_wmf_dump_done_)) 		wmf_dump_done = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_wmf_dump_status)) 		return String_.new_utf8_(wmf_dump_status);
		else if	(ctx.Match(k, Invk_wmf_dump_status_)) 		wmf_dump_status = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_wiki_dump_date)) 		return DateAdp_.Xto_str_fmt_or(Wiki_dump_date(), "yyyy-MM-dd", "");
		else if	(ctx.Match(k, Invk_wiki_dump_date_)) 		wiki_dump_date = m.ReadDate("v");
		else if	(ctx.Match(k, Invk_wiki_update_needed)) 	return Yn.X_to_str(Wiki_update_needed());
		else if	(ctx.Match(k, Invk_wiki_dump_date_)) 		wiki_dump_date = m.ReadDate("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_wmf_dump_date = "wmf_dump_date", Invk_wmf_dump_date_ = "wmf_dump_date_", Invk_wmf_dump_done = "wmf_dump_done", Invk_wmf_dump_done_ = "wmf_dump_done_"
	, Invk_wmf_dump_status = "wmf_dump_status", Invk_wmf_dump_status_ = "wmf_dump_status_", Invk_wiki_dump_date = "wiki_dump_date", Invk_wiki_dump_date_ = "wiki_dump_date_"
	, Invk_wiki_update_needed = "wiki_update_needed"
	;
} 
