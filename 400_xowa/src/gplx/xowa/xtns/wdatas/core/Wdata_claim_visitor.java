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
package gplx.xowa.xtns.wdatas.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
public interface Wdata_claim_visitor {
	void Visit_str(Wdata_claim_itm_str itm);
	void Visit_entity(Wdata_claim_itm_entity itm);
	void Visit_monolingualtext(Wdata_claim_itm_monolingualtext itm);
	void Visit_quantity(Wdata_claim_itm_quantity itm);
	void Visit_time(Wdata_claim_itm_time itm);
	void Visit_globecoordinate(Wdata_claim_itm_globecoordinate itm);
	void Visit_system(Wdata_claim_itm_system itm);
}
