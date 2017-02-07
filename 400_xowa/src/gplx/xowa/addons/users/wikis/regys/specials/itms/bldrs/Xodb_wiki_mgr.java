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
package gplx.xowa.addons.users.wikis.regys.specials.itms.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.users.*; import gplx.xowa.addons.users.wikis.*; import gplx.xowa.addons.users.wikis.regys.*; import gplx.xowa.addons.users.wikis.regys.specials.*; import gplx.xowa.addons.users.wikis.regys.specials.itms.*;
import gplx.dbs.*;
public class Xodb_wiki_mgr {
	public Xodb_wiki_mgr(String domain) {
		this.domain = domain;
	}
	public String Domain() {return domain;} private final    String domain;
	public Xodb_wiki_db Dbs__get_core() {return dbs__core;} private Xodb_wiki_db dbs__core;
	public void Dbs__add(Xodb_wiki_db file) {
		if (file.Tid() == Xodb_wiki_db_tid.Tid__core)
			dbs__core = file;
	}
}
