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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
public class Xoud_bmk_row {
	public Xoud_bmk_row(int bmk_id, int bmk_sort, String bmk_wiki, String bmk_page, String bmk_qarg, String bmk_wtxt, DateAdp bmk_time, int bmk_count) {
		this.bmk_id = bmk_id; this.bmk_sort = bmk_sort; this.bmk_wiki = bmk_wiki; this.bmk_page = bmk_page; this.bmk_qarg = bmk_qarg; this.bmk_wtxt = bmk_wtxt; this.bmk_time = bmk_time; this.bmk_count = bmk_count;
	}
	public int Bmk_id() {return bmk_id;} private final int bmk_id;
	public int Bmk_sort() {return bmk_sort;} private final int bmk_sort;
	public String Bmk_wiki() {return bmk_wiki;} private final String bmk_wiki;
	public String Bmk_page() {return bmk_page;} private final String bmk_page;
	public String Bmk_qarg() {return bmk_qarg;} private final String bmk_qarg;
	public String Bmk_wtxt() {return bmk_wtxt;} private final String bmk_wtxt;
	public DateAdp Bmk_time() {return bmk_time;} private final DateAdp bmk_time;
	public int Bmk_count() {return bmk_count;} private final int bmk_count;
}
