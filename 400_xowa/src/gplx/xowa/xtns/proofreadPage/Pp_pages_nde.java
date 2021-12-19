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
package gplx.xowa.xtns.proofreadPage;
import gplx.langs.html.HtmlEntityCodes;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.frameworks.objects.Cancelable_;
import gplx.libs.dlgs.Gfo_usr_dlg;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.arrays.IntAryUtl;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.StringUtl;
import gplx.types.custom.brys.fmts.fmtrs.BryFmtr;
import gplx.types.basics.wrappers.ByteVal;
import gplx.core.primitives.Gfo_number_parser;
import gplx.types.basics.wrappers.IntRef;
import gplx.types.basics.wrappers.IntVal;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.apps.cfgs.Xowc_xtn_pages;
import gplx.xowa.htmls.core.htmls.Xoh_html_wtr;
import gplx.xowa.htmls.core.htmls.Xoh_wtr_ctx;
import gplx.xowa.mediawiki.XophpBool_;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.Xop_parser;
import gplx.xowa.parsers.Xop_parser_;
import gplx.xowa.parsers.Xop_parser_tid_;
import gplx.xowa.parsers.Xop_root_tkn;
import gplx.xowa.parsers.amps.Xop_amp_mgr;
import gplx.xowa.parsers.htmls.Mwh_atr_itm;
import gplx.xowa.parsers.htmls.Mwh_atr_itm_owner1;
import gplx.xowa.parsers.lnkis.Xop_lnki_tkn;
import gplx.xowa.parsers.lnkis.files.Xop_file_logger_;
import gplx.xowa.parsers.tmpls.Xop_tkn_;
import gplx.xowa.parsers.xndes.Xop_xnde_tkn;
import gplx.xowa.wikis.data.tbls.Xowd_page_itm;
import gplx.xowa.wikis.nss.Xow_ns;
import gplx.xowa.wikis.pages.Xopg_tmpl_prepend_mgr;
import gplx.xowa.xtns.Xox_mgr_base;
import gplx.xowa.xtns.Xox_xnde;
import gplx.xowa.xtns.Xox_xnde_;
import gplx.xowa.xtns.lst.Lst_pfunc_itm;
import gplx.xowa.xtns.lst.Lst_pfunc_lst_;
public class Pp_pages_nde implements Xox_xnde, Mwh_atr_itm_owner1 {
	private boolean xtn_literal = false;
	private Xop_root_tkn xtn_root;
	private byte[] index_ttl_bry, bgn_page_bry, end_page_bry, bgn_sect_bry, end_sect_bry;		
	private int step_int;
	private byte[] include, exclude, step_bry, header, onlysection;
	private byte[] toc_cur, toc_nxt, toc_prv;
	private int ns_index_id = IntUtl.MinValue, ns_page_id = IntUtl.MinValue;
	private int bgn_page_int = -1, end_page_int = -1;
	private Xow_ns ns_page;
	private Xoa_ttl index_ttl;
	private Xoae_app app; private Xowe_wiki wiki; private Xop_ctx ctx; private Gfo_usr_dlg usr_dlg;
	private byte[] src; private Xop_xnde_tkn xnde_tkn;
	private Xoa_ttl cur_page_ttl;
	private List_adp unknown_xatrs = List_adp_.New();

	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, Object xatr_id_obj) {
		// cache unknown xatrs for header usage; ISSUE#:635; DATE:2020-01-19
		if (xatr_id_obj == null) {
			unknown_xatrs.Add(new Pp_index_arg(xatr.Key_bry(), xatr.Val_as_bry()));
			return;
		}

		// skip valid xatrs with invalid values; EX: <pages index=\"A\" from=1 to 2 />; ISSUE#:656 DATE:2020-01-19
		if (xatr.Val_bgn() == -1)
			return;

		ByteVal xatr_id = (ByteVal)xatr_id_obj;
		byte[] val_bry = xatr.Val_as_bry();
		switch (xatr_id.Val()) {
			case Xatr_index_ttl:	index_ttl_bry	= val_bry; break;
			case Xatr_bgn_page:		bgn_page_bry = val_bry; break;
			case Xatr_end_page:		end_page_bry = val_bry; break;
			case Xatr_bgn_sect:		if (BryUtl.IsNotNullOrEmpty(val_bry)) bgn_sect_bry = val_bry; break; // ignore empty-String; EX:fromsection=""; ISSUE#:650 DATE:2020-01-11
			case Xatr_end_sect:		if (BryUtl.IsNotNullOrEmpty(val_bry)) end_sect_bry = val_bry; break; // ignore empty-String; EX:tosection=""; ISSUE#:650 DATE:2020-01-11
			case Xatr_include:		include			= val_bry; break;
			case Xatr_exclude:		exclude			= val_bry; break;
			case Xatr_step:			step_bry		= val_bry; break;
			case Xatr_onlysection:	onlysection		= val_bry; break;
			case Xatr_header:		header			= val_bry; break;
			case Xatr_toc_cur:		toc_cur			= val_bry; break;
			case Xatr_toc_prv:		toc_prv			= val_bry; break;
			case Xatr_toc_nxt:		toc_nxt			= val_bry; break;
		}			
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		// TOMBSTONE: do not disable Enabled check; needs smarter way of checking for xtn enable / disable
		// if (!wiki.Xtn_mgr().Xtn_proofread().Enabled()) return;
		if (!Init_vars(wiki, ctx, src, xnde)) return;

		if (wiki.Parser_mgr().Lst__recursing()) return;	// moved from Pp_index_parser; DATE:2014-05-21s

		// set recursing flag
		Xoae_page page = ctx.Page();
		BryWtr full_bfr = wiki.Utl__bfr_mkr().GetM001();
		try {
			wiki.Parser_mgr().Lst__recursing_(true);
			Hash_adp_bry lst_page_regy = ctx.Lst_page_regy(); if (lst_page_regy == null) lst_page_regy = Hash_adp_bry.cs();	// SEE:NOTE:page_regy; DATE:2014-01-01
			page.Html_data().Indicators().Enabled_(BoolUtl.N);				// disable <indicator> b/c <page> should not add to current page; PAGE:en.s:The_Parochial_System_(Wilberforce,_1838); DATE:2015-04-29
			byte[] page_bry = Bld_wikitext(full_bfr, wiki.Parser_mgr().Pp_num_parser(), lst_page_regy);
			if (page_bry != null)
				xtn_root = Bld_root_nde(full_bfr, lst_page_regy, page_bry);	// NOTE: this effectively reparses page twice; needed b/c of "if {| : ; # *, auto add new_line" which can build different tokens
		} finally {
			wiki.Parser_mgr().Lst__recursing_(false);
			full_bfr.MkrRls();
		}
		page.Html_data().Indicators().Enabled_(BoolUtl.Y);
	}
	public void Xtn_write(BryWtr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		if (xtn_literal)
			Xox_mgr_base.Xtn_write_escape(app, bfr, src, xnde);
		else {
			if (xtn_root == null) return;	// xtn_root is null when Xtn_parse exits early; occurs for recursion; DATE:2014-05-21
			html_wtr.Write_tkn_to_html(bfr, ctx, hctx, xtn_root.Root_src(), xnde, Xoh_html_wtr.Sub_idx_null, xtn_root);
		}
	}
	private boolean Init_vars(Xowe_wiki wiki, Xop_ctx ctx, byte[] src, Xop_xnde_tkn xnde) {
		this.wiki = wiki; this.ctx = ctx; app = wiki.Appe(); usr_dlg = app.Usr_dlg();
		this.src = src; this.xnde_tkn = xnde; cur_page_ttl = ctx.Page().Ttl();
		Xox_xnde_.Xatr__set(wiki, this, xatrs_hash, src, xnde);
		Xop_amp_mgr amp_mgr = wiki.Appe().Parser_amp_mgr();
		index_ttl_bry = amp_mgr.Decode_as_bry(index_ttl_bry);
		bgn_page_bry = amp_mgr.Decode_as_bry(bgn_page_bry);
		end_page_bry = amp_mgr.Decode_as_bry(end_page_bry);
		Xowc_xtn_pages cfg_pages = wiki.Cfg_parser().Xtns().Itm_pages();
		if (cfg_pages.Init_needed()) cfg_pages.Init(wiki.Ns_mgr());
		ns_index_id = cfg_pages.Ns_index_id(); if (ns_index_id == IntUtl.MinValue) return Fail_msg("wiki does not have an Index ns");
		ns_page_id  = cfg_pages.Ns_page_id();  if (ns_page_id  == IntUtl.MinValue) return Fail_msg("wiki does not have a Page ns");	// occurs when <pages> used in a wiki without a "Page:" ns; EX: de.w:Help:Buchfunktion/Feedback
		index_ttl = Xoa_ttl.Parse(wiki, ns_index_id, index_ttl_bry); if (index_ttl == null) return Fail_args("index title is not valid: index={0}", StringUtl.NewU8(index_ttl_bry));
		ns_page = wiki.Ns_mgr().Ids_get_or_null(ns_page_id);
		if (onlysection != null)
			bgn_sect_bry = end_sect_bry = null;
		return true;
	}
	private byte[] Bld_wikitext(BryWtr full_bfr, Gfo_number_parser num_parser, Hash_adp_bry lst_page_regy) {
		Pp_index_page index_page = Pp_index_parser.Parse(wiki, ctx, index_ttl, ns_page_id);
		int index_page_ttls_len = index_page.Page_ttls().Len();
		byte[] rv = BryUtl.Empty;
		if (bgn_page_bry != null || end_page_bry != null || include != null) {	// from, to, or include specified				
			Xoa_ttl[] ttls = null;
			if (	index_page.Pagelist_xndes().Len() > 0		// pagelist exists; don't get from args
				||	index_page_ttls_len == 0					// no [[Page:]] in [[Index:]]
				)												// NOTE: this simulates MW's if (empty($links)); REF.MW:ProofreadPageRenderer.php|renderPages
				ttls = Get_ttls_from_xnde_args(num_parser);
			else {
				IntRef bgn_page_ref = IntRef.NewNeg1(), end_page_ref = IntRef.NewNeg1();
				ttls = index_page.Get_ttls_rng(wiki, ns_page_id, bgn_page_bry, end_page_bry, bgn_page_ref, end_page_ref);
				bgn_page_int = bgn_page_ref.Val();
				end_page_int = end_page_ref.Val();
			}
			if (ttls == Ttls_null) {Fail_msg("no index ttls found"); return null;}
			rv = Bld_wikitext_from_ttls(full_bfr, lst_page_regy, ttls);
		}
		else {
			header = Toc_bry;
		}
		if (header != null && XophpBool_.is_true(header)) {// check if header is true; ignore values like header=0; ISSUE#:622; DATE:2019-11-28
			rv = Bld_wikitext_for_header(full_bfr, index_page, rv);
		}
		return rv;
	}	private static final byte[] Toc_bry = BryUtl.NewA7("toc");
	private byte[] Make_lnki(BryWtr full_bfr, byte[] index_page_src, Xop_lnki_tkn lnki) {
		byte[] caption = Get_caption(full_bfr, index_page_src, lnki);
		full_bfr.Add(Xop_tkn_.Lnki_bgn);
		Xoa_ttl lnki_ttl = lnki.Ttl();
		if (lnki_ttl.Wik_bgn() == -1)				// no xwiki; just add ns + page
			full_bfr.Add(lnki_ttl.Full_db());
		else										// xwiki; add entire ttl which also includes xwiki; PAGE:sv.s:Valda_dikter_(Bj�rck); EX:[[:Commons:File:Valda dikter (tredje upplagan).djvu|Commons]]; DATE:2014-07-14
			full_bfr.Add(lnki_ttl.Raw());
		full_bfr.AddBytePipe()
			.Add(caption)
			.Add(Xop_tkn_.Lnki_end);
		return full_bfr.ToBryAndClear();
	}
	private byte[] Get_caption(BryWtr full_bfr, byte[] index_page_src, Xop_lnki_tkn lnki) {
		byte[] rv = BryUtl.Empty;
		try {
			// NOTE: call "Lnki_wtr().Write_caption", not "Write_tkn_to_html" else XML in caption will be escaped; ISSUE#:624 DATE:2019-11-30
			wiki.Html_mgr().Html_wtr().Lnki_wtr().Write_caption(full_bfr, Xoh_wtr_ctx.Basic, index_page_src, lnki, lnki.Ttl());
			rv = full_bfr.ToBryAndClear();
		}
		catch (Exception e) {
			wiki.Appe().Usr_dlg().Warn_many("", "", "failed to write caption: page=~{0} lnki=~{1} err=~{2}", ctx.Page().Ttl().Full_db(), StringUtl.NewU8(index_page_src, lnki.Src_bgn(), lnki.Src_end()), ErrUtl.ToStrFull(e));
			rv = lnki.Ttl().Page_txt();
		}
		return rv;
	}
	private byte[] Bld_wikitext_for_header(BryWtr full_bfr, Pp_index_page index_page, byte[] rv) {
		List_adp main_lnkis = index_page.Main_lnkis();
		int main_lnkis_len = main_lnkis.Len();
		byte[] index_page_src = index_page.Src();
		if (main_lnkis_len > 0) {
			Xoa_ttl page_ttl = ctx.Page().Ttl();
			for (int i = 0; i < main_lnkis_len; i++) {
				Xop_lnki_tkn main_lnki = (Xop_lnki_tkn)main_lnkis.GetAt(i);
				if (page_ttl.Eq_full_db(main_lnki.Ttl())) {
					Xoae_page old_page = ctx.Page();
					wiki.Html_mgr().Html_wtr().Init_by_page(ctx, Xoh_wtr_ctx.Basic, index_page_src, ctx.Page());	// HACK: should not reuse html_wtr, but should be safe to use during parse (not html_gen)
					if (toc_cur == null)	// do not set if "current" is specified, even if "blank" specified; EX: current=''
						toc_cur = Make_lnki(full_bfr, index_page_src, main_lnki);
					if (toc_prv == null		// do not set if "prev" is specified
						&& i > 0)
						toc_prv = Make_lnki(full_bfr, index_page_src, (Xop_lnki_tkn)main_lnkis.GetAt(i - 1));
					if (toc_nxt == null		// do not set if "next" is specified
						&& i + 1 < main_lnkis_len)
						toc_nxt = Make_lnki(full_bfr, index_page_src, (Xop_lnki_tkn)main_lnkis.GetAt(i + 1));
					wiki.Html_mgr().Html_wtr().Init_by_page(ctx, Xoh_wtr_ctx.Basic, index_page_src, old_page);
					break;
				}
			}
		}
		
		full_bfr.Add(Bry_tmpl);											// {{:MediaWiki:Proofreadpage_header_template
		full_bfr.Add(Bry_value).Add(header);							// |value=toc"
		if (toc_cur != null)
			full_bfr.Add(Bry_toc_cur).Add(toc_cur);						// |current=Page/2"
		if (toc_prv != null)
			full_bfr.Add(Bry_toc_prv).Add(toc_prv);						// |prev=Page/1"
		if (toc_nxt != null)
			full_bfr.Add(Bry_toc_nxt).Add(toc_nxt);						// |next=Page/3"
		if (bgn_page_int != -1)
			full_bfr.Add(Bry_page_bgn).AddIntVariable(bgn_page_int);	// |from=1"
		if (end_page_int != -1)
			full_bfr.Add(Bry_page_end).AddIntVariable(end_page_int);	// |to=3"
		Add_args(full_bfr, index_page.Invk_args());
		Add_args(full_bfr, unknown_xatrs);
		full_bfr.Add(gplx.xowa.parsers.tmpls.Xop_curly_end_lxr.Hook);
		full_bfr.Add(rv);
		return full_bfr.ToBryAndClear();
	}
	private void Add_args(BryWtr full_bfr, List_adp invk_args) {
		int invk_args_len = invk_args.Len();
		for (int i = 0; i < invk_args_len; i++) {
			Pp_index_arg arg = (Pp_index_arg)invk_args.GetAt(i);
			full_bfr
				.AddBytePipe()		// |
				.Add(wiki.Lang().Case_mgr().Case_build_lower(arg.Key()))	// per MW, always lowercase key
				.AddByteEq()			// =
				.Add(arg.Val())
				;
		}
	}
	private Xoa_ttl[] Get_ttls_from_xnde_args(Gfo_number_parser num_parser) {
		if (!Chk_step()) return Ttls_null;
		List_adp list = List_adp_.New();
		list = Get_ttls_from_xnde_args__include(list);			if (list == null) return Ttls_null;
		list = Get_ttls_from_xnde_args__rng(num_parser, list);	if (list == null) return Ttls_null;
		list = Get_ttls_from_xnde_args__exclude(list);			if (list == null) return Ttls_null;
		if (include != null || exclude != null)	// sort if include / exclude specified; will skip sort if only range specified
			list.Sort();
		return Get_ttls_from_xnde_args__ttls(list);
	}
	private List_adp Get_ttls_from_xnde_args__include(List_adp list) {
		if (BryUtl.IsNullOrEmpty(include)) return list;	// include is blank; exit early;
		int[] include_pages = IntAryUtl.ParseOr(include, null);
		if (include_pages == null) return list;	// ignore invalid include; DATE:2014-02-22
		int include_pages_len = include_pages.length;
		for (int i = 0; i < include_pages_len; i++)
			list.Add(new IntVal(include_pages[i]));
		return list;
	}
	private List_adp Get_ttls_from_xnde_args__rng(Gfo_number_parser num_parser, List_adp list) {
		// exit if "from" and "to" are blank but include is specified; ISSUE#:657 DATE:2020-01-19
		if (   BryUtl.IsNullOrEmpty(bgn_page_bry)
			&& BryUtl.IsNullOrEmpty(end_page_bry)
			&& BryUtl.IsNotNullOrEmpty(include)
			)
			return list;

		bgn_page_int = 0;	// NOTE: default to 0 (1st page)
		if (BryUtl.IsNotNullOrEmpty(bgn_page_bry)) {
			num_parser.Parse(bgn_page_bry);
			if (num_parser.Has_err()) {
				Fail_args("pages node does not have a valid 'from': from={0}", StringUtl.NewU8(bgn_page_bry));
				return null;
			}
			else
				bgn_page_int = num_parser.Rv_as_int();
		}
		end_page_int = 0;	
		if (BryUtl.IsNullOrEmpty(end_page_bry))
			end_page_int = Get_max_page_idx(wiki, index_ttl);
		else {
			num_parser.Parse(end_page_bry);
			if (num_parser.Has_err()) {
				Fail_args("pages node does not have a valid 'to': to={0}", StringUtl.NewU8(bgn_page_bry));
				return null;
			}
			else
				end_page_int = num_parser.Rv_as_int();
		}
		if (bgn_page_int > end_page_int) {
			Fail_args("from must be less than to: from={0} to={1}", bgn_page_int, end_page_int);
			return null;
		}
		for (int i = bgn_page_int; i <= end_page_int; i++)
			list.Add(new IntVal(i));
		return list;
	}
	private int Get_max_page_idx(Xowe_wiki wiki, Xoa_ttl index_ttl) {
		List_adp rslt = List_adp_.New();
		IntRef rslt_count = IntRef.NewZero();
		wiki.Db_mgr().Load_mgr().Load_ttls_for_all_pages(Cancelable_.Never, rslt, tmp_page, tmp_page, rslt_count, ns_page, index_ttl.Page_db(), IntUtl.MaxValue, 0, IntUtl.MaxValue, false, false);
		int len = rslt_count.Val();
		int page_leaf_max = 0;
		for (int i = 0; i < len; i++) {
			Xowd_page_itm page = (Xowd_page_itm)rslt.GetAt(i);
			Xoa_ttl page_ttl = Xoa_ttl.Parse(wiki, ns_page_id, page.Ttl_page_db());	if (page_ttl == null) continue;					// page_ttl is not valid; should never happen;
			byte[] page_ttl_leaf = page_ttl.Leaf_txt();									if (page_ttl_leaf == null) continue;			// page is not leaf; should not happen
			int page_leaf_val = BryUtl.ToIntOr(page_ttl_leaf, IntUtl.MinValue);			if (page_leaf_val == IntUtl.MinValue) continue;	// leaf is not int; ignore
			if (page_leaf_val > page_leaf_max) page_leaf_max = page_leaf_val;
		}
		return page_leaf_max;
	}	private static Xowd_page_itm tmp_page = new Xowd_page_itm();	// tmp_page passed to Load_ttls_for_all_pages; values are never looked at, so use static instance
	private List_adp Get_ttls_from_xnde_args__exclude(List_adp list) {
		if (BryUtl.IsNullOrEmpty(exclude)) return list;	// exclude is blank; exit early;
		int[] exclude_pages = IntAryUtl.ParseOr(exclude, null);
		if (exclude_pages == null) return list;	// ignore invalid exclude; DATE:2014-02-22
		Hash_adp exclude_pages_hash = Hash_adp_.New();
		int exclude_pages_len = exclude_pages.length;
		for (int i = 0; i < exclude_pages_len; i++) {
			IntVal exclude_page = new IntVal(exclude_pages[i]);
			if (!exclude_pages_hash.Has(exclude_page))
				exclude_pages_hash.Add(exclude_page, exclude_page);
		}
		List_adp new_list = List_adp_.New();
		int list_len = list.Len();
		for (int i = 0; i < list_len; i++) {
			IntVal page = (IntVal)list.GetAt(i);
			if (exclude_pages_hash.Has(page)) continue;
			new_list.Add(page);
		}
		return new_list;
	}
	private Xoa_ttl[] Get_ttls_from_xnde_args__ttls(List_adp list) {
		int list_len = list.Len(); if (list_len == 0) return Ttls_null;
		Xoa_ttl[] rv = new Xoa_ttl[(list_len / step_int) + ((list_len % step_int == 0) ? 0 : 1)];
		int rv_idx = 0;
		BryWtr ttl_bfr = wiki.Utl__bfr_mkr().GetB512();
		for (int i = 0; i < list_len; i += step_int) {
			IntVal page = (IntVal)list.GetAt(i);
			ttl_bfr.Add(ns_page.Name_db_w_colon())		// EX: 'Page:'
				.Add(index_ttl_bry)						// EX: 'File.djvu'
				.AddByte(AsciiByte.Slash)				// EX: '/'
				.AddIntVariable(page.Val());			// EX: '123'
			rv[rv_idx++] = Xoa_ttl.Parse(wiki, ttl_bfr.ToBryAndClear());
		}
		ttl_bfr.MkrRls();
		return rv;
	}
	private boolean Chk_step() {
		if (step_bry == null) {
			step_int = 1;
			return true;
		}
		step_int = BryUtl.ToIntOr(step_bry, IntUtl.MinValue);
		if (step_int < 1 || step_int > 1000) {
			Fail_args("pages node does not have a valid 'step': step={0}", StringUtl.NewU8(step_bry));
			return false;
		}
		return true;
	}
	private byte[] Bld_wikitext_from_ttls(BryWtr full_bfr, Hash_adp_bry lst_page_regy, Xoa_ttl[] ary) {
		int ary_len = ary.length;
		Xoa_ttl bgn_page_ttl = bgn_page_bry == null ? null : ary[0];
		Xoa_ttl end_page_ttl = end_page_bry == null ? null : ary[ary_len - 1];
		
		BryWtr page_bfr = wiki.Utl__bfr_mkr().GetM001();
		try {
		for (int i = 0; i < ary_len; i++) {
			Xoa_ttl ttl = ary[i];
			byte[] ttl_page_db = ttl.Page_db();
			if (lst_page_regy.Get_by_bry(ttl_page_db) == null)	// check if page was already added; avoids recursive <page> calls which will overflow stack; DATE:2014-01-01
				lst_page_regy.Add(ttl_page_db, ttl_page_db);
			else
				continue;
			byte[] cur_sect_bgn = Lst_pfunc_itm.Null_arg, cur_sect_end = Lst_pfunc_itm.Null_arg;
			if		(ttl.Eq_page_db(bgn_page_ttl)) {
				if		(bgn_sect_bry != null)
					cur_sect_bgn = bgn_sect_bry;
				else if (onlysection != null) {
					cur_sect_bgn = onlysection;
					cur_sect_end = onlysection;
				}
			}
			else if	(ttl.Eq_page_db(end_page_ttl)) {
				if	(end_sect_bry != null)
					cur_sect_end = end_sect_bry;
			}
			Xopg_tmpl_prepend_mgr prepend_mgr = ctx.Page().Tmpl_prepend_mgr().Bgn(full_bfr);
			Lst_pfunc_itm lst_itm = Lst_pfunc_itm.New_sect_or_null(ctx, ttl.Full_db());
			if (lst_itm != null) Lst_pfunc_lst_.Sect_include(page_bfr, lst_itm.Sec_mgr(), lst_itm.Itm_src(), cur_sect_bgn, cur_sect_end);
			prepend_mgr.End(ctx, full_bfr, page_bfr.Bry(), page_bfr.Len(), BoolUtl.Y);
			full_bfr.AddBfrAndClear(page_bfr);
			full_bfr.Add(HtmlEntityCodes.SpaceBry);	// $out.= "&#32;"; REF.MW:ProofreadPageRenderer.pn
		}			
		}
		finally {
			page_bfr.MkrRls();
		}
		return full_bfr.ToBryAndClear();
	}
	private Xop_root_tkn Bld_root_nde(BryWtr page_bfr, Hash_adp_bry lst_page_regy, byte[] wikitext) {
		Xop_ctx tmp_ctx = Xop_ctx.New__sub__reuse_lst(wiki, ctx, lst_page_regy);
		tmp_ctx.Page().Ttl_(ctx.Page().Ttl());					// NOTE: must set tmp_ctx.Ttl to ctx.Ttl; EX: Flatland and First World; DATE:2013-04-29
		tmp_ctx.Lnki().File_logger_(Xop_file_logger_.Noop);	// NOTE: set file_wkr to null, else items will be double-counted
		tmp_ctx.Parse_tid_(Xop_parser_tid_.Tid__defn);
		Xop_parser tmp_parser = Xop_parser.new_(wiki, wiki.Parser_mgr().Main().Tmpl_lxr_mgr(), wiki.Parser_mgr().Main().Wtxt_lxr_mgr());
		Xop_root_tkn rv = tmp_ctx.Tkn_mkr().Root(wikitext);
		tmp_parser.Parse_text_to_wdom(rv, tmp_ctx, tmp_ctx.Tkn_mkr(), wikitext, Xop_parser_.Doc_bgn_bos);
		return rv;
	}
	private String Fail_msg_suffix() {
		String excerpt = BryFmtr.EscapeTilde(StringUtl.NewU8(BryUtl.MidByLenSafe(src, xnde_tkn.Src_bgn(), 32)));
		return StringUtl.Format(" ttl={0} src={1}", StringUtl.NewU8(cur_page_ttl.Full_db()), excerpt);
	}
	private String Fail_msg_basic(String msg) {return msg + ";" + Fail_msg_suffix();}
	private String Fail_msg_custom(String fmt, Object... args) {return StringUtl.Format(fmt, args) + Fail_msg_suffix();}
	private boolean Fail_msg(String msg) {
		xtn_literal = true;
		usr_dlg.Warn_many("", "", StringUtl.Replace(Fail_msg_basic(msg), "\n", ""));
		return false;
	}
	private boolean Fail_args(String fmt, Object... args) {
		xtn_literal = true;
		usr_dlg.Warn_many("", "", StringUtl.Replace(Fail_msg_custom(fmt, args), "\n", ""));
		return false;
	}

	private static Hash_adp_bry xatrs_hash = Hash_adp_bry.ci_a7()	// NOTE: these do not seem to be i18n'd; no ProofreadPage.magic.php; ProofreadPage.i18n.php only has messages; ProofreadPage.body.php refers to names literally
	.Add_str_obj("index"		, ByteVal.New(Pp_pages_nde.Xatr_index_ttl))
	.Add_str_obj("from"			, ByteVal.New(Pp_pages_nde.Xatr_bgn_page))
	.Add_str_obj("to"			, ByteVal.New(Pp_pages_nde.Xatr_end_page))
	.Add_str_obj("fromsection"	, ByteVal.New(Pp_pages_nde.Xatr_bgn_sect))
	.Add_str_obj("tosection"	, ByteVal.New(Pp_pages_nde.Xatr_end_sect))
	.Add_str_obj("include"		, ByteVal.New(Pp_pages_nde.Xatr_include))
	.Add_str_obj("exclude"		, ByteVal.New(Pp_pages_nde.Xatr_exclude))
	.Add_str_obj("onlysection"	, ByteVal.New(Pp_pages_nde.Xatr_onlysection))
	.Add_str_obj("step"			, ByteVal.New(Pp_pages_nde.Xatr_step))
	.Add_str_obj("header"		, ByteVal.New(Pp_pages_nde.Xatr_header))
	.Add_str_obj("current"		, ByteVal.New(Pp_pages_nde.Xatr_toc_cur))
	.Add_str_obj("prev"			, ByteVal.New(Pp_pages_nde.Xatr_toc_prv))
	.Add_str_obj("next"			, ByteVal.New(Pp_pages_nde.Xatr_toc_nxt))
	;
	public static final byte
	  Xatr_index_ttl	=  0
	, Xatr_bgn_page		=  1
	, Xatr_end_page		=  2
	, Xatr_bgn_sect		=  3
	, Xatr_end_sect		=  4
	, Xatr_include		=  5
	, Xatr_exclude		=  6
	, Xatr_onlysection	=  7
	, Xatr_step			=  8
	, Xatr_header		=  9
	, Xatr_toc_cur		= 10
	, Xatr_toc_prv		= 11
	, Xatr_toc_nxt		= 12
	;
	private static final byte[]
	  Bry_tmpl			= BryUtl.NewA7("{{:MediaWiki:Proofreadpage_header_template")
	, Bry_value			= BryUtl.NewA7("|value=")
	, Bry_toc_cur		= BryUtl.NewA7("|current=")
	, Bry_toc_prv		= BryUtl.NewA7("|prev=")
	, Bry_toc_nxt		= BryUtl.NewA7("|next=")
	, Bry_page_bgn		= BryUtl.NewA7("|from=")
	, Bry_page_end		= BryUtl.NewA7("|to=")
	;
	public static final Xoa_ttl[] Ttls_null = null;
}	
/*
NOTE:page_regy
. original implmentation was following
in Xop_ctx
	public Hash_adp_bry			Lst_page_regy()		{if (lst_page_regy == null) lst_page_regy = Hash_adp_bry.cs(); return lst_page_regy;} 
in Pp_pages_nde
	Hash_adp_bry lst_page_regy = ctx.Lst_page_regy();
. current implementation is following
in Xop_ctx
	public Hash_adp_bry			Lst_page_regy()		{return lst_page_regy;} 
in Pp_pages_nde
	Hash_adp_bry lst_page_regy = ctx.Lst_page_regy();
	if (lst_page_regy == null) lst_page_regy = Hash_adp_bry.cs();
. note that this only skips transcluded <pages> within a given <pages> call, not across the entire page
EX: Page:A/1 has the following text
<pages index="A" from=1 to=3 />
<pages index="B" from=1 to=1 />
text
<pages index="B" from=1 to=1 />
. original implementation would correctly include <pages index="A" from=1 to=3 /> only once, but would also include <pages index="B" from=1 to=1 /> once
. current implmentation would include <pages index="B" from=1 to=1 /> twice
. also, side-effect of only having Lst_page_regy only be non-null on sub_ctx, which means nothing needs to be cleared on main_ctx
*/
