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
import gplx.core.flds.*; import gplx.ios.*;
public class Xobc_img_dump_ttl extends Xob_itm_dump_base implements Xobd_wkr, GfoInvkAble {
	public Xobc_img_dump_ttl(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	public String Wkr_key() {return KEY;} public static final String KEY = "img.dump_ttl";
	@gplx.Internal protected Io_url_gen Make_url_gen() {return make_url_gen;} Io_url_gen make_url_gen;
	public void Wkr_ini(Xob_bldr bldr) {}
	public void Wkr_bgn(Xob_bldr bldr) {
		this.Init_dump(KEY);
		make_url_gen = Io_url_gen_.dir_(temp_dir.GenSubDir("make"));
		fld_wtr = Gfo_fld_wtr.xowa_().Bfr_(dump_bfr);
		redirect_mgr = wiki.Redirect_mgr();
	}	Gfo_fld_wtr fld_wtr; Xop_redirect_mgr redirect_mgr;
	public void Wkr_run(Xodb_page page) {
		if (page.Ns_id() != Xow_ns_.Id_file) return;
		Xoa_ttl ttl = Xoa_ttl.parse_(wiki, page.Ttl_w_ns());
		byte[] ttl_bry = ttl.Page_db();
		byte[] src = page.Text();
		Xoa_ttl redirect_ttl = redirect_mgr.Extract_redirect(src, src.length);
		byte[] redirect_bry = redirect_ttl == null ? Bry_.Empty : redirect_ttl.Page_db();
		if (dump_bfr.Len() + ttl_bry.length + redirect_bry.length + Fixed_row_len > dump_fil_len) super.Flush_dump();
		fld_wtr	.Write_bry_escape_fld(ttl_bry)
				.Write_bry_escape_row(redirect_bry);
	}	private static final int Fixed_row_len = 3;	// 2=| 1=\n
	public void Wkr_end() {
		super.Flush_dump();
		Xobdc_merger.Basic(bldr.Usr_dlg(), dump_url_gen, temp_dir.GenSubDir("sort"), sort_mem_len, Io_line_rdr_key_gen_all._, new Io_sort_fil_basic(bldr.Usr_dlg(), make_url_gen, dump_fil_len));
	}
	public void Wkr_print() {}
}
