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
package gplx.xowa.users.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
public interface Xou_db_wkr {
	void Db_init(Xou_db_mgr data_mgr);
	void Db_when_new(Xou_db_mgr data_mgr);
	void Db_save(Xou_db_mgr data_mgr);
	void Db_term(Xou_db_mgr data_mgr);
	String Xtn_key();
	String Xtn_version();
}
