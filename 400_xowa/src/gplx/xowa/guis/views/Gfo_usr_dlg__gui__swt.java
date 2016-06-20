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
package gplx.xowa.guis.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
import gplx.core.lists.rings.*;
import gplx.gfui.*; import gplx.gfui.draws.*; import gplx.gfui.kits.core.*; import gplx.gfui.controls.standards.*;
public class Gfo_usr_dlg__gui__swt implements Gfo_usr_dlg__gui, Gfo_invk {
	private final    GfuiInvkCmd cmd_sync; private final    GfuiTextBox prog_box, info_box; private final    Gfo_usr_dlg__gui__opt opt;
	public Gfo_usr_dlg__gui__swt(Gfui_kit kit, GfuiTextBox prog_box, GfuiTextBox info_box, GfuiTextBox warn_box, Gfo_usr_dlg__gui__opt opt) {
		this.cmd_sync	= kit.New_cmd_sync(this);	// NOTE: cmd_sync needed else progress messages may be sent out of order
		this.prog_box = prog_box; this.info_box = info_box;
		this.opt = opt;
	}
	public void Clear() {Write(Invk_write_prog, ""); info_box.Text_(""); info_box.ForeColor_(ColorAdp_.Black); info_box.BackColor_(ColorAdp_.White); info_box.Redraw(); info_box_is_warn = false;}
	public Ring__string Prog_msgs() {return prog_msgs;} Ring__string prog_msgs = new Ring__string().Max_(128);
	public void Write_prog(String text) {Write(Invk_write_prog, text);}
	public void Write_note(String text) {if (opt.Note_enabled()) Write(Invk_write_note, text);}
	public void Write_warn(String text) {if (opt.Warn_enabled()) Write(Invk_write_warn, text);}
	public void Write_stop(String text) {Write(Invk_write_stop, text);}
	private void Write(String invk, String text) {
		GfoMsg m = GfoMsg_.new_cast_(invk).Add("v", text);
		Gfo_invk_.Invk_by_msg(cmd_sync, invk, m);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_write_prog))			{String v = m.ReadStr("v"); prog_box.Text_(v); prog_box.Redraw(); if (!String_.Eq(v, "")) prog_msgs.Push(v);}
		else if	(ctx.Match(k, Invk_write_note))			{Info_box_write(m.ReadStr("v"), false); info_box.Redraw();}
		else if	(ctx.Match(k, Invk_write_warn))			{Info_box_write(m.ReadStr("v"), true); info_box.ForeColor_(ColorAdp_.White); info_box.BackColor_(ColorAdp_.Red); info_box.Redraw();}
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}
	private void Info_box_write(String v, boolean warn) {
		if (info_box_is_warn) return;
		info_box.Text_(v);
		info_box_is_warn = warn;
	}	boolean info_box_is_warn;
	private static final String Invk_write_prog = "write_prog", Invk_write_note = "write_note", Invk_write_warn = "write_warn", Invk_write_stop = "write_stop";
}
