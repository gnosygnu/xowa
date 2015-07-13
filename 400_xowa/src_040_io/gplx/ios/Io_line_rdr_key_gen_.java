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
package gplx.ios; import gplx.*;
public class Io_line_rdr_key_gen_ {
	public static final Io_line_rdr_key_gen first_pipe	= new Io_line_rdr_key_gen_first(Byte_ascii.Pipe);
	public static final Io_line_rdr_key_gen last_pipe	= new Io_line_rdr_key_gen_last(Byte_ascii.Pipe);
	public static final Io_line_rdr_key_gen noop			= new Io_line_rdr_key_gen_noop();
}
class Io_line_rdr_key_gen_last implements Io_line_rdr_key_gen {
	public Io_line_rdr_key_gen_last(byte fld_dlm) {this.fld_dlm = fld_dlm;} private byte fld_dlm;
	public void Gen(Io_line_rdr bfr) {
		int bgn = bfr.Itm_pos_bgn(), end = bfr.Itm_pos_end() - 1;		// -1: ignore row_dlm
		bfr.Key_pos_bgn_(end).Key_pos_end_(end);
		byte[] bry = bfr.Bfr();
		for (int i = end; i >= bgn; i--) {
			if (bry[i] == fld_dlm) {
				bfr.Key_pos_bgn_(i + 1);	// +1 to position after fldDlm
				return;
			}
		}
		bfr.Key_pos_bgn_(0);	// nothing found; position at bgn
	}
}
class Io_line_rdr_key_gen_first implements Io_line_rdr_key_gen {
	public Io_line_rdr_key_gen_first(byte fld_dlm) {this.fld_dlm = fld_dlm;} private byte fld_dlm;
	public void Gen(Io_line_rdr bfr) {
		int bgn = bfr.Itm_pos_bgn(), end = bfr.Itm_pos_end();
		bfr.Key_pos_bgn_(bgn).Key_pos_end_(bgn);
		byte[] bry = bfr.Bfr();
		for (int i = bgn; i < end; i++) {
			if (bry[i] == fld_dlm) {
				bfr.Key_pos_end_(i);
				return;
			}
		}
		bfr.Key_pos_end_(end);	// nothing found; position at end
	}
}
class Io_line_rdr_key_gen_noop implements Io_line_rdr_key_gen {
	public void Gen(Io_line_rdr bfr) {}
}
