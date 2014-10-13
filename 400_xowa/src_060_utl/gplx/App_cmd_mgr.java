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
package gplx;
public class App_cmd_mgr {
	private OrderedHash expd_args = OrderedHash_.new_(), actl_args = OrderedHash_.new_(); 
	private ListAdp tmp_vals = ListAdp_.new_(); private String[] orig_ary;
	public App_cmd_mgr Msg_root_(Gfo_msg_root v) {msg_root = v; return this;} private Gfo_msg_root msg_root = Gfo_msg_root._;
	public String Arg_prefix() {return arg_prefix;} public App_cmd_mgr Arg_prefix_(String v) {arg_prefix = v; prefix_len = String_.Len(v); return this;} private String arg_prefix = "--"; int prefix_len = 2;
	public void Clear() {expd_args.Clear(); actl_args.Clear(); errs.Clear(); key_help = key_header = null;}
	public int Actl_len() {return actl_args.Count();}
	public App_cmd_arg[] Actl_ary() {return (App_cmd_arg[])actl_args.Xto_ary(App_cmd_arg.class);}
	public App_cmd_mgr Expd_add_many(App_cmd_arg... ary) {for (App_cmd_arg o : ary) Expd_add(o); return this;}
	public App_cmd_mgr Expd_add(App_cmd_arg prm) {
		expd_args.Add(prm.Key(), prm);
		switch (prm.Tid()) {
			case App_cmd_arg.Tid_help: 		key_help = prm.Key(); break;
			case App_cmd_arg.Tid_header: 	key_header = prm.Key(); break;
			case App_cmd_arg.Tid_args: 		key_args = prm.Key(); break;
		}
		return this;
	}	String key_help, key_header, key_args;
	public boolean Args_process(String[] ary) {Args_parse(ary); return errs.Count() == 0;}
	private void Args_parse(String[] ary) {
		this.orig_ary = ary;
		App_cmd_arg arg = null;			
		int ary_len = ary.length;
		actl_args = Expd_copy();
		errs.Clear(); tmp_vals.Clear();
		for (int i = 0; i < ary_len; i++) {
			String itm = ary[i];
			if (String_.HasAtBgn(itm, arg_prefix)) {	// key
				if (arg != null) {
					String[] tmp_ary = tmp_vals.XtoStrAry();
					if (!arg.Parse(this, tmp_ary)) {continue;}
					tmp_vals.Clear();
				}
				String key = String_.Mid(itm, prefix_len);
				Object o = actl_args.Fetch(key);			
				if (o == null) {Errs_add(Err_argument_is_unknown, "unknown argument: '~{0}'", key); continue;} 
				arg = (App_cmd_arg)o;
				if (arg.Dirty()) {Errs_add(Err_argument_is_duplicate, "duplicate argument: '~{0}'", key); continue;}
				arg.Dirty_(true);
			}
			else {
				if (arg == null) {Errs_add(Err_argument_is_invalid_key, "argument key must be prefixed with '~{0}'; EX: '~{0}~{1}'", arg_prefix, itm); continue;}	// should only happen if 1st itm is not "--%"
//					if (arg.Val() != null) return Errs_add("argument_is_already_valued", "argument can only take one value: '{0}'", itm);
				tmp_vals.Add(itm);
			}
		}
		if (arg != null) {
			String[] tmp_ary = tmp_vals.XtoStrAry();
			arg.Parse(this, tmp_ary);
			tmp_vals.Clear();
		}
		int len = actl_args.Count();
		for (int i = 0; i < len; i++) {
			arg = (App_cmd_arg)actl_args.FetchAt(i);
			if (arg.Reqd() && !arg.Dirty()) {Errs_add(Err_argument_is_required, "argument is required: '~{0}'", arg.Key()); continue;}
			if (!arg.Dirty() && arg.Dflt() != null) arg.Val_(arg.Dflt());
		}
	}	public static final String Err_argument_is_duplicate = "argument_is_duplicate", Err_argument_is_required = "argument_is_required", Err_argument_is_unknown = "argument_is_unknown", Err_argument_is_invalid_key = "argument_is_invalid_key";
	public String Fmt_hdr() {return fmt_hdr;} public App_cmd_mgr Fmt_hdr_(String v) {fmt_hdr = v; return this;} private String fmt_hdr = "";
	public App_cmd_arg Args_get(String key) {return (App_cmd_arg)actl_args.Fetch(key);}
	public boolean Args_has_help() {
		App_cmd_arg arg = (App_cmd_arg)actl_args.Fetch(key_help);
		return arg != null && arg.Dirty();
	}
	public App_cmd_mgr Print_header(Gfo_usr_dlg usr_dlg) {
		App_cmd_arg arg_hdr = (App_cmd_arg)actl_args.Fetch(key_header);
		if (arg_hdr == null) return this;				// no key_header specified; assume header shouldn't be printed
		if (!arg_hdr.Val_as_bool()) return this;		// key_header specified as false; return;
		usr_dlg.Note_gui_none(GRP_KEY, "print.header", fmt_hdr);
		return this;
	}
	public void Print_args(Gfo_usr_dlg usr_dlg) {
		sb.Add_char_crlf();
		sb.Add_str_w_crlf("arguments:");
		int len = orig_ary.length;
		if (len == 0) {
			sb.Add_fmt_line("  **** NONE ****");
			sb.Add_fmt_line("  use --help to show help");
		}
		else {
			for (int i = 0; i < len; i++)
				sb.Add_fmt_line("  [{0}] = '{1}'", i, orig_ary[i]);			
		}
		usr_dlg.Note_none(GRP_KEY, "print.args", sb.Xto_str_and_clear());
	}	String_bldr sb = String_bldr_.new_();
	public void Print_fail(Gfo_usr_dlg usr_dlg) {
		sb.Add("** error: ").Add_char_crlf();
		int len = errs.Count();
		for (int i = 0; i < len; i++) {
			Gfo_msg_data data = (Gfo_msg_data)errs.FetchAt(i);
			sb.Add_fmt_line("  " + data.Gen_str_ary());
		}
		sb.Add_char_crlf();
		sb.Add_str_w_crlf(String_.Repeat("-", 80));
		usr_dlg.Note_none(GRP_KEY, "print.fail", sb.Xto_str_and_clear());
	}
	public void Print_help(Gfo_usr_dlg usr_dlg, String app_name) {
		sb.Add_str_w_crlf("example:");
		sb.Add_fmt("  java -jar {0}.jar", app_name);
		int key_max = 0, tid_max = 0;
		int len = expd_args.Count();
		for (int i = 0; i < len; i++) {
			App_cmd_arg arg = (App_cmd_arg)expd_args.FetchAt(i);
			if (arg.Tid() != App_cmd_arg.Tid_general) continue; // skip header, help
			sb.Add(" ").Add(arg_prefix).Add(arg.Key()).Add(" ").Add(arg.Example());
			int key_len = String_.Len(arg.Key()); if (key_len > key_max) key_max = key_len;
			int tid_len = String_.Len(String_.Format("[{0}:{1}]", arg.Reqd_str(), arg.Val_tid_str())); if (tid_len > tid_max) tid_max = tid_len;
		}	sb.Add_char_crlf();
		sb.Add_char_crlf();
		sb.Add_str_w_crlf("detail:");
		for (int i = 0; i < len; i++) {
			App_cmd_arg arg = (App_cmd_arg)expd_args.FetchAt(i);
//				if (arg.Tid() != App_cmd_arg.Tid_general) continue; // skip header, help
			sb.Add("  ").Add(arg_prefix).Add(String_.PadEnd(arg.Key(), key_max + 1, " ")).Add(String_.PadEnd(String_.Format("[{0}:{1}]", arg.Reqd_str(), arg.Val_tid_str()), tid_max, " "));
			if (arg.Dflt() != null)
				sb.Add_fmt(" default={0}", arg.Dflt());
			sb.Add_char_crlf();
			if (arg.Note() != null)
				sb.Add("    ").Add(arg.Note()).Add_char_crlf();
//				for (int j = 0; j < arg.Itms().Count(); j++) {
//					App_arg_info expdInf = (App_arg_info)arg.Itms().FetchAt(j);
//					sb.Add("    ").Add(String_.PadEnd(expdInf.Key(), key_max + 1, " ")).Add_str_w_crlf(expdInf.Descrip());
//				}
		}
		usr_dlg.Note_gui_none(GRP_KEY, "print.info", sb.Xto_str_and_clear());
	}
	private OrderedHash Expd_copy() {
		OrderedHash rv = OrderedHash_.new_();
		int expd_len = expd_args.Count();
		for (int i = 0 ; i < expd_len; i++) {
			App_cmd_arg arg = (App_cmd_arg)expd_args.FetchAt(i);
			rv.Add(arg.Key(), arg.Clone());
		}
		return rv;
	}		
	public boolean Errs_add(String key, String fmt, Object... vals) {
		errs.Add(msg_root.Data_new_many(Gfo_msg_itm_.Cmd_warn, GRP_KEY, key, fmt, vals));
		return false;
	}
	public int			Errs_len()		{return errs.Count();} private ListAdp errs = ListAdp_.new_();
	public Gfo_msg_data Errs_get(int i) {return (Gfo_msg_data)errs.FetchAt(i);}
	private static final String GRP_KEY = "gplx.app.app_cmd_mgr";
}