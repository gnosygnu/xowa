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
package gplx.xowa.xtns.scribunto.engines; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
public interface Scrib_engine {
	boolean			Dbg_print();	void Dbg_print_(boolean v);
	Scrib_server	Server();		void Server_(Scrib_server v);
	Scrib_lua_proc	LoadString(String name, String text);
	Keyval[]		CallFunction(int id, Keyval[] args);
	void			RegisterLibrary(Keyval[] functions_ary);
	Keyval[]		ExecuteModule(int mod_id);
	void			CleanupChunks(Keyval[] ids);
}
