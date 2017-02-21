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
package gplx.xowa.wikis.domains; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*; import gplx.xowa.langs.*;
public class Xow_abrv_wm_tst {
	private Xow_abrv_wm_fxt fxt = new Xow_abrv_wm_fxt();
	@Test  public void Parse() {
		fxt.Test_parse("foundationwiki"			, Xol_lang_stub_.Id__intl	, Xow_domain_tid_.Tid__wmfblog);
		fxt.Test_parse("wikidatawiki"			, Xol_lang_stub_.Id__intl	, Xow_domain_tid_.Tid__wikidata);
		fxt.Test_parse("mediawikiwiki"			, Xol_lang_stub_.Id__intl	, Xow_domain_tid_.Tid__mediawiki);
		fxt.Test_parse("commonswiki"			, Xol_lang_stub_.Id__intl	, Xow_domain_tid_.Tid__commons);
		fxt.Test_parse("specieswiki"			, Xol_lang_stub_.Id__intl	, Xow_domain_tid_.Tid__species);
		fxt.Test_parse("metawiki"				, Xol_lang_stub_.Id__intl	, Xow_domain_tid_.Tid__meta);
		fxt.Test_parse("incubatorwiki"			, Xol_lang_stub_.Id__intl	, Xow_domain_tid_.Tid__incubator);
		fxt.Test_parse("enwiki"					, Xol_lang_stub_.Id_en		, Xow_domain_tid_.Tid__wikipedia);
		fxt.Test_parse("enwiktionary"			, Xol_lang_stub_.Id_en		, Xow_domain_tid_.Tid__wiktionary);
		fxt.Test_parse("enwikisource"			, Xol_lang_stub_.Id_en		, Xow_domain_tid_.Tid__wikisource);
		fxt.Test_parse("enwikibooks"			, Xol_lang_stub_.Id_en		, Xow_domain_tid_.Tid__wikibooks);
		fxt.Test_parse("enwikiversity"			, Xol_lang_stub_.Id_en		, Xow_domain_tid_.Tid__wikiversity);
		fxt.Test_parse("enwikiquote"			, Xol_lang_stub_.Id_en		, Xow_domain_tid_.Tid__wikiquote);
		fxt.Test_parse("enwikinews"				, Xol_lang_stub_.Id_en		, Xow_domain_tid_.Tid__wikinews);
		fxt.Test_parse("enwikivoyage"			, Xol_lang_stub_.Id_en		, Xow_domain_tid_.Tid__wikivoyage);
		fxt.Test_parse("frwiki"					, Xol_lang_stub_.Id_fr		, Xow_domain_tid_.Tid__wikipedia);
		fxt.Test_parse_null("unknown");
		fxt.Test_parse("plwikimedia"			, Xol_lang_stub_.Id_pl		, Xow_domain_tid_.Tid__wikimedia);
	}
	@Test  public void Parse_override() {
		fxt.Test_parse("arwikimedia"			, Xol_lang_stub_.Id_es		, Xow_domain_tid_.Tid__wikimedia);
		fxt.Test_parse("ukwikimedia"			, Xol_lang_stub_.Id_uk		, Xow_domain_tid_.Tid__wikimedia);
	}
	@Test   public void To_domain_itm() {
		fxt.Test_to_domain_itm("enwiki"		, "en"		, "en.wikipedia.org");
		fxt.Test_to_domain_itm("zh_yuewiki"	, "zh-yue"	, "zh-yue.wikipedia.org");
	}
	@Test   public void To_domain_bry() {
		fxt.Test_to_domain_bry("enwiki"		, "en.wikipedia.org");
		fxt.Test_to_domain_bry("zh_yuewiki"	, "zh-yue.wikipedia.org");
		fxt.Test_to_domain_bry("arwikimedia", "ar.wikimedia.org");
		fxt.Test_to_domain_bry("ukwikimedia", "ua.wikimedia.org");
	}
	@Test  public void To_abrv() {
		fxt.Test_to_abrv("simple.wikipedia.org"		, "simplewiki");
		fxt.Test_to_abrv("en.wikipedia.org"			, "enwiki");
		fxt.Test_to_abrv("commons.wikimedia.org"	, "commonswiki");
	}
	@Test  public void To_abrv_by_lang() {
		fxt.Test_to_abrv_by_lang("en", Xow_domain_tid_.Tid__wikipedia, "enwiki");
	}
}
class Xow_abrv_wm_fxt {
	public void Test_parse(String raw, int expd_lang_id, int expd_domain_tid) {
		byte[] raw_bry = Bry_.new_a7(raw);
		Xow_abrv_wm abrv = Xow_abrv_wm_.Parse_to_abrv_or_null(raw_bry);
		Xol_lang_stub actl_lang_itm = abrv.Lang_actl();
		Tfds.Eq(expd_lang_id	, actl_lang_itm == null ? Xol_lang_stub_.Id__unknown :  actl_lang_itm.Id());
		Tfds.Eq(expd_domain_tid	, abrv.Domain_type());
	}
	public void Test_parse_null(String raw) {
		byte[] raw_bry = Bry_.new_a7(raw);
		Xow_abrv_wm abrv = Xow_abrv_wm_.Parse_to_abrv_or_null(raw_bry);
		Tfds.Eq_true(abrv == null);
	}
	public void Test_to_abrv(String domain_str, String expd) {
		Xow_domain_itm domain = Xow_domain_itm_.parse(Bry_.new_a7(domain_str));
		byte[] actl = Xow_abrv_wm_.To_abrv(domain);
		Tfds.Eq(expd, String_.new_a7(actl));
	}
	public void Test_to_abrv_by_lang(String lang_key, int wiki_tid, String expd) {
		Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
		Xow_abrv_wm_.To_abrv(tmp_bfr, Bry_.new_a7(lang_key), gplx.core.primitives.Int_obj_ref.New(wiki_tid));
		Tfds.Eq_str(expd, tmp_bfr.To_str_and_clear(), "to_abrv");
	}
	public void Test_to_domain_bry(String wmf_key, String expd_domain) {
		Tfds.Eq(expd_domain		, String_.new_a7(Xow_abrv_wm_.Parse_to_domain_bry(Bry_.new_a7(wmf_key))));
	}
	public void Test_to_domain_itm(String wmf_key, String expd_lang_key, String expd_domain) {
		Xow_domain_itm domain = Xow_abrv_wm_.Parse_to_domain_itm(Bry_.new_a7(wmf_key));
		Tfds.Eq(expd_lang_key	, String_.new_a7(domain.Lang_actl_key()));
		Tfds.Eq(expd_domain		, String_.new_a7(domain.Domain_bry()));
	}
}
