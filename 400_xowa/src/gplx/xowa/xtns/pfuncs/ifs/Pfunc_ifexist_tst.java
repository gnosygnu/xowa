/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.pfuncs.ifs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*; import gplx.xowa.wikis.tdbs.metas.*;
public class Pfunc_ifexist_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {
		fxt.Reset();
		fxt.Wiki().Cache_mgr().Ifexist_cache().Clear();
	}
	@Test  public void Basic_pass()				{fxt.Test_parse_tmpl_str_test("{{#ifexist: Abc | exists | doesn't exist }}"		, "{{test}}"	, "doesn't exist");}
	@Test  public void Empty()					{fxt.Test_parse_tmpl_str_test("{{#ifexist:|y|n}}"								, "{{test}}"	, "n");}	// NOTE: {{autolink}} can pass in ""
	@Test  public void Db_key() {	// PURPOSE: test that (1) & is encoded; (2) " " becomes "_"; EX: {{#ifexist:File:Peter & Paul fortress in SPB 03.jpg|y|n}}
		fxt.Init_page_create("A_&_b", "");
		fxt.Test_parse_tmpl_str_test("{{#ifexist:A & b|y|n}}", "{{test}}", "y");
	}
	@Test  public void Media_n() {// DATE:2014-07-04
		fxt.Test_parse_tmpl_str_test("{{#ifexist:Media:A.png|y|n}}", "{{test}}", "n");
	}
	@Test  public void Media_y_wiki() {// DATE:2014-07-04
		fxt.Init_page_create("File:A.png", "");
		fxt.Test_parse_tmpl_str_test("{{#ifexist:Media:A.png|y|n}}", "{{test}}", "y");
	}
	@Test  public void Media_y_commons() {// DATE:2014-07-04
		Xowe_wiki commons_wiki = fxt.App().Wiki_mgr().Get_by_or_make(gplx.xowa.wikis.domains.Xow_domain_itm_.Bry__commons);
		fxt.Init_page_create(commons_wiki, "File:A.png", "");
		fxt.Test_parse_tmpl_str_test("{{#ifexist:Media:A.png|y|n}}", "{{test}}", "y");
	}
	@Test  public void Media_y_file_v1() {// DATE:2014-07-04
		Xof_meta_itm meta_itm = fxt.Wiki().File_mgr().Dbmeta_mgr().Get_itm_or_new(Bry_.new_a7("A.png"));
		meta_itm.Orig_exists_(Bool_.Y_byte);
		fxt.Test_parse_tmpl_str_test("{{#ifexist:Media:A.png|y|n}}", "{{test}}", "y");
	}
}
