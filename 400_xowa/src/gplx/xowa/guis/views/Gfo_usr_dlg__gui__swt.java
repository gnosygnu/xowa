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
import gplx.core.lists.rings.*;
import gplx.gfui.*; import gplx.gfui.draws.*; import gplx.gfui.kits.core.*; import gplx.gfui.controls.standards.*;
public class Gfo_usr_dlg__gui__swt implements Gfo_usr_dlg__gui, Gfo_invk {
	private final    GfuiInvkCmd cmd_sync; private final    GfuiTextBox prog_box, info_box;
	private boolean show_warn, show_note;
	public Gfo_usr_dlg__gui__swt(Xoa_app app, Gfui_kit kit, GfuiTextBox prog_box, GfuiTextBox info_box, GfuiTextBox warn_box) {
		this.cmd_sync	= kit.New_cmd_sync(this);	// NOTE: cmd_sync needed else progress messages may be sent out of order
		this.prog_box = prog_box; this.info_box = info_box;
		app.Cfg().Bind_many_app(this, Cfg__show_warn, Cfg__show_note);
	}
	public void Clear() {Write(Invk_write_prog, ""); info_box.Text_(""); info_box.ForeColor_(ColorAdp_.Black); info_box.BackColor_(ColorAdp_.White); info_box.Redraw(); info_box_is_warn = false;}
	public Ring__string Prog_msgs() {return prog_msgs;} Ring__string prog_msgs = new Ring__string().Max_(128);
	public void Write_prog(String text) {Write(Invk_write_prog, text);}
	public void Write_note(String text) {if (show_note) Write(Invk_write_note, text);}
	public void Write_warn(String text) {if (show_warn) Write(Invk_write_warn, text);}
	public void Write_stop(String text) {Write(Invk_write_stop, text);}
	private void Write(String invk, String text) {
		GfoMsg m = GfoMsg_.new_cast_(invk).Add("v", text);
		Gfo_invk_.Invk_by_msg(cmd_sync, invk, m);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_write_prog))			{String v = m.ReadStr("v"); prog_box.Text_(v); prog_box.Redraw(); if (!String_.Eq(v, "")) prog_msgs.Push(v);}
		else if	(ctx.Match(k, Invk_write_note))			{Info_box_write(m.ReadStr("v"), false); info_box.Redraw();}
		else if	(ctx.Match(k, Invk_write_warn))			{Info_box_write(m.ReadStr("v"), true); info_box.ForeColor_(ColorAdp_.White); info_box.BackColor_(ColorAdp_.Red); info_box.Redraw();}
		else if	(ctx.Match(k, Cfg__show_warn))			show_warn = m.ReadYn("v");
		else if	(ctx.Match(k, Cfg__show_note))			show_note = m.ReadYn("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private void Info_box_write(String v, boolean warn) {
		if (info_box_is_warn) return;
		info_box.Text_(v);
		info_box_is_warn = warn;
	}	boolean info_box_is_warn;

	private static final String Invk_write_prog = "write_prog", Invk_write_note = "write_note", Invk_write_warn = "write_warn", Invk_write_stop = "write_stop";
	private static final String Cfg__show_warn = "xowa.app.debug.show_warn", Cfg__show_note = "xowa.app.debug.show_note";
}
