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
package gplx.xowa.html; import gplx.*; import gplx.xowa.*;
import gplx.html.*; import gplx.xowa.files.*; import gplx.xowa.parsers.lnkis.redlinks.*; import gplx.xowa.users.history.*; import gplx.xowa.xtns.pfuncs.ttls.*;
public class Xoh_lnki_wtr {
	private Xoa_app app; private Xow_wiki wiki; private Xoa_page page; private Xop_ctx ctx;
	private Xoh_html_wtr_cfg cfg;
	private Xou_history_mgr history_mgr;		
	private Xop_lnki_caption_wtr_tkn caption_tkn_wtr;
	private Xop_lnki_caption_wtr_bry caption_bry_wtr;
	private Xop_lnki_logger_redlinks_mgr redlinks_mgr;
	public Xoh_lnki_wtr(Xoh_html_wtr html_wtr, Xow_wiki wiki, Xow_html_mgr html_mgr, Xoh_html_wtr_cfg cfg) {
		caption_tkn_wtr = new Xop_lnki_caption_wtr_tkn(html_wtr);
		caption_bry_wtr = new Xop_lnki_caption_wtr_bry();
		this.wiki = wiki; this.app = wiki.App(); this.cfg = cfg;
		file_wtr = new Xoh_lnki_file_wtr(wiki, html_mgr, html_wtr);
	}
	public Xoh_lnki_file_wtr File_wtr() {return file_wtr;} private Xoh_lnki_file_wtr file_wtr;
	public void Init_by_page(Xop_ctx ctx, Xoa_page page) {
		this.ctx = ctx; this.page = page;			// NOTE: must set ctx for file.v2; DATE:2014-06-22
		this.wiki = ctx.Wiki();
		redlinks_mgr = page.Lnki_redlinks_mgr();	// NOTE: need to set redlinks_mgr, else toc parse may fail; EX:pl.d:head_sth_off;DATE:2014-05-07
		file_wtr.Page_bgn(page);
		this.history_mgr = app.User().History_mgr();
	}
	public void Write(Bry_bfr bfr, Xoh_html_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki) {
		Xoa_ttl lnki_ttl = lnki.Ttl();
		Xow_xwiki_itm lang = lnki_ttl == null ? null : lnki_ttl.Wik_itm();
		if (lang != null && lang.Type_is_lang(wiki.Lang().Lang_id()) && !lnki_ttl.ForceLiteralLink()) {
			page.Xwiki_langs().Add(lnki_ttl);
			return;
		}
		if (lnki_ttl == null) {// NOTE: parser failed to properly invalidate lnki; escape tkn now and warn; DATE:2014-06-06
			app.Usr_dlg().Warn_many("", "", "invalid lnki evaded parser; page=~{0} ex=~{1}", ctx.Cur_page().Url().Xto_full_str(), String_.new_utf8_(src, lnki.Src_bgn(), lnki.Src_end()));
			Xoh_html_wtr_escaper.Escape(app, bfr, src, lnki.Src_bgn(), lnki.Src_end(), true, false);
			return;
		}
		boolean literal_link = lnki_ttl.ForceLiteralLink();	// NOTE: if literal link, then override ns behavior; for File, do not show image; for Ctg, do not display at bottom of page
		redlinks_mgr.Lnki_add(lnki);
		boolean stage_is_alt = hctx.Mode_is_alt();
		switch (lnki.Ns_id()) {
			case Xow_ns_.Id_media:		if (!stage_is_alt) file_wtr.Write_or_queue(bfr, page, ctx, hctx, src, lnki); return; // NOTE: literal ":" has no effect; PAGE:en.w:Beethoven and [[:Media:De-Ludwig_van_Beethoven.ogg|listen]]
			case Xow_ns_.Id_file:		if (!literal_link && !stage_is_alt) {file_wtr.Write_or_queue(bfr, page, ctx, hctx, src, lnki); return;} break;
			case Xow_ns_.Id_category:	if (!literal_link) {page.Html_data().Ctgs_add(lnki.Ttl()); return;} break;
		}
		Write_plain_by_tkn(bfr, hctx, src, lnki, lnki_ttl);
	}
	public void Write_file(Bry_bfr bfr, Xoa_page page, Xop_ctx ctx, Xoh_html_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, byte[] alt) {
		file_wtr.Write_or_queue(bfr, page, ctx, hctx, src, lnki, alt);
	}
	public void Write_file(Bry_bfr bfr, Xop_ctx ctx, Xoh_html_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, Xof_xfer_itm xfer, byte[] alt) {
		file_wtr.Write_media(bfr, hctx, src, lnki, xfer, alt);
	}
	public void Write_plain_by_bry(Bry_bfr bfr, byte[] src, Xop_lnki_tkn lnki, byte[] caption) {
		Write_plain(bfr, Xoh_html_wtr_ctx.Basic, src, lnki, lnki.Ttl(), caption_bry_wtr.Caption_bry_(caption));
	}
	public void Write_plain_by_tkn(Bry_bfr bfr, Xoh_html_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, Xoa_ttl lnki_ttl) {
		Write_plain(bfr, hctx, src, lnki, lnki_ttl, caption_tkn_wtr);
	}
	public void Write_caption(Bry_bfr bfr, Xoh_html_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, Xoa_ttl lnki_ttl) {
		Write_caption(bfr, ctx, hctx, src, lnki, lnki.Ttl_ary(), true, caption_tkn_wtr);
	}
	private void Write_plain(Bry_bfr bfr, Xoh_html_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, Xoa_ttl lnki_ttl, Xop_lnki_caption_wtr caption_wkr) {
		byte[] ttl_bry = lnki.Ttl_ary();
		if (Bry_.Len_eq_0(ttl_bry)) ttl_bry = lnki_ttl.Full_txt_raw();		// NOTE: handles ttls like [[fr:]] and [[:fr;]] which have an empty Page_txt, but a valued Full_txt_raw
		if (Bry_.Eq(lnki_ttl.Full_txt(), page.Ttl().Full_txt())) {			// lnki is same as pagename; bold; SEE: Month widget on day pages will bold current day; PAGE:en.w:January 1
			if (lnki_ttl.Anch_bgn() == -1 && Bry_.Eq(lnki_ttl.Wik_txt(), page.Ttl().Wik_txt())) {		// only bold if lnki is not pointing to anchor on same page; PAGE:en.w:Comet; [[Comet#Physical characteristics|ion tail]]
				bfr.Add(Html_tag_.B_lhs);
				Write_caption(bfr, ctx, hctx, src, lnki, ttl_bry, true, caption_wkr);
				bfr.Add(Html_tag_.B_rhs);
				return;
			}
		}
		if (hctx.Mode_is_alt())
			Write_caption(bfr, ctx, hctx, src, lnki, ttl_bry, true, caption_wkr);
		else {
			bfr.Add(Xoh_consts.A_bgn);								// '<a href="'
			app.Href_parser().Build_to_bfr(bfr, wiki, lnki_ttl, hctx.Mode_is_popup());	// '/wiki/A'
			if (cfg.Lnki_id()) {
				int lnki_html_id = lnki.Html_id();
				if (lnki_html_id > 0)								// html_id=0 for skipped lnkis; EX:anchors and interwiki
					bfr	.Add(Xoh_consts.A_mid_id)					// '" id=\"xowa_lnki_'
						.Add_int_variable(lnki_html_id);			// '1234'
			}
			if (cfg.Lnki_title())
				bfr	.Add(Xoh_consts.A_bgn_lnki_0)					// '" title=\"'
					.Add(lnki_ttl.Page_txt());						// 'Abcd'		NOTE: use Page_txt to (a) replace underscores with spaces; (b) get title casing; EX:[[roman_empire]] -> Roman empire
			if (cfg.Lnki_visited()
				&& history_mgr.Has(wiki.Domain_bry(), ttl_bry))
				bfr.Add(Bry_xowa_visited);							// '" class="xowa-visited'
			bfr.Add(Xoh_consts.__end_quote);						// '">'
			if (lnki_ttl.Anch_bgn() != -1 && !lnki_ttl.Ns().Id_main()) {	// anchor exists and not main_ns; anchor must be manually added b/c Xoa_ttl does not handle # for non main-ns
				byte[] anch_txt = lnki_ttl.Anch_txt();
				byte anch_spr 
				= (anch_txt.length > 0 && anch_txt[0] == Byte_ascii.Hash)	// 1st char is #; occurs when page_txt has trailing space; causes 1st letter of anch_txt to start at # instead of 1st letter
				? Byte_ascii.Space	// ASSUME: 1 space ("Help:A #b"); does not handle multiple spaces like ("Help:A   #b"); needs change to Xoa_ttl 
				: Byte_ascii.Hash;	// Anch_txt bgns at 1st letter, so add # for caption; 
				ttl_bry = Bry_.Add_w_dlm(anch_spr, ttl_bry, anch_txt);	// manually add anchor; else "Help:A#b" becomes "Help:A". note that lnki.Ttl_ary() uses .Full_txt (wiki + page but no anchor) to captialize 1st letter of page otherwise "Help:A#b" shows as "Help:A" (so Help:a -> Help:A); DATE:2013-06-21
			}
			Write_caption(bfr, ctx, hctx, src, lnki, ttl_bry, true, caption_wkr);
			bfr.Add(Xoh_consts.A_end);								// </a>
		}
	}
	private void Write_caption(Bry_bfr bfr, Xop_ctx ctx, Xoh_html_wtr_ctx hctx, byte[] src, Xop_lnki_tkn lnki, byte[] ttl_bry, boolean tail_enabled, Xop_lnki_caption_wtr caption_wkr) {
		if	(lnki.Caption_exists()) {					// lnki has a caption seg; EX: [[A|caption]]
			if (lnki.Caption_tkn_pipe_trick())			// "pipe trick"; [[A|]] is same as [[A|A]]; also, [[Help:A|]] -> [[Help:A|A]]
				bfr.Add(lnki.Ttl().Page_txt());
			else
				caption_wkr.Write_tkn(ctx, hctx, bfr, src, lnki, Xoh_html_wtr.Sub_idx_null, lnki.Caption_val_tkn());
		}
		else {											// lnki only has ttl
			if (!Write_caption_for_rel2abs(bfr, lnki))	// write caption if rel2abs ttls
				bfr.Add(ttl_bry);						// write ttl_bry as caption;
		}
		if (tail_enabled) {								// write tail if enabled; EX: [[A]]b -> Ab
			int tail_bgn = lnki.Tail_bgn();
			if (tail_bgn != -1) bfr.Add_mid(src, tail_bgn, lnki.Tail_end());
		}
	}
	private static boolean Write_caption_for_rel2abs(Bry_bfr bfr, Xop_lnki_tkn lnki) {
		int subpage_tid = lnki.Subpage_tid(); if (subpage_tid == Pfunc_rel2abs.Id_null) return false;	// not a subpage
		boolean subpage_slash_at_end = lnki.Subpage_slash_at_end();
		byte[] leaf_txt = lnki.Ttl().Leaf_txt_wo_qarg();
		switch (subpage_tid) {
			case Pfunc_rel2abs.Id_slash:
				if (subpage_slash_at_end)		// "/" at end; only add text;		EX: [[/A/]] -> A
					bfr.Add(leaf_txt);
				else							// "/" absent; add slash to bgn;	EX: [[/A]]  -> /A
					bfr.Add_byte(Byte_ascii.Slash).Add(leaf_txt);
				return true;
			case Pfunc_rel2abs.Id_dot_dot_slash:
				if (subpage_slash_at_end)		// "/" at end; only add text;		EX: [[../A/]] -> A
					bfr.Add(leaf_txt);
				else							// "/" absent; add page;			EX: [[../A]]  -> Page/A
					bfr.Add(lnki.Ttl().Page_txt());
				return true;
		}
		return false;
	}
	private static final byte[] Bry_xowa_visited = Bry_.new_ascii_("\" class=\"xowa-visited"); 
}
interface Xop_lnki_caption_wtr {
	void Write_tkn(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_tkn_grp grp, int sub_idx, Xop_tkn_itm tkn);
}
class Xop_lnki_caption_wtr_bry implements Xop_lnki_caption_wtr {
	private byte[] caption_bry;
	public Xop_lnki_caption_wtr_bry Caption_bry_(byte[] caption_bry) {
		this.caption_bry = caption_bry;
		return this;
	}
	public void Write_tkn(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_tkn_grp grp, int sub_idx, Xop_tkn_itm tkn) {
		bfr.Add(caption_bry);
	}
}
class Xop_lnki_caption_wtr_tkn implements Xop_lnki_caption_wtr {
	private Xoh_html_wtr html_wtr;
	public Xop_lnki_caption_wtr_tkn(Xoh_html_wtr html_wtr) {
		this.html_wtr = html_wtr;
	}
	public void Write_tkn(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_tkn_grp grp, int sub_idx, Xop_tkn_itm tkn) {
		html_wtr.Write_tkn(bfr, ctx, hctx, src, grp, sub_idx, tkn);
	}
}
