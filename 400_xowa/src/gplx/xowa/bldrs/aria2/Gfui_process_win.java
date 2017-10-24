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
package gplx.xowa.bldrs.aria2; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.gfui.*;
class Gfui_process_win {
	public void Exec_async(String process, String args, Gfo_invk_cmd done_cbk) {
		// Gfo_process process = new Gfo_process().Init_process_(process, args).Init_async_(done_cbk).Init_strm_out_err_(output_box).Exec();			
	}
}
class Gfo_process {
//		private Gfo_invk_cmd done_cbk;
//		private Gfo_process_wtr out_wtr, err_wtr;
	public String Cmd_path() {return cmd_path;} private String cmd_path;
	public String Cmd_args() {return cmd_args;} private String cmd_args;
	public byte Mode() {return mode;} private byte mode;
	public Gfo_process Init_cmd_(String cmd_path, String cmd_args) {this.cmd_path = cmd_path; this.cmd_args = cmd_args; return this;}
	public Gfo_process Init_mode_async_() {mode = Gfo_process_.Mode_async; return this;}
//		public Gfo_process Init_mode_async_(Gfo_invk_cmd done_cbk) {this.done_cbk = done_cbk; return this.Init_mode_async_();}
//		public Gfo_process Init_wtr_out_err_(Gfo_process_wtr wtr) {out_wtr = err_wtr = wtr; return this;}
}
class Gfo_process_wtr {}
class Gfo_process_rdr {}
class Gfo_process_ {
	public static final byte Mode_async = 0, Mode_sync = 1, Mode_sync_timeout = 2; 
}
