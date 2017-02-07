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
public class Wdata_dict_mainsnak {
	public static final byte
	  Tid__snaktype								= 0
	, Tid__property								= 1
	, Tid__hash									= 2
	, Tid__datavalue							= 3
	, Tid__type									= 4
	, Tid__datatype								= 5
	;
	public static final    Wbase_enum_hash Reg = new Wbase_enum_hash("core.mainsnak", 6);
	public static final    Wbase_enum_itm
	  Itm__snaktype								= Reg.Add(Tid__snaktype	, "snaktype")
	, Itm__property								= Reg.Add(Tid__property	, "property")
	, Itm__hash									= Reg.Add(Tid__hash		, "hash")
	, Itm__datavalue							= Reg.Add(Tid__datavalue, "datavalue")
	, Itm__type									= Reg.Add(Tid__type		, "type")
	, Itm__datatype								= Reg.Add(Tid__datatype	, "datatype")
	;
}
