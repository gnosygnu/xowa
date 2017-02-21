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
package gplx.xowa.apps.apis.xowa.addons.searchs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.apis.*; import gplx.xowa.apps.apis.xowa.*; import gplx.xowa.apps.apis.xowa.addons.*;
public class Xoapi_search_mode_ {
	public static final int Tid__title_full = 0, Tid__title_word = 1;
	public static final String Str__title_full = "title.full", Str__title_word = "title.word"; 
	public static String To_str(int v) {
		switch (v) {
			case Tid__title_full:	return Str__title_full;
			case Tid__title_word:	return Str__title_word;
			default:				throw Err_.new_unhandled_default(v);
		}
	}
	public static int To_int(String v) {
		if		(String_.Eq(v, Str__title_full))	return Tid__title_full;
		else if	(String_.Eq(v, Str__title_word))	return Tid__title_word;
		else										throw Err_.new_unhandled_default(v);
	}
}
