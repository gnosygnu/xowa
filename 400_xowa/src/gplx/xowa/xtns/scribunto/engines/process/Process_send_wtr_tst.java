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
package gplx.xowa.xtns.scribunto.engines.process; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.engines.*;
import org.junit.*;
public class Process_send_wtr_tst {
	@Before public void init() {fxt.Clear();} Scrib_lua_srl_fxt fxt = new Scrib_lua_srl_fxt();
	@Test  public void Encode_str_basic()				{fxt.Test_encode("ab"		, "\"ab\"");}
	@Test  public void Encode_str_quote()				{fxt.Test_encode("a\"b"		, "\"a\\\"b\"");}
	@Test  public void Encode_str_nl()					{fxt.Test_encode("a\nb"		, "\"a\\nb\"");}
	@Test  public void Encode_str_cr()					{fxt.Test_encode("a\rb"		, "\"a\\rb\"");}
	@Test  public void Encode_str_nil()					{fxt.Test_encode("a\0b"		, "\"a\\000b\"");}
	@Test  public void Encode_ary_empty()				{fxt.Test_encode(fxt.ary_()	, "{}");}
	@Test  public void Encode_ary_one()					{fxt.Test_encode(fxt.ary_(fxt.itm_("key", 123)), "{[\"key\"]=123}");}
	@Test  public void Encode_ary_many()				{fxt.Test_encode(fxt.ary_(fxt.itm_("k1", 123), fxt.itm_("k2", 234)), "{[\"k1\"]=123,[\"k2\"]=234}");}
	@Test  public void Encode_ary_nest()				{fxt.Test_encode(fxt.ary_(fxt.itm_("k1", fxt.ary_(fxt.itm_("k2", 234)))), "{[\"k1\"]={[\"k2\"]=234}}");}
}
class Scrib_lua_srl_fxt {
	public void Clear() {
		if (srl == null) {
			usr_dlg = Gfo_usr_dlg_.Test();
			srl = new Process_send_wtr(usr_dlg);
		}
	}	Process_send_wtr srl; Gfo_usr_dlg usr_dlg;
	public Keyval[] ary_(Keyval... ary)		{return ary;}
	public Keyval   itm_(String key, Object val)	{return Keyval_.new_(key, val);}
	public void Test_encode(Object o, String expd) {Tfds.Eq(expd, srl.Encode(o));}
}
