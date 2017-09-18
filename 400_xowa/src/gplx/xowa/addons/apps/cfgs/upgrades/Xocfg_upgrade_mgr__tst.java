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
package gplx.xowa.addons.apps.cfgs.upgrades; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*;
import org.junit.*; import gplx.core.tests.*;
public class Xocfg_upgrade_mgr__tst {
	private final    Xocfg_upgrade_mgr__fxt fxt = new Xocfg_upgrade_mgr__fxt();
	@Test   public void Parse__one() {
		fxt.Test__parse("app.cfgs.get('k_1', 'app').val = 'v_1';\n", Keyval_.new_("k_1", "v_1"));
	}
	@Test   public void Parse__apos() {
		fxt.Test__parse("app.cfgs.get('k_1(''k_1a'')', 'app').val = 'v_1';\n", Keyval_.new_("k_1('k_1a')", "v_1"));
	}
	@Test   public void Parse__many() {
		fxt.Test__parse
		( "app.cfgs.get('k_1', 'app').val = 'v_1';\n"
		+ "app.cfgs.get('k_2', 'app').val = 'v_2';\n"
		, Keyval_.new_("k_1", "v_1"), Keyval_.new_("k_2", "v_2"));
	}
	@Test   public void Parse__multi_line() {
		fxt.Test__parse
		( "app.cfgs.get('k_1', 'app').val = '''v_1'';\n"
		+ "v_2\n"
		+ "v_3';\n"
		, Keyval_.new_("k_1", "'v_1';\nv_2\nv_3"));
	}
	@Test   public void Parse__io_cmd() {
		fxt.Test__parse
		( "app.cfgs.get('a.cmd', 'app').val = 'cmd_1';\n"
		+ "app.cfgs.get('a.args', 'app').val = 'args_1';\n"
		, Keyval_.new_("a.cmd", "cmd_1|args_1"));
	}
	@Test   public void Parse__gui_binding() {
		fxt.Test__parse
		( "app.cfgs.get('app.cfg.get.gui.bnds.init(''xowa.app.exit-1'').src', 'app').val = 'box=''browser'';ipt=''key.none'';';\n"
		, Keyval_.new_("xowa.gui.shortcuts.xowa.app.exit-1", "browser|key.none"));
	}
}
class Xocfg_upgrade_mgr__fxt {
	public void Test__parse(String raw, Keyval... expd) {
		Keyval[] actl = Xocfg_upgrade_mgr.Parse(Bry_.new_u8(raw));
		Gftest.Eq__str(Keyval_.Ary_to_str(expd), Keyval_.Ary_to_str(actl));
	}
}