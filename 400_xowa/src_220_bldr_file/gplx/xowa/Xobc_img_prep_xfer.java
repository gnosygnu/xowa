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
import gplx.core.flds.*; import gplx.ios.*; import gplx.xowa.bldrs.*; import gplx.xowa.parsers.lnkis.redlinks.*;
public class Xobc_img_prep_xfer extends Xob_itm_basic_base implements Xob_cmd, GfoInvkAble {
	public Xobc_img_prep_xfer(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki);}		
	public String Cmd_key() {return KEY;} public static final String KEY = "img.prep_xfer";
	public Io_url Src_fil() {return src_fil;} public Xobc_img_prep_xfer Src_fil_(Io_url v) {src_fil = v; return this;} Io_url src_fil;
	public Io_url Temp_dir() {return temp_dir;}
	public Io_url Link_dir() {return link_dir;} Io_url link_dir;
	public Io_url Wiki_1_dir() {return wiki_1_dir;} Io_url wiki_1_dir;
	public Io_url Wiki_0_dir() {return wiki_0_dir;} Io_url wiki_0_dir;
	public Io_url_gen Dump_url_gen() {return dump_url_gen;} public Xobc_img_prep_xfer Dump_url_gen_(Io_url_gen v) {dump_url_gen = v; return this;} Io_url_gen dump_url_gen;
	public void Commons_url_(Io_url v) {commons_url = v;} Io_url commons_url;
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		this.bldr = bldr; this.fld_wtr = Gfo_fld_wtr.xowa_().Bfr_(dump_bfr); fld_rdr = Gfo_fld_rdr.xowa_();
		temp_dir = wiki.Fsys_mgr().Tmp_dir().GenSubDir(KEY);
		Io_mgr._.DeleteDirDeep(temp_dir);
	
		if (commons_url == null) commons_url = wiki.Fsys_mgr().Root_dir().OwnerDir().GenSubDir_nest(Xow_wiki_.Domain_commons_str);
		link_dir = wiki.Fsys_mgr().Tmp_dir().GenSubDir_nest(Xobc_lnki_wkr_file.KEY, "make");
		wiki_1_dir = wiki.Fsys_mgr().Tmp_dir().GenSubDir_nest(Xobc_img_merge_ttl_sql.KEY, "make");
		wiki_0_dir = commons_url.GenSubDir_nest("tmp", Xobc_img_merge_ttl_sql.KEY, "make");
		dump_url_gen = Io_url_gen_.dir_(temp_dir.GenSubDir("dump"));
	}	Io_line_rdr img_link_rdr; Gfo_fld_wtr fld_wtr; Gfo_fld_rdr fld_rdr;
	Io_line_rdr rdr_(Io_url dir) {
		Io_url[] fils = Io_mgr._.QueryDir_fils(dir);
		return new Io_line_rdr(bldr.Usr_dlg(), fils).Key_gen_(Io_line_rdr_key_gen_.first_pipe);
	}
	private void Write(Xofo_file xfer, ListAdp link_list) {
		xfer.Links_(link_list);
		if (dump_bfr.Len() > dump_fil_len) {Io_mgr._.AppendFilByt(dump_url_gen.Nxt_url(), dump_bfr.Bfr(), dump_bfr.Len()); dump_bfr.Clear();}
		xfer.Write_bldr(fld_wtr);
	}	private Xofo_lnki_parser lnki_parser = new Xofo_lnki_parser();
	public void Cmd_end() {}
	public void Cmd_print() {}
	public void Cmd_run() {
		img_link_rdr = rdr_(link_dir);
		Xofo_file xfer = null;
		byte[] prv_name = Bry_.Empty;
		Io_line_rdr wiki_1_rdr = rdr_(wiki_1_dir), wiki_0_rdr = rdr_(wiki_0_dir);
		ListAdp link_list = ListAdp_.new_();
		while (img_link_rdr.Read_next()) {
			Xofo_lnki link = new Xofo_lnki().Load_link_rdr(fld_rdr, img_link_rdr, lnki_parser);
			byte[] cur_name = link.Name();
			if (cur_name == null) continue;
			if (!Bry_.Eq(cur_name, prv_name)) {
				if (xfer != null) Write(xfer, link_list);
				xfer = new Xofo_file();
				prv_name = cur_name;
				boolean exists = wiki_0_rdr.Match(cur_name);
				if (exists)
					xfer.Load_by_merge_rdr(fld_rdr, wiki_0_rdr).Repo_id_(0);		// NOTE: 0 is an ordinal (denotes 1st rdr)
				else {
					exists = wiki_1_rdr.Match(cur_name);
					if (exists)
						xfer.Load_by_merge_rdr(fld_rdr, wiki_1_rdr).Repo_id_(1);	// NOTE: 0 is an ordinal (denotes 2nd rdr)
					else
						xfer.Name_(cur_name);
				}
				total_count++;
			}
			link_list.Add(link);
			total_size += xfer.Orig_size();
		}
		Write(xfer, link_list);
		Io_mgr._.AppendFilByt(dump_url_gen.Nxt_url(), dump_bfr.Bfr(), dump_bfr.Len()); dump_bfr.Clear();
	}
	public int Total_count() {return total_count;} private int total_count;
	public long Total_size() {return total_size;} long total_size;
	Sql_file_parser parser = new Sql_file_parser(); Io_url temp_dir;
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_src_fil_))			{src_fil = m.ReadIoUrl("url");}
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}	private static final String Invk_src_fil_ = "src_fil_";
	Bry_bfr dump_bfr = Bry_bfr.new_(); int dump_fil_len = 100 * Io_mgr.Len_kb;
}
class Xobc_lnki_wkr_file extends Xob_itm_dump_base implements Xop_lnki_logger {
	public static final String KEY = "img.dump_link";
	public Xobc_lnki_wkr_file(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki); this.make_fil_len = 1 * Io_mgr.Len_mb;}
	public Io_url_gen Make_url_gen() {return make_url_gen;} public Xobc_lnki_wkr_file Make_url_gen_(Io_url_gen v) {make_url_gen = v; return this;} Io_url_gen make_url_gen;
	public void Wkr_bgn(Xob_bldr bldr) {
		Io_url temp_dir2 = wiki.Fsys_mgr().Tmp_dir().GenSubDir(KEY);
		this.Init_dump(KEY, temp_dir2.GenSubDir("make"));
		this.lnki_wtr = Gfo_fld_wtr.xowa_().Bfr_(dump_bfr);
		Io_mgr._.DeleteDirDeep_ary(temp_dir);
		dump_url_gen = Io_url_gen_.dir_(temp_dir.GenSubDir("dump"));
		sort_dir = temp_dir.GenSubDir("sort");
		make_url_gen = Io_url_gen_.dir_(temp_dir.GenSubDir("make"));
	}	Io_url sort_dir; Gfo_fld_wtr lnki_wtr;
	public void Wkr_exec(Xop_ctx ctx, byte[] src, Xop_lnki_tkn lnki, byte lnki_src_tid) {
		byte[] ttl = lnki.Ttl().Page_db();
		ttl = encoder.Decode_lax(lnki.Ttl().Page_db());
		Xofo_lnki.Write(lnki_wtr, ttl, lnki);
		if (dump_bfr.Len() > dump_fil_len) Flush(); // NOTE: doing this after; in order to do before, need to precalc len of width/height which is simply not worth it
	}	Url_encoder encoder = Url_encoder.new_file_();
	public void Wkr_end() {
		Flush();
		Io_sort_cmd_img img_cmd = new Io_sort_cmd_img().Make_url_gen_(make_url_gen).Make_fil_max_(make_fil_len);
		Xobdc_merger.Basic(bldr.Usr_dlg(), dump_url_gen, sort_dir, sort_mem_len, Io_line_rdr_key_gen_all_wo_nl._, img_cmd);		
	}
	public void Flush() {
		Io_mgr._.AppendFilBfr(dump_url_gen.Nxt_url(), dump_bfr);
		dump_bfr.Reset_if_gt(Io_mgr.Len_mb);
	}
}
