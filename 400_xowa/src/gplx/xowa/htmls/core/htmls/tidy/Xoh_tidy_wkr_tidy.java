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
package gplx.xowa.htmls.core.htmls.tidy; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.htmls.*;
import gplx.core.envs.*;
public class Xoh_tidy_wkr_tidy extends Process_adp implements Xoh_tidy_wkr { 	private Xoae_app app; private Io_url tidy_source, tidy_target;
	public byte Tid() {return Xoh_tidy_wkr_.Tid_tidy;}
	public void Init_by_app(Xoae_app app) {
		this.app = app;
	}
	@Override public Process_adp Tmp_dir_(Io_url v) {
		tidy_source = v.GenSubFil("tidy_source.html");
		tidy_target = v.GenSubFil("tidy_target.html");
		return super.Tmp_dir_(v);
	}
	public void Indent_(boolean v) {Indent_val = v ? "y" : "n";}
	public void Exec_tidy(Bry_bfr bfr, byte[] page_url) {
		int bfr_len = bfr.Len();
		long bgn = System_.Ticks();
		Io_mgr.Instance.SaveFilBfr(tidy_source, bfr);			// saves bfr to source; clears bfr
		this.Run(tidy_source.Raw(), tidy_target.Raw());			// converts source to target
		Io_mgr.Instance.LoadFilBryByBfr(tidy_target, bfr);		// loads bfr by target
		if (bfr.Len_eq_0())										// something went wrong; load from source
			Io_mgr.Instance.LoadFilBryByBfr(tidy_source, bfr);	// loads bfr by target
		app.Usr_dlg().Log_many("", "", "tidy exec; elapsed=~{0} len=~{1}", System_.Ticks__elapsed_in_frac(bgn), bfr_len);
	}
	private static String Indent_val = "y";
	public static String Args_fmt = String_.Concat	// see https://meta.wikimedia.org/wiki/Data_dumps; missing numeric-entities:yes; enclose-text: yes
	(	"-utf8"							// default is ascii
	,	" --force-output y"				// always generate output; do not fail on error
	,	" --quiet y"					// suppress command-line header
	,	" --tidy-mark n"				// do not add tidy watermark
	,	" --doctype ''''"				// set to empty else some wikis will show paragraph text with little vertical gap; PAGE:tr.b:
	,	" --wrap 0"						// default is 80; do not limit lines to 80 chars
	,	" --indent ", Indent_val		// indent block levels
	,	" --quote-nbsp y"				// preserve nbsp as entities; do not convert to Unicode character 160
	,	" --literal-attributes y"		// do not alter whitespace chars in attributes
	,	" --wrap-attributes n"			// do not line-wrap attribute values (assume tidy will try to take a="b\nc" and change to a="b c" which may cause some fidelity issues?)
	,	" --fix-uri n"					// do not escape invalid chars in uris
	,	" --fix-backslash n"			// do not change \ to / in URLs
	,	" --enclose-block-text y"		// always enclose text in element with <p>
//		,	" --show-body-only y"			// prevent tidy from surrounding input with <html><body>	// removed; strips <style> tags in body, and places them in <head> which is not outputted; DATE:2014-03-09
//		,	" --output-xhtml y"				// output as xhtml (p's and li's will have closing tags)	// removed; creates unsightly <!--CDATA fragments in head; DATE:2014-03-09
	,	" -o \"~{target}\""				// target file
	,	" \"~{source}\""				// source file
	);
}
