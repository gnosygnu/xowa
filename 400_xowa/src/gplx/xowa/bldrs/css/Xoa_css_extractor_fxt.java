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
import gplx.core.ios.*;
import gplx.xowa.wikis.data.fetchers.*;
import gplx.xowa.files.downloads.*;
public class Xoa_css_extractor_fxt {
	public void Clear() {
		Io_mgr.Instance.InitEngine_mem();
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
	public void Init_fil(String url, String text) 	{Io_mgr.Instance.SaveFilStr(url, text);}
	public void Test_fil(String url, String expd) 	{Tfds.Eq_str_lines(expd, Io_mgr.Instance.LoadFilStr(Io_url_.new_any_(url)));}
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
