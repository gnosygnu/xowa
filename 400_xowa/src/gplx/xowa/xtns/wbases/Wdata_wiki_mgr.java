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
package gplx.xowa.xtns.wbases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
import gplx.langs.jsons.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.langs.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.htmls.*; import gplx.xowa.parsers.logs.*; import gplx.xowa.apps.apis.xowa.xtns.*; import gplx.xowa.apps.apis.xowa.html.*; import gplx.xowa.users.*;
import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.enums.*; import gplx.xowa.xtns.wbases.claims.itms.*; import gplx.xowa.xtns.wbases.parsers.*; import gplx.xowa.xtns.wbases.pfuncs.*; import gplx.xowa.xtns.wbases.hwtrs.*;
import gplx.xowa.parsers.*;
public class Wdata_wiki_mgr implements Gfo_evt_itm, Gfo_invk {
	private final    Xoae_app app;
	private final    Wdata_prop_val_visitor prop_val_visitor;
	private final    Wdata_doc_parser wdoc_parser_v1 = new Wdata_doc_parser_v1(), wdoc_parser_v2 = new Wdata_doc_parser_v2();
	private final    Object thread_lock = new Object();		
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(32);		
	public Wdata_wiki_mgr(Xoae_app app) {
		this.app = app;
		this.evt_mgr = new Gfo_evt_mgr(this);
		this.Qid_mgr = new Wbase_qid_mgr(this);
		this.Pid_mgr = new Wbase_pid_mgr(this);
		this.Doc_mgr = new Wbase_doc_mgr(app, this, this.Qid_mgr);
		this.prop_mgr = new Wbase_prop_mgr(Wbase_prop_mgr_loader_.New_db(this));
		this.prop_val_visitor = new Wdata_prop_val_visitor(app, this);
		this.Enabled_(true);
	}
	public Gfo_evt_mgr Evt_mgr() {return evt_mgr;} private final    Gfo_evt_mgr evt_mgr;
	public final    Wbase_qid_mgr		Qid_mgr;
	public final    Wbase_pid_mgr		Pid_mgr;
	public final    Wbase_doc_mgr		Doc_mgr;
	public Wbase_prop_mgr				Prop_mgr() {return prop_mgr;} private final    Wbase_prop_mgr prop_mgr;
	public boolean Enabled() {return enabled;} private boolean enabled;
	public void Enabled_(boolean v) {
		this.enabled = v;
		Qid_mgr.Enabled_(v);
		Pid_mgr.Enabled_(v);
		Doc_mgr.Enabled_(v);
	}
	public byte[] Domain() {return domain;} public void Domain_(byte[] v) {domain = v;} private byte[] domain = Bry_.new_a7("www.wikidata.org");
	public Wdata_hwtr_mgr Hwtr_mgr() {
		if (hwtr_mgr == null)
			Hwtr_mgr_assert();
		return hwtr_mgr;
	}	private Wdata_hwtr_mgr hwtr_mgr;
	public Xowe_wiki Wdata_wiki() {
		if (wdata_wiki == null) {
			synchronized (thread_lock) {	// LOCK:must synchronized b/c multiple threads may init wdata_mgr at same time;
				Xowe_wiki tmp_wdata_wiki = app.Wiki_mgr().Get_by_or_make(domain).Init_assert();
				if (wdata_wiki == null)	// synchronized is not around "if (wdata_wiki == null)", so multiple threads may try to set; only set if null; DATE:2016-09-12
					wdata_wiki = tmp_wdata_wiki;
			}
		}
		return wdata_wiki;
	}	private Xowe_wiki wdata_wiki;
	public Json_parser Jdoc_parser() {return jdoc_parser;} private Json_parser jdoc_parser = new Json_parser();
	public void Init_by_app() {}
	public Wdata_doc_parser Wdoc_parser(Json_doc jdoc) {
		Json_kv itm_0 = Json_kv.cast(jdoc.Root_nde().Get_at(0));										// get 1st node
		return Bry_.Eq(itm_0.Key().Data_bry(), Wdata_doc_parser_v2.Bry_type) 
			|| Bry_.Eq(itm_0.Key().Data_bry(), Wdata_doc_parser_v2.Bry_id) 
			? wdoc_parser_v2 : wdoc_parser_v1;	// if "type", must be v2
	}
	public Xop_log_property_wkr Property_wkr() {return property_wkr;} private Xop_log_property_wkr property_wkr;
	public void Clear() {
		synchronized (thread_lock) {	// LOCK:app-level
			Qid_mgr.Clear();
			Pid_mgr.Clear();
			Doc_mgr.Clear();
		}
	}
	public byte[] Get_claim_or(Xow_domain_itm domain, Xoa_ttl page_ttl, int pid, byte[] or) {
		byte[] qid = this.Qid_mgr.Get_or_null(domain.Abrv_wm(), page_ttl); if (qid == null) return or;
		Wdata_doc wdoc = Doc_mgr.Get_by_bry_or_null(qid); if (wdoc == null) return or;
		Wbase_claim_grp claim_grp = wdoc.Claim_list_get(pid); if (claim_grp == null || claim_grp.Len() == 0) return or;
		Wbase_claim_base claim_itm = claim_grp.Get_at(0);
		Resolve_claim(tmp_bfr, domain, claim_itm);
		return tmp_bfr.To_bry_and_clear();
	}
	public void Resolve_claim(Bry_bfr rv, Xow_domain_itm domain, Wbase_claim_base claim_itm) {
		synchronized (thread_lock) {	// LOCK:must synchronized b/c prop_val_visitor has member bfr which can get overwritten; DATE:2016-07-06
			if (hwtr_mgr == null) Hwtr_mgr_assert();
			prop_val_visitor.Init(rv, hwtr_mgr.Msgs(), domain.Lang_orig_key(), Bool_.N);
			claim_itm.Welcome(prop_val_visitor);
		}
	}
	public void Resolve_to_bfr(Bry_bfr bfr, Wbase_claim_grp prop_grp, byte[] lang_key, boolean mode_is_statements) {
		synchronized (thread_lock) {	// LOCK:must synchronized b/c prop_val_visitor has member bfr which can get overwritten; DATE:2016-07-06
			if (hwtr_mgr == null) Hwtr_mgr_assert();
			int len = prop_grp.Len();
			Wbase_claim_base selected = null;
			for (int i = 0; i < len; i++) {								// NOTE: multiple props possible; EX: {{#property:P1082}}; PAGE:en.w:Earth DATE:2015-08-02
				Wbase_claim_base prop = prop_grp.Get_at(i);
				if (selected == null) selected = prop;					// if selected not set, set it; will always set to 1st prop
				if (prop.Rank_tid() == Wbase_claim_rank_.Tid__preferred) {	// if prop is preferred, select it and exit;
					selected = prop;
					break;
				}
			}
			switch (selected.Snak_tid()) {
				case Wbase_claim_value_type_.Tid__novalue	: bfr.Add(Wbase_claim_value_type_.Itm__novalue.Key_bry()); break;
				case Wbase_claim_value_type_.Tid__somevalue	: bfr.Add(Wbase_claim_value_type_.Itm__somevalue.Key_bry()); break;
				default: {
					prop_val_visitor.Init(bfr, hwtr_mgr.Msgs(), lang_key, mode_is_statements);
					selected.Welcome(prop_val_visitor);
					break;
				}
			}
		}
	}
	public byte[] Popup_text(Xoae_page page) {
		Hwtr_mgr_assert();
		Wdata_doc wdoc = Doc_mgr.Get_by_bry_or_null(page.Ttl().Full_db());			 
		return hwtr_mgr.Popup(wdoc);
	}
	public void Write_json_as_html(Bry_bfr bfr, byte[] page_full_db, byte[] data_raw) {
		Hwtr_mgr_assert();
		Wdata_doc wdoc = Doc_mgr.Get_by_bry_or_null(page_full_db);
		hwtr_mgr.Init_by_wdoc(wdoc);
		bfr.Add(hwtr_mgr.Write(wdoc));
	}
	private void Hwtr_mgr_assert() {
		if (hwtr_mgr != null) return;
		Xoapi_toggle_mgr toggle_mgr = app.Api_root().Html().Page().Toggle_mgr();
		Xoapi_wikibase wikibase_api = app.Api_root().Xtns().Wikibase();
		hwtr_mgr = new Wdata_hwtr_mgr();
		hwtr_mgr.Init_by_ctor(wikibase_api, this, new Wdata_lbl_wkr_wiki(wikibase_api, this), gplx.langs.htmls.encoders.Gfo_url_encoder_.Href, toggle_mgr, app.Usere().Wiki().Xwiki_mgr());
		this.Hwtr_msgs_make();
		Gfo_evt_mgr_.Sub_same_many(app.Usere(), this, Xoue_user.Evt_lang_changed);
	}
	private void Hwtr_msgs_make() {
		// if (!app.Wiki_mgr().Wiki_regy().Has(Xow_domain_itm_.Bry__wikidata)) return; // DELETE: don't know why guard is needed; breaks test; DATE:2016-10-20
		Xol_lang_itm new_lang = app.Usere().Lang();
		Xowe_wiki cur_wiki = this.Wdata_wiki();			
		cur_wiki.Xtn_mgr().Xtn_wikibase().Load_msgs(cur_wiki, new_lang);
		Wdata_hwtr_msgs hwtr_msgs = Wdata_hwtr_msgs.new_(cur_wiki.Msg_mgr());
		hwtr_mgr.Init_by_lang(new_lang, hwtr_msgs);
	}
	public static void Write_json_as_html(Json_parser jdoc_parser, Bry_bfr bfr, byte[] data_raw) {
		bfr.Add(Xoh_consts.Span_bgn_open).Add(Xoh_consts.Id_atr).Add(Html_json_id).Add(Xoh_consts.__end_quote);	// <span id="xowa-wikidata-json">
		Json_doc json = jdoc_parser.Parse(data_raw);
		json.Root_nde().Print_as_json(bfr, 0);
		bfr.Add(Xoh_consts.Span_end);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled))					return Yn.To_str(enabled);
		else if	(ctx.Match(k, Invk_enabled_))					enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_domain))						return String_.new_u8(domain);
		else if	(ctx.Match(k, Invk_domain_))					domain = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_property_wkr))				return m.ReadYnOrY("v") ? Property_wkr_or_new() : Gfo_invk_.Noop;
		else if	(ctx.Match(k, Xoue_user.Evt_lang_changed))		Hwtr_msgs_make();
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk_enabled = "enabled", Invk_enabled_ = "enabled_", Invk_domain = "domain", Invk_domain_ = "domain_", Invk_property_wkr = "property_wkr";
	public Xop_log_property_wkr Property_wkr_or_new() {
		if (property_wkr == null) property_wkr = app.Log_mgr().Make_wkr_property();
		return property_wkr;
	}
	public static final int Ns_property = 120;
	public static final String Ns_property_name = "Property";
	public static final    byte[] Ns_property_name_bry = Bry_.new_a7(Ns_property_name);
	public static final    byte[] Bry_q = Bry_.new_a7("q");
	public static final    byte[]
	  Ttl_prefix_qid_bry_db		= Bry_.new_a7("q")	// NOTE: for historical reasons this is standardized as lowercase q not Q; DATE:2015-06-12
	, Ttl_prefix_qid_bry_gui	= Bry_.new_a7("Q")	// NOTE: use uppercase Q for writing html; DATE:2015-06-12
	, Ttl_prefix_pid_bry		= Bry_.new_a7("Property:P")
	;
	public static final int Pid_null = -1;
	public static final    byte[] Html_json_id = Bry_.new_a7("xowa-wikidata-json");
	public static boolean Wiki_page_is_json(int wiki_tid, int ns_id) {
		switch (wiki_tid) {
			case Xow_domain_tid_.Tid__wikidata:
				if (ns_id == Xow_ns_.Tid__main || ns_id == gplx.xowa.xtns.wbases.Wdata_wiki_mgr.Ns_property)
					return true;
				break;
			case Xow_domain_tid_.Tid__home:
				if (ns_id == gplx.xowa.xtns.wbases.Wdata_wiki_mgr.Ns_property)
					return true;
				break;
		}
		return false;
	}
	public static void Log_missing_qid(Xop_ctx ctx, byte[] qid) {
		ctx.Wiki().Appe().Usr_dlg().Log_many("", "", "qid not found in wikidata; qid=~{0} page=~{1}", String_.new_u8(qid), String_.new_u8(ctx.Page().Ttl().Page_db()));
	}
}
