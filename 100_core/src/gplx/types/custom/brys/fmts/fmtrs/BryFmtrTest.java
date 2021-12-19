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
package gplx.types.custom.brys.fmts.fmtrs;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.utls.StringUtl;
import gplx.frameworks.tests.GfoTstr;
import org.junit.Test;
public class BryFmtrTest {
	private final BryFmtrTstr tstr = new BryFmtrTstr();
	@Test public void Text()          {tstr.Clear().Fmt("a").Test("a");}
	@Test public void Idx1()          {tstr.Clear().Fmt("~{0}").Args("a").Test("a");}
	@Test public void Idx3()          {tstr.Clear().Fmt("~{0}~{1}~{2}").Args("a", "b", "c").Test("abc");}
	@Test public void IdxMix()        {tstr.Clear().Fmt("a~{0}c~{1}e").Args("b", "d").Test("abcde");}
	@Test public void IdxMissing()    {tstr.Clear().Fmt("~{0}").Test("~{0}");}
	@Test public void KeyBasic()      {tstr.Clear().Fmt("~{key}").Keys("key").Args("a").Test("a");}
	@Test public void KeyMult()       {tstr.Clear().Fmt("~{key1}~{key2}").Keys("key1", "key2").Args("a", "b").Test("ab");}
	@Test public void KeyRepeat()     {tstr.Clear().Fmt("~{key1}~{key1}").Keys("key1").Args("a").Test("aa");}
	@Test public void Mix()           {tstr.Clear().Fmt("~{key1}~{1}").Keys("key1", "key2").Args("a", "b").Test("ab");}
	@Test public void Simple() {
		tstr.Clear().Fmt("0~{key1}1~{key2}2").Keys("key1", "key2").Args(".", ",").Test("0.1,2");
	}
	@Test public void Cmd() {
		BryFmtrEvalMgrMok mok = new BryFmtrEvalMgrMok();
		BryFmtr fmtr = BryFmtr.New("0~{key1}2~{<>3<>}4", "key1").EvalMgrSet(mok);
		GfoTstr.Eq("012~{<>3<>}4", fmtr.BldToStrMany("1"));
		mok.EnabledSet(true);
		GfoTstr.Eq("01234", fmtr.BldToStrMany("1"));
	}
	@Test public void BldBfrManyAndSetFmt() {
		tstr.Bld_bfr_many_and_set_fmt("a~{0}c", ObjectUtl.Ary("b"), "abc");
	}
	@Test public void EscapeTilde() {
		GfoTstr.Eq("~~~~~~", BryFmtr.EscapeTilde("~~~"));
	}
}
class BryFmtrEvalMgrMok implements BryFmtrEvalMgr {
	public boolean Enabled() {return enabled;} public void EnabledSet(boolean v) {enabled = v;} private boolean enabled;
	public byte[] Eval(byte[] cmd) {
		return enabled ? cmd : null;
	}
}
class BryFmtrTstr {
	private final BryFmtr fmtr = BryFmtr.New();
	private final BryWtr bfr = BryWtr.New();
	private Object[] args;
	public BryFmtrTstr Clear() {fmtr.FmtSet(StringUtl.Empty).KeysSet(StringUtl.Empty); args = ObjectUtl.AryEmpty; return this;}
	public BryFmtrTstr Fmt(String fmt) {fmtr.FmtSet(fmt); return this;}
	public BryFmtrTstr Keys(String... args) {fmtr.KeysSet(args); return this;}
	public BryFmtrTstr Args(Object... args) {this.args = args; return this;}
	public void Test(String expd) {
		fmtr.BldToBfrMany(bfr, args);
		GfoTstr.Eq(expd, bfr.ToStrAndClear());
	}
	public void Bld_bfr_many_and_set_fmt(String fmt, Object[] args, String expd) {
		fmtr.FmtSet(fmt);
		fmtr.BldBfrManyAndSetFmt(args);
		GfoTstr.Eq(expd, StringUtl.NewA7(fmtr.Fmt()));
	}
}
