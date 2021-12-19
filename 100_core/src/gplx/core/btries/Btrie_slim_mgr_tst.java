/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.btries;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.constants.AsciiByte;
import org.junit.Before;
import org.junit.Test;
public class Btrie_slim_mgr_tst {
	@Before public void init() {
	}   private Btrie_slim_mgr trie;
	private void ini_setup1() {
		trie = Btrie_slim_mgr.cs();
		run_Add("a"        ,    1);
		run_Add("abc"    ,    123);
	}
	@Test public void Get_by() {
		ini_setup1();
		tst_MatchAtCur("a"        , 1);
		tst_MatchAtCur("abc"    , 123);
		tst_MatchAtCur("ab"        , 1);
		tst_MatchAtCur("abcde"    , 123);
		tst_MatchAtCur(" a"        , null);
	}
	@Test public void Bos() {
		ini_setup1();
		tst_Match("bc", AsciiByte.Ltr_a, -1, 123);
	}
	@Test public void Match_exact() {
		ini_setup1();
		tst_MatchAtCurExact("a", 1);
		tst_MatchAtCurExact("ab", null);
		tst_MatchAtCurExact("abc", 123);
	}
	private void ini_setup2() {
		trie = Btrie_slim_mgr.cs();
		run_Add("a"    ,    1);
		run_Add("b"    ,    2);
	}
	@Test public void Match_2() {
		ini_setup2();
		tst_MatchAtCur("a", 1);
		tst_MatchAtCur("b", 2);
	}
	private void ini_setup_caseAny() {
		trie = Btrie_slim_mgr.ci_a7();    // NOTE:ci.ascii:test
		run_Add("a"    ,    1);
		run_Add("b"    ,    2);
	}
	@Test public void CaseAny() {
		ini_setup_caseAny();
		tst_MatchAtCur("a", 1);
		tst_MatchAtCur("A", 1);
	}
	@Test public void Del() {
		ini_setup1();
		trie.Del(BryUtl.NewA7("a"));    // delete "a"; "abc" still remains;
		tst_MatchAtCur("a"        , null);
		tst_MatchAtCur("abc"    , 123);

		trie.Del(BryUtl.NewA7("abc"));
		tst_MatchAtCur("abc"    , null);
	}
	@Test public void Match_none() {
		Btrie_slim_mgr_fxt fxt = new Btrie_slim_mgr_fxt();
		fxt.Init__add("k1", "v1");
		fxt.Exec__match("ak1", 1, 3, "v1", 3); // 3 to position after match
		fxt.Exec__match("ak2", 1, 1, null, 1); // 1 to position at current pos; used to be 0; de.w:Butter; DATE:2018-04-12
	}

	private void run_Add(String k, int val) {trie.AddObj(BryUtl.NewA7(k), val);}
	private void tst_Match(String srcStr, byte b, int bgn_pos, int expd) {
		byte[] src = BryUtl.NewA7(srcStr);
		Object actl = trie.Match_bgn_w_byte(b, src, bgn_pos, src.length);
		GfoTstr.EqObj(expd, actl);
	}
	private void tst_MatchAtCur(String srcStr, Object expd) {
		byte[] src = BryUtl.NewA7(srcStr);
		Object actl = trie.Match_bgn_w_byte(src[0], src, 0, src.length);
		GfoTstr.EqObj(expd, actl);
	}
	private void tst_MatchAtCurExact(String srcStr, Object expd) {
		byte[] src = BryUtl.NewA7(srcStr);
		Object actl = trie.Match_exact(src, 0, src.length);
		GfoTstr.EqObj(expd, actl);
	}
}
class Btrie_slim_mgr_fxt {
	private final Btrie_slim_mgr trie = Btrie_slim_mgr.cs();
	public Btrie_slim_mgr_fxt Init__add(String key, String val) {
		trie.Add_str_str(key, val);
		return this;
	}
	public void Exec__match(String src, int bgn_pos, int end_pos, Object expd_val, int expd_pos) {
		Btrie_rv trv = new Btrie_rv();
		Object actl_val = trie.MatchAt(trv, BryUtl.NewU8(src), bgn_pos, end_pos);
		GfoTstr.EqObjToStr(expd_val, actl_val);
		GfoTstr.Eq(expd_pos, trv.Pos());
	}
}
