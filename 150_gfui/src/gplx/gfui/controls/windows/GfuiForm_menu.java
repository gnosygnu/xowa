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
import gplx.gfui.ipts.*; import gplx.gfui.controls.elems.*;
public class GfuiForm_menu implements Gfo_invk {
	public GfuiWin Form() {return form;} GfuiWin form;
	void Visible_toggle(GfoMsg msg) {
		GfuiElem ownerForm = owner.OwnerWin();
		sub.OwnerWin().Zorder_front();
		form.Visible_set(!form.Visible());
		if (form.Visible()) {
			PointAdp pos = ownerForm.Pos(); // NOTE: add ownerForm to handle different screens (ex: outerElem.OwnerWin.X = 1600 with dual screens))
			pos = pos.Op_add(owner.Rect().Pos());
			RectAdp rect = RectAdp_.vector_(pos, owner.Rect().Size());
			form.Pos_(GfuiMenuFormUtl.CalcShowPos(rect, form.Size()));
			form.Focus();	// force refocus; last choice is lost, but otherwise focus remains on owner box
		}
	}
	void Visible_hide(GfoMsg msg) {
		form.Visible_set(false);
	}
	GfuiElem sub; GfuiElem owner;
	void ctor_GfuiForm_menu(GfuiElem owner_, GfuiElem sub_, SizeAdp size) {
		owner = owner_; sub = sub_;
		sub.Size_(size);
		form = GfuiWin_.sub_("test", owner.OwnerWin());

		form.IptBnds().EventsToFwd_set(IptEventType_.None);
		form.TaskbarVisible_(false);
		form.Size_(sub.Size());
		sub.Owner_(form);
//			form.CmdsA().Del(GfuiWin.Invk_Minimize);
//			form.CmdsA().Del(GfuiStatusBoxBnd.Invk_ShowTime);
		IptBnd_.cmd_to_(IptCfg_.Null, form, this, Visible_hide_cmd, IptKey_.Escape);
		IptBnd_.cmd_to_(IptCfg_.Null, owner, this, Visible_toggle_cmd, IptKey_.add_(IptKey_.Ctrl, IptKey_.Space), IptMouseBtn_.Right);

		form.TaskbarParkingWindowFix(owner.OwnerWin());	// else ContextMenu shows up as WindowsFormsParkingWindow
		form.QuitMode_(GfuiQuitMode.Suspend);
	}
	public static final    String Msg_menu_Visible_toggle = "menu.visible_toggle";


	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Visible_hide_cmd))			Visible_hide(m);
		else if	(ctx.Match(k, Visible_toggle_cmd))			Visible_toggle(m);
		else return Gfo_invk_.Rv_unhandled;
		return this;
	}	public static final    String Visible_hide_cmd = "Visible_hide", Visible_toggle_cmd = "Visible_toggle";
	public static GfuiWin new_(GfuiElem owner, GfuiElem sub, SizeAdp size) {
		GfuiForm_menu rv = new GfuiForm_menu();
		rv.ctor_GfuiForm_menu(owner, sub, size);
		return rv.Form();
	}
	public static GfuiWin container3_(GfuiWin mainForm) {
		GfuiWin form = GfuiWin_.sub_("test", mainForm);
		form.TaskbarVisible_(false);
		form.TaskbarParkingWindowFix(mainForm);	// else ContextMenu shows up as WindowsFormsParkingWindow
		form.QuitMode_(GfuiQuitMode.Suspend);
		return form;
	}
}
