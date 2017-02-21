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
package gplx.xowa.bldrs.setups.maints; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.setups.*;
import gplx.core.btries.*; import gplx.core.ios.*;
public class Wmf_latest_parser {
	private Ordered_hash hash = Ordered_hash_.New_bry();
	private final    Btrie_rv trv = new Btrie_rv();
	public int Count() {return hash.Count();}
	public Wmf_latest_itm Get_at(int i)		{return (Wmf_latest_itm)hash.Get_at(i);}
	public Wmf_latest_itm Get_by(byte[] k)	{return (Wmf_latest_itm)hash.Get_by(k);}
	public Wmf_latest_itm[] To_ary()		{return (Wmf_latest_itm[])hash.To_ary(Wmf_latest_itm.class);}
	public void Parse(byte[] src) {
		hash.Clear();
		Bry_bfr tmp_bfr = Bry_bfr_.Reset(255);
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
			int name_bgn = Bry_find_.Move_fwd(src, name_bgn_bry, size_end, src_len); if (name_bgn == Bry_find_.Not_found) break;
			int name_end = Bry_find_.Find_fwd(src, Byte_ascii.Quote, name_bgn, src_len);
			byte[] name = Bry_.Mid(src, name_bgn, name_end);
			int date_bgn = Bry_find_.Move_fwd(src, date_bgn_bry, name_end, src_len); if (date_bgn == Bry_find_.Not_found) {Gfo_usr_dlg_.Instance.Warn_many("", "", "date_bgn not found"); break;}
			date_bgn = Bry_find_.Find_fwd_while_space_or_tab(src, date_bgn, src_len); if (date_bgn == Bry_find_.Not_found) {Gfo_usr_dlg_.Instance.Warn_many("", "", "date_bgn not found"); break;}
			int date_end = Bry_find_.Find_fwd(src, date_end_bry, date_bgn, src_len);
			byte[] date_bry = Bry_.Mid(src, date_bgn, date_end);
			DateAdp date = DateAdp_.parse_fmt(String_.new_a7(Replace_or(tmp_bfr, date_trie, trv, date_bry, 3, date_or)), "dd-MM-yyyy HH:mm");
			int size_bgn = Bry_find_.Find_fwd_while_space_or_tab(src, date_end, src_len); if (size_bgn == Bry_find_.Not_found) {Gfo_usr_dlg_.Instance.Warn_many("", "", "size_bgn not found"); break;}
			size_end = Bry_find_.Find_fwd(src, Byte_ascii.Cr, size_bgn, src_len);
			byte[] size_bry = Bry_.Mid(src, size_bgn, size_end);
			long size = Long_.parse_or(String_.new_u8(size_bry), -1);
			Wmf_latest_itm itm = new Wmf_latest_itm(name, date, size);
			hash.Add(name, itm);
		}
	}
	private static byte[] Replace_or(Bry_bfr tmp_bfr, Btrie_slim_mgr trie, Btrie_rv trv, byte[] src, int pos, byte[] or) {
		int src_len = src.length;
		Object o = trie.Match_at(trv, src, pos, src_len); if (o == null) return or;
		tmp_bfr.Add_mid(src, 0, pos);
		tmp_bfr.Add((byte[])o);
		tmp_bfr.Add_mid(src, trv.Pos(), src_len);
		return tmp_bfr.To_bry_and_clear();
	}
}
