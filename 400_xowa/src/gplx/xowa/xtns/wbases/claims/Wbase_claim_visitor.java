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
package gplx.xowa.xtns.wbases.claims; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.xowa.xtns.wbases.claims.itms.*;
public interface Wbase_claim_visitor {
	void Visit_str				(Wbase_claim_string itm);
	void Visit_entity			(Wbase_claim_entity itm);
	void Visit_monolingualtext	(Wbase_claim_monolingualtext itm);
	void Visit_quantity			(Wbase_claim_quantity itm);
	void Visit_time				(Wbase_claim_time itm);
	void Visit_globecoordinate	(Wbase_claim_globecoordinate itm);
	void Visit_system			(Wbase_claim_value itm);
}
