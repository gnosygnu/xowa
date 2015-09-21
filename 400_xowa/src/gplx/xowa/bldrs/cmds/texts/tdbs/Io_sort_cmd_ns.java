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
package gplx.xowa.bldrs.cmds.texts.tdbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.ios.*;
import gplx.xowa.tdbs.*; import gplx.xowa.tdbs.xdats.*;
public class Io_sort_cmd_ns implements Io_make_cmd {
	Xob_xdat_file_wtr fil_wtr; Bry_bfr reg_bfr = Bry_bfr.new_(), key_bfr_0 = Bry_bfr.new_(512), key_bfr_n = Bry_bfr.new_(512);
	int fil_count = 0, itm_count = 0;
	public Io_sort_cmd_ns(Gfo_usr_dlg usr_dlg) {this.usr_dlg = usr_dlg;} Gfo_usr_dlg usr_dlg;
	public int Trg_fil_max() {return trg_fil_max;} public Io_sort_cmd_ns Trg_fil_max_(int v) {trg_fil_max = v; return this;} private int trg_fil_max = 65 * Io_mgr.Len_kb;
	Io_url reg_url;
	public Io_sort_cmd Make_dir_(Io_url v) {make_dir = v; return this;} Io_url make_dir;
	public void Sort_bgn() {
		fil_count = itm_count = 0;
		fil_wtr = Xob_xdat_file_wtr.new_file_(trg_fil_max, make_dir);
		reg_url = make_dir.GenSubFil(Xotdb_dir_info_.Name_reg_fil);
	}
	public void Sort_do(Io_line_rdr rdr) {
		int itm_bgn = rdr.Itm_pos_bgn(), itm_end = rdr.Itm_pos_end(), key_bgn = rdr.Key_pos_bgn(), key_end = rdr.Key_pos_end();
		int itm_len = itm_end - itm_bgn;
		if (fil_wtr.FlushNeeded(itm_len)) Flush();
		byte[] bfr = rdr.Bfr();
		if (key_bfr_0.Len() == 0) {key_bfr_0.Add_mid(bfr, key_bgn, key_end);}
		key_bfr_n.Clear().Add_mid(bfr, key_bgn, key_end);
		fil_wtr.Bfr().Add_mid(rdr.Bfr(), itm_bgn, itm_end);
		fil_wtr.Add_idx(Byte_ascii.Null);
		++itm_count;
	}
	public void Sort_end() {
		Flush();
		Io_mgr.I.AppendFilBfr(reg_url, reg_bfr);
		//fil_wtr.Rls(); reg_bfr.Rls(); key_bfr_0.Rls(); key_bfr_n.Rls();
	}
	private void Flush() {
		reg_bfr
			.Add_int_variable(fil_count++).Add_byte(Byte_ascii.Pipe)
			.Add_bfr_and_preserve(key_bfr_0).Add_byte(Byte_ascii.Pipe)
			.Add_bfr_and_preserve(key_bfr_n).Add_byte(Byte_ascii.Pipe)
			.Add_int_variable(itm_count).Add_byte(Byte_ascii.Nl);
		itm_count = 0;
		key_bfr_0.Clear();
		if (fil_wtr.Fil_idx() % 10 == 0)
			usr_dlg.Prog_many("cmd_ns", "prog", "saving: ~{0} ~{1}", reg_url.OwnerDir().OwnerDir().NameOnly(), fil_wtr.Fil_url().NameOnly());			
		fil_wtr.Flush(usr_dlg);
	}
}
