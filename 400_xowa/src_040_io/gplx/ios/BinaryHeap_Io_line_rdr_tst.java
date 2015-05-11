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
public class BinaryHeap_Io_line_rdr_tst {
	BinaryHeap_Io_line_rdr_fxt fxt = new BinaryHeap_Io_line_rdr_fxt();
	@Test  public void Add() {
		fxt.Add("c", "a", "b").tst("a", "b", "c");
		fxt.Add("b", "a", "a").tst("a", "a", "b");
		fxt.Add("f", "b", "d", "c", "e", "a").tst("a", "b", "c", "d", "e", "f");
	}
}
class BinaryHeap_Io_line_rdr_fxt {
	BinaryHeap_Io_line_rdr heap = new BinaryHeap_Io_line_rdr(Io_sort_split_itm_sorter._); int file_total;
	public BinaryHeap_Io_line_rdr_fxt Add(String... ary) {
		file_total = ary.length;
		for (int i = 0; i < file_total; i++) {
			Io_url url = Io_url_.mem_fil_("mem/fil_" + ary[i] + ".txt");
			Io_mgr._.SaveFilStr(url, ary[i]);
			Io_line_rdr stream = new Io_line_rdr(Gfo_usr_dlg_.Test(), url);
			stream.Read_next();
			heap.Add(stream);
		}
		return this;
	}
	public BinaryHeap_Io_line_rdr_fxt tst(String... expd) {
		String[] actl = new String[file_total];
		for (int i = 0; i < actl.length; i++) {
			Io_line_rdr bfr = heap.Pop();
			actl[i] = String_.new_utf8_(bfr.Bfr(), 0, bfr.Bfr_len());
		}
		Tfds.Eq_ary_str(expd, actl);
		return this;
	}
}
