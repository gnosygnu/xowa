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
package gplx;
import org.junit.*;
public class Gfo_evt_mgr_tst {
	@Before public void setup() {
		pub = make_(); sub = make_();
	}	MockEvObj pub, sub;
	@Test  public void Basic() {
		Gfo_evt_mgr_.Sub_same(pub, "ev1", sub);
		Gfo_evt_mgr_.Pub_val(pub, "ev1", "val1");			
		sub.tst_Handled("val1");
	}
	@Test  public void None() {// make sure no subscribers does not cause exception
		Gfo_evt_mgr_.Sub_same(pub, "ev1", sub);
		Gfo_evt_mgr_.Pub_val(pub, "ev2", "val1");	//ev2 does not exist
		sub.tst_Handled();
	}
	@Test  public void Lnk() {
		MockEvObj mid = make_();
		mid.Evt_mgr().Lnk(pub);
		Gfo_evt_mgr_.Sub_same(mid, "ev1", sub);
		Gfo_evt_mgr_.Pub_val(pub, "ev1", "val1");
		sub.tst_Handled("val1");
	}
	@Test  public void RlsSub() {
		this.Basic();

		Gfo_evt_mgr_.Rls_sub(sub);
		Gfo_evt_mgr_.Pub_val(pub, "ev1", "val1");
		sub.tst_Handled();
	}
	@Test  public void RlsPub() {
		this.Basic();

		Gfo_evt_mgr_.Rls_sub(pub);
		Gfo_evt_mgr_.Pub_val(pub, "ev1", "val1");
		sub.tst_Handled();
	}
	MockEvObj make_() {return new MockEvObj();}
}
class MockEvObj implements Gfo_evt_itm {
	public Gfo_evt_mgr Evt_mgr() {return eventMgr;} Gfo_evt_mgr eventMgr;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		handled.Add(m.ReadStr("v"));
		return this;
	}
	List_adp handled = List_adp_.New();
	public void tst_Handled(String... expd) {
		Tfds.Eq_ary_str(expd, handled.To_str_ary());
		handled.Clear();
	}
	public MockEvObj(){eventMgr = new Gfo_evt_mgr(this);}
}
