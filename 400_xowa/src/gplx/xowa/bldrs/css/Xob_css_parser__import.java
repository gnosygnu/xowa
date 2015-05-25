/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.xowa.files.downloads.*;
class Xob_css_parser__import {
//		// "//id.wikibooks.org/w/index.php?title=MediaWiki:Common.css&oldid=43393&action=raw&ctype=text/css";
	private final Xob_css_parser__url url_parser;
	public Xob_css_parser__import(Xob_css_parser__url url_parser) {this.url_parser = url_parser;}
	public Xob_css_tkn__base Parse(byte[] src, int src_len, int tkn_bgn, int tkn_end) {	// " @import"
		int bgn_pos = Bry_finder.Find_fwd_while_ws(src, tkn_end, src_len);	// skip any ws after " @import"
		if (bgn_pos == src_len) return Xob_css_tkn__warn.new_(tkn_bgn, tkn_end, "mirror.parser.import:EOS after import; bgn=~{0}", tkn_bgn);
		if (!Bry_.HasAtBgn(src, Tkn_url_bry, bgn_pos, src_len)) return Xob_css_tkn__warn.new_(tkn_bgn, tkn_end, "mirror.parser.import:url missing; bgn=~{0}", tkn_bgn);
		tkn_end = bgn_pos + Tkn_url_bry.length;
		Xob_css_tkn__base frag = url_parser.Parse(src, src_len, bgn_pos, tkn_end);
		if (frag.Tid() != Xob_css_tkn__url.Tid_url) return Xob_css_tkn__warn.new_(tkn_bgn, frag.Pos_end(), "mirror.parser.import:url invalid; bgn=~{0}", tkn_bgn);
		Xob_css_tkn__url url_frag = (Xob_css_tkn__url)frag;
		byte[] src_url = url_frag.Src_url();
		src_url = Bry_.Replace(src_url, Byte_ascii.Space, Byte_ascii.Underline);	// NOTE: must replace spaces with underlines else download will fail; EX:https://it.wikivoyage.org/w/index.php?title=MediaWiki:Container e Infobox.css&action=raw&ctype=text/css; DATE:2015-03-08
		int semic_pos = Bry_finder.Find_fwd(src, Byte_ascii.Semic, frag.Pos_end(), src_len);
		return Xob_css_tkn__import.new_(tkn_bgn, semic_pos + 1, src_url, url_frag.Trg_url(), url_frag.Quote_byte());
	}
	private static final byte[] Tkn_url_bry = Bry_.new_a7("url(");
	public static final byte[]
	  Wikisource_dynimg_ttl		= Bry_.new_a7("en.wikisource.org/w/index.php?title=MediaWiki:Dynimg.css")
	, Wikisource_dynimg_find	= Bry_.new_a7(".freedImg img[src*=\"wikipedia\"], .freedImg img[src*=\"wikisource\"], .freedImg img[src*=\"score\"], .freedImg img[src*=\"math\"] {")
	, Wikisource_dynimg_repl	= Bry_.new_a7(".freedImg img[src*=\"wikipedia\"], .freedImg img[src*=\"wikisource\"], /*XOWA:handle file:// paths which will have /commons.wikimedia.org/ but not /wikipedia/ */ .freedImg img[src*=\"wikimedia\"], .freedImg img[src*=\"score\"], .freedImg img[src*=\"math\"] {")
	;
}
