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
public class Xoud_history_row {
	public Xoud_history_row(String history_wiki, String history_page, String history_qarg, DateAdp history_time, int history_count) {
		this.history_wiki = history_wiki;
		this.history_page = history_page;
		this.history_qarg = history_qarg;
		this.history_time = history_time;
		this.history_count = history_count;
	}
	public String History_wiki() {return history_wiki;} private final String history_wiki;
	public String History_page() {return history_page;} private final String history_page;
	public String History_qarg() {return history_qarg;} private final String history_qarg;
	public DateAdp History_time() {return history_time;} private final DateAdp history_time;
	public int History_count() {return history_count;} private final int history_count;
	public static final Xoud_history_row Null = null;
}
