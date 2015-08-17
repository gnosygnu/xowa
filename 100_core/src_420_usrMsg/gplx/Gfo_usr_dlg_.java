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
public class Gfo_usr_dlg_ {
	public static Gfo_usr_dlg			I		= Gfo_usr_dlg_noop._;	// NOTE: global instance which can be reassigned
        public static final Gfo_usr_dlg	Noop	= Gfo_usr_dlg_noop._;
	public static Gfo_usr_dlg Test() {
		if (test == null)
			test = new Gfo_usr_dlg_base(Gfo_usr_dlg__log_.Noop, Gfo_usr_dlg__gui_.Test);
		return test;
	}	private static Gfo_usr_dlg_base test;
	public static Gfo_usr_dlg Test_console() {
		if (test_console == null)
			test_console = new Gfo_usr_dlg_base(Gfo_usr_dlg__log_.Noop, Gfo_usr_dlg__gui_.Console);
		return test_console;
	}	private static Gfo_usr_dlg_base test_console;
}
class Gfo_usr_dlg_noop implements Gfo_usr_dlg {
	public boolean Canceled() {return false;} public void Canceled_y_() {} public void Canceled_n_() {}
	public void Cancel() {} public void Cancel_reset() {}
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
        public static final Gfo_usr_dlg_noop _ = new Gfo_usr_dlg_noop(); Gfo_usr_dlg_noop() {}
}
