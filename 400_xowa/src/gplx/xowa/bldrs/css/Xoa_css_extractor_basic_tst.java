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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*; import gplx.ios.*; import gplx.langs.htmls.encoders.*; import gplx.xowa.wikis.data.*; import gplx.xowa.files.downloads.*;
public class Xoa_css_extractor_basic_tst {
	@Before public void init() {fxt.Clear();} private Xoa_css_extractor_fxt fxt = new Xoa_css_extractor_fxt();
	@Test   public void Logo_download() {
		fxt.Init_fil("mem/http/en.wikipedia.org"							, Xoa_css_extractor_fxt.Main_page_html);
		fxt.Init_fil("mem/http/wiki.png"									, "download");
		fxt.Exec_logo_setup();
		fxt.Test_fil("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/logo.png", "download");
	}
	@Test   public void Logo_download_mw_wiki_logo() {
		fxt.Init_fil("mem/http/en.wikipedia.org"											, "");
		fxt.Init_fil("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/a/wiki.png"		, "download");
		fxt.Init_fil("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/xowa_common.css"	, ".mw-wiki-logo{background-image:url(\"//a/wiki.png\");");
		fxt.Exec_logo_setup();
		fxt.Test_fil("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/logo.png"			, "download");
	}
	@Test   public void Logo_failover() {
		fxt.Init_fil("mem/xowa/bin/any/html/xowa/import/logo.png"			, "failover");
		fxt.Exec_logo_setup();
		fxt.Test_fil("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/logo.png", "failover");
	}
	@Test   public void Css_common_download_failover() {
		fxt.Css_installer().Opt_download_css_common_(true);
		fxt.Init_fil("mem/xowa/bin/any/html/xowa/import/xowa_common_ltr.css", "failover");
		fxt.Exec_css_common_setup();
		fxt.Test_fil("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/xowa_common.css", "failover");
	}
	@Test   public void Css_common_copy() {
		fxt.Css_installer().Opt_download_css_common_(false);
		fxt.Init_fil("mem/xowa/bin/any/html/xowa/import/xowa_common_ltr.css", "failover");
		fxt.Exec_css_common_setup();
		fxt.Test_fil("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/xowa_common.css", "failover");
	}
	@Test   public void Css_common_copy_specific_wiki() {	// PURPOSE: css for specific wiki
		fxt.Css_installer().Opt_download_css_common_(false).Wiki_code_(Bry_.new_a7("enwiki"));
		fxt.Init_fil("mem/xowa/bin/any/html/xowa/import/xowa_common_override/xowa_common_enwiki.css", "failover");
		fxt.Exec_css_common_setup();
		fxt.Test_fil("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/xowa_common.css", "failover");
	}
	@Test   public void Css_scrape_download() {
		fxt.Css_installer().Url_encoder_(Url_encoder.new_http_url_());
		fxt.Init_fil("mem/http/en.wikipedia.org"							, Xoa_css_extractor_fxt.Main_page_html);
		fxt.Init_fil("mem/http/en.wikipedia.org/common.css"					, "download");
		fxt.Init_fil("mem/http/www/a&0|b,c"									, "data=css_0");
		fxt.Init_fil("mem/http/www/a&1|b,c"									, "data=css_1");
		fxt.Exec_css_mainpage_setup();
		fxt.Test_fil("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/xowa_common.css", String_.Concat_lines_nl
		(	"/*XOWA:mem/http/www/a&0|b,c*/"
		,	"data=css_0"
		,	""
		,	"/*XOWA:mem/http/www/a&1|b,c*/"
		,	"data=css_1"
		));
	}
	@Test   public void Css_scrape_failover() {
		fxt.Init_fil("mem/xowa/bin/any/html/xowa/import/xowa_common_ltr.css", "failover");
		fxt.Exec_css_mainpage_setup();
		fxt.Test_fil("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/xowa_common.css", "failover");
	}
}
class Xoa_css_extractor_fxt {
	public void Clear() {
		Io_mgr.I.InitEngine_mem();
		Gfo_usr_dlg usr_dlg = Gfo_usr_dlg_.Test();
		css_installer = new Xoa_css_extractor();
		css_installer.Download_xrg().Trg_engine_key_(IoEngine_.MemKey);
		css_installer
			.Usr_dlg_(usr_dlg)
			.Wiki_domain_(Bry_.new_a7("en.wikipedia.org"))
			.Protocol_prefix_("mem/http/")
			.Mainpage_url_("mem/http/en.wikipedia.org")
			.Failover_dir_(Io_url_.new_any_("mem/xowa/bin/any/html/xowa/import/"))	// "mem/xowa/user/anonymous/wiki/home/html/"
			.Wiki_html_dir_(Io_url_.new_any_("mem/xowa/user/anonymous/wiki/en.wikipedia.org/html/"))
			;
		page_fetcher = new Xow_page_fetcher_test();
		css_installer.Page_fetcher_(page_fetcher);
		Xoa_css_img_downloader css_img_downloader = new Xoa_css_img_downloader();
		css_img_downloader.Ctor(usr_dlg, new Xof_download_wkr_test(), Bry_.new_a7("mem/http/"));
		css_installer.Css_img_downloader_(css_img_downloader);
	}	private Xow_page_fetcher_test page_fetcher;
	public Xoa_css_extractor Css_installer() {return css_installer;} private Xoa_css_extractor css_installer;
	public void Init_page(int ns_id, String ttl, String text) {
		page_fetcher.Add(ns_id, Bry_.new_a7(ttl), Bry_.new_a7(text));
	}
	public void Init_fil_empty(String url) 			{Init_fil(url, "");}
	public void Init_fil(String url, String text) 	{Io_mgr.I.SaveFilStr(url, text);}
	public void Test_fil(String url, String expd) 	{Tfds.Eq_str_lines(expd, Io_mgr.I.LoadFilStr(Io_url_.new_any_(url)));}
	public void Exec_logo_setup() {
		css_installer.Mainpage_download();
		css_installer.Logo_setup();
	}
	public void Exec_css_common_setup() {
		css_installer.Mainpage_download();
		css_installer.Css_common_setup();
	}
	public void Exec_css_wiki_setup() {css_installer.Css_wiki_setup();}
	public void Exec_css_mainpage_setup() {
		css_installer.Mainpage_download();
		css_installer.Css_scrape_setup();
	}
	public static String Main_page_html = String_.Concat_lines_nl
	(	"<html>"
	,	"  <head>"
	,	"    <link rel=\"stylesheet\" href=\"www/a&amp;0%7Cb%2Cc\" />"
	,	"    <link rel=\"stylesheet\" href=\"www/a&amp;1%7Cb%2Cc\" />"
	,	"  </head>"
	,	"  <body>"
	,	"    <div id=\"p-logo\" role=\"banner\"><a style=\"background-image: url(wiki.png);\""
	,	"  </body>"
	,	"</html>"
	);
}
