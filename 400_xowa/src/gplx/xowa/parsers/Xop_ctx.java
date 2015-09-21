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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import gplx.core.btries.*;
import gplx.xowa.langs.*;
import gplx.xowa.gui.*; import gplx.xowa.xtns.lst.*;
import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.wdatas.*;
import gplx.xowa.parsers.apos.*; import gplx.xowa.parsers.amps.*; import gplx.xowa.parsers.lnkes.*; import gplx.xowa.parsers.hdrs.*; import gplx.xowa.parsers.lists.*; import gplx.xowa.parsers.tblws.*; import gplx.xowa.parsers.paras.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.parsers.logs.*; import gplx.xowa.html.modules.popups.keeplists.*;
public class Xop_ctx {
	private Xop_ctx_wkr[] wkrs = new Xop_ctx_wkr[] {};
	Xop_ctx(Xowe_wiki wiki, Xoae_page page) {
		this.app = wiki.Appe(); this.msg_log = app.Msg_log(); this.tkn_mkr = app.Parser_mgr().Tkn_mkr();
		this.wiki = wiki; this.cur_page = page; this.lang = wiki.Lang();
		this.wkrs = new Xop_ctx_wkr[] {para, apos, xnde, list, lnki, hdr, amp, lnke, tblw, invk};
		for (Xop_ctx_wkr wkr : wkrs) wkr.Ctor_ctx(this);
		this.xnde_tag_regy = wiki.Mw_parser_mgr().Xnde_tag_regy();
	}
	public Xoae_app				App()				{return app;} private final Xoae_app app;
	public Xowe_wiki			Wiki()				{return wiki;} private final Xowe_wiki wiki;
	public Xol_lang				Lang()				{return lang;} private final Xol_lang lang;
	public Xop_tkn_mkr			Tkn_mkr()			{return tkn_mkr;} private final Xop_tkn_mkr tkn_mkr;
	public Xoae_page			Cur_page()			{return cur_page;} public void Cur_page_(Xoae_page v) {cur_page = v;} private Xoae_page cur_page;
	public byte					Parse_tid()			{return parse_tid;} public Xop_ctx Parse_tid_(byte v) {parse_tid = v; xnde_names_tid = v; return this;} private byte parse_tid = Xop_parser_.Parse_tid_null;
	public byte					Xnde_names_tid()	{return xnde_names_tid;} public Xop_ctx Xnde_names_tid_(byte v) {xnde_names_tid = v; return this;} private byte xnde_names_tid = Xop_parser_.Parse_tid_null;
	public Xop_amp_wkr			Amp()				{return amp;}	private final Xop_amp_wkr  amp  = new Xop_amp_wkr();
	public Xop_apos_wkr			Apos()				{return apos;}	private final Xop_apos_wkr apos = new Xop_apos_wkr();
	public Xop_lnke_wkr			Lnke()				{return lnke;}	private final Xop_lnke_wkr lnke = new Xop_lnke_wkr();
	public Xop_lnki_wkr			Lnki()				{return lnki;}	private final Xop_lnki_wkr lnki = new Xop_lnki_wkr();
	public Xop_hdr_wkr			Hdr()				{return hdr;}	private final Xop_hdr_wkr  hdr  = new Xop_hdr_wkr();
	public Xop_para_wkr			Para()				{return para;}	private final Xop_para_wkr para = new Xop_para_wkr();
	public Xop_list_wkr			List()				{return list;}	private final Xop_list_wkr list = new Xop_list_wkr();
	public Xop_tblw_wkr			Tblw()				{return tblw;}	private final Xop_tblw_wkr tblw = new Xop_tblw_wkr();
	public Xop_xnde_wkr			Xnde()				{return xnde;}	private final Xop_xnde_wkr xnde = new Xop_xnde_wkr();
	public Xot_invk_wkr			Invk()				{return invk;}	private final Xot_invk_wkr invk = new Xot_invk_wkr();
	public Xop_curly_wkr		Curly() 			{return curly;} private final Xop_curly_wkr curly = new Xop_curly_wkr();
	public Xop_xnde_tag_regy	Xnde_tag_regy()		{return xnde_tag_regy;} private final Xop_xnde_tag_regy xnde_tag_regy;	// PERF:demeter

	public boolean					Tmpl_load_enabled() {return tmpl_load_enabled;} public void Tmpl_load_enabled_(boolean v) {tmpl_load_enabled = v;} private boolean tmpl_load_enabled = true;
	public int					Tmpl_tkn_max()		{return tmpl_tkn_max;} public void Tmpl_tkn_max_(int v) {tmpl_tkn_max = v;} private int tmpl_tkn_max = Int_.Max_value;
	public Xop_keeplist_wiki	Tmpl_keeplist()		{return tmpl_keeplist;} public void Tmpl_keeplist_(Xop_keeplist_wiki v) {this.tmpl_keeplist = v;} private Xop_keeplist_wiki tmpl_keeplist;
	public boolean					Tmpl_args_parsing() {return tmpl_args_parsing;} public Xop_ctx Tmpl_args_parsing_(boolean v) {tmpl_args_parsing = v; return this;} private boolean tmpl_args_parsing;
	public Bry_bfr				Tmpl_output() {return tmpl_output;} public Xop_ctx Tmpl_output_(Bry_bfr v) {tmpl_output = v; return this;} private Bry_bfr tmpl_output;	// OBSOLETE: after tmpl_prepend_nl rewrite; DATE:2014-08-21
	public Xot_defn_trace		Defn_trace()		{return defn_trace;} public Xop_ctx Defn_trace_(Xot_defn_trace v) {defn_trace = v; return this;} private Xot_defn_trace defn_trace = Xot_defn_trace_null._;
	public boolean					Only_include_evaluate() {return only_include_evaluate;} public Xop_ctx Only_include_evaluate_(boolean v) {only_include_evaluate = v; return this;} private boolean only_include_evaluate;
	public Lst_section_nde_mgr	Lst_section_mgr()	{if (lst_section_mgr == null) lst_section_mgr = new Lst_section_nde_mgr(); return lst_section_mgr;} private Lst_section_nde_mgr lst_section_mgr;
	public Hash_adp_bry			Lst_page_regy()		{return lst_page_regy;} private Hash_adp_bry lst_page_regy;
	public boolean Ref_ignore() {return ref_ignore;} public Xop_ctx Ref_ignore_(boolean v) {ref_ignore = v; return this;} private boolean ref_ignore;	// NOTE: only applies to sub_ctx's created by <pages> and {{#lst}}; if true, does not add <ref> to page.Ref_mgr; DATE:2014-04-24
	public byte[] References_group() {return references_group;} public Xop_ctx References_group_(byte[] v) {references_group = v; return this;} private byte[] references_group;
	public boolean Tid_is_popup() {return tid_is_popup;} public void Tid_is_popup_(boolean v) {tid_is_popup = v;} private boolean tid_is_popup = false;
	public boolean Tid_is_image_map() {return tid_is_image_map;} public Xop_ctx Tid_is_image_map_(boolean v) {tid_is_image_map = v; return this;} private boolean tid_is_image_map;
	public Xop_log_invoke_wkr	Xtn__scribunto__invoke_wkr() {
		if (scrib_invoke_wkr == null)
			scrib_invoke_wkr = ((Scrib_xtn_mgr)(app.Xtn_mgr().Get_or_fail(Scrib_xtn_mgr.XTN_KEY))).Invoke_wkr();
		return scrib_invoke_wkr;
	}	private Xop_log_invoke_wkr scrib_invoke_wkr;
	public Xop_log_property_wkr Xtn__wikidata__property_wkr() {return app.Wiki_mgr().Wdata_mgr().Property_wkr();}
	public Gfo_msg_log			Msg_log()			{return msg_log;} private Gfo_msg_log msg_log;
	public Xop_ctx Clear() {
		cur_page.Clear();
		stack = Xop_tkn_itm_.Ary_empty;
		stack_len = stack_max = 0;
		app.Wiki_mgr().Wdata_mgr().Clear();
		if (lst_section_mgr != null) lst_section_mgr.Clear();
		if (lst_page_regy != null) lst_page_regy.Clear();
		tmpl_output = null;
		tmpl_args_parsing = false;
		return this;
	}
	public String Page_url_str() {
		try {return cur_page.Url().To_str();}
		catch (Exception e) {Err_.Noop(e); return "page_url shouldn't fail";}
	}
	public void Page_bgn(Xop_root_tkn root, byte[] src) {
		this.Msg_log().Clear(); cur_tkn_tid = Xop_tkn_itm_.Tid_null;
		empty_ignored = false;
		for (Xop_ctx_wkr wkr : wkrs) wkr.Page_bgn(this, root);
	}
	public void Page_end(Xop_root_tkn root, byte[] src, int src_len) {
		Stack_pop_til(root, src, 0, true, src_len, src_len, Xop_tkn_itm_.Tid_txt);
		for (Xop_ctx_wkr wkr : wkrs) wkr.Page_end(this, root, src, src_len);
	}
	public boolean		Lxr_make()	{return lxr_make;} public Xop_ctx Lxr_make_(boolean v) {lxr_make = v; return this;} private boolean lxr_make = false;
	public int		Lxr_make_txt_(int pos) {lxr_make = false; return pos;}
	public int		Lxr_make_log_(Gfo_msg_itm itm, byte[] src, int bgn_pos, int cur_pos) {lxr_make = false; msg_log.Add_itm_none(itm, src, bgn_pos, cur_pos); return cur_pos;}
	public boolean Empty_ignored() {return empty_ignored;}
	public void Empty_ignored_y_() {empty_ignored = true;} private boolean empty_ignored = false;
	public void Empty_ignored_n_() {empty_ignored = false;}
	public void Empty_ignore(Xop_root_tkn root, int empty_bgn) {
		int empty_end = root.Subs_len();
		for (int i = empty_bgn; i < empty_end; i++) {
			Xop_tkn_itm sub_tkn = root.Subs_get(i);
			sub_tkn.Ignore_y_grp_(this, root, i);
		}
		empty_ignored = false;
	}
	public byte	Cur_tkn_tid()		{return cur_tkn_tid;} private byte cur_tkn_tid = Xop_tkn_itm_.Tid_null;
	public void Subs_add_and_stack_tblw(Xop_root_tkn root, Xop_tblw_tkn owner_tkn, Xop_tkn_itm sub) {
		if (owner_tkn != null) owner_tkn.Tblw_subs_len_add_();	// owner_tkn can be null;EX: "{|" -> prv_tkn is null
		Subs_add_and_stack(root, sub);
	}
	public void Subs_add_and_stack(Xop_root_tkn root, Xop_tkn_itm sub) {this.Subs_add(root, sub); this.Stack_add(sub);}
	public void Subs_add(Xop_root_tkn root, Xop_tkn_itm sub) {
		switch (sub.Tkn_tid()) {
			case Xop_tkn_itm_.Tid_space: case Xop_tkn_itm_.Tid_tab: case Xop_tkn_itm_.Tid_newLine:
			case Xop_tkn_itm_.Tid_para:
				break;
			default:
				empty_ignored = false;
				break;
		}
		root.Subs_add(sub);
	}
	public void StackTkn_add(Xop_root_tkn root, Xop_tkn_itm sub) {
		root.Subs_add(sub);
		this.Stack_add(sub);
	}
	public void Stack_add(Xop_tkn_itm tkn) {
		int newLen = stack_len + 1;
		if (newLen > stack_max) {
			stack_max = newLen * 2;
			stack = (Xop_tkn_itm[])Array_.Resize(stack, stack_max);
		}
		stack[stack_len] = tkn;
		cur_tkn_tid = tkn.Tkn_tid();
		stack_len = newLen;
	}	private Xop_tkn_itm[] stack = Xop_tkn_itm_.Ary_empty; int stack_len = 0, stack_max = 0;
	public int Stack_len() {return stack_len;}
	public Xop_tkn_itm Stack_get_last()		{return stack_len == 0 ? null : stack[stack_len - 1];}
	public Xop_tkn_itm Stack_get(int i)		{return i < 0 || i >= stack_len ? null : stack[i];}
	public Xop_tblw_tkn Stack_get_tblw_tb() {// find any {| (exclude <table)
		for (int i = stack_len - 1; i > -1; i--) {
			Xop_tkn_itm tkn = stack[i];
			if (tkn.Tkn_tid() == Xop_tkn_itm_.Tid_tblw_tb) {
				Xop_tblw_tkn tkn_as_tbl = (Xop_tblw_tkn)tkn;
				if (!tkn_as_tbl.Tblw_xml()) return tkn_as_tbl;
			}
		}
		return null;
	}
	public Xop_tblw_tkn Stack_get_tbl_tb() {
		for (int i = stack_len - 1; i > -1; i--) {
			Xop_tkn_itm tkn = stack[i];
			switch (tkn.Tkn_tid()) {
				case Xop_tkn_itm_.Tid_tblw_tb:
					return (Xop_tblw_tkn)tkn;
				case Xop_tkn_itm_.Tid_xnde:
					Xop_xnde_tkn xnde_tkn = (Xop_xnde_tkn)tkn;
					switch (xnde_tkn.Tag().Id()) {
						case Xop_xnde_tag_.Tid_table:
							return (Xop_tblw_tkn)tkn;
					}
					break;
			}
		}
		return null;
	}
	public Xop_tblw_tkn Stack_get_tbl() {
		for (int i = stack_len - 1; i > -1; i--) {
			Xop_tkn_itm tkn = stack[i];
			switch (tkn.Tkn_tid()) {
				case Xop_tkn_itm_.Tid_tblw_tb:
				case Xop_tkn_itm_.Tid_tblw_tr:
				case Xop_tkn_itm_.Tid_tblw_td:
				case Xop_tkn_itm_.Tid_tblw_th:
				case Xop_tkn_itm_.Tid_tblw_tc:
					return (Xop_tblw_tkn)tkn;
				case Xop_tkn_itm_.Tid_xnde:
					Xop_xnde_tkn xnde_tkn = (Xop_xnde_tkn)tkn;
					switch (xnde_tkn.Tag().Id()) {
						case Xop_xnde_tag_.Tid_table:
						case Xop_xnde_tag_.Tid_tr:
						case Xop_xnde_tag_.Tid_td:
						case Xop_xnde_tag_.Tid_th:
						case Xop_xnde_tag_.Tid_caption:
							return (Xop_tblw_tkn)tkn;
					}
					break;
			}
		}
		return null;
	}
	public static final int Stack_not_found = -1;
	public boolean Stack_has(int typeId) {return Stack_idx_typ(typeId) != Stack_not_found;}
	public int Stack_idx_typ(int typeId)	{
		for (int i = stack_len - 1; i > -1; i--)
			if (stack[i].Tkn_tid() == typeId)
				return i; 
		return Stack_not_found;
	}
	public int Stack_idx_find_but_stop_at_tbl(int tid) {
		for (int i = stack_len - 1; i > -1	; i--) {
			Xop_tkn_itm tkn_itm = stack[i];
			int tkn_itm_tid = tkn_itm.Tkn_tid();
			switch (tkn_itm_tid) {
				case Xop_tkn_itm_.Tid_tblw_tb:	// NOTE: added DATE:2014-06-26
				case Xop_tkn_itm_.Tid_tblw_td:
				case Xop_tkn_itm_.Tid_tblw_th:
				case Xop_tkn_itm_.Tid_tblw_tc:
					return -1;
			}
			if (tkn_itm_tid == tid)
				return i;
		}
		return -1;
	}
	public Xop_tkn_itm Stack_get_typ(int tid) {
		for (int i = stack_len - 1; i > -1	; i--) {
			Xop_tkn_itm tkn = stack[i];
			if (tkn.Tkn_tid() == tid) return tkn;
		}
		return null;
	}
	public void Stack_del(Xop_tkn_itm del) {
		if (stack_len == 0) return;
		for (int i = stack_len - 1; i > -1; i--) {
			Xop_tkn_itm tkn = stack[i];
			if (tkn == del) {
				for (int j = i + 1; j < stack_len; j++) {
					stack[j - 1] = stack[j];
				}
				--stack_len;
				break;
			}
		}
	}
	public Xop_tkn_itm Stack_pop_til(Xop_root_tkn root, byte[] src, int til_idx, boolean include, int bgn_pos, int cur_pos, int closing_tkn_tid) {	// NOTE: closing_tkn_tid is a book-keeping variable to indicate who started auto-close; only used by xnde.AutoClose 
		if (stack_len == 0) return null;				// nothing to pop; return;
		int min_idx	= include ? til_idx - 1	: til_idx;	// if "include", auto-close tkn at til_idx; if not, auto-close to one before
		if (min_idx < -1) min_idx = -1;					// bounds-check; make sure til_idx was not -1, resulting in -2; NOTE: does not seem to be needed; DATE:2015-03-31
		Xop_tkn_itm rv = null;
		for (int i = stack_len - 1; i > min_idx; i--) {	// pop tkns going backwards
			rv = stack[i];
			Stack_auto_close(root, src, rv, bgn_pos, cur_pos, closing_tkn_tid);
		}
		Stack_pop_idx(til_idx);
		return include ? rv : stack[stack_len];			// if include, return popped_tkn; if not, return tkn before popped_tkn
	}
	public Xop_tkn_itm Stack_pop_before(Xop_root_tkn root, byte[] src, int til_idx, boolean include, int bgn_pos, int cur_pos, int closing_tkn_tid) {	// used by Xop_tblw_lxr to detect \n| in lnki; seems useful as well
		if (stack_len == 0) return null;
		int min_idx = include ? til_idx - 1 : til_idx;
		if (min_idx < -1) min_idx = -1;
		Xop_tkn_itm rv = null;
		for (int i = stack_len - 1; i > min_idx; i--) {
			rv = stack[i];
			Stack_auto_close(root, src, rv, bgn_pos, cur_pos, closing_tkn_tid);
		}
		return include ? rv : stack[stack_len]; // if include, return poppedTkn; if not, return tkn before poppedTkn
	}
	public void Stack_auto_close(Xop_root_tkn root, byte[] src, Xop_tkn_itm tkn, int bgn_pos, int cur_pos, int closing_tkn_tid) {
		int src_len = src.length;
		switch (tkn.Tkn_tid()) {
			case Xop_tkn_itm_.Tid_newLine: break;	// NOOP: just a marker
			case Xop_tkn_itm_.Tid_list: list.AutoClose(this, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, tkn); break;
			case Xop_tkn_itm_.Tid_xnde: xnde.AutoClose(this, root, src, src_len, bgn_pos, cur_pos, tkn, closing_tkn_tid); break;
			case Xop_tkn_itm_.Tid_apos: apos.AutoClose(this, src, src_len, bgn_pos, cur_pos, tkn); break;
			case Xop_tkn_itm_.Tid_lnke: lnke.AutoClose(this, src, src_len, bgn_pos, cur_pos, tkn); break;
			case Xop_tkn_itm_.Tid_hdr:  hdr.AutoClose(this, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, tkn); break;
			case Xop_tkn_itm_.Tid_tblw_tb:
			case Xop_tkn_itm_.Tid_tblw_tr:
			case Xop_tkn_itm_.Tid_tblw_td:
			case Xop_tkn_itm_.Tid_tblw_th:
			case Xop_tkn_itm_.Tid_tblw_tc: tblw.AutoClose(this, root, src, src_len, bgn_pos, cur_pos, tkn); break;
			case Xop_tkn_itm_.Tid_lnki: lnki.Auto_close(this, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, tkn); break;
			case Xop_tkn_itm_.Tid_pre: para.AutoClose(this, tkn_mkr, root, src, src_len, bgn_pos, cur_pos, tkn); break;
		}
	}
	public void Stack_pop_idx(int tilIdx) {
		stack_len = tilIdx < 0 ? 0 : tilIdx;
		cur_tkn_tid = stack_len == 0 ? Xop_tkn_itm_.Tid_null : stack[stack_len - 1].Tkn_tid();
	}
	public void Stack_pop_last() {	// used primarily by lnke to remove lnke from stack
		--stack_len;
		cur_tkn_tid = stack_len == 0 ? Xop_tkn_itm_.Tid_null : stack[stack_len - 1].Tkn_tid();
	}
	public void CloseOpenItms(Xop_ctx ctx, Xop_tkn_mkr tkn_mkr, Xop_root_tkn root, byte[] src, int src_len, int bgn_pos, int cur_pos) {
		int stack_pos = -1, stack_len = ctx.Stack_len(); boolean stop = false;
		for (int i = 0; i < stack_len; i++) {			// loop over stack
			Xop_tkn_itm prv_tkn = ctx.Stack_get(i);
			switch (prv_tkn.Tkn_tid()) {					// find first list/hdr; close everything until this
				case Xop_tkn_itm_.Tid_list:
				case Xop_tkn_itm_.Tid_hdr:
					stack_pos = i; stop = true; break;
			}
			if (stop) break;
		}
		if (stack_pos == -1) return;
		ctx.Stack_pop_til(root, src, stack_pos, true, bgn_pos, cur_pos, Xop_tkn_itm_.Tid_txt);
	}
	public static Xop_ctx new_(Xowe_wiki wiki) {
		Xop_ctx rv = new Xop_ctx(wiki, Xoae_page.new_(wiki, Xoa_ttl.parse(wiki, Xoa_page_.Main_page_bry)));	// HACK: use "Main_Page" to put in valid page title
		return rv;
	}
	public static Xop_ctx new_sub_(Xowe_wiki wiki) {return new_sub_(wiki, wiki.Parser_mgr().Ctx().cur_page);}
	public static Xop_ctx new_sub_(Xowe_wiki wiki, Xoae_page page) {	// TODO: new_sub_ should reuse ctx's page; callers who want new_page should call new_sub_page_; DATE:2014-04-10
		Xop_ctx ctx = wiki.Parser_mgr().Ctx();
		Xop_ctx rv = new Xop_ctx(wiki, page);
		new_copy(ctx, rv);
		return rv;
	}
	public static Xop_ctx new_sub_page_(Xowe_wiki wiki, Xop_ctx ctx, Hash_adp_bry lst_page_regy) {
		Xop_ctx rv = new Xop_ctx(wiki, ctx.cur_page);
		new_copy(ctx, rv);
		rv.lst_page_regy = lst_page_regy;				// NOTE: must share ref for lst only (do not share for sub_(), else stack overflow)
		return rv;
	}
	private static void new_copy(Xop_ctx src, Xop_ctx trg) {
		trg.Lnki().File_wkr_(src.Lnki().File_wkr());	// always share file_wkr between sub contexts
		trg.tmpl_output = src.tmpl_output;				// share bfr for optimization purposes
	}		
}
