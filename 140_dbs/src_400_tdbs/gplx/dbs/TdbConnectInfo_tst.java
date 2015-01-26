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
import org.junit.*;
public class TdbConnectInfo_tst {
	@Test  public void Full() {
		Db_url connectInfo = Db_url_.parse_("gplx_key=tdb;url=C:\\dir\\xmpl.tdb;format=dsv;");
		tst_Parse(connectInfo, Io_url_.new_any_("C:\\dir\\xmpl.tdb"), "dsv");
	}
	@Test  public void DefaultFormat() {
		Db_url connectInfo = Db_url_.parse_("gplx_key=tdb;url=C:\\dir\\xmpl.tdb");	// dsv Format inferred
		tst_Parse(connectInfo, Io_url_.new_any_("C:\\dir\\xmpl.tdb"), "dsv");
	}
	void tst_Parse(Db_url connectInfo, Io_url url, String format) {
		Tfds.Eq(((Db_url__tdb)connectInfo).Url(), url);
	}
}
