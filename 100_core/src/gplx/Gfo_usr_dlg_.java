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
package gplx;
public class Gfo_usr_dlg_ {
	private static Gfo_usr_dlg_base test__list, test__show;
	public static Gfo_usr_dlg			Instance	= Gfo_usr_dlg_noop.Instance;	// NOTE: global instance which can be reassigned
        public static final    Gfo_usr_dlg	Noop		= Gfo_usr_dlg_noop.Instance;
	public static Gfo_usr_dlg__gui Test__list__init() {
		if (test__list == null)
			test__list = new Gfo_usr_dlg_base(Gfo_usr_dlg__log_.Noop, Gfo_usr_dlg__gui_.Test);
		Gfo_usr_dlg__gui_.Test.Clear();
		Instance = test__list;
		return Gfo_usr_dlg__gui_.Test;
	}
	public static String Test__list__term__get_1st() {
		Instance = Noop;
		String[] rv = ((Gfo_usr_dlg__gui_test)test__list.Gui_wkr()).Warns().To_str_ary_and_clear();
		return rv.length == 0 ? "" : rv[0];
	}
	public static void Test__show__init() {
		if (test__show == null)
			test__show = new Gfo_usr_dlg_base(Gfo_usr_dlg__log_.Noop, Gfo_usr_dlg__gui_.Console);
		Instance = test__show;
	}
	public static void Test__show__term() {
		Instance = Noop;
	}
	public static Gfo_usr_dlg Test() {
		if (test__list == null)
			test__list = new Gfo_usr_dlg_base(Gfo_usr_dlg__log_.Noop, Gfo_usr_dlg__gui_.Test);
		return test__list;
	}
	public static Gfo_usr_dlg Test_console() {
		if (test_console == null)
			test_console = new Gfo_usr_dlg_base(Gfo_usr_dlg__log_.Noop, Gfo_usr_dlg__gui_.Console);
		return test_console;
	}	private static Gfo_usr_dlg_base test_console;
}
class Gfo_usr_dlg_noop implements Gfo_usr_dlg {
	public boolean Canceled() {return false;} public void Canceled_y_() {} public void Canceled_n_() {}
	public void Cancel() {}
	public void Clear() {}
	public Gfo_usr_dlg__log Log_wkr() {return Gfo_usr_dlg__log_.Noop;} public void Log_wkr_(Gfo_usr_dlg__log v) {}
	public Gfo_usr_dlg__gui Gui_wkr() {return Gfo_usr_dlg__gui_.Noop;} public void Gui_wkr_(Gfo_usr_dlg__gui v) {}
	public String Log_many(String grp_key, String msg_key, String fmt, Object... args) {return "";}
	public String Warn_many(String grp_key, String msg_key, String fmt, Object... args) {return "";}
	public Err Fail_many(String grp_key, String msg_key, String fmt, Object... args) {return Err_.new_wo_type(fmt);}
	public String Prog_many(String grp_key, String msg_key, String fmt, Object... args) {return "";}
	public String Prog_none(String grp_key, String msg_key, String fmt) {return "";}
	public String Note_many(String grp_key, String msg_key, String fmt, Object... args) {return "";}
	public String Note_none(String grp_key, String msg_key, String fmt) {return "";}
	public String Note_gui_none(String grp_key, String msg_key, String fmt) {return "";}
	public String Prog_one(String grp_key, String msg_key, String fmt, Object arg) {return "";}
	public String Prog_direct(String msg) {return "";}
	public String Log_direct(String msg) {return "";}
	public String Plog_many(String grp_key, String msg_key, String fmt, Object... args) {return "";}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return this;}
        public static final    Gfo_usr_dlg_noop Instance = new Gfo_usr_dlg_noop(); Gfo_usr_dlg_noop() {}
}
