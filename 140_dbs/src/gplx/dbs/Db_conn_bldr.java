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
package gplx.dbs; import gplx.*;
public class Db_conn_bldr {
	private Db_conn_bldr_wkr wkr;
	public void Reg_default_sqlite()	{wkr = Db_conn_bldr_wkr__sqlite.I; wkr.Clear_for_tests();}
	public void Reg_default_mem()		{wkr = Db_conn_bldr_wkr__mem.I; wkr.Clear_for_tests();}
	public Db_conn Get(String type, Object url_obj) {return wkr.Get(type, url_obj);}
	public Db_conn New(String type, Object url_obj) {return wkr.New(type, url_obj);}
	public Db_conn_bldr_data Get_or_new(String type, Object url_obj) {
		boolean exists = wkr.Exists(type, url_obj);
		Db_conn conn = exists ? Get(type, url_obj) : New(type, url_obj);
		return new Db_conn_bldr_data(conn, exists);
	}
        public static final Db_conn_bldr I = new Db_conn_bldr(); Db_conn_bldr() {}
}
