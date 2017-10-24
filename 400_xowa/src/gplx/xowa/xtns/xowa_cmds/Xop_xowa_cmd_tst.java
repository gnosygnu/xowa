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
package gplx.xowa.xtns.xowa_cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
import gplx.xowa.apps.gfs.*;
import gplx.langs.gfs.*;
public class Xop_xowa_cmd_tst {
	@Before public void init() {
		Xoa_gfs_mgr.Msg_parser_init();
		fxt.Reset();
	} private final    Xop_fxt fxt = new Xop_fxt();
	@Test  public void Basic() {
		GfsCore.Instance.AddCmd(fxt.App(), Xoae_app.Invk_app);
		fxt.Wiki().Sys_cfg().Xowa_cmd_enabled_(false);
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "<xowa_cmd>"
		, "app.users.get('anonymous').name;"
		, "</xowa_cmd>"
		), String_.Concat_lines_nl_skip_last
		( "app.users.get('anonymous').name;"
		));
		fxt.Wiki().Sys_cfg().Xowa_cmd_enabled_(true);
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "<xowa_cmd>"
		, "app.users.get('anonymous').name;"
		, "</xowa_cmd>"
		), String_.Concat_lines_nl_skip_last
		( "anonymous"
		));
		fxt.Wiki().Sys_cfg().Xowa_cmd_enabled_(false);
	}
	@Test  public void Template() { // PURPOSE: xowa_cmd should do template expansion; DATE:2014-05-29
		fxt.Init_page_create("Template:Xowa_cmd_test", "val_0");
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "<xowa_cmd>{{xowa_cmd_test}}</xowa_cmd>"
		), String_.Concat_lines_nl_skip_last
		( "val_0"
		));
	}
	@Test  public void Ref() { // PURPOSE: ref should not be expanded twice; DATE:2014-05-29
		fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
		( "<xowa_cmd><ref name='a'></ref><references/></xowa_cmd>"
		), String_.Concat_lines_nl_skip_last
		( "<sup id=\"cite_ref-a_0-0\" class=\"reference\"><a href=\"#cite_note-a-0\">[1]</a></sup><ol class=\"references\">"
		, "<li id=\"cite_note-a-0\"><span class=\"mw-cite-backlink\"><a href=\"#cite_ref-a_0-0\">^</a></span> <span class=\"reference-text\"></span></li>"
		, "</ol>"
		, ""
		));
	}
//		@Test  public void Xow_list_fmtrs() {
//			fxt.Wiki().Sys_cfg().Xowa_cmd_enabled_(true);
//			fxt.App().Setup_mgr().Maint_mgr().Wiki_mgr().Add(Bry_.new_a7("en.wikipedia.org"));
//			fxt.Test_parse_page_all_str(String_.Concat_lines_nl_skip_last
//			(	"{|"
//			,	"<xowa_cmd>"
//			,	"app.fmtrs.new_grp {"
//			,	"  src = 'app.setup.maint.wikis;';"
//			,	"  fmt ='"
//			,	"|-"
//			,	"|~{<>domain;<>}'"
//			,	";"
//			,	"  run;"
//			,	"}"
//			,	"</xowa_cmd>"
//			,	"|}"
//			), 	String_.Concat_lines_nl
//			(	"<table>"
//			,	"  <tr>"
//			,	"    <td>en.wikipedia.org"
//			,	"    </td>"
//			,	"  </tr>"
////			,	"  <tr>"
////			,	"    <td>home"
////			,	"    </td>"
////			,	"  </tr>"
//			,	"</table>"
//			)
//			);
//		}
}
