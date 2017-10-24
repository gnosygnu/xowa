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
package gplx.xowa.htmls.core.wkrs.imgs.atrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
import org.junit.*; import gplx.core.brys.*;
import gplx.xowa.wikis.domains.*;
public class Xoh_img_src_data_tst {
	private final    Xoh_img_src_data_fxt fxt = new Xoh_img_src_data_fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Basic() {
		fxt.Test__parse("file:///C:/xowa/file/en.wikipedia.org/orig/7/0/A.png"						, "en.wikipedia.org"		, Bool_.Y, "A.png",  -1, -1, -1);
		fxt.Test__parse("file:///C:/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/220px.png"		, "commons.wikimedia.org"	, Bool_.N, "A.png", 220, -1, -1);
	}
	@Test   public void Video() {
		fxt.Test__parse("file:///C:/xowa/file/commons.wikimedia.org/thumb/7/0/A.ogv/220px-5.jpg"	, "commons.wikimedia.org"	, Bool_.N, "A.ogv", 220,  5, -1);
	}
	@Test   public void Pdf() {
		fxt.Test__parse("file:///C:/xowa/file/commons.wikimedia.org/thumb/7/0/A.pdf/220px-5.png"	, "commons.wikimedia.org"	, Bool_.N, "A.pdf", 220, -1,  5);
	}
	@Test   public void Md5_depth_4() {
		fxt.Test__parse("file:///C:/xowa/file/en.wikipedia.org/orig/7/0/1/0/A.png"					, "en.wikipedia.org"		, Bool_.Y, "A.png",  -1, -1, -1);
	}
	@Test   public void Math() {	// PURPOSE: "xowa:/math" shouldn't cause img_src_parser to fail; DATE:2016-11-17
		fxt.Test__parse("xowa:/math/596f8baf206a81478afd4194b44138715dc1a05c"						, "en.wikipedia.org"		, Bool_.Y, "A.png",  -1, -1, -1);
	}
//		@Test   public void Fail__orig_mode() {
//			fxt.Test__parse__fail("file:///C:/xowa/file/commons.wikimedia.org/fail/7/0/A.png", "failed trie check: mid='fail/7/0/A.png' ctx='Main_Page' wkr='img.src.xowa' excerpt='file:///C:/xowa/file/commons.wikimedia.org/fail/7/0/A.png'");
//		}
//		@Test   public void Fail__repo_mode() {
//			fxt.Test__parse__fail("file:///C:/xowa/file/en.wiktionary.org/orig/7/0/A.png", "repo must be commons or self: repo='en.wiktionary.org' ctx='Main_Page' wkr='img.src.xowa' excerpt='file:///C:/xowa/file/en.wiktionary.org/orig/7/0/A.png'");
//		}
}
class Xoh_img_src_data_fxt extends Xoh_itm_parser_fxt { 	private final    Xoh_img_src_data parser = new Xoh_img_src_data();
	public void Clear() {
		Xoa_app_fxt.repo2_(app, wiki);
		hctx.Init_by_page(wiki, new Xoh_page());
	}
	@Override public Xoh_itm_parser Parser_get() {return parser;}
	public void Test__parse(String src_str, String expd_repo, boolean expd_file_is_orig, String expd_file, int expd_w, int expd_time, int expd_page) {
		Exec_parse(src_str);
		if (parser.Repo_end() != -1)		// need for Math()
			Tfds.Eq_str(expd_repo, String_.new_u8(src, parser.Repo_bgn(), parser.Repo_end()));
		if (parser.File_ttl_end() != -1)	// need for Math()
			Tfds.Eq_str(expd_file, String_.new_u8(src, parser.File_ttl_bgn(), parser.File_ttl_end()));
		Tfds.Eq_bool(expd_file_is_orig, parser.File_is_orig());
		Tfds.Eq_int(expd_w, parser.File_w());
		Tfds.Eq_double(expd_time, parser.File_time());
		Tfds.Eq_int(expd_page, parser.File_page());
	}
	@Override public void Exec_parse_hook(Bry_err_wkr err_wkr, Xoh_hdoc_ctx hctx, int src_bgn, int src_end) {
		parser.Fail_throws_err_(true);
		parser.Parse(err_wkr, new Xoh_hdoc_ctx(), Xow_domain_itm_.Bry__enwiki, src_bgn, src_end);
	}
}
