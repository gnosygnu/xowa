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
import gplx.core.strings.*;
public class Gfo_usr_dlg__log_base implements Gfo_usr_dlg__log {
	private int archive_dirs_max = 8;
	private Io_url log_dir, err_fil;
	private Ordered_hash queued_list = Ordered_hash_.new_();
	private Bry_fmtr fmtr = Bry_fmtr.tmp_(); private Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
	public boolean Queue_enabled() {return queue_enabled;} public void Queue_enabled_(boolean v) {queue_enabled = v; if (!v) this.Flush();} private boolean queue_enabled;
	public boolean Enabled() {return enabled;} public void Enabled_(boolean v) {enabled = v;} private boolean enabled = true;
	public Io_url Session_dir() {return session_dir;} private Io_url session_dir;
	public Io_url Session_fil() {return session_fil;} private Io_url session_fil;
	private void Flush() {
		int queued_len = queued_list.Count();
		for (int i = 0; i < queued_len; i++) {
			Usr_log_fil fil = (Usr_log_fil)queued_list.Get_at(i);
			if (fil.Url() == null) {
				fil.Url_(session_dir.GenSubFil("session.txt"));
			}
			fil.Flush();
		}
	}
	public Io_url Log_dir() {return log_dir;}
	public void Log_dir_(Io_url log_dir) {
		this.log_dir = log_dir;
		session_dir = log_dir.GenSubDir(Dir_name_current);
		session_fil = session_dir.GenSubFil("session.txt");
		err_fil = session_dir.GenSubFil("err.txt");
	}
	public void Log_term() {
		if (!enabled) return;
		Io_url[] archive_dirs = Io_mgr.I.QueryDir_args(log_dir).DirInclude_().DirOnly_().ExecAsUrlAry();
		int archive_dirs_len = archive_dirs.length;
		int session_cutoff = archive_dirs_len - archive_dirs_max;
		for (int i = 0; i < session_cutoff; i++) {
			Io_url archive_dir = archive_dirs[i];
			Io_mgr.I.DeleteDirDeep(archive_dir);
			this.Log_to_session("archive dir del: " + session_dir.Raw());
		}
		this.Log_to_session("app term");
		MoveCurrentToArchive(session_dir);
	}
	private void MoveCurrentToArchive(Io_url dir) {Io_mgr.I.MoveDirDeep(dir, dir.OwnerDir().GenSubDir(DateAdp_.Now().XtoStr_fmt_yyyyMMdd_HHmmss_fff()));}
	public void Log_info(boolean warn, String s) {if (warn) Log_to_err(s); else Log_to_session(s);}
	public void Log_msg_to_url_fmt(Io_url url, String fmt, Object... args) {
		if (!enabled) return;
		String msg = Bld_msg(String_.new_u8(fmtr.Fmt_(fmt).Bld_bry_many(tmp_bfr, args)));
		Log_msg(url, msg);
		Log_msg(session_fil, msg);
	}
	public void Log_to_session_fmt(String fmt, Object... args) {Log_to_session(String_.new_u8(fmtr.Fmt_(fmt).Bld_bry_many(tmp_bfr, args)));}
	public void Log_to_session(String s) {
		if (!enabled) return;
		String line = Bld_msg(s);
		Log_msg(session_fil, line);
	}
	public void Log_to_session_direct(String s) {
		if (!enabled) return;
		Log_msg(session_fil, s);
	}
	public void Log_to_err(String s) {
		if (!enabled) return;
		try {
			String line = Bld_msg(s);
			Log_msg(session_fil, line);
			Log_msg(err_fil, line);
		} 
		catch (Exception e) {Exc_.Noop(e);}			// java.lang.StringBuilder can throw exceptions in some situations when called on a different thread; ignore errors
	}	private String_bldr sb = String_bldr_.new_thread();	// NOTE: use java.lang.StringBuffer to try to avoid random exceptions when called on a different thread
	private String Bld_msg(String s) {return sb.Add(DateAdp_.Now().XtoUtc().XtoStr_fmt_yyyyMMdd_HHmmss_fff()).Add(" ").Add(s).Add_char_nl().Xto_str_and_clear();}
	private void Log_msg(Io_url url, String txt) {
		if (queue_enabled) {
			String url_raw = url == null ? "mem" : url.Raw();
			Usr_log_fil fil = (Usr_log_fil)queued_list.Get_by(url_raw);
			if (fil == null) {
				fil = new Usr_log_fil(url);
				queued_list.Add(url_raw, fil);
			}
			fil.Add(txt);
		}
		else
			Io_mgr.I.AppendFilStr(url, txt);
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_enabled_))				enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_archive_dirs_max_))		archive_dirs_max = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_log_dir_))				log_dir = m.ReadIoUrl("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	public static final String Invk_enabled_ = "enabled_", Invk_archive_dirs_max_ = "archive_dirs_max_", Invk_log_dir_ = "log_dir_";
	static final String Dir_name_log = "log", Dir_name_current = "current";
	public static final Gfo_usr_dlg__log_base _ = new Gfo_usr_dlg__log_base();
}
class Usr_log_fil {
	public Usr_log_fil(Io_url url) {this.url = url;}
	public Io_url Url() {return url;} public Usr_log_fil Url_(Io_url v) {url = v; return this;} Io_url url;
	public void Add(String text) {sb.Add(text);} String_bldr sb = String_bldr_.new_();
	public void Flush() {
		if (sb.Count() == 0) return;
		try {
			Io_mgr.I.AppendFilStr(url, sb.Xto_str_and_clear());
		}
		catch (Exception e) {
			ConsoleAdp._.WriteLine(Err_.Message_gplx_brief(e));
		}
	}
}
