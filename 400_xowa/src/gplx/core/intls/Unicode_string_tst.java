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
package gplx.core.intls; import gplx.*; import gplx.core.*;
import org.junit.*; import gplx.core.tests.*;
public class Unicode_string_tst {
	private final    Unicode_string_fxt fxt = new Unicode_string_fxt();
	@Test  public void Null() {
		fxt.Init(null);
		fxt.Test__Len(0, 0, 0);
	}
	@Test  public void Blank() {
		fxt.Init("");
		fxt.Test__Len(0, 0, 0);
	}
	@Test  public void Single() {
		fxt.Init("Abc");
		fxt.Test__Len(3, 3, 3);
		fxt.Test__Val_codes(65, 98, 99);
		fxt.Test__Pos_codes_to_bytes(0, 1, 2, 3);
		fxt.Test__Pos_codes_to_chars(0, 1, 2, 3);
		fxt.Test__Pos_chars_to_codes(0, 1, 2, 3);
		fxt.Test__Pos_bytes_to_codes(0, 1, 2, 3);
	}
	@Test  public void Multi() {
		fxt.Init("a¢€𤭢");
		fxt.Test__Len(4, 5, 10);
		fxt.Test__Val_codes(97, 162, 8364, 150370);
		fxt.Test__Pos_codes_to_bytes(0, 1, 3, 6, 10);
		fxt.Test__Pos_codes_to_chars(0, 1, 2, 3, 5);
		fxt.Test__Pos_chars_to_codes( 0,  1,  2,  3, -1,  4);
		fxt.Test__Pos_bytes_to_codes( 0,  1, -1,  2, -1, -1,  3, -1, -1, -1,  4);
	}
}
class Unicode_string_fxt {
	private Unicode_string under;
	public void Init(String src) {
		this.under = Unicode_string_.New(src);
	}
	public void Test__Len(int expd_codes, int expd_chars, int expd_bytes) {
		Gftest.Eq__int(expd_codes, under.Len_codes(), "codes");
		Gftest.Eq__int(expd_chars, under.Len_chars(), "chars");
		Gftest.Eq__int(expd_bytes, under.Len_bytes(), "bytes");
	}
	public void Test__Val_codes(int... expd) {
		int actl_len = under.Len_codes();
		int[] actl = new int[actl_len];
		for (int i = 0; i < actl_len; i++)
			actl[i] = under.Val_codes(i);
		Gftest.Eq__ary(expd, actl);
	}
	public void Test__Pos_codes_to_bytes(int... expd) {
		int actl_len = under.Len_codes() + 1;
		int[] actl = new int[actl_len];
		for (int i = 0; i < actl_len; i++)
			actl[i] = under.Pos_codes_to_bytes(i);
		Gftest.Eq__ary(expd, actl);
	}
	public void Test__Pos_codes_to_chars(int... expd) {
		int actl_len = under.Len_codes() + 1;
		int[] actl = new int[actl_len];
		for (int i = 0; i < actl_len; i++)
			actl[i] = under.Pos_codes_to_chars(i);
		Gftest.Eq__ary(expd, actl);
	}
	public void Test__Pos_bytes_to_codes(int... expd) {
		int actl_len = under.Len_bytes() + 1;
		int[] actl = new int[actl_len];
		for (int i = 0; i < actl_len; i++) {
			int val = 0;
			try {
				val = under.Pos_bytes_to_codes(i);
			}
			catch (Exception exc) {
				val = -1;
				Err_.Noop(exc);
			}
			actl[i] = val;
		}
		Gftest.Eq__ary(expd, actl);
	}
	public void Test__Pos_chars_to_codes(int... expd) {
		int actl_len = under.Len_chars() + 1;
		int[] actl = new int[actl_len];
		for (int i = 0; i < actl_len; i++) {
			int val = 0;
			try {
				val = under.Pos_chars_to_codes(i);
			}
			catch (Exception exc) {
				val = -1;
				Err_.Noop(exc);
			}
			actl[i] = val;
		}
		Gftest.Eq__ary(expd, actl);
	}
}
