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
package gplx.xowa.xtns.scribunto.engines.luaj; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.engines.*;
import org.junit.*;
import gplx.core.tests.*;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
public class Luaj_value__tst {
		private static final Luaj_value__fxt fxt = new Luaj_value__fxt();
	@Test public void Obj_to_lua_val() {
		fxt.Test__Obj_to_lua_val
		( 	Keyval_.Ary
			(   Keyval_.new_("data", Object_.Ary
				(	Keyval_.Ary
					(	Keyval_.new_("type", "type1")
					,	Keyval_.new_("name", "name1")
					)
				, 	Keyval_.Ary
					(	Keyval_.new_("type", "type2")
					,	Keyval_.new_("name", "name2")
					)
				)
			)
		), String_.Concat_lines_nl_skip_last
		( "data="
		, "  1="
		, "    name=name1"
		, "    type=type1"
		, "  2="
		, "    name=name2"
		, "    type=type2"
		));
	}
	}
class Luaj_value__fxt {
	public void Test__Obj_to_lua_val(Object val, String expd) {
		Luaj_server server = null;
		LuaValue actl_lv = Luaj_value_.Obj_to_lua_val(server, val);

		String actl = null;
		if (actl_lv.istable()) {
			Keyval[] actl_kv = Luaj_value_.Lua_tbl_to_kv_ary(server, (LuaTable)actl_lv);
			actl = Keyval_.Ary__to_str__nest(actl_kv);
		}
		else {
			actl = actl_lv.tojstring();			
		}
		Gftest.Eq__ary__lines(expd, actl, "", "");
	}
}
