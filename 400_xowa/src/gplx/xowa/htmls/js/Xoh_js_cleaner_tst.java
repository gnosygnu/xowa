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
package gplx.xowa.htmls.js; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import org.junit.*;
public class Xoh_js_cleaner_tst {
	@Before public void init() {fxt.Init();} private Xoh_js_cleaner_fxt fxt = new Xoh_js_cleaner_fxt();
	@Test   public void Basic() {fxt.Test_clean("<i>a</i>", "<i>a</i>");}
	@Test   public void Js_nde() {fxt.Test_clean("a<script>b</script>c", "a&lt;script>b</script>c");}
	@Test   public void Js_atr() {fxt.Test_clean("a<span onmouseover = 'fail'>b</span>c", "a<span onMouseOver&#61; 'fail'>b</span>c");} 
	@Test   public void Js_atr_noop() {fxt.Test_clean("a onmouseover b", "a onmouseover b");} 
	@Test   public void Js_atr_noop_regionSelect() {fxt.Test_clean("regionSelect=2", "regionSelect=2");} 
}
class Xoh_js_cleaner_fxt {
	public void Init() {
		if (mgr == null) {
			app = Xoa_app_fxt.Make__app__edit();
			wiki = Xoa_app_fxt.Make__wiki__edit(app);
			mgr = wiki.Html_mgr().Js_cleaner();
		}
	}	private Xoae_app app; Xowe_wiki wiki; Xoh_js_cleaner mgr;
	public void Test_clean(String raw_str, String expd) {
		byte[] raw = Bry_.new_a7(raw_str);
		byte[] actl = mgr.Clean(wiki, raw, 0, raw.length);
		if (actl == null) actl = raw;
		Tfds.Eq(expd, String_.new_a7(actl));
	}
}
