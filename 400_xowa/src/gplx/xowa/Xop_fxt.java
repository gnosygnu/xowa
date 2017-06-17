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
package gplx.xowa; import gplx.*;
import gplx.core.tests.*; import gplx.core.log_msgs.*;
import gplx.xowa.apps.cfgs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.amps.*; import gplx.xowa.parsers.apos.*; import gplx.xowa.parsers.hdrs.*; import gplx.xowa.parsers.lists.*; import gplx.xowa.parsers.paras.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.tmpls.*; import gplx.xowa.parsers.miscs.*; import gplx.xowa.parsers.tblws.*; import gplx.xowa.parsers.lnkes.*; import gplx.xowa.parsers.lnkis.*;
import gplx.xowa.files.exts.*; import gplx.xowa.files.repos.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.tdbs.hives.*;
import gplx.xowa.wikis.pages.*;
public class Xop_fxt {
	public Xop_fxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		wiki = Xoa_app_fxt.Make__wiki__edit(app);
		ctor(app, wiki);
	}
	public Xop_fxt(Xoae_app app, Xowe_wiki wiki) {
		this.ctor(app, wiki);
	}
	private void ctor(Xoae_app app, Xowe_wiki wiki) {
		this.app = app;
		this.wiki = wiki;
		app.Wiki_mgr().Add(wiki);
		app.File_mgr().Repo_mgr().Set("src:wiki", "mem/wiki/repo/src/", wiki.Domain_str()).Ext_rules_(Xof_rule_grp.Grp_app_default).Dir_depth_(2);
		app.File_mgr().Repo_mgr().Set("trg:wiki", "mem/wiki/repo/trg/", wiki.Domain_str()).Ext_rules_(Xof_rule_grp.Grp_app_default).Dir_depth_(2).Primary_(true);
		wiki.File_mgr().Repo_mgr().Add_repo(Bry_.new_a7("src:wiki"), Bry_.new_a7("trg:wiki"));
		ctx = wiki.Parser_mgr().Ctx();
		mock_wkr.Clear_commons();	// assume all files are in repo 0
		wiki.File_mgr().Repo_mgr().Page_finder_(mock_wkr);
		parser = wiki.Parser_mgr().Main();
		this.tkn_mkr = app.Parser_mgr().Tkn_mkr();
		ctx.Para().Enabled_n_();
		hdom_wtr = wiki.Html_mgr().Html_wtr();
		wiki.Html_mgr().Img_suppress_missing_src_(false);
		wiki.Xtn_mgr().Init_by_wiki(wiki);
		Page_ttl_(Ttl_str);
		Xot_invk_tkn.Cache_enabled = false;// always disable cache for tests; can cause strange behavior when running entire suite and lnki_temp test turns on;
	}
	private Xofw_wiki_wkr_mock mock_wkr = new Xofw_wiki_wkr_mock();
	public Xoae_app App() {return app;} private Xoae_app app;
	public Xowe_wiki Wiki() {return wiki;} private Xowe_wiki wiki;
	public Xop_ctx Ctx() {return ctx;} private Xop_ctx ctx;
	public Xop_parser Parser() {return parser;} private Xop_parser parser; 
	public Xoae_page Page() {return ctx.Page();}
	public void Lang_by_id_(int id) {ctx.Page().Lang_(wiki.Appe().Lang_mgr().Get_by_or_new(Xol_lang_stub_.Get_by_id(id).Key()));}
	public Xoh_html_wtr_cfg Wtr_cfg() {return hdom_wtr.Cfg();} private Xoh_html_wtr hdom_wtr;
	public Xop_fxt Reset() {
		ctx.Clear_all();
		ctx.App().Free_mem(false);
		ctx.Page().Clear_all();
		wiki.File_mgr().Clear_for_tests();
		wiki.Db_mgr().Load_mgr().Clear();
		app.Wiki_mgr().Clear();
		Io_mgr.Instance.InitEngine_mem();	// clear created pages
		wiki.Cfg_parser().Display_title_restrict_(false);	// default to false, as a small number of tests assume restrict = false;
		return this;
	}
	public Xop_fxt Reset_for_msgs() {
		Io_mgr.Instance.InitEngine_mem();
		wiki.Lang().Msg_mgr().Clear();	// need to clear out lang
		wiki.Msg_mgr().Clear();			// need to clear out wiki.Msgs
		this.Reset();
		return this;
	}
	public Xoa_ttl Page_ttl_(String txt) {
		Xoa_ttl rv = Xoa_ttl.Parse(wiki, Bry_.new_u8(txt));
		ctx.Page().Ttl_(rv);
		return rv;
	}

	public Xop_tkn_chkr_base tkn_bry_(int bgn, int end)				{return new Xop_tkn_chkr_base().TypeId_dynamic(Xop_tkn_itm_.Tid_bry).Src_rng_(bgn, end);}
	public Xop_tkn_chkr_base tkn_txt_() 							{return tkn_txt_(String_.Pos_neg1, String_.Pos_neg1);}
	public Xop_tkn_chkr_base tkn_txt_(int bgn, int end)				{return new Xop_tkn_chkr_base().TypeId_dynamic(Xop_tkn_itm_.Tid_txt).Src_rng_(bgn, end);}
	public Xop_tkn_chkr_base tkn_space_() 							{return tkn_space_(String_.Pos_neg1, String_.Pos_neg1);}
	public Xop_tkn_chkr_base tkn_space_(int bgn, int end)			{return new Xop_tkn_chkr_base().TypeId_dynamic(Xop_tkn_itm_.Tid_space).Src_rng_(bgn, end);}
	public Xop_tkn_chkr_base tkn_eq_(int bgn) 						{return tkn_eq_(bgn, bgn + 1);}
	public Xop_tkn_chkr_base tkn_eq_(int bgn, int end) 				{return new Xop_tkn_chkr_base().TypeId_dynamic(Xop_tkn_itm_.Tid_eq).Src_rng_(bgn, end);}
	public Xop_tkn_chkr_base tkn_colon_(int bgn) 					{return new Xop_tkn_chkr_base().TypeId_dynamic(Xop_tkn_itm_.Tid_colon).Src_rng_(bgn, bgn + 1);}
	public Xop_tkn_chkr_base tkn_pipe_(int bgn)						{return new Xop_tkn_chkr_base().TypeId_dynamic(Xop_tkn_itm_.Tid_pipe).Src_rng_(bgn, bgn + 1);}
	public Xop_tkn_chkr_base tkn_tab_(int bgn)						{return new Xop_tkn_chkr_base().TypeId_dynamic(Xop_tkn_itm_.Tid_tab).Src_rng_(bgn, bgn + 1);}
	public Xop_apos_tkn_chkr tkn_apos_(int cmd) 					{return new Xop_apos_tkn_chkr().Apos_cmd_(cmd);}
	public Xop_ignore_tkn_chkr tkn_comment_(int bgn, int end)		{return tkn_ignore_(bgn, end, Xop_ignore_tkn.Ignore_tid_comment);}
	public Xop_ignore_tkn_chkr tkn_ignore_(int bgn, int end, byte t){return (Xop_ignore_tkn_chkr)new Xop_ignore_tkn_chkr().Ignore_tid_(t).Src_rng_(bgn, end);}
	public Xop_tkn_chkr_hr   tkn_hr_(int bgn, int end)				{return new Xop_tkn_chkr_hr(bgn, end).Hr_len_(Xop_hr_lxr.Hr_len);}
	public Xop_tblw_tb_tkn_chkr tkn_tblw_tb_(int bgn, int end) 		{return (Xop_tblw_tb_tkn_chkr)new Xop_tblw_tb_tkn_chkr().Src_rng_(bgn, end);}
	public Xop_tblw_tc_tkn_chkr tkn_tblw_tc_(int bgn, int end) 		{return (Xop_tblw_tc_tkn_chkr)new Xop_tblw_tc_tkn_chkr().Src_rng_(bgn, end);}
	public Xop_tblw_td_tkn_chkr tkn_tblw_td_(int bgn, int end) 		{return (Xop_tblw_td_tkn_chkr)new Xop_tblw_td_tkn_chkr().Src_rng_(bgn, end);}
	public Xop_tblw_th_tkn_chkr tkn_tblw_th_(int bgn, int end) 		{return (Xop_tblw_th_tkn_chkr)new Xop_tblw_th_tkn_chkr().Src_rng_(bgn, end);}
	public Xop_tblw_tr_tkn_chkr tkn_tblw_tr_(int bgn, int end) 		{return (Xop_tblw_tr_tkn_chkr)new Xop_tblw_tr_tkn_chkr().Src_rng_(bgn, end);}
	public Xop_xnde_tkn_chkr tkn_xnde_br_(int pos)					{return tkn_xnde_(pos, pos).Xnde_tagId_(Xop_xnde_tag_.Tid__br);}
	public Xop_xnde_tkn_chkr tkn_xnde_()							{return tkn_xnde_(String_.Pos_neg1, String_.Pos_neg1);}
	public Xop_xnde_tkn_chkr tkn_xnde_(int bgn, int end)			{return (Xop_xnde_tkn_chkr)new Xop_xnde_tkn_chkr().Src_rng_(bgn, end);}
	public Xop_tkn_chkr_base tkn_curly_bgn_(int bgn)				{return new Xop_tkn_chkr_base().TypeId_dynamic(Xop_tkn_itm_.Tid_tmpl_curly_bgn).Src_rng_(bgn, bgn + 2);}
	public Xop_tkn_chkr_base tkn_para_blank_(int pos)				{return tkn_para_(pos, Xop_para_tkn.Tid_none, Xop_para_tkn.Tid_none);}
	public Xop_tkn_chkr_base tkn_para_bgn_pre_(int pos)				{return tkn_para_(pos, Xop_para_tkn.Tid_none, Xop_para_tkn.Tid_pre);}
	public Xop_tkn_chkr_base tkn_para_bgn_para_(int pos)			{return tkn_para_(pos, Xop_para_tkn.Tid_none, Xop_para_tkn.Tid_para);}
	public Xop_tkn_chkr_base tkn_para_mid_para_(int pos)			{return tkn_para_(pos, Xop_para_tkn.Tid_para, Xop_para_tkn.Tid_para);}
	public Xop_tkn_chkr_base tkn_para_end_para_(int pos)			{return tkn_para_(pos, Xop_para_tkn.Tid_para, Xop_para_tkn.Tid_none);}
	public Xop_tkn_chkr_base tkn_para_end_pre_bgn_para_(int pos)	{return tkn_para_(pos, Xop_para_tkn.Tid_pre , Xop_para_tkn.Tid_para);}
	public Xop_tkn_chkr_base tkn_para_end_para_bgn_pre_(int pos)	{return tkn_para_(pos, Xop_para_tkn.Tid_para, Xop_para_tkn.Tid_pre);}
	public Xop_tkn_chkr_base tkn_para_end_pre_(int pos)				{return tkn_para_(pos, Xop_para_tkn.Tid_pre , Xop_para_tkn.Tid_none);}
	public Xop_tkn_chkr_base tkn_para_(int pos, byte end, byte bgn) {return new Xop_para_tkn_chkr().Para_end_(end).Para_bgn_(bgn).Src_rng_(pos, pos);}
	public Xop_tkn_chkr_base tkn_nl_char_(int bgn, int end)			{return tkn_nl_(bgn, end, Xop_nl_tkn.Tid_char);}
	public Xop_tkn_chkr_base tkn_nl_char_len1_(int bgn)				{return tkn_nl_(bgn, bgn + 1, Xop_nl_tkn.Tid_char);}
	public Xop_tkn_chkr_base tkn_nl_char_len0_(int pos)				{return tkn_nl_(pos, pos, Xop_nl_tkn.Tid_char);}
	public Xop_tkn_chkr_base tkn_nl_(int bgn, int end, byte tid)	{return new Xop_nl_tkn_chkr().Nl_tid_(tid).Src_rng_(bgn, end);}
	public Xop_list_tkn_chkr tkn_list_bgn_(int bgn, int end, byte listType) {return (Xop_list_tkn_chkr)new Xop_list_tkn_chkr().List_itmTyp_(listType).Src_rng_(bgn, end);}
	public Xop_list_tkn_chkr tkn_list_end_(int pos)					{return (Xop_list_tkn_chkr)new Xop_list_tkn_chkr().Src_rng_(pos, pos);}
	public Xop_tkn_chkr_lnke tkn_lnke_(int bgn, int end)			{return new Xop_tkn_chkr_lnke(bgn, end);}
	public Xop_lnki_tkn_chkr tkn_lnki_()							{return tkn_lnki_(-1, -1);}
	public Xop_lnki_tkn_chkr tkn_lnki_(int bgn, int end)			{return (Xop_lnki_tkn_chkr)new Xop_lnki_tkn_chkr().Src_rng_(bgn, end);}
	public Xop_arg_itm_tkn_chkr		tkn_arg_itm_(Xop_tkn_chkr_base... subs) {return (Xop_arg_itm_tkn_chkr)new Xop_arg_itm_tkn_chkr().Subs_(subs);}
	public Xop_arg_nde_tkn_chkr		tkn_arg_nde_()						{return tkn_arg_nde_(String_.Pos_neg1, String_.Pos_neg1);}
	public Xop_arg_nde_tkn_chkr		tkn_arg_nde_(int bgn, int end)		{return (Xop_arg_nde_tkn_chkr)new Xop_arg_nde_tkn_chkr().Src_rng_(bgn, end);}
	public Xop_arg_nde_tkn_chkr		tkn_arg_val_(Xop_tkn_chkr_base... subs) {
		Xop_arg_nde_tkn_chkr rv = new Xop_arg_nde_tkn_chkr();
		Xop_arg_itm_tkn_chkr val = new Xop_arg_itm_tkn_chkr();
		val.Subs_(subs);
		rv.Val_tkn_(val);
		return rv;
	}
	public Xop_arg_nde_tkn_chkr tkn_arg_val_txt_(int bgn, int end) {
		Xop_arg_nde_tkn_chkr rv = new Xop_arg_nde_tkn_chkr();
		Xop_arg_itm_tkn_chkr itm = new Xop_arg_itm_tkn_chkr();
		rv.Val_tkn_(itm);
		itm.Src_rng_(bgn, end).Subs_(tkn_txt_(bgn, end));
		return rv;
	}
	Xop_arg_nde_tkn_chkr tkn_arg_key_txt_(int bgn, int end) {
		Xop_arg_nde_tkn_chkr rv = new Xop_arg_nde_tkn_chkr();
		Xop_arg_itm_tkn_chkr itm = new Xop_arg_itm_tkn_chkr();
		rv.Key_tkn_(itm);
		itm.Src_rng_(bgn, end).Subs_(tkn_txt_(bgn, end));
		return rv;
	}
	public Xot_invk_tkn_chkr tkn_tmpl_invk_(int bgn, int end) {return (Xot_invk_tkn_chkr)new Xot_invk_tkn_chkr().Src_rng_(bgn, end);}
	public Xot_invk_tkn_chkr tkn_tmpl_invk_w_name(int bgn, int end, int name_bgn, int name_end) {
		Xot_invk_tkn_chkr rv = new Xot_invk_tkn_chkr();
		rv.Src_rng_(bgn, end);
		rv.Name_tkn_(tkn_arg_key_txt_(name_bgn, name_end));
		return rv;
	}
	public Xot_prm_chkr tkn_tmpl_prm_find_(Xop_tkn_chkr_base find) {
		Xot_prm_chkr rv = new Xot_prm_chkr();
		rv.Find_tkn_(tkn_arg_itm_(find));
		return rv;
	}
	public Xop_fxt	Init_para_y_() {ctx.Para().Enabled_y_(); return this;}
	public Xop_fxt	Init_para_n_() {ctx.Para().Enabled_n_(); return this;}
	public Xop_fxt	Init_log_(Gfo_msg_itm... itms) {for (Gfo_msg_itm itm : itms) log_itms.Add(itm); return this;} List_adp log_itms = List_adp_.New();
	public void		Init_defn_add(String name, String text) {Init_defn_add(name, text, Xow_ns_case_.Tid__all);}
	public void		Init_defn_add(String name, String text, byte case_match) {
		Xot_defn_tmpl itm = run_Parse_tmpl(Bry_.new_a7(name), Bry_.new_u8(text));
		wiki.Cache_mgr().Defn_cache().Add(itm, case_match);
	}
	public void		Init_defn_clear() {wiki.Cache_mgr().Defn_cache().Free_mem_all();}
	public Xop_fxt	Init_id_create(int id, int fil_idx, int row_idx, boolean type_redirect, int itm_len, int ns_id, String ttl) {Xow_hive_mgr_fxt.Create_id(app, wiki.Hive_mgr(), id, fil_idx, row_idx, type_redirect, itm_len, ns_id, ttl); return this;}
	public Xop_fxt	Init_page_create(String ttl) {return Init_page_create(wiki, ttl, "");}
	public Xop_fxt	Init_page_create(String ttl, String txt) {return Init_page_create(wiki, ttl, txt);}
	public Xop_fxt	Init_page_create(Xowe_wiki wiki, String ttl, String txt) {Init_page_create_static(wiki, ttl, txt);return this;}
	public static void Init_page_create_static(Xowe_wiki wiki, String ttl_str, String text_str) {
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, Bry_.new_u8(ttl_str));
		byte[] text = Bry_.new_u8(text_str);
		wiki.Db_mgr().Save_mgr().Data_create(ttl, text);
	}
	public static void Init_msg(Xowe_wiki wiki, String key, String val) {
		wiki.Lang().Msg_mgr().Itm_by_key_or_new(key, val);
	}
	public Xop_fxt	Init_page_update(String ttl, String txt) {return Init_page_update(wiki, ttl, txt);}
	public Xop_fxt	Init_page_update(Xowe_wiki wiki, String ttl, String txt) {
		Xoa_ttl page_ttl = Xoa_ttl.Parse(wiki, Bry_.new_u8(ttl));
		byte[] page_raw = Bry_.new_u8(txt);
		Xoae_page page = wiki.Data_mgr().Load_page_by_ttl(page_ttl);
		wiki.Db_mgr().Save_mgr().Data_update(page, page_raw);
		return this;
	}
	public Xop_fxt	Init_xwiki_clear() {
		wiki.Xwiki_mgr().Clear();
		app.Usere().Wiki().Xwiki_mgr().Clear();
		return this;
	}
	public Xop_fxt	Init_xwiki_add_wiki_and_user_(String alias, String domain) {
		wiki.Xwiki_mgr().Add_by_atrs(alias, domain);
		app.Usere().Wiki().Xwiki_mgr().Add_by_atrs(domain, domain);
		return this;
	}
	public Xop_fxt	Init_xwiki_add_user_(String domain) {return Init_xwiki_add_user_(domain, domain);}
	public Xop_fxt	Init_xwiki_add_user_(String alias, String domain) {
		app.Usere().Wiki().Xwiki_mgr().Add_by_atrs(alias, domain);
		return this;
	}
	public void Test_parse_template(String tmpl_raw, String expd) {Test_parse_tmpl_str_test(tmpl_raw, "{{test}}", expd);}
	public void Test_parse_tmpl_str_test(String tmpl_raw, String page_raw, String expd) {
		Init_defn_add("test", tmpl_raw);
		Test_parse_tmpl_str(page_raw, expd);
	}
	public void Test_parse_tmpl_str(String raw, String expd) {
		byte[] actl = Test_parse_tmpl_str_rv(raw);
		Tfds.Eq_str_lines(expd, String_.new_u8(actl));
		tst_Log_check();
	}
	public byte[] Test_parse_tmpl_str_rv(String raw) {
		byte[] raw_bry = Bry_.new_u8(raw);
		Xop_root_tkn root = tkn_mkr.Root(raw_bry);
		ctx.Page().Root_(root);
		ctx.Page().Db().Text().Text_bry_(raw_bry);
		return parser.Expand_tmpl(root, ctx, tkn_mkr, raw_bry);
	}
	public Xot_defn_tmpl run_Parse_tmpl(byte[] name, byte[] raw) {return parser.Parse_text_to_defn_obj(ctx, ctx.Tkn_mkr(), wiki.Ns_mgr().Ns_template(), name, raw);}
	public void Test_parse_tmpl(String raw, Tst_chkr... expd) {
		byte[] raw_bry = Bry_.new_u8(raw);
		Xot_defn_tmpl itm = run_Parse_tmpl(Bry_.Empty, raw_bry);
		Parse_chk(raw_bry, itm.Root(), expd);
	}
	public void Test_parse_page_tmpl_str(String raw, String expd) {
		byte[] raw_bry = Bry_.new_u8(raw);
		Xop_root_tkn root = tkn_mkr.Root(raw_bry);
		byte[] actl = parser.Expand_tmpl(root, ctx, tkn_mkr, raw_bry);
		Tfds.Eq(expd, String_.new_u8(actl));
		tst_Log_check();
	}
	public Xop_root_tkn Test_parse_page_tmpl_tkn(String raw) {
		byte[] raw_bry = Bry_.new_u8(raw);
		Xop_root_tkn root = tkn_mkr.Root(raw_bry);
		parser.Expand_tmpl(root, ctx, tkn_mkr, raw_bry);
		return root;
	}
	public void Test_parse_page_tmpl(String raw, Tst_chkr... expd_ary) {
		byte[] raw_bry = Bry_.new_u8(raw);
		Xop_root_tkn root = tkn_mkr.Root(raw_bry);
		parser.Expand_tmpl(root, ctx, tkn_mkr, raw_bry);
		Parse_chk(raw_bry, root, expd_ary);
	}
	public void Test_parse_page_wiki(String raw, Tst_chkr... expd_ary) {
		byte[] raw_bry = Bry_.new_u8(raw);
		Xop_root_tkn root = Test_parse_page_wiki_root(raw_bry);
		Parse_chk(raw_bry, root, expd_ary);
	}
	public Xop_root_tkn Test_parse_page_wiki_root(String raw) {return Test_parse_page_wiki_root(Bry_.new_u8(raw));}
	Xop_root_tkn Test_parse_page_wiki_root(byte[] raw_bry) {
		Xop_root_tkn root = tkn_mkr.Root(raw_bry);
		parser.Parse_wtxt_to_wdom(root, ctx, tkn_mkr, raw_bry, Xop_parser_.Doc_bgn_bos);
		return root;
	}
	public void Test_parse_page_all(String raw, Tst_chkr... expd_ary) {
		byte[] raw_bry = Bry_.new_u8(raw);
		Xop_root_tkn root = Exec_parse_page_all_as_root(Bry_.new_u8(raw));
		Parse_chk(raw_bry, root, expd_ary);
	}
	public void Data_create(String ttl_str, String text_str) {Init_page_create(wiki, ttl_str, text_str);}
	public void Test_parse_page_all_str__esc(String raw, String expd) {Test_parse_page_all_str(raw, Xoh_consts.Escape_apos(expd));}
	public void Test_parse_page_all_str(String raw, String expd) {
		String actl = Exec_parse_page_all_as_str(raw);
		Tfds.Eq_ary_str(String_.SplitLines_nl(expd), String_.SplitLines_nl(actl), raw);
	}
	public void Test_parse_page_all_str_and_chk(String raw, String expd, Gfo_msg_itm... ary) {
		this.Init_log_(ary);
		Test_parse_page_all_str(raw, expd);
		this.tst_Log_check();
	}
	public Xop_root_tkn Exec_parse_page_all_as_root(byte[] raw_bry) {
		Xop_root_tkn root = tkn_mkr.Root(raw_bry);
		parser.Parse_page_all_clear(root, ctx, tkn_mkr, raw_bry);
		return root;
	}
	public String Exec_parse_page_all_as_str(String raw) {
		Xop_root_tkn root = Exec_parse_page_all_as_root(Bry_.new_u8(raw));
		Bry_bfr actl_bfr = Bry_bfr_.New();
		hdom_wtr.Write_doc(actl_bfr, ctx, hctx, root.Root_src(), root);
		return actl_bfr.To_str_and_clear();
	}
	public Xoh_wtr_ctx Hctx() {return hctx;} private Xoh_wtr_ctx hctx = Xoh_wtr_ctx.Basic;
	public void Hctx_(Xoh_wtr_ctx v) {hctx = v;}
	public String Exec_parse_page_wiki_as_str(String raw) {
		byte[] raw_bry = Bry_.new_u8(raw);
		Xop_root_tkn root = tkn_mkr.Root(raw_bry);
		parser.Parse_wtxt_to_wdom(root, ctx, tkn_mkr, raw_bry, Xop_parser_.Doc_bgn_bos);
		Bry_bfr actl_bfr = Bry_bfr_.New();
		hdom_wtr.Write_doc(actl_bfr, ctx, hctx, raw_bry, root);
		return actl_bfr.To_str_and_clear();
	}
	private void Parse_chk(byte[] raw_bry, Xop_root_tkn root, Tst_chkr[] expd_ary) {
		int subs_len = root.Subs_len();
		Object[] actl_ary = new Object[subs_len];
		for (int i = 0; i < subs_len; i++)
			actl_ary[i] = root.Subs_get(i);
		tst_mgr.Vars().Clear().Add("raw_bry", raw_bry);
		tst_mgr.Tst_ary("tkns:", expd_ary, actl_ary);
		tst_Log_check();
	}
	public Xop_fxt Test_parse_page_wiki_str__esc(String raw, String expd) {return Test_parse_page_wiki_str(raw, Xoh_consts.Escape_apos(expd));}
	public Xop_fxt Test_parse_page_wiki_str(String raw, String expd) {
		String actl = Exec_parse_page_wiki_as_str(raw);
		Tfds.Eq_str_lines(expd, actl, raw);
		return this;
	}
	public void Log_clear() {ctx.App().Msg_log().Clear();}
	public String[] Log_xtoAry() {
		Gfo_msg_log msg_log = app.Msg_log();
		int len = msg_log.Ary_len();
		List_adp actl_list = List_adp_.New();
		for (int i = 0; i < len; i++) {
			Gfo_msg_data eny = msg_log.Ary_get(i);
			if (eny.Item().Cmd() > Gfo_msg_itm_.Cmd_note) {
				actl_list.Add(String_.new_u8(eny.Item().Path_bry()));
			}
		}
		String[] actl = actl_list.To_str_ary();
		msg_log.Clear();
		return actl;
	}
	public Xop_fxt tst_Log_check() {
		int len = log_itms.Count();
		String[] expd = new String[len];
		for (int i = 0; i < len; i++) {
			Gfo_msg_itm itm = (Gfo_msg_itm)log_itms.Get_at(i);
			expd[i] = itm.Path_str();
		}
		log_itms.Clear();
		String[] actl = Log_xtoAry();
		Tfds.Eq_ary_str(expd, actl);
		return this;
	}
	public void tst_Warn(String... expd) {
		Gfo_usr_dlg usr_dlg = app.Usr_dlg();
		Gfo_usr_dlg__gui_test ui_wkr = (Gfo_usr_dlg__gui_test)usr_dlg.Gui_wkr();
		String[] actl = ui_wkr.Warns().To_str_ary();
		Tfds.Eq_ary_str(expd, actl);
	}
	public void Test_parse_page(String ttl, String expd) {
		byte[] actl = Load_page(wiki, ttl);
		Tfds.Eq(expd, String_.new_u8(actl));
	}
	public static byte[] Load_page(Xowe_wiki wiki, String ttl_str) {
		byte[] ttl_bry = Bry_.new_u8(ttl_str);
		Xoa_url page_url = Xoa_url.New(wiki.Domain_bry(), ttl_bry);
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry);
		return wiki.Data_mgr().Load_page_and_parse(page_url, ttl).Db().Text().Text_bry();
	}
	public static void Reg_xwiki_alias(Xowe_wiki wiki, String alias, String domain) {
		byte[] domain_bry = Bry_.new_a7(domain);
		wiki.Xwiki_mgr().Add_by_atrs(Bry_.new_a7(alias), domain_bry, Bry_.Add(domain_bry, Bry_.new_a7("/wiki/~{0}")));
		wiki.Appe().Usere().Wiki().Xwiki_mgr().Add_by_atrs(domain_bry, domain_bry);
	}
	public static String html_img_none(String trg, String alt, String src, String ttl) {
		return String_.Format(String_.Concat_lines_nl_skip_last("<a href=\"/wiki/{0}\" class=\"image\" xowa_title=\"{3}\"><img id=\"xoimg_0\" alt=\"{1}\" src=\"{2}\" width=\"9\" height=\"8\" /></a>"), trg, alt, src, ttl);
	}
	private String Exec_html_full(String raw)										{return this.Exec_parse_page_all_as_str(raw);}
	private String Exec_html_wiki(String raw)										{return this.Exec_parse_page_wiki_as_str(raw);}
	public void Test_html_wiki_str(String raw, String expd)							{Test_str_full(raw, expd, Exec_html_wiki(raw));}
	public void Test_html_full_str(String raw, String expd)							{Test_str_full(raw, expd, Exec_html_full(raw));}
	public void Test_html_wiki_frag(String raw, String... expd_frags)			{Test_str_part_y(Exec_html_wiki(raw), expd_frags);}
	public void Test_html_full_frag(String raw, String... expd_frags)			{Test_str_part_y(Exec_html_full(raw), expd_frags);}
	public void Test_html_full_frag_n(String raw, String... expd_frags)		{Test_str_part_n(Exec_html_full(raw), expd_frags);}
	public void Test__parse__tmpl_to_html(String raw, String expd) {Test_str_full(raw, gplx.langs.htmls.Gfh_utl.Replace_apos(expd), Exec_html_full(raw));}
	public void Test__parse__wtxt_to_html(String raw, String expd) {
		String actl = Exec_html_wiki(raw);
		Tfds.Eq_str_lines(gplx.langs.htmls.Gfh_utl.Replace_apos(expd), actl, raw);
	}

	public void Test_str_full(String raw, String expd, String actl) {
		Tfds.Eq_str_lines(expd, actl, raw);
	}
	public void Test_str_part_y(String actl, String... expd_parts) {
		int expd_parts_len = expd_parts.length;
		for (int i = 0; i < expd_parts_len; i++) {
			String expd_part = expd_parts[i];
			boolean pass = String_.Has(actl, expd_part);
			if (!pass)
				Tfds.Eq_true(false, expd_part + "\n" + actl);
		}
	}
	private void Test_str_part_n(String actl, String... expd_parts) {
		int expd_parts_len = expd_parts.length;
		for (int i = 0; i < expd_parts_len; i++) {
			String expd_part = expd_parts[i];
			boolean has = String_.Has(actl, expd_part);
			if (has)
				Tfds.Eq_true(false, expd_part + "\n" + actl);
		}
	}
	public void Test_html_modules_js(String expd) {
		Bry_bfr bfr = wiki.Utl__bfr_mkr().Get_k004();
		this.Page().Html_data().Head_mgr().Init(app, wiki, this.Page());
		this.Page().Html_data().Head_mgr().Bfr_arg__add(bfr);
		bfr.Mkr_rls();
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}

	private Tst_mgr tst_mgr = new Tst_mgr(); private Xop_tkn_mkr tkn_mkr;
	public static final String Ttl_str = "Test page";
	public Xop_fxt Init_lang_numbers_separators_en()								{return Init_lang_numbers_separators(",", ".");}
	public Xop_fxt Init_lang_numbers_separators(String grp_spr, String dec_spr)		{return Init_lang_numbers_separators(wiki.Lang(), grp_spr, dec_spr);}
	public Xop_fxt Init_lang_numbers_separators(Xol_lang_itm lang, String grp_spr, String dec_spr) {
		gplx.xowa.langs.numbers.Xol_transform_mgr separator_mgr = lang.Num_mgr().Separators_mgr();
		separator_mgr.Clear();
		separator_mgr.Set(gplx.xowa.langs.numbers.Xol_num_mgr.Separators_key__grp, Bry_.new_u8(grp_spr));
		separator_mgr.Set(gplx.xowa.langs.numbers.Xol_num_mgr.Separators_key__dec, Bry_.new_u8(dec_spr));
		return this;
	}
	public void Init_lang_kwds(int kwd_id, boolean case_match, String... kwds) {Init_lang_kwds(wiki.Lang(), kwd_id, case_match, kwds);}
	public void Init_lang_kwds(Xol_lang_itm lang, int kwd_id, boolean case_match, String... kwds) {
		Xol_kwd_mgr kwd_mgr = lang.Kwd_mgr();
		Xol_kwd_grp kwd_grp = kwd_mgr.Get_or_new(kwd_id);
		kwd_grp.Srl_load(case_match, Bry_.Ary(kwds));
	}
	public void Init_lang_vnts(String... vnts) {
		wiki.Lang().Vnt_mgr().Enabled_(true);
		gplx.xowa.langs.vnts.Xol_vnt_regy vnt_regy = wiki.Lang().Vnt_mgr().Regy();
		for (int i = 0; i < vnts.length; i++) {
			byte[] vnt = Bry_.new_u8(vnts[i]);
			vnt_regy.Add(vnt, vnt);
			if (i == 0) {
				wiki.Lang().Vnt_mgr().Cur_itm_(vnt);
			}
		}
		wiki.Lang().Vnt_mgr().Init_end();
	}
	public void Init_xtn_pages() {
		Io_mgr.Instance.InitEngine_mem();
		wiki.Xtn_mgr().Xtn_proofread().Enabled_y_();
		wiki.Db_mgr().Load_mgr().Clear(); // must clear; otherwise fails b/c files get deleted, but wiki.data_mgr caches the Xowd_regy_mgr (the .reg file) in memory;
		wiki.Ns_mgr().Add_new(Xowc_xtn_pages.Ns_page_id_default, "Page").Add_new(Xowc_xtn_pages.Ns_index_id_default, "Index").Init();
	}
	public void Clear_ref_mgr() {this.Page().Ref_mgr().Grps_clear();}			// clear to reset count
	public static Xop_fxt new_nonwmf() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		return new Xop_fxt(app, Xoa_app_fxt.Make__wiki__edit__nonwmf(app, "nethackwiki"));
	}
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New_w_size(255);
	public String Exec__parse_to_hdump(String src_str) {
		byte[] src_bry = Bry_.new_u8(src_str);
		Xop_root_tkn root = Exec_parse_page_all_as_root(src_bry);
		Xoh_wtr_ctx hctx = Xoh_wtr_ctx.Hdump;
		Xoh_html_wtr html_wtr = wiki.Html_mgr().Html_wtr();
		html_wtr.Cfg().Toc__show_(Bool_.Y);	// needed for hdr to show <span class='mw-headline' id='A'>	
		ctx.Page().Html_data().Redlink_list().Clear();
		html_wtr.Write_doc(tmp_bfr, ctx, hctx, src_bry, root);
            // Tfds.Dbg(tmp_bfr.To_str());
		return tmp_bfr.To_str_and_clear();
	}
	public void Test__parse_to_html_mgr(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		Xop_root_tkn root = Exec_parse_page_all_as_root(src_bry);
		Xoae_page page = this.Page();
		page.Root_(root);
		byte[] actl = wiki.Html_mgr().Page_wtr_mgr().Gen(page, gplx.xowa.wikis.pages.Xopg_page_.Tid_read);
		Tfds.Eq_str_lines(expd, String_.new_u8(actl));
	}
	public String Exec__parse_to_html_w_skin(String raw) {
		Bry_bfr bfr = Bry_bfr_.New();
		Xow_html_mgr html_mgr = wiki.Html_mgr();
		this.Wiki().Html__wtr_mgr().Page_read_fmtr().Fmt_("~{page_data}");

		byte[] raw_bry = Bry_.new_u8(raw);
		Xop_root_tkn root = this.Exec_parse_page_all_as_root(raw_bry);
		this.Page().Root_(root);

		html_mgr.Page_wtr_mgr().Wkr(Xopg_page_.Tid_read).Write_page(bfr, this.Page(), this.Ctx(), Xoh_page_html_source_.Wtr);
		return bfr.To_str_and_clear();
	}
	public void Test__parse_to_html_w_skin(String raw, String expd) {
		Tfds.Eq_str_lines(expd, Exec__parse_to_html_w_skin(raw));
	}
	public static Xop_fxt New_app_html() {
		Xop_fxt fxt = new Xop_fxt();
		fxt.Wiki().Html_mgr().Page_wtr_mgr().Page_read_fmtr().Fmt_("~{page_data}");
		return fxt;
	}
}
