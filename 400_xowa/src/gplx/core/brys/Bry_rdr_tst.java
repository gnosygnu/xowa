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
	private Bry_rdr rdr;
	public void Clear() {rdr = new Bry_rdr();}
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
