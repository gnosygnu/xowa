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
package gplx;
import org.junit.*;
public class ByteTrieItm_slim_tst {
	private ByteTrieItm_slim itm = new ByteTrieItm_slim(Byte_.Zero, null, false);
	@Before public void init() {itm.Clear();}
	@Test  public void Find_nil() {
		tst_Find(Byte_ascii.Ltr_a, null);
	}
	@Test  public void Add_one() {
		run_Add(Byte_ascii.Ltr_a);
		tst_Find(Byte_ascii.Ltr_a, "a");
	}
	@Test  public void Add_many() {
		run_Add(Byte_ascii.Bang, Byte_ascii.Num_0, Byte_ascii.Ltr_a, Byte_ascii.Ltr_B);
		tst_Find(Byte_ascii.Ltr_a, "a");
	}
	@Test  public void Del() {
		run_Add(Byte_ascii.Bang, Byte_ascii.Num_0, Byte_ascii.Ltr_a, Byte_ascii.Ltr_B);
		tst_Find(Byte_ascii.Ltr_a, "a");
		run_Del(Byte_ascii.Ltr_a);
		tst_Find(Byte_ascii.Ltr_a, null);
		tst_Find(Byte_ascii.Num_0, "0");
		tst_Find(Byte_ascii.Ltr_B, "B");
	}
	private void tst_Find(byte b, String expd) {
		ByteTrieItm_slim actl_itm = itm.Ary_find(b);
		Object actl = actl_itm == null ? null : actl_itm.Val();
		Tfds.Eq(expd, actl);
	}
	private void run_Add(byte... ary) {for (byte b : ary) itm.Ary_add(b, Char_.XtoStr((char)b));}
	private void run_Del(byte... ary) {for (byte b : ary) itm.Ary_del(b);}
}
