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
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*;
public class Vnt_mnu_grp_fmtr_tst {		
	@Before public void init() {fxt.Clear();} private final Vnt_mnu_grp_fmtr_fxt fxt = new Vnt_mnu_grp_fmtr_fxt();
	@Test  public void Basic() {
//			fxt.Test_to_str("Earth", "zh-hk", String_.Concat_lines_nl_skip_last
//			( ""
//			, "    <div id='p-variants' role='navigation' class='vectorMenu' aria-labelledby='p-variants-label'>"
//			, "      <h3 id='p-variants-label'><span>Choose lang</span><a href='#'></a></h3>"
//			, "      <div class='menu'>"
//			, "        <ul>"
//			, "          <li id='ca-varlang-0'><a href='/wiki/Earth?xowa_vnt=zh-hans' lang='zh-hans' hreflang='zh-hans'>Simplified</a></li>"
//			, "          <li id='ca-varlang-1'><a href='/wiki/Earth?xowa_vnt=zh-hant' lang='zh-hant' hreflang='zh-hant'>Traditional</a></li>"
//			, "          <li id='ca-varlang-2'><a href='/wiki/Earth?xowa_vnt=zh-cn' lang='zh-cn' hreflang='zh-cn'>China</a></li>"
//			, "          <li id='ca-varlang-3' class='selected'><a href='/wiki/Earth?xowa_vnt=zh-hk' lang='zh-hk' hreflang='zh-hk'>Hong Kong</a></li>"
//			, "          <li id='ca-varlang-4'><a href='/wiki/Earth?xowa_vnt=zh-mo' lang='zh-mo' hreflang='zh-mo'>Macau</a></li>"
//			, "          <li id='ca-varlang-5'><a href='/wiki/Earth?xowa_vnt=zh-sg' lang='zh-sg' hreflang='zh-sg'>Singapore</a></li>"
//			, "          <li id='ca-varlang-6'><a href='/wiki/Earth?xowa_vnt=zh-tw' lang='zh-tw' hreflang='zh-tw'>Taiwan</a></li>"
//			, "        </ul>"
//			, "      </div>"
//			, "    </div>"
//			));
	}
}
class Vnt_mnu_grp_fmtr_fxt {
	private Vnt_mnu_grp vnt_grp;
	public void Clear() {
		this.Init_grp("Choose lang", "zh-hans", "Simplified", "zh-hant", "Traditional", "zh-cn", "China", "zh-hk", "Hong Kong", "zh-mo", "Macau", "zh-sg", "Singapore", "zh-tw", "Taiwan");
	}
	public void Init_grp(String text, String... langs) {
		vnt_grp = new Vnt_mnu_grp();
		int len = langs.length;
		String lang_code = "";
		for (int i = 0; i < len; ++i) {
			String lang = langs[i];
			if (i % 2 == 0)
				lang_code = lang;
			else {
				Vnt_mnu_itm itm = new Vnt_mnu_itm(Bry_.new_u8(lang_code), Bry_.new_u8(lang));
				vnt_grp.Add(itm);
			}
		}
	}
	public void Test_to_str(String page_href, String selected_vnt, String expd) {
		Vnt_mnu_grp_fmtr vnt_grp_fmtr = new Vnt_mnu_grp_fmtr();
		Bry_bfr bfr = Bry_bfr.new_();
		vnt_grp_fmtr.Init(vnt_grp, Bry_.new_u8(page_href), Bry_.new_a7("zh.wikipedia.org"), Bry_.new_u8(selected_vnt));
		vnt_grp_fmtr.XferAry(bfr, 0);
		Tfds.Eq_str_lines(expd, bfr.Xto_str_and_clear());
	}
}
