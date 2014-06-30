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
import org.junit.*;
import gplx.ios.*;
public class Xobc_tst {		
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
		.Fil_expd(fxt.fil_reg(Xow_ns_.Id_main, Xow_dir_info_.Tid_ttl)
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
		.Fil_expd(fxt.fil_reg(Xow_ns_.Id_main, Xow_dir_info_.Tid_ttl)
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
		.Fil_expd(fxt.fil_reg(Xow_ns_.Id_template, Xow_dir_info_.Tid_ttl)
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
		.Fil_expd(fxt.fil_reg(Xow_dir_info_.Tid_id)
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
		.Fil_expd(fxt.fil_reg(Xow_dir_info_.Tid_category)
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
		.Fil_expd(fxt.fil_reg(Xow_dir_info_.Tid_category)
		,	"0|X|Y|2"
		,	""
		)
		.Run_ctg()
		;
	}
	@Test  public void Img() {
		fxt.doc_ary_
		(	fxt.doc_wo_date_(2, "A",  "[[File:X.png|thumb|220x110px]] [[Image:Y.png|upright=.80]]")
		,	fxt.doc_wo_date_(3, "B",  "[[File:X.png]] [[Image:Y.png|upright=.80]]")
		)
		.Run_img(String_.Concat_lines_nl
		(	"X.png|0,-1,-1"
		,	"X.png|8,220,110"
		,	"Y.png|0,-1,-1,upright=0.8"
		,	""
		))
		;
	}
	@Test  public void Img_chars() {
		fxt.doc_ary_
		(	fxt.doc_wo_date_(2, "A",  "[[File:A + b.png]] [[File:A %3c b.png]] [[File:A 折 b.png]]")
		)
		.Run_img(String_.Concat_lines_nl
		(	"A_+_b.png|0,-1,-1"		// EX: ASLSJSW Aas+.PNG
		,	"A_<_b.png|0,-1,-1"		// EX: ANKAW%C3%9C-Burgunder-wei%C3%9F.jpg
		,	"A_折_b.png|0,-1,-1"		// EX: 折鶴 WUXGA.jpg
		,	""
		))
		;
	}
	@Test  public void Math() {
		fxt.doc_ary_
		(	fxt.doc_wo_date_(2, "A",  "<math>a</math>")
		,	fxt.doc_wo_date_(3, "B",  "<math>a</math> <math>b</math>")
		)
		.Run_math(String_.Concat_lines_nl
		(	"a"
		,	"b"
		,	""
		))
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
		Xoa_app app = Xoa_app_fxt.app_();	// NOTE: resets mem file system, so must happen first
		Io_url url = Io_url_.mem_fil_("mem/raw_page.csv");
		Io_mgr._.SaveFilStr(url, raw);
		Xodb_page_raw_parser parser = new Xodb_page_raw_parser();
		Xow_wiki wiki = Xoa_app_fxt.wiki_tst_(app);
		parser.Load(Gfo_usr_dlg_base.test_(), wiki, new Xow_ns(Xow_ns_.Id_template, Xow_ns_case_.Id_1st, Bry_.new_utf8_("Template"), false), new Io_url[] {url}, 1 * Io_mgr.Len_kb);
		ListAdp actl = ListAdp_.new_();
		Xodb_page page = new Xodb_page();
		while (parser.Read(page)) {
			actl.Add(String_.new_utf8_(page.Text()));
		}
		Tfds.Eq_ary(expd, actl.XtoStrAry());
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
class Xob_img_xfer_gen_fxt extends Xob_fxt {		public Xob_img_xfer_gen_fxt Files_len(int v) {expd_files_len = v; return this;} private int expd_files_len;
	public Xob_img_xfer_gen_fxt Files_(Io_fil... v) {expd_files = v; return this;} Io_fil[] expd_files;
	public Xob_img_xfer_gen_fxt tst() {
		Xob_bldr bldr = this.Bldr();
		Xobc_img_prep_xfer wkr = new Xobc_img_prep_xfer(bldr, this.Wiki());
		wkr.Cmd_bgn(bldr);
		wkr.Cmd_run();
		wkr.Cmd_end();
		Io_url[] urls = wkr.Dump_url_gen().Prv_urls();
		Tfds.Eq(expd_files_len, urls.length);
		return this;
	}
//	public void Cmd_end() {
//		Io_url_gen make_url_gen = new Io_url_gen_md5(temp_dir.GenSubFil("make")); 
//		Xobdc_merger.Basic(bldr.Status_mgr(), dump_url_gen, temp_dir.GenSubDir("sort"), sort_mem_len, Io_line_rdr_key_gen_.last_pipe, new Io_sort_fil_basic(bldr.Status_mgr(), make_url_gen, make_fil_len));
//	}	int sort_mem_len = Int_.Neg1, make_fil_len = 100 * Io_mgr.Len_kb;
}
