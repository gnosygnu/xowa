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
package gplx.xowa.xtns.scribunto.libs.wikibases; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.libs.*;
import org.junit.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.*;
import gplx.xowa.xtns.wbases.claims.enums.*;
public class GetEntityStatements__tst {
	private final    Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt();
	private final    Wdata_wiki_mgr_fxt wdata_fxt = new Wdata_wiki_mgr_fxt();
	private Scrib_lib lib;
	@Before public void init() {
		fxt.Clear_for_invoke();
		lib = fxt.Core().Lib_wikibase().Init();
		wdata_fxt.Init(fxt.Parser_fxt(), false);
	}
	@Test  public void Get_preferred() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2")
			.Add_claims
			( wdata_fxt.Make_claim_string(3, "3c").Rank_tid_(Wbase_claim_rank_.Tid__deprecated)
			, wdata_fxt.Make_claim_string(3, "3b").Rank_tid_(Wbase_claim_rank_.Tid__normal)
			, wdata_fxt.Make_claim_string(3, "3a").Rank_tid_(Wbase_claim_rank_.Tid__preferred)
			).Xto_wdoc());
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_wikibase.Invk_getEntityStatements, Object_.Ary("q2", "P3", "best"), String_.Concat_lines_nl_skip_last
		( "1="
		, "  P3="
		, "    1="
		, "      id=P3"
		, "      mainsnak="
		, "        datavalue="
		, "          type=string"
		, "          value=3a"
		, "        property=P3"
		, "        snaktype=value"
		, "        datatype=string"
		, "      rank=preferred"
		, "      type=statement"
		));
	}
	@Test  public void Get_normal_when_no_preferred() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2")
			.Add_claims
			( wdata_fxt.Make_claim_string(3, "3c").Rank_tid_(Wbase_claim_rank_.Tid__deprecated)
			, wdata_fxt.Make_claim_string(3, "3b").Rank_tid_(Wbase_claim_rank_.Tid__normal)
			).Xto_wdoc());
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_wikibase.Invk_getEntityStatements, Object_.Ary("q2", "P3", "best"), String_.Concat_lines_nl_skip_last
		( "1="
		, "  P3="
		, "    1="
		, "      id=P3"
		, "      mainsnak="
		, "        datavalue="
		, "          type=string"
		, "          value=3b"
		, "        property=P3"
		, "        snaktype=value"
		, "        datatype=string"
		, "      rank=normal"
		, "      type=statement"
		));
	}
	@Test  public void Never_get_deprecated() {
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("q2")
			.Add_claims
			( wdata_fxt.Make_claim_string(3, "3c").Rank_tid_(Wbase_claim_rank_.Tid__deprecated)
			).Xto_wdoc());
		fxt.Test_scrib_proc_str_ary(lib, Scrib_lib_wikibase.Invk_getEntityStatements, Object_.Ary("q2", "P3", "best"), String_.Concat_lines_nl_skip_last
		("1=<<NULL>>"));
	}
}
