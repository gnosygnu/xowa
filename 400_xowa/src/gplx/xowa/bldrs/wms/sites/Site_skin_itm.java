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
class Site_skin_itm implements ToStrAble {
	public Site_skin_itm(byte[] code, boolean dflt, byte[] name, boolean unusable) {
		this.code = code; this.dflt = dflt; this.name = name; this.unusable = unusable;
	}
	public byte[] Code() {return code;} private final byte[] code;
	public boolean Dflt() {return dflt;} private final boolean dflt;
	public byte[] Name() {return name;} private final byte[] name;
	public boolean Unusable() {return unusable;} private final boolean unusable;
	public String ToStr() {return StringUtl.ConcatWithObj("|", code, dflt, name, unusable);}
}
