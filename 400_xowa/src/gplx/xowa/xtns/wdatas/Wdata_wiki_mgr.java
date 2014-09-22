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
import gplx.json.*; import gplx.xowa.wikis.*;
import gplx.xowa.html.*;
import gplx.xowa.parsers.logs.*; import gplx.xowa.xtns.wdatas.parsers.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.xtns.wdatas.pfuncs.*;
public class Wdata_wiki_mgr implements GfoInvkAble {
	private Wdata_doc_parser wdoc_parser_v1, wdoc_parser_v2;
	public Wdata_wiki_mgr(Xoa_app app) {
		this.app = app;
		wdoc_parser_v1 = new Wdata_doc_parser_v1();
		wdoc_parser_v2 = new Wdata_doc_parser_v2();
		doc_cache = app.Cache_mgr().Doc_cache();
	}	private Xoa_app app; gplx.xowa.apps.caches.Wdata_doc_cache doc_cache;
	public boolean Enabled() {return enabled;} public void Enabled_(boolean v) {enabled = v;} private boolean enabled = true;
	public byte[] Domain() {return domain;} public void Domain_(byte[] v) {domain = v;} private byte[] domain = Bry_.new_ascii_("www.wikidata.org");
	public Xow_wiki Wdata_wiki() {if (wdata_wiki == null) wdata_wiki = app.Wiki_mgr().Get_by_key_or_make(domain).Init_assert(); return wdata_wiki;} private Xow_wiki wdata_wiki;
	public Json_parser Jdoc_parser() {return jdoc_parser;} private Json_parser jdoc_parser = new Json_parser();
	public Wdata_doc_parser Wdoc_parser(Json_doc jdoc) {
		Json_itm_kv itm_0 = Json_itm_kv.cast_(jdoc.Root().Subs_get_at(0));										// get 1st node
		return Bry_.Eq(itm_0.Key().Data_bry(), Wdata_doc_parser_v2.Bry_type) ? wdoc_parser_v2 : wdoc_parser_v1;	// if "type", must be v2
	}
	public Xop_log_property_wkr Property_wkr() {return property_wkr;} private Xop_log_property_wkr property_wkr;
	public Int_obj_ref Tmp_prop_ref() {return tmp_prop_ref;} Int_obj_ref tmp_prop_ref = Int_obj_ref.zero_();
	public void Clear() {
		qids_cache.Clear();
		pids_cache.Clear();
		doc_cache.Clear();
	}	private Hash_adp_bry qids_cache = Hash_adp_bry.cs_(), pids_cache = Hash_adp_bry.cs_();
	public void Qids_add(Bry_bfr bfr, byte[] lang_key, byte wiki_tid, byte[] ns_num, byte[] ttl, byte[] qid) {
		Xob_bz2_file.Build_alias_by_lang_tid(bfr, lang_key, wiki_tid_ref.Val_(wiki_tid));
		byte[] qids_key = bfr.Add_byte(Byte_ascii.Pipe).Add(ns_num).Add_byte(Byte_ascii.Pipe).Add(ttl).XtoAry();
		qids_cache.Add(qids_key, qid);
	}
	public byte[] Qids_get(Xow_wiki wiki, Xoa_ttl ttl) {return Qids_get(wiki.Wdata_wiki_abrv(), ttl);}
	private byte[] Qids_get(byte[] wiki_abrv, Xoa_ttl ttl) {
		if (!enabled) return null;
		if (Bry_.Len_eq_0(wiki_abrv)) return null;	// "other" wikis will never call wikidata
		synchronized (qid_key) {
			qid_key.Init(wiki_abrv, ttl);
			byte[] rv = (byte[])qids_cache.Fetch(qid_key.Cache_key());
			if (rv == null) {	// not in cache
				rv = this.Wdata_wiki().Db_mgr().Load_mgr().Load_qid(qid_key.Wiki_abrv(), qid_key.Ns_num_bry(), qid_key.Ttl_bry()); 
				byte[] add_val = rv == null ? Bry_.Empty : rv;	// JAVA: hashtable does not accept null as value; use Bry_.Empty
				qids_cache.Add(qid_key.Cache_key(), add_val);	// NOTE: if not in db, will insert a null value for cache_key; DATE:2014-07-23
			}
			return Bry_.Len_eq_0(rv) ? null : rv;				// JAVA: convert Bry_.Empty to null
		}
	}	private Byte_obj_ref wiki_tid_ref = Byte_obj_ref.zero_(); private Wdata_qid_key qid_key = new Wdata_qid_key();
	public Int_obj_val Pids_add(byte[] pids_key, int pid_id) {Int_obj_val rv = Int_obj_val.new_(pid_id); pids_cache.Add(pids_key, rv); return rv;}
	public int Pids_get(byte[] lang_key, byte[] pid_name) {
		if (!enabled) return Pid_null;
		byte[] pids_key = Bry_.Add(lang_key, Xoa_consts.Pipe_bry, pid_name);
		Int_obj_val rv = (Int_obj_val)pids_cache.Fetch(pids_key);
		if (rv == null) {
			int pid_id = this.Wdata_wiki().Db_mgr().Load_mgr().Load_pid(lang_key, pid_name); if (pid_id == Pid_null) return Pid_null;
			rv = Pids_add(pids_key, pid_id);
		}
		return rv.Val();
	}
	public void Pages_add(byte[] qid, Wdata_doc page) {
		doc_cache.Add(qid, page);
	}
	public Wdata_doc Pages_get(Xow_wiki wiki, Xoa_ttl ttl, Wdata_pf_property_data data) {
		if		(data.Q()	!= null)	return Pages_get(data.Q());
		else if (data.Of()	!= null) {
			Xoa_ttl of_ttl = Xoa_ttl.parse_(wiki, data.Of()); if (of_ttl == null) return null;
			byte[] qid = Qids_get(wiki, of_ttl); if (qid == null) return null;	// NOTE: for now, use wiki.Lang_key(), not page.Lang()
			return Pages_get(qid);
		}
		else							return Pages_get(wiki, ttl);
	}
	public Wdata_doc Pages_get(Xow_wiki wiki, Xoa_ttl ttl) {byte[] qid_bry = Qids_get(wiki, ttl); return qid_bry == null? null : Pages_get(qid_bry);}
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
			int qid_val = Bry_.Xto_int_or(qid, 1, qid_len, Int_.MaxValue);
			if (qid_val < qid_min) {
				qid_min = qid_val;
				qid_idx = i;
			}
		}
		return qids[qid_idx];
	}
	public void Resolve_to_bfr(Bry_bfr bfr, Wdata_claim_grp prop_grp, byte[] lang_key) {
		int len = prop_grp.Itms_len();
		for (int i = 0; i < len; i++) {	// NOTE: multiple props possible; EX: roles = scientist,painter
			if (i != 0) bfr.Add(Prop_tmpl_val_dlm);
			Wdata_claim_itm_core prop = prop_grp.Itms_get_at(i);
			switch (prop.Snak_tid()) {
				case Wdata_dict_snak_tid.Tid_novalue	: bfr.Add(Wdata_dict_snak_tid.Bry_novalue); break;
				case Wdata_dict_snak_tid.Tid_somevalue	: bfr.Add(Wdata_dict_snak_tid.Bry_somevalue); break;
				default: {
					switch (prop.Val_tid()) {
						case Wdata_dict_val_tid.Tid_string:
							Wdata_claim_itm_str claim_str = (Wdata_claim_itm_str)prop;
							bfr.Add(claim_str.Val_str());
							break;
						case Wdata_dict_val_tid.Tid_time:
							Wdata_claim_itm_time claim_time = (Wdata_claim_itm_time)prop;
							bfr.Add(claim_time.Time());
							break;
						case Wdata_dict_val_tid.Tid_entity:
							Wdata_claim_itm_entity claim_entity = (Wdata_claim_itm_entity)prop;
							Wdata_doc entity_doc = Pages_get(Bry_.Add(Bry_q, claim_entity.Entity_id_bry()));
							if (entity_doc == null) return;	// NOTE: wiki may refer to entity that no longer exists; EX: {{#property:p1}} which links to Q1, but p1 links to Q2 and Q2 was deleted; DATE:2014-02-01
							byte[] label = entity_doc.Label_list_get(lang_key);
							if (label == null && !Bry_.Eq(lang_key, Xol_lang_.Key_en))	// NOTE: some properties may not exist in language of wiki; default to english; DATE:2013-12-19
								label = entity_doc.Label_list_get(Xol_lang_.Key_en);
							if (label != null)	// if label is still not found, don't add null reference
								bfr.Add(label);
							break;
						case Wdata_dict_val_tid.Tid_globecoordinate: {
							Wdata_claim_itm_globecoordinate claim_globecoordinate = (Wdata_claim_itm_globecoordinate)prop;
							bfr.Add(claim_globecoordinate.Lat());
							bfr.Add_byte(Byte_ascii.Comma).Add_byte(Byte_ascii.Space);
							bfr.Add(claim_globecoordinate.Lng());
							break;
						}
						case Wdata_dict_val_tid.Tid_quantity: {
							Wdata_claim_itm_quantity claim_quantity = (Wdata_claim_itm_quantity)prop;
							byte[] amount_bry = claim_quantity.Amount();
							int val = Bry_.Xto_int_or(amount_bry, Ignore_comma, 0, amount_bry.length, 0);
							Xol_lang lang = app.Lang_mgr().Get_by_key(lang_key);
							bfr.Add(lang.Num_mgr().Format_num(val));	// amount; EX: 1,234
							bfr.Add(Bry_quantity_margin_of_error);		// symbol: EX: ±
							bfr.Add(claim_quantity.Unit());				// unit;   EX: 1
							break;
						}
						case Wdata_dict_val_tid.Tid_monolingualtext: {	// {{#property:monolingualprop}} -> some phrase (la)
							Wdata_claim_itm_monolingualtext claim_monolingualtext = (Wdata_claim_itm_monolingualtext)prop;
							bfr.Add(claim_monolingualtext.Text());		// phrase only; PAGE:en.w:Alberta; EX: {{#property:motto}} -> "Fortis et libre"; DATE:2014-08-28
							break;
						}
						default: throw Err_.unhandled(prop.Val_tid());
					}
					break;
				}
			}
		}
	}
	private static final byte[] Ignore_comma = new byte[]{Byte_ascii.Comma};
	private static final byte[] Bry_quantity_margin_of_error = Bry_.new_utf8_("±");
	public static final byte[] Bry_q = Bry_.new_ascii_("q"), Prop_tmpl_val_dlm = Bry_.new_ascii_(", ");
	public void Write_json_as_html(Bry_bfr bfr, byte[] data_raw) {
		bfr.Add(Xoh_consts.Span_bgn_open).Add(Xoh_consts.Id_atr).Add(Html_json_id).Add(Xoh_consts.__end_quote);	// <span id="xowa-wikidata-json">
		Json_doc json = jdoc_parser.Parse(data_raw);
		json.Root().Print_as_json(bfr, 0);
		bfr.Add(Xoh_consts.Span_end);
	}
	private Json_doc Get_json(byte[] qid_bry) {
		if (!enabled) return null;
		Xoa_ttl qid_ttl = Xoa_ttl.parse_(this.Wdata_wiki(), qid_bry); if (qid_ttl == null) {app.Usr_dlg().Warn_many("", "", "invalid qid for ttl: qid=~{0}", String_.new_utf8_(qid_bry)); return null;}
		Xoa_page qid_page = this.Wdata_wiki().Data_mgr().Get_page(qid_ttl, false); if (qid_page.Missing()) return null;
		byte[] src = qid_page.Data_raw();
		return jdoc_parser.Parse(src);
	}	
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled))			return Yn.Xto_str(enabled);
		else if	(ctx.Match(k, Invk_enabled_))			enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_domain))				return String_.new_utf8_(domain);
		else if	(ctx.Match(k, Invk_domain_))			domain = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_property_wkr))		return m.ReadYnOrY("v") ? Property_wkr_or_new() : GfoInvkAble_.Null;
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
	public static final byte[] Ns_property_name_bry = Bry_.new_ascii_(Ns_property_name);
	public static final int Pid_null = -1;
	public static final byte[] Html_json_id = Bry_.new_ascii_("xowa-wikidata-json");
	public static boolean Wiki_page_is_json(byte wiki_tid, int ns_id) {
		switch (wiki_tid) {
			case Xow_wiki_domain_.Tid_wikidata:
				if (ns_id == Xow_ns_.Id_main || ns_id == gplx.xowa.xtns.wdatas.Wdata_wiki_mgr.Ns_property)
					return true;
				break;
			case Xow_wiki_domain_.Tid_home:
				if (ns_id == gplx.xowa.xtns.wdatas.Wdata_wiki_mgr.Ns_property)
					return true;
				break;
		}
		return false;
	}
	public static void Log_missing_qid(Xop_ctx ctx, byte[] qid) {
		ctx.Wiki().App().Usr_dlg().Log_many("", "", "qid not found in wikidata; qid=~{0} page=~{1}", String_.new_utf8_(qid), String_.new_utf8_(ctx.Cur_page().Ttl().Page_db()));
	}
}
class Wdata_qid_key {
	public byte[] Wiki_abrv() {return wiki_abrv;} private byte[] wiki_abrv;
	public byte[] Ns_num_bry() {return ns_num_bry;} private byte[] ns_num_bry;
	public byte[] Ttl_bry() {return ttl_bry;} private byte[] ttl_bry;
	public byte[] Cache_key() {return cache_key;} private byte[] cache_key;
	public void Init(byte[] wiki_abrv, Xoa_ttl ttl) {
		this.wiki_abrv = wiki_abrv;
		Xow_ns ns = ttl.Ns();
		boolean ns_is_canonical = Xow_ns_.Canonical_id(ns.Name_bry()) != Xow_ns_.Id_null;	// handle titles with non-canoncial ns; PAGE:uk.s:Автор:Богдан_Гаврилишин DATE:2014-07-23
		if (ns_is_canonical) {				// canonical ns;		EX: Category:A
			ns_num_bry = ns.Num_bry();		// use ns's number;		EX: 14
			ttl_bry = ttl.Page_db();		// use page_db			EX: A
		}
		else {								// not a canonical ns;  EX: Author:A
			ns_num_bry = Ns_num_bry_main;	// use main ns			EX: 0
			ttl_bry = ttl.Full_db();		// use full_db			EX: Author:A
		}
		this.cache_key = Bry_.Add(wiki_abrv, Byte_ascii.Pipe_bry, ns_num_bry, Byte_ascii.Pipe_bry, ttl_bry);
	}
	public static final byte[] Ns_num_bry_main = Bry_.new_ascii_("000");
}
