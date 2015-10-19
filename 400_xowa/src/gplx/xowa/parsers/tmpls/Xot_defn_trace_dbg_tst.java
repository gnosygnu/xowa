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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xot_defn_trace_dbg_tst {
	Xot_defn_trace_fxt fx = new Xot_defn_trace_fxt();
	@Before public void init() {
		fx.Init_defn_clear();
		fx.Init_defn_add("print", "{{{1}}}");
		fx.Init_defn_add("concat", "{{{1}}}{{{2}}}");
		fx.Init_defn_add("bool_str", "{{#ifeq:{{{1}}}|1|y|n}}");
		fx.Init_defn_add("mid_1", "{{print|[ {{concat|{{{1}}}|{{{2}}}}} ]}}");
		fx.Ctx().Defn_trace_(Xot_defn_trace_dbg.Instance);
	}
	@Test  public void Tmpl() {
		fx.tst_
			( "{{print|a|key1=b}}"
			, "*source"
			, "{{print|a|key1=b}}"
			, "  *invk"
			, "  {{print|a|key1=b}}"
			, "  *lnk: [[Template:print]]"
			, "  *args"
			, "       1: a"
			, "       2: b"
			, "    key1: b"
			, "  *eval"
			, "  a"
			, "*result"
			, "a"
			);
	}
	//@Test  public void Basic_b()		{fx.tst_("{{mwo_b|2}}"						, "[[Test page]]", "00 11 {{mwo_b|1}} -> b");}
}
