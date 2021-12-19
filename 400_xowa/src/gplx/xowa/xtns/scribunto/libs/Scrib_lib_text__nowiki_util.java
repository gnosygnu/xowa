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
package gplx.xowa.xtns.scribunto.libs;
import gplx.core.btries.*;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.wrappers.BoolVal;
public class Scrib_lib_text__nowiki_util {
	public Btrie_slim_mgr Make_trie(byte[] tag) {
		BryWtr tmp = BryWtr.New();
		Btrie_slim_mgr rv = Btrie_slim_mgr.ci_u8();
		byte[] lhs_bry = tmp.AddBryMany(AsciiByte.AngleBgnBry, tag, AsciiByte.AngleEndBry).ToBryAndClear();
		byte[] rhs_bry = tmp.AddBryMany(AsciiByte.AngleBgnBry, AsciiByte.SlashBry, tag, AsciiByte.AngleEndBry).ToBryAndClear();
		rv.AddObj(lhs_bry, BoolVal.True);
		rv.AddObj(rhs_bry, BoolVal.False);
		return rv;
	}
	public byte[] Strip_tag(byte[] page, byte[] src, Btrie_slim_mgr trie) {
		Btrie_rv trv = new Btrie_rv();
		BryWtr tmp = null;
		int bgn = 0;
		int end = src.length;

		// main loop
		boolean lhs_found = false;
		int pos = bgn;
		int rhs_end = pos, lhs_bgn = pos, lhs_end = pos;
		while (pos < end) {
			// check byte against trie
			Object o = trie.Match_at_w_b0(trv, src[pos], src, pos, end);

			// no match; increment and continue;
			if (o == null) {
				pos++;
				continue;
			}

			// match found
			BoolVal tag_marker = (BoolVal)o;

			// match is open tag; EX: <tag>
			if (tag_marker.Val()) {
				// set lhs_bgn and lhs_end; note that if there are multiple open tags, it will only keep the first
				if (!lhs_found) {
					lhs_found = true;
					lhs_bgn = pos;
					lhs_end = trv.Pos();
				}
			}
			// match is close tag; EX: </tag>
			else {
				// only splice if open tag exists; avoids dangling rhs; EX: "a</tag>b"
				if (lhs_found) {
					lhs_found = false;
					if (tmp == null) tmp = BryWtr.New();

					// add text from previous </tag> to current <tag>;
					tmp.AddMid(src, rhs_end, lhs_bgn);

					// add text between <tag> and </tag>;
					tmp.AddMid(src, lhs_end, pos);

					// update </tag> pos
					rhs_end = trv.Pos();
				}
			}

			// update pos to after match
			pos = trv.Pos();
		}

		// add remaining text to bfr
		if (tmp != null) {
			tmp.AddMid(src, rhs_end, end);
		}

		return tmp == null ? src : tmp.ToBryAndClear();
	}
}
