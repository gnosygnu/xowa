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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import gplx.core.brys.args.*; import gplx.core.brys.fmtrs.*; import gplx.core.envs.*;
public class Io_download_fmt {
	private final    Io_size_fmtr_arg size_fmtr_arg = new Io_size_fmtr_arg(), rate_fmtr_arg = new Io_size_fmtr_arg().Suffix_(Bry_.new_a7("ps"));
	private final    Bfr_arg__time prog_left_fmtr_arg = new Bfr_arg__time(); private final    Bfr_arg__decimal_int prog_pct_fmtr_arg = new Bfr_arg__decimal_int().Places_(2);
	private long time_checkpoint_interval = 250;
	private long time_checkpoint = 0;
	private long time_prv = 0;
	public Io_download_fmt() {
		this.src_name = prog_msg_hdr = "";	// NOTE: set to "" else prog_mgr will fail with null ref
	}
	private final    Bry_bfr prog_bfr = Bry_bfr_.New(); Bry_fmtr prog_fmtr = Bry_fmtr.new_().Fail_when_invalid_escapes_(false); // NOTE: prog_fmtr can be passed file_names with ~ which are not easy to escape; DATE:2013-02-19
	public long Time_bgn() {return time_bgn;} private long time_bgn;
	public long Time_now() {return time_now;} private long time_now;
	public long Time_dif() {return time_dif;} private long time_dif;
	public long Time_end() {return time_end;} private long time_end;
	public String Src_url() {return src_url;} private String src_url;
	public String Src_name() {return src_name;} private String src_name;
	public long Src_len() {return src_len;} private long src_len;
	public long Prog_done() {return prog_done;} private long prog_done;
	public long Prog_rate() {return prog_rate;} private long prog_rate;
	public long Prog_left() {return prog_left;} private long prog_left;
	public long Prog_pct() {return prog_pct;} private long prog_pct;
	public String Prog_msg_hdr() {return prog_msg_hdr;} private String prog_msg_hdr;
	public int Prog_num_units() {return prog_num_units;} private int prog_num_units = Io_mgr.Len_kb;
	public String Prog_num_fmt() {return prog_num_fmt;} private String prog_num_fmt = "#,##0";
	public String Prog_msg() {return prog_msg;} private String prog_msg;
	public Gfo_usr_dlg Usr_dlg() {return usr_dlg;} private Gfo_usr_dlg usr_dlg;
	public void Ctor(Gfo_usr_dlg usr_dlg) {
		this.usr_dlg = usr_dlg;
	}
	public void Download_init(String src_url, String prog_msg_hdr) {
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
		time_bgn = time_prv = System_.Ticks();
		time_checkpoint = 0;
	}
	public void Prog(int prog_read) {
		time_now = System_.Ticks();
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
		prog_msg = prog_bfr.To_str_and_clear();
		if (usr_dlg != null)
			usr_dlg.Prog_none("", "prog", prog_msg);
	}
	public void Term() {
		time_end = System_.Ticks();
//			prog_rate = (prog_done * 1000) / (time_dif);
//			prog_pct = (prog_done * 10000) / src_len;	// 100 00 to get 2 decimal places; EX: .1234  -> 1234 -> 12.34% 
//			prog_left = (1000 * (src_len - prog_done)) / prog_rate;
//			if (usr_dlg != null) usr_dlg.Prog_none(GRP_KEY, "clear", "");
	}
	public static final    Io_download_fmt Null = null;
}
