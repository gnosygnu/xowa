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
package gplx;
import org.junit.*;
public class ByteTrieMgr_fast_tst {
	private ByteTrieMgr_fast_fxt fxt = new ByteTrieMgr_fast_fxt();
	@Before public void init() {fxt.Clear();}
	@Test  public void Fetch() {
		fxt.Test_matchAtCur("a"		, 1);
		fxt.Test_matchAtCur("abc"	, 123);
		fxt.Test_matchAtCur("ab"	, 1);
		fxt.Test_matchAtCur("abcde"	, 123);
		fxt.Test_matchAtCur(" a"	, null);
	}
	@Test  public void Bos() {
		fxt.Test_match("bc", Byte_ascii.Ltr_a, -1, 123);
	}
	@Test  public void MatchAtCurExact() {
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
class ByteTrieMgr_fast_fxt {
	private ByteTrieMgr_fast trie;
	public void Clear() {
		trie = ByteTrieMgr_fast.cs_();
		Init_add(  1	, Byte_ascii.Ltr_a);
		Init_add(123	, Byte_ascii.Ltr_a, Byte_ascii.Ltr_b, Byte_ascii.Ltr_c);
	}
	public void Init_add(int val, byte... ary) {trie.Add(ary, val);}
	public void Test_match(String src_str, byte b, int bgn_pos, int expd) {
		byte[] src = Bry_.new_ascii_(src_str);
		Object actl = trie.Match(b, src, bgn_pos, src.length);
		Tfds.Eq(expd, actl);
	}
	public void Test_matchAtCur(String src_str, Object expd) {
		byte[] src = Bry_.new_ascii_(src_str);
		Object actl = trie.MatchAtCur(src, 0, src.length);
		Tfds.Eq(expd, actl);
	}
	public void Test_matchAtCurExact(String src_str, Object expd) {
		byte[] src = Bry_.new_ascii_(src_str);
		Object actl = trie.MatchAtCurExact(src, 0, src.length);
		Tfds.Eq(expd, actl);
	}
	public void Exec_del(String src_str) {
		trie.Del(Bry_.new_utf8_(src_str));
	}
}
