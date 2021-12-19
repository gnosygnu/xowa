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
package gplx.core.consoles;
import gplx.libs.dlgs.Gfo_usr_dlg;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
public class Gfo_cmd_arg_mgr_printer {
	private final Gfo_cmd_arg_mgr arg_mgr;
	private final BryWtr tmp_bfr = BryWtr.New();
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
		tmp_bfr.AddByteNl();
		tmp_bfr.AddStrA7Nl("arguments:");
		String[] orig_ary = arg_mgr.Orig_ary();
		int len = orig_ary.length;
		if (len == 0) {
			tmp_bfr.AddStrA7Nl("  **** NONE ****");
			tmp_bfr.AddStrA7Nl("  use --help to show help");
		}
		else {
			for (int i = 0; i < len; i++) {
				String line = StringUtl.Format("  [{0}] = '{1}'", i, orig_ary[i]);
				tmp_bfr.AddStrU8Nl(line);
			}
		}
		return tmp_bfr.ToStrAndClear();
	}
	public String Get_fail() {
		tmp_bfr.AddStrA7Nl("** error: ");
		String[] err_ary = arg_mgr.Errs__to_str_ary();
		int len = err_ary.length;
		for (int i = 0; i < len; ++i)
			tmp_bfr.AddStrU8Nl("  " + err_ary[i]);
		tmp_bfr.AddByteNl();
		tmp_bfr.AddStrA7Nl(StringUtl.Repeat("-", 80));
		return tmp_bfr.ToStrAndClear();
	}
	public String Get_help(String app_name) {
		tmp_bfr.AddStrA7Nl("example:");
		tmp_bfr.AddStrA7(StringUtl.Format("  java -jar {0}.jar", app_name));
		int key_max = 0, tid_max = 0;
		int len = arg_mgr.Len();
		for (int i = 0; i < len; i++) {
			Gfo_cmd_arg_itm arg = arg_mgr.Get_at(i); if (arg.Tid() != Gfo_cmd_arg_itm_.Tid_general) continue; // skip header, help
			tmp_bfr.AddStrA7(" ").AddStrA7(Gfo_cmd_arg_mgr.Key_prefix).AddStrU8(arg.Key()).AddStrA7(" ").AddStrU8(arg.Example());
			int key_len = StringUtl.Len(arg.Key()); if (key_len > key_max) key_max = key_len;
			int tid_len = StringUtl.Len(StringUtl.Format("[{0}:{1}]", arg.Reqd_str(), arg.Val_tid_str())); if (tid_len > tid_max) tid_max = tid_len;
		}
		tmp_bfr.AddByteNl().AddByteNl();
		tmp_bfr.AddStrA7Nl("detail:");
		for (int i = 0; i < len; i++) {
			Gfo_cmd_arg_itm arg = (Gfo_cmd_arg_itm)arg_mgr.Get_at(i);
			tmp_bfr.AddStrA7("  ").AddStrA7(Gfo_cmd_arg_mgr.Key_prefix)
				.AddStrU8(StringUtl.PadEnd(arg.Key(), key_max + 1, " "))
				.AddStrU8(StringUtl.PadEnd(StringUtl.Format("[{0}:{1}]", arg.Reqd_str(), arg.Val_tid_str()), tid_max, " "));
			if (arg.Dflt() != null) {
				String dflt_val = ObjectUtl.ToStrOrNullMark(arg.Dflt());
				tmp_bfr.AddStrU8(StringUtl.Format(" default={0}", dflt_val));
			}
			tmp_bfr.AddByteNl();
			if (arg.Note() != null)
				tmp_bfr.AddStrA7("    ").AddStrU8(arg.Note()).AddByteNl();
		}
		return tmp_bfr.ToStrAndClear();
	}
}