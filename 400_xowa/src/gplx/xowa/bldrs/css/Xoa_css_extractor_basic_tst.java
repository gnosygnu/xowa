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
import org.junit.*; import gplx.core.ios.*; import gplx.langs.htmls.encoders.*; import gplx.xowa.wikis.data.*; import gplx.xowa.files.downloads.*;
import gplx.xowa.wikis.data.fetchers.*;
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
		fxt.Css_installer().Url_encoder_(Gfo_url_encoder_.Http_url);
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
