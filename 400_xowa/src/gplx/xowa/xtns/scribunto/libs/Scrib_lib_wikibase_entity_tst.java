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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*;
import gplx.xowa.xtns.wbases.*;
public class Scrib_lib_wikibase_entity_tst {
	@Before public void init() {
		fxt.Clear_for_lib();
		lib = fxt.Core().Lib_wikibase_entity().Init();
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	@Test  public void GetGlobalSiteId() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase_entity.Invk_getGlobalSiteId, Object_.Ary_empty, "enwiki");
	}
	@Test  public void GetLanguageCode() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase_entity.Invk_getLanguageCode, Object_.Ary_empty, "en");
	}
	@Test  public void FormatPropertyValues() {
		Wdata_wiki_mgr_fxt wdata_fxt = new Wdata_wiki_mgr_fxt().Init(fxt.Parser_fxt(), false);

		// lookup by id
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("Q2").Add_claims(wdata_fxt.Make_claim_string(3, "P3_val")).Xto_wdoc());
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase_entity.Invk_formatPropertyValues, Object_.Ary("Q2", "P3")		, "P3_val");

		// lookup by name
		wdata_fxt.Init_pids_add("en", "P3_val", 3);
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase_entity.Invk_formatPropertyValues, Object_.Ary("Q2", "P3_val")	, "P3_val");
	}
	@Test  public void FormatPropertyValues__not_found() {	// PURPOSE: should return "" not null; PAGE:fr.s:Auteur:Henri_Bergson; DATE:2014-08-13
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase_entity.Invk_formatPropertyValues, Object_.Ary("Q2", "P3"), "");
	}
}	
