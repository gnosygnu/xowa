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
package gplx.xowa.langs.msgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*;
public class Xol_msg_itm_tst {		
	@Before public void init() {fxt.Clear();} private Xol_msg_itm_fxt fxt = new Xol_msg_itm_fxt();
	@Test   public void New_plain() 			{fxt.Test_new("a"					, Bool_.N, Bool_.N);}
	@Test   public void New_fmt() 				{fxt.Test_new("a~{0}b"				, Bool_.Y, Bool_.N);}
	@Test   public void New_tmpl() 				{fxt.Test_new("a{{b}}c"				, Bool_.N, Bool_.Y);}
	@Test   public void New_fmt_tmpl() 			{fxt.Test_new("a{{b}}c~{0}d"		, Bool_.Y, Bool_.Y);}
	@Test   public void New_space() 			{fxt.Test_val("a&#32;b"				, "a b");}
}
class Xol_msg_itm_fxt {
	public void Clear() {}
	public void Test_new(String val, boolean has_fmt_arg, boolean has_tmpl_txt) {
		Xol_msg_itm itm = Xol_msg_itm_.new_(0, "test", val);
		Tfds.Eq(has_fmt_arg, itm.Has_fmt_arg(), "has_fmt_arg");
		Tfds.Eq(has_tmpl_txt, itm.Has_tmpl_txt(), "has_tmpl_txt");
	}
	public void Test_val(String val, String expd) {
		Xol_msg_itm itm = Xol_msg_itm_.new_(0, "test", val);
		Tfds.Eq(expd, String_.new_u8(itm.Val()), "has_fmt_arg");
	}
}
