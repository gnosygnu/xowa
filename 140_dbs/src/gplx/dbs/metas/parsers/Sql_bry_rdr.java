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
package gplx.dbs.metas.parsers; import gplx.*;
import gplx.core.brys.*;
import gplx.objects.strings.AsciiByte;
public class Sql_bry_rdr extends Bry_rdr {		public byte[] Read_sql_identifier() {
		this.Skip_ws();
		int bgn = pos, end = -1;
		if (pos == src_end) return null;
		if (src[pos] == AsciiByte.BrackBgn) {	// EX: [name with space]
			bgn = ++pos;	// set bgn after [
			end = this.Find_fwd_lr(AsciiByte.BrackEnd);
		}
		else {
			this.Skip_alpha_num_under();	// ASSUME: identifier is ASCII and alpha / num / underscore
			if (pos == bgn) return null;	// String is not identifier; EX: "!@#"
			end = pos;
		}
		return Bry_.Mid(src, bgn, end);
	}
	@Override public Bry_rdr Skip_ws() {
		byte b_0 = pos < src_end ? src[pos] : AsciiByte.Null;
		byte bgn_1 = AsciiByte.Null;
		byte[] end_bry = null;
		switch (b_0) {
			case AsciiByte.Dash:	bgn_1 = AsciiByte.Dash;		end_bry = Comm_end_line; break;
			case AsciiByte.Slash:	bgn_1 = AsciiByte.Star;	end_bry = Comm_end_multi; break;
			case AsciiByte.Tab: case AsciiByte.Nl: case AsciiByte.Cr: case AsciiByte.Space:
				++pos;
				return super.Skip_ws();
			default:
				return this;					
		}
		byte b_1 = pos + 1 < src_end ? src[pos + 1] : AsciiByte.Null;
		if (b_1 != bgn_1) return this;
		int end_pos = Bry_find_.Find_fwd(src, end_bry, pos + 2, src_end);
		if (end_pos == Bry_find_.Not_found) return this;
		pos = end_pos + end_bry.length;
		return super.Skip_ws();
	}
	private static final byte[] Comm_end_line = AsciiByte.NlBry, Comm_end_multi = Bry_.new_a7("*/");
}
