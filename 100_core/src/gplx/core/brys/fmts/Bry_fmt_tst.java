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
package gplx.core.brys.fmts; import gplx.*; import gplx.core.*; import gplx.core.brys.*;
import org.junit.*;
public class Bry_fmt_tst {
	private final    Bry_fmt_fxt fxt = new Bry_fmt_fxt();
	@Test  public void Text()			{fxt.Clear().Fmt("a").Test("a");}
	@Test  public void Key__basic() 	{fxt.Clear().Fmt("~{key}").Vals("a").Test("a");}
	@Test  public void Key__mult()		{fxt.Clear().Fmt("~{key1}~{key2}").Vals("a", "b").Test("ab");}
	@Test  public void Key__repeat()	{fxt.Clear().Fmt("~{key1}~{key1}").Vals("a").Test("aa");}
	@Test  public void Key__missing() 	{fxt.Clear().Fmt("~{key}").Test("~{key}");}
	@Test  public void Tilde()			{fxt.Clear().Fmt("~~~~").Test("~~");}
	@Test  public void Simple()			{fxt.Clear().Fmt("0~{key1}1~{key2}2").Vals(".", ",").Test("0.1,2");}
	@Test  public void Arg()			{fxt.Clear().Fmt("~{custom}").Args("custom", new Bfr_fmt_arg_mok(123)).Test("123");}
	@Test  public void Keys()			{fxt.Clear().Fmt("~{b}~{c}~{a}").Keys("a", "b", "c").Vals("a", "b", "c").Test("bca");}
}
class Bfr_fmt_arg_mok implements Bfr_arg {
	private int num;
	public Bfr_fmt_arg_mok(int num) {this.num = num;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		bfr.Add_int_variable(num);
	}
}
class Bry_fmt_fxt {
	private final    Bry_fmt fmt = new Bry_fmt(Bry_.Empty, Bry_.Ary_empty, Bfr_fmt_arg.Ary_empty);
	private final    Bry_bfr bfr = Bry_bfr_.New();
	private Object[] vals;
	public Bry_fmt_fxt Clear() {vals = Object_.Ary_empty; return this;}
	public Bry_fmt_fxt Fmt(String s) {fmt.Fmt_(s); return this;}
	public Bry_fmt_fxt Vals(Object... vals) {this.vals = vals; return this;}
	public Bry_fmt_fxt Args(String key, Bfr_arg arg) {fmt.Args_(new Bfr_fmt_arg(Bry_.new_u8(key), arg)); return this;}
	public Bry_fmt_fxt Keys(String... keys) {fmt.Keys_(Bry_.Ary(keys)); return this;}
	public void Test(String expd) {
		fmt.Bld_many(bfr, vals);
		Tfds.Eq(expd, bfr.To_str_and_clear());
	}
}
