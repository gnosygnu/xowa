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
package gplx.xowa.wikis.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import org.junit.*; import gplx.xowa.wikis.nss.*;
public class Xow_ttl__anchor_tst {
	@Before public void init() {fxt.Reset();} private Xow_ttl_fxt fxt = new Xow_ttl_fxt();
	@Test   public void Page__main()			{fxt.Init_ttl("a#b")			.Expd_full_txt("A").Expd_page_txt("A").Expd_anch_txt("b").Test();}
	@Test   public void Page__ns()				{fxt.Init_ttl("Help:A#B")		.Expd_full_txt("Help:A").Expd_ns_id(Xow_ns_.Tid__help).Expd_page_txt("A").Expd_anch_txt("B").Test();}
	@Test   public void Pageless__main()		{fxt.Init_ttl("#a")				.Expd_full_txt("").Expd_page_txt("").Expd_anch_txt("a").Test();}
	@Test   public void Xwiki() {	// PURPOSE: :#batch:Main Page causes ttl to fail b/c # is treated as anchor; DATE:2013-01-02
		fxt.Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_a7("#batch"), Bry_.new_a7("none"));
		fxt.Init_ttl(":#batch:Main Page").Expd_full_txt("Main Page").Test();
	}
	@Test   public void Anch_has_angles() {// PURPOSE: angles in anchor should be encoded; DATE: 2013-01-23
		fxt.Init_ttl("A#b<c>d").Expd_full_txt("A").Expd_anch_txt("b.3Cc.3Ed").Test();
	}
	@Test   public void Anch_has_slash() {	// PURPOSE: slash in anchor was being treated as a subpage; DATE:2014-01-14
		fxt.Init_ttl("A#b/c").Expd_full_txt("A").Expd_anch_txt("b/c").Expd_leaf_txt("A").Test();	// NOTE: Leaf_txt should be Page_txt; used to fail
	}
	@Test   public void Decode_ncr() {	// PURPOSE: convert &#x23; to #; PAGE:en.s:The_English_Constitution_(1894) DATE:2014-09-07
		fxt.Init_ttl("A&#x23;b").Expd_full_txt("A").Expd_page_txt("A").Expd_anch_txt("b").Test();
		fxt.Init_ttl("A&#35;b").Expd_full_txt("A").Expd_page_txt("A").Expd_anch_txt("b").Test();
		fxt.Init_ttl("A&#36;b").Expd_full_txt("A$b").Expd_page_txt("A$b").Expd_anch_txt("").Test();
	}
	@Test   public void Multiple() {// PURPOSE: handle multiple anchors; en.w:Grand_Central_Terminal; DATE:2015-12-31
		fxt.Init_ttl("A#b#c").Expd_page_txt("A").Expd_anch_txt("b#c").Test();
	}
}