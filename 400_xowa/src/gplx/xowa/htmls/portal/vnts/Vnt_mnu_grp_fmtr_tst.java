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
package gplx.xowa.htmls.portal.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.portal.*;
import org.junit.*; import gplx.xowa.langs.vnts.*;
public class Vnt_mnu_grp_fmtr_tst {		
	@Before public void init() {fxt.Clear();} private final    Vnt_mnu_grp_fmtr_fxt fxt = new Vnt_mnu_grp_fmtr_fxt();
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
	private final    Xol_vnt_regy mgr = new Xol_vnt_regy();
	public void Clear() {
		this.Init_grp("Choose lang", "zh-hans", "Simplified", "zh-hant", "Traditional", "zh-cn", "China", "zh-hk", "Hong Kong", "zh-mo", "Macau", "zh-sg", "Singapore", "zh-tw", "Taiwan");
	}
	public void Init_grp(String text, String... langs) {
		mgr.Clear();
		int len = langs.length;
		String lang_code = "";
		for (int i = 0; i < len; ++i) {
			String lang = langs[i];
			if (i % 2 == 0)
				lang_code = lang;
			else {
				mgr.Add(Bry_.new_u8(lang_code), Bry_.new_u8(lang));
			}
		}
	}
	public void Test_to_str(String page_href, String selected_vnt, String expd) {
		Vnt_mnu_grp_fmtr vnt_grp_fmtr = new Vnt_mnu_grp_fmtr();
		Bry_bfr bfr = Bry_bfr_.New();
		vnt_grp_fmtr.Init(mgr, Bry_.new_u8(page_href), Bry_.new_a7("zh.wikipedia.org"), Bry_.new_u8(selected_vnt));
		vnt_grp_fmtr.Bfr_arg__add(bfr);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
}
