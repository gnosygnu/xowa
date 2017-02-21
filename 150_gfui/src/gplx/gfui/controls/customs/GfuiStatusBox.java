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
package gplx.gfui.controls.customs; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import gplx.core.envs.*; import gplx.gfui.kits.core.*; import gplx.gfui.envs.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.standards.*;
public class GfuiStatusBox extends GfuiTextBox implements UsrMsgWkr { 	public GfuiStatusBox Active_(boolean v)			{active = v; return this;} private boolean active = true;
	public GfuiStatusBox VisibilityDuration_(int v) {timer.Interval_(v); visibilityDuration = v; return this;} int visibilityDuration;
	@Override public void Opened_cbk() {
		super.Opened_cbk();
		UsrDlg_.Instance.Reg(UsrMsgWkr_.Type_Note, this);
	}
	public void ExecUsrMsg(int type, UsrMsg umsg) {
		if (	!active
			||	!this.OwnerElem().Visible() // WORKAROUND.WINFORMS: else application hangs on Invoke
			||	!this.Opened_done()
			) return;
		this.CreateControlIfNeeded();		// WORKAROUND.WINFORMS: else will sometimes throw: Cannot call Invoke or InvokeAsync on a control until the window handle has been created
		this.VisibilityDuration_(umsg.VisibilityDuration());		
		String text = String_.Replace(umsg.To_str(), Op_sys.Cur().Nl_str(), " "); // replace NewLine with " " since TextBox cannot show NewLine
		Invoke(Gfo_invk_cmd.New_by_val(this, Invk_WriteText, text));
	}
	public void WriteText(String text) {
		if (!this.Visible()) {
			this.Visible_set(true);
			this.Zorder_front();
		}
		this.Text_(text);
		timer.Enabled_off().Enabled_on();	// Enabled_off().Enabled_on() resets timer; timer can be at second 1 of 3, and this will reset back to timer 0
		GfuiEnv_.DoEvents();				// WORKAROUND.WINFORMS: needed, else text will not refresh (ex: splash screen); NOTE!!: will cause other timers to fire! (ex: mediaPlaylistMgr)
	}
	@Override public void Focus() {
		this.Focus_able_(true);
		super.Focus();
		this.Focus_able_(false);
	}
	@Override public boolean DisposeCbk() {
		super.DisposeCbk();
		timer.Rls();
		UsrDlg_.Instance.RegOff(UsrMsgWkr_.Type_Note, this);
		if (timerCmd != null) timerCmd.Rls();
		return true;
	}
	void HideWindow() {
		timer.Enabled_off();
		Text_("");
		this.Visible_set(false);
		this.Zorder_back();
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_HideWindow))			HideWindow();
		else if	(ctx.Match(k, Invk_Text_empty))			{timer.Enabled_off(); Text_(GfuiTextBox_.NewLine + Text());}
		else if	(ctx.Match(k, Invk_WriteText))			WriteText(m.ReadStr("v"));
		else return super.Invk(ctx, ikey, k, m);
		return this;
	}	static final    String Invk_HideWindow = "HideWindow", Invk_WriteText = "WriteText", Invk_Text_empty = "Text_empty";
	TimerAdp timer;
	@Override public void ctor_GfuiBox_base(Keyval_hash ctorArgs) {
		super.ctor_GfuiBox_base(ctorArgs);
		this.Border_on_(false);
		this.Focus_able_(false);
		this.Visible_set(false);
		timer = TimerAdp.new_(this, Invk_HideWindow, 2000, false);
	}
	@Override public void ctor_kit_GfuiElemBase(Gfui_kit kit, String key, GxwElem underElem, Keyval_hash ctorArgs) {
		super.ctor_kit_GfuiElemBase(kit, key, underElem, ctorArgs);
		this.Border_on_(false);
		this.Focus_able_(false);
		this.Visible_set(true);
		timerCmd = kit.New_cmd_sync(this);
		timer = TimerAdp.new_(timerCmd, GfuiInvkCmd_.Invk_sync, 2000, false);
	}	GfuiInvkCmd timerCmd;
	public void ctor_kit_GfuiElemBase_drd(Gfui_kit kit, String key, GxwElem underElem, Keyval_hash ctorArgs) {
		super.ctor_kit_GfuiElemBase(kit, key, underElem, ctorArgs);
		this.Border_on_(false);
		this.Focus_able_(false);
		this.Visible_set(true);
		timerCmd = kit.New_cmd_sync(this);
//			timer = TimerAdp.new_(timerCmd, GfuiInvkCmd_.Invk_sync, 2000, false);
	}
}
