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
package gplx.xowa.xtns.wbases.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.xowa.xtns.wbases.claims.enums.*;
public class Wdata_dict_claim {
	public static final byte
	  Tid__mainsnak								= 0
	, Tid__type									= 1
	, Tid__id									= 2
	, Tid__rank									= 3
	, Tid__references							= 4
	, Tid__qualifiers							= 5
	, Tid__qualifiers_order						= 6
	;
	public static final    Wbase_enum_hash Reg = new Wbase_enum_hash("core.claim", 7);
	public static final    Wbase_enum_itm
	  Itm__mainsnak								= Reg.Add(Tid__mainsnak			, "mainsnak")
	, Itm__type									= Reg.Add(Tid__type				, "type")
	, Itm__id									= Reg.Add(Tid__id				, "id")
	, Itm__rank									= Reg.Add(Tid__rank				, "rank")
	, Itm__references							= Reg.Add(Tid__references		, "references")
	, Itm__qualifiers							= Reg.Add(Tid__qualifiers		, "qualifiers")
	, Itm__qualifiers_order						= Reg.Add(Tid__qualifiers_order	, "qualifiers-order")
	;
}
