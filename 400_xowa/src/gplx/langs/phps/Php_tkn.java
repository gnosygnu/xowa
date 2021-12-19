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
package gplx.langs.phps;
import gplx.types.basics.utls.ByteUtl;
public interface Php_tkn {
	byte Tkn_tid();
	int Src_bgn();
	int Src_end();
}
class Php_tkn_ {
	public static final byte Tid_txt = 1, Tid_declaration = 2, Tid_ws = 3, Tid_comment = 4, Tid_var = 5, Tid_eq = 6, Tid_eq_kv = 7, Tid_semic = 8, Tid_comma = 9, Tid_paren_bgn = 10, Tid_paren_end = 11, Tid_null = 12, Tid_false = 13, Tid_true = 14, Tid_ary = 15, Tid_num = 16, Tid_quote = 17, Tid_brack_bgn = 18, Tid_brack_end = 19;
	public static String Xto_str(byte tid) {return ByteUtl.ToStr(tid);}
}
