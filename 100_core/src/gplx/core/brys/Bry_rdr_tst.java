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
package gplx.core.brys; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Bry_rdr_tst {
	@Before public void init() {fxt.Clear();} private Bry_rdr_fxt fxt = new Bry_rdr_fxt();
	@Test   public void Int() {
		fxt.Init_src("12|3456|789");
		fxt.Test_read_int(12);
		fxt.Test_read_int(3456);
		fxt.Test_read_int(789);
		fxt.Test_read_int(Int_.Min_value);
	}
	@Test   public void Int_negative() {
		fxt.Init_src("-1|-2");
		fxt.Test_read_int(-1);
		fxt.Test_read_int(-2);
	}
	@Test   public void Bry() {
		fxt.Init_src("abc|d||ef");
		fxt.Test_read_bry("abc");
		fxt.Test_read_bry("d");
		fxt.Test_read_bry("");
		fxt.Test_read_bry("ef");
		fxt.Test_read_bry(null);
	}
}
class Bry_rdr_fxt {
	private Bry_rdr_old rdr;
	public void Clear() {rdr = new Bry_rdr_old();}
	public Bry_rdr_fxt Init_src(String v)	{rdr.Init(Bry_.new_u8(v)); return this;}
	public Bry_rdr_fxt Init_pos(int v)		{rdr.Pos_(v); return this;}
	public void Test_read_int(int expd_val) {
		Tfds.Eq(expd_val, rdr.Read_int_to_pipe());
	}
	public void Test_read_bry(String expd_str) {
		byte[] actl_bry = rdr.Read_bry_to_pipe();
		String actl_str = actl_bry == null ? null : String_.new_u8(actl_bry);
		Tfds.Eq(expd_str, actl_str);
	}
}
