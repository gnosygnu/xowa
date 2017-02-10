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
package gplx.xowa.addons.wikis.directorys.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.directorys.*;
public class Xowdir_wiki_itm {
	public Xowdir_wiki_itm(int id, String domain, Io_url url, Xowdir_wiki_json json) {
		this.id = id;
		this.domain = domain;
		this.url = url;
		this.json = json;
	}
	public int Id() {return id;} private final    int id;
	public String Domain() {return domain;} private final    String domain;
	public Io_url Url() {return url;} private final    Io_url url;
	public Xowdir_wiki_json Json() {return json;} private final    Xowdir_wiki_json json;
}
