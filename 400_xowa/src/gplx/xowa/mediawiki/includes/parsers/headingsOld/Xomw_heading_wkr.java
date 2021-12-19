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
package gplx.xowa.mediawiki.includes.parsers.headingsOld;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
public class Xomw_heading_wkr {
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
	public void Parse(byte[] src, int src_bgn, int src_end, Xomw_heading_cbk cbk) {	// REF.MW: /includes/parser/Parser.php|doHeadings
		// init members
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
		this.txt_bgn = pos == -1 ? 0 : pos;
		byte b = AsciiByte.Nl;
		while (true) {
			int nxt = pos + 1;
			// check if (a) cur is \n; (b) nxt is '='
			if (	b == AsciiByte.Nl
				&&	nxt < src_end
				&&	src[nxt] == AsciiByte.Eq
				) {
				pos = Parse_hdr_nl(txt_bgn, pos, nxt + 1);
				this.txt_bgn = pos;
			}
			else
				++pos;

			// EOS; add all text after last "==\n"
			if (pos == src_end) {
				cbk.On_src_done(this);
				break;
			}
			b = src[pos];
		}
	}
	private int Parse_hdr_nl(int txt_bgn, int nl_lhs, int pos) {
		// calc lhs vars
		this.hdr_bgn = nl_lhs;
		this.hdr_lhs_bgn = nl_lhs == 0 ? 0 : nl_lhs + 1;	// set pos of 1st "="; note that "==" can be at BOS;
		this.hdr_lhs_end = BryFind.FindFwdWhile(src, pos, src_end, AsciiByte.Eq);

		// calc rhs vars
		int nl_rhs = BryFind.FindFwdOr(src, AsciiByte.Nl, hdr_lhs_end + 1, src_end, src_end);	// if no "\n", src_end is rest of text; EX: "\n==<text>EOS
		this.hdr_end = nl_rhs;
		this.hdr_rhs_end = BryFind.FindBwdSkipWs(src, nl_rhs, hdr_lhs_end);
		this.hdr_rhs_bgn = BryFind.FindBwdSkip(src, hdr_rhs_end - 1, hdr_lhs_end, AsciiByte.Eq);

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

		cbk.On_hdr_seen(this);
		return nl_rhs;
	}
}
