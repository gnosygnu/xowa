/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.wbases;

import gplx.Bry_;
import gplx.Bry_bfr;
import gplx.Bry_bfr_;
import gplx.Byte_ascii;
import gplx.Int_;
import gplx.Io_mgr;
import gplx.List_adp;
import gplx.List_adp_;
import gplx.Ordered_hash;
import gplx.String_;
import gplx.Tfds;
import gplx.core.primitives.Gfo_number_parser;
import gplx.core.primitives.Int_obj_ref;
import gplx.langs.jsons.Json_doc;
import gplx.langs.jsons.Json_parser;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xop_fxt;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.wikis.domains.Xow_abrv_wm_;
import gplx.xowa.wikis.domains.Xow_domain_itm;
import gplx.xowa.wikis.nss.Xow_ns_;
import gplx.xowa.xtns.wbases.claims.Wbase_claim_grp;
import gplx.xowa.xtns.wbases.claims.Wbase_claim_grp_list;
import gplx.xowa.xtns.wbases.claims.Wbase_references_grp;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_entity_type_;
import gplx.xowa.xtns.wbases.claims.enums.Wbase_claim_value_type_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_base;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_entity;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_globecoordinate;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_monolingualtext;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_quantity;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_string;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_time;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_time_;
import gplx.xowa.xtns.wbases.claims.itms.Wbase_claim_value;
import gplx.xowa.xtns.wbases.core.Wbase_pid;
import gplx.xowa.xtns.wbases.core.Wdata_sitelink_itm;
import gplx.xowa.xtns.wbases.pfuncs.Wbase_statement_mgr_;
import gplx.xowa.xtns.wbases.pfuncs.Wdata_external_lang_links_data;
import gplx.xowa.xtns.wbases.stores.Wbase_prop_mgr_loader_;
import gplx.xowa.xtns.wbases.stores.Wbase_qid_mgr;

public class Wdata_wiki_mgr_fxt {
	private Xoae_app app; private Xowe_wiki wiki; private Wdata_doc_bldr wdoc_bldr;
	private final Wdata_xwiki_link_wtr wdata_lang_wtr = new Wdata_xwiki_link_wtr();
	private final Bry_bfr tmp_time_bfr = Bry_bfr_.New();
	private final Json_parser jsonParser = new Json_parser();
	public Xowe_wiki Wiki() {return parser_fxt.Wiki();}
	public Wdata_wiki_mgr_fxt Init() {return Init(new Xop_fxt(), true);}
	public Wdata_wiki_mgr_fxt Init(Xop_fxt parser_fxt, boolean reset) {
		this.parser_fxt = parser_fxt;
		this.wiki = parser_fxt.Wiki();
		app = wiki.Appe();
		app.Xwiki_mgr__sitelink_mgr().Init_by_app();
		wdoc_bldr = new Wdata_doc_bldr();
		wdata_mgr = app.Wiki_mgr().Wdata_mgr();
		wdata_mgr.Prop_mgr().Loader_(Wbase_prop_mgr_loader_.New_mock());
		wdata_mgr.Clear();
		if (reset) {
			Io_mgr.Instance.InitEngine_mem();
			parser_fxt.Reset();
		}
		return this;
	}
	public Xoae_app App() {return app;}
	public Wdata_wiki_mgr Wdata_mgr() {return wdata_mgr;} private Wdata_wiki_mgr wdata_mgr; 
	public Xop_fxt Parser_fxt() {return parser_fxt;} private Xop_fxt parser_fxt;
	public Wdata_doc_bldr Wdoc_bldr(String qid) {return wdoc_bldr.Qid_(qid);}
	public Json_doc Make_json(String src) {return app.Utl__json_parser().Parse_by_apos(src);}

	public Wbase_claim_base Make_claim_novalue(int pid)			{return Wbase_claim_value.New_novalue(pid);}
	public Wbase_claim_base Make_claim_somevalue(int pid)		{return Wbase_claim_value.New_somevalue(pid);}
	public Wbase_claim_base Make_claim_string(int pid, String val) {return Make_claim_string(pid, Bry_.new_u8(val));}
	public Wbase_claim_base Make_claim_string(int pid, byte[] val) {return new Wbase_claim_string(pid, Wbase_claim_value_type_.Tid__value, val);}
	public Wbase_claim_base Make_claim_time(int pid, String val) {return Make_claim_time(pid, val, Bry_.Empty, Bry_.Empty);}
	public Wbase_claim_base Make_claim_time(int pid, String val, int precision) {return Make_claim_time(pid, val, Int_.To_bry(precision), Bry_.Empty);}
	public Wbase_claim_base Make_claim_time(int pid, String val, byte[] precision, byte[] calendar) {
		return new Wbase_claim_time(pid, Wbase_claim_value_type_.Tid__value, Wbase_claim_time_.To_bry(tmp_time_bfr, val), Bry_.Empty, Bry_.Empty, Bry_.Empty, precision, calendar);
	}
	public Wbase_claim_base Make_claim_monolingual(int pid, String lang, String text) {return new Wbase_claim_monolingualtext(pid, Wbase_claim_value_type_.Tid__value, Bry_.new_u8(lang), Bry_.new_u8(text));}
	public Wbase_claim_base Make_claim_quantity(int pid, String amount, String unit, String ubound, String lbound) {return new Wbase_claim_quantity(pid, Wbase_claim_value_type_.Tid__value, Bry_.new_a7(amount), Bry_.new_a7(unit), Bry_.new_a7(ubound), Bry_.new_a7(lbound));}
	public Wbase_claim_base Make_claim_entity_qid(int pid, int val) {return new Wbase_claim_entity(pid, Wbase_claim_value_type_.Tid__value, Wbase_claim_entity_type_.Tid__item, Int_.To_bry(val));}
	public Wbase_claim_base Make_claim_entity_pid(int pid, int val) {return new Wbase_claim_entity(pid, Wbase_claim_value_type_.Tid__value, Wbase_claim_entity_type_.Tid__property, Int_.To_bry(val));}
	public Wbase_claim_base Make_claim_geo(int pid, String lon, String lat) {return Make_claim_geo(pid, lon, lat, ".00001", null, "http://www.wikidata.org/entity/Q2");}
	public Wbase_claim_base Make_claim_geo(int pid, String lon, String lat, String prc, String alt, String glb) {
		return new Wbase_claim_globecoordinate(pid, Wbase_claim_value_type_.Tid__value, Bry_.new_a7(lat), Bry_.new_a7(lon), Bry_.new_a7(alt), Bry_.new_a7(prc), Bry_.new_a7(glb));
	}
	public Wbase_claim_grp Make_qualifiers_grp(int pid, Wbase_claim_base... ary) {return new Wbase_claim_grp(Int_obj_ref.New(pid), ary);}
	public Wbase_claim_grp_list Make_qualifiers(Wbase_claim_grp... ary) {
		Wbase_claim_grp_list rv = new Wbase_claim_grp_list();
		int len = ary.length;
		for (int i = 0; i < len; ++i) 
			rv.Add(ary[i]);
		return rv;
	}
	public Wbase_references_grp[] Make_references(Wbase_references_grp... ary) {
		return ary;
	}
	public Wbase_references_grp Make_reference_grp(String hash, int[] snaks_order, Wbase_claim_grp... snaks) {
		Wbase_claim_grp_list list = new Wbase_claim_grp_list();
		for (Wbase_claim_grp itm : snaks)
			list.Add(itm);
		return new Wbase_references_grp(Bry_.new_u8(hash), list, snaks_order);
	}
	public Wbase_claim_grp Make_reference_itm(int id, Wbase_claim_base... itms) {
		return new Wbase_claim_grp(Int_obj_ref.New(id), itms);
	}

	public Wdata_doc_bldr Wdoc(String qid) {return wdoc_bldr.Qid_(qid);}
	public Wdata_doc doc_(String qid, Wbase_claim_base... props) {return wdoc_bldr.Qid_(qid).Add_claims(props).Xto_wdoc();}

	public void Init_pids_add(String lang_key, String pid_name, int pid) {wdata_mgr.Pid_mgr.Add(Bry_.new_u8(lang_key + "|" + pid_name), pid);}
	public void Init_qids_add(String lang_key, int wiki_tid, String ttl, String qid) {
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		wdata_mgr.Qid_mgr.Add(tmp_bfr, Bry_.new_a7(lang_key), wiki_tid, Bry_.new_a7("000"), Bry_.new_a7(ttl), Bry_.new_a7(qid));
		tmp_bfr.Mkr_rls();
	}
	public void Init_lang_fallbacks(String... fallbacks) {wiki.Lang().Fallback_bry_(Bry_.new_a7(String_.Concat_with_str(",", fallbacks)));}
	public void Init_xwikis_add(String... prefixes) {
		int len = prefixes.length;
		for (int i = 0; i < len; i++) {
			String prefix = prefixes[i];
			wiki.Xwiki_mgr().Add_by_atrs(prefix, prefix + ".wikipedia.org");
		}
	}
	public void Init_external_links_mgr_clear() {wiki.Parser_mgr().Ctx().Page().Wdata_external_lang_links().Reset();}
	public void Init_external_links_mgr_add(String... langs) {
		Wdata_external_lang_links_data external_lang_links = wiki.Parser_mgr().Ctx().Page().Wdata_external_lang_links();
		external_lang_links.Enabled_(true);
		int len = langs.length;
		for (int i = 0; i < len; i++) {
			String lang = langs[i];
			if (String_.Eq(lang, "*"))
				external_lang_links.Sort_(true);
			else
				external_lang_links.Langs_add(Bry_.new_a7(lang));
		}
	}

	public void Init__docs__add(Wdata_doc_bldr bldr)	{Init__docs__add(bldr.Xto_wdoc());}
	public void Init__docs__add(Wdata_doc wdoc)			{
		wdata_mgr.Doc_mgr.Add(wdoc.Qid(), wdoc);

		Bry_bfr tmp_bfr = Bry_bfr_.New();
		Wbase_qid_mgr qid_mgr = wdata_mgr.Qid_mgr;
		Ordered_hash slinks = wdoc.Slink_list();
		int slinks_len = slinks.Len();
		for (int i = 0; i < slinks_len; i++) {
			Wdata_sitelink_itm slink = (Wdata_sitelink_itm)slinks.Get_at(i);
			Xow_domain_itm domain = Xow_abrv_wm_.Parse_to_domain_itm(slink.Site());
			Xoa_ttl page_ttl = wiki.Ttl_parse(slink.Name());
			qid_mgr.Add(tmp_bfr, domain.Lang_actl_key(), domain.Domain_type_id(), page_ttl.Ns().Num_bry(), page_ttl.Page_db(), wdoc.Qid());
		}
	}

	public void Test_link(String ttl_str, String expd) {Test_link(Xoa_ttl.Parse(wiki, Xow_ns_.Tid__main, Bry_.new_u8(ttl_str)), expd);}
	public void Test_link(Xoa_ttl ttl, String expd) {
		byte[] qid_ttl = wdata_mgr.Qid_mgr.Get_qid_or_null(wiki, ttl);
		Tfds.Eq(expd, String_.new_u8(qid_ttl));
	}
	public void Test_parse_pid_null(String val)			{Test_parse_pid(val, Wbase_pid.Id_null);}
	public void Test_parse_pid(String val, int expd)	{Tfds.Eq(expd, Wbase_statement_mgr_.Parse_pid(num_parser, Bry_.new_a7(val)));} private Gfo_number_parser num_parser = new Gfo_number_parser();
	public void Test_parse(String raw, String expd) {
		parser_fxt.Test_parse_page_tmpl_str(raw, expd);
	}
	public void Test_parse_langs(String raw, String expd) {
		// setup langs
		Xoae_page page = wiki.Parser_mgr().Ctx().Page();
		app.Xwiki_mgr__sitelink_mgr().Parse(Bry_.new_u8(String_.Concat_lines_nl
		( "0|grp1"
		, "1|en|English"
		, "1|fr|French"
		, "1|de|German"
		, "1|pl|Polish"
		)));
		wiki.Xwiki_mgr().Add_by_sitelink_mgr();
		wiki.Appe().Usere().Wiki().Xwiki_mgr().Add_by_csv(Bry_.new_a7(String_.Concat_lines_nl
		( "1|en.wikipedia.org|en.wikipedia.org"
		, "1|fr.wikipedia.org|fr.wikipedia.org"
		, "1|de.wikipedia.org|de.wikipedia.org"
		, "1|pl.wikipedia.org|pl.wikipedia.org"
		)));

		parser_fxt.Page_ttl_("Q1_en");
		parser_fxt.Exec_parse_page_all_as_str(raw);
		Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_b512();
		
		wdata_lang_wtr.Page_(page).Bfr_arg__add(tmp_bfr);
	    Tfds.Eq_str_lines(expd, tmp_bfr.To_str_and_rls());
	}
	public void Test_xwiki_links(String ttl, String... expd) {
		tmp_langs.Clear();
		Wdata_xwiki_link_wtr.Write_wdata_links(tmp_langs, wiki, Xoa_ttl.Parse(wiki, Bry_.new_u8(ttl)), wiki.Parser_mgr().Ctx().Page().Wdata_external_lang_links());
		Tfds.Eq_ary_str(expd, Test_xwiki_links_xto_str_ary(tmp_langs));
	}	private List_adp tmp_langs = List_adp_.New();
	String[] Test_xwiki_links_xto_str_ary(List_adp list) {
		int len = list.Len();
		String[] rv = new String[len];
		for (int i = 0; i < len; i++) {
			Wdata_sitelink_itm itm = (Wdata_sitelink_itm)list.Get_at(i);
			rv[i] = String_.new_a7(itm.Page_ttl().Page_db());
		}
		tmp_langs.Clear();
		return rv;
	}
	public void Test_write_json_as_html(String raw_str, String expd) {
		byte[] raw_bry = Bry_.new_a7(raw_str);
		raw_bry = Bry_.new_u8(Json_doc.Make_str_by_apos(raw_str));
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_b512();
		Wdata_wiki_mgr.Write_json_as_html(jsonParser, bfr, raw_bry);
		Tfds.Eq(expd, bfr.To_str_and_rls());
	}
	public static String New_json(String entity_id, String grp_key, String[] grp_vals) {
		Bry_bfr bfr = Bry_bfr_.New();
		bfr.Add_str_a7("{ 'entity':'").Add_str_u8(entity_id).Add_byte(Byte_ascii.Apos).Add_byte_nl();
		bfr.Add_str_a7(", 'datatype':'commonsMedia'\n");
		bfr.Add_str_a7(", '").Add_str_u8(grp_key).Add_str_a7("':").Add_byte_nl();
		int len = grp_vals.length;
		for (int i = 0; i < len; i += 2) {
			bfr.Add_byte_repeat(Byte_ascii.Space, 2);
			bfr.Add_byte(i == 0 ? Byte_ascii.Curly_bgn : Byte_ascii.Comma).Add_byte(Byte_ascii.Space);			
			bfr.Add_byte(Byte_ascii.Apos).Add_str_u8(grp_vals[i    ]).Add_byte(Byte_ascii.Apos).Add_byte(Byte_ascii.Colon);
			bfr.Add_byte(Byte_ascii.Apos).Add_str_u8(grp_vals[i + 1]).Add_byte(Byte_ascii.Apos).Add_byte_nl();
		}			
		bfr.Add_str_a7("  }").Add_byte_nl();
		bfr.Add_str_a7("}").Add_byte_nl();
		return String_.Replace(bfr.To_str_and_clear(), "'", "\""); 
	}
}
