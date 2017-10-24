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
public class Wdata_dict_reference {
	public static final byte
	  Tid__hash									= 0
	, Tid__snaks								= 1
	, Tid__snaks_order							= 2
	;
	public static final    Wbase_enum_hash Reg = new Wbase_enum_hash("core.reference", 3);
	public static final    Wbase_enum_itm
	  Itm__hash									= Reg.Add(Tid__hash			, "hash")
	, Itm__snaks								= Reg.Add(Tid__snaks		, "snaks")
	, Itm__snaks_order							= Reg.Add(Tid__snaks_order	, "snaks-order")
	;
}
