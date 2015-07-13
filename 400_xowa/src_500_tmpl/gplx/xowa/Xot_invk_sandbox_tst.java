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
package gplx.xowa; import gplx.*;
import org.junit.*;
public class Xot_invk_sandbox_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {
		fxt.Reset();
		fxt.Init_defn_clear();
		fxt.Init_defn_add("concat", "{{{1}}}{{{2}}}");
	}
	@Test  public void Basic() {
		fxt.Test_parse_tmpl_str("{{concat|a|b}}", "ab");
	}
	@Test  public void Basic_too_many() {	// c gets ignored
		fxt.Test_parse_tmpl_str("{{concat|a|b|c}}", "ab");
	}
	@Test  public void Basic_too_few() {
		fxt.Test_parse_tmpl_str("{{concat|a}}", "a{{{2}}}");
	}
	@Test  public void Basic_else() {
		fxt.Init_defn_add("concat", "{{{1}}}{{{2|?}}}");
		fxt.Test_parse_tmpl_str("{{concat|a}}", "a?");
	}
	@Test  public void Eq_2() {
		fxt.Init_defn_add("concat", "{{{lkp1}}}");
		fxt.Test_parse_tmpl_str("{{concat|lkp1=a=b}}", "a=b");
	}
	@Test  public void Recurse()		{fxt.Test_parse_tmpl_str_test("<{{concat|{{{1}}}|{{{2}}}}}>"	, "{{test|a|b}}", "<ab>");}
	@Test  public void Recurse_mix()	{fxt.Test_parse_tmpl_str_test("{{concat|.{{{1}}}.|{{{2}}}}}"	, "{{test|a|b}}", ".a.b");}
	@Test  public void Recurse_err()	{fxt.Test_parse_tmpl_str_test("{{concat|{{{1}}}|{{{2}}}}}"	, "{{test1|a|b}}", "[[:Template:test1]]");} // NOTE: make sure test1 does not match test
	@Test  public void KeyNewLine()		{fxt.Test_parse_tmpl_str_test("{{\n  concat|a|b}}"			, "{{\n  test}}", "ab");}
}
