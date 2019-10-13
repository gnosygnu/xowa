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
package gplx.xowa.wikis.nss; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xow_ns_mgr_name_itm {
	public Xow_ns_mgr_name_itm(Xow_ns ns, byte[] name) {
		this.ns = ns;
		this.name = name;
		this.name_len = name.length;
	}
	public Xow_ns Ns() {return ns;} private final    Xow_ns ns;
	public byte[] Name() {return name;} private final    byte[] name;
	public int Name_len() {return name_len;} private final    int name_len;
}
