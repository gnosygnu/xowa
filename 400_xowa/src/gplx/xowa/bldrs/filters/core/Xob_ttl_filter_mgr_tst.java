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
package gplx.xowa.bldrs.filters.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.filters.*;
import org.junit.*;
public class Xob_ttl_filter_mgr_tst {
	@Before public void init() {fxt.Clear();} private final Xob_ttl_filter_mgr_fxt fxt = new Xob_ttl_filter_mgr_fxt();
	@Test  public void One() {
		fxt.Init_load_exclude("A");
		fxt.Init_load_include("B");
		fxt.Test_match_exclude_y("A");
		fxt.Test_match_exclude_n("B", "C");
		fxt.Test_match_include_y("B");
		fxt.Test_match_include_n("A", "C");
	}
}
class Xob_ttl_filter_mgr_fxt {
	private final Xob_ttl_filter_mgr mgr = new Xob_ttl_filter_mgr();
	public void Clear() {
		mgr.Clear();
	}
	public void Init_load_exclude(String itm) {mgr.Load(Bool_.Y, Bry_.new_u8(itm));}
	public void Init_load_include(String itm) {mgr.Load(Bool_.N, Bry_.new_u8(itm));}
	public void Test_match_exclude_y(String... itms) {Test_match(Bool_.Y, Bool_.Y, itms);}
	public void Test_match_exclude_n(String... itms) {Test_match(Bool_.Y, Bool_.N, itms);}
	public void Test_match_include_y(String... itms) {Test_match(Bool_.N, Bool_.Y, itms);}
	public void Test_match_include_n(String... itms) {Test_match(Bool_.N, Bool_.N, itms);}
	private void Test_match(boolean exclude, boolean expd, String... itms) {
		for (String itm : itms) {
			byte[] itm_bry = Bry_.new_u8(itm);
			if (exclude)
				Tfds.Eq(expd, mgr.Match_exclude(itm_bry), itm);
			else
				Tfds.Eq(expd, mgr.Match_include(itm_bry), itm);
		}
	}
} 
