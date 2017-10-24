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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_tkn_print_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
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
	}	private Bry_bfr tst_Print_bb = Bry_bfr_.New();
}
