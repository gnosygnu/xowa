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
public class XomwLanguageTest {
	private final    XomwLanguageFxt fxt = new XomwLanguageFxt();
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
}
class XomwLanguageFxt {
	private final    XomwLanguage lang;
	public XomwLanguageFxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xol_lang_itm xoLang = new Xol_lang_itm(app.Lang_mgr(), Bry_.new_a7("en"));
		this.lang = new XomwLanguage(xoLang);
	}
	public void Init_digitGroupingPattern(String digitGroupingPattern) {
		lang.setDigitGroupingPattern(Bry_.new_u8(digitGroupingPattern));
	}
	public void Test_commafy(String raw, String expd) {
		Gftest.Eq__str(expd, lang.commafy(Bry_.new_u8(raw)));
	}
}
