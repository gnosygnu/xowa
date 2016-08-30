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
package gplx.xowa; import gplx.*;
import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.lnkis.*; import gplx.xowa.wikis.pages.dbs.*; import gplx.xowa.wikis.pages.redirects.*; import gplx.xowa.wikis.pages.hdumps.*; import gplx.xowa.wikis.pages.htmls.*;
public interface Xoa_page {
	Xow_wiki				Wiki();
	Xoa_url					Url(); byte[] Url_bry_safe();
	Xoa_ttl					Ttl();
	Xopg_db_data			Db();
	Xopg_redirect_mgr		Redirect_trail();
	Xopg_html_data			Html_data();
	Xopg_hdump_data			Hdump_mgr();

	Xoa_page__commons_mgr	Commons_mgr();
	boolean					Xtn__timeline_exists();
	boolean					Xtn__gallery_exists();
}
