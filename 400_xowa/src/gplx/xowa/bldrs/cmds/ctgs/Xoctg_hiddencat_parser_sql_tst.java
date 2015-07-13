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
import org.junit.*; import gplx.dbs.*; import gplx.xowa.dbs.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.bldrs.*;
public class Xoctg_hiddencat_parser_sql_tst {
	@Before public void init() {if (Xoa_test_.Db_skip()) return; fxt.Ctor_fsys();} Db_mgr_fxt fxt = new Db_mgr_fxt();
	@After public void term() {if (Xoa_test_.Db_skip()) return; fxt.Rls();} 
	@Test   public void Basic() {
		if (Xoa_test_.Db_skip()) return; 
		fxt.Init_db_sqlite();
		fxt.Wiki().Db_mgr_as_sql().Core_data_mgr().Db__cat_core().Tbl__cat_core().Create_tbl();
		Init_ctgs(1, 2, 3);
		Io_url page_props_url = Xoa_test_.Url_root().GenSubFil_nest("root", "wiki", "en.wikipedia.org", "page_props.sql");		
		fxt	.Init_fil(page_props_url, String_.Concat
		(	"INSERT INTO `page_props` VALUES"
		,	" (1,'hiddencat','')"
		,	",(2,'pageimage','A.png')"
		,	",(3,'hiddencat','')"
		,	";"
		))
		.Exec_run(new Xoctg_hiddencat_parser_sql(fxt.Bldr(), fxt.Wiki()))
		;
		Tst_ctg_hidden(Bool_.Y, 1, 3);
		Tst_ctg_hidden(Bool_.N, 2);
	}
	private void Init_ctgs(int... ctgs) {
		int len = ctgs.length;
		Xowd_cat_core_tbl tbl = fxt.Wiki().Db_mgr_as_sql().Core_data_mgr().Db__cat_core().Tbl__cat_core().Create_tbl();
		tbl.Insert_bgn();
		try {
			for (int i = 0; i < len; i++) {
				int ctg_id = ctgs[i];
				tbl.Insert_cmd_by_batch(ctg_id, 0, 0, 0, Bool_.N_byte, 0);
			}		
		} finally {tbl.Insert_end();}
	}
	private void Tst_ctg_hidden(boolean expd_hidden, int... ctgs) {
		int len = ctgs.length;
		Xowd_cat_core_tbl tbl = fxt.Wiki().Db_mgr_as_sql().Core_data_mgr().Db__cat_core().Tbl__cat_core();
		for (int i = 0; i < len; i++) {
			int ctg_id = ctgs[i];
			Xowd_category_itm ctg_itm = tbl.Select(ctg_id);
			Tfds.Eq(expd_hidden, ctg_itm.Hidden(), Int_.Xto_str(ctg_id));
		}
	}
}
