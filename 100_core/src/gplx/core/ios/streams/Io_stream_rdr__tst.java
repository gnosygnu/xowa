/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.ios.streams; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
import org.junit.*;
public class Io_stream_rdr__tst {
	@Before public void init() {fxt.Clear();} private Io_stream_rdr__fxt fxt = new Io_stream_rdr__fxt();
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
class Io_stream_rdr__fxt {
	private java.io.InputStream stream;	
	private int stream_bry_len;
	public void Clear() {
		expd_bytes_read = Int_.Min_value;
	}
	public Io_stream_rdr__fxt Expd_bytes_read(int v) {expd_bytes_read = v; return this;} private int expd_bytes_read = Int_.Min_value;
	public Io_stream_rdr__fxt Init_stream(String v) {
		byte[] stream_bry = Bry_.new_a7(v);
		stream_bry_len = stream_bry.length;
		stream = Io_stream_rdr_.New__mem_as_stream(stream_bry);
		return this;
	}
	public Io_stream_rdr__fxt Test_read(int bgn, int len, String expd_str) {
		byte[] bfr = new byte[stream_bry_len];	// allocate whole stream; may not use it all
		int actl_bytes_read = Io_stream_rdr_.Read_by_parts(stream, 8, bfr, bgn, len);
		Tfds.Eq(expd_bytes_read, actl_bytes_read, "bytes_read");
		Tfds.Eq(expd_str, String_.new_u8(bfr, bgn, bgn + actl_bytes_read), "str");
		return this;
	}
	public void Rls() {
		Io_stream_rdr_.Close(stream);
	}
}
