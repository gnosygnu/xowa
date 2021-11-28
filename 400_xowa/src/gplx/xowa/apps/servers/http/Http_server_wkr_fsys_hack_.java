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
package gplx.xowa.apps.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
import gplx.core.btries.*;
class Http_server_wkr_fsys_hack_ {
	private static final byte[]
	  Bry__file_lhs = Bry_.new_a7("file:///")
	, Bry__file_fsys = Bry_.new_a7("/fsys")
	;
	private static final Btrie_slim_mgr xowa_path_segment_trie = Btrie_slim_mgr.ci_a7()
		.Add_many_str
		( "/file/"              // most files     EX: file:////home/lnxusr/xowa/file/en.wikipedia.org/7/0/A.png
		, "/user/anonymous/"    // wiki css;      EX: url(file:///c:/xowa/user/anonymous/wiki/www.wikidata.org/html/logo.png)
		, "/bin/any/"           // app resources; EX: file:///c:/xowa/bin/any/xowa/file/app.window/app_icon.png
		)
		;
	public static byte[] Replace(byte[] html_bry) {
		// init
		Bry_bfr bfr = Bry_bfr_.New();
		int len = html_bry.length;
		int pos = 0;

		// loop while finding root_dir_http
		while (true) {
			// find "file:///"
			int file_bgn = Bry_find_.Find_fwd(html_bry, Bry__file_lhs, pos);

			// exit if nothing found
			if (file_bgn == Bry_find_.Not_found)
				break;

			// set file_end (after "file:///")
			int file_end = file_bgn + Bry__file_lhs.length;

			// skip if page literally starts with "file:"
			if (file_bgn == 0) {
				bfr.Add_mid(html_bry, pos, file_end);
				pos = file_end;
				continue;
			}

			// get quote_bgn char before "file:///"
			int quote_bgn = file_bgn - 1;
			byte quote_bgn_char = html_bry[quote_bgn];
			byte quote_end_char = Byte_ascii.Null;
			boolean quote_is_ws = false;

			// get quote_end char
			switch (quote_bgn_char) {
				case Byte_ascii.Apos:      // EX: 'file:///...'
				case Byte_ascii.Quote:     // EX: "file:///..."
					quote_end_char = quote_bgn_char;
					break;
				case Byte_ascii.Paren_bgn: // EX: url(file:///...)
					quote_end_char = Byte_ascii.Paren_end;
					break;
				case Byte_ascii.Space:     // EX: '\sfile:///...\n'; NOTE: don't know if this is needed, but just in case it's in TemplateStyles.css
				case Byte_ascii.Tab:
				case Byte_ascii.Nl:
				case Byte_ascii.Cr:
					quote_is_ws = true;
					quote_end_char = quote_bgn_char;
					break;
				default:
					break;
			}

			// preceding char is not supported; exit; EX: '!file:///'
			if (quote_end_char == Byte_ascii.Null) {
				bfr.Add_mid(html_bry, pos, file_end);
				pos = file_end;
				continue;
			}

			// find quote_end
			int quote_end =
				quote_is_ws
				? Bry_find_.Find_fwd_until_ws(html_bry, file_end, len)
				: Bry_find_.Find_fwd         (html_bry, quote_end_char, file_end);

			// exit if no quote_end
			if (quote_end == Bry_find_.Not_found) 
				break;

			// skip if "'file: ... '" is too long. should be no more than 300
			if (quote_end - file_end > 300) {
				bfr.Add_mid(html_bry, pos, quote_end);
				pos = quote_end;
				continue;
			}

			// loop chars between quote_bgn and quote_end and must have one of segments defined above; "/file/", "/bin/any/", "/user/anonymous/"
			int mid_bgn = Bry_find_.Not_found;
			Btrie_rv trv = new Btrie_rv();
			for (int i = quote_bgn; i < quote_end; i++) {
				byte b = html_bry[i];
				Object o = xowa_path_segment_trie.Match_at_w_b0(trv, b, html_bry, i, quote_end);
				if (o != null) {
					mid_bgn = i;
					break;
				}
			}

			// skip if no xowa_path_segment found
			if (mid_bgn == -1) {
				bfr.Add_mid(html_bry, pos, quote_end);
				pos = quote_end;
				continue;
			}

			// add everything up to xowa_path_segment
			bfr.Add_mid(html_bry, pos, file_bgn);

			// add "/fsys/"
			bfr.Add(Bry__file_fsys);

			// add everything up to quote_end
			bfr.Add_mid(html_bry, mid_bgn, quote_end);

			// move pos forward
			pos = quote_end;
		}

		// add rest
		bfr.Add_mid(html_bry, pos, len);
		return bfr.To_bry_and_clear();
	}
}
