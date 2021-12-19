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
package gplx.dbs.metas;
import gplx.types.basics.utls.StringUtl;
public class Dbmeta_itm_tid {
	public static final int Tid_unknown = 0, Tid_table = 1, Tid_index = 2;
	public static final String Key_table = "table", Key_index = "index";
	public static int Xto_int(String s) {
		s = StringUtl.Lower(s);
		if		(StringUtl.Eq(s, Key_table))	return Tid_table;
		else if (StringUtl.Eq(s, Key_index))	return Tid_index;
		else								return Tid_unknown;
	}
}
