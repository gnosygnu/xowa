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
import gplx.core.btries.*; import gplx.html.*; import gplx.xowa.wikis.*; import gplx.xowa.net.*;
import gplx.xowa.parsers.apos.*; import gplx.xowa.parsers.amps.*; import gplx.xowa.parsers.lnkes.*; import gplx.xowa.parsers.hdrs.*; import gplx.xowa.parsers.lists.*;
import gplx.xowa.xtns.*; import gplx.xowa.xtns.dynamicPageList.*; import gplx.xowa.xtns.math.*; import gplx.xowa.langs.vnts.*; import gplx.xowa.xtns.cite.*;
public class Xoh_html_wtr {
	private Xow_wiki wiki; private Xoa_app app; private Xoa_page page; private Xop_xatr_whitelist_mgr whitelist_mgr;
	public Xoh_html_wtr(Xow_wiki wiki, Xow_html_mgr html_mgr) {
		this.wiki = wiki; this.app = wiki.App(); this.whitelist_mgr = app.Html_mgr().Whitelist_mgr();
		this.html_mgr = html_mgr;
		lnki_wtr = new Xoh_lnki_wtr(this, wiki, html_mgr, cfg);
		ref_wtr = new Ref_html_wtr(wiki);
		lnke_wtr = new Xoh_lnke_wtr(wiki);
	}
	public void Init_by_wiki(Xow_wiki wiki) {
		cfg.Toc_show_(true).Lnki_title_(true).Lnki_visited_(true).Lnki_id_(true);	// NOTE: set during Init_by_wiki, b/c all tests assume these are false
		ref_wtr.Init_by_wiki(wiki);
	}
	public Xow_html_mgr Html_mgr() {return html_mgr;} private Xow_html_mgr html_mgr;
	public Xoh_html_wtr_cfg Cfg() {return cfg;} private Xoh_html_wtr_cfg cfg = new Xoh_html_wtr_cfg();
	public Xoh_lnki_wtr Lnki_wtr() {return lnki_wtr;} private Xoh_lnki_wtr lnki_wtr;
	public Xoh_lnke_wtr Lnke_wtr() {return lnke_wtr;} private Xoh_lnke_wtr lnke_wtr;
	public Ref_html_wtr Ref_wtr() {return ref_wtr;} private Ref_html_wtr ref_wtr; 
	public void Init_by_page(Xop_ctx ctx, Xoa_page page) {this.page = page; lnki_wtr.Init_by_page(ctx, page);}
	public void Write_all(Bry_bfr bfr, Xop_ctx ctx, byte[] src, Xop_root_tkn root) {Write_all(bfr, ctx, Xoh_html_wtr_ctx.Basic, src, root);}
	public void Write_all(Bry_bfr bfr, Xop_ctx ctx, Xoh_html_wtr_ctx hctx, byte[] src, Xop_root_tkn root) {			
		try {
			indent_level = 0; this.page = ctx.Cur_page();
			page.Xwiki_langs().Clear();	// HACK: always clear langs; necessary for reload
			lnki_wtr.Init_by_page(ctx, ctx.Cur_page());
			Write_tkn(bfr, ctx, hctx, src, null, -1, root);
		}
		finally {
			page.Category_list_(page.Html_data().Ctgs_to_ary());
		}
	}
	public void Write_tkn_ary(Bry_bfr bfr, Xop_ctx ctx, Xoh_html_wtr_ctx hctx, byte[] src, Xop_tkn_itm[] ary) {
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			Xop_tkn_itm itm = ary[i];
			Write_tkn(bfr, ctx, hctx, src, itm, i, itm);
		}
	}
	public void Write_tkn(Bry_bfr bfr, Xop_ctx ctx, Xoh_html_wtr_ctx hctx, byte[] src, Xop_tkn_grp grp, int sub_idx, Xop_tkn_itm tkn) {
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
			case Xop_tkn_itm_.Tid_hdr:				Hdr(ctx, hctx, bfr, src, (Xop_hdr_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_apos:				Apos(ctx, hctx, bfr, src, (Xop_apos_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_lnke:				lnke_wtr.Write_all(bfr, this, hctx, ctx, src, (Xop_lnke_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_lnki:				lnki_wtr.Write(bfr, hctx, src, (Xop_lnki_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_list:				List(ctx, hctx, bfr, src, (Xop_list_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_xnde:				Xnde(ctx, hctx, bfr, src, (Xop_xnde_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_under:			Under(ctx, hctx, bfr, src, (Xop_under_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_tblw_tb:			Tblw(ctx, hctx, bfr, src, (Xop_tblw_tkn)tkn, Tag_tblw_tb_bgn_atr, Tag_tblw_tb_end, true); break;
			case Xop_tkn_itm_.Tid_tblw_tr:			Tblw(ctx, hctx, bfr, src, (Xop_tblw_tkn)tkn, Tag_tblw_tr_bgn_atr, Tag_tblw_tr_end, false); break;
			case Xop_tkn_itm_.Tid_tblw_td:			Tblw(ctx, hctx, bfr, src, (Xop_tblw_tkn)tkn, Tag_tblw_td_bgn_atr, Tag_tblw_td_end, false); break;
			case Xop_tkn_itm_.Tid_tblw_th:			Tblw(ctx, hctx, bfr, src, (Xop_tblw_tkn)tkn, Tag_tblw_th_bgn_atr, Tag_tblw_th_end, false); break;
			case Xop_tkn_itm_.Tid_tblw_tc:			Tblw(ctx, hctx, bfr, src, (Xop_tblw_tkn)tkn, Tag_tblw_tc_bgn_atr, Tag_tblw_tc_end, false); break;
			case Xop_tkn_itm_.Tid_para:				Para(ctx, hctx, bfr, src, (Xop_para_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_space:			Space(ctx, hctx, bfr, src, grp, sub_idx, (Xop_space_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_pre:				Pre(ctx, hctx, bfr, src, (Xop_pre_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_newLine:			NewLine(ctx, hctx, bfr, src, (Xop_nl_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_bry:				Bry(ctx, hctx, bfr, src, (Xop_bry_tkn)tkn); break;
			case Xop_tkn_itm_.Tid_vnt:				Vnt(ctx, hctx, bfr, src, (Xop_vnt_tkn)tkn); break;
//				case Xop_tkn_itm_.Tid_tab:				bfr.Add_byte_repeat(Byte_ascii.Tab, tkn.Src_end() - tkn.Src_bgn()); break;
			default:
				Xoh_html_wtr_escaper.Escape(app, bfr, src, tkn.Src_bgn(), tkn.Src_end(), true, false);	// NOTE: always escape text including (a) lnki_alt text; and (b) any other text, especially failed xndes; DATE:2013-06-18
				break;
		}
	}
	@gplx.Virtual public void Html_ncr(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_amp_tkn_num tkn)	{
		if (tkn.Val() < Byte_ascii.Max_7_bit)		// NOTE: must "literalize"; <nowiki> converts wiki chars to ncrs, which must be "literalized: EX: <nowiki>[[A]]</nowiki> -> &#92;&#92;A&#93;&#93; which must be converted back to [[A]]
			bfr.Add(tkn.Str_as_bry());
		else
			bfr.Add_byte(Byte_ascii.Amp).Add_byte(Byte_ascii.Hash).Add_int_variable(tkn.Val()).Add_byte(Byte_ascii.Semic);	// NOTE: do not literalize, else browser may not display multi-char bytes properly; EX: &#160; gets added as &#160; not as {192,160}; DATE:2013-12-09
	}
	@gplx.Virtual public void Html_ref(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_amp_tkn_txt tkn)	{tkn.Print_to_html(bfr);}
	private static final byte[] Bry_hdr_bgn = Bry_.new_ascii_("<span class='mw-headline' id='"), Bry_hdr_end = Bry_.new_ascii_("</span>");
	@gplx.Virtual public void Hr(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_hr_tkn tkn)				{bfr.Add(Tag_hr);}
	@gplx.Virtual public void Hdr(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_hdr_tkn hdr) {
//			page.Hdrs_id_bld(hdr, src);
		if (hdr.Hdr_html_first() && cfg.Toc_show() && !page.Hdr_mgr().Toc_manual()) {	// __TOC__ not specified; place at top; NOTE: if __TOC__ was specified, then it would be placed wherever __TOC__ appears
			wiki.Html_mgr().Toc_mgr().Html(ctx.Cur_page(), src, bfr);
		}
		int hdr_len = hdr.Hdr_len();
		if (hdr_len > 0) {	// NOTE: need to check hdr_len b/c it could be dangling
			Para_assert_tag_starts_on_nl(bfr, hdr.Src_bgn()); 
			bfr.Add(Tag_hdr_bgn).Add_int(hdr_len, 1, 1);
			if (cfg.Toc_show()) {
				bfr.Add_byte(Tag__end);
				bfr.Add(Bry_hdr_bgn);
				bfr.Add(hdr.Hdr_html_id());
				bfr.Add_byte(Byte_ascii.Apos);
			}
			bfr.Add_byte(Tag__end);
		}	
		if (hdr.Hdr_bgn_manual() > 0) bfr.Add_byte_repeat(Byte_ascii.Eq, hdr.Hdr_bgn_manual());
		int subs_len = hdr.Subs_len();
		for (int i = 0; i < subs_len; i++)
			Write_tkn(bfr, ctx, hctx, src, hdr, i, hdr.Subs_get(i));
		if (hdr_len > 0) {	// NOTE: need to check hdr_len b/c it could be dangling
			if (hdr.Hdr_end_manual() > 0) bfr.Add_byte_repeat(Byte_ascii.Eq, hdr.Hdr_end_manual());
			if (cfg.Toc_show()) {
				bfr.Add(Bry_hdr_end);
			}
			bfr.Add(Tag_hdr_end).Add_int(hdr_len, 1, 1).Add_byte(Tag__end).Add_byte_nl();// NOTE: do not need to check hdr_len b/c it is impossible for end to occur without bgn
		}
	}
	public void Apos(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_apos_tkn apos) {
		if (hctx.Mode_is_alt()) return;	// ignore apos if alt; EX: [[File:A.png|''A'']] should have alt of A; DATE:2013-10-25
		int literal_apos = apos.Apos_lit();
		if (literal_apos > 0)
			bfr.Add_byte_repeat(Byte_ascii.Apos, literal_apos);
		switch (apos.Apos_cmd()) {
			case Xop_apos_tkn_.Cmd_b_bgn:			bfr.Add(Html_tag_.B_lhs); break;
			case Xop_apos_tkn_.Cmd_b_end:			bfr.Add(Html_tag_.B_rhs); break;
			case Xop_apos_tkn_.Cmd_i_bgn:			bfr.Add(Html_tag_.I_lhs); break;		
			case Xop_apos_tkn_.Cmd_i_end:			bfr.Add(Html_tag_.I_rhs); break;
			case Xop_apos_tkn_.Cmd_bi_bgn:			bfr.Add(Html_tag_.B_lhs).Add(Html_tag_.I_lhs); break;		
			case Xop_apos_tkn_.Cmd_ib_end:			bfr.Add(Html_tag_.I_rhs).Add(Html_tag_.B_rhs); break;
			case Xop_apos_tkn_.Cmd_ib_bgn:			bfr.Add(Html_tag_.I_lhs).Add(Html_tag_.B_lhs); break;		
			case Xop_apos_tkn_.Cmd_bi_end:			bfr.Add(Html_tag_.B_rhs).Add(Html_tag_.I_rhs);; break;
			case Xop_apos_tkn_.Cmd_bi_end__b_bgn:	bfr.Add(Html_tag_.B_rhs).Add(Html_tag_.I_rhs).Add(Html_tag_.B_lhs); break;
			case Xop_apos_tkn_.Cmd_ib_end__i_bgn:	bfr.Add(Html_tag_.I_rhs).Add(Html_tag_.B_rhs).Add(Html_tag_.I_lhs); break;
			case Xop_apos_tkn_.Cmd_b_end__i_bgn:	bfr.Add(Html_tag_.B_rhs).Add(Html_tag_.I_lhs); break;		
			case Xop_apos_tkn_.Cmd_i_end__b_bgn:	bfr.Add(Html_tag_.I_rhs).Add(Html_tag_.B_lhs); break;
			case Xop_apos_tkn_.Cmd_nil:				break;
			default: throw Err_.unhandled(apos.Apos_cmd());
		}
	}
	public static byte[] Ttl_to_title(byte[] ttl) {return ttl;}	// FUTURE: swap html chars?
	public void List(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_list_tkn list) {
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
	@gplx.Virtual public void List_grp_bgn(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, byte type) {
		byte[] tag = null;
		switch (type) {
			case Xop_list_tkn_.List_itmTyp_ol: tag = Tag_list_grp_ol_bgn; break;
			case Xop_list_tkn_.List_itmTyp_ul: tag = Tag_list_grp_ul_bgn; break;
			case Xop_list_tkn_.List_itmTyp_dd:
			case Xop_list_tkn_.List_itmTyp_dt: tag = Tag_list_grp_dl_bgn; break;
			default: throw Err_.unhandled(type);
		}
		if (bfr.Len() > 0) bfr.Add_byte_nl();	// NOTE: do not add newLine if start 
		if (indent_level > 0) bfr.Add_byte_repeat(Byte_ascii.Space, indent_level * 2);
		bfr.Add(tag);
		++indent_level;
	}
	@gplx.Virtual public void List_itm_bgn(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, byte type) {
		byte[] tag = null;
		switch (type) {
			case Xop_list_tkn_.List_itmTyp_ol:
			case Xop_list_tkn_.List_itmTyp_ul: tag = Tag_list_itm_li_bgn; break;
			case Xop_list_tkn_.List_itmTyp_dt: tag = Tag_list_itm_dt_bgn; break;
			case Xop_list_tkn_.List_itmTyp_dd: tag = Tag_list_itm_dd_bgn; break;
			default: throw Err_.unhandled(type);
		}
		bfr.Add_byte_nl();
		if (indent_level > 0) bfr.Add_byte_repeat(Byte_ascii.Space, indent_level * 2);
		bfr.Add(tag);
		++indent_level;
	}
	@gplx.Virtual public void List_grp_end(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, byte type) {
		--indent_level;
		byte[] tag = null;
		switch (type) {
			case Xop_list_tkn_.List_itmTyp_ol: tag = Tag_list_grp_ol_end; break;
			case Xop_list_tkn_.List_itmTyp_ul: tag = Tag_list_grp_ul_end; break;
			case Xop_list_tkn_.List_itmTyp_dd:
			case Xop_list_tkn_.List_itmTyp_dt: tag = Tag_list_grp_dl_end; break;
			default: throw Err_.unhandled(type);
		}
		bfr.Add_byte_nl();
		if (indent_level > 0) bfr.Add_byte_repeat(Byte_ascii.Space, indent_level * 2);
		bfr.Add(tag);
	}
	
	@gplx.Virtual public void List_itm_end(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, byte type) {
		--indent_level;
		byte[] tag = null;
		switch (type) {
			case Xop_list_tkn_.List_itmTyp_ol:
			case Xop_list_tkn_.List_itmTyp_ul: tag = Tag_list_itm_li_end; break;
			case Xop_list_tkn_.List_itmTyp_dt: tag = Tag_list_itm_dt_end; break;
			case Xop_list_tkn_.List_itmTyp_dd: tag = Tag_list_itm_dd_end; break;
			default: throw Err_.unhandled(type);
		}
		bfr.Add_byte_if_not_last(Byte_ascii.NewLine);
		if (indent_level > 0) bfr.Add_byte_repeat(Byte_ascii.Space, indent_level * 2);
		bfr.Add(tag);
	}
	@gplx.Virtual public void NewLine(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_nl_tkn tkn) {
		if (hctx.Mode_is_alt())
			bfr.Add_byte_space();
		else {
			if (tkn.Nl_tid() == Xop_nl_tkn.Tid_char) {
				bfr.Add_byte_if_not_last(Byte_ascii.NewLine);
			}
		}
	}
	public void Space(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_tkn_grp grp, int sub_idx, Xop_space_tkn space) {
		bfr.Add_byte_repeat(Byte_ascii.Space, space.Src_end_grp(grp, sub_idx) - space.Src_bgn_grp(grp, sub_idx));	// NOTE: lnki.caption will convert \n to \s; see Xop_nl_lxr; PAGE:en.w:Schwarzschild radius
	}
	@gplx.Virtual public void Para(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_para_tkn para) {
		if (para.Nl_bgn() && bfr.Len() > 0) {
			if (hctx.Mode_is_alt())
				bfr.Add_byte_space();
			else
				bfr.Add_byte_if_not_last(Byte_ascii.NewLine);
		}
		switch (para.Para_end()) {
			case Xop_para_tkn.Tid_none:		break;
			case Xop_para_tkn.Tid_para:		bfr.Add(Tag_para_end).Add_byte_nl(); break;
			case Xop_para_tkn.Tid_pre:			bfr.Add(Tag_pre_end).Add_byte_nl(); break;
			default:								throw Err_.unhandled(para.Para_end());
		}
		switch (para.Para_bgn()) {
			case Xop_para_tkn.Tid_none:		break;
			case Xop_para_tkn.Tid_para:		Para_assert_tag_starts_on_nl(bfr, para.Src_bgn()); bfr.Add(Tag_para_bgn); break;
			case Xop_para_tkn.Tid_pre:			Para_assert_tag_starts_on_nl(bfr, para.Src_bgn()); bfr.Add(Tag_pre_bgn); break;
			default:								throw Err_.unhandled(para.Para_bgn());
		}
		if (para.Space_bgn() > 0)
			bfr.Add_byte_repeat(Byte_ascii.Space, para.Space_bgn());
	}
	private void Para_assert_tag_starts_on_nl(Bry_bfr bfr, int src_bgn) {
		if (!bfr.Match_end_byt_nl_or_bos()) bfr.Add_byte_nl();
		if (src_bgn != 0) bfr.Add_byte_nl();
	}
	@gplx.Virtual public void Pre(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_pre_tkn pre) {
		switch (pre.Pre_tid()) {
			case Xop_pre_tkn.Pre_tid_bgn:
				bfr.Add(Tag_pre_bgn);
				break;
			case Xop_pre_tkn.Pre_tid_end:
				bfr.Add_byte_nl().Add(Tag_pre_end).Add_byte_nl().Add_byte_nl();
				break;
		}
	}
	@gplx.Virtual public void Bry(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_bry_tkn bry) {
		bfr.Add(bry.Val());
	}
	@gplx.Virtual public void Vnt(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_vnt_tkn vnt) {
		Xop_vnt_html_wtr.Write(this, ctx, hctx, bfr, src, vnt);	// NOTE: using wiki, b/c getting nullPointer with ctx during mass parse
	}
	@gplx.Virtual public void Under(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_under_tkn under) {
		if (hctx.Mode_is_alt()) return;
		switch (under.Under_tid()) {
			case Xol_kwd_grp_.Id_toc:
				wiki.Html_mgr().Toc_mgr().Html(page, src, bfr);
				break;
			case Xol_kwd_grp_.Id_notoc:	case Xol_kwd_grp_.Id_forcetoc:	// NOTE: skip output; changes flag on page only
				break;
		}
	}
	@gplx.Virtual public void Xnde(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde) {
		if (hctx.Mode_is_alt()) {
			if (xnde.Tag_close_bgn() > 0) // NOTE: some tags are not closed; WP.EX: France; <p>
				Xoh_html_wtr_escaper.Escape(app, bfr, src, xnde.Tag_open_end(), xnde.Tag_close_bgn(), true, false);
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
				Write_xnde(bfr, ctx, hctx, xnde, tag, tag_id, src);
				break;
			case Xop_xnde_tag_.Tid_pre: {
				if (xnde.Tag_open_end() == xnde.Tag_close_bgn()) return; // ignore empty tags, else blank pre line will be printed; DATE:2014-03-12
				byte[] name = tag.Name_bry();
				bfr.Add_byte(Tag__bgn).Add(name);
				if (xnde.Atrs_bgn() > Xop_tblw_wkr.Atrs_ignore_check) Xnde_atrs(tag_id, hctx, src, xnde.Atrs_bgn(), xnde.Atrs_end(), xnde.Atrs_ary(), bfr);
				bfr.Add_byte(Tag__end);
				Xnde_subs_escape(ctx, hctx, bfr, src, xnde, false, true);
				bfr.Add(Tag__end_bgn).Add(name).Add_byte(Tag__end);
				break;
			}
			case Xop_xnde_tag_.Tid_li: {
				byte[] name = tag.Name_bry();
				int bfr_len = bfr.Len();
				if (bfr_len > 0 && bfr.Bfr()[bfr_len - 1] != Byte_ascii.NewLine) bfr.Add_byte_nl();	// NOTE: always add nl before li else some lists will merge and force long horizontal bar; EX:w:Music
				if (xnde.Tag_visible()) {
					bfr.Add_byte(Tag__bgn).Add(name);
					if (xnde.Atrs_bgn() > Xop_tblw_wkr.Atrs_ignore_check) Xnde_atrs(tag_id, hctx, src, xnde.Atrs_bgn(), xnde.Atrs_end(), xnde.Atrs_ary(), bfr);
					bfr.Add_byte(Tag__end);
				}
				Xnde_subs(ctx, hctx, bfr, src, xnde);
				if (xnde.Tag_visible())
					bfr.Add(Tag__end_bgn).Add(name).Add_byte(Tag__end);	// NOTE: inline is never written as <b/>; will be written as <b></b>; SEE: NOTE_1
				break;
			}
			case Xop_xnde_tag_.Tid_timeline: {
				bfr.Add_str("<pre class='xowa-timeline'>");
				Xox_mgr_base.Xtn_write_escape(app, bfr, src, xnde.Tag_open_end(), xnde.Tag_close_bgn());	// NOTE: do not embed <timeline> tag inside pre, else timeline will render in black; EX:<pre><timeline>a</timeline></pre> will fail; DATE:2014-05-22
				bfr.Add_str("</pre>");
				break;
			}
			case Xop_xnde_tag_.Tid_source: {							// convert <source> to <pre>;
				byte[] name = Xop_xnde_tag_.Tag_pre.Name_bry();
				bfr.Add_byte(Tag__bgn).Add(name);
				if (xnde.Atrs_bgn() > Xop_tblw_wkr.Atrs_ignore_check) Xnde_atrs(tag_id, hctx, src, xnde.Atrs_bgn(), xnde.Atrs_end(), xnde.Atrs_ary(), bfr);
				bfr.Add_byte(Tag__end);
				int tag_close_bgn = Bry_finder.Find_bwd_while(src, xnde.Tag_close_bgn(), -1, Byte_ascii.Space) + 1;	// trim space from end; PAGE:en.w:Comment_(computer_programming) DATE:2014-06-23
				Xoh_html_wtr_escaper.Escape(app, bfr, src, xnde.Tag_open_end(), tag_close_bgn, false, false);	// <source> is a .Xtn(); render literally everything between > and <; DATE:2014-03-11
				bfr.Add(Tag__end_bgn).Add(name).Add_byte(Tag__end);
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
			case Xop_xnde_tag_.Tid_xowa_html:
				Xox_xnde xtn = xnde.Xnde_xtn();
				xtn.Xtn_write(app, this, hctx, ctx, bfr, src, xnde);
				break;
			case Xop_xnde_tag_.Tid_xowa_tag_bgn:
			case Xop_xnde_tag_.Tid_xowa_tag_end:
				break;
			default:	// unknown tag
				if (tag.Restricted()) {	// a; img; script; etc..
					if (	!page.Html_data().Html_restricted()							// page is not marked restricted (only [[Special:]])
						||	page.Wiki().Domain_tid() == Xow_wiki_domain_.Tid_home) {	// page is in home wiki
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
	private void Write_xnde(Bry_bfr bfr, Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Xop_xnde_tkn xnde, Xop_xnde_tag tag, int tag_id, byte[] src) {
		byte[] name = tag.Name_bry();
		boolean at_bgn = true;
		Bry_bfr ws_bfr = wiki.Utl_bry_bfr_mkr().Get_b512();					// create separate ws_bfr to handle "a<b> c </b>d" -> "a <b>c</b> d"
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
						bfr.Add_byte(Tag__bgn).Add(name);
						if (xnde.Atrs_bgn() > Xop_tblw_wkr.Atrs_ignore_check) Xnde_atrs(tag_id, hctx, src, xnde.Atrs_bgn(), xnde.Atrs_end(), xnde.Atrs_ary(), bfr);
						bfr.Add_byte(Tag__end);
					}
					Write_tkn(bfr, ctx, hctx, src, xnde, i, sub);			// NOTE: never escape; <p>, <table>, <center> etc may have nested nodes
					break;
			}
		}
		if (at_bgn) {	// occurs when xnde is empty; EX: <b></b>
			bfr.Add_byte(Tag__bgn).Add(name);
			if (xnde.Atrs_bgn() > Xop_tblw_wkr.Atrs_ignore_check) Xnde_atrs(tag_id, hctx, src, xnde.Atrs_bgn(), xnde.Atrs_end(), xnde.Atrs_ary(), bfr);
			bfr.Add_byte(Tag__end);
		}
		bfr.Add(Tag__end_bgn).Add(name).Add_byte(Tag__end);						// NOTE: inline is never written as <b/>; will be written as <b></b>; SEE: NOTE_1
		if (ws_bfr.Len() > 0) bfr.Add_bfr_and_clear(ws_bfr);				// dump any leftover ws to bfr; handles "<b>c </b>" -> "<b>c</b> "
		ws_bfr.Mkr_rls();
	}		
	public void Xnde_atrs(int tag_id, Xoh_html_wtr_ctx hctx, byte[] src, int bgn, int end, Xop_xatr_itm[] ary, Bry_bfr bfr) {
		if (ary == null) return;	// NOTE: some nodes will have null xatrs b/c of whitelist; EX: <pre style="overflow:auto">a</pre>; style is not on whitelist so not xatr generated, but xatr_bgn will != -1
		int ary_len = ary.length;
		for (int i = 0; i < ary_len; i++) {
			Xop_xatr_itm atr = ary[i];
			if (atr.Invalid()) continue;
			if (!whitelist_mgr.Chk(tag_id, src, atr)) continue;
			Xnde_atr_write(bfr, app, hctx, src, atr);
		}
	}

	public static void Xnde_atr_write(Bry_bfr bfr, Xoa_app app, Xoh_html_wtr_ctx hctx, byte[] src, Xop_xatr_itm atr) {
		byte[] atr_key = atr.Key_bry();
		if (	hctx.Mode_is_display_title()
			&&	Xoh_display_ttl_wtr._.Is_style_restricted(bfr, hctx, src, atr, atr_key))
			return;
	
		bfr.Add_byte(Byte_ascii.Space);	// add space before every attribute
		if (atr_key != null) {
			bfr.Add(atr_key);
			bfr.Add_byte(Byte_ascii.Eq);
		}
		byte quote_byte = atr.Quote_byte(); if (quote_byte == Byte_ascii.Nil) quote_byte = Byte_ascii.Quote;
		bfr.Add_byte(quote_byte);
		if (atr.Key_tid() == Xop_xatr_itm.Key_tid_id) {	// ids should not have spaces; DATE:2013-04-01
			if (atr.Val_bry() == null)
				Xnde_atr_write_id(bfr, app, src, atr.Val_bgn(), atr.Val_end());
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
	private static void Xnde_atr_write_id(Bry_bfr bfr, Xoa_app app, byte[] bry, int bgn, int end) {
		app.Encoder_mgr().Id().Encode(bfr, bry, bgn, end);
	}
	private void Xnde_subs(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde) {
		int subs_len = xnde.Subs_len();
		for (int i = 0; i < subs_len; i++)
			Write_tkn(bfr, ctx, hctx, src, xnde, i, xnde.Subs_get(i));
	}
	private void Xnde_subs_escape(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_xnde_tkn xnde, boolean amp_enable, boolean nowiki) {
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
							bfr.Add(Html_entity_.Lt_bry).Add(tag_name);
							if (xnde.Atrs_bgn() > Xop_tblw_wkr.Atrs_ignore_check) Xnde_atrs(sub_xnde.Tag().Id(), hctx, src, sub_xnde.Atrs_bgn(), sub_xnde.Atrs_end(), xnde.Atrs_ary(), bfr);
							bfr.Add(Html_entity_.Gt_bry);
							break;
					}
					Xnde_subs_escape(ctx, hctx, bfr, src, sub_xnde, amp_enable, false);
					break;
				case Xop_tkn_itm_.Tid_txt:
					if (amp_enable)
						bfr.Add_mid(src, sub.Src_bgn(), sub.Src_end());
					else
						Xoh_html_wtr_escaper.Escape(app, bfr, src, sub.Src_bgn(), sub.Src_end(), true, nowiki);
					break;
				default:
					Write_tkn(bfr, ctx, hctx, src, xnde, i, sub);
					break;
			}
		}
	}
	public Bool_obj_ref Queue_add_ref() {return queue_add_ref;} Bool_obj_ref queue_add_ref = Bool_obj_ref.n_();
	public void Tblw(Xop_ctx ctx, Xoh_html_wtr_ctx hctx, Bry_bfr bfr, byte[] src, Xop_tblw_tkn tkn, byte[] bgn, byte[] end, boolean tblw_bgn) {
		if (hctx.Mode_is_alt())			// add \s for each \n
			bfr.Add_byte_space();
		else {
			bfr.Add_byte_if_not_last(Byte_ascii.NewLine);
			if (indent_level > 0) bfr.Add_byte_repeat(Byte_ascii.Space, indent_level * 2);
//			boolean para_mode = tblw_bgn && tbl_para && depth == 1;	// DELETE: old code; adding <p> to handle strange mozilla key down behavior on linux; DATE:2013-03-30
//			if (para_mode) {bfr.Add(Xoh_consts.P_bgn);}

			bfr.Add(bgn);
			int atrs_bgn = tkn.Atrs_bgn();
			if (atrs_bgn != -1) Xnde_atrs(tkn.Tblw_tid(), hctx, src, atrs_bgn, tkn.Atrs_end(), tkn.Atrs_ary(), bfr); //bfr.Add_byte(Byte_ascii.Space).Add_mid(src, atrs_bgn, tkn.Atrs_end());
			bfr.Add_byte(Tag__end);
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
			bfr.Add_byte_if_not_last(Byte_ascii.NewLine);
			if (indent_level > 0) bfr.Add_byte_repeat(Byte_ascii.Space, indent_level * 2);
			bfr.Add(end);
//			if (para_mode) {bfr.Add(Xoh_consts.P_end);}				// DELETE: old code; adding <p> to handle strange mozilla key down behavior on linux; DATE:2013-03-30
			bfr.Add_byte_if_not_last(Byte_ascii.NewLine);
//				bfr.Add_byte_nl();
		}
	}
	public static final byte[] Tag__end_quote = Bry_.new_ascii_("\">"), Tag__end_bgn = Bry_.new_ascii_("</")
	, Tag_hdr_bgn = Bry_.new_ascii_("<h"), Tag_hdr_end = Bry_.new_ascii_("</h"), Tag_hr = Bry_.new_ascii_("<hr/>"), Tag_br = Bry_.new_ascii_("<br/>")
	, Tag_list_grp_ul_bgn = Bry_.new_ascii_("<ul>"), Tag_list_grp_ul_end = Bry_.new_ascii_("</ul>")
	, Tag_list_grp_ol_bgn = Bry_.new_ascii_("<ol>"), Tag_list_grp_ol_end = Bry_.new_ascii_("</ol>")
	, Tag_list_itm_li_bgn = Bry_.new_ascii_("<li>"), Tag_list_itm_li_end = Bry_.new_ascii_("</li>")
	, Tag_list_itm_dt_bgn = Bry_.new_ascii_("<dt>"), Tag_list_itm_dt_end = Bry_.new_ascii_("</dt>")
	, Tag_list_itm_dd_bgn = Bry_.new_ascii_("<dd>"), Tag_list_itm_dd_end = Bry_.new_ascii_("</dd>")
	, Tag_list_grp_dl_bgn = Bry_.new_ascii_("<dl>"), Tag_list_grp_dl_end = Bry_.new_ascii_("</dl>")
	, File_divider = Bry_.new_ascii_("---------------------------------")
	, Tag_tblw_tb_bgn = Bry_.new_ascii_("<table>"), Tag_tblw_tb_bgn_atr = Bry_.new_ascii_("<table"), Tag_tblw_tb_end = Bry_.new_ascii_("</table>")
	, Tag_tblw_tr_bgn = Bry_.new_ascii_("<tr>"), Tag_tblw_tr_bgn_atr = Bry_.new_ascii_("<tr"), Tag_tblw_tr_end = Bry_.new_ascii_("</tr>")
	, Tag_tblw_td_bgn = Bry_.new_ascii_("<td>"), Tag_tblw_td_bgn_atr = Bry_.new_ascii_("<td"), Tag_tblw_td_end = Bry_.new_ascii_("</td>")
	, Tag_tblw_th_bgn = Bry_.new_ascii_("<th>"), Tag_tblw_th_bgn_atr = Bry_.new_ascii_("<th"), Tag_tblw_th_end = Bry_.new_ascii_("</th>")
	, Tag_tblw_tc_bgn = Bry_.new_ascii_("<caption>"), Tag_tblw_tc_bgn_atr = Bry_.new_ascii_("<caption"), Tag_tblw_tc_end = Bry_.new_ascii_("</caption>")
	, Ary_escape_bgn = Bry_.new_ascii_("&lt;"), Ary_escape_end = Bry_.new_ascii_("&gt;"), Ary_escape_end_bgn = Bry_.new_ascii_("&lt;/")
	, Tag_para_bgn = Bry_.new_ascii_("<p>"), Tag_para_end = Bry_.new_ascii_("</p>"), Tag_para_mid = Bry_.new_ascii_("</p>\n\n<p>")
	, Tag_image_end = Bry_.new_ascii_("</img>")
	, Tag_pre_bgn = Bry_.new_ascii_("<pre>"), Tag_pre_end = Bry_.new_ascii_("</pre>")
	;
	public static final byte Tag__bgn = Byte_ascii.Lt, Tag__end = Byte_ascii.Gt;
	public static final byte Dir_spr_http = Byte_ascii.Slash;
	private int indent_level = 0;
	public static final int Sub_idx_null = -1;	// nonsense placeholder
}
class Xoh_display_ttl_wtr {
	private static final byte[] 
	  Atr_key_style = Bry_.new_ascii_("style")
	, Msg_style_restricted = Bry_.new_ascii_(" style='/* attempt to bypass $wgRestrictDisplayTitle */'")
	;
	private Btrie_slim_mgr style_trie = Btrie_slim_mgr.ci_ascii_()
	.Add_str_byte__many(Byte_.int_(0), "display", "user-select", "visibility");  // if ( preg_match( '/(display|user-select|visibility)\s*:/i', $decoded['style'] ) ) {
	public boolean Is_style_restricted(Bry_bfr bfr, Xoh_html_wtr_ctx hctx, byte[] src, Xop_xatr_itm atr, byte[] atr_key) {
		if (atr_key != null 
			&& Bry_.Eq(atr_key, Atr_key_style)
			) {
			byte[] atr_val = atr.Val_as_bry(src); if (atr_val == null) return false; // bounds_chk
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
        public static final Xoh_display_ttl_wtr _ = new Xoh_display_ttl_wtr(); Xoh_display_ttl_wtr() {}
}
/*
NOTE_1:inline always written as <tag></tag>, not <tag/>
see WP:Permian�Triassic extinction event
this will cause firefox to swallow up rest of text
<div id="ScaleBar" style="width:1px; float:left; height:38em; padding:0; background-color:#242020" />
this will not
<div id="ScaleBar" style="width:1px; float:left; height:38em; padding:0; background-color:#242020"  ></div>
*/