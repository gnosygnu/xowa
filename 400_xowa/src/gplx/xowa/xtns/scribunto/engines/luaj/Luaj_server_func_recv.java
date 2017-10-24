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
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
public class Luaj_server_func_recv extends OneArgFunction {
	private final Luaj_engine engine;
	public Luaj_server_func_recv(Luaj_engine v) {this.engine = v;}
	public LuaValue call(LuaValue tbl_val) {
		LuaTable tbl = (LuaTable)tbl_val;
		String op = Luaj_value_.Get_val_as_str(tbl, "op");
		if (!String_.Eq(op, "call")) throw Err_.new_wo_type("luaj_recvr only processes op calls");
		return engine.Server_recv_call(tbl);
	}
}
