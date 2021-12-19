/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.custom.brys.fmts.itms;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.wtrs.args.BryBfrArg;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.ObjectUtl;
import org.junit.Test;
public class BryFmtTest {
	private final BryFmtTstr tstr = new BryFmtTstr();
	@Test public void Text()         {tstr.Clear().Fmt("a").Test("a");}
	@Test public void KeyBasic()     {tstr.Clear().Fmt("~{key}").Vals("a").Test("a");}
	@Test public void KeyMult()      {tstr.Clear().Fmt("~{key1}~{key2}").Vals("a", "b").Test("ab");}
	@Test public void KeyRepeat()    {tstr.Clear().Fmt("~{key1}~{key1}").Vals("a").Test("aa");}
	@Test public void KeyMissing()   {tstr.Clear().Fmt("~{key}").Test("~{key}");}
	@Test public void Tilde()        {tstr.Clear().Fmt("~~~~").Test("~~");}
	@Test public void Simple()       {tstr.Clear().Fmt("0~{key1}1~{key2}2").Vals(".", ",").Test("0.1,2");}
	@Test public void Arg()          {tstr.Clear().Fmt("~{custom}").Args("custom", new BryArgMock(123)).Test("123");}
	@Test public void Keys()         {tstr.Clear().Fmt("~{b}~{c}~{a}").Keys("a", "b", "c").Vals("a", "b", "c").Test("bca");}
}
class BryArgMock implements BryBfrArg {
	private int num;
	public BryArgMock(int num) {this.num = num;}
	public void AddToBfr(BryWtr bfr) {bfr.AddIntVariable(num);}
}
class BryFmtTstr {
	private final BryFmt fmt = new BryFmt(BryUtl.Empty, BryUtl.AryEmpty, BfrFmtArg.Ary_empty);
	private final BryWtr bfr = BryWtr.New();
	private Object[] vals;
	public BryFmtTstr Clear() {vals = ObjectUtl.AryEmpty; return this;}
	public BryFmtTstr Fmt(String s) {fmt.Fmt_(s); return this;}
	public BryFmtTstr Vals(Object... vals) {this.vals = vals; return this;}
	public BryFmtTstr Args(String key, BryBfrArg arg) {fmt.Args_(new BfrFmtArg(BryUtl.NewU8(key), arg)); return this;}
	public BryFmtTstr Keys(String... keys) {fmt.Keys_(BryUtl.Ary(keys)); return this;}
	public void Test(String expd) {
		fmt.Bld_many(bfr, vals);
		GfoTstr.Eq(expd, bfr.ToStrAndClear());
	}
}
