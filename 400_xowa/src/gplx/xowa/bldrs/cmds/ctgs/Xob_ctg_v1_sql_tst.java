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
package gplx.xowa.bldrs.cmds.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*;
import org.junit.*; import gplx.dbs.*; import gplx.xowa.specials.search.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.texts.*;
import gplx.xowa.bldrs.cmds.texts.sqls.*;
public class Xob_ctg_v1_sql_tst {
	@Before public void init() {if (Xoa_test_.Db_skip()) return; fxt.Ctor_fsys();} Db_mgr_fxt fxt = new Db_mgr_fxt();
	@After public void term() {if (Xoa_test_.Db_skip()) return; fxt.Rls();} 
	@Test   public void Basic() {
		if (Xoa_test_.Db_skip()) return; 
		fxt.Init_db_sqlite();
		fxt.doc_ary_
		(	fxt.doc_wo_date_(1, "A"				, "[[Category:Ctg_0]] [[Category:Ctg_1]]")
		,	fxt.doc_wo_date_(2, "B"				, "[[Category:Ctg_1]] [[Category:Ctg_2]]")
		,	fxt.doc_wo_date_(3, "File:C"		, "[[Category:Ctg_0]]")
		,	fxt.doc_wo_date_(4, "Category:D"	, "[[Category:Ctg_0]]")
		,	fxt.doc_wo_date_(5, "E"				, "[[Category:Ctg_a'b]]")
		,	fxt.doc_wo_date_(6, "F"				, "[[Category:Ctg_a\\]]")
		)
		.Exec_run(new Xob_page_cmd(fxt.Bldr(), fxt.Wiki()))
		.Exec_run(new Xob_ctg_v1_sql().Ctor(fxt.Bldr(), fxt.Wiki()))
		;
		fxt.Test_file(Xoa_test_.Url_root().GenSubFil_nest("root", "wiki", "en.wikipedia.org", "xowa_categorylinks.sql").Raw(), String_.Concat_lines_nl
		(	  "CREATE TABLE `categorylinks` ("
		,	"  `cl_from` int(10) unsigned NOT NULL DEFAULT '0',"
		,	"  `cl_to` varbinary(255) NOT NULL DEFAULT '',"
		,	"  `cl_sortkey` varbinary(230) NOT NULL DEFAULT '',"
		,	"  `cl_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
		,	"  `cl_sortkey_prefix` varbinary(255) NOT NULL DEFAULT '',"
		,	"  `cl_collation` varbinary(32) NOT NULL DEFAULT '',"
		,	"  `cl_type` enum('page','subcat','file') NOT NULL DEFAULT 'page',"
		,	"  UNIQUE KEY `cl_from` (`cl_from`,`cl_to`),"
		,	");"
		,	"INSERT INTO 'categorylinks' VALUES (1,'Ctg_0','','','','','p')"
		,	",(3,'Ctg_0','','','','','p')"
		,	",(4,'Ctg_0','','','','','p')"
		,	",(1,'Ctg_1','','','','','p')"
		,	",(2,'Ctg_1','','','','','p')"
		,	",(2,'Ctg_2','','','','','p')"
		,	",(5,'Ctg_a\\'b','','','','','p')"
		,	",(6,'Ctg_a\\\\','','','','','p')"
		,	";"
		));
	}
	@Test   public void Ignore_dupes() {	// PURPOSE: ignore dupe ctgs
		if (Xoa_test_.Db_skip()) return;
		fxt.Init_db_sqlite();
		fxt.doc_ary_
		(	fxt.doc_wo_date_(1, "A"				, "[[Category:Ctg_0]] [[Category:Ctg_0]]")
		)
		.Exec_run(new Xob_page_cmd(fxt.Bldr(), fxt.Wiki()))
		.Exec_run(new Xob_ctg_v1_sql().Ctor(fxt.Bldr(), fxt.Wiki()))
		;
		fxt.Test_file(Xoa_test_.Url_root().GenSubFil_nest("root", "wiki", "en.wikipedia.org", "xowa_categorylinks.sql").Raw(), String_.Concat_lines_nl
		(	  "CREATE TABLE `categorylinks` ("
		,	"  `cl_from` int(10) unsigned NOT NULL DEFAULT '0',"
		,	"  `cl_to` varbinary(255) NOT NULL DEFAULT '',"
		,	"  `cl_sortkey` varbinary(230) NOT NULL DEFAULT '',"
		,	"  `cl_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
		,	"  `cl_sortkey_prefix` varbinary(255) NOT NULL DEFAULT '',"
		,	"  `cl_collation` varbinary(32) NOT NULL DEFAULT '',"
		,	"  `cl_type` enum('page','subcat','file') NOT NULL DEFAULT 'page',"
		,	"  UNIQUE KEY `cl_from` (`cl_from`,`cl_to`),"
		,	");"
		,	"INSERT INTO 'categorylinks' VALUES (1,'Ctg_0','','','','','p')"
		,	";"
		));
	}
}
