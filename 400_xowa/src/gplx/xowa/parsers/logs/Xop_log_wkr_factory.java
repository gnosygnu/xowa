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
package gplx.xowa.parsers.logs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.dbs.*; import gplx.xowa.bldrs.*;
public class Xop_log_wkr_factory {
	private final    Db_conn conn; private Xop_log_basic_tbl log_tbl;
	public Xop_log_wkr_factory(Db_conn conn) {this.conn = conn;}
	public Xop_log_invoke_wkr	Make__invoke()		{return new Xop_log_invoke_wkr(conn);}
	public Xop_log_property_wkr Make__property()	{return new Xop_log_property_wkr(conn);}
	public Xop_log_basic_wkr	Make__generic()		{
		if (log_tbl == null) log_tbl = new Xop_log_basic_tbl(conn);
		return new Xop_log_basic_wkr(log_tbl);
	}
}
