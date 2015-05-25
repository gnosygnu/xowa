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
public class z011_IntObjHash_tst {
	@Before public void setup() {
		hash = new IntObjHash_base();
	}	IntObjHash_base hash;
	@Test  public void Empty() {
		tst_Count(0);
		tst_Fetch(1, null);
	}
	@Test  public void Add() {
		hash.Add(1, "1");
		tst_Count(1);
		tst_Fetch(1, "1");
		tst_Fetch(2, null);
	}
	@Test  public void Del() {
		hash.Add(1, "1");

		hash.Del(1);
		tst_Count(0);
		tst_Fetch(1, null);
	}
	@Test  public void Clear() {
		hash.Add(1, "1");
		hash.Add(32, "32");
		tst_Fetch(1, "1");
		tst_Fetch(32, "32");
		tst_Count(2);

		hash.Clear();
		tst_Count(0);
		tst_Fetch(2, null);
		tst_Fetch(32, null);
	}
	@Test  public void Add_bug() { // fails after expanding ary, and fetching at key=n*16
		hash.Add(1, "1");
		tst_Count(1);
		tst_Fetch(1, "1");
		tst_Fetch(15, null);	// works
		tst_Fetch(17, null);	// works
		tst_Fetch(16, null);	// used to fail
	}
	void tst_Fetch(int key, Object expd) {Tfds.Eq(expd, hash.Get_by(key));}
	void tst_Count(int expd) {Tfds.Eq(expd, hash.Count());}
}
