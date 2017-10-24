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
import gplx.gfui.draws.*; import gplx.gfui.ipts.*; import gplx.gfui.layouts.*; import gplx.gfui.envs.*; import gplx.gfui.controls.elems.*; import gplx.gfui.controls.standards.*; import gplx.gfui.controls.customs.*;
import gplx.core.envs.*;
import gplx.gfml.*; import gplx.langs.gfs.*;
public class GfoConsoleWin implements Gfo_invk, UsrMsgWkr {
	GfuiWin win; GfoConsoleWinCmds cmds; GfuiTextBox statusBox, resultBox; GfuiTextBoxLogger logger;
	public boolean Enabled() {return enabled;} public GfoConsoleWin Enabled_(boolean v) {enabled = v; return this;} private boolean enabled = true;
	public GfuiWin Win() {return win;}
	void Init() {
		win = GfuiWin_.tool_("gfoConsoleWin");
		win.Size_(700, 600);
		PointAdp point = PointAdp_.new_(ScreenAdp_.Primary.Width() - win.Width(), ScreenAdp_.Primary.Height() - win.Height());
		win.Pos_(point).Focus_default_("consoleBox"); win.QuitMode_(GfuiQuitMode.Suspend);
		GfuiTextBox_.fld_("consoleFilBox", win);
		GfuiTextBox consoleBox = GfuiTextBox_.multi_("consoleBox", win);
		consoleBox.TextMgr().Font_(FontAdp.new_("Courier New", 8, FontStyleAdp_.Plain));
		consoleBox.OverrideTabKey_(false);
		resultBox = GfuiTextBox_.multi_("resultBox", win);
		resultBox .OverrideTabKey_(false);
		resultBox.TextMgr().Font_(FontAdp.new_("Courier New", 8, FontStyleAdp_.Plain));
		statusBox = GfuiTextBox_.multi_("statusBox", win);
		statusBox.OverrideTabKey_(false);
		statusBox.TextMgr().Font_(FontAdp.new_("Courier New", 8, FontStyleAdp_.Plain));
		win.Inject_(GfuiStatusBarBnd.new_());
		cmds = new GfoConsoleWinCmds(this);
		cmds.Owner_set(win); cmds.Init();
		IptBnd_.cmd_to_(IptCfg_.Null, win, cmds, GfoConsoleWinCmds.Invk_Hide, IptKey_.Escape);
		IptBnd_.cmd_to_(IptCfg_.Null, consoleBox, cmds, GfoConsoleWinCmds.Invk_Exec, IptKey_.add_(IptKey_.Ctrl, IptKey_.E));
		IptBnd_.cmd_to_(IptCfg_.Null, consoleBox, cmds, GfoConsoleWinCmds.Invk_Save, IptKey_.add_(IptKey_.Ctrl, IptKey_.S));
		IptBnd_.cmd_to_(IptCfg_.Null, consoleBox, cmds, GfoConsoleWinCmds.Invk_Load, IptKey_.add_(IptKey_.Ctrl, IptKey_.L));			
		IptBnd_.cmd_to_(IptCfg_.Null, consoleBox, cmds, GfoConsoleWinCmds.Invk_Help, IptKey_.add_(IptKey_.Ctrl, IptKey_.D));			
		IptBnd_.cmd_to_(IptCfg_.Null, consoleBox, cmds, GfoConsoleWinCmds.Invk_Clear, IptKey_.add_(IptKey_.Ctrl, IptKey_.Alt, IptKey_.C));			
		logger = new GfuiTextBoxLogger(this).Init(statusBox);
//			gplx.core.ios.GfioApp.InitGfs();
		UsrDlg_.Instance.Reg(UsrMsgWkr_.Type_Note, this);

		win.Lyt_activate();
		win.Lyt().Bands_add(GftBand.fillWidth_());
		win.Lyt().Bands_add(GftBand.fillWidth_().Len1_pct_(50));
		win.Lyt().Bands_add(GftBand.fillWidth_().Len1_abs_(20));
		win.Lyt().Bands_add(GftBand.fillWidth_().Len1_pct_(50));
		win.Lyt().Bands_add(GftBand.fillWidth_());
	}
	void Show() {
		if (win == null) this.Init();
		win.Pin_(false);
		win.Visible_set(true);
		win.Zorder_front_and_focus();
	}
	public void ExecUsrMsg(int type, UsrMsg umsg) {
		if (win == null) this.Init();
		String s = umsg.To_str();
		if		(type == UsrMsgWkr_.Type_Warn) {
			if (!win.Pin()) win.Pin_();
			s = "!!warn!! " + umsg.To_str();
			if (logger == null) return;
			logger.Write(s);
		}
		else {
			statusBox.Text_(statusBox.Text() + s);
			statusBox.SelBgn_set(String_.Len(statusBox.Text()) - 1);
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_Show))				Show();
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}	public static final    String Invk_Show	= "Show"
			;
	public static final    GfoConsoleWin Instance = new GfoConsoleWin(); GfoConsoleWin() {}
}
class GfoConsoleWinCmds implements Gfo_invk {
	GfuiWin win; GfuiTextBox consoleFilBox, consoleBox, statusBox, resultBox;
	public void Owner_set(GfuiElem elem) {win = (GfuiWin)elem;}
	public void Init() {
		consoleFilBox = (GfuiTextBox)win.SubElems().Get_by("consoleFilBox");
		consoleBox = (GfuiTextBox)win.SubElems().Get_by("consoleBox");
		resultBox = (GfuiTextBox)win.SubElems().Get_by("resultBox");
		statusBox = (GfuiTextBox)win.SubElems().Get_by("statusBox");
		GfsCore.Instance.AddObj(this, "gfoConsoleWin");
		GfsCore.Instance.ExecRegy("gplx.gfui.GfoConsoleWin.ini");
	}
	public void Results_add(String s) {
		if (!String_.Has_at_end(s, GfuiTextBox_.NewLine))
			s += GfuiTextBox_.NewLine;
		statusBox.Text_(statusBox.Text() + s);
		statusBox.SelBgn_set(String_.Len(statusBox.Text()) - 1);
	}
	void Hide() {win.Hide();}
	void Exec(GfoMsg msg) {
		String cmdText = consoleBox.SelLen() == 0 ? consoleBox.Text() : consoleBox.SelText();
		String cmd = FixNewLines(cmdText);
		GfoMsg runMsg = GfoMsg_.Null;
		try {runMsg = GfsCore.Instance.MsgParser().ParseToMsg(cmd);} catch (Exception e) {statusBox.Text_("invalid gfml " + Err_.Message_gplx_full(e)); return;}
		GfsCtx ctx = GfsCtx.new_();
		Object rv = GfsCore.Instance.ExecMany(ctx, runMsg);
		resultBox.Text_(Object_.Xto_str_strict_or_empty(rv));
	}
	void Help() {
		statusBox.Text_("");
		if (consoleBox.SelLen() == 0) return;
		String cmdText = "help:'" + consoleBox.SelText() + "';";
		String cmd = FixNewLines(cmdText);
		GfoMsg runMsg = GfoMsg_.Null;
		try {runMsg = GfmlDataNde.XtoMsgNoRoot(cmd);} catch (Exception e) {statusBox.Text_("invalid gfml " + Err_.Message_gplx_full(e)); return;}
		GfsCtx ctx = GfsCtx.new_();
		try {
		Object rv = GfsCore.Instance.ExecOne(ctx, runMsg);
		if (rv != Gfo_invk_.Rv_handled && rv != Gfo_invk_.Rv_unhandled) {
			UsrDlg_.Instance.Note(Object_.Xto_str_strict_or_empty(rv));
		}
//			Results_add(FixNewLines(ctx.Results_XtoStr()));
		} catch (Exception e) {statusBox.Text_("help failed " + Err_.Message_gplx_full(e)); return;}
	}
	void Save() {
		String consoleFilStr = consoleFilBox.Text();
		Io_url url = Io_url_.Empty;
		if (String_.Len_eq_0(consoleFilStr)) {
			url = GfuiIoDialogUtl.SelectFile();
			consoleFilBox.Text_(url.Raw());
			return;
		}
		else
			url = Io_url_.new_any_(consoleFilStr);
		Io_mgr.Instance.SaveFilStr(url, consoleBox.Text());
	}
	void Load() {
		String consoleFilStr = consoleFilBox.Text();
		Io_url dir = Io_url_.Empty;
		if (String_.Len_eq_0(consoleFilStr))
			dir = Io_url_.Empty;
		else {
			dir = Io_url_.new_any_(consoleFilStr);
			dir = dir.OwnerDir();
		}
		Io_url url = GfuiIoDialogUtl.SelectFile(dir); if (url == Io_url_.Empty) return;
		consoleBox.Text_(Io_mgr.Instance.LoadFilStr(url));
	}
	String FixNewLines(String cmd) {
		cmd = String_.Replace(cmd, "\n", "\r\n");
		cmd = String_.Replace(cmd, "\r\r", "\r");
		return cmd;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_Exec))				Exec(m);
		else if (ctx.Match(k, Invk_Hide))				Hide();
		else if (ctx.Match(k, Invk_Save))				Save();
		else if (ctx.Match(k, Invk_Load))				Load();
		else if (ctx.Match(k, Invk_Help))				Help();
		else if (ctx.Match(k, Invk_Clear))				statusBox.Text_("");
		else if (ctx.Match(k, "consoleBox"))			return consoleBox;
		else if (ctx.Match(k, Invk_X_))	{
			int v = m.ReadInt("v");
			if (ctx.Deny()) return this;
			win.X_(v);
		}
		else if (ctx.Match(k, Invk_Y_)) {
			int v = m.ReadInt("v");
			if (ctx.Deny()) return this;
			win.Y_(v);
		}
		else if (ctx.Match(k, Invk_Width_)) {
			int v = m.ReadInt("v");
			if (ctx.Deny()) return this;
			win.Width_(v);
		}
		else if (ctx.Match(k, Invk_Height_)) {
			int v = m.ReadInt("v");
			if (ctx.Deny()) return this;
			win.Height_(v);
		}
		else if (ctx.Match(k, Invk_Enabled_)) {
			boolean v = m.ReadBool("v");
			if (ctx.Deny()) return this;
			owner.Enabled_(v);
		}
		else if (ctx.Match(k, Invk_SaveUrl_)) {
			Io_url v = m.ReadIoUrl("v");
			if (ctx.Deny()) return this;
			consoleFilBox.Text_(v.Xto_api());
			consoleBox.Text_(Io_mgr.Instance.LoadFilStr(v));
		}
		else return win.Invk(ctx, ikey, k, m);
		return this;
	}	public static final    String Invk_Exec = "Exec", Invk_Hide	= "Hide", Invk_Save	= "Save", Invk_Load	= "Load", Invk_Help = "Help", Invk_Clear = "Clear"
			, Invk_X_ = "X_", Invk_Y_ = "Y_", Invk_Width_ = "Width_", Invk_Height_ = "Height_", Invk_Enabled_ = "Enabled_", Invk_SaveUrl_ = "SaveUrl_"
			;
	GfoConsoleWin owner;
	public GfoConsoleWinCmds(GfoConsoleWin owner) {this.owner = owner;}
}
class GfuiTextBoxLogger implements Gfo_invk {
	public GfuiTextBoxLogger Init(GfuiTextBox tbox) {
		this.tbox = tbox;
		win = tbox.OwnerWin();
		timer = TimerAdp.new_(this, Tmr_cmd, 500, false);
		return this;
	}
	public void Write(String s) {
		if (!owner.Enabled()) return;
		if (!win.Visible()) win.Visible_set(true);
		textToShow += tbox.Text() + String_.as_(s)+ String_.CrLf;
		long curTick = System_.Ticks();
//			if (curTick - lastTick > 500) {
			WriteText(textToShow);
			textToShow = "";
			timer.Enabled_off();
//			}
//			else
//				timer.Enabled_on();
		lastTick = curTick;
	}
	void WriteText(String text) {
		if (!win.Visible()) return; // StatusForm not visible; return; otherwise .CreateControl will be called
		tbox.CreateControlIfNeeded(); // otherwise will occasionally throw: Cannot call Invoke or InvokeAsync on a control until the window handle has been created
		tbox.Invoke(Gfo_invk_cmd.New_by_val(this, Invk_WriteText_cmd, text));
	}
	void Invk_WriteText(String text) {
		tbox.Text_(text);
		tbox.SelBgn_set(String_.Len(text) - 1);
		if (!tbox.Focus_has()) tbox.Focus();
	}
	void WhenTick() {
		WriteText(textToShow);
		textToShow = "";
		timer.Enabled_off();
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Tmr_cmd))				WhenTick();
		else if	(ctx.Match(k, Invk_WriteText_cmd))		Invk_WriteText(m.ReadStr("v"));
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}
	String Invk_WriteText_cmd = "Invk_WriteText", Tmr_cmd = "Tmr";
	GfoConsoleWin owner;
	public GfuiTextBoxLogger(GfoConsoleWin owner) {this.owner = owner;}
	GfuiTextBox tbox; GfuiWin win;
	TimerAdp timer; long lastTick; String textToShow = "";
}	
