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
package gplx.core.texts; import gplx.*; import gplx.core.*;
public class StringTableColAlign {
	public int Val() {return val;} int val = 0;
	public static StringTableColAlign new_(int v) {
		StringTableColAlign rv = new StringTableColAlign();
		rv.val = v;
		return rv;
	}	StringTableColAlign() {}
	public static final StringTableColAlign Left = new_(0);
	public static final StringTableColAlign Mid = new_(1);
	public static final StringTableColAlign Right = new_(2);
}
