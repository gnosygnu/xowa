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
package gplx.xowa.htmls.core.wkrs.imgs.atrs;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.StringUtl;
import gplx.types.custom.brys.rdrs.BryRdrErrWkr;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xoa_app_fxt;
import gplx.xowa.htmls.Xoh_page;
import gplx.xowa.htmls.core.wkrs.Xoh_hdoc_ctx;
import gplx.xowa.htmls.core.wkrs.Xoh_itm_parser;
import gplx.xowa.htmls.core.wkrs.Xoh_itm_parser_fxt;
import gplx.xowa.wikis.domains.Xow_domain_itm_;
import org.junit.Before;
import org.junit.Test;
public class Xoh_img_src_data_tst {
	private final Xoh_img_src_data_fxt fxt = new Xoh_img_src_data_fxt();
	@Before public void init() {fxt.Clear();}
	@Test public void Basic() {
		fxt.Test__parse("file:///C:/xowa/file/en.wikipedia.org/orig/7/0/A.png"						, "en.wikipedia.org"		, BoolUtl.Y, "A.png",  -1, -1, -1);
		fxt.Test__parse("file:///C:/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/220px.png"		, "commons.wikimedia.org"	, BoolUtl.N, "A.png", 220, -1, -1);
	}
	@Test public void Video() {
		fxt.Test__parse("file:///C:/xowa/file/commons.wikimedia.org/thumb/7/0/A.ogv/220px-5.jpg"	, "commons.wikimedia.org"	, BoolUtl.N, "A.ogv", 220,  5, -1);
	}
	@Test public void Pdf() {
		fxt.Test__parse("file:///C:/xowa/file/commons.wikimedia.org/thumb/7/0/A.pdf/220px-5.png"	, "commons.wikimedia.org"	, BoolUtl.N, "A.pdf", 220, -1,  5);
	}
	@Test public void Md5_depth_4() {
		fxt.Test__parse("file:///C:/xowa/file/en.wikipedia.org/orig/7/0/1/0/A.png"					, "en.wikipedia.org"		, BoolUtl.Y, "A.png",  -1, -1, -1);
	}
	@Test public void Math() {	// PURPOSE: "xowa:/math" shouldn't cause img_src_parser to fail; DATE:2016-11-17
		fxt.Test__parse("xowa:/math/596f8baf206a81478afd4194b44138715dc1a05c"						, "en.wikipedia.org"		, BoolUtl.Y, "A.png",  -1, -1, -1);
	}
//		@Test public void Fail__orig_mode() {
//			fxt.Test__parse__fail("file:///C:/xowa/file/commons.wikimedia.org/fail/7/0/A.png", "failed trie check: mid='fail/7/0/A.png' ctx='Main_Page' wkr='img.src.xowa' excerpt='file:///C:/xowa/file/commons.wikimedia.org/fail/7/0/A.png'");
//		}
//		@Test public void Fail__repo_mode() {
//			fxt.Test__parse__fail("file:///C:/xowa/file/en.wiktionary.org/orig/7/0/A.png", "repo must be commons or self: repo='en.wiktionary.org' ctx='Main_Page' wkr='img.src.xowa' excerpt='file:///C:/xowa/file/en.wiktionary.org/orig/7/0/A.png'");
//		}
}
class Xoh_img_src_data_fxt extends Xoh_itm_parser_fxt { 	private final Xoh_img_src_data parser = new Xoh_img_src_data();
	public void Clear() {
		Xoa_app_fxt.repo2_(app, wiki);
		hctx.Init_by_page(wiki, new Xoh_page());
	}
	@Override public Xoh_itm_parser Parser_get() {return parser;}
	public void Test__parse(String src_str, String expd_repo, boolean expd_file_is_orig, String expd_file, int expd_w, int expd_time, int expd_page) {
		Exec_parse(src_str);
		if (parser.Repo_end() != -1)		// need for Math()
			GfoTstr.Eq(expd_repo, StringUtl.NewU8(src, parser.Repo_bgn(), parser.Repo_end()));
		if (parser.File_ttl_end() != -1)	// need for Math()
			GfoTstr.Eq(expd_file, StringUtl.NewU8(src, parser.File_ttl_bgn(), parser.File_ttl_end()));
		GfoTstr.Eq(expd_file_is_orig, parser.File_is_orig());
		GfoTstr.Eq(expd_w, parser.File_w());
		GfoTstr.EqDouble(expd_time, parser.File_time());
		GfoTstr.Eq(expd_page, parser.File_page());
	}
	@Override public void Exec_parse_hook(BryRdrErrWkr err_wkr, Xoh_hdoc_ctx hctx, byte[] src, int src_bgn, int src_end) {
		parser.Fail_throws_err_(true);
		parser.Parse(err_wkr, Xow_domain_itm_.Bry__enwiki, src, src_bgn, src_end);
	}
}
