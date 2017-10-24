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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
import gplx.langs.htmls.entitys.*;
import gplx.xowa.parsers.apos.*; import gplx.xowa.parsers.amps.*; import gplx.xowa.parsers.lnkes.*; import gplx.xowa.parsers.hdrs.*; import gplx.xowa.parsers.lists.*; import gplx.xowa.parsers.tblws.*;
import gplx.xowa.parsers.paras.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.tmpls.*; import gplx.xowa.parsers.miscs.*; import gplx.xowa.parsers.vnts.*; import gplx.xowa.xtns.cites.*; 
public class Xop_tkn_mkr {
	public Xop_root_tkn Root(byte[] raw)													{return new Xop_root_tkn().Root_src_(raw);}
	public Xop_txt_tkn Txt(int bgn, int end)												{return new Xop_txt_tkn(bgn, end);}
	public Xop_space_tkn Space(Xop_tkn_grp grp, int bgn, int end)							{Xop_space_tkn rv = new Xop_space_tkn(false, bgn, end); grp.Subs_src_pos_(grp.Subs_len(), bgn, end); return rv;}
	public Xop_space_tkn Space_mutable(int bgn, int end)									{return new Xop_space_tkn(false, bgn, end);}
	public Xop_apos_tkn Apos(int bgn, int end
		, int aposLen, int typ, int cmd, int lit_apos)										{return new Xop_apos_tkn(bgn, end, aposLen, typ, cmd, lit_apos);}
	public Xop_tkn_itm Amp_txt(int bgn, int end, Gfh_entity_itm itm)						{return new Xop_amp_tkn_ent(bgn, end, itm);}
	public Xop_tkn_itm Amp_num(int bgn, int end, int val_int, byte[] val_bry)				{return new Xop_amp_tkn_num(bgn, end, val_int, val_bry);}
	public Xop_tkn_itm Amp_num(int bgn, int end, int val_int)								{return new Xop_amp_tkn_num(bgn, end, val_int, gplx.core.intls.Utf16_.Encode_int_to_bry(val_int));}
	public Xop_nl_tkn NewLine(int bgn, int end, byte nl_typ, int nl_len)					{return new Xop_nl_tkn(bgn, end, nl_typ, nl_len);}
	public Xop_lnki_tkn Lnki(int bgn, int end)												{return (Xop_lnki_tkn)new Xop_lnki_tkn().Tkn_ini_pos(false, bgn, end);}
	public Xop_list_tkn List_bgn(int bgn, int end, byte listType, int symLen)				{return Xop_list_tkn.bgn_(bgn, end, listType, symLen);}
	public Xop_list_tkn List_end(int pos, byte listType)									{return Xop_list_tkn.end_(pos, listType);}
	public Xop_tkn_itm Pipe(int bgn, int end)												{return new Xop_pipe_tkn(bgn, end);}
	public Xop_tkn_itm Colon(int bgn, int end)												{return new Xop_colon_tkn(bgn, end);}
	public Xop_eq_tkn Eq(int bgn, int end)													{return new Xop_eq_tkn(bgn, end, end - bgn);}
	public Xop_eq_tkn Eq(int bgn, int end, int eq_len)										{return new Xop_eq_tkn(bgn, end, eq_len);}
	public Xot_invk_tkn Tmpl_invk(int bgn, int end)											{return new Xot_invk_tkn(bgn, end);}
	public Arg_nde_tkn ArgNde(int arg_idx, int bgn)											{return new Arg_nde_tkn(arg_idx, bgn);}
	public Arg_itm_tkn ArgItm(int bgn, int end)												{return new Arg_itm_tkn_base(bgn, end);}
	public Xop_xnde_tkn Xnde(int bgn, int end)												{return (Xop_xnde_tkn)Xop_xnde_tkn.new_().Tkn_ini_pos(false, bgn, end);}
	public Xop_hdr_tkn Hdr(int bgn, int end, int hdr_len)									{return new Xop_hdr_tkn(bgn, end, hdr_len);}
	public Xop_hr_tkn Hr(int bgn, int end, int hr_len)										{return new Xop_hr_tkn(bgn, end, hr_len);}
	public Xop_tab_tkn Tab(int bgn, int end)												{return new Xop_tab_tkn(bgn, end);}
	public Xop_curly_bgn_tkn Tmpl_curly_bgn(int bgn, int end)								{return new Xop_curly_bgn_tkn(bgn, end);}
	public Xop_tkn_itm Brack_bgn(int bgn, int end)											{return new Xop_brack_bgn_tkn(bgn, end);}
	public Xop_tkn_itm Brack_end(int bgn, int end)											{return new Xop_brack_end_tkn(bgn, end);}
	public Xop_lnke_tkn Lnke(int bgn, int end, byte[] protocol, byte proto_tid, byte lnke_typ, int lnk_bgn, int lnk_end) {
		return new Xop_lnke_tkn(bgn, end, protocol, proto_tid, lnke_typ, lnk_bgn, lnk_end);
	}
	public Xop_tblw_tb_tkn Tblw_tb(int bgn, int end, boolean tblw_xml, boolean auto_created)		{return new Xop_tblw_tb_tkn(bgn, end, tblw_xml, auto_created);}
	public Xop_tblw_tr_tkn Tblw_tr(int bgn, int end, boolean tblw_xml, boolean auto_created)		{return new Xop_tblw_tr_tkn(bgn, end, tblw_xml, auto_created);}
	public Xop_tblw_td_tkn Tblw_td(int bgn, int end, boolean tblw_xml)							{return new Xop_tblw_td_tkn(bgn, end, tblw_xml);}
	public Xop_tblw_th_tkn Tblw_th(int bgn, int end, boolean tblw_xml)							{return new Xop_tblw_th_tkn(bgn, end, tblw_xml);}
	public Xop_tblw_tc_tkn Tblw_tc(int bgn, int end, boolean tblw_xml)							{return new Xop_tblw_tc_tkn(bgn, end, tblw_xml);}
	public Xot_prm_tkn Tmpl_prm(int bgn, int end)											{return new Xot_prm_tkn(bgn, end);}
	public Xop_para_tkn Para(int pos)														{return new Xop_para_tkn(pos);}
	public Xop_pre_tkn Para_pre_bgn(int pos)												{return new Xop_pre_tkn(pos, pos, Xop_pre_tkn.Pre_tid_bgn, null);}
	public Xop_pre_tkn Para_pre_end(int pos, Xop_tkn_itm bgn)								{return new Xop_pre_tkn(pos, pos, Xop_pre_tkn.Pre_tid_end, bgn);}
	public Xop_ignore_tkn Ignore(int bgn, int end, byte ignore_type)						{return new Xop_ignore_tkn(bgn, end, ignore_type);}
	public Xop_bry_tkn Bry_raw(int bgn, int end, byte[] bry)								{return new Xop_bry_tkn(bgn, end, bry);}
	public Xop_bry_tkn Bry_mid(byte[] src, int bgn, int end)								{return new Xop_bry_tkn(bgn, end, Bry_.Mid(src, bgn, end));}
	public Xop_under_tkn Under(int bgn, int end, int v)										{return new Xop_under_tkn(bgn, end, v);}
	public gplx.xowa.xtns.xowa_cmds.Xop_xowa_cmd Xnde__xowa_cmd()							{return new gplx.xowa.xtns.xowa_cmds.Xop_xowa_cmd();}
	public gplx.xowa.xtns.poems.Poem_nde Xnde__poem()										{return new gplx.xowa.xtns.poems.Poem_nde();}
	public Ref_nde Xnde__ref()																{return new Ref_nde();}
	public References_nde Xnde__references()												{return new References_nde();}
	public gplx.xowa.xtns.math.Xomath_xnde Xnde__math()										{return new gplx.xowa.xtns.math.Xomath_xnde();}
	public gplx.xowa.xtns.gallery.Gallery_xnde Xnde__gallery()								{return new gplx.xowa.xtns.gallery.Gallery_xnde();}
	public gplx.xowa.xtns.imaps.Imap_xnde Xnde__imageMap()									{return new gplx.xowa.xtns.imaps.Imap_xnde();}
	public gplx.xowa.xtns.hieros.Hiero_xnde Xnde__hiero()									{return new gplx.xowa.xtns.hieros.Hiero_xnde();}
	public gplx.xowa.xtns.graphs.Graph_xnde Xnde__graph()									{return new gplx.xowa.xtns.graphs.Graph_xnde();}
	public gplx.xowa.xtns.kartographers.Mapframe_xnde Xnde__mapframe()						{return new gplx.xowa.xtns.kartographers.Mapframe_xnde();}
	public gplx.xowa.xtns.kartographers.Maplink_xnde Xnde__maplink()						{return new gplx.xowa.xtns.kartographers.Maplink_xnde();}
	public gplx.xowa.xtns.proofreadPage.Pp_pages_nde Xnde__pages()							{return new gplx.xowa.xtns.proofreadPage.Pp_pages_nde();}
	public gplx.xowa.xtns.proofreadPage.Pp_pagelist_nde Xnde__pagelist()					{return new gplx.xowa.xtns.proofreadPage.Pp_pagelist_nde();}
	public gplx.xowa.xtns.proofreadPage.Pp_pagequality_nde Xnde__pagequality()				{return new gplx.xowa.xtns.proofreadPage.Pp_pagequality_nde();}
	public gplx.xowa.xtns.lst.Lst_section_nde Xnde__section()								{return new gplx.xowa.xtns.lst.Lst_section_nde();}
	public gplx.xowa.xtns.categoryList.Xtn_categorylist_nde Xnde__categoryList()			{return new gplx.xowa.xtns.categoryList.Xtn_categorylist_nde();}
	public gplx.xowa.xtns.dynamicPageList.Dpl_xnde Xnde__dynamicPageList()					{return new gplx.xowa.xtns.dynamicPageList.Dpl_xnde();}
	public gplx.xowa.xtns.syntax_highlights.Synh_xtn_nde Xnde__syntaxHighlight()			{return new gplx.xowa.xtns.syntax_highlights.Synh_xtn_nde();}
	public gplx.xowa.xtns.templateData.Xtn_templateData_nde Xnde__templateData()			{return new gplx.xowa.xtns.templateData.Xtn_templateData_nde();}
	public gplx.xowa.xtns.rss.Rss_xnde Xnde__rss()											{return new gplx.xowa.xtns.rss.Rss_xnde();}
	public gplx.xowa.xtns.quiz.Quiz_xnde Xnde__quiz()										{return new gplx.xowa.xtns.quiz.Quiz_xnde();}
	public gplx.xowa.xtns.indicators.Indicator_xnde Xnde__indicator()						{return new gplx.xowa.xtns.indicators.Indicator_xnde();}
	public gplx.xowa.xtns.xowa_cmds.Xox_xowa_html_cmd Xnde__xowa_html()						{return new gplx.xowa.xtns.xowa_cmds.Xox_xowa_html_cmd();}
	public gplx.xowa.xtns.xowa_cmds.wiki_setups.Xop_wiki_setup_xnde Xnde__xowa_wiki_setup()	{return new gplx.xowa.xtns.xowa_cmds.wiki_setups.Xop_wiki_setup_xnde();}
	public gplx.xowa.xtns.listings.Listing_xnde Xnde__listing(int tag_id)					{return new gplx.xowa.xtns.listings.Listing_xnde(tag_id);}
	public gplx.xowa.xtns.scores.Score_xnde Xnde__score()									{return new gplx.xowa.xtns.scores.Score_xnde();}
	public gplx.xowa.xtns.inputBox.Xtn_inputbox_nde Xnde__inputbox()						{return new gplx.xowa.xtns.inputBox.Xtn_inputbox_nde();}
	public gplx.xowa.xtns.translates.Xop_translate_xnde Xnde__translate()					{return new gplx.xowa.xtns.translates.Xop_translate_xnde();}
	public gplx.xowa.xtns.translates.Xop_languages_xnde Xnde__languages()					{return new gplx.xowa.xtns.translates.Xop_languages_xnde();}
	public gplx.xowa.xtns.wikias.Random_selection_xnde Xnde__random_selection()				{return new gplx.xowa.xtns.wikias.Random_selection_xnde();}
	public gplx.xowa.xtns.wikias.Tabber_xnde Xnde__tabber()									{return new gplx.xowa.xtns.wikias.Tabber_xnde();}
	public gplx.xowa.xtns.wikias.Tabview_xnde Xnde__tabview()								{return new gplx.xowa.xtns.wikias.Tabview_xnde();}

	public gplx.xowa.xtns.translates.Xop_tvar_tkn Tvar(int tkn_bgn, int tkn_end, int key_bgn, int key_end, int txt_bgn, int txt_end, byte[] wikitext) {return new gplx.xowa.xtns.translates.Xop_tvar_tkn(tkn_bgn, tkn_end, key_bgn, key_end, txt_bgn, txt_end, wikitext);}
//		public void Clear() {
//			space_tkns_len = txt_tkns_len = 0;
//		}
//		public Xop_txt_tkn Txt(int bgn, int end) {
//			Xop_txt_tkn rv = null;
//			if (txt_tkns_len < txt_tkns_max) {
//				rv = txt_tkns[txt_tkns_len];
//				if (rv == null) {
//					rv = new Xop_txt_tkn(bgn, end);
//					txt_tkns[txt_tkns_len] = rv;
//				}
//				else {
//					rv.Reset();
//					rv.Src_rng_(bgn, end);
//				}
//				txt_tkns_len++;
//			}
//			else {
//				rv = new Xop_txt_tkn(bgn, end);
//				Txt_tkns_add(rv);
//			}
//			return rv;
////			return new Xop_txt_tkn(bgn, end);
//		}
//		public Xop_space_tkn Space(int bgn, int end)											{
//			Xop_space_tkn rv = null;
//			if (space_tkns_len < space_tkns_max) {
//				rv = space_tkns[space_tkns_len];
//				if (rv == null) {
//					rv = new Xop_space_tkn(bgn, end);
//					space_tkns[space_tkns_len] = rv;
//				}
//				else {
//					rv.Reset();
//					rv.Src_rng_(bgn, end);
//				}
//				space_tkns_len++;
//			}
//			else {
//				rv = new Xop_space_tkn(bgn, end);
//				Space_tkns_add(rv);
//			}
//			return rv;
////			return new Xop_space_tkn(bgn, end);
//		}
//		private void Txt_tkns_add(Xop_txt_tkn sub) {
//			int new_len = txt_tkns_len + 1;
//			if (new_len > txt_tkns_max) {
//				txt_tkns_max = new_len * 2;
//				txt_tkns = Resize(txt_tkns, txt_tkns_len, txt_tkns_max);
//			}
//			txt_tkns[txt_tkns_len] = sub;
//			txt_tkns_len = new_len;
//		}	private Xop_txt_tkn[] txt_tkns = new Xop_txt_tkn[0]; int txt_tkns_len, txt_tkns_max;
//		Xop_txt_tkn[] Resize(Xop_txt_tkn[] src, int cur_len, int new_len) {
//			Xop_txt_tkn[] rv = new Xop_txt_tkn[new_len];
//			for (int i = 0; i < cur_len; i++)
//				rv[i] = src[i];
//			return rv;
//		}
//		private void Space_tkns_add(Xop_space_tkn sub) {
//			int new_len = space_tkns_len + 1;
//			if (new_len > space_tkns_max) {
//				space_tkns_max = new_len * 2;
//				space_tkns = Resize(space_tkns, space_tkns_len, space_tkns_max);
//			}
//			space_tkns[space_tkns_len] = sub;
//			space_tkns_len = new_len;
//		}	private Xop_space_tkn[] space_tkns = new Xop_space_tkn[0]; int space_tkns_len, space_tkns_max;
//		Xop_space_tkn[] Resize(Xop_space_tkn[] src, int cur_len, int new_len) {
//			Xop_space_tkn[] rv = new Xop_space_tkn[new_len];
//			for (int i = 0; i < cur_len; i++)
//				rv[i] = src[i];
//			return rv;
//		}
}
