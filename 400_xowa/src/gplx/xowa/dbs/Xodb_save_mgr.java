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
public interface Xodb_save_mgr {
	boolean Create_enabled(); void Create_enabled_(boolean v);
	boolean Update_modified_on_enabled(); void Update_modified_on_enabled_(boolean v);
	int Page_id_next(); void Page_id_next_(int v);
	void Data_create(Xoa_ttl ttl, byte[] text);
	void Data_update(Xoa_page page, byte[] text);
	void Data_rename(Xoa_page page, int trg_ns, byte[] trg_ttl);
	void Clear();
}
