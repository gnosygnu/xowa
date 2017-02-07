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
import gplx.dbs.*;
public interface Merge2_trg_itm {
	int			Idx();
	Db_conn		Conn();
}
class Merge2_trg_itm__wiki implements Merge2_trg_itm {
	public Merge2_trg_itm__wiki(int idx, Db_conn conn) {this.idx = idx; this.conn = conn;}
	public int			Idx()	{return idx;}	private final    int idx;
	public Db_conn		Conn()	{return conn;}	private final    Db_conn conn;
}
