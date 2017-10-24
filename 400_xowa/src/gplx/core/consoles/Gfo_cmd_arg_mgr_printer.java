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
package gplx.core.consoles; import gplx.*; import gplx.core.*;
public class Gfo_cmd_arg_mgr_printer {
	private final    Gfo_cmd_arg_mgr arg_mgr;
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	public Gfo_cmd_arg_mgr_printer(Gfo_cmd_arg_mgr arg_mgr) {this.arg_mgr = arg_mgr;}
	public boolean Print(Gfo_usr_dlg usr_dlg, String header, String app_name, String key__print_help, String key__print_header, String key__print_args) {
		if (arg_mgr.Get_by_as_bool(key__print_header))
			usr_dlg.Note_gui_none("", "", header);
		if (arg_mgr.Errs__exist()) {
			usr_dlg.Note_none("", "", Get_args());
			usr_dlg.Note_none("", "", Get_fail());
			usr_dlg.Note_none("", "", Get_help(app_name));
			return false;
		}
		if (arg_mgr.Has(key__print_help)) {
			usr_dlg.Note_none("", "", Get_help(app_name));
			return false;
		}
		if (arg_mgr.Get_by_as_bool(key__print_args))
			usr_dlg.Note_none("", "", Get_args());
		return true;
	}
	public String Get_args() {
		tmp_bfr.Add_byte_nl();
		tmp_bfr.Add_str_a7_w_nl("arguments:");
		String[] orig_ary = arg_mgr.Orig_ary();
		int len = orig_ary.length;
		if (len == 0) {
			tmp_bfr.Add_str_a7_w_nl("  **** NONE ****");
			tmp_bfr.Add_str_a7_w_nl("  use --help to show help");
		}
		else {
			for (int i = 0; i < len; i++) {
				String line = String_.Format("  [{0}] = '{1}'", i, orig_ary[i]);
				tmp_bfr.Add_str_u8_w_nl(line);
			}
		}
		return tmp_bfr.To_str_and_clear();
	}
	public String Get_fail() {
		tmp_bfr.Add_str_a7_w_nl("** error: ");
		String[] err_ary = arg_mgr.Errs__to_str_ary();
		int len = err_ary.length;
		for (int i = 0; i < len; ++i)
			tmp_bfr.Add_str_u8_w_nl("  " + err_ary[i]);
		tmp_bfr.Add_byte_nl();
		tmp_bfr.Add_str_a7_w_nl(String_.Repeat("-", 80));
		return tmp_bfr.To_str_and_clear();
	}
	public String Get_help(String app_name) {
		tmp_bfr.Add_str_a7_w_nl("example:");
		tmp_bfr.Add_str_a7(String_.Format("  java -jar {0}.jar", app_name));
		int key_max = 0, tid_max = 0;
		int len = arg_mgr.Len();
		for (int i = 0; i < len; i++) {
			Gfo_cmd_arg_itm arg = arg_mgr.Get_at(i); if (arg.Tid() != Gfo_cmd_arg_itm_.Tid_general) continue; // skip header, help
			tmp_bfr.Add_str_a7(" ").Add_str_a7(Gfo_cmd_arg_mgr.Key_prefix).Add_str_u8(arg.Key()).Add_str_a7(" ").Add_str_u8(arg.Example());
			int key_len = String_.Len(arg.Key()); if (key_len > key_max) key_max = key_len;
			int tid_len = String_.Len(String_.Format("[{0}:{1}]", arg.Reqd_str(), arg.Val_tid_str())); if (tid_len > tid_max) tid_max = tid_len;
		}
		tmp_bfr.Add_byte_nl().Add_byte_nl();
		tmp_bfr.Add_str_a7_w_nl("detail:");
		for (int i = 0; i < len; i++) {
			Gfo_cmd_arg_itm arg = (Gfo_cmd_arg_itm)arg_mgr.Get_at(i);
			tmp_bfr.Add_str_a7("  ").Add_str_a7(Gfo_cmd_arg_mgr.Key_prefix)
				.Add_str_u8(String_.PadEnd(arg.Key(), key_max + 1, " "))
				.Add_str_u8(String_.PadEnd(String_.Format("[{0}:{1}]", arg.Reqd_str(), arg.Val_tid_str()), tid_max, " "));
			if (arg.Dflt() != null) {
				String dflt_val = Object_.Xto_str_strict_or_null_mark(arg.Dflt());
				tmp_bfr.Add_str_u8(String_.Format(" default={0}", dflt_val));
			}
			tmp_bfr.Add_byte_nl();
			if (arg.Note() != null)
				tmp_bfr.Add_str_a7("    ").Add_str_u8(arg.Note()).Add_byte_nl();
		}
		return tmp_bfr.To_str_and_clear();
	}
}