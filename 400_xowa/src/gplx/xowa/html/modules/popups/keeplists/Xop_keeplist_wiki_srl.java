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
package gplx.xowa.html.modules.popups.keeplists; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.modules.*; import gplx.xowa.html.modules.popups.*;
import gplx.srls.dsvs.*;
import gplx.core.regxs.*;
import gplx.xowa.langs.cases.*;
public class Xop_keeplist_wiki_srl extends Dsv_wkr_base {
	private Xol_case_mgr case_mgr; private Xow_wiki wiki;
	private byte[] wiki_bry;
	private byte[] keeps_bry;
	private byte[] skips_bry;
	private int rules_count;
	public Xop_keeplist_wiki_srl(Xow_wiki wiki) {this.wiki = wiki; this.case_mgr = wiki.Lang().Case_mgr();}
	@Override public Dsv_fld_parser[] Fld_parsers() {return new Dsv_fld_parser[] {Dsv_fld_parser_.Bry_parser, Dsv_fld_parser_.Bry_parser, Dsv_fld_parser_.Bry_parser};}
	@Override public boolean Write_bry(Dsv_tbl_parser parser, int fld_idx, byte[] src, int bgn, int end) {
		switch (fld_idx) {
			case 0: wiki_bry  = Xoa_ttl.Replace_spaces(case_mgr.Case_build_lower(Bry_.Mid(src, bgn, end))); return true;
			case 1: keeps_bry = Xoa_ttl.Replace_spaces(case_mgr.Case_build_lower(Bry_.Mid(src, bgn, end))); return true;
			case 2: skips_bry = Xoa_ttl.Replace_spaces(case_mgr.Case_build_lower(Bry_.Mid(src, bgn, end))); return true;
			default: return false;
		}
	}
	@Override public void Commit_itm(Dsv_tbl_parser parser, int pos) {
		if (wiki_bry == null) throw parser.Err_row_bgn("wikis missing", pos);
		if (keeps_bry == null) throw parser.Err_row_bgn("keeps missing", pos);
		if (skips_bry == null) throw parser.Err_row_bgn("skips missing", pos);
		if (!Bry_.Eq(wiki_bry, wiki.Domain_bry())) return;
		Xop_keeplist_wiki tmpl_keeplist = Get_tmpl_keeplist();
		Gfo_pattern[] keeps = Gfo_pattern.Parse_to_ary(keeps_bry);
		Gfo_pattern[] skips = Gfo_pattern.Parse_to_ary(skips_bry);
		Xop_keeplist_rule rule = new Xop_keeplist_rule(keeps, skips);
		tmpl_keeplist.Rules_add(rule);
		wiki_bry = skips_bry = keeps_bry = null;
		++rules_count;
	}
	@Override public void Load_by_bry_end() {
		if (rules_count == 0) return;	// NOTE: keeplist set in global cfg, so fires when each wiki loads; if loading wiki does not match keeplist, then noop; DATE:2014-07-05
		Xop_keeplist_wiki tmpl_keeplist = Get_tmpl_keeplist();
		tmpl_keeplist.Rules_seal();
		rules_count = 0;
	}
	public Xop_keeplist_wiki Get_tmpl_keeplist() {
		Xow_popup_parser popup_parser = wiki.Html_mgr().Module_mgr().Popup_mgr().Parser();
		Xop_keeplist_wiki rv = popup_parser.Tmpl_keeplist();
		if (rv == null) {
			rv = new Xop_keeplist_wiki(wiki);
			popup_parser.Tmpl_keeplist_(rv);
		}
		return rv;
	}
}
