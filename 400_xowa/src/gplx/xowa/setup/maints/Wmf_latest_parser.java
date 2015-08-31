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
package gplx.xowa.setup.maints; import gplx.*; import gplx.xowa.*; import gplx.xowa.setup.*;
import gplx.core.btries.*; import gplx.ios.*;
public class Wmf_latest_parser {
	private Ordered_hash hash = Ordered_hash_.new_bry_();
	public int Count() {return hash.Count();}
	public Wmf_latest_itm Get_at(int i)		{return (Wmf_latest_itm)hash.Get_at(i);}
	public Wmf_latest_itm Get_by(byte[] k)	{return (Wmf_latest_itm)hash.Get_by(k);}
	public Wmf_latest_itm[] To_ary()		{return (Wmf_latest_itm[])hash.To_ary(Wmf_latest_itm.class);}
	public void Parse(byte[] src) {
		hash.Clear();
		Bry_bfr tmp_bfr = Bry_bfr.reset_(255);
		byte[] name_bgn_bry = Bry_.new_a7("\n<a href=\"");
		byte[] date_bgn_bry = Bry_.new_a7("</a>");
		byte[] date_end_bry = Bry_.new_a7("  ");
//			byte[] size_bgn_bry = Bry_.new_a7("</td><td class=\"s\">");
		Btrie_slim_mgr date_trie = Btrie_slim_mgr.cs()
			.Add_bry("Jan", "01").Add_bry("Feb", "02").Add_bry("Mar", "03").Add_bry("Apr", "04").Add_bry("May", "05").Add_bry("Jun", "06")
			.Add_bry("Jul", "07").Add_bry("Aug", "08").Add_bry("Sep", "09").Add_bry("Oct", "10").Add_bry("Nov", "11").Add_bry("Dec", "12")
			;
//			Btrie_slim_mgr size_trie = Btrie_slim_mgr.cs()
//				.Add_bry("B", "  B").Add_bry("K", " KB").Add_bry("M", " MB").Add_bry("G", " GB");
		byte[] date_or = Bry_.new_a7("1970-01-01 00:00:00");
//			byte[] size_or = Bry_.new_a7("0 B");
		int size_end = 0; int src_len = src.length;
		while (true) {
			int name_bgn = Bry_finder.Move_fwd(src, name_bgn_bry, size_end, src_len); if (name_bgn == Bry_finder.Not_found) break;
			int name_end = Bry_finder.Find_fwd(src, Byte_ascii.Quote, name_bgn, src_len);
			byte[] name = Bry_.Mid(src, name_bgn, name_end);
			int date_bgn = Bry_finder.Move_fwd(src, date_bgn_bry, name_end, src_len); if (date_bgn == Bry_finder.Not_found) {Gfo_usr_dlg_.I.Warn_many("", "", "date_bgn not found"); break;}
			date_bgn = Bry_finder.Find_fwd_while_space_or_tab(src, date_bgn, src_len); if (date_bgn == Bry_finder.Not_found) {Gfo_usr_dlg_.I.Warn_many("", "", "date_bgn not found"); break;}
			int date_end = Bry_finder.Find_fwd(src, date_end_bry, date_bgn, src_len);
			byte[] date_bry = Bry_.Mid(src, date_bgn, date_end);
			DateAdp date = DateAdp_.parse_fmt(String_.new_a7(Replace_or(tmp_bfr, date_trie, date_bry, 3, date_or)), "dd-MM-yyyy HH:mm");
			int size_bgn = Bry_finder.Find_fwd_while_space_or_tab(src, date_end, src_len); if (size_bgn == Bry_finder.Not_found) {Gfo_usr_dlg_.I.Warn_many("", "", "size_bgn not found"); break;}
			size_end = Bry_finder.Find_fwd(src, Byte_ascii.Cr, size_bgn, src_len);
			byte[] size_bry = Bry_.Mid(src, size_bgn, size_end);
			long size = Long_.parse_or(String_.new_u8(size_bry), -1);
			Wmf_latest_itm itm = new Wmf_latest_itm(name, date, size);
			hash.Add(name, itm);
		}
	}
	private static byte[] Replace_or(Bry_bfr tmp_bfr, Btrie_slim_mgr trie, byte[] src, int pos, byte[] or) {
		int src_len = src.length;
		Object o = trie.Match_bgn(src, pos, src_len); if (o == null) return or;
		tmp_bfr.Add_mid(src, 0, pos);
		tmp_bfr.Add((byte[])o);
		tmp_bfr.Add_mid(src, trie.Match_pos(), src_len);
		return tmp_bfr.Xto_bry_and_clear();
	}
}
