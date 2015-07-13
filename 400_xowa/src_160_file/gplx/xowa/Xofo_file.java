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
import gplx.core.flds.*; import gplx.ios.*; import gplx.gfui.*;
class Xofo_file {
	public byte[] Name() {return name;} public Xofo_file Name_(byte[] v) {name = v; return this;}  byte[] name = Bry_.Empty;
	public byte[] Redirect() {return redirect;} private byte[] redirect = Bry_.Empty; public boolean Redirect_exists() {return redirect.length > 0;}
	public int Orig_w() {return orig_w;} private int orig_w;
	public int Orig_h() {return orig_h;} private int orig_h;
	public int Orig_size() {return orig_size;} private int orig_size;
	public int Bits() {return bits;} private int bits;
	public int Repo_id() {return repo_id;} public Xofo_file Repo_id_(int v) {this.repo_id = v; return this;} private int repo_id = -1;
	public byte[] Status_msg() {return status_msg;} public Xofo_file Status_msg_(byte[] v) {status_msg = v; return this;} private byte[] status_msg = Bry_.Empty;
	public int[] Thumbs() {return (int[])thumbs.To_ary(int.class);} private Ordered_hash thumbs = Ordered_hash_.new_();
	public Xofo_lnki[] Links() {return lnkis;} private Xofo_lnki[] lnkis = Xofo_lnki.Ary_empty; int links_len;
	public void Links_(List_adp list) {
		lnkis = (Xofo_lnki[])list.To_ary(Xofo_lnki.class);
		links_len = lnkis.length;
		list.Clear();
	}
	public void Write(boolean reverse, Gfo_fld_wtr wtr) {
		Write_main(reverse, wtr);
		wtr.Write_dlm_row();
	}
	public void Write_bldr(Gfo_fld_wtr wtr) {
		Write_main(false, wtr);
		wtr.Write_int_variable_fld(repo_id);
		wtr.Write_bry_escape_fld(status_msg);
		Xofo_lnki[] lnkis = this.Links();
		int links_len = lnkis.length;
		Bry_bfr bfr = wtr.Bfr();
		for (int i = 0; i < links_len; i++) {
			if (i != 0) bfr.Add_byte(Byte_ascii.Semic);
			Xofo_lnki lnki = lnkis[i];
			bfr .Add_int_variable(lnki.Lnki_type())		.Add_byte(Byte_ascii.Comma)
				.Add_int_variable(lnki.Lnki_w())		.Add_byte(Byte_ascii.Comma)
				.Add_int_variable(lnki.Lnki_h());
				if (lnki.Lnki_upright() != Xop_lnki_tkn.Upright_null)
					bfr.Add_byte(Byte_ascii.Comma).Add(Xop_lnki_arg_parser.Bry_upright).Add_byte(Byte_ascii.Eq).Add_double(lnki.Lnki_upright());
		}
		wtr.Write_dlm_row();
	}
	private void Write_main(boolean reverse, Gfo_fld_wtr wtr) {
		if (reverse) {
			wtr.Write_bry_escape_fld(redirect);
			wtr.Write_bry_escape_fld(name);			
		}
		else {
			wtr.Write_bry_escape_fld(name);
			wtr.Write_bry_escape_fld(redirect);
		}
		wtr.Write_bry_escape_fld(name_ext);
		wtr.Write_int_variable_fld(orig_size);
		wtr.Write_int_variable_fld(orig_w);
		wtr.Write_int_variable_fld(orig_h);
		wtr.Write_int_variable_fld(bits);		
	}
	public Xofo_file Load_by_xfer_rdr(Gfo_fld_rdr fld_parser, Xofo_lnki_parser lnki_parser, Io_line_rdr rdr, List_adp link_list) {
		byte[] bfr = rdr.Bfr();
		fld_parser.Ini(bfr, rdr.Itm_pos_bgn());
		Load_ttl_atrs(fld_parser);
		Load_sql_atrs(fld_parser);
		repo_id = fld_parser.Read_int();
		status_msg = fld_parser.Read_bry_escape();
		lnkis = lnki_parser.Parse_ary(fld_parser.Data(), fld_parser.Pos(), rdr.Itm_pos_end() - 1);	// -1 to ignore closing \n
		return this;
	}
	public void Load_by_name_rdr(Gfo_fld_rdr fld_parser, Io_line_rdr rdr) {
		fld_parser.Ini(rdr.Bfr(), rdr.Itm_pos_bgn());
		Load_ttl_atrs(fld_parser);
	}
	public void Load_by_size_rdr(Gfo_fld_rdr fld_parser, Io_line_rdr rdr) {
		fld_parser.Ini(rdr.Bfr(), rdr.Itm_pos_bgn());
		fld_parser.Move_next_escaped();
		Load_sql_atrs(fld_parser);
	}
	public Xofo_file Load_by_merge_rdr(Gfo_fld_rdr fld_parser, Io_line_rdr rdr) {
		fld_parser.Ini(rdr.Bfr(), rdr.Itm_pos_bgn());
		Load_ttl_atrs(fld_parser);
		Load_sql_atrs(fld_parser);
		return this;
	}
	private void Load_ttl_atrs(Gfo_fld_rdr fld_parser) {
		name = fld_parser.Read_bry_escape();
		redirect = fld_parser.Read_bry_escape();		
	}
	private void Load_sql_atrs(Gfo_fld_rdr fld_parser) {
		name_ext = fld_parser.Read_bry_escape();
		orig_size = fld_parser.Read_int();
		orig_w = fld_parser.Read_int();
		orig_h = fld_parser.Read_int();
		bits = fld_parser.Read_int();
	}	byte[] name_ext = Bry_.Empty;
}
