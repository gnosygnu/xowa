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
