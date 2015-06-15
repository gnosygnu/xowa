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
package gplx.xowa.users; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*;
import gplx.xowa.users.data.*;
import gplx.xowa.files.*; import gplx.xowa.files.caches.*;
import gplx.xowa.wikis.*;
public class Xouv_user implements Xou_user {
	private Xoa_wiki_mgr wiki_mgr;
	public Xouv_user(String key) {this.key = key;}
	public String					Key() {return key;} private String key;
	public Xou_db_mgr				User_db_mgr()  {return user_db_mgr;} private Xou_db_mgr user_db_mgr;
	public Xow_wiki					Wikii() {if (wiki == null) wiki = wiki_mgr.Get_by_key_or_make_2(Xow_domain_.Domain_bry_home); return wiki;} private Xow_wiki wiki;
	public void Init_db(Xoa_app app, Xoa_wiki_mgr wiki_mgr, Io_url db_url) {
		this.wiki_mgr = wiki_mgr;
		this.user_db_mgr = new Xou_db_mgr(app);
		user_db_mgr.Init_by_app(Bool_.Y, db_url);
	}
}
