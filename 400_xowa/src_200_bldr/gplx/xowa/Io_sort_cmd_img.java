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
import gplx.ios.*;
class Io_sort_cmd_img implements Io_sort_cmd {
	Bry_bfr fil_bfr = Bry_bfr.new_();
	int prv_itm_bgn, prv_itm_end;
	public int Make_fil_max() {return make_fil_max;} public Io_sort_cmd_img Make_fil_max_(int v) {make_fil_max = v; return this;} private int make_fil_max = 65 * Io_mgr.Len_kb;
	public Io_url_gen Make_url_gen() {return make_url_gen;} public Io_sort_cmd_img Make_url_gen_(Io_url_gen v) {make_url_gen = v; return this;} Io_url_gen make_url_gen;
	public void Sort_bgn() {
		prv_itm_bgn = prv_itm_end = 0;
	} 	byte line_dlm = Byte_ascii.Nil;
	public void Sort_do(Io_line_rdr rdr) {
		if (line_dlm == Byte_ascii.Nil) line_dlm = rdr.Line_dlm();
		int rdr_key_bgn = rdr.Key_pos_bgn(), rdr_key_end = rdr.Key_pos_end();
		if (prv_itm_end == 0 || !Bry_.Match(rdr.Bfr(), rdr_key_bgn, rdr_key_end, fil_bfr.Bfr(), prv_itm_bgn, prv_itm_end)) {
			int fil_bfr_len = fil_bfr.Len();
			int rdr_key_len = rdr_key_end - rdr_key_bgn;
			if (fil_bfr_len + rdr_key_len > make_fil_max) Flush();
			prv_itm_bgn = fil_bfr_len;
			prv_itm_end = prv_itm_bgn + rdr_key_len;   
			fil_bfr.Add_mid(rdr.Bfr(), rdr_key_bgn, rdr_key_end).Add_byte_nl();
		}
	}
	public void Sort_end() {
		Flush();
		//fil_wtr.Rls(); itm_bfr.Rls(); fil_wtr.Rls(); reg_bfr.Rls(); key_bfr_0.Rls(); key_bfr_n.Rls();
	}
	private void Flush() {
		Io_mgr._.SaveFilBry(make_url_gen.Nxt_url(), fil_bfr.Bfr(), fil_bfr.Len());
		fil_bfr.Clear();
	}
}
class Io_line_rdr_key_gen_img implements Io_line_rdr_key_gen {
	public void Gen(Io_line_rdr rdr) {
		int itm_bgn = rdr.Itm_pos_bgn();
		int itm_end = rdr.Itm_pos_end();
		rdr.Key_pos_bgn_(itm_bgn);
		int key_end = Bry_finder.Find_bwd(rdr.Bfr(), Byte_ascii.Pipe, itm_end - 2, itm_bgn); // NOTE: -2 to skip terminating |\n
		rdr.Key_pos_end_(key_end + 1); // NOTE: +1 to include terminating |; enforces every field terminating with |; EX: A.png|0|220|120|.8|\n
	}
}
class Io_line_rdr_key_gen_all_wo_nl implements Io_line_rdr_key_gen {
	public void Gen(Io_line_rdr bfr) {
		bfr.Key_pos_bgn_(bfr.Itm_pos_bgn()).Key_pos_end_(bfr.Itm_pos_end() - 1);	// subtract closing nl
	}
	public static final Io_line_rdr_key_gen_all_wo_nl _ = new Io_line_rdr_key_gen_all_wo_nl(); Io_line_rdr_key_gen_all_wo_nl() {}
}
