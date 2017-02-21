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
package gplx.gfui.kits.core; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
import gplx.gfui.imgs.*; import gplx.gfui.controls.windows.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.standards.*;
public interface Gfui_kit extends Gfo_invk {
	byte			Tid();
	String			Key();
	void			Cfg_set(String type, String key, Object val);
	boolean			Kit_mode__ready();
	void			Kit_init(Gfo_usr_dlg gui_wtr);
	void			Kit_run();
	void			Kit_term();
	void			Kit_term_cbk_(Gfo_invk_cmd cmd);
	Gfui_clipboard	Clipboard();
	void			Ask_ok(String grp_key, String msg_key, String fmt, Object... args);
	boolean			Ask_yes_no(String grp_key, String msg_key, String fmt, Object... args);
	boolean			Ask_ok_cancel(String grp_key, String msg_key, String fmt, Object... args);
	int				Ask_yes_no_cancel(String grp_key, String msg_key, String fmt, Object... args);
	GfuiInvkCmd		New_cmd_sync(Gfo_invk invk);
	GfuiInvkCmd		New_cmd_async(Gfo_invk invk);
	GfuiWin			New_win_app(String key, Keyval... args);
	GfuiWin			New_win_utl(String key, GfuiWin owner, Keyval... args);
	Gfui_html		New_html(String key, GfuiElem owner, Keyval... args);
	Gfui_tab_mgr	New_tab_mgr(String key, GfuiElem owner, Keyval... args);
	Gfui_grp		New_grp(String key, GfuiElem owner, Keyval... args);
	GfuiTextBox		New_text_box(String key, GfuiElem owner, Keyval... args);
	GfuiBtn			New_btn(String key, GfuiElem owner, Keyval... args);
	GfuiComboBox	New_combo(String key, GfuiElem owner, Keyval... args);
	GfuiLbl			New_lbl(String key, GfuiElem owner, Keyval... args);
	Gfui_dlg_file	New_dlg_file(byte type, String msg);
	Gfui_dlg_msg	New_dlg_msg(String msg);
	ImageAdp		New_img_load(Io_url path);
	Object			New_color(int a, int r, int g, int b);
	Gfui_mnu_grp	New_mnu_popup(String key, GfuiElem owner);
	Gfui_mnu_grp	New_mnu_bar(String key, GfuiWin owner);
	void			Set_mnu_popup(GfuiElem owner, Gfui_mnu_grp grp);
	float			Calc_font_height(GfuiElem elem, String s);
}
