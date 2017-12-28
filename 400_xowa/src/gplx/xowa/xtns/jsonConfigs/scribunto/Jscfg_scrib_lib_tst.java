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
package gplx.xowa.xtns.jsonConfigs.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.jsonConfigs.*;
import org.junit.*;
import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.libs.*;
public class Jscfg_scrib_lib_tst {
	@Before public void init() {
		fxt.Clear_for_lib();
		lib = new Jscfg_scrib_lib();
		lib.Init();
		lib.Core_(fxt.Core());
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Jscfg_scrib_lib lib;
	@Test   public void Get() {
		Xowe_wiki commons_wiki = fxt.Parser_fxt().Wiki().Appe().Wiki_mgr().Get_by_or_make(gplx.xowa.wikis.domains.Xow_domain_itm_.Bry__commons).Init_assert();
		fxt.Parser_fxt().Init_page_create(commons_wiki, "Data:Test.tab", gplx.langs.jsons.Json_doc.Make_str_by_apos
		( "{"
		, "  'data':"
		, "  ["
		, "    ["
		, "      'Q1'"
		, "    , 'Data:Q1'"
		, "    ]"
		, "  ,"
		, "    ["
		, "      'Q2'"
		, "    , 'Data:Q2'"
		, "    ]"
		, "  ]"
		, "}"
		));
		fxt.Test_scrib_proc_str_ary(lib, Jscfg_scrib_lib.Invk_get, Keyval_.Ary(Keyval_.int_(1, "Test.tab")), String_.Concat_lines_nl_skip_last
		( "1="
		, "  data="
		, "    1="
		, "      1=Q1"
		, "      2=Data:Q1"
		, "    2="
		, "      1=Q2"
		, "      2=Data:Q2"
		));
	}
}	
