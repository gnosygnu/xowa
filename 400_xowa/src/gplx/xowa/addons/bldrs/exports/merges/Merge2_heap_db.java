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
package gplx.xowa.addons.bldrs.exports.merges; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*;
import gplx.dbs.*; import gplx.xowa.addons.bldrs.exports.utls.*;
public class Merge2_heap_db implements Merge2_trg_itm {
	public Merge2_heap_db(Split_tbl tbl, Dbmeta_fld_list flds, int idx, Io_url url, Db_conn conn) {
		this.tbl = tbl; this.flds = flds; this.idx = idx;
		this.url = url; this.conn = conn; 
	}
	public Split_tbl		Tbl()	{return tbl;}	private final    Split_tbl tbl;
	public Dbmeta_fld_list	Flds()	{return flds;}	private final    Dbmeta_fld_list flds;
	public int				Idx()	{return idx;}	private final    int idx;
	public Io_url			Url()	{return url;}	private final    Io_url url;
	public Db_conn			Conn()	{return conn;}	private final    Db_conn conn;
}
