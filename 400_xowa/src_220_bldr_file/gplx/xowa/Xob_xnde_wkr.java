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
public interface Xob_xnde_wkr {
	void Wkr_run(Xop_ctx ctx, Xop_root_tkn root, Xop_xnde_tkn xnde);
}
class Xobc_xnde_math_dump extends Xob_itm_dump_base implements Xob_xnde_wkr {	// note: similar to img_dump, except for Wkr_run
	public Xobc_xnde_math_dump(Xob_bldr bldr, Xow_wiki wiki) {this.Cmd_ctor(bldr, wiki); this.make_fil_len = 100 * Io_mgr.Len_kb;}
	public static final String KEY = "math.text";
	public Io_url_gen Make_url_gen() {return make_url_gen;} public Xobc_xnde_math_dump Make_url_gen_(Io_url_gen v) {make_url_gen = v; return this;} Io_url_gen make_url_gen;
	public void Wkr_bgn(Xob_bldr bldr) {
		this.Init_dump(KEY);
		this.fld_wtr = Gfo_fld_wtr.xowa_().Bfr_(dump_bfr);
		sort_dir = temp_dir.GenSubDir("sort");
		make_url_gen = Io_url_gen_.dir_(temp_dir.GenSubDir("make"));
	}	Io_url sort_dir; Gfo_fld_wtr fld_wtr; Bry_bfr url_decoder_bfr = Bry_bfr.new_();
	public void Wkr_run(Xop_ctx ctx, Xop_root_tkn root, Xop_xnde_tkn xnde) {
		if (xnde.CloseMode() == Xop_xnde_tkn.CloseMode_inline) return;	// ignore <math/>; EX:FOSD origami
		byte[] math = Bry_.Mid(root.Root_src(), xnde.Src_bgn() + 6, xnde.Src_end() - 7); // 6=<math>; 7=</math>
		byte[] ttl = ctx.Cur_page().Ttl().Full_txt();
		int entry_len = ttl.length + math.length + 3; // 3=| + | + \n  
		if (dump_bfr.Len() + entry_len > dump_fil_len) Flush();
		fld_wtr.Write_bry_escape_fld(math);
		fld_wtr.Write_bry_escape_row(ttl);
	}
	public void Wkr_end() {
		Flush();
		Io_sort_cmd_img img_cmd = new Io_sort_cmd_img().Make_url_gen_(make_url_gen).Make_fil_max_(make_fil_len);
		Xobdc_merger.Basic(bldr.Usr_dlg(), dump_url_gen, sort_dir, sort_mem_len, Io_line_rdr_key_gen_.first_pipe, img_cmd);		
	}
	public void Flush() {
		Io_mgr._.AppendFilBfr(dump_url_gen.Nxt_url(), dump_bfr);	
		dump_bfr.Reset_if_gt(Io_mgr.Len_mb);
	}
}
