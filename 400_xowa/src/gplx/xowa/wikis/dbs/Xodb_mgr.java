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
package gplx.xowa.wikis.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.*; import gplx.xowa.htmls.hdumps.*;
public interface Xodb_mgr extends GfoInvkAble {
	byte Tid();
	String Tid_name();
	byte Category_version();
	byte Search_version(); void Search_version_refresh();
	Xodb_load_mgr Load_mgr();
	Xodb_save_mgr Save_mgr();
	DateAdp Dump_date_query(); // used by maint_mgr
}
