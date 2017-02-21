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
package gplx.xowa.parsers.logs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.dbs.*; import gplx.xowa.bldrs.*;
public class Xop_log_wkr_factory {
	private final    Db_conn conn; private Xop_log_basic_tbl log_tbl;
	public Xop_log_wkr_factory(Db_conn conn) {this.conn = conn;}
	public Xop_log_invoke_wkr	Make__invoke()		{return new Xop_log_invoke_wkr(conn);}
	public Xop_log_property_wkr Make__property()	{return new Xop_log_property_wkr(conn);}
	public Xop_log_basic_wkr	Make__generic()		{
		if (log_tbl == null) {
			log_tbl = new Xop_log_basic_tbl(conn);
			conn.Meta_tbl_assert(log_tbl);
		}
		return new Xop_log_basic_wkr(log_tbl);
	}
}
