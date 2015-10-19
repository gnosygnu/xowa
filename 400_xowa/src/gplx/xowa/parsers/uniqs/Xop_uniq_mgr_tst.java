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
package gplx.xowa.parsers.uniqs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xop_uniq_mgr_tst {
	private final Xop_uniq_mgr_fxt fxt = new Xop_uniq_mgr_fxt();
	@Before public void init() {fxt.Init();}
	@Test  public void Test_random_bry() {
		fxt.Init_random_int_ary(Int_.Ary(240563374, 22728940, 1451248133));
		fxt.Test_uniq_bry_new("UNIQE56B4AE15AD0EC68");

		fxt.Init_random_int_ary(Int_.Ary(1363621437, 426295411, 421041101));
		fxt.Test_uniq_bry_new("UNIQ147363D968C07391");
	}
	@Test   public void Add_and_get() {
		String expd_key = "UNIQ-item-0--QINU";
		fxt.Test_add("a", expd_key);
		fxt.Test_get(expd_key, "a");
	}
	@Test   public void Parse__basic() {
		String expd_key = "UNIQ-item-0--QINU";
		fxt.Test_add("_b_", expd_key);
		fxt.Test_parse("a" + expd_key + "c", "a_b_c");
	}
	@Test   public void Parse__recurse() {
		String key_0 = "UNIQ-item-0--QINU";
		String key_1 = "UNIQ-item-1--QINU";
		String key_2 = "UNIQ-item-2--QINU";
		fxt.Test_add("0", key_0);
		fxt.Test_add("1-" + key_0 + "-1", key_1);
		fxt.Test_add("2-" + key_1 + "-2", key_2);
		fxt.Test_parse("3-" + key_2 + "-3", "3-2-1-0-1-2-3");
	}
}
class Xop_uniq_mgr_fxt {
	private final Xop_uniq_mgr mgr = new Xop_uniq_mgr();
	public Xop_uniq_mgr_fxt Init_random_int_ary(int... v) {mgr.Random_int_ary_(v); return this;}
	public void Init() {mgr.Clear();}
	public void Test_uniq_bry_new(String expd) {
		Tfds.Eq_str(expd, String_.new_a7(mgr.Uniq_bry_new()), "unique_bry");
	}
	public void Test_add(String raw, String expd) {
		Tfds.Eq_str(expd, String_.new_a7(mgr.Add(Bry_.new_a7(raw))), "add");
	}
	public void Test_get(String key, String expd) {
		Tfds.Eq_str(expd, String_.new_a7(mgr.Get(Bry_.new_a7(key))), "get");
	}
	public void Test_parse(String raw, String expd) {
		Tfds.Eq_str(expd, String_.new_a7(mgr.Parse(Bry_.new_a7(raw))), "get");
	}
}
