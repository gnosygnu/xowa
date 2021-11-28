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
package gplx.xowa.xtns.scribunto.libs.wikibases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.libs.*;
import org.junit.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.*;
import gplx.xowa.xtns.wbases.claims.enums.*;
public class ResolvePropertyId__tst {
	private final ResolvePropertyId__fxt fxt = new ResolvePropertyId__fxt();
	@Before public void init() {
		fxt.Init();
	}
	@Test public void Basic() {
		fxt.Init__doc("Property:P2");
		fxt.Init__pid("de", "de0", 2);
		fxt.Init__pid("en", "en0", 2);

		fxt.Test__ResolvePropertyId("P2" , "P2");  // match exact
		fxt.Test__ResolvePropertyId("p2" , "P2");  // match by lower-case
		fxt.Test__ResolvePropertyId("P3" , null);  // do not match if unknown doc
		fxt.Test__ResolvePropertyId("de0", "P2");  // match by label
		fxt.Test__ResolvePropertyId("en0", null);  // do not match if label is not same lang as wiki
		fxt.Test__ResolvePropertyId("fr0", null);  // do not match if unknown label
	}
}
class ResolvePropertyId__fxt {
	private final Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt();
	private final Wdata_wiki_mgr_fxt wdata_fxt = new Wdata_wiki_mgr_fxt();
	private Scrib_lib lib;
	public void Init() {
		fxt.Clear_for_lib("de.wikipedia.org", "de");
		lib = fxt.Core().Lib_wikibase().Init();
		wdata_fxt.Init(fxt.Parser_fxt(), false);
	}
	public void Init__doc(String title) {
		Wdata_doc_bldr bldr = wdata_fxt.Wdoc_bldr(title);
		wdata_fxt.Init__docs__add(bldr.Xto_wdoc());
	}
	public void Init__pid(String lang_key, String pid_name, int pid) {
		wdata_fxt.Init_pids_add(lang_key, pid_name, pid);
	}
	public void Test__ResolvePropertyId(String arg, String expd) {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase.Invk_resolvePropertyId, Object_.Ary(arg), expd);
	}
}
