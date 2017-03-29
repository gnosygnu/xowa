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
package gplx.xowa.guis.bnds; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.gfui.*; import gplx.gfui.draws.*; import gplx.gfui.ipts.*; import gplx.gfui.kits.core.*; import gplx.gfui.envs.*; import gplx.gfui.controls.windows.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.standards.*;
import gplx.gfui.layouts.swts.*;
import gplx.xowa.guis.cbks.*;
public class Xog_bnd_win implements Gfo_invk {
	private GfuiWin win;
	private GfuiTextBox shortcut_txt, binding_txt, keycode_txt;
	private GfuiBtn clear_btn, ok_btn, cxl_btn;
	private Gfui_bnd_parser bnd_parser = Gfui_bnd_parser.new_en_();
	private Xoa_app app;
	private String key_text;
	public void Show(Xoa_app app, Gfui_kit kit, GfuiWin owner_win, String key_text, String shortcut_text, String binding_text) {
		this.app = app;
		this.key_text = key_text;

		// create controls
		this.win = kit.New_win_utl("shortcut_win", owner_win); win.BackColor_(ColorAdp_.White).Size_(240, 140);
		GfuiLbl shortcut_lbl		= Make_lbl(kit, win, "shortcut_lbl"	, "Shortcut:");
		GfuiLbl binding_lbl			= Make_lbl(kit, win, "binding_lbl"	, "Binding:");
		GfuiLbl keycode_lbl			= Make_lbl(kit, win, "keycode_lbl"	, "Keycode:");
		this.shortcut_txt			= Make_txt(kit, win, "shortcut_txt"	, shortcut_text);
		this.binding_txt			= Make_txt(kit, win, "binding_txt"	, binding_text);
		this.keycode_txt			= Make_txt(kit, win, "keycode_txt"	, "");
		this.clear_btn				= Make_btn(kit, win, "clear_btn"	, "Clear");
		this.ok_btn					= Make_btn(kit, win, "ok_btn"		, "Ok");
		this.cxl_btn				= Make_btn(kit, win, "cxl_btn"		, "Cancel");

		// layout controls
		Layout( 0, shortcut_lbl	, shortcut_txt);
		Layout(20, binding_lbl	, binding_txt);
		Layout(40, keycode_lbl	, keycode_txt);
		clear_btn.Pos_(70, 70); ok_btn.Pos_(110, 70); cxl_btn.Pos_(150, 70);

		// hookup events
		IptCfg null_cfg = IptCfg_.Null; IptEventType btn_event_type = IptEventType_.add_(IptEventType_.MouseDown, IptEventType_.KeyDown); IptArg[] btn_args = IptArg_.Ary(IptMouseBtn_.Left, IptKey_.Enter, IptKey_.Space);
		IptBnd_.ipt_to_(null_cfg		, binding_txt	, this, Invk__when_key_down		, IptEventType_.KeyDown, IptArg_.Wildcard);
		IptBnd_.ipt_to_(null_cfg		, binding_txt	, this, Invk__when_key_up		, IptEventType_.KeyUp, IptArg_.Wildcard);
		IptBnd_.ipt_to_(null_cfg		, clear_btn		, this, Invk__when_clear		, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, ok_btn		, this, Invk__when_ok			, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, cxl_btn		, this, Invk__when_cxl			, btn_event_type, btn_args);

		// open
		win.Pos_(SizeAdp_.center_(ScreenAdp_.Primary.Size(), win.Size()));
		win.Show();
		binding_txt.Focus();
	}
	private void When_key_down(GfoMsg m) {
		IptEventData event_data = (IptEventData)m.Args_getAt(0).Val();
		int keycode = event_data.Key().Val();
		binding_txt.Text_(bnd_parser.Xto_norm(IptKey_.To_str(keycode)));
		keycode_txt.Text_(Int_.To_str(keycode));
		event_data.Handled_on();
	}
	private void When_key_up(GfoMsg m) {
		IptEventData event_data = (IptEventData)m.Args_getAt(0).Val();
		event_data.Handled_on();
	}
	private GfuiLbl Make_lbl(Gfui_kit kit, GfuiWin owner_win, String key, String text) {
		return (GfuiLbl)kit.New_lbl(key, owner_win).Text_(text).Size_(80, 20).BackColor_(ColorAdp_.White);
	}
	private GfuiTextBox Make_txt(Gfui_kit kit, GfuiWin owner_win, String key, String text) {
		return (GfuiTextBox)kit.New_text_box(key, owner_win).Text_(text).Size_(120, 20).Border_on_().BackColor_(ColorAdp_.White);
	}
	private GfuiBtn Make_btn(Gfui_kit kit, GfuiWin owner_win, String key, String text) {
		return (GfuiBtn)kit.New_btn(key, owner_win).Text_(text).Size_(40, 20).Focus_able_(true).Border_on_().BackColor_(ColorAdp_.White);
	}
	private void Layout(int y, GfuiElem elem_1, GfuiElem elem_2) {
		elem_1.Y_(y);
		elem_2.Y_(y);
		elem_2.X_(elem_1.X_max());
	}

	private final    Xog_cbk_trg cbk_trg = Xog_cbk_trg.New(gplx.xowa.addons.apps.cfgs.specials.edits.pages.Xocfg_edit_special.Prototype.Special__meta().Ttl_bry());
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk__when_key_down))		When_key_down(m);
		else if	(ctx.Match(k, Invk__when_key_press))	When_key_up(m);
		else if	(ctx.Match(k, Invk__when_key_up))		When_key_up(m);
		else if	(ctx.Match(k, Invk__when_clear))		binding_txt.Text_("None");
		else if	(ctx.Match(k, Invk__when_ok))			Remap_and_close(binding_txt.Text());

		else if	(ctx.Match(k, Invk__when_cxl))			{win.Close();}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private static final String Invk__when_key_down = "when_key_down", Invk__when_key_press = "when_key_press", Invk__when_key_up = "when_key_up"
	, Invk__when_clear= "when_clear", Invk__when_ok = "when_ok", Invk__when_cxl = "when_cxl"
	;
	private void Remap_and_close(String new_ipt) {
		app.Gui__cbk_mgr().Send_json(cbk_trg, "xo.cfg_edit.gui_binding__remap_recv", gplx.core.gfobjs.Gfobj_nde.New().Add_str("key", key_text).Add_str("bnd", new_ipt));
		win.Close();
	}
}
