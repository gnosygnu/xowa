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
package gplx.xowa.xtns.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.xtns.scribunto.procs.*;
public interface Scrib_lib {
	Scrib_proc_mgr	Procs();
	Scrib_lib		Init();
	Scrib_lua_mod	Register(Scrib_core core, Io_url script_dir);
	boolean			Procs_exec(int key, Scrib_proc_args args, Scrib_proc_rslt rslt);
	Scrib_lib		Clone_lib(Scrib_core core);
}
