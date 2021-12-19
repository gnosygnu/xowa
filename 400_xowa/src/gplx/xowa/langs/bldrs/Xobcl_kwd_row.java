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
package gplx.xowa.langs.bldrs;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
public class Xobcl_kwd_row {
	public Xobcl_kwd_row(byte[] key, byte[][] itms) {
		this.key = key; this.itms = itms;
		for (byte[] itm : itms)
			itms_hash.Add(itm, itm);
	} 
	public byte[] Key() {return key;} private byte[] key;
	public byte[][] Itms() {return itms;} private byte[][] itms;
	public boolean Itms_has(byte[] key) {return itms_hash.Has(key);} private Ordered_hash itms_hash = Ordered_hash_.New_bry();
}
