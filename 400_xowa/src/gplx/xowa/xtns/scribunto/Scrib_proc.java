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
package gplx.xowa.xtns.scribunto; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.xtns.scribunto.lib.*;
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
