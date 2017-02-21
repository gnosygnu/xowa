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
package gplx.core.ios; import gplx.*; import gplx.core.*;
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
	BinaryHeap_Io_line_rdr heap = new BinaryHeap_Io_line_rdr(Io_sort_split_itm_sorter.Instance); int file_total;
	public BinaryHeap_Io_line_rdr_fxt Add(String... ary) {
		file_total = ary.length;
		for (int i = 0; i < file_total; i++) {
			Io_url url = Io_url_.mem_fil_("mem/fil_" + ary[i] + ".txt");
			Io_mgr.Instance.SaveFilStr(url, ary[i]);
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
			actl[i] = String_.new_u8(bfr.Bfr(), 0, bfr.Bfr_len());
		}
		Tfds.Eq_ary_str(expd, actl);
		return this;
	}
}
