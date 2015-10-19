/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
