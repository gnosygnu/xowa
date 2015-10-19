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
public abstract class Gfui_kit_base implements Gfui_kit {
	private KeyValHash ctor_args = KeyValHash.new_();
	public abstract byte Tid();
	public abstract String Key();
	public abstract GxwElemFactory_base Factory();
	public GfuiWin Main_win() {return main_win;} public Gfui_kit Main_win_(GfuiWin v) {main_win = v; return this;} private GfuiWin main_win;
	public Gfui_clipboard Clipboard() {return Gfui_clipboard_.Null;}
	public GfoInvkAbleCmd Kit_term_cbk() {return kit_term_cbk;} public void	Kit_term_cbk_(GfoInvkAbleCmd v) {kit_term_cbk = v;} private GfoInvkAbleCmd kit_term_cbk;
	public void Cfg_set(String type, String key, Object val) {}
	public boolean Kit_mode__ready() {return true;}
	public void Kit_init(Gfo_usr_dlg gui_wtr) {}
	@gplx.Virtual public void Kit_run() {}
	@gplx.Virtual public void Kit_term() {kit_term_cbk.Invk();}
	@gplx.Virtual public void Ask_ok(String grp_key, String msg_key, String fmt, Object... args) {}
	public boolean Ask_yes_no(String grp_key, String msg_key, String fmt, Object... args) {return false;}
	public int Ask_yes_no_cancel(String grp_key, String msg_key, String fmt, Object... args) {return Gfui_dlg_msg_.Btn_cancel;}
	public boolean Ask_ok_cancel(String grp_key, String msg_key, String fmt, Object... args) {return false;}
	public void Btn_img_(GfuiBtn btn, IconAdp v) {}
	public GfuiInvkCmd New_cmd_sync(GfoInvkAble invk) {return new Gfui_kit_cmd_sync(invk);}
	public GfuiInvkCmd New_cmd_async(GfoInvkAble invk) {return new Gfui_kit_cmd_async(invk);}
	public GfuiWin New_win_app(String key, KeyVal... args) {
		GfuiWin rv = GfuiWin_.kit_(this, key, this.Factory().win_app_(), ctor_args);
		main_win = rv;
		return rv;
	}
	public GfuiWin New_win_utl(String key, GfuiWin owner, KeyVal... args) {return GfuiWin_.kit_(this, key, this.Factory().win_tool_(ctor_args), ctor_args);}
	@gplx.Virtual public Gfui_html New_html(String key, GfuiElem owner, KeyVal... args) {
		Gfui_html rv = Gfui_html.kit_(this, key, this.New_html_impl(), ctor_args);
		owner.SubElems().Add(rv);
		return rv;
	}
	public Gfui_tab_mgr New_tab_mgr(String key, GfuiElem owner, KeyVal... args) {
		Gfui_tab_mgr rv = Gfui_tab_mgr.kit_(this, key, this.New_tab_mgr_impl(), ctor_args);
		owner.SubElems().Add(rv);
		return rv;
	}
	public Gfui_tab_itm New_tab_itm(String key, Gfui_tab_mgr owner, KeyVal... args) {
		Gfui_tab_itm rv = Gfui_tab_itm.kit_(this, key, this.New_tab_itm_impl(), ctor_args);
		owner.SubElems().Add(rv);
		return rv;
	}
	public GfuiTextBox New_text_box(String key, GfuiElem owner, KeyVal... args) {
		GfuiTextBox rv = GfuiTextBox_.kit_(this, key, this.Factory().text_fld_(), ctor_args);
		owner.SubElems().Add(rv);
		return rv;
	}
	@gplx.Virtual public GfuiBtn New_btn(String key, GfuiElem owner, KeyVal... args) {
		GfuiBtn rv = GfuiBtn_.kit_(this, key, New_btn_impl(), ctor_args);
		owner.SubElems().Add(rv);
		return rv;
	}
	@gplx.Virtual public GfuiLbl New_lbl(String key, GfuiElem owner, KeyVal... args) {
		GfuiLbl rv = GfuiLbl_.kit_(this, key, New_btn_impl(), ctor_args);
		owner.SubElems().Add(rv);
		return rv;
	}
	@gplx.Virtual public GfuiStatusBox New_status_box(String key, GfuiElem owner, KeyVal... args) {
		GfuiStatusBox rv = GfuiStatusBox_.kit_(this, key, this.Factory().text_memo_());
		owner.SubElems().Add(rv);
		return rv;
	}
	public void Set_mnu_popup(GfuiElem owner, Gfui_mnu_grp grp) {}
	protected abstract Gxw_html New_html_impl();
	protected abstract Gxw_tab_mgr New_tab_mgr_impl();
	protected abstract Gxw_tab_itm New_tab_itm_impl();
	protected abstract GxwElem New_btn_impl();
	@gplx.Virtual public Gfui_dlg_file New_dlg_file(byte type, String msg) {return Gfui_dlg_file_.Noop;}
	@gplx.Virtual public Gfui_dlg_msg New_dlg_msg(String msg) {return Gfui_dlg_msg_.Noop;}
	@gplx.Virtual public Gfui_mnu_grp New_mnu_popup(String key, GfuiElem owner) {return Gfui_mnu_grp_.Noop;}
	@gplx.Virtual public Gfui_mnu_grp New_mnu_bar(String key, GfuiWin owner) {return Gfui_mnu_grp_.Noop;}
	public abstract ImageAdp New_img_load(Io_url url);
	public Object New_color(int a, int r, int g, int b) {return null;}
	public float Calc_font_height(GfuiElem elem, String s) {return 13;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return this;
	}
}
class Gfui_kit_cmd_sync implements GfuiInvkCmd {
	public Gfui_kit_cmd_sync(GfoInvkAble target) {this.target = target;} private GfoInvkAble target;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return target.Invk(ctx, ikey, k, m);
	}
	public void Rls() {target = null;}
}
class Gfui_kit_cmd_async implements GfuiInvkCmd {
	public Gfui_kit_cmd_async(GfoInvkAble target) {this.target = target;} private GfoInvkAble target;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return target.Invk(ctx, ikey, k, m);
	}
	public void Rls() {target = null;}
}
