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
package gplx.xowa.guis.bnds; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.gfui.*;
public class Xog_bnd_win implements GfoInvkAble {
	private GfuiWin win;
	private GfuiTextBox shortcut_txt, binding_txt, keycode_txt;
	private GfuiBtn ok_btn, cxl_btn;
	private Gfui_bnd_parser bnd_parser;
	public void Show(Gfui_kit kit, GfuiWin owner_win, Gfui_bnd_parser bnd_parser, String shortcut_text, String binding_text) {
		// create controls
		this.win = kit.New_win_utl("shortcut_win", owner_win); win.BackColor_(ColorAdp_.White).Size_(200, 120);
		this.bnd_parser = bnd_parser;
		GfuiLbl shortcut_lbl		= Make_lbl(kit, win, "shortcut_lbl"	, "Shortcut:");
		GfuiLbl binding_lbl			= Make_lbl(kit, win, "binding_lbl"	, "Binding:");
		GfuiLbl keycode_lbl			= Make_lbl(kit, win, "keycode_lbl"	, "Keycode:");
		this.shortcut_txt			= Make_txt(kit, win, "shortcut_txt"	, shortcut_text);
		this.binding_txt			= Make_txt(kit, win, "binding_txt"	, binding_text);
		this.keycode_txt			= Make_txt(kit, win, "keycode_txt"	, "");
		this.ok_btn					= Make_btn(kit, win, "ok_btn"		, "Ok");
		this.cxl_btn				= Make_btn(kit, win, "cxl_btn"		, "Cancel");
		// layout controls
		Layout( 0, shortcut_lbl	, shortcut_txt);
		Layout(20, binding_lbl	, binding_txt);
		Layout(40, keycode_lbl	, keycode_txt);
		ok_btn.Pos_(110, 70); cxl_btn.Pos_(150, 70);
		// hookup events
		IptCfg null_cfg = IptCfg_.Null; IptEventType btn_event_type = IptEventType_.add_(IptEventType_.MouseDown, IptEventType_.KeyDown); IptArg[] btn_args = IptArg_.Ary(IptMouseBtn_.Left, IptKey_.Enter, IptKey_.Space);
		IptBnd_.ipt_to_(null_cfg		, binding_txt	, this, Invk_when_key_down	, IptEventType_.KeyDown, IptArg_.Wildcard);
		IptBnd_.ipt_to_(null_cfg		, binding_txt	, this, Invk_when_key_up	, IptEventType_.KeyUp, IptArg_.Wildcard);
		IptBnd_.ipt_to_(null_cfg		, ok_btn		, this, "when_ok"			, btn_event_type, btn_args);
		IptBnd_.ipt_to_(null_cfg		, cxl_btn		, this, "when_cxl"			, btn_event_type, btn_args);
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
		return (GfuiTextBox)kit.New_text_box(key, owner_win).Text_(text).Size_(120, 20).Border_on_();
	}
	private GfuiBtn Make_btn(Gfui_kit kit, GfuiWin owner_win, String key, String text) {
		return (GfuiBtn)kit.New_btn(key, owner_win).Text_(text).Size_(40, 20).Focus_able_(true);
	}
	private void Layout(int y, GfuiElem elem_1, GfuiElem elem_2) {
		elem_1.Y_(y);
		elem_2.Y_(y);
		elem_2.X_(elem_1.X_max());
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_when_key_down))		When_key_down(m);
		else if	(ctx.Match(k, Invk_when_key_press))		When_key_up(m);
		else if	(ctx.Match(k, Invk_when_key_up))		When_key_up(m);
		else if	(ctx.Match(k, Invk_when_ok))			{win.Close();}
		else if	(ctx.Match(k, Invk_when_cxl))			{win.Close();}
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	private static final String Invk_when_key_down = "when_key_down", Invk_when_key_press = "when_key_press", Invk_when_key_up = "when_key_up"
	, Invk_when_ok = "when_ok", Invk_when_cxl = "when_cxl"
	;
}
