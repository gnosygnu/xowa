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
import gplx.types.basics.utls.StringUtl;
class Site_showhook_itm implements ToStrAble {
	public Site_showhook_itm(byte[] name, byte[] scribunto, byte[][] subscribers) {
		this.name = name; this.scribunto = scribunto; this.subscribers = subscribers;
	}
	public byte[] Name() {return name;} private final byte[] name;
	public byte[] Scribunto() {return scribunto;} private final byte[] scribunto;
	public byte[][] Subscribers() {return subscribers;} private final byte[][] subscribers;
	public String ToStr() {return StringUtl.ConcatWithObj("|", name, scribunto, StringUtl.ConcatWithObj(";", (Object[])subscribers));}
}
