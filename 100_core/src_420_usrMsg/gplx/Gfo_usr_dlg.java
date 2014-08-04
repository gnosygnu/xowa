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
public interface Gfo_usr_dlg extends GfoInvkAble, Cancelable {
	void Canceled_y_(); void Canceled_n_();
	void Clear();
	Gfo_usr_dlg_ui Ui_wkr(); void Ui_wkr_(Gfo_usr_dlg_ui v);
	Gfo_log_wtr Log_wtr(); void Log_wtr_(Gfo_log_wtr v);
	String Log_many(String grp_key, String msg_key, String fmt, Object... args);
	String Warn_many(String grp_key, String msg_key, String fmt, Object... args);
	Err    Fail_many(String grp_key, String msg_key, String fmt, Object... args);
	String Prog_many(String grp_key, String msg_key, String fmt, Object... args);
	String Prog_none(String grp_key, String msg_key, String fmt);
	String Note_many(String grp_key, String msg_key, String fmt, Object... args);
	String Note_none(String grp_key, String msg_key, String fmt);
	String Note_gui_none(String grp_key, String msg_key, String fmt);
	String Prog_one(String grp_key, String msg_key, String fmt, Object arg);
	String Prog_direct(String msg);
	String Log_direct(String msg);
	String Plog_many(String grp_key, String msg_key, String fmt, Object... args);
}
