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
package gplx.xowa.bldrs.cmds.texts.tdbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.cmds.texts.*;
import org.junit.*;
import gplx.core.ios.*; import gplx.xowa.wikis.tdbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.nss.*;
public class Xob_tst {		
	@Before public void init() {fxt = new Xob_fxt().Ctor_mem();} private Xob_fxt fxt;
	// @After public void term() {fxt.Wiki().Ctx().Sys_load_tmpls_(true);} // commented during wiki.Ctx() removal; DATE:2014-04-22
	@Test  public void Basic() {
		fxt.doc_ary_
		(	fxt.doc_(3, "2012-01-02 13:15", "Title 2a", "text2a\ny")
		,	fxt.doc_(2, "2012-01-02 13:14", "Title 1",  "text1\nz")
		)
		.Fil_expd(fxt.fil_ns_page(Xow_ns_.Id_main, 0)
		,	"!!!!@|!!!!>|"
		,	"!!!!$\t#6>K6\tTitle 2a\ttext2a"
		,	"y\t"
		,	"!!!!#\t#6>K5\tTitle 1\ttext1"
		,	"z\t"
		,	""
		)
		.Fil_expd(fxt.fil_ns_title(Xow_ns_.Id_main, 0)
		,	"!!!!C|!!!!D|"
		,	"!!!!#|!!!!!|!!!!\"|0|!!!!(|Title 1"
		,	"!!!!$|!!!!!|!!!!!|0|!!!!)|Title 2a"
		,	""
		)
		.Fil_expd(fxt.fil_reg(Xow_ns_.Id_main, Xotdb_dir_info_.Tid_ttl)
		,	"0|Title 1|Title 2a|2"
		,	""
		)
		.Run_page_title()
		;
	}
	@Test  public void Utf8() {
		fxt.doc_ary_
		(	fxt.doc_(3, "2012-01-02 13:15", "↑", "t2")
		,	fxt.doc_(2, "2012-01-02 13:14", "!", "t1")
		)
		.Fil_expd(fxt.fil_ns_page(Xow_ns_.Id_main, 0)
		,	"!!!!5|!!!!3|"
		,	"!!!!$\t#6>K6\t↑\tt2\t"
		,	"!!!!#\t#6>K5\t!\tt1\t"
		,	""
		)
		.Fil_expd(fxt.fil_ns_title(Xow_ns_.Id_main, 0)
		,	"!!!!=|!!!!?|"
		,	"!!!!#|!!!!!|!!!!\"|0|!!!!#|!"
		,	"!!!!$|!!!!!|!!!!!|0|!!!!#|↑"
		,	""
		)
		.Fil_expd(fxt.fil_reg(Xow_ns_.Id_main, Xotdb_dir_info_.Tid_ttl)
		,	"0|!|↑|2"
		,	""
		)
		.Run_page_title()
		;
	}
	@Test  public void Ns() {
		fxt.doc_ary_
		(	fxt.doc_(2, "2012-01-02 13:14", "Template:A",  "test a")
		)
		.Fil_expd(fxt.fil_ns_page(Xow_ns_.Id_template, 0)
		,	"!!!!7|"
		,	"!!!!#\t#6>K5\tA\ttest a\t"
		,	""
		)
		.Fil_expd(fxt.fil_ns_title(Xow_ns_.Id_template, 0)
		,	"!!!!=|"
		,	"!!!!#|!!!!!|!!!!!|0|!!!!'|A"
		,	""
		)
		.Fil_expd(fxt.fil_reg(Xow_ns_.Id_template, Xotdb_dir_info_.Tid_ttl)
		,	"0|A|A|1"
		,	""
		)
		.Run_page_title()
		;
	}
	@Test  public void Id() {
		fxt.doc_ary_
		(	fxt.doc_wo_date_(2, "A",  "a")
		,	fxt.doc_wo_date_(3, "Template:B",  "b")
		)
		.Fil_expd(fxt.fil_site_id(0)
		,	"!!!!C|!!!!C|"
		,	"!!!!#|!!!!!|!!!!!|0|!!!!\"|!!!!!|A"
		,	"!!!!$|!!!!!|!!!!!|0|!!!!\"|!!!!+|B"
		,	""
		)
		.Fil_expd(fxt.fil_reg(Xotdb_dir_info_.Tid_id)
		,	"0|!!!!#|!!!!$|2"
		,	""
		)
		.Run_id()
		;
	}
	@Test  public void Category() {
		fxt.doc_ary_
		(	fxt.doc_wo_date_(2, "A",  "[[Category:Y]] [[Category:Z]]")
		,	fxt.doc_wo_date_(3, "B",  "[[Category:Y]]")
		)
		.Fil_expd(fxt.fil_site_ctg(0)
		,	"!!!!/|!!!!)|"
		,	"Y|!!!!#|!!!!$"
		,	"Z|!!!!#"
		,	""
		)
		.Fil_expd(fxt.fil_reg(Xotdb_dir_info_.Tid_category)
		,	"0|Y|Z|2"
		,	""
		)
		.Run_ctg()
		;
	}
	@Test  public void Category2() {
		fxt.doc_ary_
		(	fxt.doc_wo_date_(2, "A",  "[[Category:X]] [[Category:Y]]")
		,	fxt.doc_wo_date_(3, "B",  "[[Category:Y]]")
		)
		.Fil_expd(fxt.fil_site_ctg(0)
		,	"!!!!)|!!!!/|"
		,	"X|!!!!#"
		,	"Y|!!!!#|!!!!$"
		,	""
		)
		.Fil_expd(fxt.fil_reg(Xotdb_dir_info_.Tid_category)
		,	"0|X|Y|2"
		,	""
		)
		.Run_ctg()
		;
	}
	@Test  public void Tmpl_dump() {
		fxt.doc_ary_
		(	fxt.doc_wo_date_(2, "Template:A",  "a")
		,	fxt.doc_wo_date_(3, "B",  "b")
		,	fxt.doc_wo_date_(4, "Template:C",  "c")
		)
		.Fil_expd(null
		,	"!!!!#\t#6>K5\tA\ta\t"
		,	"!!!!%\t#6>K5\tC\tc\t"
		,	""
		)
		.Run_tmpl_dump()
		;
	}
	@Test  public void Parse() {
		tst_Parse(String_.Concat
			(	"!!!!#\t#6>K5\tA\ta\t\n"
			,	"!!!!$\t#6>K5\tB\tb\t\n"	
			,	"!!!!%\t#6>K5\tC\tc\t\n"
			)
			,	"a", "b", "c");
	}
	private void tst_Parse(String raw, String... expd) {
		Xoae_app app = Xoa_app_fxt.app_();	// NOTE: resets mem file system, so must happen first
		Io_url url = Io_url_.mem_fil_("mem/raw_page.csv");
		Io_mgr.Instance.SaveFilStr(url, raw);
		Xotdb_page_raw_parser parser = new Xotdb_page_raw_parser();
		Xowe_wiki wiki = Xoa_app_fxt.wiki_tst_(app);
		parser.Load(Gfo_usr_dlg_.Test(), wiki, new Xow_ns(Xow_ns_.Id_template, Xow_ns_case_.Tid__1st, Bry_.new_a7("Template"), false), new Io_url[] {url}, 1 * Io_mgr.Len_kb);
		List_adp actl = List_adp_.new_();
		Xowd_page_itm page = new Xowd_page_itm();
		while (parser.Read(page)) {
			actl.Add(String_.new_u8(page.Text()));
		}
		Tfds.Eq_ary(expd, actl.To_str_ary());
	}
	@Test  public void Img_gen() {
		/*
		fxt.Src_(String_.Concat_lines_nl
		( 	"A.png|||1234|90|80|8|1||0,-1,-1,-1"
		,	"B.png|||1234|90|80|8|1||0,-1,-1,-1"
		,	"C.png|||1234|90|80|8|1||0,-1,-1,-1"
		)
		.Expd_len_(3)
		.Expd_files_
		(	Io_fil.new_("mem/dump/70a"
			,  "A.png|||1234|90|80|8|1||0,-1,-1,-1|70a")
		,	Io_fil.new_("mem/dump/70b"
			,  "B.png|||1234|90|80|8|1||0,-1,-1,-1|71b")
		,	Io_fil.new_("mem/dump/70c"
			,  "C.png|||1234|90|80|8|1||0,-1,-1,-1|72c")
		)
		.tst_()
		*/;
	}
}
