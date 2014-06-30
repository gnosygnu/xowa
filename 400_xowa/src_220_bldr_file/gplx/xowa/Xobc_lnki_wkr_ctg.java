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
import gplx.ios.*; import gplx.xowa.parsers.lnkis.redlinks.*;
class Xobc_lnki_wkr_ctg extends Xob_itm_dump_base implements Xop_lnki_logger {
	public static final String KEY = "dump.ctg";
	public Xobc_lnki_wkr_ctg(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki); this.make_fil_len = 1 * Io_mgr.Len_mb;}
	public Io_url_gen Make_url_gen() {return make_url_gen;} Io_url_gen make_url_gen;
	public void Wkr_bgn(Xob_bldr bldr) {
		this.Init_dump(KEY, wiki.Fsys_mgr().Tmp_dir().GenSubDir(KEY).GenSubDir("make"));
		this.lnki_wtr = Gfo_fld_wtr.xowa_().Bfr_(dump_bfr);
		Io_mgr._.DeleteDirDeep_ary(temp_dir);
		dump_url_gen = Io_url_gen_.dir_(temp_dir.GenSubDir("dump"));
		sort_dir = temp_dir.GenSubDir("sort");
		make_url_gen = Io_url_gen_.dir_(temp_dir.GenSubDir("make"));
		fld_wtr.Bfr_(dump_bfr);
	}	Io_url sort_dir; Gfo_fld_wtr lnki_wtr; private Gfo_fld_wtr fld_wtr = Gfo_fld_wtr.xowa_();
	public void Wkr_exec(Xop_ctx ctx, byte[] src, Xop_lnki_tkn lnki, byte lnki_src_tid) {
		gplx.xowa.bldrs.imports.ctgs.Xob_ctg_v1_base.Process_ctg_row(fld_wtr, dump_fil_len, dump_url_gen, ctx.Cur_page().Revision_data().Id(), src, src.length, lnki.Src_bgn(), lnki.Src_end());
	}
	public void Wkr_end() {
		this.Term_dump(new Xob_make_cmd_site(bldr.Usr_dlg(), make_dir, make_fil_len));
		if (delete_temp) Io_mgr._.DeleteDirDeep(temp_dir);
	}
}
