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
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import org.junit.*;
public class Xol_vnt_regy_tst {
	private final Xol_vnt_regy_fxt fxt = new Xol_vnt_regy_fxt();
	@Test  public void Calc() {
		fxt.Test_calc(String_.Ary("zh")				, 1);
		fxt.Test_calc(String_.Ary("zh", "zh-hans")	, 3);
		fxt.Test_calc(String_.Ary("zh", "bad")		, 1);
	}
	@Test  public void Match() {
		String[] lang_chain = fxt.Make_lang_chain_cn();	// zh;zh-hans;zh-hant;zh-cn
		fxt.Test_match_any(Bool_.Y, lang_chain
		, String_.Ary("zh")
		, String_.Ary("zh-hans")
		, String_.Ary("zh-hant")
		, String_.Ary("zh-cn")
		, String_.Ary("zh", "zh-hans")
		, String_.Ary("zh-cn", "zh-hk")
		);
		fxt.Test_match_any(Bool_.N, lang_chain
		, String_.Ary_empty
		, String_.Ary("bad")
		, String_.Ary("zh-hk")
		, String_.Ary("zh-hk", "zh-sg")
		);
	}
	@Test   public void Match_2() {
		fxt.Test_match_any(Bool_.Y, String_.Ary("zh-hans")
		, String_.Ary("zh", "zh-hant", "zh-hans")
		);
	}
}
