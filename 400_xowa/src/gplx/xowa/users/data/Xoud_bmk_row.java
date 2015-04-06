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
	public Xoud_bmk_row(int id, String wiki, String page, String qarg, String name, String comment, String tag, int sort, int count, DateAdp time) {
		this.id = id; this.wiki = wiki; this.page = page; this.qarg = qarg;
		this.name = name; this.comment = comment; this.tag = tag;
		this.sort = sort; this.count = count; this.time = time;
	}
	public int		Id() {return id;} private final int id;
	public String	Wiki() {return wiki;} private final String wiki;
	public String	Page() {return page;} private final String page;
	public String	Qarg() {return qarg;} private final String qarg;
	public String	Name() {return name;} private final String name;
	public String	Comment() {return comment;} private final String comment;
	public String	Tag() {return tag;} private final String tag;
	public int		Sort() {return sort;} private final int sort;
	public int		Count() {return count;} private final int count;
	public DateAdp	Time() {return time;} private final DateAdp time;
}
