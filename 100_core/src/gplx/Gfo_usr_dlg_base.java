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
package gplx;
import gplx.core.brys.fmtrs.*;
public class Gfo_usr_dlg_base implements Gfo_usr_dlg {
	private final    Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
	private final    Bry_fmtr tmp_fmtr = Bry_fmtr.New__tmp().Fail_when_invalid_escapes_(false);	// do not fail b/c msgs may contain excerpt of random text; EX:[[User:A|~A~]] DATE:2014-11-28
	public Gfo_usr_dlg_base(Gfo_usr_dlg__log log_wkr, Gfo_usr_dlg__gui gui_wkr) {this.log_wkr = log_wkr; this.gui_wkr = gui_wkr;}
	public Gfo_usr_dlg__log Log_wkr() {return log_wkr;} public void Log_wkr_(Gfo_usr_dlg__log v) {log_wkr = v;} private Gfo_usr_dlg__log log_wkr;
	public Gfo_usr_dlg__gui Gui_wkr() {return gui_wkr;} public void Gui_wkr_(Gfo_usr_dlg__gui v) {gui_wkr = v;} private Gfo_usr_dlg__gui gui_wkr;
	public boolean Canceled() {return canceled;} public void Canceled_y_() {canceled = true;} public void Canceled_n_() {canceled = false;} private boolean canceled;
	public void Cancel() {canceled = true;}
	public String Log_many(String grp_key, String msg_key, String fmt, Object... args)	{String rv = Bld_msg_many(grp_key, msg_key, fmt, args	); log_wkr.Log_to_session(rv); return rv;}
	public String Warn_many(String grp_key, String msg_key, String fmt, Object... args)	{String rv = Bld_msg_many(grp_key, msg_key, fmt, args	); log_wkr.Log_to_err(rv); gui_wkr.Write_warn(rv); return rv;}
	public String Prog_many(String grp_key, String msg_key, String fmt, Object... args)	{String rv = Bld_msg_many(grp_key, msg_key, fmt, args	); gui_wkr.Write_prog(rv); return rv;}
	public String Prog_one(String grp_key, String msg_key, String fmt, Object arg)				{String rv = Bld_msg_one (grp_key, msg_key, fmt, arg	); gui_wkr.Write_prog(rv); return rv;}
	public String Prog_none(String grp_key, String msg_key, String fmt)							{String rv = Bld_msg_none(grp_key, msg_key, fmt			); gui_wkr.Write_prog(rv); return rv;}
	public String Prog_direct(String msg)														{														   gui_wkr.Write_prog(msg); return msg;}
	public String Log_direct(String msg)														{														   log_wkr.Log_to_session(msg); return msg;}
	public String Note_many(String grp_key, String msg_key, String fmt, Object... args)	{String rv = Bld_msg_many(grp_key, msg_key, fmt, args	); log_wkr.Log_to_session(rv); gui_wkr.Write_note(rv); return rv;}
	public String Note_none(String grp_key, String msg_key, String fmt)							{String rv = Bld_msg_none(grp_key, msg_key, fmt			); log_wkr.Log_to_session(rv); gui_wkr.Write_note(rv); return rv;}
	public String Note_gui_none(String grp_key, String msg_key, String fmt)						{String rv = Bld_msg_none(grp_key, msg_key, fmt			);                                 gui_wkr.Write_note(rv); return rv;}
	public String Plog_many(String grp_key, String msg_key, String fmt, Object... args) {
		String rv = Log_many(grp_key, msg_key, fmt, args);
		return Prog_direct(rv);
	}
	public Err Fail_many(String grp_key, String msg_key, String fmt, Object... args) {
		Err rv = Err_.new_wo_type(Bld_msg_many(grp_key, msg_key, fmt, args));
		log_wkr.Log_to_err(rv.To_str__full());
		return rv;
	}
	private String Bld_msg_many(String grp_key, String msg_key, String fmt, Object[] args) {
		synchronized (tmp_fmtr) {
			try {
				tmp_fmtr.Fmt_(fmt).Bld_bfr_many(tmp_bfr, args);
				return tmp_bfr.To_str_and_clear();
			}
			catch (Exception e) {	// NOTE: can fail if fmt has ~{}; callers should proactively remove, but for now, just return fmt if fails; EX:Page_sync and en.w:Web_crawler; DATE:2016-11-17
				Err_.Noop(e);
				return fmt;
			}
		}
	}
	private String Bld_msg_one(String grp_key, String msg_key, String fmt, Object val) {
		synchronized (tmp_fmtr) {
			tmp_fmtr.Fmt_(fmt).Bld_bfr_one(tmp_bfr, val);
			return tmp_bfr.To_str_and_clear();
		}
	}
	private String Bld_msg_none(String grp_key, String msg_key, String fmt) {return fmt;}
}
