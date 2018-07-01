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
package gplx.xowa.mediawiki.includes.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.mediawiki.includes.filerepo.*; import gplx.xowa.mediawiki.includes.filerepo.file.*;
import gplx.xowa.mediawiki.includes.media.*;
public class Xomw_lnki_wkr__file__tst {
	private final    Xomw_lnki_wkr__fxt fxt = new Xomw_lnki_wkr__fxt();
	@Before public void init() {
		fxt.Clear();
		fxt.Init__file("File:A.png", 300, 200);
	}
	@Test   public void Orig() {
		// basic
		fxt.Test__to_html("[[File:A.png]]", "<a href='/wiki/File:A.png' class='image'><img alt='A.png' src='/orig/7/70/A.png' width='300' height='200' /></a>");

		// caption
		fxt.Test__to_html("[[File:A.png|abc]]", "<a href='/wiki/File:A.png' class='image' title='abc'><img alt='abc' src='/orig/7/70/A.png' width='300' height='200' /></a>");
	}
	@Test   public void Thumb() {
		// basic
		fxt.Test__to_html("[[File:A.png|thumb]]", "<div class='thumb tright'><div class='thumbinner' style='width:222px;'><a href='/wiki/File:A.png' class='image'><img alt='A.png' src='/thumb/7/70/A.png/220px-A.png' width='220' height='146' class='thumbimage' /></a>  <div class='thumbcaption'><div class='magnify'><a href='/wiki/File:A.png' class='internal' title='enlarge'></a></div></div></div></div>");

		// caption
		fxt.Test__to_html("[[File:A.png|thumb|abc]]", "<div class='thumb tright'><div class='thumbinner' style='width:222px;'><a href='/wiki/File:A.png' class='image'><img alt='' src='/thumb/7/70/A.png/220px-A.png' width='220' height='146' class='thumbimage' /></a>  <div class='thumbcaption'><div class='magnify'><a href='/wiki/File:A.png' class='internal' title='enlarge'></a></div>abc</div></div></div>");
	}
	@Test   public void Size() {
		fxt.Test__to_html("[[File:A.png|123x456px]]", "<a href='/wiki/File:A.png' class='image'><img alt='A.png' src='/thumb/7/70/A.png/123px-A.png' width='123' height='82' /></a>");
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
		fxt.Init__file("File:A.png", 200, 100);
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
	private final    XomwParserCtx pctx;
	private final    XomwParserBfr pbfr = new XomwParserBfr();
	private final    XomwEnv env;
	private final    XomwFileFinderMock file_finder;
	private final    XomwFileRepo repo = new XomwFileRepo(Bry_.new_a7("/orig"), Bry_.new_a7("/thumb"));
	private boolean apos = true;
	public Xomw_lnki_wkr__fxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app);

		XomwParser parser = new XomwParser(XomwEnv.NewTestByApp(app));
		wkr = parser.Lnki_wkr();

		// env
		file_finder = new XomwFileFinderMock(parser.Env());
		env = parser.Env();
		env.File_finder_(file_finder);
		env.Magic_word_mgr().Add(Bry_.new_u8("img_thumbnail"), Bool_.Y, Bry_.Ary("thumb"));
		env.Magic_word_mgr().Add(Bry_.new_u8("img_width"), Bool_.Y, Bry_.Ary("$1px"));
		env.Message_mgr().Add("thumbnail-more", "enlarge", env.Language());
		parser.Init_by_wiki(wiki);

		// ctx
		pctx = new XomwParserCtx();
		pctx.Init_by_page(XomwTitle.newFromText(env, Bry_.new_a7("Page_1")));
	}
	public void Clear() {
		wkr.Clear_state();
	}
	public void Init__file(String title, int w, int h) {
		file_finder.Add(title, repo, w, h, XomwMediaHandlerFactory.Mime__image__png);
	}
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		wkr.replaceInternalLinks(pbfr.Init(src_bry), env, pctx);
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
		wkr.replaceInternalLinks(pbfr.Init(src_bry), env, pctx);
		wkr.replaceLinkHolders(pbfr);
		return pbfr.Rslt().To_str_and_clear();
	}
	public void Test__parseWidthParam(int[] img_size, String src_str, int expd_w, int expd_h) {
		wkr.parseWidthParam(img_size, Bry_.new_u8(src_str));
		Gftest.Eq__int(expd_w, img_size[0], "w");
		Gftest.Eq__int(expd_h, img_size[1], "h");
	}
}
