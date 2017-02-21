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
package gplx.core.brys; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Bry_bfr_mkr_tst {
	private final Bry_bfr_mkr_fxt fxt = new Bry_bfr_mkr_fxt();
	@Before public void setup() {fxt.Clear();}
	@Test   public void Get_1() 		{fxt.Clear().Get().Test__used(0);}
	@Test   public void Get_2()			{fxt.Clear().Get().Get().Test__used(0, 1);}
	@Test   public void Get_3()			{fxt.Clear().Get().Get().Get().Test__used(0, 1, 2);}
	@Test   public void Rls()			{fxt.Clear().Get().Rls(0).Test__used();}
	@Test   public void Rls_skip_1() {
		fxt.Clear().Get().Get().Rls(0).Test__used(-1, 1);
		fxt.Get().Test__used(0, 1);
	}
	@Test   public void Rls_skip_2_1() {
		fxt.Clear().Get().Get().Get().Rls(1).Rls(0).Test__used(-1, -1, 2);
		fxt.Get().Test__used(0, -1, 2);
		fxt.Get().Test__used(0, 1, 2);
		fxt.Get().Test__used(0, 1, 2, 3);
	}
	@Test   public void Get_rls_get() {	// PURPOSE: defect in which last rls failed b/c was not doing ++ if rv existed
		fxt.Clear().Get().Rls(0).Get().Get().Rls(1).Rls(0).Test__used();
	}
}
class Bry_bfr_mkr_fxt {
	private final Bry_bfr_mkr_mgr mkr = new Bry_bfr_mkr_mgr(Byte_.Zero, 32);
	public Bry_bfr_mkr_fxt Clear()		{mkr.Clear(); return this;}
	public Bry_bfr_mkr_fxt Get()		{mkr.Get(); return this;}
	public Bry_bfr_mkr_fxt Rls(int i)	{mkr.Used()[i].Mkr_rls(); return this;}
	public Bry_bfr_mkr_fxt Test__used(int... expd) {
		int actl_len = mkr.Used_len();
		int[] actl = new int[actl_len];
		for (int i = 0; i < actl_len; i++) {
			Bry_bfr bfr = mkr.Used()[i];
			int actl_val = bfr == null ? -2 : bfr.Mkr_idx();
			actl[i] = actl_val;
		}
		Tfds.Eq_ary(expd, actl);
		return this;
	}
}
