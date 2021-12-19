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

import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.libs.files.Io_mgr;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.utls.StringUtl;
import gplx.core.primitives.Gfo_number_parser;
import gplx.types.basics.wrappers.IntRef;
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
	private final BryWtr tmp_time_bfr = BryWtr.New();
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
	public Wbase_claim_base Make_claim_string(int pid, String val) {return Make_claim_string(pid, BryUtl.NewU8(val));}
	public Wbase_claim_base Make_claim_string(int pid, byte[] val) {return new Wbase_claim_string(pid, Wbase_claim_value_type_.Tid__value, val);}
	public Wbase_claim_base Make_claim_time(int pid, String val) {return Make_claim_time(pid, val, BryUtl.Empty, BryUtl.Empty);}
	public Wbase_claim_base Make_claim_time(int pid, String val, int precision) {return Make_claim_time(pid, val, IntUtl.ToBry(precision), BryUtl.Empty);}
	public Wbase_claim_base Make_claim_time(int pid, String val, byte[] precision, byte[] calendar) {
		return new Wbase_claim_time(pid, Wbase_claim_value_type_.Tid__value, Wbase_claim_time_.To_bry(tmp_time_bfr, val), BryUtl.Empty, BryUtl.Empty, BryUtl.Empty, precision, calendar);
	}
	public Wbase_claim_base Make_claim_monolingual(int pid, String lang, String text) {return new Wbase_claim_monolingualtext(pid, Wbase_claim_value_type_.Tid__value, BryUtl.NewU8(lang), BryUtl.NewU8(text));}
	public Wbase_claim_base Make_claim_quantity(int pid, String amount, String unit, String ubound, String lbound) {return new Wbase_claim_quantity(pid, Wbase_claim_value_type_.Tid__value, BryUtl.NewA7(amount), BryUtl.NewA7(unit), BryUtl.NewA7(ubound), BryUtl.NewA7(lbound));}
	public Wbase_claim_base Make_claim_entity_qid(int pid, int val) {return new Wbase_claim_entity(pid, Wbase_claim_value_type_.Tid__value, Wbase_claim_entity_type_.Tid__item, IntUtl.ToBry(val));}
	public Wbase_claim_base Make_claim_entity_pid(int pid, int val) {return new Wbase_claim_entity(pid, Wbase_claim_value_type_.Tid__value, Wbase_claim_entity_type_.Tid__property, IntUtl.ToBry(val));}
	public Wbase_claim_base Make_claim_geo(int pid, String lon, String lat) {return Make_claim_geo(pid, lon, lat, ".00001", null, "http://www.wikidata.org/entity/Q2");}
	public Wbase_claim_base Make_claim_geo(int pid, String lon, String lat, String prc, String alt, String glb) {
		return new Wbase_claim_globecoordinate(pid, Wbase_claim_value_type_.Tid__value, BryUtl.NewA7(lat), BryUtl.NewA7(lon), BryUtl.NewA7(alt), BryUtl.NewA7(prc), BryUtl.NewA7(glb));
	}
	public Wbase_claim_grp Make_qualifiers_grp(int pid, Wbase_claim_base... ary) {return new Wbase_claim_grp(IntRef.New(pid), ary);}
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
		return new Wbase_references_grp(BryUtl.NewU8(hash), list, snaks_order);
	}
	public Wbase_claim_grp Make_reference_itm(int id, Wbase_claim_base... itms) {
		return new Wbase_claim_grp(IntRef.New(id), itms);
	}

	public Wdata_doc_bldr Wdoc(String qid) {return wdoc_bldr.Qid_(qid);}
	public Wdata_doc doc_(String qid, Wbase_claim_base... props) {return wdoc_bldr.Qid_(qid).Add_claims(props).Xto_wdoc();}

	public void Init_pids_add(String lang_key, String pid_name, int pid) {wdata_mgr.Pid_mgr.Add(BryUtl.NewU8(lang_key + "|" + pid_name), pid);}
	public void Init_qids_add(String lang_key, int wiki_tid, String ttl, String qid) {
		BryWtr tmp_bfr = wiki.Utl__bfr_mkr().GetB512();
		wdata_mgr.Qid_mgr.Add(tmp_bfr, BryUtl.NewA7(lang_key), wiki_tid, BryUtl.NewA7("000"), BryUtl.NewA7(ttl), BryUtl.NewA7(qid));
		tmp_bfr.MkrRls();
	}
	public void Init_lang_fallbacks(String... fallbacks) {wiki.Lang().Fallback_bry_(BryUtl.NewA7(StringUtl.ConcatWith(",", fallbacks)));}
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
			if (StringUtl.Eq(lang, "*"))
				external_lang_links.Sort_(true);
			else
				external_lang_links.Langs_add(BryUtl.NewA7(lang));
		}
	}

	public void Init__docs__add(Wdata_doc_bldr bldr)	{Init__docs__add(bldr.Xto_wdoc());}
	public void Init__docs__add(Wdata_doc wdoc)			{
		wdata_mgr.Doc_mgr.Add(wdoc.Qid(), wdoc);

		BryWtr tmp_bfr = BryWtr.New();
		Wbase_qid_mgr qid_mgr = wdata_mgr.Qid_mgr;
		Ordered_hash slinks = wdoc.Slink_list();
		int slinks_len = slinks.Len();
		for (int i = 0; i < slinks_len; i++) {
			Wdata_sitelink_itm slink = (Wdata_sitelink_itm)slinks.GetAt(i);
			Xow_domain_itm domain = Xow_abrv_wm_.Parse_to_domain_itm(slink.Site());
			Xoa_ttl page_ttl = wiki.Ttl_parse(slink.Name());
			qid_mgr.Add(tmp_bfr, domain.Lang_actl_key(), domain.Domain_type_id(), page_ttl.Ns().Num_bry(), page_ttl.Page_db(), wdoc.Qid());
		}
	}

	public void Test_link(String ttl_str, String expd) {Test_link(Xoa_ttl.Parse(wiki, Xow_ns_.Tid__main, BryUtl.NewU8(ttl_str)), expd);}
	public void Test_link(Xoa_ttl ttl, String expd) {
		byte[] qid_ttl = wdata_mgr.Qid_mgr.Get_qid_or_null(wiki, ttl);
		GfoTstr.EqObj(expd, StringUtl.NewU8(qid_ttl));
	}
	public void Test_parse_pid_null(String val)			{Test_parse_pid(val, Wbase_pid.Id_null);}
	public void Test_parse_pid(String val, int expd)	{GfoTstr.EqObj(expd, Wbase_statement_mgr_.Parse_pid(num_parser, BryUtl.NewA7(val)));} private Gfo_number_parser num_parser = new Gfo_number_parser();
	public void Test_parse(String raw, String expd) {
		parser_fxt.Test_parse_page_tmpl_str(raw, expd);
	}
	public void Test_parse_langs(String raw, String expd) {
		// setup langs
		Xoae_page page = wiki.Parser_mgr().Ctx().Page();
		app.Xwiki_mgr__sitelink_mgr().Parse(BryUtl.NewU8(StringUtl.ConcatLinesNl
		( "0|grp1"
		, "1|en|English"
		, "1|fr|French"
		, "1|de|German"
		, "1|pl|Polish"
		)));
		wiki.Xwiki_mgr().Add_by_sitelink_mgr();
		wiki.Appe().Usere().Wiki().Xwiki_mgr().Add_by_csv(BryUtl.NewA7(StringUtl.ConcatLinesNl
		( "1|en.wikipedia.org|en.wikipedia.org"
		, "1|fr.wikipedia.org|fr.wikipedia.org"
		, "1|de.wikipedia.org|de.wikipedia.org"
		, "1|pl.wikipedia.org|pl.wikipedia.org"
		)));

		parser_fxt.Page_ttl_("Q1_en");
		parser_fxt.Exec_parse_page_all_as_str(raw);
		BryWtr tmp_bfr = wiki.Utl__bfr_mkr().GetB512();
		
		wdata_lang_wtr.Page_(page).AddToBfr(tmp_bfr);
	    GfoTstr.EqLines(expd, tmp_bfr.ToStrAndRls());
	}
	public void Test_xwiki_links(String ttl, String... expd) {
		tmp_langs.Clear();
		Wdata_xwiki_link_wtr.Write_wdata_links(tmp_langs, wiki, Xoa_ttl.Parse(wiki, BryUtl.NewU8(ttl)), wiki.Parser_mgr().Ctx().Page().Wdata_external_lang_links());
		GfoTstr.EqLines(expd, Test_xwiki_links_xto_str_ary(tmp_langs));
	}	private List_adp tmp_langs = List_adp_.New();
	String[] Test_xwiki_links_xto_str_ary(List_adp list) {
		int len = list.Len();
		String[] rv = new String[len];
		for (int i = 0; i < len; i++) {
			Wdata_sitelink_itm itm = (Wdata_sitelink_itm)list.GetAt(i);
			rv[i] = StringUtl.NewA7(itm.Page_ttl().Page_db());
		}
		tmp_langs.Clear();
		return rv;
	}
	public void Test_write_json_as_html(String raw_str, String expd) {
		byte[] raw_bry = BryUtl.NewA7(raw_str);
		raw_bry = BryUtl.NewU8(Json_doc.Make_str_by_apos(raw_str));
		BryWtr bfr = wiki.Utl__bfr_mkr().GetB512();
		Wdata_wiki_mgr.Write_json_as_html(jsonParser, bfr, raw_bry);
		GfoTstr.EqObj(expd, bfr.ToStrAndRls());
	}
	public static String New_json(String entity_id, String grp_key, String[] grp_vals) {
		BryWtr bfr = BryWtr.New();
		bfr.AddStrA7("{ 'entity':'").AddStrU8(entity_id).AddByte(AsciiByte.Apos).AddByteNl();
		bfr.AddStrA7(", 'datatype':'commonsMedia'\n");
		bfr.AddStrA7(", '").AddStrU8(grp_key).AddStrA7("':").AddByteNl();
		int len = grp_vals.length;
		for (int i = 0; i < len; i += 2) {
			bfr.AddByteRepeat(AsciiByte.Space, 2);
			bfr.AddByte(i == 0 ? AsciiByte.CurlyBgn : AsciiByte.Comma).AddByte(AsciiByte.Space);
			bfr.AddByte(AsciiByte.Apos).AddStrU8(grp_vals[i    ]).AddByte(AsciiByte.Apos).AddByte(AsciiByte.Colon);
			bfr.AddByte(AsciiByte.Apos).AddStrU8(grp_vals[i + 1]).AddByte(AsciiByte.Apos).AddByteNl();
		}			
		bfr.AddStrA7("  }").AddByteNl();
		bfr.AddStrA7("}").AddByteNl();
		return StringUtl.Replace(bfr.ToStrAndClear(), "'", "\"");
	}
}
