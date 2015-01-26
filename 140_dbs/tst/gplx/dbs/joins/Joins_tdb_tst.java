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
package gplx.dbs.joins; import gplx.*; import gplx.dbs.*;
import org.junit.*;
public class Joins_tdb_tst extends Joins_base_tst {
	@Override protected Db_conn provider_() {return Db_conn_fxt.Tdb("120_dbs_joins.dsv");}
	@Test  public void InnerJoin() {
		try {
			InnerJoin_hook();
		}
		catch (Exception exc) {
			if (String_.Has(Err_.Message_lang(exc), "joins not supported for tdbs")) return;
		}
		Tfds.Fail("'joins not supported for tdbs' error not thrown");
	}
}
