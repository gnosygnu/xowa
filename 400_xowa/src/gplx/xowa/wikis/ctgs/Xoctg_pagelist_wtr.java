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
package gplx.xowa.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.brys.fmtrs.*;
import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.users.history.*; import gplx.xowa.wikis.data.tbls.*;
public class Xoctg_pagelist_wtr {
	private Xoctg_pagelist_mgr pagelist_mgr = new Xoctg_pagelist_mgr();
	public Xoctg_pagelist_wtr Init_by_app(Xoae_app app) {pagelist_mgr.Init_by_app(app, this); return this;}
	public void Write(Bry_bfr bfr, Xowe_wiki wiki, Xoae_page page, Xoh_wtr_ctx hctx) {
		Xowd_page_itm[] page_ary = wiki.Db_mgr().Load_mgr().Load_ctg_list(page.Category_list());
		Print_hidden(bfr, wiki, page_ary, hctx);
	}
	public void Print_hidden(Bry_bfr bfr, Xowe_wiki wiki, Xowd_page_itm[] page_ary, Xoh_wtr_ctx hctx) {
		int len = page_ary.length;
		for (int i = 0; i < len; i++) {
			Xowd_page_itm page = page_ary[i];
			Xowd_category_itm ctg_xtn = (Xowd_category_itm)page.Xtn();				
			Xoctg_pagelist_grp list = ctg_xtn != null && ctg_xtn.Hidden() ? pagelist_mgr.Grp_hidden() : pagelist_mgr.Grp_normal(); 
			list.Itms().Itms_add(page);
		}
		pagelist_mgr.Init_by_wiki(wiki, hctx);
		pagelist_mgr.Bfr_arg__add(bfr);
		pagelist_mgr.Grp_hidden().Itms().Itms_clear();
		pagelist_mgr.Grp_normal().Itms().Itms_clear();
	}
	public Bry_fmtr Fmtr_all() {return fmtr_all;} private Bry_fmtr fmtr_all = Bry_fmtr.new_(String_.Concat_lines_nl
	( "<div id=\"catlinks\" class=\"catlinks\">~{grp_normal}~{grp_hidden}"
	, "</div>"
	), "grp_normal", "grp_hidden"
	);
	public Bry_fmtr Fmtr_grp_normal() {return fmtr_grp_normal;} private Bry_fmtr fmtr_grp_normal = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "" 
	,   "<div id=\"mw-normal-catlinks\" class=\"mw-normal-catlinks\">"
	,     "<a href=\"/wiki/~{ctg_help_page}\" title=\"~{ctg_help_page}\">~{ctg_text}</a>:"
	,     "<ul>~{grp_itms}"
	,     "</ul>"
	,   "</div>"
	), "ctg_help_page", "ctg_text", "grp_itms"
	);
	public Bry_fmtr Fmtr_grp_hidden() {return fmtr_grp_hidden;} private Bry_fmtr fmtr_grp_hidden = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( "" 
	,   "<div id=\"mw-hidden-catlinks\" class=\"mw-hidden-catlinks mw-hidden-cats-user-shown\">~{hidden_ctg_txt}:"
	,     "<ul>~{grp_itms}"
	,     "</ul>"
	,   "</div>"
	), "hidden_ctg_txt", "grp_itms"
	);
	public Bry_fmtr Fmtr_itm() {return fmtr_itm;} private Bry_fmtr fmtr_itm = Bry_fmtr.new_(String_.Concat_lines_nl_skip_last
	( ""
	,       "<li>"
	,         "<a~{lnki_cls} href=\"~{lnki_href}\" title=\"~{lnki_ttl}\">~{lnki_text}</a>"
	,       "</li>"
	), "lnki_cls", "lnki_href", "lnki_ttl", "lnki_text"
	);
}
