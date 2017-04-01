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
package gplx.xowa.wikis.xwikis.interwikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*;
public class Xow_interwiki_map {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	public int Len() {return hash.Len();}
	public Xow_interwiki_itm Get_at(int i) {return (Xow_interwiki_itm)hash.Get_at(i);}
	public void Add(byte[] key, byte[] domain, byte[] url) {
		Xow_interwiki_itm itm = new Xow_interwiki_itm(key, domain, url);
		hash.Add_if_dupe_use_nth(key, itm);
	}
}
