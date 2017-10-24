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
package gplx.langs.gfs; import gplx.*; import gplx.langs.*;
class Gfs_lxr_ {
	public static final int Rv_init = -1, Rv_null = -2, Rv_eos = -3, Rv_lxr = -4;
	public static final int Tid_identifier = 1, Tid_dot = 2, Tid_semic = 3, Tid_paren_bgn = 4, Tid_paren_end = 5, Tid_curly_bgn = 6, Tid_curly_end = 7, Tid_quote = 8, Tid_comma = 9, Tid_whitespace = 10, Tid_comment = 11, Tid_eq = 12;
	public static String Tid__name(int tid) {
		switch (tid) {
			case Tid_identifier:		return "identifier";
			case Tid_dot:				return "dot";
			case Tid_semic:				return "semic";
			case Tid_paren_bgn:			return "paren_bgn";
			case Tid_paren_end:			return "paren_end";
			case Tid_curly_bgn:			return "curly_bgn";
			case Tid_curly_end:			return "curly_end";
			case Tid_quote:				return "quote";
			case Tid_comma:				return "comma";
			case Tid_whitespace:		return "whitespace";
			case Tid_comment:			return "comment";
			case Tid_eq:				return "eq";
			default:					throw Err_.new_unhandled(tid);
		}
	}
}
