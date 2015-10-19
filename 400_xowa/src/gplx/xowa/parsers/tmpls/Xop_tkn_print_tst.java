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
public class Xop_tkn_print_tst {
	private Xop_fxt fxt = new Xop_fxt();
	@Test  public void Text()			{tst_Print("a ''b'' c [[d]] e");}
	@Test  public void Prm()			{tst_Print("{{{1}}}");}
	@Test  public void Prm_dflt()		{tst_Print("{{{1|a}}}");}
	@Test  public void Prm_dflt_prm()	{tst_Print("{{{1|{{{2}}}}}}");}
	@Test  public void Tmpl()			{tst_Print("{{a}}");}
	@Test  public void Tmpl_arg()		{tst_Print("{{a|1|2}}");}
	@Test  public void Tmpl_arg_prm()	{tst_Print("{{a|1|{{{1}}}}}");}
	@Test  public void Tmpl_arg_tmpl()	{tst_Print("{{a|1|{{b}}}}");}
	@Test  public void Tmpl_pf()		{tst_Print("{{#expr:1}}");}
	private void tst_Print(String raw) {
		Xop_ctx ctx = fxt.Ctx();
		byte[] raw_bry = Bry_.new_u8(raw);
		Xot_defn_tmpl defn = fxt.run_Parse_tmpl(Bry_.Empty, raw_bry);
		Xot_fmtr_prm raw_fmtr = new Xot_fmtr_prm();
		defn.Root().Tmpl_fmt(ctx, raw_bry, raw_fmtr);
		raw_fmtr.Print(tst_Print_bb);
		Tfds.Eq(raw, tst_Print_bb.To_str_and_clear());
	}	private Bry_bfr tst_Print_bb = Bry_bfr.new_();
}
