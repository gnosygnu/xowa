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
package gplx.xowa.specials.search; import gplx.*; import gplx.xowa.*; import gplx.xowa.specials.*;
import gplx.core.primitives.*; import gplx.xowa.wikis.*;
public class Xosrh_core implements GfoInvkAble, Xows_page {
	public Xosrh_core(Xowe_wiki wiki) {this.wiki = wiki;} 
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	@gplx.Internal protected Xosrh_args_mgr Args_mgr() {return args_mgr;} private Xosrh_args_mgr args_mgr = new Xosrh_args_mgr();
	@gplx.Internal protected Xosrh_html_mgr Html_mgr() {return html_mgr;} private Xosrh_html_mgr html_mgr = new Xosrh_html_mgr();
	public Xosrh_page_mgr Page_mgr() {return page_mgr;} private Xosrh_page_mgr page_mgr = new Xosrh_page_mgr();
	public Xosrh_rslt_grp Cur_grp() {return cur_grp;} private Xosrh_rslt_grp cur_grp;
	public void Special_gen(Xoa_url url, Xoae_page page, Xowe_wiki wiki, Xoa_ttl ttl) {
		if (wiki.Domain_tid() == Xow_domain_.Tid_int_home) return;	// do not allow search in home wiki; will throw null ref error b/c no search_ttl dirs
		Xog_search_suggest_mgr search_suggest_mgr = wiki.Appe().Gui_mgr().Search_suggest_mgr();
		args_mgr.Clear();
		Gfo_url_arg[] args_default = search_suggest_mgr.Args_default();
		if (args_default.length > 0) args_mgr.Parse(args_default);
		args_mgr.Parse(url.Args());
		byte[] search_bry = args_mgr.Search_bry();
		if (search_bry == null) {
			search_bry = ttl.Leaf_txt_wo_qarg();	// leaf has page name; EX: Special:Search/William Shakespeare
			args_mgr.Search_bry_(search_bry);
		}
		if (	search_suggest_mgr.Auto_wildcard()
			&&	wiki.Db_mgr().Tid() == gplx.xowa.dbs.Xodb_mgr_sql.Tid_sql	// only apply to sql
			&&	Bry_finder.Find_fwd(search_bry, Byte_ascii.Asterisk) == -1		// search term does not have asterisk
			)
			search_bry = Bry_.Add_w_dlm(Byte_ascii.Asterisk, search_bry, Bry_.Empty);
		url.Page_bry_(Bry_.Add(Bry_.new_ascii_("Special:Search/"), search_bry));	// HACK: need to re-set Page b/c href_parser does not eliminate qargs; DATE:2013-02-08
		Xoa_ttl page_ttl = Xoa_ttl.parse_(wiki, search_bry); 
		Xoae_page find_page = page;
		if (!Bry_.Eq(search_bry, Bry_page_name))				// do not lookup page else stack overflow; happens when going directly to Special:Search (from history)
			find_page = wiki.Data_mgr().Get_page(page_ttl, false);	// lookup page
		if (find_page.Missing() || url.Search_fulltext()) {	// page not found, or explicit_search invoked
			Bry_bfr tmp_bfr = wiki.Utl__bfr_mkr().Get_k004();
			int page_idx = args_mgr.Page_idx();
			Sort_tid(args_mgr.Sort_tid());
			page_mgr.Ns_mgr_(args_mgr.Ns_mgr());
			cur_grp = page_mgr.Search(tmp_bfr, wiki, search_bry, page_idx, page_mgr);
			html_mgr.Bld_html(tmp_bfr, this, cur_grp, search_bry, page_idx, page_mgr.Pages_len());
			page.Data_raw_(tmp_bfr.Mkr_rls().Xto_bry_and_clear());
			wiki.ParsePage(page, false);	// do not clear else previous Search_text will be lost
			page.Html_data().Xtn_search_text_(search_bry);
		}
		else {														// page found; return it;
			wiki.ParsePage(find_page, true);
			page.Data_raw_(find_page.Data_raw());
			if (page.Root() != null)	// NOTE: null when going from w:Earth -> q:Earth; DATE:2013-03-20
				page.Root().Data_htm_(find_page.Root().Data_htm());
			page.Ttl_(page_ttl).Url_(Xoa_url.new_(wiki.Domain_bry(), page_ttl.Full_txt())).Redirected_(true);
		}
	}	static final byte[] Bry_page_name = Bry_.new_ascii_("Special:Search");
	private void Sort_tid(byte v) {
		switch (v) {
			case Xosrh_rslt_itm_sorter.Tid_none:
				if (!page_mgr.Prv_search_is_same(args_mgr.Search_bry(), args_mgr.Ns_mgr().Xto_hash_key()))	// new search; reset sort to len dsc
					page_mgr.Sort_tid_(Xosrh_rslt_itm_sorter.Tid_len_dsc);
				break;
			default:
				page_mgr.Sort_tid_(v);
				break;
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_itms_per_page_))			page_mgr.Itms_per_page_(m.ReadInt("v"));
		else if	(ctx.Match(k, Invk_html))					return html_mgr;
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_itms_per_page_ = "itms_per_page_", Invk_html = "html";
	public static final byte Match_tid_all = 0, Match_tid_bgn = 1;
	public static final byte Version_null = 0, Version_1 = 1, Version_2 = 2;
}
class Xosrh_args_mgr {
	public byte[] Search_bry() {return search_bry;} public Xosrh_args_mgr Search_bry_(byte[] v) {search_bry = v; return this;} private byte[] search_bry;
	public int Page_idx() {return page_idx;} private int page_idx;
	public byte Sort_tid() {return sort_tid;} public Xosrh_args_mgr Sort_tid_(byte v) {sort_tid = v; return this;} private byte sort_tid;
	public Xosrh_ns_mgr Ns_mgr() {return ns_mgr;} private Xosrh_ns_mgr ns_mgr = new Xosrh_ns_mgr();
	public Xosrh_args_mgr Clear() {
		this.search_bry = null;
		page_idx = 0;
		sort_tid = Xosrh_rslt_itm_sorter.Tid_none;
		ns_mgr.Clear();
		return this;
	}
	public void Parse(Gfo_url_arg[] args_mgr) {
		if (args_mgr == null) return;
		int len = args_mgr.length;
		for (int i = 0; i < len; i++) {
			Gfo_url_arg arg = args_mgr[i];
			byte[] key = arg.Key_bry();
			Object tid = url_args.Fetch(key);
			if (tid != null) {
				switch (((Byte_obj_val)tid).Val()) {
					case Arg_search: 		search_bry 	= Bry_.Replace(arg.Val_bry(), Byte_ascii.Plus, Byte_ascii.Space); break;
					case Arg_page_idx: 		page_idx 	= Bry_.Xto_int_or(arg.Val_bry(), 0); break;
					case Arg_sort: 			sort_tid	= Xosrh_rslt_itm_sorter.parse_(String_.new_ascii_(arg.Val_bry())); break;			
					default:				break;
				}
			}
			else {
				if (Bry_.HasAtBgn(key, Ns_bry))		// check for ns*; EX: &ns0=1&ns8=1; NOTE: lowercase only
					ns_mgr.Add_by_parse(key, arg.Val_bry());
			}
		}
		ns_mgr.Add_main_if_empty();
	}	private static final byte Arg_search = 0, Arg_page_idx = 1, Arg_sort = 2;
	private static byte[] Ns_bry = Bry_.new_ascii_("ns");
	private static final Hash_adp_bry url_args = Hash_adp_bry.ci_ascii_()
		.Add_str_byte("xowa_page_index", Arg_page_idx)
		.Add_str_byte("xowa_sort", Arg_sort)
		.Add_str_byte("search", Arg_search)
	;
}
