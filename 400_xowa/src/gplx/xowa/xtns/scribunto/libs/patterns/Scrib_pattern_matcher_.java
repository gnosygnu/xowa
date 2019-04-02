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
package gplx.xowa.xtns.scribunto.libs.patterns; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.scribunto.libs.*;
import gplx.core.intls.*;
import gplx.langs.regxs.*;
public class Scrib_pattern_matcher_ {
	private static final    Scrib_pattern_matcher instance = New();
	private static Scrib_pattern_matcher New() {
				return new Scrib_pattern_matcher__regx();
//		return new Scrib_pattern_matcher__luaj();
			}
	public static Scrib_pattern_matcher Instance() {return instance;}
}
class Scrib_pattern_matcher__regx implements Scrib_pattern_matcher {
	public Regx_match[] Match(Xoa_url url, Unicode_string text_ucs, Scrib_regx_converter regx_converter, String find_str, int bgn_as_codes) {
		// convert regex from lua to java
		find_str = regx_converter.patternToRegex(find_str, Scrib_regx_converter.Anchor_G, true);

		// run regex
		Regx_adp regx_adp = Scrib_lib_ustring.RegxAdp_new_(url, find_str);
		return regx_adp.Match_all(text_ucs.Src_string(), text_ucs.Pos_codes_to_chars(bgn_as_codes));	// NOTE: MW calculates an offset to handle mb strings. however, java's regex always takes offset in chars (not bytes like PHP preg_match); DATE:2014-03-04
	}
}
