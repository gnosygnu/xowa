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
import gplx.core.flds.*; import gplx.ios.*; import gplx.gfui.*; import gplx.xowa.bldrs.*; import gplx.xowa.files.*;
public class Xobc_img_run_xfer extends Xob_itm_basic_base implements Xob_cmd, GfoInvkAble {
	public Xobc_img_run_xfer(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Cmd_key() {return KEY;} public static final String KEY = "img.run_xfer";
	public Io_url Rdr_dir() {return rdr_dir;} Io_url rdr_dir;
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		this.bldr = bldr; fld_wtr = Gfo_fld_wtr.xowa_(); fld_rdr = Gfo_fld_rdr.xowa_();
		tmp_dir = wiki.Fsys_mgr().Tmp_dir().GenSubDir(KEY);
		if (rdr_dir == null) rdr_dir = wiki.Fsys_mgr().Tmp_dir().GenSubDir_nest(Xobc_img_prep_xfer.KEY, "dump");
		Io_url errors_dir = tmp_dir.GenSubDir("errors");
//			Io_mgr._.DeleteDirDeep_ary(errors_dir);
		dump_url_gen = Io_url_gen_.dir_(errors_dir);
		dump_bfr = Bry_bfr.new_(dump_fil_len);
		file_mgr = bldr.App().File_mgr();
		wiki.App().File_mgr().Download_mgr().Enabled_(true);
		wiki.File_mgr().Cfg_download().Enabled_(true);
		// BLOCK: init ext_stats
		int ext_stats_len = Xof_ext_.Id_ogv + 1;
		ext_stats = new Xof_file_stat[ext_stats_len];
		for (int i = 0; i < ext_stats_len; i++)
			ext_stats[i] = new Xof_file_stat(i);
	}	Io_url tmp_dir; Bry_bfr url_bfr = Bry_bfr.new_(); Xof_file_stat[] ext_stats; int fail_count; Xof_file_mgr file_mgr;
	byte[] Redirect(Xofo_file row) {return row.Redirect_exists() ? row.Name() : Bry_.Empty;}	// handle redirect rows which are reversed (Redirect|Name)
	public Xof_xfer_itm Xfer_itm() {return xfer_itm;} private Xof_xfer_itm xfer_itm = new Xof_xfer_itm();
	public void Cmd_run() {
		Io_line_rdr rdr = new Io_line_rdr(bldr.Usr_dlg(), Io_mgr._.QueryDir_fils(rdr_dir));
		ListAdp link_list = ListAdp_.new_();
		int count = 0; fail_count = 0;
		Xof_xfer_mgr xfer = new Xof_xfer_mgr(file_mgr).Check_file_exists_before_xfer_n_();	// turn off check for mass download
		int url_idx = rdr.Url_idx();
		Io_url dump_url = dump_url_gen.Nxt_url();	// gen one url
		time_bgn = Env_.TickCount();
		xfer_itm.Clear();
		Xofo_file row = new Xofo_file();
		Xow_repo_mgr wiki_repo_mgr = wiki.File_mgr().Repo_mgr();
		Xof_repo_itm src_repo, trg_repo;
		Xofo_lnki_parser lnki_parser = new Xofo_lnki_parser();
		Xof_meta_mgr trg_meta_mgr = wiki.File_mgr().Meta_mgr();
		fld_wtr.Bfr_(dump_bfr);
		while (rdr.Read_next()) {
			try {
				if (url_idx != rdr.Url_idx()) {
					this.Flush(rdr, dump_url, url_idx, time_bgn, count);
					url_idx = rdr.Url_idx();
					count = 0;
					fail_count = 0;
					time_bgn = Env_.TickCount();
				}
				row.Load_by_xfer_rdr(fld_rdr, lnki_parser, rdr, link_list);
				int repo_id = row.Repo_id();
				if (repo_id == -1) repo_id = 0;		// HACK: xfer_itm not found in either File ns; assume 0 (which should be commons)
				Xof_repo_pair repo_pair = wiki_repo_mgr.Repos_get_at(repo_id);
				src_repo = repo_pair.Src(); trg_repo = repo_pair.Trg();
				xfer_itm.Atrs_by_orig(row.Orig_w(), row.Orig_h(), row.Orig_size());
				xfer_itm.Atrs_by_ttl(row.Name(), row.Redirect());
				xfer_itm.Trg_repo_idx_(repo_id);
				xfer_itm.Atrs_by_meta(trg_meta_mgr.Get_itm_or_new(xfer_itm.Lnki_ttl(), xfer_itm.Lnki_md5()), trg_repo, wiki.Html_mgr().Img_thumb_width());
				xfer_itm.Meta_itm().Load_orig_(xfer_itm.Orig_w(), xfer_itm.Orig_h());
				if (row.Redirect_exists()) {	// write redirect row; note that rdr has name/redirect reversed
					byte[] redirect_ttl = row.Name();
					Xof_meta_itm redirect_itm = trg_meta_mgr.Get_itm_or_new(redirect_ttl, Xof_xfer_itm_.Md5_(redirect_ttl));
					redirect_itm.Ptr_ttl_(row.Redirect()).Vrtl_repo_(row.Repo_id());
				}

				Xofo_lnki[] lnks = row.Links(); int lnks_len = lnks.length;
				ext_stats[xfer_itm.Lnki_ext().Id()].Update_file(row, lnks_len);
				boolean thumb_pass = false, skip = false; //byte orig_pass = Bool_.__byte;
				for (int i = 0; i < lnks_len; i++) {
					Xofo_lnki lnk = lnks[i];
					xfer_itm.Init_by_lnki(row.Name(), row.Redirect(), lnk.Lnki_type(), lnk.Lnki_w(), lnk.Lnki_h(), lnk.Lnki_upright(), lnk.Lnki_thumbtime(), Xof_doc_page.Null);	// NOTE: row.Name(), row.Redirect() is redundant with above, but (a) performance implications only and (b) this class is deprecated
					xfer_itm.Atrs_calc_for_html();
					xfer.Atrs_by_itm(xfer_itm, src_repo, trg_repo);
					if (!xfer.Download_allowed_by_ext() || xfer_itm.Meta_itm().Orig_exists() == Xof_meta_itm.Exists_n) {skip = true; break;}
					thumb_pass = xfer.Make_file(wiki);
					if (!thumb_pass) break;
				}
				if (skip) continue;

				if (thumb_pass)
					bldr.Usr_dlg().Prog_many(GRP_KEY, "xfer_passed", "pass|~{0}|~{1}", Int_.XtoStr_PadBgn_space(count++, 7), String_.new_utf8_(row.Name()));
				else {
					bldr.Usr_dlg().Note_many(GRP_KEY, "xfer_failed", "fail|~{0}|~{1}|~{2}|~{3}", Int_.XtoStr_PadBgn_space(count++, 7), xfer.Rslt().Err_msg(), String_.new_utf8_(xfer_itm.Lnki_md5(), 0, 2) , String_.new_utf8_(row.Name()));
					if (dump_bfr.Len() > dump_fil_len) Io_mgr._.AppendFilBfr(dump_url_gen.Nxt_url(), dump_bfr);
//						if		(orig_pass == Bool_.Y_byte && thumb_pass)	mode = Xof_meta_itm.Mode_both_exists;
//						else if (orig_pass == Bool_.Y_byte)					mode = Xof_meta_itm.Mode_orig_exists;
//						else if (thumb_pass)								mode = Xof_meta_itm.Mode_thumb_exists;
					
					xfer.Meta_itm().Orig_exists_(Xof_meta_itm.Exists_unknown);
					xfer.Meta_itm().Vrtl_repo_(Xof_meta_itm.Repo_missing);
//						xfer.Regy_itm().Mode_set(xfer_itm.File_type().Id_is_video() ? Xof_meta_itm.Mode_thumb_missing : Xof_meta_itm.Mode_orig_missing);
					row.Status_msg_(Bry_.new_utf8_(xfer.Rslt().Err_msg()));
					row.Write_bldr(fld_wtr);
					fail_count++;
				}
			}
			catch (Exception exc) {
				bldr.Usr_dlg().Note_many(GRP_KEY, "xfer_error", "fail|~{0}|~{1}|~{2}", Int_.XtoStr_PadBgn_space(count++, 7), String_.new_utf8_(xfer_itm.Lnki_md5(), 0, 2) + "|" + String_.new_utf8_(row.Name()), Err_.Message_lang(exc));
				if (dump_bfr.Len() > dump_fil_len) Io_mgr._.AppendFilBfr(dump_url_gen.Nxt_url(), dump_bfr);
				row.Write_bldr(fld_wtr);							
				fail_count++;
			}
		}
		this.Flush(rdr, dump_url, url_idx, time_bgn, count);
	}	long files_sum = 0, elapsed_sum = 0; long time_bgn;
	private void Flush(Io_line_rdr rdr, Io_url dump_url, int url_idx, long time_bgn, int fil_count) {
		wiki.File_mgr().Meta_mgr().Save();
//			int dirty_meta_mgrs_len = dirty_meta_mgrs.Count();
//			for (int i = 0; i < dirty_meta_mgrs_len; i++) {
//				Xof_meta_mgr dirty_meta_mgr = (Xof_meta_mgr)dirty_meta_mgrs.FetchAt(i);
//				dirty_meta_mgr.Save();
//			}
		Io_mgr._.AppendFilBfr(dump_url, dump_bfr);
		Io_url cur = rdr.Urls()[url_idx];
		int seconds = Env_.TickCount_elapsed_in_sec(time_bgn);
		double rate = Math_.Div_safe_as_double(fil_count * 3600, seconds);
		files_sum += fil_count;
		elapsed_sum += seconds;
		String txt = bldr.Usr_dlg().Note_many(GRP_KEY, "flush", "name=~{0} files=~{1} fails=~{2} seconds=~{3} files/hour=~{4} files_total=~{5} seconds_total=~{6}", cur.NameOnly(), fil_count, fail_count, seconds, rate, files_sum, elapsed_sum);
		Io_mgr._.AppendFilStr(tmp_dir.GenSubFil("progress.txt"), txt + "\n");
	}
	public void Cmd_end() {
		Bry_bfr bfr = Bry_bfr.new_();
		int ext_stats_len = ext_stats.length;
		for (int i = 0; i < ext_stats_len; i++)
			ext_stats[i].Bld(bfr);
		bldr.Usr_dlg().Note_many(GRP_KEY, "end", "~{0}", bfr.XtoStrAndClear());
	}
	public void Cmd_print() {}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_rdr_dir_))			{rdr_dir = Io_url_.new_dir_(m.ReadStr("v"));}
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}	public static final String Invk_rdr_dir_ = "rdr_dir_";
	Bry_bfr dump_bfr = Bry_bfr.new_(); Gfo_fld_wtr fld_wtr; Gfo_fld_rdr fld_rdr; Bry_bfr tmp = Bry_bfr.new_();
	Io_url_gen dump_url_gen; int dump_fil_len = 4 * Io_mgr.Len_mb;
	static final String GRP_KEY = "xowa.bldr.img_run_xfer";
}
