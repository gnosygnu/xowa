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
package gplx.xowa; import gplx.*;
import org.junit.*; import gplx.xowa.wikis.*; import gplx.xowa.langs.*;
public class Xow_xwiki_mgr_tst {
	Xow_xwiki_mgr_fxt fxt = new Xow_xwiki_mgr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Add_bulk_wiki_en() 						{fxt.Tst_add_bulk("w|en.wikipedia.org"				, Xol_lang_itm_.Id__unknown		, Xow_wiki_domain_.Tid_wikipedia		, "w"			, "http://en.wikipedia.org/wiki/~{0}", "en.wikipedia.org");}
	@Test  public void Add_bulk_wiki_fr() 						{fxt.Tst_add_bulk("fr|fr.wikipedia.org"				, Xol_lang_itm_.Id_fr			, Xow_wiki_domain_.Tid_wikipedia		, "fr"			, "http://fr.wikipedia.org/wiki/~{0}", "fr.wikipedia.org");}
	@Test  public void Add_bulk_wikt_en() 						{fxt.Tst_add_bulk("wikt|en.wiktionary.org"			, Xol_lang_itm_.Id__unknown		, Xow_wiki_domain_.Tid_wiktionary		, "wikt"		, "http://en.wiktionary.org/wiki/~{0}", "en.wiktionary.org");}
	@Test  public void Add_bulk_commons() 						{fxt.Tst_add_bulk("commons|commons.wikimedia.org"	, Xol_lang_itm_.Id__unknown		, Xow_wiki_domain_.Tid_commons		, "commons"		, "http://commons.wikimedia.org/wiki/~{0}", "commons.wikimedia.org");}
	@Test  public void Add_bulk_commons_cap() 					{fxt.Tst_add_bulk("Commons|commons.wikimedia.org"	, Xol_lang_itm_.Id__unknown		, Xow_wiki_domain_.Tid_commons		, "Commons"		, "http://commons.wikimedia.org/wiki/~{0}", "commons.wikimedia.org");}
	@Test  public void Add_bulk_langs_wiki() 					{fxt.Langs_ini().Tst_add_bulk_langs("wiki", fxt.xwiki_("en", "en.wikipedia.org", "http://en.wikipedia.org/wiki/~{0}"), fxt.xwiki_("de", "de.wikipedia.org", "http://de.wikipedia.org/wiki/~{0}"), fxt.xwiki_("fr", "fr.wikipedia.org", "http://fr.wikipedia.org/wiki/~{0}"), fxt.xwiki_("ja", "ja.wikipedia.org", "http://ja.wikipedia.org/wiki/~{0}"));}
	@Test  public void Add_bulk_langs_grps() 					{fxt.Langs_ini().Tst_add_bulk_langs("europe_west~asia_east", fxt.xwiki_("de", "de.wikipedia.org", "http://de.wikipedia.org/wiki/~{0}"), fxt.xwiki_("fr", "fr.wikipedia.org", "http://fr.wikipedia.org/wiki/~{0}"), fxt.xwiki_("ja", "ja.wikipedia.org", "http://ja.wikipedia.org/wiki/~{0}"));}
	@Test  public void Add_bulk_langs_grp_itm() 				{fxt.Langs_ini().Tst_add_bulk_langs("europe_west~ja", fxt.xwiki_("de", "de.wikipedia.org", "http://de.wikipedia.org/wiki/~{0}"), fxt.xwiki_("fr", "fr.wikipedia.org", "http://fr.wikipedia.org/wiki/~{0}"), fxt.xwiki_("ja", "ja.wikipedia.org", "http://ja.wikipedia.org/wiki/~{0}"));}
	@Test  public void Add_bulk_langs_grp_commons() {
		fxt.Langs_ini();
		GfoInvkAble_.InvkCmd_msg(fxt.Wiki().Xwiki_mgr(), Xow_xwiki_mgr.Invk_add_bulk_langs, GfoMsg_.new_parse_(Xow_xwiki_mgr.Invk_add_bulk_langs).Add("grp_key", "europe_west").Add("wiki_type_name", "wikipedia"));
		fxt.Tst_itms(fxt.xwiki_("de", "de.wikipedia.org", "http://de.wikipedia.org/wiki/~{0}"), fxt.xwiki_("fr", "fr.wikipedia.org", "http://fr.wikipedia.org/wiki/~{0}"));
	}
	@Test  public void Add_bulk_peers() 						{fxt.Peers_ini().Tst_add_bulk_peers("peer", fxt.xwiki_null_("commons"), fxt.xwiki_null_("m"), fxt.xwiki_("wikt", "en.wiktionary.org", "http://en.wiktionary.org/wiki/~{0}"), fxt.xwiki_("wiktionary", "en.wiktionary.org", "http://en.wiktionary.org/wiki/~{0}"), fxt.xwiki_("s", "en.wikisource.org", "http://en.wikisource.org/wiki/~{0}"));}
	@Test  public void Add_bulk_peers_skip_self() 				{fxt.Peers_ini().Tst_add_bulk_peers("peer", fxt.xwiki_null_("wikipedia"), fxt.xwiki_("w", "en.wikipedia.org", "http://en.wikipedia.org/wiki/~{0}"));}	// PURPOSE: skip "wikipedia" as alias since "Wikipedia" is namespace; needed for titles of "Wikipedia:Main page" (which would otherwise try to go to page "Main Page" in the main names of xwiki "Wikipedia"
	@Test  public void Add_bulk_core_wikidata() 				{fxt.Peers_ini().Tst_add_bulk_peers("core", fxt.xwiki_("d", "www.wikidata.org", "http://www.wikidata.org/wiki/~{0}"));}
}
class Xow_xwiki_mgr_fxt {
	Xow_xwiki_mgr xwiki_mgr; Xoa_lang_mgr lang_mgr; String_bldr sb = String_bldr_.new_(); Xoa_app app; Xow_wiki wiki;
	public void Clear() {
		if (xwiki_mgr == null) {
			app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_tst_(app);
			xwiki_mgr = wiki.Xwiki_mgr();
			lang_mgr = app.Lang_mgr();
		}
		xwiki_mgr.Clear();
		lang_mgr.Clear();
	}
	public Xow_wiki Wiki() {return wiki;}
	public Xow_xwiki_itm xwiki_null_(String key) {return new Xow_xwiki_itm(Bry_.new_utf8_(key), Bry_.Empty, Xow_wiki_domain_.Tid_other, Xol_lang_itm_.Id__unknown, Bry_.Empty);}
	public Xow_xwiki_itm xwiki_(String key, String domain, String fmt) {return new Xow_xwiki_itm(Bry_.new_utf8_(key), Bry_.new_utf8_(fmt), Xow_wiki_domain_.Tid_other, Xol_lang_itm_.Id__unknown, Bry_.new_utf8_(domain));}
	public Xow_xwiki_mgr_fxt Tst_add_bulk(String raw, int lang_tid, byte wiki_tid, String alias, String fmt, String domain) {
		Xow_xwiki_itm itm = xwiki_mgr.Add_bulk_row(Xol_lang_itm_.Regy(), Bry_.new_ascii_(raw));
		Tfds.Eq(alias, String_.new_ascii_(itm.Key()));
		Tfds.Eq(fmt, String_.new_ascii_(itm.Fmt()));
		Tfds.Eq(wiki_tid, itm.Wiki_tid(), "wiki_tid");
		Tfds.Eq(lang_tid, itm.Lang_id(), "lang_id");
		return this;
	}
	public Xow_xwiki_mgr_fxt Langs_ini() {
		lang_mgr.Groups().Set_bulk(Bry_.new_utf8_(String_.Concat_lines_nl
			(	"+||grp|wiki"
			,	"+|wiki|grp|english"
			,	"+|wiki|grp|europe_west"
			,	"+|wiki|grp|asia_east"
			,	"+|english|itm|en|English"
			,	"+|europe_west|itm|fr|French"
			,	"+|europe_west|itm|de|German"
			,	"+|asia_east|itm|ja|Japanese"
			)));
		return this;
	}
	public Xow_xwiki_mgr_fxt Peers_ini() {
		app.Wiki_mgr().Groups().Set_bulk(Bry_.new_utf8_(String_.Concat_lines_nl
			(	"+|core|itm|commons|commons"
			,	"+|core|itm|meta|meta;m"
			,	"+|core|itm|wikidata|d"
			,	"+|peer|itm|wiktionary|wikt;wiktionary"
			,	"+|peer|itm|wikisource|s"
			,	"+|peer|itm|wikipedia|w;wikipedia"
			)));
		return this;
	}
	public Xow_xwiki_mgr_fxt Tst_add_bulk_langs(String langs, Xow_xwiki_itm... itms) {
		xwiki_mgr.Add_bulk_langs(Bry_.new_utf8_(langs));
		Tfds.Eq_str_lines(Xto_str(itms), Xto_str(Xto_ary(itms)));
		return this;
	}
	public Xow_xwiki_mgr_fxt Tst_add_bulk_peers(String exclude, Xow_xwiki_itm... itms) {
		xwiki_mgr.Add_bulk_peers(Bry_.new_utf8_(exclude));
		Tfds.Eq_str_lines(Xto_str(itms), Xto_str(Xto_ary(itms)));
		return this;
	}
	public Xow_xwiki_mgr_fxt Tst_itms(Xow_xwiki_itm... itms) {
		Tfds.Eq_str_lines(Xto_str(itms), Xto_str(Xto_ary(itms)));
		return this;
	}
	Xow_xwiki_itm[] Xto_ary(Xow_xwiki_itm[] itms) {
		int len = itms.length;
		ListAdp rv = ListAdp_.new_();
		for (int i = 0; i < len; i++) {
			byte[] alias = itms[i].Key();
			Xow_xwiki_itm itm = xwiki_mgr.Get_by_key(alias);
			if (itm == null) itm = xwiki_null_(String_.new_utf8_(alias)); // "null", ignore
			rv.Add(itm);
		}
		return (Xow_xwiki_itm[])rv.XtoAry(Xow_xwiki_itm.class);
	}
	String Xto_str(Xow_xwiki_itm[] itms) {
		int len = itms.length;
		for (int i = 0; i < len; i++) {
			Xow_xwiki_itm itm = itms[i];
			if (Bry_.Len_eq_0(itm.Domain()))	// "null", ignore
				sb.Add(itm.Key()).Add_char_nl();
			else {
				sb.Add(itm.Key()).Add_char_pipe().Add(itm.Domain()).Add_char_pipe().Add(itm.Fmt()).Add_char_nl();
			}
		}
		return sb.XtoStrAndClear();
	}
}
