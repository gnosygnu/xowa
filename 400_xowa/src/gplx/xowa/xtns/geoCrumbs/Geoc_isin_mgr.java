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
package gplx.xowa.xtns.geoCrumbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.dbs.*;
class Geoc_isin_mgr {
	public void Reg(int page_id, byte[] page_ttl, byte[] owner_ttl) {

	}
}
class Geoc_isin_itm {
	public int Page_id() {return page_id;} private int page_id;
	public byte[] Page_ttl() {return page_ttl;} private byte[] page_ttl;
	public byte[] Owner_ttl() {return owner_ttl;} private byte[] owner_ttl;
	public Db_obj_state Db_state() {return db_state;} private Db_obj_state db_state = Db_obj_state.Retrieved;
	public void Init_by_make(int page_id, byte[] page_ttl, byte[] owner_ttl) {
		this.page_id = page_id; this.page_ttl = page_ttl; this.owner_ttl = owner_ttl; db_state = Db_obj_state.Created;
	}
}
