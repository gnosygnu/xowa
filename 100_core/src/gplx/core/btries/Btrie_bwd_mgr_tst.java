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
package gplx.core.btries; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Btrie_bwd_mgr_tst {
	@Before public void init() {}	private Btrie_bwd_mgr trie;
	private void ini_setup1() {
		trie = new Btrie_bwd_mgr(false);
		run_Add("c"		,	1);
		run_Add("abc"	,	123);
	}
	@Test  public void Get_by() {
		ini_setup1();
		tst_MatchAtCur("c"		, 1);
		tst_MatchAtCur("abc"	, 123);
		tst_MatchAtCur("bc"		, 1);
		tst_MatchAtCur("yzabc"	, 123);
		tst_MatchAtCur("ab"		, null);
	}
	@Test  public void Fetch_intl() {
		trie = new Btrie_bwd_mgr(false);
		run_Add("a�",	1);
		tst_MatchAtCur("a�"		, 1);
		tst_MatchAtCur("�"		, null);
	}
	@Test  public void Eos() {
		ini_setup1();
		tst_Match("ab", Byte_ascii.Ltr_c, 2, 123);
	}
	@Test  public void Match_exact() {
		ini_setup1();
		tst_MatchAtCurExact("c", 1);
		tst_MatchAtCurExact("bc", null);
		tst_MatchAtCurExact("abc", 123);
	}
	private void ini_setup2() {
		trie = new Btrie_bwd_mgr(false);
		run_Add("a"	,	1);
		run_Add("b"	,	2);
	}
	@Test  public void Match_2() {
		ini_setup2();
		tst_MatchAtCur("a", 1);
		tst_MatchAtCur("b", 2);
	}
	private void ini_setup_caseAny() {
		trie = Btrie_bwd_mgr.ci_();
		run_Add("a"	,	1);
		run_Add("b"	,	2);
	}
	@Test  public void CaseAny() {
		ini_setup_caseAny();
		tst_MatchAtCur("a", 1);
		tst_MatchAtCur("A", 1);
	}
	private void run_Add(String k, int val) {trie.Add(Bry_.new_u8(k), val);}
	private void tst_Match(String srcStr, byte b, int bgn_pos, int expd) {
		byte[] src = Bry_.new_u8(srcStr);
		Object actl = trie.Match(b, src, bgn_pos, -1);
		Tfds.Eq(expd, actl);
	}
	private void tst_MatchAtCur(String srcStr, Object expd) {
		byte[] src = Bry_.new_u8(srcStr);
		Object actl = trie.Match(src[src.length - 1], src, src.length - 1, -1);
		Tfds.Eq(expd, actl);
	}
	private void tst_MatchAtCurExact(String srcStr, Object expd) {
		byte[] src = Bry_.new_u8(srcStr);
		Object actl = trie.Match_exact(src, src.length - 1, -1);
		Tfds.Eq(expd, actl);
	}
}
