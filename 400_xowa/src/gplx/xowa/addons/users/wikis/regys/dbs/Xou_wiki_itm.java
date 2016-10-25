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
package gplx.xowa.addons.users.wikis.regys.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.users.*; import gplx.xowa.addons.users.wikis.*; import gplx.xowa.addons.users.wikis.regys.*;
public class Xou_wiki_itm {
	public Xou_wiki_itm(int id, int type, String domain, String name, Io_url url, String data) {
		this.id = id;
		this.type = type;
		this.domain = domain;
		this.name = name;
		this.url = url;
		this.data = data;
	}
	public int Id() {return id;} private int id;
	public int Type() {return type;} private int type;
	public String Domain() {return domain;} private String domain;
	public String Name() {return name;} private String name;
	public Io_url Url() {return url;} private Io_url url;
	public String Data() {return data;} private String data;
}
