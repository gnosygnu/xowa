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
package gplx.core.brys; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Bit_heap_wtr_tst {
	private final Bit_heap_wtr_fxt fxt = new Bit_heap_wtr_fxt();
	@Test   public void Add_bool() {
		fxt.Clear().Add_bool(Bool_.Y).Test__vals(1, 1);
		fxt.Clear().Add_bool(Bool_.N).Test__vals(1, 0);
		fxt.Clear().Add_bool(Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y).Test__vals(4,  15);
		fxt.Clear().Add_bool(Bool_.Y, Bool_.N, Bool_.N, Bool_.Y).Test__vals(4,   9);
		fxt.Clear().Add_bool(8, Bool_.Y).Test__vals(0, 0, 255);
	}
	@Test   public void Add_byte() {
		fxt.Clear().Add_byte(255).Test__vals(0, 0, 255);
	}
	@Test   public void Add_bool_byte() {
		fxt.Clear().Add_bool(Bool_.N).Add_byte(255).Test__vals(1, 127, 254);
		fxt.Clear().Add_bool(Bool_.Y).Add_byte(255).Test__vals(1, 127, 255);
		fxt.Clear().Add_bool(Bool_.Y, Bool_.Y, Bool_.Y, Bool_.Y).Add_byte(255).Test__vals(4, 15, 255);
	}
	@Test   public void Add_byte_digits() {
		fxt.Clear().Add_byte(4,  15).Test__vals(4,  15);
		fxt.Clear().Add_byte(7, 127).Add_byte(2, 3).Test__vals(1, 1, 255);

		fxt.Clear().Add_byte(3, 7).Add_byte(3, 7).Test__vals(6, 63);
		fxt.Clear().Add_byte(6, 63).Add_byte(3, 7).Test__vals(1, 1, 255);

		fxt.Clear().Add_byte(3, 6).Add_byte(3, 6).Test__vals(6, 54);
	}
	@Test   public void Add_int_hzip() {
		fxt.Clear().Add_int_hzip(1, 100).Test__vals(0, 0, 100);
		fxt.Clear().Add_int_hzip(1, 300).Test__vals(0, 0, 253, 1, 44);
		fxt.Clear().Add_int_hzip(2, 100).Test__vals(0, 0, 0, 100);
		fxt.Clear().Add_bool(Bool_.N).Add_int_hzip(1, 300).Test__vals(1, 0, 250, 3, 88);
	}
}
class Bit_heap_wtr_fxt {
	private final Bit_heap_wtr heap = new Bit_heap_wtr();
	public Bit_heap_wtr_fxt Clear() {heap.Clear(); return this;}
	public Bit_heap_wtr_fxt Add_bool(int len, boolean v) {
		boolean[] ary = new boolean[len];
		for (int i = 0; i < len; ++i)
			ary[i] = v;
		Add_bool(ary);
		return this;
	}
	public Bit_heap_wtr_fxt Add_bool(boolean... v) {
		int len = v.length;
		for (int i = 0; i < len; ++i)
			heap.Add_bool(v[i]);
		return this;
	}
	public Bit_heap_wtr_fxt Add_byte(int... v) {
		int len = v.length;
		for (int i = 0; i < len; ++i)
			heap.Add_byte((byte)v[i]);
		return this;
	}
	public Bit_heap_wtr_fxt Add_byte(int bits, int val) {
		heap.Add_byte(bits, (byte)val);
		return this;
	}
	public Bit_heap_wtr_fxt Add_int_hzip(int reqd, int val) {
		heap.Add_int_hzip(reqd, val);
		return this;
	}
	public void Test__vals(int expd_cur_bits, int expd_cur, int... expd_ary) {
		Tfds.Eq_int	(expd_cur_bits				, heap.Cur_bits()		, "cur_bits");
		Tfds.Eq_int	(expd_cur					, heap.Cur()			, "cur");
		Tfds.Eq_ary	(Bry_.New_by_ints(expd_ary)	, heap.Heap().To_bry()	, "heap");
	}
}
