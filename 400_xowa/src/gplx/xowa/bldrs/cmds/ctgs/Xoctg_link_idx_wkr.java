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
package gplx.xowa.bldrs.cmds.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import gplx.core.flds.*; import gplx.ios.*; import gplx.xowa.ctgs.*; import gplx.xowa.tdbs.*;
public class Xoctg_link_idx_wkr extends Xob_idx_base {	// NOTE: similar functionality to Xob_make_cmd_site, but more complicated due to p,f,s; not inheriting
	Io_url src_link_dir; int make_fil_max = Int_.MinValue;
	public Xoctg_link_idx_wkr(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	@Override public String Cmd_key() {return Xob_cmd_keys.Key_tdb_ctg_link_idx;}
	@Override public void Cmd_bgn_hook() {
		this.fld_rdr = this.Fld_rdr();
		if (src_link_dir == null) src_link_dir = wiki.Fsys_mgr().Tmp_dir().GenSubDir_nest(Xob_cmd_keys.Key_tdb_text_cat_link, "make");
		if (make_fil_max == Int_.MinValue) make_fil_max = Io_mgr.Len_mb;
		make_link_mgr = new Xoctg_make_link_mgr(usr_dlg, make_fil_max, wiki.Tdb_fsys_mgr()); 
		make_main_mgr = new Xoctg_make_main_mgr(usr_dlg, make_fil_max, wiki.Tdb_fsys_mgr());
		Io_mgr.I.DeleteDirDeep_ary(make_link_mgr.Make_dir(), make_main_mgr.Make_dir());
	}	Gfo_fld_rdr fld_rdr; Xoctg_make_link_mgr make_link_mgr;
	@Override public void Cmd_run() {
		Xoctg_make_link_grp cur_grp = null;
		Io_line_rdr link_rdr = rdr_(src_link_dir);
		byte[] prv_name = Bry_.Empty; byte prv_tid = Xoa_ctg_mgr.Tid_null;
		make_link_mgr.Write_bgn();
		make_main_mgr.Write_bgn();
		while (link_rdr.Read_next()) {
			link_data.Parse_from_sql_dump(fld_rdr, link_rdr);
			byte[] cur_name = link_data.Name();
			if (!Bry_.Eq(cur_name, prv_name)) {	// grp changed
				if (prv_name != Bry_.Empty)		// ignore change from 1st row
					Write_grp(prv_name, make_link_mgr);
				prv_name = cur_name;
			}
			byte cur_tid = link_data.Tid();
			if (cur_tid != prv_tid) {				// tid changed
				cur_grp = make_link_mgr.Grp_by_tid(cur_tid);
				prv_tid = cur_tid;
			}
			cur_grp.Add_itm(link_data);
		}
		Write_grp(prv_name, make_link_mgr);
		make_link_mgr.Flush();
		make_main_mgr.Flush();
	}	private Xoctg_idx_data_link link_data = new Xoctg_idx_data_link();
	@Override public void Cmd_end() {
		if (delete_temp) Io_mgr.I.DeleteDirDeep_ary(src_link_dir, make_main_mgr.Src_dir());
	}	boolean delete_temp = true;
	private void Write_grp(byte[] cur_name, Xoctg_make_link_mgr make_link_mgr) {
		make_main_mgr.Grps_write(cur_name, make_link_mgr.Subc_grp().Count(), make_link_mgr.File_grp().Count(), make_link_mgr.Page_grp().Count());
		make_link_mgr.Grps_write(cur_name);
	}	private Xoctg_make_main_mgr make_main_mgr;
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_src_link_dir_))				src_link_dir = m.ReadIoUrl("v");
		else if	(ctx.Match(k, Invk_make_fil_max_))				make_fil_max = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_make_fil_max_))				make_fil_max = m.ReadInt("v");
		else if	(ctx.Match(k, Invk_delete_temp_))				delete_temp = m.ReadBool("v");
		else													return super.Invk(ctx, ikey, k, m);
		return this;
	}
	public static final String Invk_src_link_dir_ = "src_link_dir_", Invk_make_fil_max_ = "make_fil_max_", Invk_delete_temp_ = "delete_temp_";
}
class Xoctg_make_link_mgr {
	public Xoctg_make_link_mgr(Gfo_usr_dlg usr_dlg, int make_fil_max, Xotdb_fsys_mgr fsys_mgr) {
		this.make_fil_max = make_fil_max;
		subc_grp = new Xoctg_make_link_grp(Xoa_ctg_mgr.Tid_subc, make_fil_max);
		file_grp = new Xoctg_make_link_grp(Xoa_ctg_mgr.Tid_file, make_fil_max);
		page_grp = new Xoctg_make_link_grp(Xoa_ctg_mgr.Tid_page, make_fil_max);
		make_fil_bfr = Bry_bfr.reset_(make_fil_max);
		make_fld_wtr = Gfo_fld_wtr.xowa_().Bfr_(make_fil_bfr); 
		make_dir = fsys_mgr.Url_site_dir(Xotdb_dir_info_.Tid_category2_link);
		make_cmd = new Xob_make_cmd_site(usr_dlg, make_dir, make_fil_max);
	}	Gfo_fld_wtr make_fld_wtr; Bry_bfr make_fil_bfr; int make_fil_max; Xob_make_cmd_site make_cmd;
	public Io_url Make_dir() {return make_dir;} Io_url make_dir;
	public Xoctg_make_link_grp Subc_grp() {return subc_grp;} private Xoctg_make_link_grp subc_grp;
	public Xoctg_make_link_grp File_grp() {return file_grp;} private Xoctg_make_link_grp file_grp;
	public Xoctg_make_link_grp Page_grp() {return page_grp;} private Xoctg_make_link_grp page_grp;
	public Xoctg_make_link_grp Grp_by_tid(byte tid) {
		switch (tid) {
			case Byte_ascii.Ltr_c: return subc_grp;
			case Byte_ascii.Ltr_f: return file_grp;
			case Byte_ascii.Ltr_p: return page_grp;
			default: throw Err_.unhandled(tid);
		}
	}
	public int Grps_bfr_len() {return subc_grp.Bfr_len() + file_grp.Bfr_len() + page_grp.Bfr_len();}
	public void Grps_reset() {subc_grp.Reset(); file_grp.Reset(); page_grp.Reset();}
	public void Grps_write(byte[] cur_name) {
		make_fld_wtr.Write_bry_escape_fld(cur_name);		// write name
		make_fld_wtr.Write_int_base85_len5_fld(subc_grp.Bfr_len()).Write_int_base85_len5_fld(file_grp.Bfr_len()).Write_int_base85_len5_fld(page_grp.Bfr_len());
		Write_grp(make_fil_bfr, subc_grp); Write_grp(make_fil_bfr, file_grp); Write_grp(make_fil_bfr, page_grp);
		make_fil_bfr.Add_byte_nl();
		byte[] bry = make_fld_wtr.Bfr().Bfr();
		make_cmd.Do_bry(bry, 0, cur_name.length, 0, make_fld_wtr.Bfr().Len());
		make_fil_bfr.Clear();
		this.Grps_reset();
	}
	public void Write_bgn() {
		make_cmd.Sort_bgn();
		make_cmd.Line_dlm_(Byte_ascii.NewLine);
	}
	public void Flush() {
		make_cmd.Sort_end();
	}
	private void Write_grp(Bry_bfr make_fil_bfr, Xoctg_make_link_grp grp) {
		if (grp.Bfr_len() == 0) return;
		make_fil_bfr.Add_bfr_and_clear(grp.Bfr());		// NOTE: should have trailing pipe
	}
	static final int Itm_len_fixed 
		= 1 				// name: 1 pipe
		+ (4 + (5 * 3))		// meta: 3 semis; 1 pipe; 3 base85 ints
		+ (2 * 3)			// sect: c| f| p|
		+ 1					// nl
		;
}
class Xoctg_make_link_grp {
	public Xoctg_make_link_grp(byte tid, int bfr_max) {this.tid = tid; bfr = Bry_bfr.reset_(bfr_max);} Gfo_fld_wtr fld_wtr = Gfo_fld_wtr.xowa_();
	public byte Tid() {return tid;} private byte tid;
	public int Bfr_len() {return bfr.Len();}
	public Bry_bfr Bfr() {return bfr;} Bry_bfr bfr;
	public int Count() {return count;} private int count;
	public void Reset() {count = 0;}
	public void Add_itm(Xoctg_idx_data_link link_data) {
		bfr	.Add_base85_len_5(link_data.Id()).Add_byte(Byte_ascii.Semic)			// add id;			"!!!!;"
			.Add_base85_len_5(link_data.Timestamp()).Add_byte(Byte_ascii.Semic);	// add timestamp;	"!!!!;"
		fld_wtr.Bfr_(bfr).Write_bry_escape_fld(link_data.Sortkey());				// add sortkey;		"A|"
		++count;
	}
}
class Xoctg_idx_data_link {
	public byte[] Name() {return name;} private byte[] name;
	public byte Tid() {return tid;} private byte tid;
	public int Id() {return id;} private int id;
	public int Timestamp() {return timestamp;} private int timestamp;
	public byte[] Sortkey() {return sortkey;} private byte[] sortkey;
	public void Parse_from_sql_dump(Gfo_fld_rdr fld_rdr, Io_line_rdr rdr) {
		fld_rdr.Ini(rdr.Bfr(), rdr.Itm_pos_bgn());
		name 			= fld_rdr.Read_bry_escape();
		tid 			= (byte)(fld_rdr.Read_byte());// - Byte_ascii.Num_0);
		sortkey			= fld_rdr.Read_bry_escape();
		id				= fld_rdr.Read_int_base85_len5();
		timestamp		= fld_rdr.Read_int_base85_len5();
	}
}
class Xoctg_make_main_mgr {
	public Xoctg_make_main_mgr(Gfo_usr_dlg usr_dlg, int make_fil_max, Xotdb_fsys_mgr fsys_mgr) {
		this.make_fil_max = make_fil_max;
		make_fil_bfr = Bry_bfr.reset_(make_fil_max);
		make_fld_wtr = Gfo_fld_wtr.xowa_().Bfr_(make_fil_bfr); 
		make_dir = fsys_mgr.Url_site_dir(Xotdb_dir_info_.Tid_category2_main);
		make_cmd = new Xob_make_cmd_site(usr_dlg, make_dir, make_fil_max);
		src_dir = fsys_mgr.Tmp_dir().GenSubDir_nest(Xob_cmd_keys.Key_tdb_cat_hidden_ttl, "make");
		hidden_rdr = new Io_line_rdr(usr_dlg, Io_mgr.I.QueryDir_fils(src_dir));
	}	Gfo_fld_wtr make_fld_wtr; Bry_bfr make_fil_bfr; int make_fil_max; Xob_make_cmd_site make_cmd; Io_line_rdr hidden_rdr;
	public Io_url Src_dir() {return src_dir;} Io_url src_dir;
	public Io_url Make_dir() {return make_dir;} Io_url make_dir;
	public void Grps_write(byte[] cur_name, int count_subc, int count_file, int count_page) {
		boolean hidden = hidden_rdr.Match(cur_name);
		make_fld_wtr.Write_bry_escape_fld(cur_name);		// write name
		make_fld_wtr.Write_byte_fld(hidden ? Byte_ascii.Ltr_y : Byte_ascii.Ltr_n);
		make_fld_wtr.Write_int_base85_len5_fld(count_subc).Write_int_base85_len5_fld(count_file).Write_int_base85_len5_fld(count_page);
		make_fil_bfr.Add_byte_nl();
		byte[] bry = make_fld_wtr.Bfr().Bfr();
		make_cmd.Do_bry(bry, 0, cur_name.length, 0, make_fld_wtr.Bfr().Len());
		make_fil_bfr.Clear();
	}
	public void Write_bgn() {
		make_cmd.Sort_bgn();
		make_cmd.Line_dlm_(Byte_ascii.NewLine);
	}
	public void Flush() {
		make_cmd.Sort_end();
	}
}
