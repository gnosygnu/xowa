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
package gplx.core.lists.rings; import gplx.*; import gplx.core.*; import gplx.core.lists.*;
import org.junit.*;
public class Ring__string__tst {
	private final    Ring__string__fxt fxt = new Ring__string__fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Basic() {
		fxt.Clear().Max_(3).Push_many("a")								.Expd("a");
		fxt.Clear().Max_(3).Push_many("a", "b")							.Expd("a", "b");
		fxt.Clear().Max_(3).Push_many("a", "b", "c")					.Expd("a", "b", "c");
		fxt.Clear().Max_(3).Push_many("a", "b", "c", "d")				.Expd("b", "c", "d");
		fxt.Clear().Max_(3).Push_many("a", "b", "c", "d", "e")			.Expd("c", "d", "e");
		fxt.Clear().Max_(3).Push_many("a", "b", "c", "d", "e", "f")		.Expd("d", "e", "f");
	}
}
class Ring__string__fxt {
	Ring__string ring = new Ring__string();
	public Ring__string__fxt Clear() {ring.Clear(); return this;}
	public Ring__string__fxt Max_(int v) {ring.Max_(v); return this;}
	public Ring__string__fxt Push_many(String... ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++)
 				ring.Push(ary[i]); 
		return this;
	}
	public Ring__string__fxt Expd(String... expd) {
		Tfds.Eq_ary_str(expd, ring.Xto_str_ary());
		return this;
	}
}
