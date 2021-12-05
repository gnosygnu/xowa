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
package gplx.objects.strings.unicodes;
import gplx.objects.errs.ErrUtl;
import gplx.tests.GfoTstr;
import org.junit.Test;
public class UstringUtlTest {
	private final UstringTstr fxt = new UstringTstr();
	@Test public void Empty() {
		fxt.Init("");
		fxt.TestLen(0, 0);
	}
	@Test public void Blank() {
		fxt.Init("");
		fxt.TestLen(0, 0);
	}
	@Test public void Single() {
		fxt.Init("Abc");
		fxt.TestLen(3, 3);
		fxt.TestGetCode(65, 98, 99);
		fxt.TestMapCodeToChar(0, 1, 2, 3);
		fxt.TestMapCharToCode(0, 1, 2, 3);
	}
	@Test public void Multi() {
		fxt.Init("a¢€𤭢b");
		fxt.TestLen(5, 6);
		fxt.TestGetCode(97, 162, 8364, 150370, 98);
		fxt.TestMapCodeToChar(0, 1, 2, 3, 5, 6);
		fxt.TestMapCharToCode(0,  1,  2,  3, -1,  4, 5);
	}
	@Test public void IndexOf() {
		fxt.TestIndexOf("abc", "b", 0, 1);  // basic
		fxt.TestIndexOf("ab", "bc", 0, -1); // out-of-bounds
		fxt.TestIndexOf("a¢e", "¢", 0, 1);  // check UTF-8 strings still match at byte-level
	}

	@Test public void Substring() {
		fxt.TestSubstring("abc", 1, 2, "b");  // basic
		fxt.TestSubstring("¢bc", 1, 2, "b");  // check UTF-8 strings don't get lopped off
	}
}
class UstringTstr {
	private Ustring under;
	public void Init(String src) {
		this.under = UstringUtl.NewCodepoints(src);
	}
	public void TestLen(int expdCodes, int expdChars) {
		GfoTstr.EqInt(expdCodes, under.LenInData(), "codes");
		GfoTstr.EqInt(expdChars, under.LenInChars(), "chars");
	}
	public void TestGetCode(int... expd) {
		int actlLen = under.LenInData();
		int[] actl = new int[actlLen];
		for (int i = 0; i < actlLen; i++)
			actl[i] = under.GetData(i);
		GfoTstr.EqAry(expd, actl);
	}
	public void TestMapCodeToChar(int... expd) {
		int actlLen = under.LenInData() + 1;
		int[] actl = new int[actlLen];
		for (int i = 0; i < actlLen; i++)
			actl[i] = under.MapDataToChar(i);
		GfoTstr.EqAry(expd, actl);
	}
	public void TestMapCharToCode(int... expd) {
		int actlLen = under.LenInChars() + 1;
		int[] actl = new int[actlLen];
		for (int i = 0; i < actlLen; i++) {
			int val = 0;
			try {
				val = under.MapCharToData(i);
			}
			catch (Exception exc) {
				val = -1;
				ErrUtl.Noop(exc);
			}
			actl[i] = val;
		}
		GfoTstr.EqAry(expd, actl);
	}
	public void TestIndexOf(String srcStr, String findStr, int bgn, int expd) {
		Ustring src = UstringUtl.NewCodepoints(srcStr);
		Ustring find = UstringUtl.NewCodepoints(findStr);
		int actl = src.IndexOf(find, bgn);
		GfoTstr.EqInt(expd, actl);
	}
	public void TestSubstring(String srcStr, int bgn, int end, String expd) {
		Ustring src = UstringUtl.NewCodepoints(srcStr);
		String actl = src.Substring(bgn, end);
		GfoTstr.EqStr(expd, actl);
	}
}
