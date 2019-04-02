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
import org.luaj.vm2.lib.StringLib;
import org.luaj.vm2.lib.Str_find_mgr;
import org.luaj.vm2.lib.Str_find_mgr__regx;
class Scrib_pattern_matcher__luaj implements Scrib_pattern_matcher {
	public Regx_match[] Match(Xoa_url url, Unicode_string text_ucs, Scrib_regx_converter regx_converter, String find_str, int bgn_as_codes) {
		//		int src_bgn = bgn_as_codes < 0 ? bgn_as_codes : text_ucs.Pos_codes_to_bytes(bgn_as_codes);
		int src_bgn = bgn_as_codes < 0 ? Int_.Base1 : bgn_as_codes + Int_.Base1;
		src_bgn = src_bgn >= text_ucs.Len_codes() ? text_ucs.Len_codes() : text_ucs.Pos_codes_to_bytes(src_bgn);
		Str_find_mgr__regx mgr = new Str_find_mgr__regx(text_ucs.Src_string(), find_str, src_bgn, false, true);
		mgr.Process();
		
		// convert to Regx_match
		int find_bgn = mgr.Bgn() == -1 ? -1 : text_ucs.Pos_bytes_to_chars(mgr.Bgn());
		int find_end = mgr.End() == -1 ? -1 : text_ucs.Pos_bytes_to_chars(mgr.End());
		boolean found = find_bgn != -1;
		if (!found) {
			return Regx_match.Ary_empty;
		}
		int[] captures = mgr.Capture_ints();
		Regx_group[] groups = null;
		if (found && captures != null) {
			int captures_len = captures.length;
			groups = new Regx_group[captures_len / 2];
			for (int i = 0; i < captures_len; i += 2) {				
				groups[i / 2] = new Regx_group(true, captures[i], captures[i + 1], String_.Mid(text_ucs.Src_string(), text_ucs.Pos_bytes_to_chars(captures[i]), text_ucs.Pos_bytes_to_chars(captures[i + 1])));
			}
		}
		Regx_match rv = new Regx_match(found, find_bgn, find_end, groups);		
		return new Regx_match[] {rv};
			}
}
