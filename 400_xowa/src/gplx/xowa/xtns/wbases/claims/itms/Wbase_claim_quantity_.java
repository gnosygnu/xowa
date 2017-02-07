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
public class Wbase_claim_quantity_ {
	public static final byte
	  Tid__amount								= 0
	, Tid__unit									= 1
	, Tid__upperbound							= 2
	, Tid__lowerbound							= 3
	;
	public static final    Wbase_enum_hash Reg = new Wbase_enum_hash("claim.val.quantity", 4);
	public static final    Wbase_enum_itm
	  Itm__amount					= Reg.Add(Tid__amount			, "amount")
	, Itm__unit						= Reg.Add(Tid__unit				, "unit")
	, Itm__upperbound				= Reg.Add(Tid__upperbound		, "upperBound")
	, Itm__lowerbound				= Reg.Add(Tid__lowerbound		, "lowerBound")
	;
}
