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
package gplx.xowa.htmls.core.wkrs.imgs.atrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
import org.junit.*; import gplx.core.brys.*;
import gplx.xowa.wikis.domains.*;
public class Xoh_img_src_parser_tst {
	private final Xoh_img_src_parser_fxt fxt = new Xoh_img_src_parser_fxt();
	@Test   public void Basic() {
		fxt.Test__parse("file:///C:/xowa/file/en.wikipedia.org/orig/7/0/A.png"						, "en.wikipedia.org"		, Bool_.Y, "A.png",  -1, -1, -1);
		fxt.Test__parse("file:///C:/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/220px.png"		, "commons.wikimedia.org"	, Bool_.N, "A.png", 220, -1, -1);
		fxt.Test__parse("file:///C:/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/220px@5.png"	, "commons.wikimedia.org"	, Bool_.N, "A.png", 220,  5, -1);
		fxt.Test__parse("file:///C:/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/220px-5.png"	, "commons.wikimedia.org"	, Bool_.N, "A.png", 220, -1,  5);
	}
	@Test   public void Md5_depth_4() {
		fxt.Test__parse("file:///C:/xowa/file/en.wikipedia.org/orig/7/0/1/0/A.png"					, "en.wikipedia.org"		, Bool_.Y, "A.png",  -1, -1, -1);
	}
//		@Test   public void Fail__orig_mode() {
//			fxt.Test__parse__fail("file:///C:/xowa/file/commons.wikimedia.org/fail/7/0/A.png", "failed trie check: mid='fail/7/0/A.png' ctx='Main_Page' wkr='img.src.xowa' excerpt='file:///C:/xowa/file/commons.wikimedia.org/fail/7/0/A.png'");
//		}
//		@Test   public void Fail__repo_mode() {
//			fxt.Test__parse__fail("file:///C:/xowa/file/en.wiktionary.org/orig/7/0/A.png", "repo must be commons or self: repo='en.wiktionary.org' ctx='Main_Page' wkr='img.src.xowa' excerpt='file:///C:/xowa/file/en.wiktionary.org/orig/7/0/A.png'");
//		}
}
class Xoh_img_src_parser_fxt extends Xoh_itm_parser_fxt { 	private final Xoh_img_src_parser parser = new Xoh_img_src_parser();
	@Override public Xoh_itm_parser Parser_get() {return parser;}
	public void Test__parse(String src_str, String expd_repo, boolean expd_file_is_orig, String expd_file, int expd_w, int expd_time, int expd_page) {
		Exec_parse(src_str);
		Tfds.Eq_str(expd_repo, String_.new_u8(src, parser.Repo_bgn(), parser.Repo_end()));
		Tfds.Eq_str(expd_file, String_.new_u8(src, parser.File_ttl_bgn(), parser.File_ttl_end()));
		Tfds.Eq_bool(expd_file_is_orig, parser.File_is_orig());
		Tfds.Eq_int(expd_w, parser.File_w());
		Tfds.Eq_int(expd_time, parser.File_time());
		Tfds.Eq_int(expd_page, parser.File_page());
	}
	@Override public void Exec_parse_hook(Bry_err_wkr err_wkr, Xoh_hdoc_ctx hctx, int src_bgn, int src_end) {
		parser.Parse(err_wkr, Xow_domain_itm_.Bry__enwiki, src_bgn, src_end);
	}
}
