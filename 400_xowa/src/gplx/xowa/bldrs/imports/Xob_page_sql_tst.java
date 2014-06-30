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
package gplx.xowa.bldrs.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*; import gplx.dbs.*; import gplx.xowa.specials.search.*; import gplx.xowa.bldrs.imports.ctgs.*;
public class Xob_page_sql_tst {
	@Before public void init() {fxt.Ctor_fsys();} Db_mgr_fxt fxt = new Db_mgr_fxt();
	@After public void term() {fxt.Rls();} 
	@Test   public void Basic() {
		fxt.Init_db_sqlite();
		fxt.doc_ary_
		(	fxt.doc_(2, "2013-06-03 01:23", "A", "text_a")
		,	fxt.doc_(1, "2013-06-03 12:34", "B", "#REDIRECT [[A]]")
		)
		.Exec_run(new Xob_page_sql(fxt.Bldr(), fxt.Wiki()))
		;
		fxt.Test_load_ttl(Xow_ns_.Id_main, "A", fxt.page_(2, "2013-06-03 01:23", false, 6));
		fxt.Test_load_page(Xow_ns_.Id_main, 2, "text_a");
		fxt.Test_load_ttl(Xow_ns_.Id_main, "B", fxt.page_(1, "2013-06-03 12:34", true, 15));
	}
}
