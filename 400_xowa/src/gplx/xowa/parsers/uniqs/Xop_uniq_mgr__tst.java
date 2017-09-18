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
package gplx.xowa.parsers.uniqs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.core.tests.*;
public class Xop_uniq_mgr__tst {
	private final    Xop_uniq_mgr__fxt fxt = new Xop_uniq_mgr__fxt();
	@Before public void init() {fxt.Init();}
	@Test  public void Test__random_bry() {
		fxt.Init_random_int_ary(Int_.Ary(240563374, 22728940, 1451248133));
		fxt.Test__uniq_bry_new("'\"`UNIQ-E56B4AE15AD0EC68");

		fxt.Init_random_int_ary(Int_.Ary(1363621437, 426295411, 421041101));
		fxt.Test__uniq_bry_new("'\"`UNIQ-147363D968C07391");
	}
	@Test   public void Add_and_get() {
		String expd_key = "'\"`UNIQ--item-0-QINU`\"'";
		fxt.Test__add("a", expd_key);
		fxt.Test__get(expd_key, "a");
	}
	@Test   public void Parse__basic() {
		String expd_key = "'\"`UNIQ--item-0-QINU`\"'";
		fxt.Test__add("_b_", expd_key);
		fxt.Test__parse("a" + expd_key + "c", "a_b_c");
	}
	@Test   public void Parse__recurse() {
		String key_0 = "'\"`UNIQ--item-0-QINU`\"'";
		String key_1 = "'\"`UNIQ--item-1-QINU`\"'";
		String key_2 = "'\"`UNIQ--item-2-QINU`\"'";
		fxt.Test__add("0", key_0);
		fxt.Test__add("1-" + key_0 + "-1", key_1);
		fxt.Test__add("2-" + key_1 + "-2", key_2);
		fxt.Test__parse("3-" + key_2 + "-3", "3-2-1-0-1-2-3");
	}
	@Test   public void Convert() {
		String key = "'\"`UNIQ--item-0-QINU`\"'";
		fxt.Test__add("2", key);
		fxt.Test__convert("1" + key + "3", "123");
	}
	@Test   public void Convert__many() {
		String key_0 = "'\"`UNIQ--item-0-QINU`\"'";
		String key_1 = "'\"`UNIQ--item-1-QINU`\"'";
		fxt.Test__add("0", key_0);
		fxt.Test__add("1", key_1);
		fxt.Test__convert("a " + key_0 + " b " + key_1 + " c", "a 0 b 1 c");
	}
}
class Xop_uniq_mgr__fxt {
	private final    Xop_uniq_mgr mgr = new Xop_uniq_mgr();
	public Xop_uniq_mgr__fxt Init_random_int_ary(int... v) {mgr.Random_int_ary_(v); return this;}
	public void Init() {mgr.Clear();}
	public void Test__uniq_bry_new(String expd) {
		Gftest.Eq__str(expd, String_.new_a7(mgr.Uniq_bry_new()), "unique_bry");
	}
	public void Test__add(String raw, String expd) {
		Gftest.Eq__str(expd, String_.new_a7(mgr.Add(Bry_.new_a7("item"), Bry_.new_a7(raw))), "add");
	}
	public void Test__get(String key, String expd) {
		Gftest.Eq__str(expd, String_.new_a7(mgr.Get(Bry_.new_a7(key))), "get");
	}
	public void Test__parse(String raw, String expd) {
		Gftest.Eq__str(expd, String_.new_a7(mgr.Parse(Bry_.new_a7(raw))), "parse");
	}
	public void Test__convert(String raw, String expd) {
		Gftest.Eq__str(expd, String_.new_a7(mgr.Convert(Bry_.new_a7(raw))), "convert");
	}
}
