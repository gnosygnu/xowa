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
package gplx.xowa.bldrs.filters.dansguardians; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.filters.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.xowa.addons.apps.cfgs.*;
import gplx.xowa.langs.*;
import gplx.xowa.bldrs.filters.core.*;
public class Dg_match_mgr {
	private int score_init, score_fail; private boolean log_enabled, case_match;
	private final    Btrie_slim_mgr btrie = Btrie_slim_mgr.cs();
	private final    Ordered_hash rules = Ordered_hash_.New_bry();
	private final    Ordered_hash rule_group_hash = Ordered_hash_.New_bry(), rule_tally_hash = Ordered_hash_.New_bry();
	private final    Dg_parser parser = new Dg_parser();
	private final    Xob_ttl_filter_mgr ttl_filter_mgr = new Xob_ttl_filter_mgr();
	private final    Dg_ns_skip_mgr ns_skip_mgr = new Dg_ns_skip_mgr();
	private final    Dg_log_mgr log_mgr = new Dg_log_mgr();
	public Dg_match_mgr(Io_url root_dir, int score_init, int score_fail, boolean case_match, boolean log_enabled, Io_url log_url) {
		this.score_init = score_init; this.score_fail = score_fail; this.case_match = case_match; this.log_enabled = log_enabled;
		if (log_enabled) log_mgr.Init(log_url);
		ttl_filter_mgr.Load(Bool_.N, root_dir.GenSubFil("xowa.title.include.txt"));
		ttl_filter_mgr.Load(Bool_.Y, root_dir.GenSubFil("xowa.title.exclude.txt"));
		ns_skip_mgr.Load(root_dir.GenSubFil("xowa.ns.skip.txt"));
		Io_url dg_root_url = root_dir.GenSubDir("dansguardian");
		Dg_file[] files = parser.Parse_dir(dg_root_url); Gfo_usr_dlg_.Instance.Plog_many("", "", "import.dg.rules: url=~{0} files=~{1}", dg_root_url, files.length);
		Init_by_files(files);
		if (log_enabled) log_mgr.Commit();
	}
	public void Clear() {
		btrie.Clear();
		rules.Clear();
		rule_group_hash.Clear();
		rule_tally_hash.Clear();
	}
	private void Init_by_files(Dg_file[] files) {
		for (Dg_file file : files) {
			Dg_rule[] rules = file.Lines();
			if (log_enabled) log_mgr.Insert_file(file);
			for (Dg_rule rule : rules)
				Init_by_rule(rule);
		}
	}
	@gplx.Internal protected void Init_by_rule(Dg_rule rule) {
		if (rule.Tid() != Dg_rule.Tid_rule) return;
		if (log_enabled) log_mgr.Insert_rule(rule);
		Dg_word[] words = rule.Words();
		for (Dg_word word : words) {
			Dg_rule_group rule_group = Get_rule_group_or_new(word.Raw());
			rule_group.Rules_list().Add(rule);
			btrie.Add_obj(word.Raw(), rule_group);
		}
	}
	private Dg_rule_group Get_rule_group_or_new(byte[] word) {
		Dg_rule_group rv = (Dg_rule_group)rule_group_hash.Get_by(word);
		if (rv == null) {
			rv = new Dg_rule_group(word);
			rule_group_hash.Add(word, rv);
		}
		return rv;
	}
	private Dg_rule_tally Get_rule_tally_or_new(byte[] key, Dg_rule rule) {
		Dg_rule_tally rv = (Dg_rule_tally)rule_tally_hash.Get_by(key);
		if (rv == null) {
			rv = new Dg_rule_tally(rule);
			rule_tally_hash.Add(key, rv);
		}
		return rv;
	}
	public boolean Match(int log_tid, int page_id, int page_ns, byte[] page_ttl, byte[] page_ttl_db, Xol_lang_itm lang, byte[] src) {
		// if ns is in skip_mgr, ignore; needed to skip Template and Module
		if (ns_skip_mgr.Has(page_ns))
			return false;

		int src_len = src.length;
		int clude_type = 0;
		if		(ttl_filter_mgr.Match_include(page_ttl_db)) clude_type = -1;
		else if (ttl_filter_mgr.Match_exclude(page_ttl_db)) clude_type =  1;
		if (clude_type != 0) {
			log_mgr.Insert_page_score(log_tid, page_id, page_ns, page_ttl, src_len, 0, 0, clude_type);
			return clude_type == 1;
		}
		if (!case_match) {
			src = lang.Case_mgr().Case_build_lower(src);
			src_len = src.length;
		}
		rules.Clear();
		rule_tally_hash.Clear();
		int pos = 0;
		int score_cur = score_init;
		while (pos < src_len) {
			Object o = btrie.Match_bgn(src, pos, src_len);
			if (o == null)
				++pos;
			else {
				Dg_rule_group rule_group = (Dg_rule_group)o;
				Dg_rule[] rules_ary = rule_group.Rules_ary();
				for (Dg_rule rule : rules_ary) {
					Dg_rule_tally rule_tally = Get_rule_tally_or_new(rule.Key(), rule);
					rule_tally.Process(rule_group.Word());
				}
				++pos;
			}
		}
		int rule_tally_len = rule_tally_hash.Count(); if (rule_tally_len == 0) return false;
		int rule_match_count = 0;
		for (int i = 0; i < rule_tally_len; ++i) {
			Dg_rule_tally rule_tally = (Dg_rule_tally)rule_tally_hash.Get_at(i);
			int min_results = rule_tally.Results_pass_count();
			if (min_results > 0) {
				int rule_score = rule_tally.Rule().Score();
				int rule_score_total = rule_score * min_results;
				if (log_enabled) log_mgr.Insert_page_rule(log_tid, page_id, rule_tally.Rule().Id(), rule_score_total);
				if (rule_score == Dg_rule.Score_banned) {score_cur = Int_.Max_value; break;}
				score_cur += rule_score_total;
				++rule_match_count;
			}
		}
		boolean rv = score_cur > score_fail;
		if (rv && log_enabled) log_mgr.Insert_page_score(log_tid, page_id, page_ns, page_ttl, src_len, score_cur, rule_match_count, 0);
		return rv;
	}
	public void Rls() {log_mgr.Rls();}
	public void Commit() {if (log_enabled) log_mgr.Commit();}

	public static void Cfg__reg(Xoa_app app) {
		app.Cfg().Dflt_mgr().Add(Cfg__root_dir, app.Fsys_mgr().Bin_xowa_dir().GenSubDir_nest("cfg", "bldr", "filter").Raw());
	}
	public static Dg_match_mgr New_mgr(Xoa_app app, Xow_wiki wiki) {
		Xocfg_mgr cfg_mgr = app.Cfg();
		if (!cfg_mgr.Get_bool_wiki_or(wiki, Cfg__enabled, false)) return null;
		String ctx = cfg_mgr.To_ctx(wiki);
		return new Dg_match_mgr
		( cfg_mgr.Get_url_or(ctx, Cfg__root_dir, app.Fsys_mgr().Bin_xowa_dir().GenSubDir_nest("cfg", "bldr", "filter")).GenSubDir(wiki.Domain_str())
		, cfg_mgr.Get_int_or(ctx, "xowa.bldr.dansguardian.score_init", 0)
		, cfg_mgr.Get_int_or(ctx, "xowa.bldr.dansguardian.score_fail", 0)
		, cfg_mgr.Get_bool_or(ctx, "xowa.bldr.dansguardian.case_match", false)
		, cfg_mgr.Get_bool_or(ctx, "xowa.bldr.dansguardian.log_enabled", true)
		, wiki.Fsys_mgr().Root_dir().GenSubFil("dansguardian_log.sqlite3")
		);
	}
	public static final String Cfg__enabled		= "xowa.bldr.dansguardian.enabled";
	private static final String Cfg__root_dir		= "xowa.bldr.dansguardian.root_dir";
}
class Dg_rule_group {
	public Dg_rule_group(byte[] word) {this.word = word;}
	public byte[] Word() {return word;} private final    byte[] word;
	public List_adp Rules_list() {return rules_list;} private final    List_adp rules_list = List_adp_.New();
	public Dg_rule[] Rules_ary() {
		if (rules_ary == null)
			rules_ary = (Dg_rule[])rules_list.To_ary_and_clear(Dg_rule.class);
		return rules_ary;
	}	private Dg_rule[] rules_ary;
}
class Dg_rule_tally {
	public Dg_rule_tally(Dg_rule rule) {
		this.rule = rule;
		Dg_word[] words = rule.Words();
		this.results_len = words.length;
		this.results = new int[results_len];
	}
	public Dg_rule Rule() {return rule;} private final    Dg_rule rule;
	public int[] Results() {return results;} private final    int[] results; private final    int results_len;
	public void Process(byte[] word) {
		Int_obj_ref idx = (Int_obj_ref)rule.Word_idx_hash().Get_by_bry(word);
		int idx_val = idx.Val();
		results[idx_val] = results[idx_val] + 1;
	}
	public int Results_pass_count() {
		int rv = Int_.Max_value;
		for (int i = 0; i < results_len; ++i) {
			int result = results[i];
			if (rv > result) rv = result;
		}
		return rv;
	}
}
