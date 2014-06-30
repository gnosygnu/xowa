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
import gplx.ios.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wikis.images.*;
public class Xobc_img_merge_ttl_sql extends Xob_itm_dump_base implements Xob_cmd, GfoInvkAble {
	public Xobc_img_merge_ttl_sql(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki); this.make_fil_len = Io_mgr.Len_mb;}
	public String Cmd_key() {return KEY;} public static final String KEY = "img.merge_ttl";
	public Io_url Ttl_name_dir() {return ttl_name_dir;} Io_url ttl_name_dir;
	public Io_url Sql_size_dir() {return sql_size_dir;} Io_url sql_size_dir;
	public Io_url Make_url_dir() {return make_url_dir;} Io_url make_url_dir;
	public Io_url_gen Make_url_gen() {return make_url_gen;} Io_url_gen make_url_gen;
	public void Cmd_ini(Xob_bldr bldr) {}
	public void Cmd_bgn(Xob_bldr bldr) {
		this.Init_dump(KEY);

		sql_size_dir = wiki.Fsys_mgr().Tmp_dir().GenSubDir_nest(Xob_wiki_image_sql.KEY, "make");
		ttl_name_dir = wiki.Fsys_mgr().Tmp_dir().GenSubDir_nest(Xobc_img_dump_ttl.KEY, "make");
		redirect_name_dir = temp_dir.GenSubDir("redirect_dump");
		redirect_sort_dir = temp_dir.GenSubDir("redirect_sort");
		redirect_make_dir = temp_dir.GenSubDir("redirect_make");
		make_url_dir = temp_dir.GenSubDir("make");

		standard_url_gen = Io_url_gen_.dir_(temp_dir.GenSubDir("standard_dump"));
		redirect_url_gen = Io_url_gen_.dir_(redirect_name_dir);
		make_url_gen = Io_url_gen_.dir_(temp_dir.GenSubDir("make"));
	}	Gfo_fld_wtr fld_wtr = Gfo_fld_wtr.xowa_(); Gfo_fld_rdr fld_rdr = Gfo_fld_rdr.xowa_(); Io_url redirect_name_dir, redirect_sort_dir, redirect_make_dir;
	Io_line_rdr rdr_(Io_url dir) {
		Io_url[] fils = Io_mgr._.QueryDir_fils(dir);
		return new Io_line_rdr(bldr.Usr_dlg(), fils).Key_gen_(Io_line_rdr_key_gen_.first_pipe);
	}
	public void Cmd_end() {}
	public void Cmd_print() {}
	public void Cmd_run() {
		Io_line_rdr name_rdr = rdr_(ttl_name_dir);
		Io_line_rdr size_rdr = rdr_(sql_size_dir);
		Merge(true, name_rdr, size_rdr);
		Flush(standard_dump_bfr, standard_url_gen);
		Flush(redirect_dump_bfr, redirect_url_gen);
		Xobdc_merger.Basic(bldr.Usr_dlg(), redirect_url_gen, redirect_sort_dir, sort_mem_len, Io_line_rdr_key_gen_all._, new Io_sort_fil_basic(bldr.Usr_dlg(), Io_url_gen_.dir_(redirect_make_dir), make_fil_len));

		name_rdr = rdr_(redirect_make_dir);
		size_rdr = rdr_(sql_size_dir);
		Merge(false, name_rdr, size_rdr);
		Flush(standard_dump_bfr, standard_url_gen);
		Xobdc_merger.Basic(bldr.Usr_dlg(), standard_url_gen, temp_dir.GenSubDir("sort"), sort_mem_len, Io_line_rdr_key_gen_all._, new Io_sort_fil_basic(bldr.Usr_dlg(), make_url_gen, make_fil_len));
	}
	private void Merge(boolean pass_0, Io_line_rdr name_rdr, Io_line_rdr size_rdr) {
		while (name_rdr.Read_next()) {
			Xofo_file itm = new Xofo_file();
			itm.Load_by_name_rdr(fld_rdr, name_rdr);
			byte[] name_lower = Xof_ext_.Lower_ext(Bry_.Copy(itm.Name()));
			boolean found = size_rdr.Match(name_lower);
			if (found) itm.Load_by_size_rdr(fld_rdr, size_rdr);
			Write(pass_0, itm);
		}	
	}
	private void Write(boolean pass_0, Xofo_file itm) {
		if (pass_0) {
			if (itm.Redirect_exists())
				Write(redirect_dump_bfr, redirect_url_gen, itm, true);
			else
				Write(standard_dump_bfr, standard_url_gen, itm, false);
		}
		else
			Write(standard_dump_bfr, standard_url_gen, itm, true);	// reverse name/redirect from pass_0
	}
	private void Write(Bry_bfr bfr, Io_url_gen url_gen, Xofo_file itm, boolean reverse) {
		if (bfr.Len() > dump_fil_len) Flush(bfr, url_gen);
		fld_wtr.Bfr_(bfr);
		itm.Write(reverse, fld_wtr);
	}
	private void Flush(Bry_bfr bfr, Io_url_gen url_gen) {Io_mgr._.SaveFilBfr(url_gen.Nxt_url(), bfr);}
	Sql_file_parser parser = new Sql_file_parser(); 
	Bry_bfr standard_dump_bfr = Bry_bfr.new_(), redirect_dump_bfr = Bry_bfr.new_();
	Io_url_gen standard_url_gen, redirect_url_gen;
	Bry_bfr noop = Bry_bfr.new_();
}
