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
package gplx.xowa.htmls.core.wkrs.xndes.dicts; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
public class Xoh_xnde_dict_itm {
	public Xoh_xnde_dict_itm(int id, byte[] val) {this.id = id; this.val = val; count = 1;}
	public int Id() {return id;} private final int id;
	public byte[] Val() {return val;} private final byte[] val;
	public int Count() {return count;} private int count;
	public void Count_add_1() {++count;}
	public void Save(Xoh_hzip_bfr bfr, int id_len) {
		bfr.Add_hzip_int(id_len, id);
		bfr.Add(val).Add_byte_nl();
	}
}
