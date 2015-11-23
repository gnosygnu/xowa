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
package gplx.xowa.htmls.core.wkrs.lnkis.anchs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import gplx.core.brys.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.ttls.*;
public class Xoh_anch_capt_parser {
	public int Split_pos() {return split_pos;} private int split_pos;
	public byte Parse(Bry_rdr owner_rdr, boolean capt_bgn_has_ns, boolean cs_tid_1st, byte[] href_bry, int href_bgn, int href_end, byte[] capt_bry, int capt_bgn, int capt_end) {
		this.split_pos = -1;
		int href_len = href_end - href_bgn;
		int capt_len = capt_end - capt_bgn;
		for (int i = 0; i < capt_len; ++i) {
			if (i == href_len) {							// ran out of href; mark as trail; EX: [[A]]s -> href="A"; capt="As"
				split_pos = i + capt_bgn;
				return Tid__href_trail;
			}
			byte href_byte = href_bry[i + href_bgn];
			byte capt_byte = capt_bry[i + capt_bgn];
			if (href_byte == capt_byte) continue;
			if (	i == 0									// ignore case if 1st letter and ns is Tid__1st; EX: [[earth]] -> href="Earth"; capt="earth"
				&&	cs_tid_1st
				&&	capt_byte >= Byte_ascii.Ltr_a && capt_byte <= Byte_ascii.Ltr_z
				&&	(capt_byte - href_byte) == 32
				)
				continue;
			if (	capt_byte == Byte_ascii.Space			// ignore " " vs "_"
				&&	href_byte == Byte_ascii.Underline
				)
				continue;
			return Tid__capt;								// bytes still diff; return diff
		}
		if (capt_len == href_len)							// all bytes same and capt_len == href_len; must be same
			return capt_bgn_has_ns ? Tid__href : Tid__href_pipe;
		else {												// capt < href; EX: [[A_(b)|A]] -> href="A_(b)"; capt = "A"
			split_pos = capt_len + href_bgn;
			return Tid__capt_short;
		}
	}
	public static final byte // SERIALIAZED
	  Tid__href				= 0	// [[A]]		->  "A|A"		-> "A|"
	, Tid__capt				= 1	// [[A|b]]		->	"A|b"		-> "A|b"
	, Tid__href_trail		= 2	// [[A]]s		->	"A|As"		-> "A|s"
	, Tid__capt_short		= 3	// [[A_(b)|A]]	->	"A_(b)|A"	-> "A|_(b)"
	, Tid__href_pipe		= 4	// [[Help:A|]]	->	"Help:A|A"	-> "A|"
	;
}
