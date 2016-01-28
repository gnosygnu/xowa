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
package gplx.xowa.htmls.core.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.primitives.*; import gplx.core.net.*; import gplx.core.btries.*;
import gplx.langs.htmls.*; import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.apos.*; import gplx.xowa.parsers.amps.*; import gplx.xowa.parsers.lnkes.*; import gplx.xowa.parsers.lists.*; import gplx.xowa.htmls.core.wkrs.lnkis.htmls.*; import gplx.xowa.parsers.tblws.*; import gplx.xowa.parsers.paras.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.miscs.*; import gplx.xowa.parsers.vnts.*; import gplx.xowa.parsers.htmls.*;
import gplx.xowa.xtns.*; import gplx.xowa.xtns.dynamicPageList.*; import gplx.xowa.xtns.math.*; import gplx.xowa.xtns.cites.*; import gplx.xowa.htmls.core.hzips.*;	import gplx.xowa.parsers.hdrs.*;
import gplx.xowa.htmls.core.*;
import gplx.xowa.htmls.core.wkrs.hdrs.*; import gplx.xowa.htmls.core.wkrs.lnkes.*;
public class Xoh_html_wtr {
	private Xowe_wiki wiki; private Xoae_app app; private Xoae_page page; private Xop_xatr_whitelist_mgr whitelist_mgr;
	public Xoh_html_wtr(Xowe_wiki wiki, Xow_html_mgr html_mgr) {
		this.wiki = wiki; this.app = wiki.Appe(); this.whitelist_mgr = app.Html_mgr().Whitelist_mgr();
		this.html_mgr = html_mgr;
		lnki_wtr = new Xoh_lnki_wtr(this, wiki, html_mgr, cfg);
		ref_wtr = new Ref_html_wtr(wiki);
	}
	public void Init_by_wiki(Xowe_wiki wiki) {
		cfg.Toc__show_(Bool_.Y).Lnki__title_(true).Lnki_visited_y_().Lnki__id_(Bool_.Y);	// NOTE: set during Init_by_wiki, b/c all tests assume these are false
		ref_wtr.Init_by_wiki(wiki);
	}
	public Xow_html_mgr Html_mgr() {return html_mgr;} private Xow_html_mgr html_mgr;
	public Xoh_html_wtr_cfg Cfg() {return cfg;} private Xoh_html_wtr_cfg cfg = new Xoh_html_wtr_cfg();
	public Xoh_lnke_html		Wkr__lnke() {return wkr__lnke;} private Xoh_lnke_html wkr__lnke = new Xoh_lnke_html();
	public Xoh_hdr_html			Wkr__hdr()	{return wkr__hdr;}	private final Xoh_hdr_html wkr__hdr = new Xoh_hdr_html();
	public Xoh_lnki_wtr Lnki_wtr() {return lnki_wtr;} private Xoh_lnki_wtr lnki_wtr;
	public Ref_html_wtr Ref_wtr() {return ref_wtr;} private Ref_html_wtr ref_wtr; 
	public void Init_by_page(Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xoae_page page) {this.page = page; lnki_wtr.Init_by_page(ctx, hctx, src, page);}
	public void Write_all(Bry_bfr bfr, Xop_ctx ctx, byte[] src, Xop_root_tkn root) {Write_all(bfr, ctx, Xoh_wtr_ctx.Basic, src, root);}
	public void Write_all(Bry_bfr bfr, Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_root_tkn root) {			
		try {
			indent_level = 0; this.page = ctx.Page();
			page.Slink_list().Clear();	// HACK: always clear langs; necessary for reload
			lnki_wtr.Init_by_page(ctx, hctx, src, ctx.Page());				
			Write_tkn(bfr, ctx, hctx, src, null, -1, root);
		}
		finally {
			page.Category_list_(page.Html_data().Ctgs_to_ary());
		}
	}
	public void Write_tkn_ary(Bry_bfr bfr, Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_tkn_itm[] ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			Xop_tkn_itm itm = ary[i];
			Write_tkn(bfr, ctx, hctx, src, itm, i, itm);
		}
	}
	public void Write_tkn(Bry_bfr bfr, Xop_ctx ctx, Xoh_wtr_ctx hctx, byte[] src, Xop_tkn_grp grp, int sub_idx, Xop_tkn_itm tkn) {
		if (tkn.Ignore()) return;
		switch (tkn.Tkn_tid()) {
			case Xop_tkn_itm_.Tid_arg_itm:
			case Xop_tkn_itm_.Tid_root:
				int subs_len = tkn.Subs_len();
				for (int i = 0; i < subs_len; i++)
					Write_tkn(bfr, ctx, hctx, src, tkn, i, tkn.Subs_get(i));
				break;
			case Xop_tkn_itm_.Tid_ignore:			break;
			case Xop_tkn_itm_.Tid_html_ncr:			Html_ncr(ctx, hctx, bfr, src, (Xop_amp_tkn_num)tkn); break;
			case Xop_tkn_itm_.Tid_html_ref:			Html_ref(ctx, hctx, bfr, src, (Xop_amp_tkn_txt)tkn); break;
			case Xop_tkn_itm_.Tid_hr:				Hr(ctx, hctx, bfr, src, (Xop_hr_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_apos:				Apos(ctx, hctx, bfr, src, (Xop_apos_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_lnki:				lnki_wtr.Write(bfr, hctx, src, (Xop_lnki_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_list:				List(ctx, hctx, bfr, src, (Xop_list_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_xnde:				Xnde(ctx, hctx, bfr, src, (Xop_xnde_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_under:			Under(ctx, hctx, bfr, src, (Xop_under_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_tblw_tb:			Tblw(ctx, hctx, bfr, src, (Xop_tblw_tkn)tkn, Tag_tblw_tb_bgn_atr, Tag_tblw_tb_end, true); break;
			case Xop_tkn_itm_.Tid_tblw_tr:			Tblw(ctx, hctx, bfr, src, (Xop_tblw_tkn)tkn, Tag_tblw_tr_bgn_atr, Tag_tblw_tr_end, false); break;
			case Xop_tkn_itm_.Tid_tblw_td:			Tblw(ctx, hctx, bfr, src, (Xop_tblw_tkn)tkn, Tag_tblw_td_bgn_atr, Tag_tblw_td_end, false); break;
			case Xop_tkn_itm_.Tid_tblw_th:			Tblw(ctx, hctx, bfr, src, (Xop_tblw_tkn)tkn, Tag_tblw_th_bgn_atr, Tag_tblw_th_end, false); break;
			case Xop_tkn_itm_.Tid_tblw_tc:			Tblw(ctx, hctx, bfr, src, (Xop_tblw_tkn)tkn, Tag_tblw_tc_bgn_atr, Tag_tblw_tc_end, false); break;
			case Xop_tkn_itm_.Tid_newLine:			NewLine(ctx, hctx, bfr, src, (Xop_nl_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_bry:				Bry(ctx, hctx, bfr, src, (Xop_bry_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_lnke:				wkr__lnke.Write_html(bfr, this, hctx, ctx, src, (Xop_lnke_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_hdr:				wkr__hdr.Write_html(bfr, this, wiki, page, ctx, hctx, cfg, grp, sub_idx, src, (Xop_hdr_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_para:
			case Xop_tkn_itm_.Tid_pre:
			case Xop_tkn_itm_.Tid_space:
			case Xop_tkn_itm_.Tid_escape:
				tkn.Html__write(bfr, this, wiki, page, ctx, hctx, cfg, grp, sub_idx, src); break;
			default:
				Xoh_html_wtr_escaper.Escape(app.Parser_amp_mgr(), bfr, src, tkn.Src_bgn(), tkn.Src_end(), true, false);	// NOTE: always escape text including (a) lnki_alt text; and (b) any other text, especially failed xndes; DATE:2013-06-18
				break;
		}
	}
	public void Html_ncr(Xop_ctx ctx, Xoh_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_amp_tkn_num tkn)	{
		bfr.Add_byte(Byte_ascii.Amp).Add_byte(Byte_ascii.Hash).Add_int_variable(tkn.Val()).Add_byte(Byte_ascii.Semic);	// NOTE: do not literalize, else browser may not display multi-char bytes properly; EX: &#160; gets added as &#160; not as {192,160}; DATE:2013-12-09
	}
	public void Html_ref(Xop_ctx ctx, Xoh_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_amp_tkn_txt tkn) {
		if (tkn.Itm_is_custom())	// used by <nowiki>; EX:<nowiki>&#60;</nowiki> -> &xowa_lt; DATE:2014-11-07
			tkn.Print_literal(bfr);
		else
			tkn.Print_ncr(bfr);
	}
	public void Hr(Xop_ctx ctx, Xoh_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_hr_tkn tkn)				{bfr.Add(Tag_hr);}
	public void Apos(Xop_ctx ctx, Xoh_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_apos_tkn apos) {
		if (hctx.Mode_is_alt()) return;	// ignore apos if alt; EX: [[File:A.png|''A'']] should have alt of A; DATE:2013-10-25
		int literal_apos = apos.Apos_lit();
		if (literal_apos > 0)
			bfr.Add_byte_repeat(Byte_ascii.Apos, literal_apos);
		switch (apos.Apos_cmd()) {
			case Xop_apos_tkn_.Cmd_b_bgn:			bfr.Add(Gfh_tag_.B_lhs); break;
			case Xop_apos_tkn_.Cmd_b_end:			bfr.Add(Gfh_tag_.B_rhs); break;
			case Xop_apos_tkn_.Cmd_i_bgn:			bfr.Add(Gfh_tag_.I_lhs); break;		
			case Xop_apos_tkn_.Cmd_i_end:			bfr.Add(Gfh_tag_.I_rhs); break;
			case Xop_apos_tkn_.Cmd_bi_bgn:			bfr.Add(Gfh_tag_.B_lhs).Add(Gfh_tag_.I_lhs); break;		
			case Xop_apos_tkn_.Cmd_ib_end:			bfr.Add(Gfh_tag_.I_rhs).Add(Gfh_tag_.B_rhs); break;
			case Xop_apos_tkn_.Cmd_ib_bgn:			bfr.Add(Gfh_tag_.I_lhs).Add(Gfh_tag_.B_lhs); break;		
			case Xop_apos_tkn_.Cmd_bi_end:			bfr.Add(Gfh_tag_.B_rhs).Add(Gfh_tag_.I_rhs);; break;
			case Xop_apos_tkn_.Cmd_bi_end__b_bgn:	bfr.Add(Gfh_tag_.B_rhs).Add(Gfh_tag_.I_rhs).Add(Gfh_tag_.B_lhs); break;
			case Xop_apos_tkn_.Cmd_ib_end__i_bgn:	bfr.Add(Gfh_tag_.I_rhs).Add(Gfh_tag_.B_rhs).Add(Gfh_tag_.I_lhs); break;
			case Xop_apos_tkn_.Cmd_b_end__i_bgn:	bfr.Add(Gfh_tag_.B_rhs).Add(Gfh_tag_.I_lhs); break;		
			case Xop_apos_tkn_.Cmd_i_end__b_bgn:	bfr.Add(Gfh_tag_.I_rhs).Add(Gfh_tag_.B_lhs); break;
			case Xop_apos_tkn_.Cmd_nil:				break;
			default: throw Err_.new_unhandled(apos.Apos_cmd());
		}
	}
	public static byte[] Ttl_to_title(byte[] ttl) {return ttl;}	// FUTURE: swap html chars?
	public void List(Xop_ctx ctx, Xoh_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_list_tkn list) {
		if (hctx.Mode_is_alt()) {						// alt; add literally; EX: "*" for "\n*"; note that \n is added in NewLine()
			if (list.List_bgn() == Bool_.Y_byte) {	// bgn tag
				bfr.Add_byte(list.List_itmTyp());	// add literal byte
			}
			else {}									// end tag; ignore
		}
		else {
			byte list_itm_type = list.List_itmTyp();
			if (list.List_bgn() == Bool_.Y_byte) {
				if (list.List_sub_first()) List_grp_bgn(ctx, hctx, bfr, src, list_itm_type);
				List_itm_bgn(ctx, hctx, bfr, src, list_itm_type);
			}
			else {
				List_itm_end(ctx, hctx, bfr, src, list_itm_type);
				if (list.List_sub_last() == Bool_.Y_byte) List_grp_end(ctx, hctx, bfr, src, list_itm_type);
			}
		}
	}
	public void List_grp_bgn(Xop_ctx ctx, Xoh_wtr_ctx hctx, Bry_bfr bfr, byte[] src, byte type) {
		byte[] tag = null;
		switch (type) {
			case Xop_list_tkn_.List_itmTyp_ol: tag = Tag_list_grp_ol_bgn; break;
			case Xop_list_tkn_.List_itmTyp_ul: tag = Tag_list_grp_ul_bgn; break;
			case Xop_list_tkn_.List_itmTyp_dd:
			case Xop_list_tkn_.List_itmTyp_dt: tag = Tag_list_grp_dl_bgn; break;
			default: throw Err_.new_unhandled(type);
		}
		if (bfr.Len() > 0) bfr.Add_byte_nl();	// NOTE: do not add newLine if start 
		if (indent_level > 0) bfr.Add_byte_repeat(Byte_ascii.Space, indent_level * 2);
		bfr.Add(tag);
		++indent_level;
	}
	public void List_itm_bgn(Xop_ctx ctx, Xoh_wtr_ctx hctx, Bry_bfr bfr, byte[] src, byte type) {
		byte[] tag = null;
		switch (type) {
			case Xop_list_tkn_.List_itmTyp_ol:
			case Xop_list_tkn_.List_itmTyp_ul: tag = Tag_list_itm_li_bgn; break;
			case Xop_list_tkn_.List_itmTyp_dt: tag = Tag_list_itm_dt_bgn; break;
			case Xop_list_tkn_.List_itmTyp_dd: tag = Tag_list_itm_dd_bgn; break;
			default: throw Err_.new_unhandled(type);
		}
		bfr.Add_byte_nl();
		if (indent_level > 0) bfr.Add_byte_repeat(Byte_ascii.Space, indent_level * 2);
		bfr.Add(tag);
		++indent_level;
	}
	public void List_grp_end(Xop_ctx ctx, Xoh_wtr_ctx hctx, Bry_bfr bfr, byte[] src, byte type) {
		--indent_level;
		byte[] tag = null;
		switch (type) {
			case Xop_list_tkn_.List_itmTyp_ol: tag = Tag_list_grp_ol_end; break;
			case Xop_list_tkn_.List_itmTyp_ul: tag = Tag_list_grp_ul_end; break;
			case Xop_list_tkn_.List_itmTyp_dd:
			case Xop_list_tkn_.List_itmTyp_dt: tag = Tag_list_grp_dl_end; break;
			default: throw Err_.new_unhandled(type);
		}
		bfr.Add_byte_nl();
		if (indent_level > 0) bfr.Add_byte_repeat(Byte_ascii.Space, indent_level * 2);
		bfr.Add(tag);
	}
	
	public void List_itm_end(Xop_ctx ctx, Xoh_wtr_ctx hctx, Bry_bfr bfr, byte[] src, byte type) {
		--indent_level;
		byte[] tag = null;
		switch (type) {
			case Xop_list_tkn_.List_itmTyp_ol:
			case Xop_list_tkn_.List_itmTyp_ul: tag = Tag_list_itm_li_end; break;
			case Xop_list_tkn_.List_itmTyp_dt: tag = Tag_list_itm_dt_end; break;
			case Xop_list_tkn_.List_itmTyp_dd: tag = Tag_list_itm_dd_end; break;
			default: throw Err_.new_unhandled(type);
		}
		bfr.Add_byte_if_not_last(Byte_ascii.Nl);
		if (indent_level > 0) bfr.Add_byte_repeat(Byte_ascii.Space, indent_level * 2);
		bfr.Add(tag);
	}
	public void NewLine(Xop_ctx ctx, Xoh_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_nl_tkn tkn) {
		if (hctx.Mode_is_alt())
			bfr.Add_byte_space();
		else {
			if (tkn.Nl_tid() == Xop_nl_tkn.Tid_char) {
				bfr.Add_byte_if_not_last(Byte_ascii.Nl);
			}
		}
	}
	public void Bry(Xop_ctx ctx, Xoh_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_bry_tkn bry) {
		bfr.Add(bry.Val());
	}
	public void Under(Xop_ctx ctx, Xoh_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_under_tkn under) {
		if (hctx.Mode_is_alt()) return;
		switch (under.Under_tid()) {
			case Xol_kwd_grp_.Id_toc:
				wiki.Html_mgr().Toc_mgr().Html(page, hctx, src, bfr);
				break;
			case Xol_kwd_grp_.Id_notoc:	case Xol_kwd_grp_.Id_forcetoc:	// NOTE: skip output; changes flag on page only
				break;
		}
	}
	public void Xnde(Xop_ctx ctx, Xoh_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde) {
		if (hctx.Mode_is_alt()) {
			if (xnde.Tag_close_bgn() > 0) // NOTE: some tags are not closed; WP.EX: France; <p>
				Xoh_html_wtr_escaper.Escape(app.Parser_amp_mgr(), bfr, src, xnde.Tag_open_end(), xnde.Tag_close_bgn(), true, false);
			else
				Xnde_subs(ctx, hctx, bfr, src, xnde);
			return;
		}
		Xop_xnde_tag tag = xnde.Tag();
		int tag_id = tag.Id();
		switch (tag_id) {
			case Xop_xnde_tag_.Tid_br:
				if (xnde.Src_end() - xnde.Src_bgn() < 4
					|| xnde.Src_bgn() == -1) 
					bfr.Add(Tag_br); else bfr.Add_mid(src, xnde.Src_bgn(), xnde.Src_end()); break;
			case Xop_xnde_tag_.Tid_hr: bfr.Add(Tag_hr); break;
			case Xop_xnde_tag_.Tid_includeonly:	// NOTE: do not write tags or content
				break;
			case Xop_xnde_tag_.Tid_noinclude:		// NOTE: do not write tags
			case Xop_xnde_tag_.Tid_onlyinclude:	
				Xnde_subs_escape(ctx, hctx, bfr, src, xnde, false, false);
				break;
			case Xop_xnde_tag_.Tid_nowiki:
				Xnde_subs_escape(ctx, hctx, bfr, src, xnde, false, false);
				break;
			case Xop_xnde_tag_.Tid_b: case Xop_xnde_tag_.Tid_strong:
			case Xop_xnde_tag_.Tid_i: case Xop_xnde_tag_.Tid_em: case Xop_xnde_tag_.Tid_cite: case Xop_xnde_tag_.Tid_dfn: case Xop_xnde_tag_.Tid_var:
			case Xop_xnde_tag_.Tid_u: case Xop_xnde_tag_.Tid_ins: case Xop_xnde_tag_.Tid_abbr:
			case Xop_xnde_tag_.Tid_strike: case Xop_xnde_tag_.Tid_s: case Xop_xnde_tag_.Tid_del:
			case Xop_xnde_tag_.Tid_sub: case Xop_xnde_tag_.Tid_sup: case Xop_xnde_tag_.Tid_big: case Xop_xnde_tag_.Tid_small:
			case Xop_xnde_tag_.Tid_code: case Xop_xnde_tag_.Tid_tt: case Xop_xnde_tag_.Tid_kbd: case Xop_xnde_tag_.Tid_samp: case Xop_xnde_tag_.Tid_blockquote:
			case Xop_xnde_tag_.Tid_font: case Xop_xnde_tag_.Tid_center:
			case Xop_xnde_tag_.Tid_p: case Xop_xnde_tag_.Tid_span: case Xop_xnde_tag_.Tid_div:
			case Xop_xnde_tag_.Tid_h1: case Xop_xnde_tag_.Tid_h2: case Xop_xnde_tag_.Tid_h3: case Xop_xnde_tag_.Tid_h4: case Xop_xnde_tag_.Tid_h5: case Xop_xnde_tag_.Tid_h6:
			case Xop_xnde_tag_.Tid_dt: case Xop_xnde_tag_.Tid_dd: case Xop_xnde_tag_.Tid_ol: case Xop_xnde_tag_.Tid_ul: case Xop_xnde_tag_.Tid_dl:
			case Xop_xnde_tag_.Tid_table: case Xop_xnde_tag_.Tid_tr: case Xop_xnde_tag_.Tid_td: case Xop_xnde_tag_.Tid_th: case Xop_xnde_tag_.Tid_caption: case Xop_xnde_tag_.Tid_tbody:
			case Xop_xnde_tag_.Tid_ruby: case Xop_xnde_tag_.Tid_rt: case Xop_xnde_tag_.Tid_rb: case Xop_xnde_tag_.Tid_rp: 
			case Xop_xnde_tag_.Tid_time: case Xop_xnde_tag_.Tid_bdi: case Xop_xnde_tag_.Tid_data: case Xop_xnde_tag_.Tid_mark: case Xop_xnde_tag_.Tid_wbr: case Xop_xnde_tag_.Tid_bdo:	// HTML 5: write literally and let browser handle them
			case Xop_xnde_tag_.Tid_q:
				Write_xnde(bfr, ctx, hctx, xnde, tag, tag_id, src);
				break;
			case Xop_xnde_tag_.Tid_pre: {
				if (xnde.Tag_open_end() == xnde.Tag_close_bgn()) return; // ignore empty tags, else blank pre line will be printed; DATE:2014-03-12
				byte[] name = tag.Name_bry();
				bfr.Add_byte(Byte_ascii.Angle_bgn).Add(name);
				if (xnde.Atrs_bgn() > Xop_tblw_wkr.Atrs_ignore_check) Xnde_atrs(tag_id, hctx, src, xnde.Atrs_bgn(), xnde.Atrs_end(), xnde.Atrs_ary(), bfr);
				bfr.Add_byte(Byte_ascii.Angle_end);
				Xnde_subs_escape(ctx, hctx, bfr, src, xnde, false, true);
				bfr.Add(Tag__end_bgn).Add(name).Add_byte(Byte_ascii.Angle_end);
				break;
			}
			case Xop_xnde_tag_.Tid_li: {
				byte[] name = tag.Name_bry();
				int bfr_len = bfr.Len();
				if (bfr_len > 0 && bfr.Bfr()[bfr_len - 1] != Byte_ascii.Nl) bfr.Add_byte_nl();	// NOTE: always add nl before li else some lists will merge and force long horizontal bar; EX:w:Music
				if (xnde.Tag_visible()) {
					bfr.Add_byte(Byte_ascii.Angle_bgn).Add(name);
					if (xnde.Atrs_bgn() > Xop_tblw_wkr.Atrs_ignore_check) Xnde_atrs(tag_id, hctx, src, xnde.Atrs_bgn(), xnde.Atrs_end(), xnde.Atrs_ary(), bfr);
					bfr.Add_byte(Byte_ascii.Angle_end);
				}
				Xnde_subs(ctx, hctx, bfr, src, xnde);
				if (xnde.Tag_visible())
					bfr.Add(Tag__end_bgn).Add(name).Add_byte(Byte_ascii.Angle_end);	// NOTE: inline is never written as <b/>; will be written as <b></b>; SEE: NOTE_1
				break;
			}
			case Xop_xnde_tag_.Tid_timeline: {
				bfr.Add_str_a7("<pre class='xowa-timeline'>");
				Xox_mgr_base.Xtn_write_escape(app, bfr, src, xnde.Tag_open_end(), xnde.Tag_close_bgn());	// NOTE: do not embed <timeline> tag inside pre, else timeline will render in black; EX:<pre><timeline>a</timeline></pre> will fail; DATE:2014-05-22
				bfr.Add_str_a7("</pre>");
				break;
			}
			case Xop_xnde_tag_.Tid_gallery:
			case Xop_xnde_tag_.Tid_poem:
			case Xop_xnde_tag_.Tid_hiero:
			case Xop_xnde_tag_.Tid_score:
			case Xop_xnde_tag_.Tid_ref:
			case Xop_xnde_tag_.Tid_references:
			case Xop_xnde_tag_.Tid_inputBox:
			case Xop_xnde_tag_.Tid_imageMap:
			case Xop_xnde_tag_.Tid_pages:
			case Xop_xnde_tag_.Tid_pagequality:
			case Xop_xnde_tag_.Tid_pagelist:
			case Xop_xnde_tag_.Tid_section:
			case Xop_xnde_tag_.Tid_translate:
			case Xop_xnde_tag_.Tid_dynamicPageList:
			case Xop_xnde_tag_.Tid_languages:
			case Xop_xnde_tag_.Tid_templateData:
			case Xop_xnde_tag_.Tid_source:	// DATE:2015-09-29
			case Xop_xnde_tag_.Tid_syntaxHighlight:
			case Xop_xnde_tag_.Tid_listing_buy:
			case Xop_xnde_tag_.Tid_listing_do:
			case Xop_xnde_tag_.Tid_listing_drink:
			case Xop_xnde_tag_.Tid_listing_eat:
			case Xop_xnde_tag_.Tid_listing_listing:
			case Xop_xnde_tag_.Tid_listing_see:
			case Xop_xnde_tag_.Tid_listing_sleep:
			case Xop_xnde_tag_.Tid_xowa_cmd:
			case Xop_xnde_tag_.Tid_rss:
			case Xop_xnde_tag_.Tid_quiz:
			case Xop_xnde_tag_.Tid_math:
			case Xop_xnde_tag_.Tid_indicator:
			case Xop_xnde_tag_.Tid_xowa_html:
			case Xop_xnde_tag_.Tid_graph:
				Xox_xnde xtn = xnde.Xnde_xtn();
				xtn.Xtn_write(bfr, app, ctx, this, hctx, xnde, src);
				break;
			case Xop_xnde_tag_.Tid_xowa_tag_bgn:
			case Xop_xnde_tag_.Tid_xowa_tag_end:
				break;
			default:	// unknown tag
				if (tag.Restricted()) {	// a; img; script; etc..
					if (	!page.Html_data().Html_restricted()							// page is not marked restricted (only [[Special:]])
						||	page.Wiki().Domain_tid() == Xow_domain_tid_.Int__home) {		// page is in home wiki
						bfr.Add_mid(src, xnde.Src_bgn(), xnde.Src_end());
						return;
					}
				}
				bfr.Add(Ary_escape_bgn).Add(tag.Name_bry());	// escape bgn
				if (xnde.Atrs_bgn() > Xop_tblw_wkr.Atrs_ignore_check) Xnde_atrs(tag_id, hctx, src, xnde.Atrs_bgn(), xnde.Atrs_end(), xnde.Atrs_ary(), bfr);
				switch (xnde.CloseMode()) {
					case Xop_xnde_tkn.CloseMode_inline:
						bfr.Add_byte(Byte_ascii.Slash).Add(Ary_escape_end);
						break;
					default:	// NOTE: close tag, even if dangling; EX: <center>a -> <center>a</center>
						bfr.Add_byte(Byte_ascii.Gt);
						Xnde_subs(ctx, hctx, bfr, src, xnde);
						bfr.Add(Ary_escape_bgn).Add_byte(Byte_ascii.Slash).Add(tag.Name_bry()).Add(Ary_escape_end);
						break;
				}
				break;
		}
	}
	private void Write_xnde(Bry_bfr bfr, Xop_ctx ctx, Xoh_wtr_ctx hctx, Xop_xnde_tkn xnde, Xop_xnde_tag tag, int tag_id, byte[] src) {
		byte[] name = tag.Name_bry();
		boolean at_bgn = true;
		Bry_bfr ws_bfr = wiki.Utl__bfr_mkr().Get_b512();					// create separate ws_bfr to handle "a<b> c </b>d" -> "a <b>c</b> d"
		int subs_len = xnde.Subs_len();
		for (int i = 0; i < subs_len; i++) {
			Xop_tkn_itm sub = xnde.Subs_get(i);
			byte tkn_tid = sub.Tkn_tid();
			switch (tkn_tid) {
				case Xop_tkn_itm_.Tid_space:									// space; add to ws_bfr;
					ws_bfr.Add_mid(src, sub.Src_bgn(), sub.Src_end());
					break;
				default:
					if (tkn_tid == Xop_tkn_itm_.Tid_html_ncr) {					// html_entity &#32; needed for fr.wikipedia.org and many spans with <span>&#32;</span>; DATE:2013-06-18
						Xop_amp_tkn_num ncr_tkn = (Xop_amp_tkn_num)sub;
						if (ncr_tkn.Val() == Byte_ascii.Space
							|| ncr_tkn.Val() == 160
							) {

							ws_bfr.Add_mid(src, ncr_tkn.Src_bgn(), ncr_tkn.Src_end());
							continue;											// just add entity; don't process rest;
						}
					}
					if (ws_bfr.Len() > 0) bfr.Add_bfr_and_clear(ws_bfr);	// dump ws_bfr to real bfr
					if (at_bgn) {												// 1st non-ws tkn; add open tag; <b>
						at_bgn = false;
						bfr.Add_byte(Byte_ascii.Angle_bgn).Add(name);
						if (xnde.Atrs_bgn() > Xop_tblw_wkr.Atrs_ignore_check) Xnde_atrs(tag_id, hctx, src, xnde.Atrs_bgn(), xnde.Atrs_end(), xnde.Atrs_ary(), bfr);
						bfr.Add_byte(Byte_ascii.Angle_end);
					}
					Write_tkn(bfr, ctx, hctx, src, xnde, i, sub);			// NOTE: never escape; <p>, <table>, <center> etc may have nested nodes
					break;
			}
		}
		if (at_bgn) {	// occurs when xnde is empty; EX: <b></b>
			bfr.Add_byte(Byte_ascii.Angle_bgn).Add(name);
			if (xnde.Atrs_bgn() > Xop_tblw_wkr.Atrs_ignore_check) Xnde_atrs(tag_id, hctx, src, xnde.Atrs_bgn(), xnde.Atrs_end(), xnde.Atrs_ary(), bfr);
			bfr.Add_byte(Byte_ascii.Angle_end);
		}
		bfr.Add(Tag__end_bgn).Add(name).Add_byte(Byte_ascii.Angle_end);						// NOTE: inline is never written as <b/>; will be written as <b></b>; SEE: NOTE_1
		if (ws_bfr.Len() > 0) bfr.Add_bfr_and_clear(ws_bfr);				// dump any leftover ws to bfr; handles "<b>c </b>" -> "<b>c</b> "
		ws_bfr.Mkr_rls();
	}		
	public void Xnde_atrs(int tag_id, Xoh_wtr_ctx hctx, byte[] src, int bgn, int end, Mwh_atr_itm[] ary, Bry_bfr bfr) {
		if (ary == null) return;	// NOTE: some nodes will have null xatrs b/c of whitelist; EX: <pre style="overflow:auto">a</pre>; style is not on whitelist so not xatr generated, but xatr_bgn will != -1
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			Mwh_atr_itm atr = ary[i];
			if (atr.Invalid()) continue;
			if (!whitelist_mgr.Chk(tag_id, src, atr)) continue;
			Xnde_atr_write(bfr, app, hctx, src, atr);
		}
	}

	public static void Xnde_atr_write(Bry_bfr bfr, Xoae_app app, Xoh_wtr_ctx hctx, byte[] src, Mwh_atr_itm atr) {
		byte[] atr_key = atr.Key_bry();
		if (	hctx.Mode_is_display_title()
			&&	Xoh_display_ttl_wtr.Instance.Is_style_restricted(bfr, hctx, src, atr, atr_key))
			return;
	
		bfr.Add_byte(Byte_ascii.Space);	// add space before every attribute
		if (atr_key != null) {
			bfr.Add(atr_key);
			bfr.Add_byte(Byte_ascii.Eq);
		}
		byte quote_byte = atr.Qte_byte(); if (quote_byte == Byte_ascii.Null) quote_byte = Byte_ascii.Quote;
		bfr.Add_byte(quote_byte);
		if (atr.Key_tid() == Mwh_atr_itm_.Key_tid__id) {	// ids should not have spaces; DATE:2013-04-01
			if (atr.Val_bry() == null)
				Xnde_atr_write_id(bfr, app, atr.Src(), atr.Val_bgn(), atr.Val_end());
			else {
				byte[] atr_val = atr.Val_bry();
				Xnde_atr_write_id(bfr, app, atr_val, 0, atr_val.length);
			}
		}
		else {
			if (atr.Val_bry() == null)
				bfr.Add_mid(src, atr.Val_bgn(), atr.Val_end());
			else
				bfr.Add(atr.Val_bry());
		}
		bfr.Add_byte(quote_byte);
	}
	private static void Xnde_atr_write_id(Bry_bfr bfr, Xoae_app app, byte[] bry, int bgn, int end) {
		gplx.langs.htmls.encoders.Gfo_url_encoder_.Id.Encode(bfr, bry, bgn, end);
	}
	private void Xnde_subs(Xop_ctx ctx, Xoh_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde) {
		int subs_len = xnde.Subs_len();
		for (int i = 0; i < subs_len; i++)
			Write_tkn(bfr, ctx, hctx, src, xnde, i, xnde.Subs_get(i));
	}
	private void Xnde_subs_escape(Xop_ctx ctx, Xoh_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde, boolean amp_enable, boolean nowiki) {
		int xndesubs_len = xnde.Subs_len();
		for (int i = 0; i < xndesubs_len; i++) {
			Xop_tkn_itm sub = xnde.Subs_get(i);
			switch (sub.Tkn_tid()) {
				case Xop_tkn_itm_.Tid_xnde:
					Xop_xnde_tkn sub_xnde = (Xop_xnde_tkn)sub;
					switch (sub_xnde.Tag().Id()) {
						case Xop_xnde_tag_.Tid_noinclude:
						case Xop_xnde_tag_.Tid_onlyinclude:
						case Xop_xnde_tag_.Tid_includeonly:
							break;
						default:
							byte[] tag_name = sub_xnde.Tag().Name_bry();
							bfr.Add(Gfh_entity_.Lt_bry).Add(tag_name);
							if (xnde.Atrs_bgn() > Xop_tblw_wkr.Atrs_ignore_check) Xnde_atrs(sub_xnde.Tag().Id(), hctx, src, sub_xnde.Atrs_bgn(), sub_xnde.Atrs_end(), xnde.Atrs_ary(), bfr);
							bfr.Add(Gfh_entity_.Gt_bry);
							break;
					}
					Xnde_subs_escape(ctx, hctx, bfr, src, sub_xnde, amp_enable, false);
					break;
				case Xop_tkn_itm_.Tid_txt:
					if (amp_enable)
						bfr.Add_mid(src, sub.Src_bgn(), sub.Src_end());
					else
						Xoh_html_wtr_escaper.Escape(app.Parser_amp_mgr(), bfr, src, sub.Src_bgn(), sub.Src_end(), true, nowiki);
					break;
				default:
					Write_tkn(bfr, ctx, hctx, src, xnde, i, sub);
					break;
			}
		}
	}
	public void Tblw(Xop_ctx ctx, Xoh_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_tblw_tkn tkn, byte[] bgn, byte[] end, boolean tblw_bgn) {
		if (hctx.Mode_is_alt())			// add \s for each \n
			bfr.Add_byte_space();
		else {
			bfr.Add_byte_if_not_last(Byte_ascii.Nl);
			if (indent_level > 0) bfr.Add_byte_repeat(Byte_ascii.Space, indent_level * 2);
			bfr.Add(bgn);
			int atrs_bgn = tkn.Atrs_bgn();
			if (atrs_bgn != -1) Xnde_atrs(tkn.Tblw_tid(), hctx, src, atrs_bgn, tkn.Atrs_end(), tkn.Atrs_ary(), bfr); //bfr.Add_byte(Byte_ascii.Space).Add_mid(src, atrs_bgn, tkn.Atrs_end());
			bfr.Add_byte(Byte_ascii.Angle_end);
			++indent_level;
		}
		int subs_len = tkn.Subs_len();
		for (int i = 0; i < subs_len; i++)
			Write_tkn(bfr, ctx, hctx, src, tkn, i, tkn.Subs_get(i));
		if (hctx.Mode_is_alt()) {
			if (tblw_bgn)			// only add \s for closing table; |} -> "\s"
				bfr.Add_byte_space();
		}
		else {
			--indent_level;
			bfr.Add_byte_if_not_last(Byte_ascii.Nl);
			if (indent_level > 0) bfr.Add_byte_repeat(Byte_ascii.Space, indent_level * 2);
			bfr.Add(end);
			bfr.Add_byte_if_not_last(Byte_ascii.Nl);
		}
	}
	public static final byte[] Tag__end_quote = Bry_.new_a7("\">"), Tag__end_bgn = Bry_.new_a7("</")
	, Tag_hr = Bry_.new_a7("<hr/>"), Tag_br = Bry_.new_a7("<br/>")
	, Tag_list_grp_ul_bgn = Bry_.new_a7("<ul>"), Tag_list_grp_ul_end = Bry_.new_a7("</ul>")
	, Tag_list_grp_ol_bgn = Bry_.new_a7("<ol>"), Tag_list_grp_ol_end = Bry_.new_a7("</ol>")
	, Tag_list_itm_li_bgn = Bry_.new_a7("<li>"), Tag_list_itm_li_end = Bry_.new_a7("</li>")
	, Tag_list_itm_dt_bgn = Bry_.new_a7("<dt>"), Tag_list_itm_dt_end = Bry_.new_a7("</dt>")
	, Tag_list_itm_dd_bgn = Bry_.new_a7("<dd>"), Tag_list_itm_dd_end = Bry_.new_a7("</dd>")
	, Tag_list_grp_dl_bgn = Bry_.new_a7("<dl>"), Tag_list_grp_dl_end = Bry_.new_a7("</dl>")
	, File_divider = Bry_.new_a7("---------------------------------")
	, Tag_tblw_tb_bgn = Bry_.new_a7("<table>"), Tag_tblw_tb_bgn_atr = Bry_.new_a7("<table"), Tag_tblw_tb_end = Bry_.new_a7("</table>")
	, Tag_tblw_tr_bgn = Bry_.new_a7("<tr>"), Tag_tblw_tr_bgn_atr = Bry_.new_a7("<tr"), Tag_tblw_tr_end = Bry_.new_a7("</tr>")
	, Tag_tblw_td_bgn = Bry_.new_a7("<td>"), Tag_tblw_td_bgn_atr = Bry_.new_a7("<td"), Tag_tblw_td_end = Bry_.new_a7("</td>")
	, Tag_tblw_th_bgn = Bry_.new_a7("<th>"), Tag_tblw_th_bgn_atr = Bry_.new_a7("<th"), Tag_tblw_th_end = Bry_.new_a7("</th>")
	, Tag_tblw_tc_bgn = Bry_.new_a7("<caption>"), Tag_tblw_tc_bgn_atr = Bry_.new_a7("<caption"), Tag_tblw_tc_end = Bry_.new_a7("</caption>")
	, Ary_escape_bgn = Bry_.new_a7("&lt;"), Ary_escape_end = Bry_.new_a7("&gt;"), Ary_escape_end_bgn = Bry_.new_a7("&lt;/")
	;
	public static final byte Dir_spr_http = Byte_ascii.Slash;
	private int indent_level = 0;
	public static final int Sub_idx_null = -1;	// nonsense placeholder
}
class Xoh_display_ttl_wtr {
	private static final byte[] 
	  Atr_key_style = Bry_.new_a7("style")
	, Msg_style_restricted = Bry_.new_a7(" style='/* attempt to bypass $wgRestrictDisplayTitle */'")
	;
	private Btrie_slim_mgr style_trie = Btrie_slim_mgr.ci_a7()
	.Add_str_byte__many(Byte_.By_int(0), "display", "user-select", "visibility");  // if ( preg_match( '/(display|user-select|visibility)\s*:/i', $decoded['style'] ) ) {
	public boolean Is_style_restricted(Bry_bfr bfr, Xoh_wtr_ctx hctx, byte[] src, Mwh_atr_itm atr, byte[] atr_key) {
		if (atr_key != null 
			&& Bry_.Eq(atr_key, Atr_key_style)
			) {
			byte[] atr_val = atr.Val_as_bry(); if (atr_val == null) return false; // bounds_chk
			int atr_val_len = atr_val.length;
			int atr_pos = 0;
			while (atr_pos < atr_val_len) {
				byte b = atr_val[atr_pos];
				Object o = style_trie.Match_bgn_w_byte(b, atr_val, atr_pos, atr_val_len);
				if (o != null) {
					bfr.Add(Msg_style_restricted);
					return true;
				}
				++atr_pos;
			}
		}
		return false;
	}
	public static final Xoh_display_ttl_wtr Instance = new Xoh_display_ttl_wtr(); Xoh_display_ttl_wtr() {}
}
/*
NOTE_1:inline always written as <tag></tag>, not <tag/>
see WP:Permian�Triassic extinction event
this will cause firefox to swallow up rest of text
<div id="ScaleBar" style="width:1px; float:left; height:38em; padding:0; background-color:#242020" />
this will not
<div id="ScaleBar" style="width:1px; float:left; height:38em; padding:0; background-color:#242020"  ></div>
*/