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
public class Io_sort_split_itm {
	public Io_sort_split_itm() {}
	public Io_sort_split_itm(Io_line_rdr rdr) {Set(rdr);}
	public int Row_bgn() {return row_bgn;} private int row_bgn;
	public int Row_end() {return row_end;} private int row_end;
	public int Key_bgn() {return key_bgn;} private int key_bgn;
	public int Key_end() {return key_end;} private int key_end;
	public byte[] Bfr() {return bfr;} private byte[] bfr;
	public void Set(Io_line_rdr rdr) {
		bfr = rdr.Bfr();
		row_bgn = rdr.Itm_pos_bgn();
		row_end = rdr.Itm_pos_end();
		key_bgn = rdr.Key_pos_bgn();
		key_end = rdr.Key_pos_end();
	}
	public void Rls() {bfr = null;}
}
