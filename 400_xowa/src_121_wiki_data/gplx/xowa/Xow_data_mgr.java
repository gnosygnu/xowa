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
import gplx.lists.*; /*ComparerAble*/ import gplx.xowa.bldrs.imports.ctgs.*;
import gplx.xowa.dbs.*; import gplx.xowa.wikis.*; import gplx.xowa.langs.msgs.*;
public class Xow_data_mgr implements GfoInvkAble {
	public Xow_data_mgr(Xow_wiki wiki) {
		this.wiki = wiki; this.redirect_mgr = wiki.Redirect_mgr();
	}	private Xop_redirect_mgr redirect_mgr;
	private Xoa_url tmp_url = Xoa_url.blank_();
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
	public Xoa_page Get_page(Xoa_ttl ttl, boolean called_from_tmpl) {wiki.App().Url_parser().Parse(tmp_url, ttl.Raw()); return Get_page(tmp_url, ttl, called_from_tmpl, false);}
	public Xoa_page Get_page_from_msg(Xoa_ttl ttl) {wiki.App().Url_parser().Parse(tmp_url, ttl.Raw()); return Get_page(tmp_url, ttl, false, true);}
	public Xoa_page Get_page(Xoa_url url, Xoa_ttl ttl, boolean called_from_tmpl, boolean called_from_msg) {
		Xow_ns ns = ttl.Ns();
		Xoa_page rv = Xoa_page.new_(wiki, ttl); rv.Url_(url);	// NOTE: must update page.Url(); should combine with Xoa_page.new_()
		switch (ns.Id()) {
			case Xow_ns_.Id_special:
				wiki.Special_mgr().Special_gen(url, rv, wiki, ttl);
				return rv;
			case Xow_ns_.Id_mediaWiki:
				if (	!called_from_msg	// if called from msg, fall through to actual data retrieval below, else infinite loop; DATE:2014-05-09
					&&	Xow_page_tid.Identify_by_ttl(ttl.Page_db()) == Xow_page_tid.Tid_wikitext // skip ".js" and ".css" pages in MediaWiki; DATE:2014-06-13
					) {		
					Xol_lang lang = wiki.Lang();
					byte[] msg_key = ttl.Page_db();
					Bry_bfr tmp_bfr = wiki.Utl_bry_bfr_mkr().Get_b512();
					msg_key = lang.Case_mgr().Case_build_1st_lower(tmp_bfr, msg_key, 0, msg_key.length);
					byte[] msg_val = Xol_msg_mgr_.Get_msg_itm(tmp_bfr, wiki, wiki.Lang(), msg_key).Val();	// NOTE: do not change to Get_msg_val; Get_msg_val, also replaces $1 with values, and $1 needs to be preserved for callers;
					rv.Data_raw_(msg_val);
					tmp_bfr.Mkr_rls();
					return rv;
				}
				break;
		}
		return Get_page(rv, ns, ttl, called_from_tmpl, url.Redirect_force());
	}
	public Xoa_page Get_page(Xoa_page rv, Xow_ns ns, Xoa_ttl ttl, boolean called_from_tmpl, boolean redirect_force) {
		int redirects = 0;
		Xodb_page db_page = Xodb_page.tmp_();
		while (true) {
			boolean exists = wiki.Db_mgr().Load_mgr().Load_by_ttl(db_page, ns, ttl.Page_db());
			if (!exists) return rv.Missing_();
			if (wiki.App().Mode() == Xoa_app_.Mode_gui)	// NOTE: must check if gui, else will write during mass build; DATE:2014-05-03
				wiki.App().Gui_wtr().Prog_many(GRP_KEY, "file_load", "loading page for ~{0}", String_.new_utf8_(ttl.Raw()));
			wiki.Db_mgr().Load_mgr().Load_page(db_page, ns, !called_from_tmpl);
			byte[] bry = db_page.Text();
			rv.Data_raw_(bry).Revision_data().Modified_on_(db_page.Modified_on()).Id_(db_page.Id());
			if (redirect_force) return rv;
			Xoa_ttl redirect_ttl = redirect_mgr.Extract_redirect(bry, bry.length);
			if  (	redirect_ttl == null				// not a redirect
				||	redirects++ > 4)					// too many redirects; something went wrong
				break;				
			rv.Redirected_ttls().Add(ttl.Full_url());	// NOTE: must be url_encoded; EX: "en.wikipedia.org/?!" should generate link of "en.wikipedia.org/%3F!?redirect=no"
			if (rv.Redirected_src() == null) rv.Redirected_src_(bry);	// only add src for first redirect; DATE:2014-07-11
			rv.Ttl_(redirect_ttl);
			ns = redirect_ttl.Ns();
			ttl = redirect_ttl;
		}
		return rv;
	}
	public Xoa_page Redirect(Xoa_page page, byte[] page_bry) {
		Xoa_ttl trg_ttl = Xoa_ttl.parse_(wiki, page_bry);
		Xoa_url trg_url = Xoa_url.new_(wiki.Domain_bry(), page_bry);
		page.Ttl_(trg_ttl).Url_(trg_url).Redirected_(true);
		return wiki.Data_mgr().Get_page(page, trg_ttl.Ns(), trg_ttl, false, trg_url.Redirect_force());
	}
	public static final int File_idx_unknown = -1;
	static final String GRP_KEY = "xowa.wiki.data";
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_create_enabled_))				wiki.Db_mgr().Save_mgr().Create_enabled_(m.ReadYn("v"));
		else if	(ctx.Match(k, Invk_update_modified_on_enabled_))	wiki.Db_mgr().Save_mgr().Update_modified_on_enabled_(m.ReadYn("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_create_enabled_ = "create_enabled_", Invk_update_modified_on_enabled_ = "update_modified_on_enabled_";
}
