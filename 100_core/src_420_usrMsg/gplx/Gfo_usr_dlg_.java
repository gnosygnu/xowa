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
	public static Gfo_usr_dlg I = Gfo_usr_dlg_null._;
        public static final Gfo_usr_dlg Null = Gfo_usr_dlg_null._;
}
class Gfo_usr_dlg_null implements Gfo_usr_dlg {
	public boolean Canceled() {return false;} public void Canceled_y_() {} public void Canceled_n_() {}
	public void Cancel() {} public void Cancel_reset() {}
	public void Clear() {}
	public Gfo_usr_dlg_ui Ui_wkr() {throw Err_.not_implemented_();} public void Ui_wkr_(Gfo_usr_dlg_ui v) {}
	public Gfo_log_wtr Log_wtr() {throw Err_.not_implemented_();} public void Log_wtr_(Gfo_log_wtr v) {}
	public String Log_many(String grp_key, String msg_key, String fmt, Object... args) {return "";}
	public String Warn_many(String grp_key, String msg_key, String fmt, Object... args) {return "";}
	public Err Fail_many(String grp_key, String msg_key, String fmt, Object... args) {return Err_.new_(fmt);}
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
        public static final Gfo_usr_dlg_null _ = new Gfo_usr_dlg_null(); Gfo_usr_dlg_null() {}
}
