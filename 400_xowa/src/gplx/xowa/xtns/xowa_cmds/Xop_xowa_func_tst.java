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
import gplx.xowa.apps.gfs.*; import gplx.langs.gfs.*;
public class Xop_xowa_func_tst {
	@Before public void init() {
		Xoa_gfs_mgr.Msg_parser_init();
	} private final Xop_fxt fxt = new Xop_fxt();
	@Test  public void Template() {
		GfsCore.Instance.AddCmd(fxt.App(), Xoae_app.Invk_app);
		fxt.Wiki().Sys_cfg().Xowa_cmd_enabled_(true);
		fxt.Init_defn_add("A", "{{#xowa|{{{1}}}}}");
		fxt.Test_parse_page_all_str("{{A|app.users.get('anonymous').name;}}", "anonymous");
	}
}
