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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*; import gplx.xowa.files.downloads.*;
public class Xoa_css_img_downloader_tst {		
	@Before public void init() {fxt.Clear();} private Xoa_css_img_downloader_fxt fxt = new Xoa_css_img_downloader_fxt();
	@Test  public void Basic() {
		fxt.Test_css_convert
		(	"x {url(\"//site/a.jpg\")} y {url(\"//site/b.jpg\")}"
		, 	"x {url(\"site/a.jpg\")} y {url(\"site/b.jpg\")}"
		,	"site/a.jpg"
		,	"site/b.jpg"
		);
	}
	@Test  public void Unquoted() {
		fxt.Test_css_convert
		(	"x {url(//site/a.jpg)}"
		, 	"x {url(\"site/a.jpg\")}"
		,	"site/a.jpg"
		);
	}
	@Test  public void Http() {
		fxt.Test_css_convert
		(	"x {url(http://site/a.jpg)}"
		, 	"x {url(\"site/a.jpg\")}"
		,	"site/a.jpg"
		);
	}
	@Test  public void Base64() {
		fxt.Test_css_convert
		(	"x {url(\"//site/a.jpg\")} y {url(\"data:image/png;base64,BASE64DATA;ABC=\")} z {}"
		, 	"x {url(\"site/a.jpg\")} y {url(\"data:image/png;base64,BASE64DATA;ABC=\")} z {}"
		,	"site/a.jpg"
		);
	}
	@Test  public void Exc_missing_quote() {
		fxt.Test_css_convert
		(	"x {url(\"//site/a.jpg\")} y {url(\"//site/b.jpg} z {}"
		, 	"x {url(\"site/a.jpg\")} y {url(\"//site/b.jpg} z {}"
		,	"site/a.jpg"
		);
	}
	@Test  public void Exc_empty() {
		fxt.Test_css_convert
		(	"x {url(\"//site/a.jpg\")} y {url(\"\"} z {}"
		, 	"x {url(\"site/a.jpg\")} y {url(\"\"} z {}"
		,	"site/a.jpg"
		);
	}
//		@Test  public void Exc_name_only() {	// COMMENTED: not sure how to handle "b.jpg" (automatically add "current" path?); RESTORE: when example found
//			fxt.Test_css_convert
//			(	"x {url(\"//site/a.jpg\")} y {url(\"b.jpg\"} z {}"
//			, 	"x {url(\"site/a.jpg\")} y {url(\"b.jpg\"} z {}"
//			,	"site/a.jpg"
//			);
//		}
	@Test  public void Repeat() {// PURPOSE.fix: exact same item was being added literally
		fxt.Test_css_convert
		(	"x {url(\"//site/a.jpg?a=b\")} y {url(\"//site/a.jpg?a=b\"}"
		, 	"x {url(\"site/a.jpg\")} y {url(\"site/a.jpg\"}"
		,	"site/a.jpg"
		);
	}
	@Test  public void Clean_basic() 				{fxt.Test_clean_img_url("//site/a.jpg"							, "site/a.jpg");}
	@Test  public void Clean_query() 				{fxt.Test_clean_img_url("//site/a.jpg?key=val"					, "site/a.jpg");}
	@Test  public void Clean_dir() 					{fxt.Test_clean_img_url("//site/a/b/c.jpg?key=val"				, "site/a/b/c.jpg");}
	@Test  public void Clean_exc_site_only() 		{fxt.Test_clean_img_url("//site"								, null);}
	@Test  public void Clean_exc_site_only_2() 		{fxt.Test_clean_img_url("//site/"								, null);}
	@Test  public void Import_url() {
		Io_mgr.Instance.InitEngine_mem();
		Io_mgr.Instance.SaveFilStr("mem/www/b.css", "imported_css");
		fxt.Test_css_convert
		(	"x @import url(\"mem/www/b.css\") screen; z"
		, 	String_.Concat_lines_nl
		(	"x "
		,	"/*XOWA:mem/www/b.css*/"
		,	"imported_css"
		,	""
		,	" z"
		)
		);
	}
	@Test  public void Import_url_make() {
		fxt.Test_import_url("a.org/b"				, "http:a.org/b");	// add "stylesheet_prefix"
		fxt.Test_import_url("http://a.org"			, "http://a.org");	// unless it starts with http
		fxt.Test_import_url("https://a.org"			, "https://a.org");	// unless starts with https EX:: handle @import(https://...); PAGE:tr.n:Main_Page; DATE:2014-06-04
	}
	@Test  public void Import_url_relative() {	// PURPOSE: if directory, add domain; "/a/b.css" -> "//domain/a/b.css"; DATE:2014-02-03
		Io_mgr.Instance.InitEngine_mem();
		Io_mgr.Instance.SaveFilStr("mem/en.wikipedia.org/www/b.css", "imported_css");
		fxt.Test_css_convert
		(	"x @import url(\"/www/b.css\") screen; z"	// starts with "/"
		, 	String_.Concat_lines_nl
		(	"x "
		,	"/*XOWA:mem/en.wikipedia.org/www/b.css*/"
		,	"imported_css"
		,	""
		,	" z"
		)
		);
	}
	@Test  public void Import_url_relative_skip() {	// PURPOSE: if rel path, skip; "//site/a/b.css"; DATE:2014-02-03
		fxt.Downloader().Stylesheet_prefix_(Bry_.new_a7("mem"));	// stylesheet prefix prefix defaults to ""; set to "mem", else test will try to retrieve "//url" which will fail
		Io_mgr.Instance.InitEngine_mem();
		Io_mgr.Instance.SaveFilStr("mem//en.wikipedia.org/a/b.css", "imported_css");
		fxt.Test_css_convert
		(	"x @import url(\"//en.wikipedia.org/a/b.css\") screen; z"	// starts with "//"
		, 	String_.Concat_lines_nl
		(	"x "
		,	"/*XOWA://en.wikipedia.org/a/b.css*/"
		,	"imported_css"
		,	""
		,	" z"
		)
		);
	}
	@Test  public void Import_url_space() {		// PURPOSE: some css has spaces; replace with underlines else fails when downloaded; EX: https://it.wikivoyage.org/w/index.php?title=MediaWiki:Container e Infobox.css&action=raw&ctype=text/css; DATE:2015-03-08
		Io_mgr.Instance.InitEngine_mem();
		Io_mgr.Instance.SaveFilStr("mem/www/b_c.css", "imported_css");
		fxt.Test_css_convert
		(	"x @import url(\"mem/www/b c.css\") screen; z"
		, 	String_.Concat_lines_nl
		(	"x "
		,	"/*XOWA:mem/www/b_c.css*/"
		,	"imported_css"
		,	""
		,	" z"
		)
		);
	}
	@Test  public void Wikisource_freedimg() {	// PURPOSE: check that "wikimedia" is replaced for FreedImg hack; PAGE:en.s:Page:Notes_on_Osteology_of_Baptanodon._With_a_Description_of_a_New_Species.pdf/3 DATE:2014-09-06
		fxt.Downloader().Stylesheet_prefix_(Bry_.new_a7("mem"));	// stylesheet prefix prefix defaults to ""; set to "mem", else test will try to retrieve "//url" which will fail
		Io_mgr.Instance.InitEngine_mem();
		Io_mgr.Instance.SaveFilStr("mem//en.wikisource.org/w/index.php?title=MediaWiki:Dynimg.css", ".freedImg img[src*=\"wikipedia\"], .freedImg img[src*=\"wikisource\"], .freedImg img[src*=\"score\"], .freedImg img[src*=\"math\"] {");
		fxt.Test_css_convert
		(	"x @import url(\"//en.wikisource.org/w/index.php?title=MediaWiki:Dynimg.css\") screen; z"	// starts with "//"
		, 	String_.Concat_lines_nl
		(	"x "
		,	"/*XOWA://en.wikisource.org/w/index.php?title=MediaWiki:Dynimg.css*/"
		,	".freedImg img[src*=\"wikipedia\"], .freedImg img[src*=\"wikisource\"], /*XOWA:handle file:// paths which will have /commons.wikimedia.org/ but not /wikipedia/ */ .freedImg img[src*=\"wikimedia\"], .freedImg img[src*=\"score\"], .freedImg img[src*=\"math\"] {"
		,	""
		,	" z"
		)
		);
	}
}
class Xoa_css_img_downloader_fxt {
	public Xoa_css_img_downloader Downloader() {return downloader;} private Xoa_css_img_downloader downloader;
	public void Clear() {
		downloader = new Xoa_css_img_downloader();
		downloader.Ctor(Gfo_usr_dlg_.Test(), new Xof_download_wkr_test(), Bry_.Empty);
	}
	public void Test_css_convert(String raw, String expd, String... expd_img_ary) {
		List_adp actl_img_list = List_adp_.New();
		byte[] actl_bry = downloader.Convert_to_local_urls(Bry_.new_a7("mem/en.wikipedia.org"), Bry_.new_u8(raw), actl_img_list);
		Tfds.Eq_str_lines(expd, String_.new_u8(actl_bry));
		Tfds.Eq_ary_str(expd_img_ary, actl_img_list.To_str_ary());
	}
	public void Test_clean_img_url(String raw_str, String expd) {
		byte[] raw = Bry_.new_a7(raw_str);
		byte[] actl = downloader.Clean_img_url(raw, raw.length);
		Tfds.Eq(expd, actl == null ? null : String_.new_a7(actl));
	}
	public void Test_import_url(String raw, String expd) {
		byte[] actl = Xoa_css_img_downloader.Import_url_build(Bry_.new_a7("http:"), Bry_.new_a7("//en.wikipedia.org"), Bry_.new_u8(raw));
		Tfds.Eq(expd, String_.new_u8(actl));
	}
}
