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
package gplx.xowa.langs.durations; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xol_duration_itm {
	public Xol_duration_itm(byte tid, String name_str, long seconds) {
		this.tid = tid; this.seconds = seconds;
		this.name_str = name_str; this.name_bry = Bry_.new_a7(name_str);
	}
	public byte Tid() {return tid;} private byte tid;
	public byte[] Name_bry() {return name_bry;} private byte[] name_bry;
	public String Name_str() {return name_str;} private String name_str;
	public long Seconds() {return seconds;} private long seconds;
}
