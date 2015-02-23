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
package gplx.xowa.bldrs.filters.dansguardians; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.filters.*;
import gplx.core.primitives.*; import gplx.core.btries.*;
import gplx.xowa.bldrs.filters.core.*;
public class Dg_match_mgr {
	private int score_init, score_fail; private boolean log_enabled, case_match;
	private final Btrie_slim_mgr btrie = Btrie_slim_mgr.cs_();
	private final OrderedHash rules = OrderedHash_.new_bry_();
	private final OrderedHash rule_group_hash = OrderedHash_.new_bry_(), rule_tally_hash = OrderedHash_.new_bry_();
	private final Dg_parser parser = new Dg_parser();
	private final Xob_ttl_filter_mgr ttl_filter_mgr = new Xob_ttl_filter_mgr();
	private final Dg_log_mgr log_mgr = new Dg_log_mgr();
	public Dg_match_mgr(Io_url root_dir, int score_init, int score_fail, boolean case_match, boolean log_enabled, Io_url log_url) {
		this.score_init = score_init; this.score_fail = score_fail; this.case_match = case_match; this.log_enabled = log_enabled;
		if (log_enabled) log_mgr.Init(log_url);
		ttl_filter_mgr.Load(Bool_.N, root_dir.GenSubFil("xowa.title.include.txt"));
		ttl_filter_mgr.Load(Bool_.Y, root_dir.GenSubFil("xowa.title.exclude.txt"));
		Io_url dg_root_url = root_dir.GenSubDir("dansguardian");
		Dg_file[] files = parser.Parse_dir(dg_root_url); Gfo_usr_dlg_._.Plog_many("", "", "import.dg.rules: url=~{0} files=~{1}", dg_root_url, files.length);
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
		Dg_rule_group rv = (Dg_rule_group)rule_group_hash.Fetch(word);
		if (rv == null) {
			rv = new Dg_rule_group(word);
			rule_group_hash.Add(word, rv);
		}
		return rv;
	}
	private Dg_rule_tally Get_rule_tally_or_new(byte[] key, Dg_rule rule) {
		Dg_rule_tally rv = (Dg_rule_tally)rule_tally_hash.Fetch(key);
		if (rv == null) {
			rv = new Dg_rule_tally(rule);
			rule_tally_hash.Add(key, rv);
		}
		return rv;
	}
	public boolean Match(int log_tid, int page_id, int page_ns, byte[] page_ttl, byte[] page_ttl_db, Xol_lang lang, byte[] src) {
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
			Dg_rule_tally rule_tally = (Dg_rule_tally)rule_tally_hash.FetchAt(i);
			int min_results = rule_tally.Results_pass_count();
			if (min_results > 0) {
				int rule_score = rule_tally.Rule().Score();
				int rule_score_total = rule_score * min_results;
				if (log_enabled) log_mgr.Insert_page_rule(log_tid, page_id, rule_tally.Rule().Id(), rule_score_total);
				if (rule_score == Dg_rule.Score_banned) {score_cur = Int_.MaxValue; break;}
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
	public static final int Target_tid_title = 1, Target_tid_wikitext = 2;
}
class Dg_rule_group {
	public Dg_rule_group(byte[] word) {this.word = word;}
	public byte[] Word() {return word;} private final byte[] word;
	public ListAdp Rules_list() {return rules_list;} private final ListAdp rules_list = ListAdp_.new_();
	public Dg_rule[] Rules_ary() {
		if (rules_ary == null)
			rules_ary = (Dg_rule[])rules_list.Xto_ary_and_clear(Dg_rule.class);
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
	public Dg_rule Rule() {return rule;} private final Dg_rule rule;
	public int[] Results() {return results;} private final int[] results; private final int results_len;
	public void Process(byte[] word) {
		Int_obj_ref idx = (Int_obj_ref)rule.Word_idx_hash().Get_by_bry(word);
		int idx_val = idx.Val();
		results[idx_val] = results[idx_val] + 1;
	}
	public int Results_pass_count() {
		int rv = Int_.MaxValue;
		for (int i = 0; i < results_len; ++i) {
			int result = results[i];
			if (rv > result) rv = result;
		}
		return rv;
	}
}
