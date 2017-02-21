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
package gplx.xowa.wikis.xwikis.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*;
import org.junit.*; import gplx.core.strings.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.langs.*;
public class Xow_xwiki_mgr_tst {		
	@Before public void init() {fxt.Clear();} private Xow_xwiki_mgr_fxt fxt = new Xow_xwiki_mgr_fxt();
	@Test   public void Add_bulk_langs_wiki() 		{
		fxt.Init_langs();
		fxt.Test_add_bulk_langs
		( fxt.xwiki_("en", "en.wikipedia.org", "https://en.wikipedia.org/wiki/~{0}")
		, fxt.xwiki_("de", "de.wikipedia.org", "https://de.wikipedia.org/wiki/~{0}")
		, fxt.xwiki_("fr", "fr.wikipedia.org", "https://fr.wikipedia.org/wiki/~{0}")
		, fxt.xwiki_("ja", "ja.wikipedia.org", "https://ja.wikipedia.org/wiki/~{0}")
		);
	}
	@Test   public void Add_bulk_langs_grp_commons() {
		fxt.Init_langs();
		fxt.Wiki().Xwiki_mgr().Add_by_sitelink_mgr(Xow_domain_tid_.Tid__wikipedia);
		fxt.Tst_itms(fxt.xwiki_("de", "de.wikipedia.org", "https://de.wikipedia.org/wiki/~{0}"), fxt.xwiki_("fr", "fr.wikipedia.org", "https://fr.wikipedia.org/wiki/~{0}"));
	}
	@Test   public void Add_bulk_peers() {
		fxt.Init_peers();
		fxt.Test_add_bulk_peers
		( fxt.xwiki_("wikt", "en.wiktionary.org"
		, "https://en.wiktionary.org/wiki/~{0}")
		, fxt.xwiki_("wiktionary", "en.wiktionary.org"
		, "https://en.wiktionary.org/wiki/~{0}")
		, fxt.xwiki_("s", "en.wikisource.org", "https://en.wikisource.org/wiki/~{0}"));
	}
	@Test   public void Add_bulk_peers_skip_self() 	{	// PURPOSE: skip "wikipedia" as alias since "Wikipedia" is Srch_rslt_cbk; needed for titles of "Wikipedia:Main page" (which would otherwise try to go to page "Main Page" in the main names of xwiki "Wikipedia"
		fxt.Init_peers();
		fxt.Test_add_bulk_peers
		( fxt.xwiki_null_("wikipedia")
		, fxt.xwiki_("w", "en.wikipedia.org", "https://en.wikipedia.org/wiki/~{0}"));
	}
	@Test   public void Add_bulk_peers_tid() { // PURPOSE:wikt should generate wiki_tid of wiktionary, not wikipedia; PAGE:en.s:Main_Page DATE:2014-09-14
		fxt.Init_wikt ().Test_add_bulk_peers(fxt.xwiki_("wikt", "en.wiktionary.org", "https://en.wiktionary.org/wiki/~{0}"));
	}
//		@Test   public void Duplicate() {	// PURPOSE.FIX: multiple aliases for same domain should only be added once to Get_at's list; DATE:2014-11-07
//			fxt.Exec_parse(String_.Concat_lines_nl_skip_last
//			( "0|a1|a.org"
//			, "0|a2|a.org"
//			));
//			fxt.Test_parse(String_.Concat_lines_nl_skip_last
//			( "a1|https://a.org//~{0}"
//			));
//		}
}
class Xow_xwiki_mgr_fxt {
	Xow_xwiki_mgr xwiki_mgr; Xoa_lang_mgr lang_mgr; String_bldr sb = String_bldr_.new_(); Xoae_app app; Xowe_wiki wiki;
	public void Clear() {
		if (xwiki_mgr == null) {
			app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
			xwiki_mgr = wiki.Xwiki_mgr();
			lang_mgr = app.Lang_mgr();
		}
		xwiki_mgr.Clear();
		lang_mgr.Clear();
	}
	public Xowe_wiki Wiki() {return wiki;}
	public Xow_xwiki_itm xwiki_null_(String key) {return Xow_xwiki_itm.new_(Bry_.new_u8(key), Bry_.Empty, Xol_lang_stub_.Id__unknown, Xow_domain_tid_.Tid__other, Bry_.Empty, Bry_.Empty);}
	public Xow_xwiki_itm xwiki_(String key, String domain_str, String url_fmt) {
		Xow_domain_itm domain = Xow_domain_itm_.parse(Bry_.new_u8(domain_str));
		return Xow_xwiki_itm.new_(Bry_.new_u8(key), Bry_.new_u8(url_fmt), domain.Lang_actl_itm().Id(), domain.Domain_type_id(), domain.Domain_bry(), domain.Abrv_wm());
	}
	public Xow_xwiki_mgr_fxt Init_langs() {
		app.Xwiki_mgr__sitelink_mgr().Parse(Bry_.new_u8(String_.Concat_lines_nl
		( "0|english"
		, "1|en|English"
		, "0|europe_west"
		, "1|fr|French"
		, "1|de|German"
		, "0|asia_east"
		, "1|ja|Japanese"
		)));
		return this;
	}
	public Xow_xwiki_mgr_fxt Init_peers() {
		wiki.Xwiki_mgr().Add_by_csv(Bry_.new_u8(String_.Concat_lines_nl
		( "1|d|www.wikidata.org"
		, "2|wikt;wiktionary|wiktionary"
		, "2|s|wikisource"
		, "2|w;wikipedia|wikipedia"
		)));
		return this;
	}
	public Xow_xwiki_mgr_fxt Init_wikt() {
		wiki.Xwiki_mgr().Add_by_csv(Bry_.new_u8(String_.Concat_lines_nl
		( "2|wikt;wiktionary|wiktionary"
		)));
		return this;
	}
	public Xow_xwiki_mgr_fxt Test_add_bulk_langs(Xow_xwiki_itm... itms) {
		xwiki_mgr.Add_by_sitelink_mgr();
		Tfds.Eq_str_lines(Xto_str(itms), Xto_str(To_ary(itms)));
		return this;
	}
	public Xow_xwiki_mgr_fxt Test_add_bulk_peers(Xow_xwiki_itm... itms) {
		Tfds.Eq_str_lines(Xto_str(itms), Xto_str(To_ary(itms)));
		return this;
	}
	public Xow_xwiki_mgr_fxt Tst_itms(Xow_xwiki_itm... itms) {
		Tfds.Eq_str_lines(Xto_str(itms), Xto_str(To_ary(itms)));
		return this;
	}
	public Xow_xwiki_mgr_fxt Test_len(int expd)			{Tfds.Eq(expd, xwiki_mgr.Len()); return this;}
	Xow_xwiki_itm[] To_ary(Xow_xwiki_itm[] itms) {
		int len = itms.length;
		List_adp rv = List_adp_.New();
		for (int i = 0; i < len; i++) {
			byte[] alias = itms[i].Key_bry();
			Xow_xwiki_itm itm = xwiki_mgr.Get_by_key(alias);
			if (itm == null) itm = xwiki_null_(String_.new_u8(alias)); // "null", ignore
			rv.Add(itm);
		}
		return (Xow_xwiki_itm[])rv.To_ary(Xow_xwiki_itm.class);
	}
	String Xto_str(Xow_xwiki_itm[] itms) {
		int len = itms.length;
		for (int i = 0; i < len; i++) {
			Xow_xwiki_itm itm = itms[i];
			if (Bry_.Len_eq_0(itm.Domain_bry()))	// "null", ignore
				sb.Add(itm.Key_bry()).Add_char_nl();
			else {
				sb.Add(itm.Key_bry()).Add_char_pipe().Add(itm.Domain_bry()).Add_char_pipe().Add(itm.Url_fmt()).Add_char_pipe().Add(itm.Domain_tid()).Add_char_nl();
			}
		}
		return sb.To_str_and_clear();
	}
}
