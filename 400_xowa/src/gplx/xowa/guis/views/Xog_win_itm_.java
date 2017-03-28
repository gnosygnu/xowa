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
package gplx.xowa.guis.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.gfui.*; import gplx.gfui.draws.*; import gplx.gfui.kits.core.*; import gplx.gfui.imgs.*; import gplx.gfui.controls.windows.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.standards.*;
import gplx.xowa.guis.bnds.*; import gplx.xowa.guis.cmds.*;
import gplx.xowa.guis.langs.*;
public class Xog_win_itm_ {
	public static void Show_win(Xog_win_itm win) {
		Xoae_app app = win.App(); GfuiWin win_box = win.Win_box();			
		win_box.Focus_able_(false);
		app.Gui_mgr().Nightmode_mgr().Enabled_by_cfg();
		Xog_startup_win_.Startup(app, win_box);

		win_box.Icon_(IconAdp.file_or_blank(app.Fsys_mgr().Bin_xowa_dir().GenSubFil_nest("file", "app.window", "app_icon.png")));
	}
	public static void Show_widget(boolean show, GfuiElem box, GfuiElem btn) {
		int box_w, btn_w;
		if (show) {
			box_w = Toolbar_txt_w;
			btn_w = Toolbar_btn_w;
		}
		else {
			box_w = 0;
			btn_w = 0;
		}
		box.Layout_data_(new gplx.gfui.layouts.swts.Swt_layout_data__grid().Hint_w_(box_w).Hint_h_(Toolbar_grp_h));	// WORKAROUND.SWT: need to specify height, else SWT will shrink textbox on re-layout when showing / hiding search / allpages; DATE:2017-03-28
		btn.Layout_data_(new gplx.gfui.layouts.swts.Swt_layout_data__grid().Hint_w_(btn_w).Align_w__fill_());
	}
	public static Gfui_grp new_grp(Xoae_app app, Gfui_kit kit, GfuiElem win, String id) {
		return kit.New_grp(id, win);
	}
	public static GfuiBtn new_btn(Xoae_app app, Gfui_kit kit, GfuiElem win, String id) {
		return kit.New_btn(id, win);
	}
	public static GfuiComboBox new_cbo(Xoae_app app, Gfui_kit kit, GfuiElem win, FontAdp ui_font, String id, boolean border_on) {
		GfuiComboBox rv = kit.New_combo(id, win, Keyval_.new_(GfuiTextBox.CFG_border_on_, border_on));
		rv.TextMgr().Font_(ui_font);
		return rv;
	}
	public static GfuiTextBox new_txt(Xoae_app app, Gfui_kit kit, GfuiElem win, FontAdp ui_font, String id, boolean border_on) {
		GfuiTextBox rv = kit.New_text_box(id, win, Keyval_.new_(GfuiTextBox.CFG_border_on_, border_on));
		rv.TextMgr().Font_(ui_font);
		return rv;
	}
	public static void Update_tiptext(Xoae_app app, GfuiElem elem, int tiptext_id) {
		elem.TipText_(Xog_win_itm_.new_tiptext(app, tiptext_id));
	}
	public static void Font_update(Xog_win_itm win, Xol_font_info itm_font) {
		FontAdp gui_font = win.Url_box().TextMgr().Font();
		if (!itm_font.Eq(gui_font)) {
			FontAdp new_font = itm_font.To_font();
			win.Url_box().TextMgr().Font_(new_font);
			win.Search_box().TextMgr().Font_(new_font);
			win.Allpages_box().TextMgr().Font_(new_font);
			win.Find_box().TextMgr().Font_(new_font);
			win.Prog_box().TextMgr().Font_(new_font);
			win.Info_box().TextMgr().Font_(new_font);
			win.Tab_mgr().Tab_mgr().TextMgr().Font_(new_font);
		}
	}
	public static String new_tiptext(Xoae_app app, int id) {return String_.new_u8(app.Usere().Lang().Msg_mgr().Val_by_id(app.Usere().Wiki(), id));}
	public static final int 
	  Toolbar_grp_h = 24
	, Toolbar_txt_w = 160
	, Toolbar_btn_w = 16;
}
