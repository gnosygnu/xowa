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
package gplx.core.btries; import gplx.*; import gplx.core.*;
import org.junit.*;
public class Btrie_bwd_mgr_tst {
	@Before public void init() {}	private Btrie_bwd_mgr trie;
	private void ini_setup1() {
		trie = new Btrie_bwd_mgr(false);
		run_Add("c"		,	1);
		run_Add("abc"	,	123);
	}
	@Test  public void Fetch() {
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
	private void run_Add(String k, int val) {trie.Add(Bry_.new_utf8_(k), val);}
	private void tst_Match(String srcStr, byte b, int bgn_pos, int expd) {
		byte[] src = Bry_.new_utf8_(srcStr);
		Object actl = trie.Match(b, src, bgn_pos, -1);
		Tfds.Eq(expd, actl);
	}
	private void tst_MatchAtCur(String srcStr, Object expd) {
		byte[] src = Bry_.new_utf8_(srcStr);
		Object actl = trie.Match(src[src.length - 1], src, src.length - 1, -1);
		Tfds.Eq(expd, actl);
	}
	private void tst_MatchAtCurExact(String srcStr, Object expd) {
		byte[] src = Bry_.new_utf8_(srcStr);
		Object actl = trie.Match_exact(src, src.length - 1, -1);
		Tfds.Eq(expd, actl);
	}
}
