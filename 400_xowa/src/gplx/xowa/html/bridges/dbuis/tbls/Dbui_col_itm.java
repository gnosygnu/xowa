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
package gplx.xowa.html.bridges.dbuis.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.bridges.*; import gplx.xowa.html.bridges.dbuis.*;
public class Dbui_col_itm {
	public Dbui_col_itm(int type, int width, String key, String display) {this.type = type; this.width = width; this.key = key; this.display = display;}
	public int Type() {return type;} private final int type;
	public String Key() {return key;} private final String key;
	public String Display() {return display;} private final String display;
	public int Width() {return width;} private final int width;
	public static final int Type_id_str = 1, Type_id_text = 2, Type_id_int = 3, Type_id_datetime = 4;
}
