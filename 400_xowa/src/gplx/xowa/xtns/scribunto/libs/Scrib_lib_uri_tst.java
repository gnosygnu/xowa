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
public class Scrib_lib_uri_tst {
	@Before public void init() {
		fxt.Clear_for_lib();
		lib = fxt.Core().Lib_uri().Init();
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	@Test  public void Url() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_uri.Invk_localUrl		, Object_.Ary("a&b! c"						), "/wiki/A%26b!_c");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_uri.Invk_fullUrl			, Object_.Ary("a&b! c"						), "//en.wikipedia.org/wiki/A%26b!_c");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_uri.Invk_canonicalUrl	, Object_.Ary("a&b! c"						), "https://en.wikipedia.org/wiki/A%26b!_c");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_uri.Invk_localUrl		, Object_.Ary("a&b! c"		, "action=edit"	), "/wiki/A%26b!_c?action=edit");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_uri.Invk_localUrl		, Object_.Ary("Media:A.png"					), "/wiki/File:A.png");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_uri.Invk_localUrl		, Object_.Ary("[bad]"						), Scrib_invoke_func_fxt.Null_rslt);	// handle invalid titles; EX:it.w:Billy_the_Kid; DATE:2014-01-20
	}
	@Test  public void Url__args_many() {	// PUPROSE: GetUrl sometimes passes in kvs for qry_args; it.w:Astronomie; DATE:2014-01-18
		fxt.Test_scrib_proc_str(lib, Scrib_lib_uri.Invk_fullUrl, Object_.Ary("A", Keyval_.Ary(Keyval_.new_("action", "edit"))), "//en.wikipedia.org/wiki/A?action=edit");
	}
	@Test  public void AnchorEncode() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_uri.Invk_anchorEncode	, Object_.Ary("[irc://a b c]"				), "b_c");
	}
	@Test  public void Init_uri_for_page() {
		fxt.Parser_fxt().Page_ttl_("Page_1");
		fxt.Test_scrib_proc_str(lib, Scrib_lib_uri.Invk_init_uri_for_page	, Object_.Ary_empty						, "//en.wikipedia.org/wiki/Page_1");
	}
}	
