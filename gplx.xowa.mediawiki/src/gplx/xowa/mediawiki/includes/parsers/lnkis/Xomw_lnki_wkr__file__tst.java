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
package gplx.xowa.mediawiki.includes.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.mediawiki.includes.filerepo.*; import gplx.xowa.mediawiki.includes.filerepo.file.*;
import gplx.xowa.mediawiki.includes.media.*;
public class Xomw_lnki_wkr__file__tst {
	private final    Xomw_lnki_wkr__fxt fxt = new Xomw_lnki_wkr__fxt();
	@Before public void init() {
		fxt.Clear();
		fxt.Init__file("A.png", 300, 200);
	}
	@Test   public void Plain() {
		fxt.Test__to_html("[[File:A.png]]",     "<a href='A.png' class='image'><img alt='A.png' src='/orig/7/70/A.png' /></a>");
	}
	@Test   public void Thumb() {
		fxt.Test__to_html("[[File:A.png|thumb]]", "<div class='thumb tright'><div class='thumbinner' style='width:222px;'><a href='A.png' class='image'><img alt='A.png' src='/thumb/7/70/A.png/220px-A.png' class='thumbimage' /></a>  <div class='thumbcaption'><div class='magnify'><a href='' class='internal'></a></div></div></div></div>");
	}
	@Test   public void Size() {
		fxt.Test__to_html("[[File:A.png|123x456px]]", "<a href='A.png' class='image'><img alt='A.png' src='/thumb/7/70/A.png/123px-A.png' /></a>");
	}
	@Test   public void fitBoxWidth() {
		// COMMENT:"Height is the relative smaller dimension, so scale width accordingly"
		// consider file of 200,100 (2:1)
		// EX_1: view is 120,40 (3:1)
		// - dimensions are either (a) 120,80 or (b) 80,40
		// - use (b) 80,40
		// EX_2: view is 120,80 (1.5:1)
		// - dimensions are either (a) 120,60 or (b) 160,80
		// - use (a) 120,60
		fxt.Init__file("A.png", 200, 100);
		fxt.Test__to_html__has("[[File:A.png|120x40px]]", "/80px-A.png");
		fxt.Test__to_html__has("[[File:A.png|120x80px]]", "/120px-A.png");
	}

	@Test   public void Test__parseWidthParam() {
		int[] img_size = new int[2];
		// WxHpx
		fxt.Test__parseWidthParam(img_size, "12x34px"  , 12, 34);
		// WxH
		fxt.Test__parseWidthParam(img_size, "12x34"    , 12, 34);
		// Wpx
		fxt.Test__parseWidthParam(img_size, "12px"     , 12, 0);
		// W
		fxt.Test__parseWidthParam(img_size, "12"       , 12, 0);
		// 12x
		fxt.Test__parseWidthParam(img_size, "12x"      , 12, 0);
		// x34
		fxt.Test__parseWidthParam(img_size, "x34"      , 0, 34);
	}
}
class Xomw_lnki_wkr__fxt {
	private final    Xomw_lnki_wkr wkr;
	private final    Xomw_parser_ctx pctx;
	private final    Xomw_parser_bfr pbfr = new Xomw_parser_bfr();
	private final    Xomw_file_finder__mock file_finder;
	private final    Xomw_FileRepo repo = new Xomw_FileRepo(Bry_.new_a7("/orig"), Bry_.new_a7("/thumb"));
	private boolean apos = true;
	public Xomw_lnki_wkr__fxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app);
		Xomw_parser parser = new Xomw_parser();
		wkr = parser.Lnki_wkr();

		// env
		file_finder = new Xomw_file_finder__mock(parser.Env());
		parser.Env().File_finder_(file_finder);
		parser.Env().Magic_word_mgr().Add(Bry_.new_u8("img_thumbnail"), Bool_.Y, Bry_.Ary("thumb"));
		parser.Env().Magic_word_mgr().Add(Bry_.new_u8("img_width"), Bool_.Y, Bry_.Ary("$1px"));
		parser.Init_by_wiki(wiki);

		// ctx
		pctx = new Xomw_parser_ctx();
		pctx.Init_by_page(Xomw_Title.newFromText(Bry_.new_a7("Page_1")));
	}
	public void Clear() {
		wkr.Clear_state();
	}
	public void Init__file(String title, int w, int h) {
		file_finder.Add(title, repo, w, h, Xomw_MediaHandlerFactory.Mime__image__png);
	}
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		wkr.Replace_internal_links(pctx, pbfr.Init(src_bry));
		if (apos) expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		Gftest.Eq__ary__lines(expd, pbfr.Rslt().To_str_and_clear(), src_str);
	}
	public void Test__to_html(String src_str, String expd) {
		if (apos) expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		Gftest.Eq__ary__lines(expd, Exec__to_html(src_str), src_str);
	}
	public void Test__to_html__has(String src_str, String expd) {
		if (apos) expd = gplx.langs.htmls.Gfh_utl.Replace_apos(expd);
		Gftest.Eq__bool_y(String_.Has(Exec__to_html(src_str), expd));
	}
	private String Exec__to_html(String src_str) {
		byte[] src_bry = Bry_.new_u8(src_str);
		wkr.Replace_internal_links(pctx, pbfr.Init(src_bry));
		wkr.Replace_link_holders(pctx, pbfr);
		return pbfr.Rslt().To_str_and_clear();
	}
	public void Test__parseWidthParam(int[] img_size, String src_str, int expd_w, int expd_h) {
		wkr.parseWidthParam(img_size, Bry_.new_u8(src_str));
		Gftest.Eq__int(expd_w, img_size[0], "w");
		Gftest.Eq__int(expd_h, img_size[1], "h");
	}
}
