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
package gplx.xowa.users.history; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
public class Xoud_history_row {
	public Xoud_history_row(int id, byte[] wiki, byte[] url, DateAdp time, int count) {
		this.id = id;
		this.wiki = wiki; this.url = url;
		this.time = time; this.count = count;
	}
	public int Id() {return id;} private final int id;
	public byte[] Wiki() {return wiki;} private final byte[] wiki;
	public byte[] Url()  {return url;} private final byte[] url;
	public DateAdp Time() {return time;} private final DateAdp time;
	public int Count() {return count;} private final int count;
}
