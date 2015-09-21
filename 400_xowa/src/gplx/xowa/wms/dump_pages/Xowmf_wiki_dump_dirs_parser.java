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
package gplx.xowa.wms.dump_pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.wms.*;
public class Xowmf_wiki_dump_dirs_parser {
	public static String[] Parse(byte[] wiki, byte[] src) {
		List_adp rv = List_adp_.new_();
		int pos = 0;
		while (true) {
			int href_bgn = Bry_find_.Move_fwd(src, Tkn_href		, pos);			if (href_bgn == Bry_find_.Not_found) break;
			int href_end = Bry_find_.Find_fwd(src, Byte_ascii.Quote, href_bgn);	if (href_end == Bry_find_.Not_found) throw Err_.new_wo_type("unable to find date_end", "wiki", wiki, "snip", Bry_.Mid_by_len_safe(src, href_bgn - 3, 25));
			if (src[href_end - 1] != Byte_ascii.Slash) throw Err_.new_wo_type("href should end in slash", "wiki", wiki, "snip", Bry_.Mid_by_len_safe(src, href_bgn - 3, 25));
			pos = href_end + 1;
			byte[] href_bry = Bry_.Mid(src, href_bgn, href_end - 1);
			if (Bry_.Eq(href_bry, Tkn_owner)) continue;	// ignore <a href="../">
			rv.Add(String_.new_u8(href_bry));
		}
		return (String[])rv.To_ary_and_clear(String.class);
	}
	private static final byte[] Tkn_href = Bry_.new_a7(" href=\""), Tkn_owner = Bry_.new_a7("..");
}
