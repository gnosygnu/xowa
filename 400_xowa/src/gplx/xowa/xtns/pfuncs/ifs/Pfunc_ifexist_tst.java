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
package gplx.xowa.xtns.pfuncs.ifs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import org.junit.*;
public class Pfunc_ifexist_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init()					{fxt.Reset();}
	@Test  public void Basic_pass()				{fxt.Test_parse_tmpl_str_test("{{#ifexist: Abc | exists | doesn't exist }}"		, "{{test}}"	, "doesn't exist");}
	@Test  public void Empty()					{fxt.Test_parse_tmpl_str_test("{{#ifexist:|y|n}}"								, "{{test}}"	, "n");}	// NOTE: {{autolink}} can pass in ""
	@Test  public void Db_key() {	// PURPOSE: test that (1) & is encoded; (2) " " becomes "_"; EX: {{#ifexist:File:Peter & Paul fortress in SPB 03.jpg|y|n}}
		fxt.Init_page_create("A_&_b", "");
		fxt.Test_parse_tmpl_str_test("{{#ifexist:A & b|y|n}}", "{{test}}", "y");
	}
	@Test  public void Media_n() {// DATE:2014-07-04
		Pfunc_ifexist.Mgr.Clear();
		fxt.Test_parse_tmpl_str_test("{{#ifexist:Media:A.png|y|n}}", "{{test}}", "n");
	}
	@Test  public void Media_y_wiki() {// DATE:2014-07-04
		Pfunc_ifexist.Mgr.Clear();
		fxt.Init_page_create("File:A.png", "");
		fxt.Test_parse_tmpl_str_test("{{#ifexist:Media:A.png|y|n}}", "{{test}}", "y");
	}
	@Test  public void Media_y_commons() {// DATE:2014-07-04
		Pfunc_ifexist.Mgr.Clear();
		Xow_wiki commons_wiki = fxt.App().Wiki_mgr().Get_by_key_or_make(gplx.xowa.wikis.Xow_wiki_domain_.Url_commons);
		fxt.Init_page_create(commons_wiki, "File:A.png", "");
		fxt.Test_parse_tmpl_str_test("{{#ifexist:Media:A.png|y|n}}", "{{test}}", "y");
	}
	@Test  public void Media_y_file_v1() {// DATE:2014-07-04
		Pfunc_ifexist.Mgr.Clear();
		Xof_meta_itm meta_itm = fxt.Wiki().File_mgr().Meta_mgr().Get_itm_or_new(Bry_.new_ascii_("A.png"));
		meta_itm.Orig_exists_(Bool_.Y_byte);
		fxt.Test_parse_tmpl_str_test("{{#ifexist:Media:A.png|y|n}}", "{{test}}", "y");
	}
}
