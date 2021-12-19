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
package gplx.langs.regxs;
import gplx.types.commons.lists.GfoListBase;
import gplx.types.basics.utls.StringUtl;
public class Regx_adp_ {
	public static Regx_adp new_(String pattern) {return new Regx_adp(pattern, Regx_adp.FLAG__DEFAULT);}
	public static GfoListBase<Regx_match> Find_all(String src, String pat) {
		int src_len = StringUtl.Len(src);
		Regx_adp regx = Regx_adp_.new_(pat);
		int pos = 0;
		GfoListBase<Regx_match> rv = new GfoListBase<Regx_match>();
		while (pos < src_len) {
			Regx_match match = regx.Match(src, pos);
			if (match.Rslt_none()) break;
			rv.Add(match);
			pos = match.Find_bgn() + match.Find_len();
		}
		return rv;
	}
	public static String Replace(String raw, String regx_str, String replace) {
		Regx_adp regx = Regx_adp_.new_(regx_str);
		return regx.ReplaceAll(raw, replace);
	}
	public static boolean Match(String input, String pattern) {
		Regx_adp rv = new Regx_adp(pattern, Regx_adp.FLAG__DEFAULT);
		return rv.Match(input, 0).Rslt();
	}
}
