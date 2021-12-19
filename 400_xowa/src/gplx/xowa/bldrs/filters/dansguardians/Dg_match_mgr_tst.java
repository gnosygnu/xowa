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
package gplx.xowa.bldrs.filters.dansguardians;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.dbs.Db_conn_bldr;
import gplx.types.basics.utls.BoolUtl;
import org.junit.Before;
import org.junit.Test;
public class Dg_match_mgr_tst {
	@Before public void init() {fxt.Clear();} private Dg_match_mgr_fxt fxt = new Dg_match_mgr_fxt();
	@Test public void One() {
		fxt.Init_line(100, "a");
		fxt.Test_match_many_y("a", "ab", "ba", "abc");
		fxt.Test_match_many_n("b");
	}
}
class Dg_match_mgr_fxt {
	private Dg_match_mgr match_mgr;
	private final List_adp rule_list = List_adp_.New();
	public void Clear() {
		Db_conn_bldr.Instance.Reg_default_mem();
		Io_url root_dir = Io_url_.mem_dir_("mem/dg/");
		match_mgr = new Dg_match_mgr(root_dir.GenSubDir("words"), 1, 0, BoolUtl.Y, BoolUtl.Y, root_dir.GenSubDir("log"));
		rule_list.Clear();
	}
	public void Init_line(int score, String... words) {
		Dg_rule line = new Dg_rule(-1, -1, -1, Dg_rule.Tid_rule, BryUtl.NewA7("key"), score, Dg_word.Ary_new_by_str_ary(words));
		rule_list.Add(line);
	}
	public void Test_match_many_y(String... words) {Test_match_many(BoolUtl.Y, words);}
	public void Test_match_many_n(String... words) {Test_match_many(BoolUtl.N, words);}
	public void Test_match_many(boolean expd, String... words) {
		int words_len = words.length;
		for (int i = 0; i < words_len; ++i)
			Test_match_one(expd, words[i]);
	}
	public void Test_match_one(boolean expd, String word_str) {
		match_mgr.Clear();
		int rule_list_len = rule_list.Len();
		for (int j = 0; j < rule_list_len; ++j) {
			Dg_rule rule = (Dg_rule)rule_list.GetAt(j);
			match_mgr.Init_by_rule(rule);
		}
		byte[] word_bry = BryUtl.NewU8(word_str);
		GfoTstr.EqObj(expd, match_mgr.Match(1, 101, 0, BryUtl.Empty, BryUtl.Empty, null, word_bry), (expd ? "pass:" : "fail:") + word_str);
	}
}
