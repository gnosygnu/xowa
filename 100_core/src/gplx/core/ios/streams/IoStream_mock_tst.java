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
