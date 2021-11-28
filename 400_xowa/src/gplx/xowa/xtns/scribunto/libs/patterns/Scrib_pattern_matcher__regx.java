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
import gplx.objects.strings.unicodes.*;
import gplx.langs.regxs.*;
class Scrib_pattern_matcher__regx extends Scrib_pattern_matcher { 	private final byte[] page_url;
	public Scrib_pattern_matcher__regx(byte[] page_url) {
		this.page_url = page_url;
	}
	@Override public Regx_match Match_one(Ustring src_ucs, String pat_str, int bgn_as_codes, boolean replace) {
		// convert lua pattern to java regex
		if (replace) // note that replace will be false for Gmatch_callback (b/c Gmatch_init already converted)
			pat_str = regx_converter.patternToRegex(pat_str, Scrib_regx_converter.Anchor_G, true);

		// run regex
		Regx_adp regx_adp = Scrib_lib_ustring.RegxAdp_new_(page_url, pat_str);
		Regx_match match = regx_adp.Match(src_ucs.Src(), src_ucs.Map_data_to_char(bgn_as_codes));	// NOTE: MW calculates an offset to handle mb strings. however, java's regex always takes offset in chars (not bytes like PHP preg_match); DATE:2014-03-04
		match = regx_converter.Adjust_balanced_one(match);
		return match;
	}
	@Override public String Gsub(Scrib_lib_ustring_gsub_mgr gsub_mgr, Ustring src_ucs, String pat_str, int bgn_as_codes) {
		// convert lua pattern to java regex
		pat_str = regx_converter.patternToRegex(pat_str, Scrib_regx_converter.Anchor_pow, true);
		String src_str = src_ucs.Src();
		Regx_adp regx_adp = Scrib_lib_ustring.RegxAdp_new_(page_url, pat_str);
		if (regx_adp.Pattern_is_invalid()) return src_str; // NOTE: invalid patterns should return self; EX:[^]; DATE:2014-09-02

		// run regex
		Regx_match[] rslts =  regx_adp.Match_all(src_str, src_ucs.Map_data_to_char(bgn_as_codes));	// NOTE: MW calculates an offset to handle mb strings. however, java's regex always takes offset in chars (not bytes like PHP preg_match); DATE:2014-03-04
		if (rslts.length == 0) return src_str; // PHP: If matches are found, the new subject will be returned, otherwise subject will be returned unchanged.; http://php.net/manual/en/function.preg-replace-callback.php
		rslts = regx_converter.Adjust_balanced(rslts);

		// replace results
		Bry_bfr tmp_bfr = Bry_bfr_.New();
		int rslts_len = rslts.length;
		int text_pos = 0;
		for (int i = 0; i < rslts_len; i++) {
			if (gsub_mgr.Repl_count__done()) break; // stop if repl_count reaches limit; note that limit = -1 by default, unless specified

			// add text up to find.bgn
			Regx_match rslt = rslts[i];
			tmp_bfr.Add_str_u8(String_.Mid(src_str, text_pos, rslt.Find_bgn()));	// NOTE: regx returns char text_pos (not bry); must add as String, not bry; DATE:2013-07-17
			
			// replace result
			if (!gsub_mgr.Exec_repl_itm(tmp_bfr, regx_converter, rslt)) {
				// will be false when gsub_proc returns nothing; PAGE:en.d:tracer PAGE:en.d:שלום DATE:2017-04-22;
				tmp_bfr.Add_str_u8(String_.Mid(src_str, rslt.Find_bgn(), rslt.Find_end()));
			}

			// update
			text_pos = rslt.Find_end();
			gsub_mgr.Repl_count__add();
		}

		// add rest of String
		int text_len = String_.Len(src_str);
		if (text_pos < text_len)
			tmp_bfr.Add_str_u8(String_.Mid(src_str, text_pos, text_len));			// NOTE: regx returns char text_pos (not bry); must add as String, not bry; DATE:2013-07-17
		return tmp_bfr.To_str_and_clear();
	}
}
