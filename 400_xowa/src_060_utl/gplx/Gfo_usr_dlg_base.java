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
package gplx;
public class Gfo_usr_dlg_base implements Gfo_usr_dlg {
	public Gfo_usr_dlg_ui Ui_wkr() {return ui_wkr;} public void Ui_wkr_(Gfo_usr_dlg_ui v) {ui_wkr = v;} Gfo_usr_dlg_ui ui_wkr = Gfo_usr_dlg_ui_.Null;
	public Gfo_log_wtr Log_wtr() {return log_wtr;} public void Log_wtr_(Gfo_log_wtr v) {log_wtr = v;} Gfo_log_wtr log_wtr;
	@gplx.Virtual public void Clear() {ui_wkr.Clear();}
	public boolean Canceled() {return canceled;} public void Canceled_y_() {canceled = true;} public void Canceled_n_() {canceled = false;} private boolean canceled;
	public void Cancel() {canceled = true;} public void Cancel_reset() {canceled = false;}
	public String Log_many(String grp_key, String msg_key, String fmt, Object... args)	{String rv = Bld_msg_many(grp_key, msg_key, fmt, args	); log_wtr.Log_msg_to_session(rv); return rv;}
	public String Warn_many(String grp_key, String msg_key, String fmt, Object... args)	{String rv = Bld_msg_many(grp_key, msg_key, fmt, args	); log_wtr.Log_err(rv); ui_wkr.Write_warn(rv); return rv;}
	public String Prog_many(String grp_key, String msg_key, String fmt, Object... args)	{String rv = Bld_msg_many(grp_key, msg_key, fmt, args	); ui_wkr.Write_prog(rv); return rv;}
	public String Prog_one(String grp_key, String msg_key, String fmt, Object arg)				{String rv = Bld_msg_one (grp_key, msg_key, fmt, arg	); ui_wkr.Write_prog(rv); return rv;}
	public String Prog_none(String grp_key, String msg_key, String fmt)							{String rv = Bld_msg_none(grp_key, msg_key, fmt			); ui_wkr.Write_prog(rv); return rv;}
	public String Prog_direct(String msg)														{														   ui_wkr.Write_prog(msg); return msg;}
	public String Log_direct(String msg)														{														   log_wtr.Log_msg_to_session(msg); return msg;}
	public String Note_many(String grp_key, String msg_key, String fmt, Object... args)	{String rv = Bld_msg_many(grp_key, msg_key, fmt, args	); log_wtr.Log_msg_to_session(rv); ui_wkr.Write_note(rv); return rv;}
	public String Note_none(String grp_key, String msg_key, String fmt)							{String rv = Bld_msg_none(grp_key, msg_key, fmt			); log_wtr.Log_msg_to_session(rv); ui_wkr.Write_note(rv); return rv;}
	public String Note_gui_none(String grp_key, String msg_key, String fmt)						{String rv = Bld_msg_none(grp_key, msg_key, fmt			);                                 ui_wkr.Write_note(rv); return rv;}
	public Err Fail_many(String grp_key, String msg_key, String fmt, Object... args) {
		Err rv = Err_.new_(Bld_msg_many(grp_key, msg_key, fmt, args));
		log_wtr.Log_err(Err_.Message_gplx(rv));
		return rv;
	}
	String Bld_msg_many(String grp_key, String msg_key, String fmt, Object[] args) {
		tmp_fmtr.Fmt_(fmt).Bld_bfr_many(tmp_bfr, args);
		return tmp_bfr.XtoStrAndClear();
	}	private Bry_fmtr tmp_fmtr = Bry_fmtr.tmp_(); Bry_bfr tmp_bfr = Bry_bfr.new_();
	String Bld_msg_one(String grp_key, String msg_key, String fmt, Object val) {
		tmp_fmtr.Fmt_(fmt).Bld_bfr_one(tmp_bfr, val);
		return tmp_bfr.XtoStrAndClear();
	}
	String Bld_msg_none(String grp_key, String msg_key, String fmt) {
		return fmt;
	}
	private void Ui_wkr_parse(String s) {
		if		(String_.Eq(s, "null"))			ui_wkr = Gfo_usr_dlg_ui_.Null;
		else									throw Err_.unhandled(s);
	}
	@gplx.Virtual public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_ui_wkr_))		Ui_wkr_parse(m.ReadStr("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_ui_wkr_ = "ui_wkr_";
	public static Gfo_usr_dlg_base test_() {
		if (Test == null) {
			Test = new Gfo_usr_dlg_base();
			Test.Ui_wkr_(Gfo_usr_dlg_ui_.Test);
			Test.Log_wtr_(Gfo_log_wtr_.Null);
		}
		return Test;
	}	private static Gfo_usr_dlg_base Test;
}
