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
package gplx.xowa.guis.views; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*;
public class Xog_tab_itm_ {
	public static String Tab_name_min(String name, int min) {
		int name_len = String_.Len(name);
		return min == Tab_name_min_disabled || name_len > min ? name : name + String_.Repeat(" ", min - name_len);
	}
	public static String Tab_name_max(String name, int max) {
		int name_len = String_.Len(name);
		return max == Tab_name_max_disabled || name_len <= max ? name : String_.Mid(name, 0, max) + "...";
	}
	public static final int Tab_name_min_disabled = -1, Tab_name_max_disabled = -1;
	public static final    Xog_tab_itm Null = null;
}
