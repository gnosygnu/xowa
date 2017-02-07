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
package gplx.xowa.addons.bldrs.hdumps.diffs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.hdumps.*;
class Dumpdiff_page_itm {
	public Dumpdiff_page_itm(int page_id, int ns_id, byte[] ttl_bry, int cur_db_id, int prv_db_id) {
		this.page_id = page_id;
		this.ns_id = ns_id;
		this.ttl_bry = ttl_bry;
		this.cur_db_id = cur_db_id;
		this.prv_db_id = prv_db_id;
	}
	public int Page_id() {return page_id;} private final    int page_id;
	public int Ns_id() {return ns_id;} private final    int ns_id;
	public byte[] Ttl_bry() {return ttl_bry;} private final    byte[] ttl_bry;
	public int Cur_db_id() {return cur_db_id;} private final    int cur_db_id;
	public int Prv_db_id() {return prv_db_id;} private final    int prv_db_id;
}
