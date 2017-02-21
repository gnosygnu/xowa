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
public class Btrie_fast_mgr_tst {
	private Btrie_fast_mgr_fxt fxt = new Btrie_fast_mgr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Get_by() {
		fxt.Test_matchAtCur("a"		, 1);
		fxt.Test_matchAtCur("abc"	, 123);
		fxt.Test_matchAtCur("ab"	, 1);
		fxt.Test_matchAtCur("abcde"	, 123);
		fxt.Test_matchAtCur(" a"	, null);
	}
	@Test  public void Bos() {
		fxt.Test_match("bc", Byte_ascii.Ltr_a, -1, 123);
	}
	@Test  public void Match_exact() {
		fxt.Test_matchAtCurExact("a", 1);
		fxt.Test_matchAtCurExact("ab", null);
		fxt.Test_matchAtCurExact("abc", 123);
	}
	@Test  public void Del_noop__no_match() {
		fxt.Exec_del("d");
		fxt.Test_matchAtCurExact("a"	, 1);
		fxt.Test_matchAtCurExact("abc"	, 123);
	}
	@Test  public void Del_noop__partial_match() {
		fxt.Exec_del("ab");
		fxt.Test_matchAtCurExact("a"	, 1);
		fxt.Test_matchAtCurExact("abc"	, 123);
	}
	@Test  public void Del_match__long() {
		fxt.Exec_del("abc");
		fxt.Test_matchAtCurExact("a"	, 1);
		fxt.Test_matchAtCurExact("abc"	, null);
	}
	@Test  public void Del_match__short() {
		fxt.Exec_del("a");
		fxt.Test_matchAtCurExact("a"	, null);
		fxt.Test_matchAtCurExact("abc"	, 123);
	}
}
class Btrie_fast_mgr_fxt {
	private Btrie_fast_mgr trie;
	public void Clear() {
		trie = Btrie_fast_mgr.cs();
		Init_add(  1	, Byte_ascii.Ltr_a);
		Init_add(123	, Byte_ascii.Ltr_a, Byte_ascii.Ltr_b, Byte_ascii.Ltr_c);
	}
	public void Init_add(int val, byte... ary) {trie.Add(ary, val);}
	public void Test_match(String src_str, byte b, int bgn_pos, int expd) {
		byte[] src = Bry_.new_a7(src_str);
		Object actl = trie.Match_bgn_w_byte(b, src, bgn_pos, src.length);
		Tfds.Eq(expd, actl);
	}
	public void Test_matchAtCur(String src_str, Object expd) {
		byte[] src = Bry_.new_a7(src_str);
		Object actl = trie.Match_bgn(src, 0, src.length);
		Tfds.Eq(expd, actl);
	}
	public void Test_matchAtCurExact(String src_str, Object expd) {
		byte[] src = Bry_.new_a7(src_str);
		Object actl = trie.Match_exact(src, 0, src.length);
		Tfds.Eq(expd, actl);
	}
	public void Exec_del(String src_str) {
		trie.Del(Bry_.new_u8(src_str));
	}
}
