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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.omg.PortableServer.THREAD_POLICY_ID;

import gplx.core.threads.*;
public class Swt_kit implements Gfui_kit {
	private final KeyValHash ctor_args = KeyValHash.new_(); private final KeyValHash ctor_args_null = KeyValHash.new_();
	private final Hash_adp kit_args = Hash_adp_.new_(); private Swt_msg_wkr_stop msg_wkr_stop;
	private Gfo_usr_dlg gui_wtr; private String xul_runner_path = null;
	private final Bry_fmtr ask_fmtr = Bry_fmtr.new_().Fail_when_invalid_escapes_(false); private final Bry_bfr ask_bfr = Bry_bfr.new_();
	private final Object thread_lock = new Object();
	public byte 				Tid() {return Gfui_kit_.Swt_tid;}
	public String 				Key() {return "swt";}
	public Display 				Swt_display() 	{return display;} 	private Display display;
	public Shell				Swt_shell() 	{return shell;} 	private Shell shell;
	public Gfui_clipboard 		Clipboard() 	{return clipboard;} private Swt_clipboard clipboard;
	public int					Kit_mode()		 		{synchronized (thread_lock) {return mode;}} private int mode = Swt_kit_mode.Tid_ctor;
	public void					Kit_mode_(int v) 		{synchronized (thread_lock) {mode = v;}}
	public boolean				Kit_mode__ready() 		{return Kit_mode() == Swt_kit_mode.Tid_ready;}
	public boolean 				Kit_mode__term() 		{return Kit_mode() == Swt_kit_mode.Tid_term;}
	public boolean 				Kit_sync_cmd_exists() {synchronized (thread_lock) {return sync_cmd_list.Count() != 0;}} private final List_adp sync_cmd_list = List_adp_.new_();
	public void 				Kit_sync_cmd_add(Swt_gui_cmd cmd) {synchronized (thread_lock) {sync_cmd_list.Add(cmd);}}
	public void 				Kit_sync_cmd_del(Swt_gui_cmd cmd) {synchronized (thread_lock) {sync_cmd_list.Del(cmd);}}
	public GfoInvkAbleCmd 		Kit_term_cbk() {return term_cbk;} public void Kit_term_cbk_(GfoInvkAbleCmd v) {this.term_cbk = v;} private GfoInvkAbleCmd term_cbk = GfoInvkAbleCmd.Null;
	public void Kit_init(Gfo_usr_dlg gui_wtr) {
		this.gui_wtr = gui_wtr;
		this.msg_wkr_stop 	= new Swt_msg_wkr_stop(this, gui_wtr);
		this.display 		= new Display();
		this.clipboard 		= new Swt_clipboard(display);
		UsrDlg_._.Reg(UsrMsgWkr_.Type_Warn, GfoConsoleWin._);
		UsrDlg_._.Reg(UsrMsgWkr_.Type_Stop, msg_wkr_stop);
		if (xul_runner_path != null) System.setProperty("org.eclipse.swt.browser.XULRunnerPath", xul_runner_path);
		this.Kit_mode_(Swt_kit_mode.Tid_ready); 
		gui_wtr.Log_many("", "", "swt.kit.init.done");
	}
	public void Kit_run() {
	    shell.addListener(SWT.Close, new Swt_shell_close_lnr(this, gui_wtr));
		shell.open();
		Cursor cursor = new Cursor(display, SWT.CURSOR_ARROW);
		shell.setCursor(cursor);	// set cursor to hand else cursor defaults to Hourglass until mouse is moved; DATE: 2014-01-31
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		gui_wtr.Log_many("", "", "swt.kit.term:bgn");
		cursor.dispose(); 	gui_wtr.Log_many("", "", "swt.kit.term:cursor");
	}
	public void Kit_term() {
		clipboard.Rls(); 	gui_wtr.Log_many("", "", "swt.kit.term:clipboard");
		shell.close();
	}	
	public void Cfg_set(String type, String key, Object val) {
		// XulRunnerPath gets set immediately; do not add to widget_cfg_hash
		if 	(String_.Eq(type, Gfui_kit_.Cfg_HtmlBox) && String_.Eq(key, "XulRunnerPath")) {
			this.xul_runner_path = (String)val;
			return;
		}
		// add kv to widget_cfg_hash; new controls will get properties from cfg_hash
		KeyValHash widget_cfg_hash = (KeyValHash)kit_args.Get_by(type);
		if (widget_cfg_hash == null) {
			widget_cfg_hash = KeyValHash.new_();
			kit_args.Add(type, widget_cfg_hash);
		}
		widget_cfg_hash.Add_if_dupe_use_nth(key, val);
	}
	public boolean Ask_yes_no(String grp_key, String msg_key, String fmt, Object... args) {
		Swt_dlg_msg dlg = (Swt_dlg_msg)New_dlg_msg(ask_fmtr.Bld_str_many(ask_bfr, fmt, args)).Init_btns_(Gfui_dlg_msg_.Btn_yes, Gfui_dlg_msg_.Btn_no).Init_ico_(Gfui_dlg_msg_.Ico_question);
		display.syncExec(dlg);
		return dlg.Ask_rslt == Gfui_dlg_msg_.Btn_yes;
	}
	public boolean Ask_ok_cancel(String grp_key, String msg_key, String fmt, Object... args) {
		Swt_dlg_msg dlg = (Swt_dlg_msg)New_dlg_msg(ask_fmtr.Bld_str_many(ask_bfr, fmt, args)).Init_btns_(Gfui_dlg_msg_.Btn_ok, Gfui_dlg_msg_.Btn_cancel).Init_ico_(Gfui_dlg_msg_.Ico_question);
		display.syncExec(dlg);
		return dlg.Ask_rslt == Gfui_dlg_msg_.Btn_ok;
	}
	public int Ask_yes_no_cancel(String grp_key, String msg_key, String fmt, Object... args) 	{
		Swt_dlg_msg dlg = (Swt_dlg_msg)New_dlg_msg(ask_fmtr.Bld_str_many(ask_bfr, fmt, args)).Init_btns_(Gfui_dlg_msg_.Btn_yes, Gfui_dlg_msg_.Btn_no, Gfui_dlg_msg_.Btn_cancel).Init_ico_(Gfui_dlg_msg_.Ico_question);		
		display.syncExec(dlg);
		return dlg.Ask_rslt;
	}
	public void Ask_ok(String grp_key, String msg_key, String fmt, Object... args) 				{
		Swt_dlg_msg dlg = (Swt_dlg_msg)New_dlg_msg(ask_fmtr.Bld_str_many(ask_bfr, fmt, args)).Init_btns_(Gfui_dlg_msg_.Btn_ok).Init_ico_(Gfui_dlg_msg_.Ico_information);
		display.syncExec(dlg);
	}
	public GfuiInvkCmd New_cmd_sync	(GfoInvkAble invk) 	{return new Swt_gui_cmd(this, gui_wtr, display, invk, Bool_.N);}
	public GfuiInvkCmd New_cmd_async(GfoInvkAble invk) 	{return new Swt_gui_cmd(this, gui_wtr, display, invk, Bool_.Y);}
	public GfuiWin New_win_utl(String key, GfuiWin owner, KeyVal... args) {
		return GfuiWin_.kit_(this, key, new Swt_win(shell), ctor_args_null);
		}
	public GfuiWin New_win_app(String key, KeyVal... args) {
		Swt_win win = new Swt_win(display);
		this.shell = win.UnderShell();
		shell.setLayout(null);
		return GfuiWin_.kit_(this, key, win, ctor_args_null);
	}
	public GfuiBtn New_btn(String key, GfuiElem owner, KeyVal... args) {
		GfuiBtn rv = GfuiBtn_.kit_(this, key, new Swt_btn_no_border(Swt_control_.cast_or_fail(owner), ctor_args), ctor_args);
		owner.SubElems().Add(rv);
		return rv;
	}
	public GfuiLbl New_lbl(String key, GfuiElem owner, KeyVal... args) {
		GfuiLbl rv = GfuiLbl_.kit_(this, key, new Swt_lbl(Swt_control_.cast_or_fail(owner), ctor_args), ctor_args);
		owner.SubElems().Add(rv);
		return rv;
	}
	public Gfui_html New_html(String key, GfuiElem owner, KeyVal... args) {
		ctor_args.Clear();
		// check cfg for browser type
		KeyValHash html_cfg_args = (KeyValHash)kit_args.Get_by(Gfui_kit_.Cfg_HtmlBox);
		if (html_cfg_args != null) {
			KeyVal browser_type = html_cfg_args.FetchOrNull(Cfg_Html_BrowserType);
			if (browser_type != null) ctor_args.Add(browser_type);
		}
		Swt_html swt_html = new Swt_html(this, Swt_control_.cast_or_fail(owner), ctor_args);
		Gfui_html gfui_html = Gfui_html.kit_(this, key, swt_html, ctor_args);
		swt_html.Under_control().addMenuDetectListener(new Swt_lnr__menu_detect(this, gfui_html));
		gfui_html.Owner_(owner);
		swt_html.Delete_elems_(owner, gfui_html);
		return gfui_html;
	}
	public Gfui_tab_mgr New_tab_mgr(String key, GfuiElem owner, KeyVal... args) {
		ctor_args.Clear();
		Swt_tab_mgr rv_swt = new Swt_tab_mgr(this, Swt_control_.cast_or_fail(owner), ctor_args);
		Gfui_tab_mgr rv = Gfui_tab_mgr.kit_(this, key, rv_swt, ctor_args);
		rv.Owner_(owner);
		rv_swt.EvMgr_(rv.EvMgr());
		return rv;
	}
	public GfuiTextBox New_text_box(String key, GfuiElem owner, KeyVal... args) {
		ctor_args.Clear();
		int args_len = args.length;
		for (int i = 0; i < args_len; i++)
			ctor_args.Add(args[i]);
		boolean border_on = Bool_.cast_(ctor_args.FetchValOr(GfuiTextBox.CFG_border_on_, true));
		GxwTextFld under = new Swt_text_w_border(Swt_control_.cast_or_fail(owner), New_color(border_on ? ColorAdp_.LightGray : ColorAdp_.White), ctor_args);
		GfuiTextBox rv = GfuiTextBox_.kit_(this, key, under, ctor_args);
		rv.Owner_(owner);
		ctor_args.Clear();
		return rv;
	}
	public GfuiStatusBox New_status_box(String key, GfuiElem owner, KeyVal... args) {
		ctor_args.Clear();
		GfuiStatusBox rv = GfuiStatusBox_.kit_(this, key, new Swt_text(Swt_control_.cast_or_fail(owner), ctor_args));
		rv.Owner_(owner);
		return rv;
	}
	public Gfui_dlg_file New_dlg_file(byte type, String msg) {return new Swt_dlg_file(type, shell).Init_msg_(msg);}
	public Gfui_dlg_msg New_dlg_msg(String msg) {return new Swt_dlg_msg(shell).Init_msg_(msg);}
	public ImageAdp New_img_load(Io_url url) {
		if (url == Io_url_.Empty) return ImageAdp_.Null;
		Image img = new Image(display, url.Raw());
		Rectangle rect = img.getBounds();
		return new Swt_img(this, img, rect.width, rect.height).Url_(url);
	}
	public Color New_color(ColorAdp v) {return (Color)New_color(v.Alpha(), v.Red(), v.Green(), v.Blue());}
	public Object New_color(int a, int r, int g, int b) {return new Color(display, r, g, b);}
	public Gfui_mnu_grp New_mnu_popup(String key, GfuiElem owner) 	{return Swt_popup_grp.new_popup(key, owner);}
	public Gfui_mnu_grp New_mnu_bar(String key, GfuiWin owner) 		{return Swt_popup_grp.new_bar(key, owner);}
	public float Calc_font_height(GfuiElem elem, String s) {
		if (String_.Len_eq_0(s)) return 8;
		try {
			String old_text = elem.Text();
			elem.Text_(s);
			float rv = ((Swt_text_w_border)(elem.UnderElem())).Under_text().getFont().getFontData()[0].height;
			elem.Text_(old_text);	// was shell.setText(old_text); DATE:2014-07-25
			return rv;
		}
		catch (Exception e) {
			Gfo_usr_dlg_.I.Warn_many("", "", "error while calculating font height; err=~{0}", Err_.Message_gplx_full(e));
			return 8;
		}
	}
	public void Set_mnu_popup(GfuiElem owner, Gfui_mnu_grp grp) {
		Control control = Swt_control_.cast_or_fail(owner).Under_menu_control();
		Swt_popup_grp popup = (Swt_popup_grp)grp;
		control.setMenu(popup.Under_menu());
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(String_.Eq(k, Invk_Cfg_add)) {
			String type = m.ReadStrOr("type", "");
			String key = m.ReadStrOr("key", "");
			String val = m.ReadStrOr("val", "");
			if (ctx.Deny()) return this;			
			if (String_.Eq(type, Gfui_kit_.Cfg_HtmlBox)) {
				if 		(String_.Eq(key, "XulRunnerPath"))
					xul_runner_path = val;
				else if (String_.Eq(key, Swt_kit.Cfg_Html_BrowserType))
					Cfg_set(type, Swt_kit.Cfg_Html_BrowserType, Cfg_Html_BrowserType_parse(val));
			}
		}
		else if	(String_.Eq(k, Invk_ask_file)) return this.New_dlg_file(Gfui_kit_.File_dlg_type_open, m.Args_getAt(0).Val_to_str_or_empty()).Ask();
		else if (String_.Eq(k, Invk_shell_close)) shell.close();
		return this;
	}
	public static final String Invk_Cfg_add = "Cfg_add", Invk_ask_file = "ask_file";	// private or public?
	public static final String Invk_shell_close = "shell_close";	// public
	public static final Swt_kit _ = new Swt_kit(); private Swt_kit() {}	// singleton b/c of following line "In particular, some platforms which SWT supports will not allow more than one active display" (http://help.eclipse.org/indigo/topic/org.eclipse.platform.doc.isv/reference/api/org/eclipse/swt/widgets/Display.html)
	public static final String Cfg_Html_BrowserType = "BrowserType";
	public static int Cfg_Html_BrowserType_parse(String v) {
		if		(String_.Eq(v, "mozilla"))	return Swt_html.Browser_tid_mozilla;
		else if	(String_.Eq(v, "webkit"))	return Swt_html.Browser_tid_webkit;
		else								return Swt_html.Browser_tid_none;
	}
	public static FontAdp Control_font_get(Font font, GxwCore_base owner) {
		FontData fontData = font.getFontData()[0];
		FontAdp rv = FontAdp.new_(fontData.getName(), fontData.getHeight(), FontStyleAdp_.lang_(fontData.getStyle()));	// NOTE: swt style constants match swing
		rv.OwnerGxwCore_(owner);
		return rv;
	}
	public static void Control_font_set(FontAdp font, GxwCore_base owner, Control control) {
		font.OwnerGxwCore_(owner);
		FontData fontData = new FontData(font.Name(), (int)font.size, font.Style().Val());
		Font rv = new Font(control.getDisplay(), fontData);
		control.setFont(rv);
	}
}
class Swt_shell_close_lnr implements Listener, GfoInvkAble {
	private final Swt_kit kit; private final Gfo_usr_dlg usr_dlg;
	public Swt_shell_close_lnr(Swt_kit kit, Gfo_usr_dlg usr_dlg) {this.kit = kit; this.usr_dlg = usr_dlg;}
	@Override public void handleEvent(Event event) {
		if (kit.Kit_mode__term()) return;							// NOTE: will be term if called again from wait_for_sync_cmd
		kit.Kit_mode_(Swt_kit_mode.Tid_term);						// NOTE: must mark kit as shutting down, else writing to status_bar will create stack overflow; DATE:2014-05-05
		boolean rslt = Bool_.cast_(kit.Kit_term_cbk().Invk());		// call bgn term
		if (!rslt) {
			event.doit = false;										// cbk canceled term; stop close
			kit.Kit_mode_(Swt_kit_mode.Tid_ready);					// reset kit back to "running" mode;
			return;
		}
		if (kit.Kit_sync_cmd_exists()) {							// sync cmd is running; cannot shut down app else app just hangs; DATE:2015-04-13
			event.doit = false;										// cancel shutdown
			Thread_adp_.invk_(this, Invk_wait_for_sync_cmd).Start();	// wait for sync_cmd to end in background thread; call shutdown again when it does
		}
	}
	private void Wait_for_sync_cmd() {	// THREAD:non-GUI
		int loop_count = 0, loop_max = 50, loop_wait = 100; // loop for 100 ms for no more than 5 seconds
		while (loop_count < loop_max) {
			usr_dlg.Log_many("", "", "swt:waiting for sync cmd; loop=~{0}", loop_count);
			if (!kit.Kit_sync_cmd_exists()) {
				usr_dlg.Log_many("", "", "swt:sync cmd done; shutting down");
				break;
			}
			Thread_adp_.Sleep(loop_wait);
			loop_count++;
		}
		if (loop_count == loop_max)
			usr_dlg.Log_many("", "", "swt:sync_wait failed", loop_count);
		GfoInvkAble_.InvkCmd(kit.New_cmd_sync(kit), Swt_kit.Invk_shell_close);	// shutdown again; note that cmd must be called on GUI thread		
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_wait_for_sync_cmd)) 	Wait_for_sync_cmd();
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_wait_for_sync_cmd = "wait_for_sync_cmd";
}
class Swt_kit_mode {
	public static final int
	  Tid_ctor 			= 0
	, Tid_ready 		= 1
	, Tid_term 			= 2
	;
}
class Swt_gui_cmd implements GfuiInvkCmd, Runnable {
	private final Swt_kit kit; private final Gfo_usr_dlg usr_dlg; private final Display display; private final GfoInvkAble target; private final boolean async;	
	private GfsCtx invk_ctx; private int invk_ikey; private String invk_key; private GfoMsg invk_msg;
	public Swt_gui_cmd(Swt_kit kit, Gfo_usr_dlg usr_dlg, Display display, GfoInvkAble target, boolean async) {
		this.kit = kit; this.usr_dlg = usr_dlg; this.display = display; this.target = target; this.async = async;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		this.invk_ctx = ctx; this.invk_ikey = ikey; this.invk_key = k; this.invk_msg = m;
		if (async)
			display.asyncExec(this);
		else {
			kit.Kit_sync_cmd_add(this);
			try 	{display.syncExec(this);}
			finally {kit.Kit_sync_cmd_del(this);}
		}
		return this;
	}	
	@Override public void run() {
		synchronized (this) {// needed for Special:Search and async; DATE:2015-04-23
			try {target.Invk(invk_ctx, invk_ikey, invk_key, invk_msg);}
			catch (Exception e) {
				if (kit.Kit_mode__term()) return;	// NOTE: if shutting down, don't warn; warn will try to write to status.bar, which will fail b/c SWT is shutting down; failures will try to write to status.bar again, causing StackOverflow exception; DATE:2014-05-04
				usr_dlg.Warn_many("", "", "fatal error while running; key=~{0} err=~{1}", invk_key, Err_.Message_gplx_full(e));
			}
		}
	} 
	public void Rls() {
		this.invk_ctx = null; this.invk_key = null; this.invk_msg = null;
	}
}
class Swt_msg_wkr_stop implements UsrMsgWkr {
	private final Swt_kit kit; private final Gfo_usr_dlg gui_wtr;
	public Swt_msg_wkr_stop(Swt_kit kit, Gfo_usr_dlg gui_wtr) {this.kit = kit; this.gui_wtr = gui_wtr;}
	public void ExecUsrMsg(int type, UsrMsg umsg) {
		String msg = umsg.To_str(); 
		kit.Ask_ok("xowa.gui", "stop", msg);
		gui_wtr.Log_many("", "", msg);
	}
}
