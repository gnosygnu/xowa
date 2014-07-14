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
import gplx.xowa.parsers.lnkis.*; import gplx.xowa.files.*;
class Xofo_lnki {
	public byte[] Name() {return name;} private byte[] name;
	public byte Lnki_type() {return lnki_type;} public Xofo_lnki Lnki_type_(byte v) {lnki_type = v; return this;} private byte lnki_type;
	public int Lnki_w() {return lnki_w;} public Xofo_lnki Lnki_w_(int v) {lnki_w = v; return this;} private int lnki_w;
	public int Lnki_h() {return lnki_h;} public Xofo_lnki Lnki_h_(int v) {lnki_h = v; return this;} private int lnki_h;
	public double Lnki_upright() {return lnki_upright;} public Xofo_lnki Lnki_upright_(double v) {lnki_upright = v; return this;} double lnki_upright = Xop_lnki_tkn.Upright_null;
	public double Lnki_thumbtime() {return lnki_thumbtime;} public Xofo_lnki Lnki_thumbtime_(double v) {lnki_thumbtime = v; return this;} private double lnki_thumbtime = Xof_doc_thumb.Null;
	public byte Xfer_status() {return xfer_status;} public Xofo_lnki Xfer_status_(byte v) {xfer_status = v; return this;} private byte xfer_status; public static final byte Xfer_status_none = 0, Xfer_status_xfer_pass = 1, Xfer_status_xfer_fail = 2, Xfer_status_convert_pass = 3, Xfer_status_convert_fail = 4;
	public Xofo_lnki Xfer_status_(boolean v) {return Xfer_status_(v ? Xfer_status_xfer_pass : Xfer_status_xfer_fail);}
	public boolean Orig() {return !thumb;}
	public boolean Thumb() {return thumb;} public Xofo_lnki Thumb_(boolean v) {thumb = v; return this;} private boolean thumb;
	public int File_w() {return file_w;} public Xofo_lnki File_w_(int v) {file_w = v; return this;} private int file_w;
	public int File_h() {return file_h;} public Xofo_lnki File_h_(int v) {file_h = v; return this;} private int file_h;
	public void Calc_size_(int w, int h) {file_w = w; file_h = h;}
	public void Load_by_html_wtr(byte typ, int w, int h, double lnki_upright) {this.lnki_type = typ; this.lnki_w = w; this.lnki_h = h; this.lnki_upright = lnki_upright;}
	public Xofo_lnki Load_link_rdr(Gfo_fld_rdr fld_rdr, Io_line_rdr rdr, Xofo_lnki_parser lnki_parser) {
		fld_rdr.Ini(rdr.Bfr(), rdr.Itm_pos_bgn());
		name 			= fld_rdr.Read_bry_escape();
		Xofo_lnki tmp = lnki_parser.Parse_ary(rdr.Bfr(), fld_rdr.Pos(), rdr.Itm_pos_end())[0];
		lnki_type 		= tmp.Lnki_type();
		lnki_w 			= tmp.Lnki_w();
		lnki_h 			= tmp.Lnki_h();
		lnki_upright 	= tmp.Lnki_upright();
		return this;
	}
	public static void Write(Gfo_fld_wtr wtr, byte[] ttl, Xop_lnki_tkn lnki) {
		wtr.Write_bry_escape_fld(ttl);
		wtr.Bfr()
			.Add_int_variable(lnki.Lnki_type()).Add_byte(Byte_ascii.Comma)
			.Add_int_variable(lnki.Lnki_w()).Add_byte(Byte_ascii.Comma)
			.Add_int_variable(lnki.Lnki_h())
			;
		boolean upright_exists = lnki.Upright() != Xop_lnki_tkn.Upright_null;
		if (upright_exists) {
			wtr.Bfr().Add_byte(Byte_ascii.Comma)
				.Add(Xop_lnki_arg_parser.Bry_upright).Add_byte(Byte_ascii.Eq).Add_double(lnki.Upright());
		}
		wtr.Write_dlm_row();
	}
	public static final Xofo_lnki[] Ary_empty = new Xofo_lnki[0];
}
