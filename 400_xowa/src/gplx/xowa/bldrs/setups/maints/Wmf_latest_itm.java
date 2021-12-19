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
package gplx.xowa.bldrs.setups.maints;
import gplx.types.commons.GfoDate;
public class Wmf_latest_itm {
	public Wmf_latest_itm(byte[] name, GfoDate date, long size) {
		this.name = name; this.date = date; this.size = size;
	}
	public byte[] Name() {return name;} private final byte[] name;
	public GfoDate Date() {return date;} private final GfoDate date;
	public long Size() {return size;} private final long size;
}
