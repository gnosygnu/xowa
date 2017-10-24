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
package gplx.xowa.htmls.core.wkrs.lnkis.anchs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.lnkis.*;
import gplx.core.brys.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.ttls.*;
public class Xoh_anch_capt_itm {
	public int		Cs0_tid() {return cs0_tid;} private int cs0_tid;
	public int		Split_pos() {return split_pos;} private int split_pos;
	public byte Parse(Bry_rdr owner_rdr, boolean ns_name_exists, byte[] href_bry, int href_bgn, int href_end, byte[] capt_bry, int capt_bgn, int capt_end) {
		this.cs0_tid = Cs0__exact; this.split_pos = -1; 
		// do compare
		int href_len = href_end - href_bgn;
		int capt_len = capt_end - capt_bgn;
		for (int i = 0; i < capt_len; ++i) {
			if (i == href_len) {							// ran out of href; mark as trail; EX: [[A]]s -> href="A"; capt="As"
				split_pos = i + capt_bgn;
				return Tid__more;
			}
			byte href_byte = href_bry[i + href_bgn];
			byte capt_byte = capt_bry[i + capt_bgn];
			if (i == 0) {									// ignore case if 1st letter and ns is Tid__1st; EX: [[earth]] -> href="Earth"; capt="earth"
				if (	capt_byte == href_byte) {
					cs0_tid = Cs0__exact;
					continue;
				}
				else if(href_byte >= Byte_ascii.Ltr_A && href_byte <= Byte_ascii.Ltr_Z
					&&	capt_byte - href_byte == 32
					) {
					cs0_tid = Cs0__lower;
					continue;
				}
				else if(href_byte >= Byte_ascii.Ltr_a && href_byte <= Byte_ascii.Ltr_z
					&&	href_byte - capt_byte == 32
					) {
					cs0_tid = Cs0__upper;
					continue;
				}
			}
			else
				if (href_byte == capt_byte) continue;
			if (	capt_byte == Byte_ascii.Space			// ignore " " vs "_"
				&&	href_byte == Byte_ascii.Underline
				)
				continue;
			this.cs0_tid = Cs0__exact;
			return Tid__diff;								// bytes still diff; return diff
		}
		if (capt_len == href_len)							// all bytes same and capt_len == href_len; must be same
			return Tid__same;// : Tid__href_pipe;
		else {												// capt < href; EX: [[A_(b)|A]] -> href="A_(b)"; capt = "A"
			split_pos = capt_len + href_bgn;
			return Tid__less;
		}
	}
	public static final byte // SERIALIAZED
	  Tid__same				= 0	// [[A]]			->  "A|A"		-> "A|"
	, Tid__diff				= 1	// [[A|b]]			->	"A|b"		-> "A|b"
	, Tid__more				= 2	// [[A]]s			->	"A|As"		-> "A|s"
	, Tid__less				= 3	// [[A_(b)|A]]		->	"A_(b)|A"	-> "A|_(b)"
//		, Tid__href_pipe		= 4	// [[Help:A|]]		->	"Help:A|A"	-> "A|"
	;
	public static final int // SERIALIAZED
	  Cs0__exact			= 0
	, Cs0__lower			= 1	// [[A|a]]			-> "A|a"		-> "A"
	, Cs0__upper			= 2	// [[a|A]]			-> "a|A"		-> "a"
	;
}
