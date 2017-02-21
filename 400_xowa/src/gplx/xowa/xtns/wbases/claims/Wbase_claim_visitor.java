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
