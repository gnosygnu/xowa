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
package gplx.xowa.htmls.bridges.dbuis.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.bridges.*; import gplx.xowa.htmls.bridges.dbuis.*;
public class Dbui_col_itm {
	public Dbui_col_itm(int type, int width, String key, String display) {this.type = type; this.width = width; this.key = key; this.display = display;}
	public int Type() {return type;} private final int type;
	public String Key() {return key;} private final String key;
	public String Display() {return display;} private final String display;
	public int Width() {return width;} private final int width;
	public static final int Type_id_str = 1, Type_id_text = 2, Type_id_int = 3, Type_id_datetime = 4;
}
