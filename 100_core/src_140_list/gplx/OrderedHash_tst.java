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
public class OrderedHash_tst {
	@Before public void setup() {
		hash = OrderedHash_.new_();
	}
	@Test  public void FetchAt() {
		hash.Add("key1", "val1");
		Tfds.Eq("val1", hash.FetchAt(0));
	}
	@Test  public void iterator() {
		hash.Add("key2", "val2");
		hash.Add("key1", "val1");

		ListAdp list = ListAdp_.new_();
		for (Object val : hash)
			list.Add(val);
		Tfds.Eq("val2", list.FetchAt(0));
		Tfds.Eq("val1", list.FetchAt(1));
	}
	OrderedHash hash;
}
