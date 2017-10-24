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
import org.junit.*; import gplx.xowa.xtns.scribunto.engines.mocks.*;
public class Scrib_lib_ustring__gmatch__tst {
	private final Mock_scrib_fxt fxt = new Mock_scrib_fxt(); private Scrib_lib lib;
	@Before public void init() {
		fxt.Clear();
		lib = fxt.Core().Lib_ustring().Init();
	}
	@Test  public void Init__basic() {
		fxt.Test__proc__objs__nest(lib, Scrib_lib_ustring.Invk_gmatch_init, Object_.Ary("abcabc", "a(b)")					, "1=a(b)\n2=\n  1=false");
		fxt.Test__proc__objs__nest(lib, Scrib_lib_ustring.Invk_gmatch_init, Object_.Ary("abcabc", "a()(b)")					, "1=a()(b)\n2=\n  1=true\n  2=false");
	}
	@Test  public void Callback__basic() {
		fxt.Test__proc__objs__nest(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary("abcabc", "a(b)", Scrib_kv_utl_.base1_many_(false), 0)	, "1=2\n2=\n  1=b");
		fxt.Test__proc__objs__nest(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary("abcabc", "a(b)", Scrib_kv_utl_.base1_many_(false), 2)	, "1=5\n2=\n  1=b");
		fxt.Test__proc__objs__nest(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary("abcabc", "a(b)", Scrib_kv_utl_.base1_many_(false), 8)	, "1=8\n2=");
	}
	@Test  public void Callback__nomatch() {// PURPOSE.fix: was originally returning "" instead of original String; EX:vi.d:trở_thành; DATE:2014-04-23
		fxt.Test__proc__objs__nest(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary("a", "a"			, Keyval_.Ary_empty, 0)	, "1=1\n2=\n  1=a");
	}
	@Test  public void Callback__anypos() {// PURPOSE.fix: was not handling $capt argument; EX:vi.d:trở_thành; DATE:2014-04-23
		fxt.Test__proc__objs__nest(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary("a bcd e", "()(b)"	, Scrib_kv_utl_.base1_many_(true, false), 0), String_.Concat_lines_nl_skip_last
		( "1=3"
		, "2="
		, "  1=3"
		, "  2=b"
		));
	}
	@Test  public void Callback__text_as_number() { // PURPOSE: Gmatch_callback must be able to take non String value; DATE:2013-12-20
		fxt.Test__proc__objs__nest(lib, Scrib_lib_ustring.Invk_gmatch_callback, Object_.Ary(1234, "1(2)", Scrib_kv_utl_.base1_many_(false), 0), String_.Concat_lines_nl_skip_last
		( "1=2"
		, "2="
		, "  1=2"
		));
	}
}
