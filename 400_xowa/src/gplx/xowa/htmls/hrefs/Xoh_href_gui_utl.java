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
package gplx.xowa.htmls.hrefs;
import gplx.core.btries.Btrie_slim_mgr;
import gplx.gfui.kits.swts.Swt_html_utl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.wrappers.ByteVal;
import gplx.types.errs.ErrUtl;
public class Xoh_href_gui_utl {
	public static String Html_extract_text(String site, String page, String text_str) {
		byte[] text_bry = BryUtl.NewU8(text_str);
		int text_len = text_bry.length;
		int text_tid = AsciiByte.ToA7Int(text_bry[0]);
		switch (text_tid) {
			case Text_tid_none: return "";	// "0"
			case Text_tid_text: return StringUtl.NewU8(text_bry, 2, text_len);	// 2 to skip "1|"
			case Text_tid_href: break;	// fall through to below
			default:			throw ErrUtl.NewUnhandled(text_tid);
		}
		String href_str = StringUtl.Mid(StringUtl.NewU8(text_bry), 2);
		href_str = Swt_html_utl.NormalizeSwtUrl(href_str);
		if (StringUtl.HasAtBgn(href_str, Xoh_href_.Str__file))
			href_str = Standardize_xowa_link(href_str);	// skip "file://"
		ByteVal href_tid = (ByteVal)href_trie.MatchBgn(BryUtl.NewU8(href_str), 0, href_str.length());
		if (href_tid != null) {
			switch (href_tid.Val()) {
				case Href_tid_wiki:			return site + href_str;
				case Href_tid_site:			return StringUtl.Mid(href_str, 6);			// +6 to skip "site/"
				case Href_tid_anch:			return site + "/wiki/" + page + href_str;
			}
		}
		return href_str;
	}
	public static String Standardize_xowa_link(String str) {
		byte[] bry = BryUtl.NewU8(str);
		int skip = Skip_start_of_xowa_link(bry, bry.length, 0);
		return skip == 0 ? str : StringUtl.Mid(str, skip);
	}
	private static int Skip_start_of_xowa_link(byte[] src, int src_len, int bgn) {
		if (!BryUtl.HasAtBgn(src, Xoh_href_.Bry__file, bgn, src_len)) return bgn;	// does not start with "file://"
		int pos = bgn + Xoh_href_.Len__file;	// skip "file://"
		Object tid_obj = href_trie.MatchBgn(src, pos, src_len);
		if (tid_obj == null) {
			if (src_len - pos > 0 && src[pos] == AsciiByte.Slash) { // handle "file:///C:/dir/fil.png"
				return pos + 1;
			}
			else {
				return bgn; // if not a known xowa link, return original bgn;
			}
		}
		switch (((ByteVal)tid_obj).Val()) {
			case Href_tid_site:			return pos;
			case Href_tid_wiki:			return pos;
			case Href_tid_anch:			return pos;
			default:					throw ErrUtl.NewUnhandled(tid_obj);
		}
	}
	private static final byte Text_tid_none = 0, Text_tid_text = 1, Text_tid_href = 2;
	private static final byte Href_tid_wiki = 1, Href_tid_site = 2, Href_tid_anch = 3;
	private static final Btrie_slim_mgr href_trie = Btrie_slim_mgr.cs()
	.Add_bry_byte(Xoh_href_.Bry__site		, Href_tid_site)
	.Add_bry_byte(Xoh_href_.Bry__wiki		, Href_tid_wiki)
	.Add_bry_byte(Xoh_href_.Bry__anch		, Href_tid_anch)
	;
}
/*
NOTE_1:
. swt/mozilla treats text differently in href="{text}" when content_editable=n; occurs in LocationListener.changing
http://a.org						-> http://a.org								does nothing
A									-> file:///A								adds "file:///"
/wiki/A								-> file:///wiki/A							adds "file://"
Category:A							-> Category:A								noops; Category is assumed to be protocol?
//en.wiktionary.org/wiki/a			-> file:///wiki/a							strips out site name and prepends "file://"; no idea why

. so, to handle the above, the code does the following
http://a.org						-> http://a.org								does nothing; nothing needed
A									-> /wiki/A									always prepend /wiki/
Category:A							-> /wiki/Category:A							always prepend /wiki/
//en.wiktionary.org/wiki/A			-> /site/en.wiktionary.org/wiki/A			always transform relative url to /site/

. the href will still come here as file:///wiki/A or file:///site/en.wiktionary.org/wiki/A.
. however, the file:// can be lopped off and discarded and the rest of the href will fall into one of the following cases
.. /wiki/
.. /site/
.. /xcmd/
.. #
.. anything else -> assume to be really a file:// url; EX: file://C/dir/fil.txt -> C/dir/fil.txt
. the other advantage of this approach is that this proc can be reused outside of swt calls; i.e.: it can parse both "file:///wiki/A" and "/wiki/A"
*/
