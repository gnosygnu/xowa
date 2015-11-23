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
public class Ordered_hash_tst {
	@Before public void setup() {
		hash = Ordered_hash_.New();
	}
	@Test  public void Get_at() {
		hash.Add("key1", "val1");
		Tfds.Eq("val1", hash.Get_at(0));
	}
	@Test  public void iterator() {
		hash.Add("key2", "val2");
		hash.Add("key1", "val1");

		List_adp list = List_adp_.new_();
		for (Object val : hash)
			list.Add(val);
		Tfds.Eq("val2", list.Get_at(0));
		Tfds.Eq("val1", list.Get_at(1));
	}
	Ordered_hash hash;
}
