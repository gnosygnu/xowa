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
public class Dbui_btn_itm {
	public Dbui_btn_itm(String cmd, String img, String text) {this.cmd = cmd; this.img = img; this.text = text;}
	public String Key() {return text;}
	public String Cmd() {return cmd;} private final String cmd;
	public String Img() {return img;} private final String img;
	public String Text() {return text;} private final String text;
	public static final Dbui_btn_itm[] Ary_empty = new Dbui_btn_itm[0];
}
