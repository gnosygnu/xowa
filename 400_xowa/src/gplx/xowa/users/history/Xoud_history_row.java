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
package gplx.xowa.users.history; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
public class Xoud_history_row {
	public Xoud_history_row(int id, byte[] wiki, byte[] url, DateAdp time, int count) {
		this.id = id;
		this.wiki = wiki; this.url = url;
		this.time = time; this.count = count;
	}
	public int Id() {return id;} private final int id;
	public byte[] Wiki() {return wiki;} private final byte[] wiki;
	public byte[] Url()  {return url;} private final byte[] url;
	public DateAdp Time() {return time;} private final DateAdp time;
	public int Count() {return count;} private final int count;
}
