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
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
import org.junit.*;
import gplx.xowa.xtns.wdatas.*;
public class Scrib_lib_wikibase_entity_tst {
	@Before public void init() {
		fxt.Clear_for_lib();
		lib = fxt.Core().Lib_wikibase_entity().Init();
	}	private Scrib_invoke_func_fxt fxt = new Scrib_invoke_func_fxt(); private Scrib_lib lib;
	@Test  public void GetGlobalSiteId() {
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase_entity.Invk_getGlobalSiteId, Object_.Ary_empty, "enwiki");
	}
	@Test  public void FormatPropertyValues() {
		Wdata_wiki_mgr_fxt wdata_fxt = new Wdata_wiki_mgr_fxt().Init(fxt.Parser_fxt(), false);
		wdata_fxt.Init__docs__add(wdata_fxt.Wdoc_bldr("Q2").Add_claims(wdata_fxt.Make_claim_str(3, "P3_val")).Xto_wdoc());
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase_entity.Invk_formatPropertyValues, Object_.Ary("Q2", "P3")		, "P3_val");	// lookup by id
		wdata_fxt.Init_pids_add("en", "P3_val", 3);
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase_entity.Invk_formatPropertyValues, Object_.Ary("Q2", "P3_val")	, "P3_val");	// lookup by name
	}
	@Test  public void FormatPropertyValues__not_found() {	// PURPOSE: should return "" not null; PAGE:fr.s:Auteur:Henri_Bergson; DATE:2014-08-13
		fxt.Test_scrib_proc_str(lib, Scrib_lib_wikibase_entity.Invk_formatPropertyValues, Object_.Ary("Q2", "P3"), "");
	}
}	
