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
package gplx.xowa.apps.servers.http;
import gplx.core.btries.*;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
class Http_server_wkr_fsys_hack_ {
	private static final byte[]
	  Bry__file_lhs = BryUtl.NewA7("file:///")
	, Bry__file_fsys = BryUtl.NewA7("/fsys")
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
		BryWtr bfr = BryWtr.New();
		int len = html_bry.length;
		int pos = 0;

		// loop while finding root_dir_http
		while (true) {
			// find "file:///"
			int file_bgn = BryFind.FindFwd(html_bry, Bry__file_lhs, pos);

			// exit if nothing found
			if (file_bgn == BryFind.NotFound)
				break;

			// set file_end (after "file:///")
			int file_end = file_bgn + Bry__file_lhs.length;

			// skip if page literally starts with "file:"
			if (file_bgn == 0) {
				bfr.AddMid(html_bry, pos, file_end);
				pos = file_end;
				continue;
			}

			// get quote_bgn char before "file:///"
			int quote_bgn = file_bgn - 1;
			byte quote_bgn_char = html_bry[quote_bgn];
			byte quote_end_char = AsciiByte.Null;
			boolean quote_is_ws = false;

			// get quote_end char
			switch (quote_bgn_char) {
				case AsciiByte.Apos:      // EX: 'file:///...'
				case AsciiByte.Quote:     // EX: "file:///..."
					quote_end_char = quote_bgn_char;
					break;
				case AsciiByte.ParenBgn: // EX: url(file:///...)
					quote_end_char = AsciiByte.ParenEnd;
					break;
				case AsciiByte.Space:     // EX: '\sfile:///...\n'; NOTE: don't know if this is needed, but just in case it's in TemplateStyles.css
				case AsciiByte.Tab:
				case AsciiByte.Nl:
				case AsciiByte.Cr:
					quote_is_ws = true;
					quote_end_char = quote_bgn_char;
					break;
				default:
					break;
			}

			// preceding char is not supported; exit; EX: '!file:///'
			if (quote_end_char == AsciiByte.Null) {
				bfr.AddMid(html_bry, pos, file_end);
				pos = file_end;
				continue;
			}

			// find quote_end
			int quote_end =
				quote_is_ws
				? BryFind.FindFwdUntilWs(html_bry, file_end, len)
				: BryFind.FindFwd(html_bry, quote_end_char, file_end);

			// exit if no quote_end
			if (quote_end == BryFind.NotFound)
				break;

			// skip if "'file: ... '" is too long. should be no more than 300
			if (quote_end - file_end > 300) {
				bfr.AddMid(html_bry, pos, quote_end);
				pos = quote_end;
				continue;
			}

			// loop chars between quote_bgn and quote_end and must have one of segments defined above; "/file/", "/bin/any/", "/user/anonymous/"
			int mid_bgn = BryFind.NotFound;
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
				bfr.AddMid(html_bry, pos, quote_end);
				pos = quote_end;
				continue;
			}

			// add everything up to xowa_path_segment
			bfr.AddMid(html_bry, pos, file_bgn);

			// add "/fsys/"
			bfr.Add(Bry__file_fsys);

			// add everything up to quote_end
			bfr.AddMid(html_bry, mid_bgn, quote_end);

			// move pos forward
			pos = quote_end;
		}

		// add rest
		bfr.AddMid(html_bry, pos, len);
		return bfr.ToBryAndClear();
	}
}
