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
package gplx.xowa.bldrs.cmds.texts.sqls; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import org.junit.*; import gplx.dbs.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.ctgs.*; import gplx.xowa.wikis.nss.*;
public class Xob_page_cmd_tst {
	@Before public void init() {if (Xoa_test_.Db_skip()) return; fxt.Ctor_fsys();} Db_mgr_fxt fxt = new Db_mgr_fxt();
	@After public void term() {if (Xoa_test_.Db_skip()) return; fxt.Rls();} 
	@Test   public void Basic() {
		if (Xoa_test_.Db_skip()) return;
		fxt.Init_db_sqlite();
		fxt.Bldr().App().Api_root().Bldr().Wiki().Import().Zip_tid_text_raw_();
		fxt.doc_ary_
		(	fxt.doc_(2, "2013-06-03 01:23", "A", "text_a")
		,	fxt.doc_(1, "2013-06-03 12:34", "B", "#REDIRECT [[A]]")
		)
		.Exec_run(new Xob_page_cmd(fxt.Bldr(), fxt.Wiki()))
		;
		fxt.Test_load_ttl(Xow_ns_.Tid__main, "A", fxt.page_(2, "2013-06-03 01:23", false, 6));
		fxt.Test_load_page(Xow_ns_.Tid__main, 2, "text_a");
		fxt.Test_load_ttl(Xow_ns_.Tid__main, "B", fxt.page_(1, "2013-06-03 12:34", true, 15));
	}
}
