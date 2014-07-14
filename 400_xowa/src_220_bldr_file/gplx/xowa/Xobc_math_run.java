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
package gplx.xowa; import gplx.*;
import gplx.core.flds.*; import gplx.ios.*; import gplx.xowa.bldrs.*; import gplx.xowa.xtns.math.*;
public class Xobc_math_run extends Xob_itm_basic_base implements Xob_cmd, GfoInvkAble {
	public Xobc_math_run(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return KEY;} public static final String KEY = "math.run";
	Xof_file_mgr file_mgr;
	public Io_url Rdr_dir() {return rdr_dir;} Io_url rdr_dir;
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		this.bldr = bldr; fld_rdr = Gfo_fld_rdr.xowa_();
		lnk_parser = (Gfo_fld_rdr)Gfo_fld_rdr.xowa_().Row_dlm_(Byte_ascii.Pipe).Fld_dlm_(Byte_ascii.Comma);
		tmp_dir = wiki.Fsys_mgr().Tmp_dir().GenSubDir(KEY);
		dump_bfr = Bry_bfr.new_(dump_fil_len); fld_wtr = Gfo_fld_wtr.xowa_().Bfr_(dump_bfr); 
		file_mgr = bldr.App().File_mgr(); math_mgr = file_mgr.Math_mgr(); math_mgr.Init(bldr.App());
		if (rdr_dir == null) rdr_dir = wiki.Fsys_mgr().Tmp_dir().GenSubDir_nest(Xobc_xnde_math_dump.KEY, "make");
		Io_url errors_dir = tmp_dir.GenSubDir("errors");
		dump_url_gen = Io_url_gen_.dir_(errors_dir);
		Io_mgr._.DeleteDirDeep_ary(errors_dir);
	}	Io_url tmp_dir; Bry_bfr url_bfr = Bry_bfr.new_(); private Xof_math_mgr math_mgr;
	public void Cmd_run() {
		Io_line_rdr rdr = new Io_line_rdr(bldr.Usr_dlg(), Io_mgr._.QueryDir_fils(rdr_dir));
		int count = 0;
		int url_idx = rdr.Url_idx();
		Io_url dump_url = dump_url_gen.Nxt_url();	// gen one url
		long time_bgn = Env_.TickCount();
		byte[] math = Bry_.Empty;
		Xof_math_itm math_itm = new Xof_math_itm();
		while (rdr.Read_next()) {
			try {
				if (url_idx != rdr.Url_idx()) {
					this.Flush(rdr, dump_url, url_idx, time_bgn, count);
					url_idx = rdr.Url_idx();
					count = 0;
					time_bgn = Env_.TickCount();
				}
				fld_rdr.Ini(rdr.Bfr(), rdr.Itm_pos_bgn());
				math = fld_rdr.Read_bry_escape();
				math_mgr.Make_itm(math_itm, wiki.Domain_str(), math);
				boolean pass = math_mgr.MakePng(math_itm.Math(), math_itm.Hash(), math_itm.Png_url(), "");	// NOTE: no progress needed for batch
				if (pass)
					bldr.Usr_dlg().Note_many(GRP_KEY, "run.pass", "pass|~{0}|~{1}", Int_.XtoStr_PadBgn_space(count++, 7), String_.new_utf8_(math_itm.Math()));
				else {
					bldr.Usr_dlg().Note_many(GRP_KEY, "run.fail", "fail|~{0}|~{1}", Int_.XtoStr_PadBgn_space(count++, 7), String_.new_utf8_(math_itm.Math()));
					Write_err(math);
				}
				math = Bry_.Empty;
			}
			catch (Exception exc) {
				bldr.Usr_dlg().Warn_many(GRP_KEY, "fail", "error: ~{0}", Err_.Message_lang(exc));
				Write_err(math);
			}
		}
		this.Flush(rdr, dump_url, url_idx, time_bgn, count);
	}	long files_sum = 0, elapsed_sum = 0; 
	private void Write_err(byte[] math) {
		if (dump_bfr.Len() > dump_fil_len) Io_mgr._.AppendFilBfr(dump_url_gen.Nxt_url(), dump_bfr);
		fld_wtr.Write_bry_escape_row(math);
	}
	private void Flush(Io_line_rdr rdr, Io_url dump_url, int url_idx, long time_bgn, int fil_count) {
		Io_mgr._.AppendFilBfr(dump_url, dump_bfr);
		Io_url cur = rdr.Urls()[url_idx];
		int seconds = Env_.TickCount_elapsed_in_sec(time_bgn);
		double rate = Math_.Div_safe_as_double(fil_count * 3600, seconds);
		files_sum += fil_count;
		elapsed_sum += seconds;
		String txt = bldr.Usr_dlg().Note_many(GRP_KEY, "flush", "name=~{0} files=~{1} seconds=~{2} files/hour=~{3} files_total=~{4} seconds_total=~{5}", cur.NameOnly(), fil_count, seconds, rate, files_sum, elapsed_sum);
		Io_mgr._.AppendFilStr(tmp_dir.GenSubFil("progress.txt"), txt + "\n");
	}
	public void Cmd_end() {}
	public void Cmd_print() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_rdr_dir_))			{rdr_dir = Io_url_.new_dir_(m.ReadStr("v"));}
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}	private static final String Invk_rdr_dir_ = "rdr_dir_";
	Bry_bfr dump_bfr = Bry_bfr.new_(); Gfo_fld_wtr fld_wtr; Gfo_fld_rdr fld_rdr, lnk_parser; Bry_bfr tmp = Bry_bfr.new_();
	Io_url_gen dump_url_gen; int dump_fil_len = 100 * Io_mgr.Len_kb;
	static final String GRP_KEY = "xowa.bldr.math";
}
