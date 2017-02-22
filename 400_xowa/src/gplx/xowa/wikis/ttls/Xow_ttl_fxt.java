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
package gplx.xowa.wikis.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.log_msgs.*;
class Xow_ttl_fxt {
	private final    Xop_fxt fxt = new Xop_fxt();
	public Xow_ttl_fxt Init_ttl(String raw) {test_raw = raw; return this;} private String test_raw = "";
	public Xow_ttl_fxt Expd_ns_id(int v) {expd_ns_id = v; return this;} private int expd_ns_id = Int_.Min_value;
	public Xow_ttl_fxt Expd_page_txt(String v) {expd_page_txt = v; return this;} private String expd_page_txt;
	public Xow_ttl_fxt Expd_page_url(String v) {expd_page_url = v; return this;} private String expd_page_url;
	public Xow_ttl_fxt Expd_page_db (String v) {expd_page_db  = v; return this;} private String expd_page_db;
	public Xow_ttl_fxt Expd_full_txt(String v) {expd_full_txt = v; return this;} private String expd_full_txt;
	public Xow_ttl_fxt Expd_full_url(String v) {expd_full_url = v; return this;} private String expd_full_url;
	public Xow_ttl_fxt Expd_leaf_txt(String v) {expd_leaf_txt = v; return this;} private String expd_leaf_txt;
	public Xow_ttl_fxt Expd_leaf_url(String v) {expd_leaf_url = v; return this;} private String expd_leaf_url;
	public Xow_ttl_fxt Expd_base_txt(String v) {expd_base_txt = v; return this;} private String expd_base_txt;
	public Xow_ttl_fxt Expd_base_url(String v) {expd_base_url = v; return this;} private String expd_base_url;
	public Xow_ttl_fxt Expd_root_txt(String v) {expd_root_txt = v; return this;} private String expd_root_txt;
	public Xow_ttl_fxt Expd_rest_txt(String v) {expd_rest_txt = v; return this;} private String expd_rest_txt;
	public Xow_ttl_fxt Expd_talk_txt(String v) {expd_talk_txt = v; return this;} private String expd_talk_txt;
	public Xow_ttl_fxt Expd_talk_url(String v) {expd_talk_url = v; return this;} private String expd_talk_url;
	public Xow_ttl_fxt Expd_subj_txt(String v) {expd_subj_txt = v; return this;} private String expd_subj_txt;
	public Xow_ttl_fxt Expd_subj_url(String v) {expd_subj_url = v; return this;} private String expd_subj_url;
	public Xow_ttl_fxt Expd_qarg_txt(String v) {expd_qarg_txt = v; return this;} private String expd_qarg_txt;
	public Xow_ttl_fxt Expd_xwik_txt(String v) {expd_xwik_txt = v; return this;} private String expd_xwik_txt;
	public Xow_ttl_fxt Expd_anch_txt(String v) {expd_anch_txt = v; return this;} private String expd_anch_txt;
	public Xow_ttl_fxt Expd_base_txt_wo_qarg(String v) {expd_base_txt_wo_qarg = v; return this;} private String expd_base_txt_wo_qarg;
	public Xow_ttl_fxt Expd_leaf_txt_wo_qarg(String v) {expd_leaf_txt_wo_qarg = v; return this;} private String expd_leaf_txt_wo_qarg;
	public Xow_ttl_fxt Expd_force_literal_link(int v) {expd_force_literal_link = v; return this;} private int expd_force_literal_link = -1;
	public Xow_ttl_fxt Expd_err(Gfo_msg_itm v) {expd_err = v; return this;} private Gfo_msg_itm expd_err;
	public Xowe_wiki Wiki() {return fxt.Wiki();}
	public void Reset() {
		fxt.Reset();
		fxt.Wiki().Xwiki_mgr().Add_by_atrs(Bry_.new_a7("fr"), Bry_.new_a7("fr.wikipedia.org"));
		test_raw = "Test page";
		expd_ns_id = Int_.Min_value;
		expd_xwik_txt = expd_full_txt = expd_full_url = expd_page_txt = expd_page_url = expd_leaf_txt = expd_leaf_url = expd_base_txt = expd_base_url
			= expd_root_txt = expd_rest_txt = expd_talk_txt = expd_talk_url = expd_subj_txt = expd_subj_url = expd_anch_txt 
			= expd_base_txt_wo_qarg = expd_leaf_txt_wo_qarg = expd_qarg_txt = null;
		expd_err = null;
		expd_force_literal_link = -1;			
		fxt.Log_clear();
	}
	public void Test() {
		Xoa_ttl actl = Xoa_ttl.Parse(fxt.Wiki(), Bry_.new_u8(test_raw));
		if (expd_err == null) {
			if (expd_ns_id != Int_.Min_value) Tfds.Eq(expd_ns_id, actl.Ns().Id(), "ns");
			if (expd_xwik_txt != null) Tfds.Eq(expd_xwik_txt, String_.new_u8(actl.Wik_txt()), "Wiki");
			if (expd_page_txt != null) Tfds.Eq(expd_page_txt, String_.new_u8(actl.Page_txt()), "Page_txt");
			if (expd_page_url != null) Tfds.Eq(expd_page_url, String_.new_u8(actl.Page_url()), "Page_url");
			if (expd_page_db  != null) Tfds.Eq(expd_page_db , String_.new_u8(actl.Page_db()) , "Page_db");
			if (expd_full_txt != null) Tfds.Eq(expd_full_txt, String_.new_u8(actl.Full_txt_w_ttl_case()), "Full_txt");
			if (expd_full_url != null) Tfds.Eq(expd_full_url, String_.new_u8(actl.Full_url()), "Full_url");
			if (expd_leaf_txt != null) Tfds.Eq(expd_leaf_txt, String_.new_u8(actl.Leaf_txt()), "Leaf_txt");
			if (expd_leaf_url != null) Tfds.Eq(expd_leaf_url, String_.new_u8(actl.Leaf_url()), "Leaf_url");
			if (expd_base_txt != null) Tfds.Eq(expd_base_txt, String_.new_u8(actl.Base_txt()), "Base_txt");
			if (expd_base_url != null) Tfds.Eq(expd_base_url, String_.new_u8(actl.Base_url()), "Base_url");
			if (expd_root_txt != null) Tfds.Eq(expd_root_txt, String_.new_u8(actl.Root_txt()), "Root_txt");
			if (expd_rest_txt != null) Tfds.Eq(expd_rest_txt, String_.new_u8(actl.Rest_txt()), "Rest_txt");
			if (expd_talk_txt != null) Tfds.Eq(expd_talk_txt, String_.new_u8(actl.Talk_txt()), "Talk_txt");
			if (expd_talk_url != null) Tfds.Eq(expd_talk_url, String_.new_u8(actl.Talk_url()), "Talk_url");
			if (expd_subj_txt != null) Tfds.Eq(expd_subj_txt, String_.new_u8(actl.Subj_txt()), "Subj_txt");
			if (expd_subj_url != null) Tfds.Eq(expd_subj_url, String_.new_u8(actl.Subj_url()), "Subj_url");
			if (expd_anch_txt != null) Tfds.Eq(expd_anch_txt, String_.new_u8(actl.Anch_txt()), "Anch_txt");
			if (expd_qarg_txt != null) Tfds.Eq(expd_qarg_txt, String_.new_u8(actl.Qarg_txt()), "Qarg_txt");
			if (expd_base_txt_wo_qarg != null) Tfds.Eq(expd_base_txt_wo_qarg, String_.new_u8(actl.Base_txt_wo_qarg()), "Expd_base_txt_wo_qarg");
			if (expd_leaf_txt_wo_qarg != null) Tfds.Eq(expd_leaf_txt_wo_qarg, String_.new_u8(actl.Leaf_txt_wo_qarg()), "Expd_leaf_txt_wo_qarg");
			if (expd_force_literal_link != -1) Tfds.Eq(expd_force_literal_link == 1, actl.ForceLiteralLink(), "ForceLiteralLink");
		}
		else {
			Tfds.Eq_ary(String_.Ary(String_.new_u8(expd_err.Owner().Path()) + "." + String_.new_u8(expd_err.Key_bry())), fxt.Log_xtoAry());
		}
	}
}
