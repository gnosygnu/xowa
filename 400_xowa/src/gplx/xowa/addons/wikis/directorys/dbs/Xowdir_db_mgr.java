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
import gplx.dbs.*;
public class Xowdir_db_mgr {
	public Xowdir_db_mgr(Db_conn conn) {
		tbl__wiki = new Xowdir_wiki_tbl(conn);
		conn.Meta_tbl_assert(tbl__wiki);
	}
	public Xowdir_wiki_tbl Tbl__wiki() {return tbl__wiki;} private final    Xowdir_wiki_tbl tbl__wiki;
}
