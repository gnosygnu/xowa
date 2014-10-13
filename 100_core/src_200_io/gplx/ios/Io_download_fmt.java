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
package gplx.ios; import gplx.*;
import gplx.brys.*;
public class Io_download_fmt {
	public long Time_bgn() {return time_bgn;} long time_bgn;
	public long Time_now() {return time_now;} long time_now;
	public long Time_dif() {return time_dif;} long time_dif;
	public long Time_end() {return time_end;} long time_end;
	public String Src_url() {return src_url;} private String src_url;
	public String Src_name() {return src_name;} private String src_name;
	public long Src_len() {return src_len;} long src_len;
	public long Prog_done() {return prog_done;} long prog_done;
	public long Prog_rate() {return prog_rate;} long prog_rate;
	public long Prog_left() {return prog_left;} long prog_left;
	public long Prog_pct() {return prog_pct;} long prog_pct;
	public String Prog_msg_hdr() {return prog_msg_hdr;} private String prog_msg_hdr;
	public int Prog_num_units() {return prog_num_units;} int prog_num_units = Io_mgr.Len_kb;
	public String Prog_num_fmt() {return prog_num_fmt;} private String prog_num_fmt = "#,##0";
	public String Prog_msg() {return prog_msg;} private String prog_msg;
	Io_size_fmtr_arg size_fmtr_arg = new Io_size_fmtr_arg(), rate_fmtr_arg = new Io_size_fmtr_arg().Suffix_(Bry_.new_ascii_("ps"));
	Bry_fmtr_arg_time prog_left_fmtr_arg = new Bry_fmtr_arg_time(); Bry_fmtr_arg_decimal_int prog_pct_fmtr_arg = new Bry_fmtr_arg_decimal_int().Places_(2);
	public void Ctor(Gfo_usr_dlg usr_dlg) {
		this.usr_dlg = usr_dlg;
	}	Gfo_usr_dlg usr_dlg;
	public void Init(String src_url, String prog_msg_hdr) {
		this.src_url = src_url;
		this.src_name = String_.Extract_after_bwd(src_url, "/");
		this.prog_msg_hdr = prog_msg_hdr;
	}
	public void Bgn(long src_len) {
		this.src_len = src_len;
		prog_fmtr.Fmt_(prog_msg_hdr).Keys_("src_name", "src_len").Bld_bfr_many_and_set_fmt(src_name, size_fmtr_arg.Val_(src_len));
		prog_fmtr.Keys_("prog_done", "prog_pct", "prog_rate", "prog_left");
		prog_done = 0;
		prog_pct = 0;
		prog_rate = 0;
		prog_left = 0;
		time_bgn = time_prv = Env_.TickCount();
		time_checkpoint = 0;
		size_checkpoint = size_checkpoint_interval;
	}
	long time_checkpoint_interval = 250;
	long time_checkpoint = 0;
	long time_prv = 0;
	long size_checkpoint = 0;
	long size_checkpoint_interval = 32 * Io_mgr.Len_kb;
	public void Prog(int prog_read) {
		time_now = Env_.TickCount();
		time_dif = time_now - time_bgn; if (time_dif == 0) time_dif = 1;	// avoid div by zero error below
		prog_done += prog_read;
		time_checkpoint += time_now - time_prv;
		time_prv = time_now;
		if ((time_checkpoint < time_checkpoint_interval)) return;	// NOTE: using time_checkpoint instead of size_checkpoint b/c WMF dump servers transfer in spurts (sends 5 packets, and then waits);
		time_checkpoint = 0;
		prog_rate = (prog_done * 1000) / (time_dif);
		prog_pct = (prog_done * 10000) / src_len;	// 100 00 to get 2 decimal places; EX: .1234  -> 1234 -> 12.34% 
		prog_left = (1000 * (src_len - prog_done)) / prog_rate;
		prog_fmtr.Bld_bfr_many(prog_bfr
		,	size_fmtr_arg.Val_(prog_done)
		,	prog_pct_fmtr_arg.Val_((int)prog_pct)
		,	rate_fmtr_arg.Val_(prog_rate)
		,	prog_left_fmtr_arg.Seconds_(prog_left / 1000)
		);
		prog_msg = prog_bfr.Xto_str_and_clear();
		if (usr_dlg != null)
			usr_dlg.Prog_none(GRP_KEY, "prog", prog_msg);
	}	private Bry_bfr prog_bfr = Bry_bfr.new_(); Bry_fmtr prog_fmtr = Bry_fmtr.new_().Fail_when_invalid_escapes_(false); // NOTE: prog_fmtr can be passed file_names with ~ which are not easy to escape; DATE:2013-02-19
	public void Term() {
		time_end = Env_.TickCount();
//			prog_rate = (prog_done * 1000) / (time_dif);
//			prog_pct = (prog_done * 10000) / src_len;	// 100 00 to get 2 decimal places; EX: .1234  -> 1234 -> 12.34% 
//			prog_left = (1000 * (src_len - prog_done)) / prog_rate;
//			if (usr_dlg != null) usr_dlg.Prog_none(GRP_KEY, "clear", "");
	}
	static final String GRP_KEY = "gplx.download";
}
