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
package gplx.xowa.xtns.wbases.pfuncs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.core.envs.*;
import gplx.core.primitives.*;
import gplx.xowa.parsers.logs.*; import gplx.xowa.xtns.pfuncs.*; import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*;
import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Wbase_statement_mgr_ {
	public static void Get_wbase_data(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src, Pf_func_base pfunc, boolean mode_is_statements) {
		// init
		byte[] pid_ttl = pfunc.Eval_argx(ctx, src, caller, self);
		Xop_log_property_wkr property_wkr = ctx.Xtn__wikidata__property_wkr();
		long log_time_bgn = 0;
		if (property_wkr != null) {
			log_time_bgn = System_.Ticks();
			if (!property_wkr.Eval_bgn(ctx.Page(), pid_ttl)) return;
		}
		Xoae_app app = ctx.App();
		Xowe_wiki wiki = ctx.Wiki();
		Xoa_ttl ttl = ctx.Page().Ttl();
		Wdata_wiki_mgr wdata_mgr = app.Wiki_mgr().Wdata_mgr(); if (!wdata_mgr.Enabled()) return;

		// get pid_int; EX: {{#property:p123}} -> 123
		int pid_int = Wbase_statement_mgr_.Parse_pid(app.Utl_num_parser(), pid_ttl); // parse "p123" to "123"
		if (pid_int == Wdata_wiki_mgr.Pid_null)	{ // pid_ttl is name; EX: {{#property:road_map}}
			pid_int = wdata_mgr.Pid_mgr.Get_or_null(wiki.Wdata_wiki_lang(), pid_ttl);
			if (pid_int == Wdata_wiki_mgr.Pid_null) {
				Print_self(app.Usr_dlg(), bfr, src, self, "prop_not_found", "prop id not found: ~{0} ~{1} ~{2}", wiki.Domain_str(), ttl.Page_db_as_str(), pid_ttl); 
				return;
			}
		}

		// get doc from args; EX:{{#property:p123}} -> "current_page"; EX:{{#property:p123|of=Earth}} -> "Q2"; {{#property:p123|q=q2}} -> "Q2"; {{#property:p123|from=p321}} -> "Property:P321"
		Wdata_pf_property_data doc_data = Wdata_pf_property_data.Parse(ctx, src, caller, self);
		Wdata_doc doc = Wbase_statement_mgr_.Get_doc(wdata_mgr, wiki, ttl, doc_data);
		if (doc == null) return; // NOTE: some pages will not have a qid; EX: "Some_unknown_page" will not have a qid in wikidata; if no qid, then all {{#property:p###}} will have no prop_val

		// get val based on pid and doc; EX: {{#property:p123|of=Earth}} -> doc=Q2; pid=123 -> "value of p123 in Q2"
		Wbase_claim_grp claim_grp = doc.Claim_list_get(pid_int);
		if (claim_grp == null) return;// NOTE: some props may not exist; EX: "Some_known_page" has a qid of 123 but does not have pid 345 required by {{#property:P345|q=123}}
		wdata_mgr.Resolve_to_bfr(bfr, claim_grp, wiki.Wdata_wiki_lang(), mode_is_statements); // NOTE: was ctx.Page().Lang().Key_bry(), but fails in simplewiki; DATE:2013-12-02
		if (property_wkr != null) property_wkr.Eval_end(ctx.Page(), pid_ttl, log_time_bgn);
	}
	public static int Parse_pid(Gfo_number_parser num_parser, byte[] bry) {
		int bry_len = bry.length;
		if (bry_len < 2) return Wdata_wiki_mgr.Pid_null;	// must have at least 2 chars; p#
		byte b_0 = bry[0];
		if (b_0 != Byte_ascii.Ltr_p && b_0 != Byte_ascii.Ltr_P)	return Wdata_wiki_mgr.Pid_null;
		num_parser.Parse(bry, 1, bry_len);
		return num_parser.Has_err() ? Wdata_wiki_mgr.Pid_null : num_parser.Rv_as_int();
	}
	private static void Print_self(Gfo_usr_dlg usr_dlg, Bry_bfr bfr, byte[] src, Xot_invk self, String warn_cls, String warn_fmt, Object... args) {
		bfr.Add_mid(src, self.Src_bgn(), self.Src_end());
		usr_dlg.Log_many("", warn_cls, warn_fmt, args);
	}
	public static Wdata_doc Get_doc(Wdata_wiki_mgr wdata_mgr, Xowe_wiki wiki, Xoa_ttl ttl, Wdata_pf_property_data data) {
		if		(Bry_.Len_gt_0(data.Q))		return wdata_mgr.Doc_mgr.Get_by_bry_or_null(data.Q);
		else if	(Bry_.Len_gt_0(data.From))	return wdata_mgr.Doc_mgr.Get_by_xid_or_null(data.From);	// NOTE: by_xid b/c Module passes just "p1" not "Property:P1"
		else if (Bry_.Len_gt_0(data.Of)) {
			Xoa_ttl of_ttl = Xoa_ttl.Parse(wiki, data.Of); if (of_ttl == null) return null;
			byte[] qid = wdata_mgr.Qid_mgr.Get_or_null(wiki, of_ttl); if (qid == null) return null;	// NOTE: for now, use wiki.Lang_key(), not page.Lang()
			return wdata_mgr.Doc_mgr.Get_by_bry_or_null(qid);
		}
		else							return wdata_mgr.Doc_mgr.Get_by_ttl_or_null(wiki, ttl);
	}
}
