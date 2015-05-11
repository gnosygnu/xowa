/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
	public KeyVal[] ary_(KeyVal... ary)		{return ary;}
	public KeyVal   itm_(String key, Object val)	{return KeyVal_.new_(key, val);}
	public void Test_encode(Object o, String expd) {Tfds.Eq(expd, srl.Encode(o));}
}
