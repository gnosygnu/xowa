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
package gplx.xowa.xtns.wdatas.pfuncs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.langs.jsons.*; import gplx.core.primitives.*;
import gplx.xowa.parsers.logs.*; import gplx.xowa.xtns.pfuncs.*; import gplx.xowa.xtns.wdatas.core.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Wdata_pf_property extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_property;}
	@Override public Pf_func New(int id, byte[] name) {return new Wdata_pf_property().Name_(name);}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {// {{#property:pNumber|}}
		byte[] id = Eval_argx(ctx, src, caller, self);
		Xop_log_property_wkr property_wkr = ctx.Xtn__wikidata__property_wkr();
		long log_time_bgn = 0;
		if (property_wkr != null) {
			log_time_bgn = Env_.TickCount();
			if (!property_wkr.Eval_bgn(ctx.Cur_page(), id)) return;
		}

		Xoae_app app = ctx.App();
		Wdata_wiki_mgr wdata_mgr = app.Wiki_mgr().Wdata_mgr();
		if (!wdata_mgr.Enabled()) return;
		Xowe_wiki wiki = ctx.Wiki();
		Xoa_ttl ttl = ctx.Cur_page().Ttl();

		Wdata_pf_property_data data = new Wdata_pf_property_data();
		data.Init_by_parse(ctx, src, caller, self, this, id);
		Wdata_doc prop_doc = wdata_mgr.Pages_get(wiki, ttl, data); if (prop_doc == null) return; // NOTE: some pages will not exist in qid; EX: {{#property:P345}} for "Unknown_page" will not even had a qid; if no qid, then no pid
		int pid = data.Id_int();			// check if arg is number; EX: {{#property:p1}}
		if (pid == Wdata_wiki_mgr.Pid_null)	// arg is name of property; EX: {{#property:name}}
			pid = wdata_mgr.Pids__get_by_name(wiki.Wdata_wiki_lang(), data.Id());
		if (pid == Wdata_wiki_mgr.Pid_null) {Print_self(app.Usr_dlg(), bfr, src, self, "prop_not_found", "prop id not found: ~{0} ~{1} ~{2}", wiki.Domain_str(), ttl.Page_db_as_str(), data.Id()); return;}
		Wdata_claim_grp prop_grp = prop_doc.Claim_list_get(pid); if (prop_grp == null) return;// NOTE: some props may not exist; EX: {{#property:P345}} for "Unknown_movie" may have a qid, but doesn't have a defined pid
		wdata_mgr.Resolve_to_bfr(bfr, prop_grp, wiki.Wdata_wiki_lang()); // NOTE: was ctx.Cur_page().Lang().Key_bry(), but fails in simplewiki; DATE:2013-12-02
		if (property_wkr != null)
			property_wkr.Eval_end(ctx.Cur_page(), id, log_time_bgn);
	}
	public static int Parse_pid(Number_parser num_parser, byte[] bry) {
		int bry_len = bry.length;
		if (bry_len < 2) return Wdata_wiki_mgr.Pid_null;	// must have at least 2 chars; p#
		byte b_0 = bry[0];
		if (b_0 != Byte_ascii.Ltr_p && b_0 != Byte_ascii.Ltr_P)	return Wdata_wiki_mgr.Pid_null;
		num_parser.Parse(bry, 1, bry_len);
		return num_parser.Has_err() ? Wdata_wiki_mgr.Pid_null : num_parser.Rv_as_int();
	}
	public static void Print_self(Gfo_usr_dlg usr_dlg, Bry_bfr bfr, byte[] src, Xot_invk self, String warn_cls, String warn_fmt, Object... args) {
		bfr.Add_mid(src, self.Src_bgn(), self.Src_end());
		usr_dlg.Warn_many(GRP_KEY, warn_cls, warn_fmt, args);
	}
	public static void Print_empty(Gfo_usr_dlg usr_dlg, String warn_cls, String warn_fmt, Object... args) {
		usr_dlg.Warn_many(GRP_KEY, warn_cls, warn_fmt, args);
	}
	private static final String GRP_KEY = "xowa.xtns.wdata.property";
}
