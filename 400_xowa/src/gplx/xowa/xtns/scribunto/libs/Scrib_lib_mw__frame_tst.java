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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*;
import gplx.xowa.xtns.scribunto.engines.mocks.*;
import gplx.xowa.xtns.scribunto.procs.*;
public class Scrib_lib_mw__frame_tst {
	private final    Mock_scrib_fxt fxt = new Mock_scrib_fxt(); private Scrib_lib lib;
	@Before public void init() {
		fxt.Clear();
		fxt.Init__mock_mod_system();
		lib = fxt.Core().Lib_mw().Init();

		fxt.Init__mock_mod(lib, "Module:Mod_1"
			, fxt.Init__mock_fnc_for_lib("Get_frame_title_current", lib, Scrib_lib_mw.Invk_getFrameTitle, "current")
			, fxt.Init__mock_fnc_for_lib("Get_frame_title_parent", lib, Scrib_lib_mw.Invk_getFrameTitle, "parent")
			);
	}
	@Test public void GetFrameTitle__template() {
		fxt.Init__page("Template:Template_1", "{{#invoke:Mod_1|Get_frame_title_{{{1}}}}}");
		fxt.Test__parse__tmpl_to_html("{{Template_1|current}}", "Module:Mod 1");
		fxt.Test__parse__tmpl_to_html("{{Template_1|parent}}" , "Template:Template 1");
	}
	@Test public void GetFrameTitle__transclude() {
		fxt.Init__page("Page_1", "{{#invoke:Mod_1|Get_frame_title_parent}}");
		fxt.Test__parse__tmpl_to_html("{{:Page_1}}", "Page 1");

		fxt.Init__page("Page_2", "{{#invoke:Mod_1|Get_frame_title_current}}");
		fxt.Test__parse__tmpl_to_html("{{:Page_2}}", "Module:Mod 1");
	}
	@Test public void GetFrameTitle__page() {
		fxt.Test__parse__tmpl_to_html("{{#invoke:Mod_1|Get_frame_title_parent}}", "Test page");
		fxt.Test__parse__tmpl_to_html("{{#invoke:Mod_1|Get_frame_title_current}}", "Module:Mod 1");
	}
	@Test public void GetFrameTitle__onlyinclude() {
		fxt.Test__parse__tmpl_to_html("<onlyinclude>{{#invoke:Mod_1|Get_frame_title_parent}}</onlyinclude>", "Test page");
		fxt.Test__parse__tmpl_to_html("<onlyinclude>{{#invoke:Mod_1|Get_frame_title_current}}</onlyinclude>", "Module:Mod 1");
	}
}