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
import org.junit.*; import gplx.dbs.*; import gplx.xowa.specials.search.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.texts.*; import gplx.xowa.bldrs.cmds.ctgs.*;
import gplx.xowa.wikis.data.tbls.*;
public class Xob_search_sql_cmd_tst {
	@Before public void init() {if (Xoa_test_.Db_skip()) return; fxt.Ctor_fsys();} private final Db_mgr_fxt fxt = new Db_mgr_fxt();
	@After public void term() {if (Xoa_test_.Db_skip()) return; fxt.Rls();} 
	@Test   public void Basic() {
		if (Xoa_test_.Db_skip()) return;
		fxt.Init_db_sqlite();
		fxt.doc_ary_
		(	fxt.doc_ttl_(2, "A b")
		,	fxt.doc_ttl_(1, "B c")
		)
		.Exec_run(new Xob_page_cmd(fxt.Bldr(), fxt.Wiki()))
		.Exec_run(new Xob_search_sql_cmd(fxt.Bldr(), fxt.Wiki()))
		;
		fxt.Test_search("a", 2);
		fxt.Test_search("b", 1, 2);
		fxt.Test_search("c", 1);
		fxt.Test_search("d");
	}
	@Test   public void Wildcard() {
		if (Xoa_test_.Db_skip()) return;
		fxt.Init_db_sqlite();
		fxt.doc_ary_
		(	fxt.doc_ttl_(1, "A")
		,	fxt.doc_ttl_(2, "A2")
		,	fxt.doc_ttl_(3, "A3")
		)
		.Exec_run(new Xob_page_cmd(fxt.Bldr(), fxt.Wiki()))
		.Exec_run(new Xob_search_sql_cmd(fxt.Bldr(), fxt.Wiki()))
		;
		fxt.Test_search("a*", 1, 2, 3);
	}
}
