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
package gplx.xowa.xtns.pfuncs.wikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.core.btries.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*; import gplx.xowa.langs.numbers.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.data.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.addons.wikis.ctgs.dbs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_pagesincategory extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_site_pagesincategory;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_pagesincategory().Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {	// REF.MW: /includes/parser/CoreParserFunctions.php|pagesincategory
		byte[] ctg_ttl_bry = Eval_argx(ctx, src, caller, self);
		if (Bry_.Len_eq_0(ctg_ttl_bry)) {bfr.Add_int_digits(1, 0); return;}			// no title; EX: "{{PAGESINCATEGORY:}}"
		Xowe_wiki wiki = ctx.Wiki();
		Xow_db_mgr core_data_mgr = wiki.Data__core_mgr();
		int ctg_id = core_data_mgr.Tbl__page().Select_id(Xow_ns_.Tid__category, Xoa_ttl.Replace_spaces(ctg_ttl_bry));
		if (ctg_id == Xowd_page_itm.Id_null) {bfr.Add_int_digits(1, 0); return;}	// category doesn't exist; EX: "{{PAGESINCATEGORY:Unknown_category}}"
		Xowd_category_itm ctg_itm = Xodb_cat_db_.Get_cat_core_or_fail(core_data_mgr).Select(ctg_id);
		if (ctg_itm == null) {bfr.Add_int_digits(1, 0); return;}					// category counts don't exist; shouldn't happen
		Xol_lang_itm lang = wiki.Lang();
		Btrie_slim_mgr num_format_trie = Xol_kwd_mgr.trie_(lang.Kwd_mgr(), Xol_kwd_grp_.Id_str_rawsuffix);
		Btrie_slim_mgr type_page_trie = Xol_kwd_mgr.trie_(lang.Kwd_mgr(), Xol_kwd_grp_.Id_pagesincategory_pages);
		Btrie_slim_mgr type_subc_trie = Xol_kwd_mgr.trie_(lang.Kwd_mgr(), Xol_kwd_grp_.Id_pagesincategory_subcats);
		Btrie_slim_mgr type_file_trie = Xol_kwd_mgr.trie_(lang.Kwd_mgr(), Xol_kwd_grp_.Id_pagesincategory_files);
		boolean fmt_num = true; byte[] type_bry = null;
		int self_args_len = self.Args_len();
		byte[] arg_1 = self_args_len < 1 ? null : Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 0);
		byte[] arg_2 = self_args_len < 2 ? null : Pf_func_.Eval_arg_or_empty(ctx, src, caller, self, self_args_len, 1);
		if	(num_format_trie.Match_exact(arg_1) != null) {	// is arg_1 raw?
			fmt_num = false;
			type_bry = arg_2;
		}
		else {
			type_bry = arg_1;
			fmt_num = num_format_trie.Match_exact(arg_2) == null;
		}
		int num = ctg_itm.Count_all();
		if (type_bry != null) {
			if		(type_page_trie.Match_exact(type_bry) != null) num = ctg_itm.Count_pages();
			else if	(type_subc_trie.Match_exact(type_bry) != null) num = ctg_itm.Count_subcs();
			else if	(type_file_trie.Match_exact(type_bry) != null) num = ctg_itm.Count_files();
		}
		byte[] num_bry = Int_.To_bry(num);
		byte[] rslt = fmt_num ? lang.Num_mgr().Format_num(num_bry) : lang.Num_mgr().Raw(num_bry);
		bfr.Add(rslt);
	}
	public static final    Pfunc_pagesincategory Instance = new Pfunc_pagesincategory(); Pfunc_pagesincategory() {}
}
