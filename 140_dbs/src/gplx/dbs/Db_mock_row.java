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
package gplx.dbs; import gplx.*;
public class Db_mock_row {
	public int Idx() {return idx;} public Db_mock_row Idx_(int val) {idx = val; return this;} int idx = -1;
	public Db_mock_cell[] Dat() {return dat;} Db_mock_cell[] dat = null;
	public static Db_mock_row vals_only_(Object... ary) {
		Db_mock_row rv = new Db_mock_row();
		int len = Array_.Len(ary);
		rv.dat = new Db_mock_cell[len];
		for (int i = 0; i < len; i++)
			rv.dat[i] = Db_mock_cell.new_().Val_(ary[i]);
		return rv;
	}
	public static Db_mock_row new_() {return new Db_mock_row();} Db_mock_row() {}
}
