/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.objects.strings.char_sources;

public interface Char_source {
	String Src();
	int Get_data(int pos);
	int Len_in_data();

	String Substring(int bgn, int end);
	byte[] SubstringAsBry(int bgn, int end);
	int Index_of(Char_source find, int bgn);
	boolean Eq(int lhs_bgn, Char_source rhs, int rhs_bgn, int rhs_end);
}
