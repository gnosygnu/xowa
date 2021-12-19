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
package gplx.xowa.bldrs.setups.maints;
import gplx.core.btries.*;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.LongUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.GfoDate;
import gplx.types.commons.GfoDateUtl;
public class Wmf_latest_parser {
	private Ordered_hash hash = Ordered_hash_.New_bry();
	private final Btrie_rv trv = new Btrie_rv();
	public int Count() {return hash.Len();}
	public Wmf_latest_itm Get_at(int i)		{return (Wmf_latest_itm)hash.GetAt(i);}
	public Wmf_latest_itm Get_by(byte[] k)	{return (Wmf_latest_itm)hash.GetByOrNull(k);}
	public Wmf_latest_itm[] To_ary()		{return (Wmf_latest_itm[])hash.ToAry(Wmf_latest_itm.class);}
	public void Parse(byte[] src) {
		hash.Clear();
		BryWtr tmp_bfr = BryWtr.NewAndReset(255);
		byte[] name_bgn_bry = BryUtl.NewA7("\n<a href=\"");
		byte[] date_bgn_bry = BryUtl.NewA7("</a>");
		byte[] date_end_bry = BryUtl.NewA7("  ");
//			byte[] size_bgn_bry = Bry_.new_a7("</td><td class=\"s\">");
		Btrie_slim_mgr date_trie = Btrie_slim_mgr.cs()
			.Add_bry("Jan", "01").Add_bry("Feb", "02").Add_bry("Mar", "03").Add_bry("Apr", "04").Add_bry("May", "05").Add_bry("Jun", "06")
			.Add_bry("Jul", "07").Add_bry("Aug", "08").Add_bry("Sep", "09").Add_bry("Oct", "10").Add_bry("Nov", "11").Add_bry("Dec", "12")
			;
//			Btrie_slim_mgr size_trie = Btrie_slim_mgr.cs()
//				.Add_bry("B", "  B").Add_bry("K", " KB").Add_bry("M", " MB").Add_bry("G", " GB");
		byte[] date_or = BryUtl.NewA7("1970-01-01 00:00:00");
//			byte[] size_or = Bry_.new_a7("0 B");
		int size_end = 0; int src_len = src.length;
		while (true) {
			int name_bgn = BryFind.MoveFwd(src, name_bgn_bry, size_end, src_len); if (name_bgn == BryFind.NotFound) break;
			int name_end = BryFind.FindFwd(src, AsciiByte.Quote, name_bgn, src_len);
			byte[] name = BryLni.Mid(src, name_bgn, name_end);
			int date_bgn = BryFind.MoveFwd(src, date_bgn_bry, name_end, src_len); if (date_bgn == BryFind.NotFound) {Gfo_usr_dlg_.Instance.Warn_many("", "", "date_bgn not found"); break;}
			date_bgn = BryFind.FindFwdWhileSpaceOrTab(src, date_bgn, src_len); if (date_bgn == BryFind.NotFound) {Gfo_usr_dlg_.Instance.Warn_many("", "", "date_bgn not found"); break;}
			int date_end = BryFind.FindFwd(src, date_end_bry, date_bgn, src_len);
			byte[] date_bry = BryLni.Mid(src, date_bgn, date_end);
			GfoDate date = GfoDateUtl.ParseFmt(StringUtl.NewA7(Replace_or(tmp_bfr, date_trie, trv, date_bry, 3, date_or)), "dd-MM-yyyy HH:mm");
			int size_bgn = BryFind.FindFwdWhileSpaceOrTab(src, date_end, src_len); if (size_bgn == BryFind.NotFound) {Gfo_usr_dlg_.Instance.Warn_many("", "", "size_bgn not found"); break;}
			size_end = BryFind.FindFwd(src, AsciiByte.Cr, size_bgn, src_len);
			byte[] size_bry = BryLni.Mid(src, size_bgn, size_end);
			long size = LongUtl.ParseOr(StringUtl.NewU8(size_bry), -1);
			Wmf_latest_itm itm = new Wmf_latest_itm(name, date, size);
			hash.Add(name, itm);
		}
	}
	private static byte[] Replace_or(BryWtr tmp_bfr, Btrie_slim_mgr trie, Btrie_rv trv, byte[] src, int pos, byte[] or) {
		int src_len = src.length;
		Object o = trie.MatchAt(trv, src, pos, src_len); if (o == null) return or;
		tmp_bfr.AddMid(src, 0, pos);
		tmp_bfr.Add((byte[])o);
		tmp_bfr.AddMid(src, trv.Pos(), src_len);
		return tmp_bfr.ToBryAndClear();
	}
}
