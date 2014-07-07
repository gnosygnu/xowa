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
public class Xob_make_cmd_site implements Io_make_cmd {
	Xob_xdat_file_wtr fil_wtr; Bry_bfr cur_bfr = Bry_bfr.new_(), reg_bfr = Bry_bfr.new_(), reg_key_0 = Bry_bfr.new_(512), reg_key_n = Bry_bfr.new_(512);
	int make_fil_max = 65 * Io_mgr.Len_kb, fil_count = 0, itm_count = 0, itm_key_end = 0; Io_url reg_url;
	public Xob_make_cmd_site(Gfo_usr_dlg usr_dlg, Io_url make_dir, int make_fil_max) {this.usr_dlg = usr_dlg; this.make_dir = make_dir; this.make_fil_max = make_fil_max;} Gfo_usr_dlg usr_dlg;
	public Io_sort_cmd Make_dir_(Io_url v) {make_dir = v; return this;} Io_url make_dir;
	public byte Line_dlm() {return line_dlm;} public Xob_make_cmd_site Line_dlm_(byte v) {line_dlm = v; return this;} private byte line_dlm = Byte_ascii.Nil;
	public void Sort_bgn() {
		fil_count = itm_count = itm_key_end = 0;
		reg_url = make_dir.GenSubFil(Xow_dir_info_.Name_reg_fil);
		fil_wtr = Xob_xdat_file_wtr.new_file_(make_fil_max, make_dir);
	}
	public void Sort_do(Io_line_rdr rdr) {
		if (line_dlm == Byte_ascii.Nil) line_dlm = rdr.Line_dlm();
		int rdr_key_bgn = rdr.Key_pos_bgn(), rdr_key_end = rdr.Key_pos_end();
		int rdr_key_len = rdr_key_end - rdr_key_bgn;
		int rdr_val_bgn = rdr_key_end, /* NOTE: no +1: want to include fld_dlm for below*/ rdr_val_end = rdr.Itm_pos_end() - 1; // -1: ignore rdr_dlm
		if (Bry_.Match(cur_bfr.Bfr(), 0, itm_key_end, rdr.Bfr(), rdr_key_bgn, rdr_key_end))	// key is same; add rest of line as val
			cur_bfr.Add_mid(rdr.Bfr(), rdr_val_bgn, rdr_val_end);
		else {
			if (fil_wtr.FlushNeeded(cur_bfr.Len() + rdr_key_len)) Flush();
			byte[] bfr = rdr.Bfr();
			if (reg_key_0.Len() == 0) {
				if (cur_bfr.Len() == 0)
					reg_key_0.Add_mid(bfr, rdr_key_bgn, rdr_key_end);
				else
					reg_key_0.Add_mid(cur_bfr.Bfr(), 0, itm_key_end);
			}
			if (cur_bfr.Len() > 0) {
				reg_key_n.Clear().Add_mid(cur_bfr.Bfr(), 0, itm_key_end);
				fil_wtr.Bfr().Add_bfr_and_clear(cur_bfr);
				fil_wtr.Add_idx(line_dlm);
			}
			cur_bfr.Add_mid(rdr.Bfr(), rdr.Itm_pos_bgn(), rdr.Itm_pos_end() - 1);	// -1 to ignore closing newline
			itm_key_end = rdr_key_len;	// NOTE: must be set last
			++itm_count;
		}
	}
	public void Do_bry(byte[] bry, int key_bgn, int key_end, int itm_bgn, int itm_end) {
		int val_bgn = key_end, /* NOTE: no +1: want to include fld_dlm for below*/ val_end = itm_end - 1; // -1: ignore rdr_dlm
		if (Bry_.Match(cur_bfr.Bfr(), 0, itm_key_end, bry, key_bgn, key_end))	// key is same; add rest of line as val
			cur_bfr.Add_mid(bry, val_bgn, val_end);
		else {																		// key changed;
			int itm_len = itm_end - itm_bgn;
			if (cur_bfr.Len() > 0) {											// pending itm
				fil_wtr.Bfr().Add_bfr_and_clear(cur_bfr);							// add cur_bfr to fil_bfr
				fil_wtr.Add_idx(line_dlm);											// add cur_itm to hdr
				if (fil_wtr.FlushNeeded(cur_bfr.Len() + itm_len))
					Flush();
			}
			if (reg_key_0.Len() == 0)											// regy.key_0 bfr is empty
				reg_key_0.Add_mid(bry, key_bgn, key_end);							// update reg_0key_0
			reg_key_n.Clear().Add_mid(bry, key_bgn, key_end);						// always update reg_key_n
			if (itm_len > 100 * Io_mgr.Len_mb)
				Flush_large(bry, itm_bgn, itm_end, itm_len);
			else {
				cur_bfr.Add_mid(bry, itm_bgn, itm_end - 1);							// add incoming itm; -1 to ignore closing newline
				itm_key_end = key_end;												// NOTE: must be set last
				++itm_count;
			}
		}
	}
	public void Sort_end() {
		reg_key_n.Clear().Add_mid(cur_bfr.Bfr(), 0, itm_key_end);
		fil_wtr.Bfr().Add_bfr_and_clear(cur_bfr);
		fil_wtr.Add_idx(line_dlm);
		Flush();
		Io_mgr._.AppendFilBfr(reg_url, reg_bfr);
		//fil_wtr.Rls(); cur_bfr.Rls(); fil_wtr.Rls(); reg_bfr.Rls(); reg_key_0.Rls(); reg_key_n.Rls();
	}
//		private void Flush_large(byte[] bry, int itm_bgn, int itm_end, int itm_len) {
//			++itm_count;
//			this.Flush_reg();
//			fil_wtr.Add_idx_direct(itm_len, Byte_.Zero);
//			IoStream stream = IoStream_.Null;
//			try {
//				stream = Io_mgr._.OpenStreamWrite(fil_wtr.Fil_url());
//				fil_wtr.FlushIdx(stream);
//				stream.Write_and_flush(bry, itm_bgn, itm_end);
//				fil_wtr.Clear();
//				fil_wtr.Url_gen_add();
//			}
//			finally {stream.Rls();}
//		}
	private void Flush_large(byte[] bry, int itm_bgn, int itm_end, int itm_len) {
		++itm_count;
		this.Flush_reg();
		fil_wtr.Add_idx_direct(itm_len, Byte_.Zero);
		Io_stream_wtr wtr = null;
		try {
			wtr = Io_stream_wtr_.file_(fil_wtr.Fil_url());
			wtr.Open();
			fil_wtr.FlushIdx(wtr);
			wtr.Write(bry, itm_bgn, itm_end);
			wtr.Flush();
			fil_wtr.Clear();
			fil_wtr.Url_gen_add();
		}
		finally {if (wtr != null) wtr.Rls();}
	}
	private void Flush() {
		Flush_reg();
		Flush_fil();
	}
	private void Flush_reg() {
		reg_bfr
			.Add_int_variable(fil_count++).Add_byte(Byte_ascii.Pipe)
			.Add_bfr_and_preserve(reg_key_0).Add_byte(Byte_ascii.Pipe)
			.Add_bfr_and_preserve(reg_key_n).Add_byte(Byte_ascii.Pipe)
			.Add_int_variable(itm_count).Add_byte(Byte_ascii.NewLine);
		itm_count = 0;
		reg_key_0.Clear();
	}
	private void Flush_fil() {
		if (fil_wtr.Fil_idx() % 10 == 0)
			usr_dlg.Prog_many("cmd_site", "prog", "saving: ~{0} ~{1}", reg_url.OwnerDir().NameOnly(), fil_wtr.Fil_url().NameOnly());			
		fil_wtr.Flush(usr_dlg);
	}
}
