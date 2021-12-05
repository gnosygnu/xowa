/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.wbases;

import gplx.objects.primitives.BoolUtl;
import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.GfoMsg;
import gplx.Gfo_evt_itm;
import gplx.Gfo_evt_mgr;
import gplx.Gfo_evt_mgr_;
import gplx.Gfo_invk;
import gplx.Gfo_invk_;
import gplx.GfsCtx;
import gplx.String_;
import gplx.Yn;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_kv;
import gplx.langs.jsons.Json_parser;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.apps.apis.xowa.html.Xoapi_toggle_mgr;
import gplx.xowa.apps.apis.xowa.xtns.Xoapi_wikibase;
import gplx.xowa.htmls.Xoh_consts;
import gplx.xowa.langs.Xol_lang_itm;
import gplx.xowa.langs.msgs.Xol_msg_itm_;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.logs.Xop_log_property_wkr;
import gplx.xowa.users.Xoue_user;
import gplx.xowa.wikis.domains.Xow_domain_itm;
import gplx.xowa.wikis.domains.Xow_domain_tid_;
import gplx.xowa.wikis.nss.Xow_ns_;
import gplx.xowa.xtns.wbases.claims.Wbase_claim_grp;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_rank_;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_value_type_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_base;
import gplx.xowa.xtns.wbases.hwtrs.Wdata_hwtr_mgr;
import gplx.xowa.xtns.wbases.hwtrs.Wdata_hwtr_msgs;
import gplx.xowa.xtns.wbases.hwtrs.Wdata_lbl_wkr_wiki;
import gplx.xowa.xtns.wbases.parsers.Wdata_doc_parser;
import gplx.xowa.xtns.wbases.parsers.Wdata_doc_parser_v1;
import gplx.xowa.xtns.wbases.parsers.Wdata_doc_parser_v2;
import gplx.xowa.xtns.wbases.stores.Wbase_doc_mgr;
import gplx.xowa.xtns.wbases.stores.Wbase_pid_mgr;
import gplx.xowa.xtns.wbases.stores.Wbase_prop_mgr;
import gplx.xowa.xtns.wbases.stores.Wbase_prop_mgr_loader_;
import gplx.xowa.xtns.wbases.stores.Wbase_qid_mgr;

public class Wdata_wiki_mgr implements Gfo_evt_itm, Gfo_invk {
	private final Xoae_app app;
	private final Wdata_prop_val_visitor prop_val_visitor;
	private final Wdata_doc_parser wdoc_parser_v1 = new Wdata_doc_parser_v1(), wdoc_parser_v2 = new Wdata_doc_parser_v2();
	private final Object thread_lock = new Object();		
	private final Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(32);
	public Wdata_wiki_mgr(Xoae_app app) {
		this.app = app;
		this.evt_mgr = new Gfo_evt_mgr(this);
		this.Qid_mgr = new Wbase_qid_mgr(this);
		this.Pid_mgr = new Wbase_pid_mgr(this);	
		this.Doc_mgr = new Wbase_doc_mgr(this, this.Qid_mgr);
		this.prop_mgr = new Wbase_prop_mgr(Wbase_prop_mgr_loader_.New_db(this));
		this.prop_val_visitor = new Wdata_prop_val_visitor(app, this);
		this.Enabled_(true);
	}
	public Gfo_evt_mgr Evt_mgr() {return evt_mgr;} private final Gfo_evt_mgr evt_mgr;
	public final Wbase_qid_mgr		Qid_mgr;
	public final Wbase_pid_mgr		Pid_mgr;
	public final Wbase_doc_mgr		Doc_mgr;
	public Wbase_prop_mgr				Prop_mgr() {return prop_mgr;} private final Wbase_prop_mgr prop_mgr;
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
	public void Init_by_app() {}
	public Wdata_doc_parser Wdoc_parser(Json_doc jdoc) {
		Json_kv itm_0 = Json_kv.Cast(jdoc.Root_nde().Get_at(0));										// get 1st node
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
		byte[] qid = this.Qid_mgr.Get_qid_or_null(domain.Abrv_wm(), page_ttl); if (qid == null) return or;
		Wdata_doc wdoc = Doc_mgr.Get_by_loose_id_or_null(qid); if (wdoc == null) return or;
		Wbase_claim_grp claim_grp = wdoc.Get_claim_grp_or_null(pid);
		if (claim_grp == null || claim_grp.Len() == 0) return or;
		Wbase_claim_base claim_itm = claim_grp.Get_at(0);
		Resolve_claim(tmp_bfr, domain, claim_itm);
		return tmp_bfr.To_bry_and_clear();
	}
	public void Resolve_claim(Bry_bfr rv, Xow_domain_itm domain, Wbase_claim_base claim_itm) {
		synchronized (thread_lock) {	// LOCK:must synchronized b/c prop_val_visitor has member bfr which can get overwritten; DATE:2016-07-06
			if (hwtr_mgr == null) Hwtr_mgr_assert();
			prop_val_visitor.Init(rv, hwtr_mgr.Msgs(), domain.Lang_orig_key(), BoolUtl.N);
			claim_itm.Welcome(prop_val_visitor);
		}
	}
	public void Resolve_to_bfr(Bry_bfr bfr, Xowe_wiki wiki, Wbase_claim_grp prop_grp, byte[] lang_key, boolean mode_is_statements) {
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
			switch (selected.Snak_tid()) { // SEE:NOTE:novalue/somevalue
				case Wbase_claim_value_type_.Tid__novalue:
					bfr.Add(wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_xowa_wikidata_novalue));
					break;
				case Wbase_claim_value_type_.Tid__somevalue:
					bfr.Add(wiki.Msg_mgr().Val_by_id(Xol_msg_itm_.Id_xowa_wikidata_somevalue));
					break;
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
		Wdata_doc wdoc = Doc_mgr.Get_by_exact_id_or_null(page.Ttl().Full_db());
		if (wdoc == null) return Bry_.Empty;
		return hwtr_mgr.Popup(wdoc);
	}
	public void Write_json_as_html(Bry_bfr bfr, Xoa_ttl page_ttl, byte[] data_raw) {
		Hwtr_mgr_assert();
		Wdata_doc wdoc = Doc_mgr.Get_by_exact_id_or_null(page_ttl.Full_db());
		if (wdoc == null) return;
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
	public static final byte[] Ns_property_name_bry = Bry_.new_a7(Ns_property_name);
	public static final int Ns_lexeme = 146;
	public static final String Ns_lexeme_name = "Lexeme";
	public static final byte[] Ns_lexeme_name_bry = Bry_.new_a7(Ns_lexeme_name);

	public static final byte[] Html_json_id = Bry_.new_a7("xowa-wikidata-json");
	public static boolean Wiki_page_is_json(int wiki_tid, int ns_id) {
		switch (wiki_tid) {
			case Xow_domain_tid_.Tid__wikidata:
				switch (ns_id) {
					case Xow_ns_.Tid__main:
					case Wdata_wiki_mgr.Ns_property:
					case Wdata_wiki_mgr.Ns_lexeme:
						return true;
					default:
						return false;
				}
			case Xow_domain_tid_.Tid__home:
				if (ns_id == gplx.xowa.xtns.wbases.Wdata_wiki_mgr.Ns_property)
					return true;
				break;
		}
		return false;
	}
	public static void Log_missing_qid(Xop_ctx ctx, String type, byte[] id) {
		if (id == null) id = Bry_.Empty;
		ctx.Wiki().Appe().Usr_dlg().Log_many("", "", "Unknown id in wikidata; type=~{0} id=~{1} page=~{2}", type, id, ctx.Page().Url_bry_safe());
	}
}
/*
NOTE:novalue/somevalue
Rough approximation of wikibase logic which is more involved with its different SnakFormatters
* https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/lib/includes/Formatters/OutputFormatSnakFormatterFactory.php: formatter factory; note lines for somevalue / novalue
* https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/lib/includes/Formatters/MessageSnakFormatter.php: formatter definition
* https://github.com/wikimedia/mediawiki-extensions-Wikibase/blob/master/repo/i18n/en.json: message definitions
*/
