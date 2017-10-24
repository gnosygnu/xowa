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
public class Xob_ttl_filter_mgr_srl_tst {
	@Before public void init() {fxt.Clear();} private final Xob_ttl_filter_mgr_srl_fxt fxt = new Xob_ttl_filter_mgr_srl_fxt();
	@Test  public void One()			{fxt.Test_parse("a"			, 1, "a");}
	@Test  public void Two()			{fxt.Test_parse("a\nb"		, 2, "a", "b");}
	@Test  public void Comment()		{fxt.Test_parse("|x"		, 0);}
	@Test  public void Comment_many()	{fxt.Test_parse("|x||"		, 0);}
	@Test  public void Blank()			{fxt.Test_parse("\n"		, 0);}
	@Test  public void Mix()			{
		fxt.Test_parse(String_.Concat_lines_nl_skip_last
		( "|comment 1"
		, "a"
		, ""
		, "|comment 2"
		, "b"
		)
		, 2, "a", "b")
		;}
}
class Xob_ttl_filter_mgr_srl_fxt {
	private final Xob_ttl_filter_mgr_srl mgr = new Xob_ttl_filter_mgr_srl();
	private final Hash_adp_bry hash = Hash_adp_bry.cs();
	public void Clear() {
		hash.Clear();
	}
	public void Test_parse(String src, int expd_count, String... expd_itms) {
		mgr.Init(hash);
		mgr.Load_by_bry(Bry_.new_u8(src));
		Tfds.Eq(expd_count, hash.Count());
		int expd_len = expd_itms.length;
		for (int i = 0; i < expd_len; ++i) {
			String expd_itm = expd_itms[i];
			Tfds.Eq_true(hash.Has(Bry_.new_u8(expd_itm)));
		}
	}
} 
