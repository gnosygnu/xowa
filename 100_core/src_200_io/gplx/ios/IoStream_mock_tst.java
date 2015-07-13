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
import org.junit.*;
public class IoStream_mock_tst {
	@Before public void init() {fxt.Clear();} IoStream_mock_fxt fxt = new IoStream_mock_fxt();
	@Test   public void Basic() {
		fxt.Init_src_str_("abcde").Init_trg_len_(5).Init_rdr_limit_(2).Init_read_len_(2);
		fxt.Test_read("ab").Test_read("cd").Test_read("e");
	}
	@Test   public void Read_limit() {
		fxt.Init_src_str_("abcde").Init_trg_len_(5).Init_rdr_limit_(2).Init_read_len_(4);
		fxt.Test_read("ab").Test_read("cd").Test_read("e");
	}
}
class IoStream_mock_fxt {
	public void Clear() {
		if (rdr == null)
			rdr = new IoStream_mock();
		rdr.Reset();
		trg_bgn = 0;
	}	IoStream_mock rdr; byte[] trg_bry;
	public IoStream_mock_fxt Init_src_str_(String v) {rdr.Data_bry_(Bry_.new_a7(v)); return this;}
	public IoStream_mock_fxt Init_trg_len_(int v) {trg_bry = new byte[v]; return this;} 
	public IoStream_mock_fxt Init_read_len_(int v) {read_len = v; return this;} int read_len; 
	public IoStream_mock_fxt Init_rdr_limit_(int v) {rdr.Read_limit_(v); return this;} 
	public IoStream_mock_fxt Test_read(String expd) {
		int bytes_read = rdr.Read(trg_bry, trg_bgn, read_len);
		Tfds.Eq(expd, String_.new_a7(trg_bry, trg_bgn, trg_bgn + bytes_read));
		trg_bgn += bytes_read;
		return this;
	}	int trg_bgn;
}
