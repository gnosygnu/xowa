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
import gplx.gfui.imgs.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.standards.*; import gplx.gfui.controls.customs.*; import gplx.gfui.controls.windows.*;
public abstract class Gfui_kit_base implements Gfui_kit {
	private Keyval_hash ctor_args = new Keyval_hash();
	public abstract byte Tid();
	public abstract String Key();
	public abstract GxwElemFactory_base Factory();
	public GfuiWin Main_win() {return main_win;} public Gfui_kit Main_win_(GfuiWin v) {main_win = v; return this;} private GfuiWin main_win;
	public Gfui_clipboard Clipboard() {return Gfui_clipboard_.Null;}
	public Gfo_invk_cmd Kit_term_cbk() {return kit_term_cbk;} public void	Kit_term_cbk_(Gfo_invk_cmd v) {kit_term_cbk = v;} private Gfo_invk_cmd kit_term_cbk;
	public void Cfg_set(String type, String key, Object val) {}
	public boolean Kit_mode__ready() {return true;}
	public void Kit_init(Gfo_usr_dlg gui_wtr) {}
	@gplx.Virtual public void Kit_run() {}
	@gplx.Virtual public void Kit_term() {kit_term_cbk.Exec();}
	@gplx.Virtual public void Ask_ok(String grp_key, String msg_key, String fmt, Object... args) {}
	public boolean Ask_yes_no(String grp_key, String msg_key, String fmt, Object... args) {return false;}
	public int Ask_yes_no_cancel(String grp_key, String msg_key, String fmt, Object... args) {return Gfui_dlg_msg_.Btn_cancel;}
	public boolean Ask_ok_cancel(String grp_key, String msg_key, String fmt, Object... args) {return false;}
	public void Btn_img_(GfuiBtn btn, IconAdp v) {}
	public GfuiInvkCmd New_cmd_sync(Gfo_invk invk) {return new Gfui_kit_cmd_sync(invk);}
	public GfuiInvkCmd New_cmd_async(Gfo_invk invk) {return new Gfui_kit_cmd_async(invk);}
	public GfuiWin New_win_app(String key, Keyval... args) {
		GfuiWin rv = GfuiWin_.kit_(this, key, this.Factory().win_app_(), ctor_args);
		main_win = rv;
		return rv;
	}
	public GfuiWin New_win_utl(String key, GfuiWin owner, Keyval... args) {return GfuiWin_.kit_(this, key, this.Factory().win_tool_(ctor_args), ctor_args);}
	@gplx.Virtual public Gfui_html New_html(String key, GfuiElem owner, Keyval... args) {
		Gfui_html rv = Gfui_html.kit_(this, key, this.New_html_impl(), ctor_args);
		owner.SubElems().Add(rv);
		return rv;
	}
	public Gfui_tab_mgr New_tab_mgr(String key, GfuiElem owner, Keyval... args) {
		Gfui_tab_mgr rv = Gfui_tab_mgr.kit_(this, key, this.New_tab_mgr_impl(), ctor_args);
		owner.SubElems().Add(rv);
		return rv;
	}
	public Gfui_tab_itm New_tab_itm(String key, Gfui_tab_mgr owner, Keyval... args) {
		Gfui_tab_itm rv = Gfui_tab_itm.kit_(this, key, this.New_tab_itm_impl(), ctor_args);
		owner.SubElems().Add(rv);
		return rv;
	}
	public Gfui_grp New_grp(String key, GfuiElem owner, Keyval... args) {
		Gfui_grp rv = Gfui_grp.kit_(this, key, this.New_grp_impl(), ctor_args);
		owner.SubElems().Add(rv);
		return rv;
	}
	public GfuiTextBox New_text_box(String key, GfuiElem owner, Keyval... args) {
		GfuiTextBox rv = GfuiTextBox_.kit_(this, key, this.Factory().text_fld_(), ctor_args);
		owner.SubElems().Add(rv);
		return rv;
	}
	@gplx.Virtual public GfuiBtn New_btn(String key, GfuiElem owner, Keyval... args) {
		GfuiBtn rv = GfuiBtn_.kit_(this, key, New_btn_impl(), ctor_args);
		owner.SubElems().Add(rv);
		return rv;
	}
	@gplx.Virtual public GfuiComboBox New_combo(String key, GfuiElem owner, Keyval... args) {
		GfuiComboBox rv = GfuiComboBox.kit_(this, key, New_combo_impl(), ctor_args);
		owner.SubElems().Add(rv);
		return rv;
	}
	@gplx.Virtual public GfuiLbl New_lbl(String key, GfuiElem owner, Keyval... args) {
		GfuiLbl rv = GfuiLbl_.kit_(this, key, New_btn_impl(), ctor_args);
		owner.SubElems().Add(rv);
		return rv;
	}
	@gplx.Virtual public GfuiStatusBox New_status_box(String key, GfuiElem owner, Keyval... args) {
		GfuiStatusBox rv = GfuiStatusBox_.kit_(this, key, this.Factory().text_memo_());
		owner.SubElems().Add(rv);
		return rv;
	}
	public void Set_mnu_popup(GfuiElem owner, Gfui_mnu_grp grp) {}
	protected abstract Gxw_html New_html_impl();
	protected abstract Gxw_tab_mgr New_tab_mgr_impl();
	protected abstract Gxw_tab_itm New_tab_itm_impl();
	protected abstract GxwElem New_grp_impl();
	protected abstract GxwElem New_btn_impl();
	protected abstract GxwElem New_combo_impl();
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
	public Gfui_kit_cmd_sync(Gfo_invk target) {this.target = target;} private Gfo_invk target;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return target.Invk(ctx, ikey, k, m);
	}
	public void Rls() {target = null;}
}
class Gfui_kit_cmd_async implements GfuiInvkCmd {
	public Gfui_kit_cmd_async(Gfo_invk target) {this.target = target;} private Gfo_invk target;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		return target.Invk(ctx, ikey, k, m);
	}
	public void Rls() {target = null;}
}
