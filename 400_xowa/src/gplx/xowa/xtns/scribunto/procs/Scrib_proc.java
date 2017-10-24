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
package gplx.xowa.xtns.scribunto.procs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import gplx.xowa.xtns.scribunto.libs.*;
public class Scrib_proc {
	private Scrib_lib lib;
	public Scrib_proc(Scrib_lib lib, String proc_name, int proc_id) {
		this.lib = lib;
		this.proc_name = proc_name;
		this.proc_key = Scrib_core.Key_mw_interface + "-" + proc_name;
		this.proc_id = proc_id;
	}
	public String Proc_name() {return proc_name;} private String proc_name;
	public int Proc_id() {return proc_id;} private int proc_id;
	public String Proc_key() {return proc_key;} private String proc_key;
	public boolean Proc_exec(Scrib_proc_args args, Scrib_proc_rslt rslt) {return lib.Procs_exec(proc_id, args, rslt);}
}
