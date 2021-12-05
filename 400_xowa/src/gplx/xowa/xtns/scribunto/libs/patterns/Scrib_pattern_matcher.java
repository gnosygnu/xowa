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
package gplx.xowa.xtns.scribunto.libs.patterns;
import gplx.Keyval;
import gplx.langs.regxs.Regx_match;
import gplx.objects.primitives.BoolUtl;
import gplx.objects.strings.unicodes.Ustring;
import gplx.xowa.xtns.scribunto.libs.Scrib_lib_ustring_gsub_mgr;
import gplx.xowa.xtns.scribunto.libs.Scrib_regx_converter;
public abstract class Scrib_pattern_matcher {
	protected final Scrib_regx_converter regx_converter = new Scrib_regx_converter();
	public Keyval[] Capt_ary() {return regx_converter.Capt_ary();}
	public abstract Regx_match Match_one(Ustring src_ucs, String pat_str, int bgn_as_codes, boolean replace);
	public abstract String Gsub(Scrib_lib_ustring_gsub_mgr gsub_mgr, Ustring src_ucs, String pat_str, int bgn_as_codes);

	public static boolean Mode_is_xowa() {return BoolUtl.Y;}
	public static Scrib_pattern_matcher New(byte[] page_url) {
		return Mode_is_xowa()
			? (Scrib_pattern_matcher)new Scrib_pattern_matcher__xowa(page_url)
			: (Scrib_pattern_matcher)new Scrib_pattern_matcher__regx(page_url)
			;
	}
}
