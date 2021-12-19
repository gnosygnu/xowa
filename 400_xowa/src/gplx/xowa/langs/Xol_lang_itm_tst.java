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
package gplx.xowa.langs;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.xowa.*;
import org.junit.*;
public class Xol_lang_itm_tst {
	private final Xol_lang_itm_fxt fxt = new Xol_lang_itm_fxt();
	@Before public void init() {fxt.Clear();}
	@Test public void Fallback_bry__dupes() { // ISSUE#:330; DATE:2019-02-17
		Xol_lang_itm lang = fxt.Make("qqq");
		fxt.Init_fallback_bry(lang, "en");
		fxt.Init_fallback_bry(lang, "en"); // dupes would throw error; note that dupes can happen b/c "en" is default language; EX: isRTL("gl") -> "pt-br,en" -> "pt,en"
		GfoTstr.Eq("en", lang.Fallback_bry());
	}
}
class Xol_lang_itm_fxt {
	private Xop_fxt fxt;
	public void Clear() {
		fxt = new Xop_fxt();
	}
	public Xol_lang_itm Make(String code) {
		return new Xol_lang_itm(fxt.App().Lang_mgr(), BryUtl.NewA7(code));
	}
	public void Init_fallback_bry(Xol_lang_itm lang, String itm) {
		lang.Fallback_bry_(BryUtl.NewU8(itm));
	}
}
