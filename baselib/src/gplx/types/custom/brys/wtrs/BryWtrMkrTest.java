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
package gplx.types.custom.brys.wtrs;
import gplx.types.basics.utls.ByteUtl;
import gplx.frameworks.tests.GfoTstr;
import org.junit.Before;
import org.junit.Test;
public class BryWtrMkrTest {
	private final BryBfrMkrTstr tstr = new BryBfrMkrTstr();
	@Before public void setup() {tstr.Clear();}
	@Test public void Get1() {tstr.Clear().Get().TestUsed(0);}
	@Test public void Get2() {tstr.Clear().Get().Get().TestUsed(0, 1);}
	@Test public void Get3() {tstr.Clear().Get().Get().Get().TestUsed(0, 1, 2);}
	@Test public void Rls()  {tstr.Clear().Get().Rls(0).TestUsed();}
	@Test public void RlsSkip1() {
		tstr.Clear().Get().Get().Rls(0).TestUsed(-1, 1);
		tstr.Get().TestUsed(0, 1);
	}
	@Test public void RlsSkip2() {
		tstr.Clear().Get().Get().Get().Rls(1).Rls(0).TestUsed(-1, -1, 2);
		tstr.Get().TestUsed(0, -1, 2);
		tstr.Get().TestUsed(0, 1, 2);
		tstr.Get().TestUsed(0, 1, 2, 3);
	}
	@Test public void GetRls() {    // PURPOSE: defect in which last rls failed b/c was not doing ++ if rv existed
		tstr.Clear().Get().Rls(0).Get().Get().Rls(1).Rls(0).TestUsed();
	}
}
class BryBfrMkrTstr {
	private final BryBfrMkrMgr mkr = new BryBfrMkrMgr(ByteUtl.Zero, 32);
	public BryBfrMkrTstr Clear()    {mkr.Clear(); return this;}
	public BryBfrMkrTstr Get()      {mkr.Get(); return this;}
	public BryBfrMkrTstr Rls(int i) {mkr.Used()[i].MkrRls(); return this;}
	public BryBfrMkrTstr TestUsed(int... expd) {
		int actl_len = mkr.UsedLen();
		int[] actl = new int[actl_len];
		for (int i = 0; i < actl_len; i++) {
			BryWtr bfr = mkr.Used()[i];
			int actl_val = bfr == null ? -2 : bfr.MkrIdx();
			actl[i] = actl_val;
		}
		GfoTstr.EqAry(expd, actl);
		return this;
	}
}
