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
package gplx.xowa.mediawiki.languages;
import gplx.core.tests.Gftest;
import gplx.libs.files.Io_mgr;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.Xoa_app_fxt;
import gplx.xowa.Xoae_app;
import gplx.xowa.langs.Xol_lang_itm;
import gplx.xowa.mediawiki.XophpArray;
import gplx.xowa.mediawiki.includes.cache.localisation.XomwLocalisationCacheForXowa;
public class XomwLanguage_fxt {
	private XomwLanguage lang;
	private final Xoae_app app;
	public XomwLanguage_fxt() {
		this.app = Xoa_app_fxt.Make__app__edit();
		this.Init__lang("en");
	}
	public void Init_digitGroupingPattern(String digitGroupingPattern) {
		lang.setDigitGroupingPattern(BryUtl.NewU8(digitGroupingPattern));
	}
	public void Init__lang(String lang_code) {
		Xol_lang_itm xoLang = app.Lang_mgr().Get_by_or_load(BryUtl.NewA7(lang_code));
		this.lang = new XomwLanguage(xoLang);
	}
	public void Test_commafy(String raw, String expd) {
		GfoTstr.Eq(expd, lang.commafy(BryUtl.NewU8(raw)));
	}
	public void Test__handleExplicitPluralForms__string(String count, XophpArray forms, String expd) {
		GfoTstr.Eq(expd, (String)lang.handleExplicitPluralForms(count, forms));
	}
	public void Test__handleExplicitPluralForms__array(String count, XophpArray forms, XophpArray expd) {
		Gftest.EqAry(expd.To_ary(), ((XophpArray)lang.handleExplicitPluralForms(count, forms)).To_ary());
	}
	public void Init__pluralRulesXml(String... ary) {
		String xml = StringUtl.Replace(StringUtl.ConcatLinesNl
		( "<supplementalData>"
		, "    <version number='$Revision: 10807 $'/>"
		, "    <generation date='$Date: 2014-08-14 14:43:27 -0500 (Thu, 14 Aug 2014) $'/>"
		, "    <plurals type='cardinal'>"
		, StringUtl.ConcatLinesNl(ary)
		, "    </plurals>"
		, "</supplementalData>"
		), "'", "\"");
		XomwLocalisationCacheForXowa.Init_ip(app.Fsys_mgr().Root_dir());
		Io_mgr.Instance.SaveFilStr(app.Fsys_mgr().Root_dir().GenSubFil_nest("languages", "data", "plurals.xml"), xml);
	}
	public void Init__pluralRulesXml__en() {
		this.Init__pluralRulesXml
		( "<pluralRules locales='ast ca de en et fi fy gl it ji nl sv sw ur yi'>"
		, "    <pluralRule count='one'>i = 1 and v = 0 @integer 1</pluralRule>"
		, "    <pluralRule count='other'> @integer 0, 2~16, 100, 1000, 10000, 100000, 1000000, � @decimal 0.0~1.5, 10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0, �</pluralRule>"
		, "</pluralRules>"
		);
	}

	public void Test__getPluralRuleIndexNumber(int expd, String... ary) {
		for (String itm : ary) {
			GfoTstr.Eq(expd, lang.getPluralRuleIndexNumber(itm), itm);
		}
	}
}
