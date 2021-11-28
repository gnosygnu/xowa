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
package gplx.objects.strings.unicodes; import gplx.*; import gplx.objects.*; import gplx.objects.strings.*;
import org.junit.*; import gplx.tests.*;
import gplx.objects.errs.*;
public class Ustring_tst {
	private final    Ustring_fxt fxt = new Ustring_fxt();
	@Test public void Empty() {
		fxt.Init("");
		fxt.Test__Len(0, 0);
	}
	@Test public void Blank() {
		fxt.Init("");
		fxt.Test__Len(0, 0);
	}
	@Test public void Single() {
		fxt.Init("Abc");
		fxt.Test__Len(3, 3);
		fxt.Test__Get_code(65, 98, 99);
		fxt.Test__Map_code_to_char(0, 1, 2, 3);
		fxt.Test__Map_char_to_code(0, 1, 2, 3);
	}
	@Test public void Multi() {
		fxt.Init("a¢€𤭢b");
		fxt.Test__Len(5, 6);
		fxt.Test__Get_code(97, 162, 8364, 150370, 98);
		fxt.Test__Map_code_to_char(0, 1, 2, 3, 5, 6);
		fxt.Test__Map_char_to_code(0,  1,  2,  3, -1,  4, 5);
	}
	@Test public void Index_of() {
		fxt.Test__Index_of("abc", "b", 0, 1);  // basic
		fxt.Test__Index_of("ab", "bc", 0, -1); // out-of-bounds
		fxt.Test__Index_of("a¢e", "¢", 0, 1);  // check UTF-8 strings still match at byte-level
	}

	@Test public void Substring() {
		fxt.Test__Substring("abc", 1, 2, "b");  // basic
		fxt.Test__Substring("¢bc", 1, 2, "b");  // check UTF-8 strings don't get lopped off
	}
}
class Ustring_fxt {
	private Ustring under;
	public void Init(String src) {
		this.under = Ustring_.New_codepoints(src);
	}
	public void Test__Len(int expd_codes, int expd_chars) {
		Gftest_fxt.Eq__int(expd_codes, under.Len_in_data(), "codes");
		Gftest_fxt.Eq__int(expd_chars, under.Len_in_chars(), "chars");
	}
	public void Test__Get_code(int... expd) {
		int actl_len = under.Len_in_data();
		int[] actl = new int[actl_len];
		for (int i = 0; i < actl_len; i++)
			actl[i] = under.Get_data(i);
		Gftest_fxt.Eq__ary(expd, actl);
	}
	public void Test__Map_code_to_char(int... expd) {
		int actl_len = under.Len_in_data() + 1;
		int[] actl = new int[actl_len];
		for (int i = 0; i < actl_len; i++)
			actl[i] = under.Map_data_to_char(i);
		Gftest_fxt.Eq__ary(expd, actl);
	}
	public void Test__Map_char_to_code(int... expd) {
		int actl_len = under.Len_in_chars() + 1;
		int[] actl = new int[actl_len];
		for (int i = 0; i < actl_len; i++) {
			int val = 0;
			try {
				val = under.Map_char_to_data(i);
			}
			catch (Exception exc) {
				val = -1;
				Err_.Noop(exc);
			}
			actl[i] = val;
		}
		Gftest_fxt.Eq__ary(expd, actl);
	}
	public void Test__Index_of(String src_str, String find_str, int bgn, int expd) {
		Ustring src = Ustring_.New_codepoints(src_str);
		Ustring find = Ustring_.New_codepoints(find_str);
		int actl = src.Index_of(find, bgn);
		Gftest_fxt.Eq__int(expd, actl);
	}
	public void Test__Substring(String src_str, int bgn, int end, String expd) {
		Ustring src = Ustring_.New_codepoints(src_str);
		String actl = src.Substring(bgn, end);
		Gftest_fxt.Eq__str(expd, actl);
	}
}
