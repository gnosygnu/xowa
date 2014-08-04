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
package gplx;
import org.junit.*;
public class GfoEvMgr_tst {
	@Before public void setup() {
		pub = make_(); sub = make_();
	}	MockEvObj pub, sub;
	@Test  public void Basic() {
		GfoEvMgr_.SubSame(pub, "ev1", sub);
		GfoEvMgr_.PubVal(pub, "ev1", "val1");			
		sub.tst_Handled("val1");
	}
	@Test  public void None() {// make sure no subscribers does not cause exception
		GfoEvMgr_.SubSame(pub, "ev1", sub);
		GfoEvMgr_.PubVal(pub, "ev2", "val1");	//ev2 does not exist
		sub.tst_Handled();
	}
	@Test  public void Lnk() {
		MockEvObj mid = make_();
		mid.EvMgr().Lnk(pub);
		GfoEvMgr_.SubSame(mid, "ev1", sub);
		GfoEvMgr_.PubVal(pub, "ev1", "val1");
		sub.tst_Handled("val1");
	}
	@Test  public void RlsSub() {
		this.Basic();

		GfoEvMgr_.RlsSub(sub);
		GfoEvMgr_.PubVal(pub, "ev1", "val1");
		sub.tst_Handled();
	}
	@Test  public void RlsPub() {
		this.Basic();

		GfoEvMgr_.RlsSub(pub);
		GfoEvMgr_.PubVal(pub, "ev1", "val1");
		sub.tst_Handled();
	}
	MockEvObj make_() {return new MockEvObj();}
}
class MockEvObj implements GfoEvObj {
	public GfoEvMgr EvMgr() {return eventMgr;} GfoEvMgr eventMgr;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		handled.Add(m.ReadStr("v"));
		return this;
	}
	ListAdp handled = ListAdp_.new_();
	public void tst_Handled(String... expd) {
		Tfds.Eq_ary_str(expd, handled.XtoStrAry());
		handled.Clear();
	}
	public MockEvObj(){eventMgr = GfoEvMgr.new_(this);}
}
