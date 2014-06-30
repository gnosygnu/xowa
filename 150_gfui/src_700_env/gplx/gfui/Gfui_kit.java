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
package gplx.gfui; import gplx.*;
public interface Gfui_kit extends GfoInvkAble {
	byte			Tid();
	String			Key();
	void			Cfg_set(String type, String key, Object val);
	boolean			Kit_init_done();
	void			Kit_init(Gfo_usr_dlg gui_wtr);
	void			Kit_run();
	void			Kit_term();
	void			Kit_term_cbk_(GfoInvkAbleCmd cmd);
	Gfui_clipboard	Clipboard();
	void			Ask_ok(String grp_key, String msg_key, String fmt, Object... args);
	boolean			Ask_yes_no(String grp_key, String msg_key, String fmt, Object... args);
	boolean			Ask_ok_cancel(String grp_key, String msg_key, String fmt, Object... args);
	int				Ask_yes_no_cancel(String grp_key, String msg_key, String fmt, Object... args);
	GfuiInvkCmd		New_cmd_sync(GfoInvkAble invk);
	GfuiInvkCmd		New_cmd_async(GfoInvkAble invk);
	GfuiWin			New_win_app(String key, KeyVal... args);
	GfuiWin			New_win_utl(String key, GfuiWin owner, KeyVal... args);
	Gfui_html		New_html(String key, GfuiElem owner, KeyVal... args);
	Gfui_tab_mgr	New_tab_mgr(String key, GfuiElem owner, KeyVal... args);
	GfuiTextBox		New_text_box(String key, GfuiElem owner, KeyVal... args);
	GfuiBtn			New_btn(String key, GfuiElem owner, KeyVal... args);
	Gfui_dlg_file	New_dlg_file(byte type, String msg);
	Gfui_dlg_msg	New_dlg_msg(String msg);
	ImageAdp		New_img_load(Io_url path);
	Object			New_color(int a, int r, int g, int b);
	Gfui_mnu_grp	New_mnu_popup(String key, GfuiElem owner);
	Gfui_mnu_grp	New_mnu_bar(String key, GfuiWin owner);
	void			Set_mnu_popup(GfuiElem owner, Gfui_mnu_grp grp);
	float			Calc_font_height(GfuiElem elem, String s);
}
