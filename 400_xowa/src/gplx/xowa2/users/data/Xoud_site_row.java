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
package gplx.xowa2.users.data; import gplx.*; import gplx.xowa2.*; import gplx.xowa2.users.*;
public class Xoud_site_row {
	public Xoud_site_row(int id, int priority, String domain, String name, String path, String xtn) {
		this.id = id; this.priority = priority; this.domain = domain; this.name = name; this.path = path; this.xtn = xtn;
	}
	public int Id() {return id;} private final int id;
	public int Priority() {return priority;} private final int priority;
	public String Domain() {return domain;} private final String domain;
	public String Name() {return name;} private final String name;
	public String Path() {return path;} private final String path;
	public String Xtn() {return xtn;} private String xtn;
}
