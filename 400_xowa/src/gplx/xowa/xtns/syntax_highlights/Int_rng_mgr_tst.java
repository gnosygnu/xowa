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
package gplx.xowa.xtns.syntax_highlights; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*;
public class Int_rng_mgr_tst {
	private final Int_rng_mgr_fxt fxt = new Int_rng_mgr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Val()	{fxt.Test_parse_y("2")				.Test_match_y(2)					.Test_match_n(1, 3);}
	@Test  public void Rng()	{fxt.Test_parse_y("2-5")			.Test_match_y(2, 3, 4, 5)			.Test_match_n(0, 1, 6);}
	@Test  public void Many()	{fxt.Test_parse_y("1,3-5,7,9-10")	.Test_match_y(1, 3, 4, 5, 7, 9, 10)	.Test_match_n(0, 2, 6, 8, 11);}
}
class Int_rng_mgr_fxt {
	private Int_rng_mgr_base mgr;
	public void Clear() {
		if (mgr == null)
			mgr = new Int_rng_mgr_base();
		mgr.Clear();
	}
	public Int_rng_mgr_fxt Test_parse_y(String raw) {return Test_parse(raw, true);}
	public Int_rng_mgr_fxt Test_parse_n(String raw) {return Test_parse(raw, false);}
	public Int_rng_mgr_fxt Test_parse(String raw, boolean expd) {Tfds.Eq(expd, mgr.Parse(Bry_.new_a7(raw))); return this;}
	public Int_rng_mgr_fxt Test_match_y(int... v) {return Test_match(v, true);}
	public Int_rng_mgr_fxt Test_match_n(int... v) {return Test_match(v, false);}
	public Int_rng_mgr_fxt Test_match(int[] ary, boolean expd) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Tfds.Eq(expd, mgr.Match(ary[i]), Int_.To_str(ary[i]));
		}
		return this;
	}
}
