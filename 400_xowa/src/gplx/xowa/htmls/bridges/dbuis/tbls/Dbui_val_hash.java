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
package gplx.xowa.htmls.bridges.dbuis.tbls;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.BryUtl;
import gplx.types.errs.ErrUtl;
public class Dbui_val_hash {
	private final Ordered_hash hash = Ordered_hash_.New_bry();
	public void Add(byte[] key, Dbui_val_itm itm) {hash.Add(key, itm);}
	public byte[] Get_val_as_bry(String key) {return Get_val_as_bry(BryUtl.NewU8(key));}
	public byte[] Get_val_as_bry(byte[] key) {
		Dbui_val_itm itm = (Dbui_val_itm)hash.GetByOrNull(key); if (itm == null) throw ErrUtl.NewArgs("dbui.val_hash; unknown key", "key", key);
		return itm.Data();
	}
}
