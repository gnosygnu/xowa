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
package gplx.xowa.htmls.heads; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.numbers.*;
public class Xow_fragment_mgr_tst {
	Xow_fragment_mgr_fxt fxt = new Xow_fragment_mgr_fxt();
	@Before public void init()	{fxt.Clear();}
	@Test   public void Html_js_edit_toolbar_fmt() {
		fxt.Test_fragment(Xow_fragment_mgr.Invk_html_js_edit_toolbar, String_.Concat_lines_nl
		(	"  var xowa_edit_i18n = {"
		,	"    'bold_tip'             : 'Bold text',"
		,	"    'bold_sample'          : 'Bold text',"
		,	"    'italic_tip'           : 'Italic text',"
		,	"    'italic_sample'        : 'Italic text',"
		,	"    'link_tip'             : 'Internal link',"
		,	"    'link_sample'          : 'Link title',"
		,	"    'headline_tip'         : 'Level 2 headline',"
		,	"    'headline_sample'      : 'Headline text',"
		,	"    'ulist_tip'            : 'Bulleted list',"
		,	"    'ulist_sample'         : 'Bulleted list item',"
		,	"    'olist_tip'            : 'Numbered list',"
		,	"    'olist_sample'         : 'Numbered list item'"
		,	"  };"
		));
	}
}
class Xow_fragment_mgr_fxt {
	public void Clear() {
		if (wiki == null) {
			Xoae_app app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
		}
	}	private Xowe_wiki wiki;
	public Xol_lang_itm Make_lang(String key) {return wiki.Appe().Lang_mgr().Get_by_or_new(Bry_.new_a7(key));}
	public void Test_fragment(String key, String expd) {Test_fragment(wiki.Lang(), key, expd);}
	public void Test_fragment(Xol_lang_itm lang, String key, String expd) {
		wiki.Fragment_mgr().Evt_lang_changed(lang);
		byte[] actl = (byte[])Gfo_invk_.Invk_by_key(wiki.Fragment_mgr(), key);
		Tfds.Eq_str_lines(expd, String_.new_u8(actl));
	}
}
