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
package gplx.xowa.dbs; import gplx.*; import gplx.xowa.*;
import gplx.dbs.*; import gplx.xowa.dbs.hdumps.*;
public interface Xodb_mgr extends GfoInvkAble, RlsAble {
	byte Tid();
	String Tid_name();
	byte Data_storage_format(); void Data_storage_format_(byte v);
	byte Category_version();
	byte Search_version(); void Search_version_refresh();
	DateAdp Dump_date_query();
	Xodb_load_mgr Load_mgr();
	Xodb_save_mgr Save_mgr();
	Xodb_html_mgr Html_mgr();
}
