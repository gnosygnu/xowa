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
import org.junit.*; import gplx.xowa.ctgs.*; import gplx.xowa.bldrs.*;
public class Xoctg_link_idx_wkr_tst {		
	@Before public void init() {fxt.Clear();} private Xob_base_fxt fxt = new Xob_base_fxt();
	@Test   public void Basic() {
		fxt
		.Init_fil("mem/xowa/wiki/en.wikipedia.org/tmp/ctg.link_sql/make/0000000000.csv", String_.Concat_lines_nl
		(	"Ctg_1|c|Ctg_1a|!!!!@|#8DDL|"
		,	"Ctg_1|f|A.png|!!!!#|#8DDL|"
		,	"Ctg_1|p|A0|!!!!$|#8DDL|"
		,	"Ctg_1|p|A1|!!!!%|#8DDL|"
		,	"Ctg_2|p|B1|!!!!%|#8DDL|"
		))
		.Init_fil("mem/xowa/wiki/en.wikipedia.org/tmp/ctg.hiddencat_ttl/make/0000000000.csv", String_.Concat_lines_nl
		(	"Ctg_2|!!!!%|"
		))
		.Exec_cmd(Xob_cmd_keys.Key_tdb_ctg_link_idx, GfoMsg_.basic_(Xoctg_link_idx_wkr.Invk_make_fil_max_, 72))
		.Test_fil("mem/xowa/wiki/en.wikipedia.org/site/category2/link/00/00/00/00/0000000000.xdat", String_.Concat_lines_nl
		(	"!!!\"(|"
		,	"Ctg_1|!!!!4|!!!!3|!!!!?|!!!!@;#8DDL;Ctg_1a|!!!!#;#8DDL;A.png|!!!!$;#8DDL;A0|!!!!%;#8DDL;A1|"
		))
		.Test_fil("mem/xowa/wiki/en.wikipedia.org/site/category2/link/00/00/00/00/0000000001.xdat", String_.Concat_lines_nl
		(	"!!!!I|"
		,	"Ctg_2|!!!!!|!!!!!|!!!!0|!!!!%;#8DDL;B1|"
		))
		.Test_fil("mem/xowa/wiki/en.wikipedia.org/site/category2/link/reg.csv", String_.Concat_lines_nl
		(	"0|Ctg_1|Ctg_1|1"
		,	"1|Ctg_2|Ctg_2|1"
		))
		.Test_fil("mem/xowa/wiki/en.wikipedia.org/site/category2/main/00/00/00/00/0000000000.xdat", String_.Concat_lines_nl
		(	"!!!!<|!!!!<|"
		,	"Ctg_1|n|!!!!\"|!!!!\"|!!!!#|"
		,	"Ctg_2|y|!!!!!|!!!!!|!!!!\"|"
		))
		.Test_fil("mem/xowa/wiki/en.wikipedia.org/site/category2/main/reg.csv", String_.Concat_lines_nl
		(	"0|Ctg_1|Ctg_2|2"
		))
		;
		byte[] ctg_name = Bry_.new_ascii_("Ctg_1");
		Xoctg_data_ctg main = new Xoctg_data_ctg(ctg_name);
		fxt.Wiki().Db_mgr().Load_mgr().Load_ctg_v2(main, ctg_name);
		Tfds.Eq(1, main.Grp_by_tid(Xoa_ctg_mgr.Tid_subc).Total());
		Tfds.Eq(1, main.Grp_by_tid(Xoa_ctg_mgr.Tid_file).Total());
		Tfds.Eq(2, main.Grp_by_tid(Xoa_ctg_mgr.Tid_page).Total());
	}
}
