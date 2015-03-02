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
import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.gui.*; import gplx.xowa.xtns.wdatas.imports.*; import gplx.xowa.pages.*;
import gplx.xowa.langs.*; import gplx.xowa.xtns.wdatas.core.*; import gplx.xowa.xtns.wdatas.pfuncs.*;
public class Wdata_wiki_mgr_fxt {
	public Xowe_wiki Wiki() {return parser_fxt.Wiki();}
	public Wdata_wiki_mgr_fxt Init() {return Init(new Xop_fxt(), true);}
	public Wdata_wiki_mgr_fxt Init(Xop_fxt parser_fxt, boolean reset) {
		this.parser_fxt = parser_fxt;
		this.wiki = parser_fxt.Wiki();
		app = wiki.Appe();
		wdoc_bldr = new Wdata_doc_bldr();
		wdata_mgr = app.Wiki_mgr().Wdata_mgr();
		wdata_mgr.Clear();
		if (reset) {
			Io_mgr._.InitEngine_mem();
			parser_fxt.Reset();
		}
		return this;
	}	private Xoae_app app; private Xowe_wiki wiki; private Wdata_wiki_mgr wdata_mgr; private Wdata_doc_bldr wdoc_bldr; private Xop_fxt parser_fxt;
	public Xoae_app App() {return app;}
	public Wdata_doc_bldr Wdoc_bldr(String qid) {return wdoc_bldr.Qid_(qid);}
	public Wdata_claim_itm_core Make_claim_novalue(int pid)			{return Wdata_claim_itm_system.new_novalue(pid);}
	public Wdata_claim_itm_core Make_claim_somevalue(int pid)		{return Wdata_claim_itm_system.new_somevalue(pid);}
	public Wdata_claim_itm_core Make_claim_str(int pid, String val) {return Make_claim_str(pid, Bry_.new_utf8_(val));}
	public Wdata_claim_itm_core Make_claim_str(int pid, byte[] val) {return new Wdata_claim_itm_str(pid, Wdata_dict_snak_tid.Tid_value, val);}
	public Wdata_claim_itm_core Make_claim_time(int pid, String val) {return Make_claim_time(pid, val, Bry_.Empty, Bry_.Empty);}
	public Wdata_claim_itm_core Make_claim_time(int pid, String val, int precision) {return Make_claim_time(pid, val, Int_.Xto_bry(precision), Bry_.Empty);}
	public Wdata_claim_itm_core Make_claim_time(int pid, String val, byte[] precision, byte[] calendar) {
		return new Wdata_claim_itm_time(pid, Wdata_dict_snak_tid.Tid_value, Wdata_dict_value_time.Xto_time(val), Bry_.Empty, Bry_.Empty, Bry_.Empty, precision, calendar);
	}
	public Wdata_claim_itm_core Make_claim_monolingual(int pid, String lang, String text) {return new Wdata_claim_itm_monolingualtext(pid, Wdata_dict_snak_tid.Tid_value, Bry_.new_utf8_(lang), Bry_.new_utf8_(text));}
	public Wdata_claim_itm_core Make_claim_quantity(int pid, String amount, String unit, String ubound, String lbound) {return new Wdata_claim_itm_quantity(pid, Wdata_dict_snak_tid.Tid_value, Bry_.new_ascii_(amount), Bry_.new_ascii_(unit), Bry_.new_ascii_(ubound), Bry_.new_ascii_(lbound));}
	public Wdata_claim_itm_core Make_claim_entity(int pid, int val) {return new Wdata_claim_itm_entity(pid, Wdata_dict_snak_tid.Tid_value, Int_.Xto_bry(val));}
	public Wdata_claim_itm_core Make_claim_geo(int pid, String lon, String lat) {return Make_claim_geo(pid, lon, lat, ".000277777", null, "Q2");}
	public Wdata_claim_itm_core Make_claim_geo(int pid, String lon, String lat, String prc, String alt, String glb) {
		return new Wdata_claim_itm_globecoordinate(pid, Wdata_dict_snak_tid.Tid_value, Bry_.new_ascii_(lat), Bry_.new_ascii_(lon), Bry_.new_ascii_(alt), Bry_.new_ascii_(prc), Bry_.new_ascii_(glb));
	}
	public Wdata_claim_grp Make_qualifiers_grp(int pid, Wdata_claim_itm_core... ary) {return new Wdata_claim_grp(Int_obj_ref.new_(pid), ary);}
	public Wdata_claim_grp_list Make_qualifiers(Wdata_claim_grp... ary) {
		Wdata_claim_grp_list rv = new Wdata_claim_grp_list();
		int len = ary.length;
		for (int i = 0; i < len; ++i) 
			rv.Add(ary[i]);
		return rv;
	}

	public Wdata_doc doc_(String qid, Wdata_claim_itm_core... props) {return wdoc_bldr.Qid_(qid).Add_claims(props).Xto_wdoc();}
	public void Init_xwikis_add(String... prefixes) {
		int len = prefixes.length;
		for (int i = 0; i < len; i++) {
			String prefix = prefixes[i];
			wiki.Xwiki_mgr().Add_full(prefix, prefix + ".wikipedia.org");
		}
	}
	public void Init_qids_add(String lang_key, int wiki_tid, String ttl, String qid) {
		Bry_bfr tmp_bfr = app.Utl__bfr_mkr().Get_b512();
		wdata_mgr.Qids_add(tmp_bfr, Bry_.new_ascii_(lang_key), wiki_tid, Bry_.new_ascii_("000"), Bry_.new_ascii_(ttl), Bry_.new_ascii_(qid));
		tmp_bfr.Mkr_rls();
	}
	public void Init_pids_add(String lang_key, String pid_name, int pid) {wdata_mgr.Pids_add(Bry_.new_utf8_(lang_key + "|" + pid_name), pid);}
	public void Init_links_add(String wiki, String ttl, String qid) {Init_links_add(wiki, "000", ttl, qid);}
	public void Init_links_add(String wiki, String ns_num, String ttl, String qid) {
		byte[] ttl_bry = Bry_.new_utf8_(ttl);
		Xowd_regy_mgr regy_mgr = app.Hive_mgr().Regy_mgr();
		Io_url regy_fil = wdata_mgr.Wdata_wiki().Tdb_fsys_mgr().Site_dir().GenSubDir_nest("data", "qid").GenSubFil_nest(wiki, ns_num, "reg.csv");
		regy_mgr.Init(regy_fil);
		regy_mgr.Create(ttl_bry);
		regy_mgr.Save();

		Bry_bfr bfr = app.Utl__bfr_mkr().Get_b512().Mkr_rls();
		byte[] itm = bfr.Add(ttl_bry).Add_byte(Byte_ascii.Pipe).Add(Bry_.new_ascii_(qid)).Add_byte_nl().Xto_bry_and_clear();
		Xob_xdat_file xdat_file = new Xob_xdat_file();
		xdat_file.Insert(bfr, itm);
		Io_url file_orig = Xob_wdata_qid_base_tst.ttl_(app.Wiki_mgr().Wdata_mgr().Wdata_wiki(), wiki, ns_num, 0);
		xdat_file.Save(file_orig);
	}
	public void Init_external_links_mgr_clear() {wiki.Ctx().Cur_page().Wdata_external_lang_links().Reset();}
	public void Init_external_links_mgr_add(String... langs) {
		Wdata_external_lang_links_data external_lang_links = wiki.Ctx().Cur_page().Wdata_external_lang_links();
		external_lang_links.Enabled_(true);
		int len = langs.length;
		for (int i = 0; i < len; i++) {
			String lang = langs[i];
			if (String_.Eq(lang, "*"))
				external_lang_links.Sort_(true);
			else
				external_lang_links.Langs_add(Bry_.new_ascii_(lang));
		}
	}
	public void Test_get_low_qid(String qid, String expd) {
		Tfds.Eq(expd, String_.new_ascii_(Wdata_wiki_mgr.Get_low_qid(Bry_.new_ascii_(qid))));
	}
	public void Test_link(String ttl_str, String expd) {Test_link(Xoa_ttl.parse_(wiki, Xow_ns_.Id_main, Bry_.new_utf8_(ttl_str)), expd);}
	public void Test_link(Xoa_ttl ttl, String expd) {
		byte[] qid_ttl = wdata_mgr.Qids_get(wiki, ttl);
		Tfds.Eq(expd, String_.new_utf8_(qid_ttl));
	}
	public void Test_parse_pid_null(String val)			{Test_parse_pid(val, Wdata_wiki_mgr.Pid_null);}
	public void Test_parse_pid(String val, int expd)	{Tfds.Eq(expd, Wdata_pf_property.Parse_pid(num_parser, Bry_.new_ascii_(val)));} private NumberParser num_parser = new NumberParser();
	public void Init_pages_add(Wdata_doc page) {wdata_mgr.Pages_add(page.Qid(), page);}
	public void Test_parse(String raw, String expd) {
		parser_fxt.Test_parse_page_tmpl_str(raw, expd);
	}
	public void Test_parse_langs(String raw, String expd) {
		// clear langs, else dupes
		wiki.Xwiki_mgr().Lang_mgr().Clear();

		// setup langs
		Xoae_page page = wiki.Ctx().Cur_page();
		Xoa_lang_mgr lang_mgr = app.Lang_mgr();
		lang_mgr.Groups().Set_bulk(Bry_.new_ascii_(String_.Concat_lines_nl
			(	"+||grp|wiki"
			,	"+|wiki|grp|grp1"
			,	"+|grp1|itm|en|English"
			,	"+|grp1|itm|fr|French"
			,	"+|grp1|itm|de|German"
			,	"+|grp1|itm|pl|Polish"
			)));
		wiki.Xwiki_mgr().Add_bulk_langs(Bry_.new_ascii_("wiki"));
		String bulk = String_.Concat_lines_nl
			(	"en.wikipedia.org|en.wikipedia.org"
			,	"fr.wikipedia.org|fr.wikipedia.org"
			,	"de.wikipedia.org|de.wikipedia.org"
			,	"pl.wikipedia.org|pl.wikipedia.org"
			);
		wiki.Appe().User().Wiki().Xwiki_mgr().Add_bulk(Bry_.new_ascii_(bulk));

		// register lang itms (needed for perf)
		Xow_xwiki_mgr xwiki_mgr = wiki.Xwiki_mgr();
		int len = xwiki_mgr.Len();
		for (int i = 0; i < len; i++) {
			Xow_xwiki_itm xwiki_itm = xwiki_mgr.Get_at(i);
			byte[] lang_key = Xol_lang_itm_.Get_by_id(xwiki_itm.Lang_id()).Key();
			Object lang_obj = app.Lang_mgr().Groups().Grps_get(Bry_.new_ascii_("wiki")).Nde_subs_get_at(0).Nde_subs_get(lang_key);
			wiki.Xwiki_mgr().Lang_mgr().Itms_reg(xwiki_itm, (Xoac_lang_itm)lang_obj);
		}

		parser_fxt.Page_ttl_("Q1_en");
		parser_fxt.Exec_parse_page_all_as_str(raw);
		Bry_bfr tmp_bfr = wiki.Appe().Utl__bfr_mkr().Get_b512();
		wiki.Html_mgr().Page_wtr_mgr().Wkr(Xopg_view_mode.Tid_read).Wdata_lang_wtr().Page_(page).XferAry(tmp_bfr, 0);
	    Tfds.Eq_str_lines(expd, tmp_bfr.Mkr_rls().Xto_str_and_clear());
	}
	public void Test_xwiki_links(String ttl, String... expd) {
		tmp_langs.Clear();
		Wdata_xwiki_link_wtr.Write_wdata_links(tmp_langs, wiki, Xoa_ttl.parse_(wiki, Bry_.new_utf8_(ttl)), wiki.Ctx().Cur_page().Wdata_external_lang_links());
		Tfds.Eq_ary_str(expd, Test_xwiki_links_xto_str_ary(tmp_langs));
	}	ListAdp tmp_langs = ListAdp_.new_();
	String[] Test_xwiki_links_xto_str_ary(ListAdp list) {
		int len = list.Count();
		String[] rv = new String[len];
		for (int i = 0; i < len; i++) {
			Wdata_sitelink_itm itm = (Wdata_sitelink_itm)list.FetchAt(i);
			rv[i] = String_.new_ascii_(itm.Page_ttl().Page_db());
		}
		tmp_langs.Clear();
		return rv;
	}
	public void Test_write_json_as_html(String raw_str, String expd) {
		byte[] raw_bry = Bry_.new_ascii_(raw_str);
		raw_bry = gplx.json.Json_parser_tst.Replace_apos(raw_bry);
		Bry_bfr bfr = app.Utl__bfr_mkr().Get_b512();
		Wdata_wiki_mgr.Write_json_as_html(wdata_mgr.Jdoc_parser(), bfr, raw_bry);
		Tfds.Eq(expd, bfr.Mkr_rls().Xto_str_and_clear());
	}
}
