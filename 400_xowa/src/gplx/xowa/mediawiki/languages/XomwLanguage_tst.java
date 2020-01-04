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
package gplx.xowa.mediawiki.languages; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.langs.*;
import gplx.xowa.mediawiki.includes.cache.localisation.*;
public class XomwLanguage_tst {
	private final    XomwLanguage_fxt fxt = new XomwLanguage_fxt();
	@Test  public void Commafy_standard() {
		// basic
		fxt.Test_commafy("1"              , "1");
		fxt.Test_commafy("12"             , "12");
		fxt.Test_commafy("123"            , "123");
		fxt.Test_commafy("1234"           , "1,234");
		fxt.Test_commafy("12345"          , "12,345");
		fxt.Test_commafy("123456"         , "123,456");
		fxt.Test_commafy("1234567"        , "1,234,567");
		fxt.Test_commafy("12345678"       , "12,345,678");
		fxt.Test_commafy("123456789"      , "123,456,789");
		fxt.Test_commafy("1234567890"     , "1,234,567,890");

		// decimal
		fxt.Test_commafy("1.234"          , "1.234");
		fxt.Test_commafy("1234.456"       , "1,234.456");
		fxt.Test_commafy(".456"           , ".456");

		// negative
		fxt.Test_commafy("-1"             , "-1");
		fxt.Test_commafy("-12"            , "-12");
		fxt.Test_commafy("-123"           , "-123");
		fxt.Test_commafy("-1234"          , "-1,234");
		fxt.Test_commafy("-12345"         , "-12,345");
		fxt.Test_commafy("-123456"        , "-123,456");
		fxt.Test_commafy("-1234567"       , "-1,234,567");
		fxt.Test_commafy("-12345678"      , "-12,345,678");
		fxt.Test_commafy("-123456789"     , "-123,456,789");
		fxt.Test_commafy("-1234567890"    , "-1,234,567,890");
	}
	@Test  public void Commafy_custom_standard() {
		fxt.Init_digitGroupingPattern("###,###");

		// basic
		fxt.Test_commafy("1"              , "1");
		fxt.Test_commafy("12"             , "12");
		fxt.Test_commafy("123"            , "123");
		fxt.Test_commafy("1234"           , "1,234");
		fxt.Test_commafy("12345"          , "12,345");
		fxt.Test_commafy("123456"         , "123,456");
		fxt.Test_commafy("1234567"        , "1,234,567");
		fxt.Test_commafy("12345678"       , "12,345,678");
		fxt.Test_commafy("123456789"      , "123,456,789");
		fxt.Test_commafy("1234567890"     , "1,234,567,890");

		// decimal
		fxt.Test_commafy("1.234"          , "1.234");
		fxt.Test_commafy("1234.456"       , "1,234.456");
		fxt.Test_commafy(".456"           , ".456");

		// negative
		fxt.Test_commafy("-1"             , "-1");
		fxt.Test_commafy("-12"            , "-12");
		fxt.Test_commafy("-123"           , "-123");
		fxt.Test_commafy("-1234"          , "-1,234");
		fxt.Test_commafy("-12345"         , "-12,345");
		fxt.Test_commafy("-123456"        , "-123,456");
		fxt.Test_commafy("-1234567"       , "-1,234,567");
		fxt.Test_commafy("-12345678"      , "-12,345,678");
		fxt.Test_commafy("-123456789"     , "-123,456,789");
		fxt.Test_commafy("-1234567890"    , "-1,234,567,890");
	}
	@Test  public void Commafy_custom_hindi() {
		fxt.Init_digitGroupingPattern("##,##,###");

		// basic
		fxt.Test_commafy("1"              , "1");
		fxt.Test_commafy("12"             , "12");
		fxt.Test_commafy("123"            , "123");
		fxt.Test_commafy("1234"           , "1,234");
		fxt.Test_commafy("12345"          , "12,345");
		fxt.Test_commafy("123456"         , "1,23,456");
		fxt.Test_commafy("1234567"        , "12,34,567");
		fxt.Test_commafy("12345678"       , "1,23,45,678");
		fxt.Test_commafy("123456789"      , "12,34,56,789");
		fxt.Test_commafy("1234567890"     , "1,23,45,67,890");

		// decimal
		fxt.Test_commafy("1.234"          , "1.234");
		fxt.Test_commafy("1234.456"       , "1,234.456");
		fxt.Test_commafy(".456"           , ".456");

		// negative
		fxt.Test_commafy("-1"             , "-1");
		fxt.Test_commafy("-12"            , "-12");
		fxt.Test_commafy("-123"           , "-123");
		fxt.Test_commafy("-1234"          , "-1,234");
		fxt.Test_commafy("-12345"         , "-12,345");
		fxt.Test_commafy("-123456"        , "-1,23,456");
		fxt.Test_commafy("-1234567"       , "-12,34,567");
		fxt.Test_commafy("-12345678"      , "-1,23,45,678");
		fxt.Test_commafy("-123456789"     , "-12,34,56,789");
		fxt.Test_commafy("-1234567890"    , "-1,23,45,67,890");
	}
	@Test   public void handleExplicitPluralForms() {
		fxt.Test__handleExplicitPluralForms__string("1", XophpArray.New().Add("1=one"), "one");
		fxt.Test__handleExplicitPluralForms__array("1", XophpArray.New().Add("no_match"), XophpArray.New().Add(0, "no_match"));
	}
	@Test   public void getPluralRuleIndexNumber() {
		fxt.Init__pluralRulesXml
		( "<pluralRules locales='ast ca de en et fi fy gl it ji nl sv sw ur yi'>"
		, "    <pluralRule count='one'>i = 1 and v = 0 @integer 1</pluralRule>"
		, "    <pluralRule count='other'> @integer 0, 2~16, 100, 1000, 10000, 100000, 1000000, … @decimal 0.0~1.5, 10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0, …</pluralRule>"
		, "</pluralRules>"
		, "<pluralRules locales='ru uk'>"
		, "    <pluralRule count='one'>v = 0 and i % 10 = 1 and i % 100 != 11 @integer 1, 21, 31, 41, 51, 61, 71, 81, 101, 1001, …</pluralRule>"
		, "    <pluralRule count='few'>v = 0 and i % 10 = 2..4 and i % 100 != 12..14 @integer 2~4, 22~24, 32~34, 42~44, 52~54, 62, 102, 1002, …</pluralRule>"
		, "    <pluralRule count='many'>v = 0 and i % 10 = 0 or v = 0 and i % 10 = 5..9 or v = 0 and i % 100 = 11..14 @integer 0, 5~19, 100, 1000, 10000, 100000, 1000000, …</pluralRule>"
		, "    <pluralRule count='other'>   @decimal 0.0~1.5, 10.0, 100.0, 1000.0, 10000.0, 100000.0, 1000000.0, …</pluralRule>"
		, "</pluralRules>"
		);
		fxt.Init__lang("qqq");
		fxt.Test__getPluralRuleIndexNumber(0, "0", "1", "2", "1.1");

		fxt.Init__lang("en");
		fxt.Test__getPluralRuleIndexNumber(0, "1");
		fxt.Test__getPluralRuleIndexNumber(1, "2", "1.1", "3");

		fxt.Init__lang("ru");
		fxt.Test__getPluralRuleIndexNumber(0, "1");
		fxt.Test__getPluralRuleIndexNumber(1, "2");
		fxt.Test__getPluralRuleIndexNumber(2, "0", "5", "12", "19", "100");
		fxt.Test__getPluralRuleIndexNumber(3, "0.0", "1.5", "10.0");
	}
}
