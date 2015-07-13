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
package gplx.gfml; import gplx.*;
import org.junit.*;
public class z051_GfmlFldPool_keyed_tst {
	@Before public void setup() {
		GfmlTypeMakr makr = GfmlTypeMakr.new_();
		GfmlType type = makr.MakeSubType("point", "x", "y", "z");
		fldPool = GfmlFldPool.new_(type);
	}	GfmlFldPool fldPool;
	@Test  public void PopByKey_inOrder() {
		tst_Keyed_PopByKey(fldPool, "x", "x", "y", "z");
	}
	@Test  public void PopByKey_outOfOrder() {
		tst_Keyed_PopByKey(fldPool, "y", "y", "x", "z");
	}
	@Test  public void PopByKey_unknown() {
		tst_Keyed_PopByKey(fldPool, "a", GfmlItmKeys.NullKey, "x", "y", "z");
	}
	@Test  public void PopByKey_alreadyRemoved() {
		tst_Keyed_PopByKey(fldPool, "x", "x", "y", "z");
		tst_Keyed_PopByKey(fldPool, "x", GfmlItmKeys.NullKey, "y", "z");
	}
	@Test  public void PopByKey_depleted() {
		tst_Keyed_PopByKey(fldPool, "x", "x", "y", "z");
		tst_Keyed_PopByKey(fldPool, "y", "y", "z");
		tst_Keyed_PopByKey(fldPool, "z", "z");
		tst_Keyed_PopByKey(fldPool, "x", GfmlItmKeys.NullKey);
	}
	@Test  public void PopNext_inOrder() {
		tst_Keyed_PopNext(fldPool, "x", "y", "z");
		tst_Keyed_PopNext(fldPool, "y", "z");
		tst_Keyed_PopNext(fldPool, "z");
		try {
			tst_Keyed_PopNext(fldPool, GfmlItmKeys.NullKey);
			Tfds.Fail("should have failed");
		}
		catch (Exception exc) {Exc_.Noop(exc);}
	}
	@Test  public void PopByKey_PopNext() {
		tst_Keyed_PopByKey(fldPool, "y", "y", "x", "z");
		tst_Keyed_PopNext(fldPool, "x", "z");
	}
	void tst_Keyed_PopByKey(GfmlFldPool fldPool, String key, String expdFldId, String... expdSubs) {
		GfmlFld actlFld = fldPool.Keyed_PopByKey(key);
		Tfds.Eq(expdFldId, actlFld.Name());
		String[] actlSubs = new String[fldPool.Keyd_Count()];
		for (int i = 0; i < actlSubs.length; i++) {
			actlSubs[i] = fldPool.Keyd_FetchAt(i).Name();
		}
		Tfds.Eq_ary(expdSubs, actlSubs);
	}
	void tst_Keyed_PopNext(GfmlFldPool fldPool, String expdFldId, String... expdSubs) {
		GfmlFld actlFld = fldPool.Keyed_PopNext();
		Tfds.Eq(expdFldId, actlFld.Name());
		String[] actlSubs = new String[fldPool.Keyd_Count()];
		for (int i = 0; i < actlSubs.length; i++) {
			actlSubs[i] = fldPool.Keyd_FetchAt(i).Name();
		}
		Tfds.Eq_ary(expdSubs, actlSubs);
	}
}
