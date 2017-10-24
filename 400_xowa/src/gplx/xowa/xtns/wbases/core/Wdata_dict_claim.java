/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
