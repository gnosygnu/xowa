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
package gplx.xowa.wikis.domains;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.wrappers.IntRef;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import org.junit.*; import gplx.xowa.langs.*;
public class Xow_abrv_wm_tst {
	private Xow_abrv_wm_fxt fxt = new Xow_abrv_wm_fxt();
	@Test public void Parse() {
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
	@Test public void Parse_override() {
		fxt.Test_parse("arwikimedia"			, Xol_lang_stub_.Id_es		, Xow_domain_tid_.Tid__wikimedia);
		fxt.Test_parse("ukwikimedia"			, Xol_lang_stub_.Id_uk		, Xow_domain_tid_.Tid__wikimedia);
	}
	@Test public void To_domain_itm() {
		fxt.Test_to_domain_itm("enwiki"		, "en"		, "en.wikipedia.org");
		fxt.Test_to_domain_itm("zh_yuewiki"	, "zh-yue"	, "zh-yue.wikipedia.org");
	}
	@Test public void To_domain_bry() {
		fxt.Test_to_domain_bry("enwiki"		, "en.wikipedia.org");
		fxt.Test_to_domain_bry("zh_yuewiki"	, "zh-yue.wikipedia.org");
		fxt.Test_to_domain_bry("arwikimedia", "ar.wikimedia.org");
		fxt.Test_to_domain_bry("ukwikimedia", "ua.wikimedia.org");
	}
	@Test public void To_abrv() {
		fxt.Test_to_abrv("simple.wikipedia.org"		, "simplewiki");
		fxt.Test_to_abrv("en.wikipedia.org"			, "enwiki");
		fxt.Test_to_abrv("commons.wikimedia.org"	, "commonswiki");
	}
	@Test public void To_abrv_by_lang() {
		fxt.Test_to_abrv_by_lang("en", Xow_domain_tid_.Tid__wikipedia, "enwiki");
	}
	@Test public void Wikimania() {
		fxt.Test_parse("wikimaniawiki", Xol_lang_stub_.Id__intl, Xow_domain_tid_.Tid__wikimania);
		fxt.Test_to_domain_itm("wikimaniawiki", "", "wikimania.wikimedia.org");
		fxt.Test_to_abrv("wikimania.wikimedia.org", "wikimaniawiki");
	}
	@Test public void Wikisources() {
		fxt.Test_parse("sourceswiki", Xol_lang_stub_.Id_en, Xow_domain_tid_.Tid__wikisource_org);
		fxt.Test_to_domain_itm("sourceswiki", "", "wikisource.org");
		fxt.Test_to_abrv("wikisource.org", "sourceswiki");
	}
}
class Xow_abrv_wm_fxt {
	public void Test_parse(String raw, int expd_lang_id, int expd_domain_tid) {
		byte[] raw_bry = BryUtl.NewA7(raw);
		Xow_abrv_wm abrv = Xow_abrv_wm_.Parse_to_abrv_or_null(raw_bry);
		Xol_lang_stub actl_lang_itm = abrv.Lang_actl();
		GfoTstr.EqObj(expd_lang_id	, actl_lang_itm == null ? Xol_lang_stub_.Id__unknown :  actl_lang_itm.Id());
		GfoTstr.EqObj(expd_domain_tid	, abrv.Domain_type());
	}
	public void Test_parse_null(String raw) {
		byte[] raw_bry = BryUtl.NewA7(raw);
		Xow_abrv_wm abrv = Xow_abrv_wm_.Parse_to_abrv_or_null(raw_bry);
		GfoTstr.EqBoolY(abrv == null);
	}
	public void Test_to_abrv(String domain_str, String expd) {
		Xow_domain_itm domain = Xow_domain_itm_.parse(BryUtl.NewA7(domain_str));
		byte[] actl = Xow_abrv_wm_.To_abrv(domain);
		GfoTstr.EqObj(expd, StringUtl.NewA7(actl));
	}
	public void Test_to_abrv_by_lang(String lang_key, int wiki_tid, String expd) {
		BryWtr tmp_bfr = BryWtr.NewAndReset(255);
		Xow_abrv_wm_.To_abrv(tmp_bfr, BryUtl.NewA7(lang_key), IntRef.New(wiki_tid));
		GfoTstr.Eq(expd, tmp_bfr.ToStrAndClear(), "to_abrv");
	}
	public void Test_to_domain_bry(String wmf_key, String expd_domain) {
		GfoTstr.EqObj(expd_domain		, StringUtl.NewA7(Xow_abrv_wm_.Parse_to_domain_bry(BryUtl.NewA7(wmf_key))));
	}
	public void Test_to_domain_itm(String wmf_key, String expd_lang_key, String expd_domain) {
		Xow_domain_itm domain = Xow_abrv_wm_.Parse_to_domain_itm(BryUtl.NewA7(wmf_key));
		GfoTstr.EqObj(expd_lang_key	, StringUtl.NewA7(domain.Lang_actl_key()));
		GfoTstr.EqObj(expd_domain		, StringUtl.NewA7(domain.Domain_bry()));
	}
}
