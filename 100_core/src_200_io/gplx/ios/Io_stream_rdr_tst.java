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
public class Io_stream_rdr_tst {
	@Before public void init() {fxt.Clear();} private Io_stream_rdr_fxt fxt = new Io_stream_rdr_fxt();
	@After public void term() {fxt.Rls();}
	@Test  public void Bz2_read() {
		fxt	.Init_stream("abcd")	// read everything at once
			.Expd_bytes_read(4).Test_read(0, 4, "abcd");
		fxt	.Init_stream("abcd")	// read in steps
			.Expd_bytes_read(1).Test_read(0, 1, "a")
			.Expd_bytes_read(2).Test_read(1, 2, "bc")
			.Expd_bytes_read(1).Test_read(3, 1, "d")
			;		
	}
}
class Io_stream_rdr_fxt {
	private java.io.InputStream stream;	
	private int stream_bry_len;
	public void Clear() {
		expd_bytes_read = Int_.Min_value;
	}
	public Io_stream_rdr_fxt Expd_bytes_read(int v) {expd_bytes_read = v; return this;} private int expd_bytes_read = Int_.Min_value;
	public Io_stream_rdr_fxt Init_stream(String v) {
		byte[] stream_bry = Bry_.new_a7(v);
		stream_bry_len = stream_bry.length;
		stream = Io_stream_rdr_.Stream_new_mem(stream_bry);
		return this;
	}
	public Io_stream_rdr_fxt Test_read(int bgn, int len, String expd_str) {
		byte[] bfr = new byte[stream_bry_len];	// allocate whole stream; may not use it all
		int actl_bytes_read = Io_stream_rdr_.Stream_read_by_parts(stream, 8, bfr, bgn, len);
		Tfds.Eq(expd_bytes_read, actl_bytes_read, "bytes_read");
		Tfds.Eq(expd_str, String_.new_u8(bfr, bgn, bgn + actl_bytes_read), "str");
		return this;
	}
	public void Rls() {
		Io_stream_rdr_.Stream_close(stream);
	}
}
