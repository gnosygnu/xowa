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
			app = Xoa_app_fxt.app_();
			wiki = Xoa_app_fxt.wiki_tst_(app);
			mgr = app.Html_mgr().Js_cleaner();
		}
	}	private Xoae_app app; Xowe_wiki wiki; Xoh_js_cleaner mgr;
	public void Test_clean(String raw_str, String expd) {
		byte[] raw = Bry_.new_a7(raw_str);
		byte[] actl = mgr.Clean(wiki, raw, 0, raw.length);
		if (actl == null) actl = raw;
		Tfds.Eq(expd, String_.new_a7(actl));
	}
}
