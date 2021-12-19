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
package gplx.xowa.bldrs.wms.sites;
import gplx.frameworks.objects.ToStrAble;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
class Site_namespacealias_itm implements ToStrAble {
	public Site_namespacealias_itm(int id, byte[] alias) {
		this.id = id; this.alias = alias;
		this.key = BryUtl.AddWithDlm(AsciiByte.Pipe, IntUtl.ToBry(id), alias);
	}
	public byte[] Key() {return key;} private final byte[] key;
	public int Id() {return id;} private final int id;
	public byte[] Alias() {return alias;} private final byte[] alias;
	public String ToStr() {return StringUtl.ConcatWithObj("|", id, alias);}
}
