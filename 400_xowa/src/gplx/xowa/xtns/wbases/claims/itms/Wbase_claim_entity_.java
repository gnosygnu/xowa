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
package gplx.xowa.xtns.wbases.claims.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.claims.*;
import gplx.xowa.xtns.wbases.claims.enums.*;
public class Wbase_claim_entity_ {
	public static final byte
	  Tid__entity_type							= 0	// EX: "entity-type":"item"
	, Tid__numeric_id							= 1	// EX: "numeric-id":121410
	, Tid__id									= 2	// EX: "id":"Q121410"
	;
	public static final    Wbase_enum_hash Reg = new Wbase_enum_hash("claim.val.entity", 3);
	public static final    Wbase_enum_itm
	  Itm__entity_type				= Reg.Add(Tid__entity_type		, "entity-type")
	, Itm__numeric_id				= Reg.Add(Tid__numeric_id		, "numeric-id")
	, Itm__id						= Reg.Add(Tid__id				, "id")
	;
}
