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
public class Bit_heap_rdr_tst {
	private final Bit_heap_rdr_fxt fxt = new Bit_heap_rdr_fxt();
	@Test   public void Get_bool() {
		fxt.Load(255);
		fxt.Test__get_bool(Bool_.Y).Test__cur(127, 1, 0);
		fxt.Test__get_bool(Bool_.Y).Test__cur( 63, 2, 0);
		fxt.Test__get_bool(Bool_.Y).Test__cur( 31, 3, 0);
		fxt.Test__get_bool(Bool_.Y).Test__cur( 15, 4, 0);
		fxt.Test__get_bool(Bool_.Y).Test__cur(  7, 5, 0);
		fxt.Test__get_bool(Bool_.Y).Test__cur(  3, 6, 0);
		fxt.Test__get_bool(Bool_.Y).Test__cur(  1, 7, 0);
		fxt.Test__get_bool(Bool_.Y).Test__cur(  0, 0, 1);
		fxt.Load(0);
		fxt.Test__get_bool(Bool_.N).Test__cur(  0, 1, 0);
		fxt.Load(6);
		fxt.Test__get_bool(Bool_.N).Test__cur(  3, 1, 0);
		fxt.Test__get_bool(Bool_.Y).Test__cur(  1, 2, 0);
		fxt.Test__get_bool(Bool_.Y).Test__cur(  0, 3, 0);
	}
	@Test   public void Get_byte() {
		fxt.Load(255).Test__get_byte(2, 3).Test__cur(63, 2, 0);
		fxt.Load(255, 3);
		fxt.Test__get_byte(7, 127);
		fxt.Test__get_byte(3,   7);
		fxt.Load(10);
		fxt.Test__get_bool(false);
		fxt.Test__get_byte(3, 5);
	}
	@Test   public void Get_int_hzip() {
		fxt.Load(100).Test__get_int_hzip(1, 100).Test__cur(0, 0, 1);
		fxt.Load(253, 1, 44).Test__get_int_hzip(1, 300).Test__cur(0, 0, 3);
		fxt.Load(0, 100).Test__get_int_hzip(2, 100).Test__cur(0, 0, 2);
		fxt.Load(250, 3, 88, 0).Test__get_bool(Bool_.N).Test__get_int_hzip(1, 300).Test__cur(0, 1, 3);
	}
}
class Bit_heap_rdr_fxt {
	private final Bit_heap_rdr heap = new Bit_heap_rdr();
	public Bit_heap_rdr_fxt Load(int... src) {
		byte[] bry = Bry_.New_by_ints(src);
		heap.Load(bry, 0, bry.length);
		return this;
	}
	public Bit_heap_rdr_fxt Test__get_bool(boolean expd) {
		Tfds.Eq_bool(expd, heap.Get_bool());
		return this;
	}
	public Bit_heap_rdr_fxt Test__get_byte(int bits, int expd) {
		Tfds.Eq_byte((byte)expd, heap.Get_byte(bits));
		return this;
	}
	public Bit_heap_rdr_fxt Test__get_int_hzip(int reqd, int expd) {
		Tfds.Eq_int(expd, heap.Get_int_hzip(reqd));
		return this;
	}
	public Bit_heap_rdr_fxt Test__cur(int cur_val, int cur_bits, int cur_pos) {
		Tfds.Eq_int(cur_val, heap.Cur_val(), "val");
		Tfds.Eq_int(cur_bits, heap.Cur_bits(), "bits");
		Tfds.Eq_int(cur_pos, heap.Cur_pos(), "pos");
		return this;
	}
}
