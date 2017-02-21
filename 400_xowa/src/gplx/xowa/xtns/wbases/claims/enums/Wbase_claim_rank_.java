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
package gplx.xowa.xtns.wbases.claims.enums; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.claims.*;
public class Wbase_claim_rank_ {
	public static final byte	// SERIALIZED.MW
	  Tid__preferred							=  2
	, Tid__normal								=  1
	, Tid__deprecated							=  0
	, Tid__unknown								=  3
	;
	public static final    Wbase_enum_hash Reg = new Wbase_enum_hash("claim.rank", 4);
	public static final    Wbase_enum_itm
	  Itm__preferred				= Reg.Add(Tid__preferred		, "preferred")
	, Itm__normal					= Reg.Add(Tid__normal			, "normal")
	, Itm__deprecated				= Reg.Add(Tid__deprecated		, "deprecated")
	, Itm__unknown					= Reg.Add(Tid__unknown			, "unknown")
	;
}