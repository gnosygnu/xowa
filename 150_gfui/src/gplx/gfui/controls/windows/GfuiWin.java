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
package gplx.gfui.controls.windows; import gplx.*; import gplx.gfui.*; import gplx.gfui.controls.*;
import java.awt.Window;
import gplx.gfui.ipts.*; import gplx.gfui.layouts.*; import gplx.gfui.kits.core.*; import gplx.gfui.controls.gxws.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.customs.*;
import gplx.core.envs.*; import gplx.gfui.imgs.*;
public class GfuiWin extends GfuiElemBase {
	private GxwWin win; private List_adp loadList = List_adp_.New(); 
	public void Show()					{win.ShowWin();}
	public void Hide()					{win.HideWin();}
	public void Close()					{win.CloseWin();}
	public IconAdp Icon()				{return win.IconWin();} public GfuiWin Icon_(IconAdp icon) {win.IconWin_set(icon); return this;}
	public boolean Pin()					{return win.Pin();} public GfuiWin Pin_(boolean v) {win.Pin_set(v); return this;}
	public GfuiWin Pin_()				{return Pin_(true);} public void Pin_toggle() {Pin_(!Pin());}
	@gplx.Virtual public void Quit()			{GfuiQuitMode.Exec(this, quitMode);}
	public boolean Maximized() {return win.Maximized();} public void Maximized_(boolean v) {win.Maximized_(v);}
	public boolean Minimized() {return win.Minimized();} public void Minimized_(boolean v) {win.Minimized_(v);}
	public GfuiQuitMode QuitMode()		{return quitMode;} public GfuiWin QuitMode_(GfuiQuitMode val) {quitMode = val; return this;} private GfuiQuitMode quitMode = GfuiQuitMode.ExitApp; // easier to debug
	@Override public boolean Opened_done()	{return opened;} private boolean opened;
	@Override public GfuiWin OwnerWin()	{return this;}  // TODO_OLD: null
	@gplx.Internal protected GfuiWinKeyCmdMgr KeyCmdMgr() {return keyCmdMgr;} private GfuiWinKeyCmdMgr keyCmdMgr = GfuiWinKeyCmdMgr.new_(); 
	public GfuiWinFocusMgr FocusMgr() {return focusMgr;} private GfuiWinFocusMgr focusMgr;
	@gplx.New public GfuiWin Size_(SizeAdp size) {
		super.Size_(size);
		if (!opened && (size.Width() < 112 || size.Height() < 27)) // WORKAROUND/WINFORMS: Form.Size must be > 112,27 if Form is not Visible
			smallOpenSize = size;				
		return this;
	}	private SizeAdp smallOpenSize = SizeAdp_.Null;
	@Override public void ctor_kit_GfuiElemBase(Gfui_kit kit, String key, GxwElem underElem, Keyval_hash ctorArgs) {
		super.ctor_kit_GfuiElemBase(kit, key, underElem, ctorArgs);
		win = (GxwWin)underElem;
		win.OpenedCmd_set(Gfo_invk_cmd.New_by_key(this, Evt_Opened));
		Gfo_evt_mgr_.Sub(this, GfuiElemKeys.IptRcvd_evt, keyCmdMgr, GfuiWinKeyCmdMgr.CheckForHotKey_cmd);
		IptBnd_.cmd_(IptCfg_.Null, this, StopAppByAltF4_evt, IptKey_.Alt.Add(IptKey_.F4));
//			IptBnd_.cmd_to_(IptCfg_.Null, this, GfoConsoleWin.Instance, GfoConsoleWin.Invk_Show, IptKey_.Ctrl.Add(IptKey_.Alt).Add(IptKey_.E));
		IptBnd_.cmd_(IptCfg_.Null, this, Invk_ShowFocusOwner, IptKey_.add_(IptKey_.Ctrl, IptKey_.Alt, IptKey_.F12));
		loadList.Add(keyCmdMgr); loadList.Add(GfuiTipTextMgr.Instance);
		focusMgr = GfuiWinFocusMgr.new_(this);
	}
	@Override public void ctor_GfuiBox_base(Keyval_hash ctorArgs) {
		super.ctor_GfuiBox_base(ctorArgs);
		win = (GxwWin)this.UnderElem();
		win.OpenedCmd_set(Gfo_invk_cmd.New_by_key(this, Evt_Opened));
		Gfo_evt_mgr_.Sub(this, GfuiElemKeys.IptRcvd_evt, keyCmdMgr, GfuiWinKeyCmdMgr.CheckForHotKey_cmd);
		IptBnd_.cmd_(IptCfg_.Null, this, StopAppByAltF4_evt, IptKey_.Alt.Add(IptKey_.F4));
		IptBnd_.cmd_to_(IptCfg_.Null, this, GfoConsoleWin.Instance, GfoConsoleWin.Invk_Show, IptKey_.Ctrl.Add(IptKey_.Alt).Add(IptKey_.E));
		IptBnd_.cmd_(IptCfg_.Null, this, Invk_ShowFocusOwner, IptKey_.add_(IptKey_.Ctrl, IptKey_.Alt, IptKey_.F12));
		loadList.Add(keyCmdMgr); loadList.Add(GfuiTipTextMgr.Instance);
		focusMgr = GfuiWinFocusMgr.new_(this);
	}
	@Override public GxwElem UnderElem_make(Keyval_hash ctorArgs) {
		String type = (String)ctorArgs.Get_val_or(GfuiWin_.InitKey_winType, GfuiWin_.InitKey_winType_app);
		if		(String_.Eq(type, GfuiWin_.InitKey_winType_tool))		return GxwElemFactory_.Instance.win_tool_(ctorArgs);
		else if	(String_.Eq(type, GfuiWin_.InitKey_winType_toaster))	return GxwElemFactory_.Instance.win_toaster_(ctorArgs);
		else															return GxwElemFactory_.Instance.win_app_();
	}
	@Override public void Opened_cbk() {
		if (!smallOpenSize.Eq(SizeAdp_.Null)) super.Size_(smallOpenSize);	// NOTE: call before opened = true, else Layout will happen again
		opened = true;
		GftGrid.LytExecRecur(this);
		GfuiWinUtl.Open_exec(this, loadList, this);
		GfuiFocusOrderer.OrderByX(this);
		focusMgr.Init(this);
				if (this.Kit().Tid() == Gfui_kit_.Swing_tid)
			((Window)win).setFocusTraversalPolicy(new FocusTraversalPolicy_cls_base(focusMgr));
				this.Focus();
		super.Opened_cbk();
		Gfo_evt_mgr_.Pub(this, Evt_Opened);
	}
	@Override public boolean VisibleChangedCbk() {
		boolean rv = super.VisibleChangedCbk();
		Gfo_evt_mgr_.Pub_val(this, Evt_VisibleChanged, this.Visible());
		return rv;
	}
	@Override public boolean DisposeCbk() {
		GfuiWinUtl.SubElems_dispose(this);
		return super.DisposeCbk();
	}
	public GfuiWin TaskbarVisible_(boolean val) {win.TaskbarVisible_set(val); return this;}
	public void TaskbarParkingWindowFix(GfuiWin owner) {
		if (Env_.Mode_testing()) return; // FIXME: owner.UnderElem will throw exception in MediaPlaylistMgr_tst; see note there
		if (owner == null) return;
		win.TaskbarParkingWindowFix(owner.UnderElem());
	}
	void StopAppByAltF4(IptEventData ipt) {
		ipt.Handled_on();								// WORKAROUND/WINFORMS: must set Handled to true, or else WinForms.Form.Close() will be called
//			GfoFwd_.Send_event(this, GfuiWin.Invk_Quit);	// NOTE: no longer relying on Invk_Quit; // NOTE: must call send in order to execute other commands added to Cmds() (ex: DVD AppForm)
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_Quit))								{Quit(); Gfo_evt_mgr_.Pub(this, Evt_Quited);}
		else if	(ctx.Match(k, Invk_Zorder_front))						Zorder_front();
		else if	(ctx.Match(k, Invk_Minimize))							{win.Minimized_(true); return this;}
		else if	(ctx.Match(k, Invk_Pin_toggle))							Pin_toggle();
		else if	(ctx.Match(k, Invk_Show))								Show();
		else if	(ctx.Match(k, Evt_Opened))								Opened_cbk();
		else if	(ctx.Match(k, StopAppByAltF4_evt))						StopAppByAltF4(IptEventData.ctx_(ctx, m));
		else if	(ctx.Match(k, Invk_ShowFocusOwner))						GfuiEnv_.ShowMsg(GfuiFocusMgr.Instance.FocusedElem().Key_of_GfuiElem());
		else if	(ctx.Match(k, GfuiStatusBoxBnd.Invk_ShowTime))			{UsrDlg_.Instance.Note(UsrMsg.new_(Datetime_now.Get().toString())); return this;}
		else if	(ctx.MatchIn(k, Invk_Close, GfuiQuitMode.Destroy_cmd))	Close();
		else if	(ctx.MatchIn(k, Invk_Hide, GfuiQuitMode.Suspend_cmd))	Hide();
		else {
			Object rv = this.InvkMgr().Invk(ctx, ikey, k, m, this);
			return (rv == Gfo_invk_cmd_mgr.Unhandled) ? super.Invk(ctx, ikey, k, m) : rv;
		}
		return this;
	}	public static final    String Invk_Show = "Show", Invk_Hide = "Hide", Invk_Close = "Close", Invk_Quit = "Quit", Invk_Minimize = "Minimize"
			, Invk_Pin_toggle = "Pin_toggle", Invk_Zorder_front = "Zorder_front", Invk_ShowFocusOwner = "ShowFocusOwner"
			, Evt_VisibleChanged = "VisibleChanged", Evt_Opened = "Opened_evt", Evt_Quited = "Quited_evt"
			, StopAppByAltF4_evt = "StopAppByAltF4_evt";
}
