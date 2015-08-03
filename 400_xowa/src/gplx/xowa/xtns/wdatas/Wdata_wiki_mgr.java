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
package gplx.xowa.xtns.wdatas; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
import gplx.core.json.*; import gplx.xowa.wikis.*; import gplx.xowa.html.*; import gplx.xowa.parsers.logs.*; import gplx.xowa.apis.xowa.xtns.*; import gplx.xowa.apis.xowa.html.*; import gplx.xowa.users.*;
import gplx.xowa.xtns.wdatas.parsers.*; import gplx.xowa.xtns.wdatas.pfuncs.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.xtns.wdatas.hwtrs.*;
public class Wdata_wiki_mgr implements GfoEvObj, GfoInvkAble {
	private final Wdata_doc_parser wdoc_parser_v1, wdoc_parser_v2;
	private final Wdata_prop_val_visitor prop_val_visitor;
	private final Hash_adp_bry qids_cache = Hash_adp_bry.cs(), pids_cache = Hash_adp_bry.cs();
	private Int_obj_ref wiki_tid_ref = Int_obj_ref.zero_();
	private Wdata_hwtr_mgr hwtr_mgr;
	public Wdata_wiki_mgr(Xoae_app app) {
		this.app = app;
		this.evMgr = GfoEvMgr.new_(this);
		wdoc_parser_v1 = new Wdata_doc_parser_v1();
		wdoc_parser_v2 = new Wdata_doc_parser_v2();
		doc_cache = app.Cache_mgr().Doc_cache();
		prop_val_visitor = new Wdata_prop_val_visitor(app, this);
	}	private Xoae_app app; gplx.xowa.apps.caches.Wdata_doc_cache doc_cache;
	public GfoEvMgr EvMgr() {return evMgr;} private GfoEvMgr evMgr;
	public boolean Enabled() {return enabled;} public void Enabled_(boolean v) {enabled = v;} private boolean enabled = true;
	public byte[] Domain() {return domain;} public void Domain_(byte[] v) {domain = v;} private byte[] domain = Bry_.new_a7("www.wikidata.org");
	public Xowe_wiki Wdata_wiki() {if (wdata_wiki == null) wdata_wiki = app.Wiki_mgr().Get_by_key_or_make(domain).Init_assert(); return wdata_wiki;} private Xowe_wiki wdata_wiki;
	public Json_parser Jdoc_parser() {return jdoc_parser;} private Json_parser jdoc_parser = new Json_parser();
	public void Init_by_app() {}
	public Wdata_doc_parser Wdoc_parser(Json_doc jdoc) {
		Json_kv itm_0 = Json_kv.cast_(jdoc.Root().Get_at(0));										// get 1st node
		return Bry_.Eq(itm_0.Key().Data_bry(), Wdata_doc_parser_v2.Bry_type) 
			|| Bry_.Eq(itm_0.Key().Data_bry(), Wdata_doc_parser_v2.Bry_id) 
			? wdoc_parser_v2 : wdoc_parser_v1;	// if "type", must be v2
	}
	public Xop_log_property_wkr Property_wkr() {return property_wkr;} private Xop_log_property_wkr property_wkr;
	public Int_obj_ref Tmp_prop_ref() {return tmp_prop_ref;} Int_obj_ref tmp_prop_ref = Int_obj_ref.zero_();
	public void Clear() {
		qids_cache.Clear();
		pids_cache.Clear();
		doc_cache.Clear();
	}
	public void Qids_add(Bry_bfr bfr, byte[] lang_key, int wiki_tid, byte[] ns_num, byte[] ttl, byte[] qid) {
		Xow_wiki_alias.Build_alias_by_lang_tid(bfr, lang_key, wiki_tid_ref.Val_(wiki_tid));
		byte[] qids_key = bfr.Add_byte(Byte_ascii.Pipe).Add(ns_num).Add_byte(Byte_ascii.Pipe).Add(ttl).Xto_bry();
		qids_cache.Add(qids_key, qid);
	}
	public byte[] Qids_get(Xowe_wiki wiki, Xoa_ttl ttl) {return Qids_get(wiki.Wdata_wiki_abrv(), ttl);}
	private byte[] Qids_get(byte[] wiki_abrv, Xoa_ttl ttl) {
		if (!enabled) return null;
		if (Bry_.Len_eq_0(wiki_abrv)) return null;	// "other" wikis will never call wikidata
		byte[] cache_key = Bry_.Add(wiki_abrv, Byte_ascii.Pipe_bry, ttl.Ns().Num_bry(), Byte_ascii.Pipe_bry, ttl.Page_db());	// EX:enwiki|014|Earth
		byte[] rv = (byte[])qids_cache.Get_by(cache_key);
		if (rv == null) {	// not in cache; load from db
			rv = this.Wdata_wiki().Db_mgr().Load_mgr().Load_qid(wiki_abrv, ttl.Ns().Num_bry(), ttl.Page_db()); 
			byte[] add_val = rv == null ? Bry_.Empty : rv;	// JAVA: hashtable does not accept null as value; use Bry_.Empty
			qids_cache.Add(cache_key, add_val);				// NOTE: if not in db, will insert a null value for cache_key; DATE:2014-07-23
		}
		return Bry_.Len_eq_0(rv) ? null : rv;				// JAVA: convert Bry_.Empty to null
	}
	public Int_obj_val Pids_add(byte[] pids_key, int pid_id) {Int_obj_val rv = Int_obj_val.new_(pid_id); pids_cache.Add(pids_key, rv); return rv;}
	public int Pids_get(byte[] lang_key, byte[] pid_name) {
		if (!enabled) return Pid_null;
		byte[] pids_key = Bry_.Add(lang_key, Xoa_consts.Pipe_bry, pid_name);
		Int_obj_val rv = (Int_obj_val)pids_cache.Get_by(pids_key);
		if (rv == null) {
			int pid_id = this.Wdata_wiki().Db_mgr().Load_mgr().Load_pid(lang_key, pid_name); if (pid_id == Pid_null) return Pid_null;
			rv = Pids_add(pids_key, pid_id);
		}
		return rv.Val();
	}
	public void Pages_add(byte[] qid, Wdata_doc page) {
		doc_cache.Add(qid, page);
	}
	public Wdata_doc Pages_get(Xowe_wiki wiki, Xoa_ttl ttl, Wdata_pf_property_data data) {
		if		(data.Q()	!= null)	return Pages_get(data.Q());
		else if (data.Of()	!= null) {
			Xoa_ttl of_ttl = Xoa_ttl.parse_(wiki, data.Of()); if (of_ttl == null) return null;
			byte[] qid = Qids_get(wiki, of_ttl); if (qid == null) return null;	// NOTE: for now, use wiki.Lang_key(), not page.Lang()
			return Pages_get(qid);
		}
		else							return Pages_get(wiki, ttl);
	}
	public Wdata_doc Pages_get(Xowe_wiki wiki, Xoa_ttl ttl) {byte[] qid_bry = Qids_get(wiki, ttl); return qid_bry == null? null : Pages_get(qid_bry);}
	public Wdata_doc Pages_get_by_ttl_name(byte[] ttl_bry) {
		if (Byte_ascii.Case_lower(ttl_bry[0]) == Byte_ascii.Ltr_p)	// if ttl starts with "p", change title to "Property:" ns; DATE:2014-02-18
			ttl_bry = Bry_.Add_w_dlm(Byte_ascii.Colon, Wdata_wiki_mgr.Ns_property_name_bry, ttl_bry);
		return Pages_get(ttl_bry);
	}
	public Wdata_doc Pages_get(byte[] qid_bry) {
		qid_bry = Get_low_qid(qid_bry);
		Wdata_doc rv = doc_cache.Get_or_null(qid_bry);
		if (rv == null) {
			Json_doc jdoc = Get_json(qid_bry); if (jdoc == null) return null;	// page not found
			rv = new Wdata_doc(qid_bry, this, jdoc);
			doc_cache.Add(qid_bry, rv);
		}
		return rv;
	}
	public static byte[] Get_low_qid(byte[] bry) {	// HACK: wdata currently does not differentiate between "Vandalism" and "Wikipedia:Vandalism", combining both into "Vandalism:q4664011|q6160"; get lowest qid
		int bry_len = bry.length;
		int pipe_pos = Bry_finder.Find_fwd(bry, Byte_ascii.Pipe, 0, bry_len);
		if (pipe_pos == Bry_.NotFound) return bry;
		byte[][] qids = Bry_.Split(bry, Byte_ascii.Pipe);
		int qids_len = qids.length;
		int qid_min = Int_.MaxValue;
		int qid_idx = 0;
		for (int i = 0; i < qids_len; i++) {
			byte[] qid = qids[i];
			int qid_len = qid.length;
			int qid_val = Bry_.To_int_or(qid, 1, qid_len, Int_.MaxValue);
			if (qid_val < qid_min) {
				qid_min = qid_val;
				qid_idx = i;
			}
		}
		return qids[qid_idx];
	}
	public void Resolve_to_bfr(Bry_bfr bfr, Wdata_claim_grp prop_grp, byte[] lang_key) {
		int len = prop_grp.Len();
		Wdata_claim_itm_core selected = null;
		for (int i = 0; i < len; i++) {								// NOTE: multiple props possible; EX: {{#property:P1082}}; PAGE:en.w:Earth DATE:2015-08-02
			Wdata_claim_itm_core prop = prop_grp.Get_at(i);
			if (selected == null) selected = prop;					// if selected not set, set it; will always set to 1st prop
			if (prop.Rank_tid() == Wdata_dict_rank.Tid_preferred) {	// if prop is preferred, select it and exit;
				selected = prop;
				break;
			}
		}
		switch (selected.Snak_tid()) {
			case Wdata_dict_snak_tid.Tid_novalue	: bfr.Add(Wdata_dict_snak_tid.Bry_novalue); break;
			case Wdata_dict_snak_tid.Tid_somevalue	: bfr.Add(Wdata_dict_snak_tid.Bry_somevalue); break;
			default: {
				prop_val_visitor.Init(bfr, lang_key);
				selected.Welcome(prop_val_visitor);
				break;
			}
		}
	}
	public static final byte[] Bry_q = Bry_.new_a7("q");
	public byte[] Popup_text(Xoae_page page) {
		Hwtr_mgr_assert();
		Wdata_doc wdoc = this.Pages_get_by_ttl_name(page.Ttl().Page_db());			 
		return hwtr_mgr.Popup(wdoc);
	}
	public void Write_json_as_html(Bry_bfr bfr, byte[] ttl_bry, byte[] data_raw) {
		Hwtr_mgr_assert();
		Wdata_doc wdoc = this.Pages_get_by_ttl_name(ttl_bry);
		hwtr_mgr.Init_by_wdoc(wdoc);
		bfr.Add(hwtr_mgr.Write(wdoc));
	}
	private void Hwtr_mgr_assert() {
		if (hwtr_mgr != null) return;
		Xoapi_toggle_mgr toggle_mgr = app.Api_root().Html().Page().Toggle_mgr();
		Xoapi_wikibase wikibase_api = app.Api_root().Xtns().Wikibase();
		hwtr_mgr = new Wdata_hwtr_mgr();
		hwtr_mgr.Init_by_ctor(wikibase_api, new Wdata_lbl_wkr_wiki(wikibase_api, this), Xoa_app_.Utl__encoder_mgr().Href(), toggle_mgr, app.Usere().Wiki().Xwiki_mgr());
		this.Hwtr_msgs_make();
		GfoEvMgr_.SubSame_many(app.Usere(), this, Xoue_user.Evt_lang_changed);
	}
	private void Hwtr_msgs_make() {
		if (!app.Wiki_mgr().Wiki_regy().Has(Xow_domain_.Domain_bry_wikidata)) return;
		Xol_lang new_lang = app.Usere().Lang();
		Xowe_wiki cur_wiki = this.Wdata_wiki();			
		cur_wiki.Xtn_mgr().Xtn_wikibase().Load_msgs(cur_wiki, new_lang);
		Wdata_hwtr_msgs hwtr_msgs = Wdata_hwtr_msgs.new_(cur_wiki.Msg_mgr());
		hwtr_mgr.Init_by_lang(hwtr_msgs);
	}
	public static void Write_json_as_html(Json_parser jdoc_parser, Bry_bfr bfr, byte[] data_raw) {
		bfr.Add(Xoh_consts.Span_bgn_open).Add(Xoh_consts.Id_atr).Add(Html_json_id).Add(Xoh_consts.__end_quote);	// <span id="xowa-wikidata-json">
		Json_doc json = jdoc_parser.Parse(data_raw);
		json.Root().Print_as_json(bfr, 0);
		bfr.Add(Xoh_consts.Span_end);
	}
	private Json_doc Get_json(byte[] qid_bry) {
		if (!enabled) return null;
		Xoa_ttl qid_ttl = Xoa_ttl.parse_(this.Wdata_wiki(), qid_bry); if (qid_ttl == null) {app.Usr_dlg().Warn_many("", "", "invalid qid for ttl: qid=~{0}", String_.new_u8(qid_bry)); return null;}
		Xoae_page qid_page = this.Wdata_wiki().Data_mgr().Get_page(qid_ttl, false); if (qid_page.Missing()) return null;
		byte[] src = qid_page.Data_raw();
		return jdoc_parser.Parse(src);
	}	
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled))					return Yn.Xto_str(enabled);
		else if	(ctx.Match(k, Invk_enabled_))					enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_domain))						return String_.new_u8(domain);
		else if	(ctx.Match(k, Invk_domain_))					domain = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_property_wkr))				return m.ReadYnOrY("v") ? Property_wkr_or_new() : GfoInvkAble_.Null;
		else if	(ctx.Match(k, Xoue_user.Evt_lang_changed))		Hwtr_msgs_make();
		else	return GfoInvkAble_.Rv_unhandled;
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
	public static final byte[]
	  Ttl_prefix_qid_bry_db		= Bry_.new_a7("q")	// NOTE: for historical reasons this is standardized as lowercase q not Q; DATE:2015-06-12
	, Ttl_prefix_qid_bry_gui	= Bry_.new_a7("Q")	// NOTE: use uppercase Q for writing html; DATE:2015-06-12
	, Ttl_prefix_pid_bry		= Bry_.new_a7("Property:P")
	;
	public static final int Pid_null = -1;
	public static final byte[] Html_json_id = Bry_.new_a7("xowa-wikidata-json");
	public static boolean Wiki_page_is_json(int wiki_tid, int ns_id) {
		switch (wiki_tid) {
			case Xow_domain_type_.Tid_wikidata:
				if (ns_id == Xow_ns_.Id_main || ns_id == gplx.xowa.xtns.wdatas.Wdata_wiki_mgr.Ns_property)
					return true;
				break;
			case Xow_domain_type_.Tid_home:
				if (ns_id == gplx.xowa.xtns.wdatas.Wdata_wiki_mgr.Ns_property)
					return true;
				break;
		}
		return false;
	}
	public static void Log_missing_qid(Xop_ctx ctx, byte[] qid) {
		ctx.Wiki().Appe().Usr_dlg().Log_many("", "", "qid not found in wikidata; qid=~{0} page=~{1}", String_.new_u8(qid), String_.new_u8(ctx.Cur_page().Ttl().Page_db()));
	}
}
