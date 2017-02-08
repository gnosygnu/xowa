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
package gplx.xowa.mediawiki.includes.parsers.headings; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import gplx.core.btries.*; import gplx.xowa.langs.*;
public class Xomw_heading_wkr {
	private Xomw_parser_ctx pctx;
	private Xomw_heading_cbk cbk;
	public byte[] Src()			{return src;} private byte[] src;
	public int Src_end()		{return src_end;} private int src_end;
	public int Txt_bgn()		{return txt_bgn;} private int txt_bgn;
	public int Hdr_bgn()		{return hdr_bgn;} private int hdr_bgn;
	public int Hdr_end()		{return hdr_end;} private int hdr_end;
	public int Hdr_num()		{return hdr_num;} private int hdr_num;
	public int Hdr_lhs_bgn()	{return hdr_lhs_bgn;} private int hdr_lhs_bgn;
	public int Hdr_lhs_end()	{return hdr_lhs_end;} private int hdr_lhs_end;
	public int Hdr_rhs_bgn()	{return hdr_rhs_bgn;} private int hdr_rhs_bgn;
	public int Hdr_rhs_end()	{return hdr_rhs_end;} private int hdr_rhs_end;
	public void Do_headings(Xomw_parser_ctx pctx, Xomw_parser_bfr pbfr, Xomw_heading_cbk__html cbk) {
		Bry_bfr src_bfr = pbfr.Src();
		byte[] src_bry = src_bfr.Bfr();
		int src_end = src_bfr.Len();
		cbk.Bfr_(pbfr.Trg());
		pbfr.Switch();
		Parse(pctx, src_bry, 0, src_end, cbk);
	}
	public void Parse(Xomw_parser_ctx pctx, byte[] src, int src_bgn, int src_end, Xomw_heading_cbk cbk) {	// REF.MW: /includes/parser/Parser.php|doHeadings
		// init members
		this.pctx = pctx;
		this.src = src;
		this.src_end = src_end;
		this.cbk = cbk;

		// PORTED:
		// for ( $i = 6; $i >= 1; --$i ) {
		//   $h = str_repeat( '=', $i );
		//   $text = preg_replace( "/^$h(.+)$h\\s*$/m", "<h$i>\\1</h$i>", $text );
		// }

		// do loop
		int pos = src_bgn;
		this.txt_bgn = pos == Xomw_parser_ctx.Pos__bos ? 0 : pos;
		byte b = Byte_ascii.Nl;
		while (true) {
			int nxt = pos + 1;
			// check if (a) cur is \n; (b) nxt is '='
			if (	b == Byte_ascii.Nl
				&&	nxt < src_end
				&&	src[nxt] == Byte_ascii.Eq
				) {
				pos = Parse_hdr_nl(txt_bgn, pos, nxt + 1);
				this.txt_bgn = pos;
			}
			else
				++pos;

			// EOS; add all text after last "==\n"
			if (pos == src_end) {
				cbk.On_src_done(pctx, this);
				break;
			}
			b = src[pos];
		}
	}
	private int Parse_hdr_nl(int txt_bgn, int nl_lhs, int pos) {
		// calc lhs vars
		this.hdr_bgn = nl_lhs;
		this.hdr_lhs_bgn = nl_lhs == 0 ? 0 : nl_lhs + 1;	// set pos of 1st "="; note that "==" can be at BOS;
		this.hdr_lhs_end = Bry_find_.Find_fwd_while(src, pos, src_end, Byte_ascii.Eq);

		// calc rhs vars
		int nl_rhs = Bry_find_.Find_fwd_or(src, Byte_ascii.Nl, hdr_lhs_end + 1, src_end, src_end);	// if no "\n", src_end is rest of text; EX: "\n==<text>EOS
		this.hdr_end = nl_rhs;
		this.hdr_rhs_end = Bry_find_.Find_bwd__skip_ws(src, nl_rhs, hdr_lhs_end);
		this.hdr_rhs_bgn = Bry_find_.Find_bwd__skip(src, hdr_rhs_end - 1, hdr_lhs_end, Byte_ascii.Eq);

		int hdr_lhs_len = hdr_lhs_end - hdr_lhs_bgn;
		int hdr_rhs_len = hdr_rhs_end - hdr_rhs_bgn;

		// handle rare situations like "\n====\n"
		if (hdr_rhs_len == 0) {
			int hdr_lhs_len_half = hdr_lhs_len / 2;
			hdr_rhs_len = hdr_lhs_len - hdr_lhs_len_half;
			hdr_lhs_len = hdr_lhs_len_half;
			this.hdr_lhs_end = hdr_lhs_bgn + hdr_lhs_len;
			this.hdr_rhs_bgn = hdr_lhs_end;
		}

		this.hdr_num = hdr_lhs_len < hdr_rhs_len ? hdr_lhs_len : hdr_rhs_len;

		cbk.On_hdr_seen(pctx, this);
		return nl_rhs;
	}
}
