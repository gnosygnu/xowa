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
package gplx.xowa.apps; import gplx.*; import gplx.xowa.*;
import org.junit.*; import gplx.xowa.apps.gfs.*;
public class Xoa_shell_tst {
	@Test  public void Fetch_page() {	// PURPOSE.fix: fetch_page failed with nullRef; DATE:2013-04-12
		Xop_fxt parser_fxt = new Xop_fxt();
		Xoae_app app = parser_fxt.App(); Xowe_wiki wiki = parser_fxt.Wiki();
		parser_fxt.Init_page_create("A", "test"); // need to create page in order for html output to gen
		wiki.Html_mgr().Page_wtr_mgr().Html_capable_(true);	// need to set html_capable in order for div_home to load
		Xoa_gfs_mgr.Msg_parser_init();
		wiki.Html_mgr().Portal_mgr().Div_home_fmtr().Fmt_("~{<>app.user.msgs.get('mainpage-description');<>}");
		app.Gfs_mgr().Run_str("app.shell.fetch_page('en.wikipedia.org/wiki/A' 'html');"); // this causes a nullRef error b/c app.user.lang is null
	}
}
