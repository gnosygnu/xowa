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
package gplx.xowa.wikis.tdbs.hives;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.wrappers.IntRef;
import gplx.xowa.wikis.tdbs.*;
public class Xowd_hive_regy_itm {// csv file with the format of "idx|bgn|end|count"; EX: "0|AA|AZ|120\n1|BA|BZ|110"
	public Xowd_hive_regy_itm(int idx) {this.idx = idx;}
	public int		Idx() {return idx;} private int idx;
	public byte[]	Bgn() {return bgn;} public Xowd_hive_regy_itm Bgn_(byte[] v) {bgn = v; return this;} private byte[] bgn;
	public byte[]	End() {return end;} public Xowd_hive_regy_itm End_(byte[] v) {end = v; return this;} private byte[] end;
	public int		Count() {return count;} public Xowd_hive_regy_itm Count_(int v) {this.count = v; return this;} private int count;
	public static Xowd_hive_regy_itm[] parse_fil_(ByteAry_fil utl) {
		List_adp rv = utl.Itms();
		byte[] ary = utl.Raw_bry();
		int ary_len = utl.Raw_len(); if (ary_len == 0) return Xowd_hive_regy_itm.Ary_empty; //throw Err_mgr.Instance.fmt_("xowa.wiki.data", "title_registry_file_not_found", "title_registry file not found: ~{0}", utl.Fil().Xto_api());
		IntRef pos = IntRef.NewZero();
		while (pos.Val() < ary_len) {
			Xowd_hive_regy_itm file = new Xowd_hive_regy_itm();
			file.idx	= BryUtlByWtr.ReadCsvInt(ary, pos, BryUtl.DlmFld);
			file.bgn 	= BryUtlByWtr.ReadCsvBry(ary, pos, BryUtl.DlmFld);	// skip bgn
			file.end	= BryUtlByWtr.ReadCsvBry(ary, pos, BryUtl.DlmFld);
			file.count	= BryUtlByWtr.ReadCsvInt(ary, pos, BryUtl.DlmRow);
			rv.Add(file);
		}
		return (Xowd_hive_regy_itm[])utl.Xto_itms(Xowd_hive_regy_itm.class);
	}
	public Xowd_hive_regy_itm() {}
	public Xowd_hive_regy_itm(int id, byte[] bgn, byte[] end, int count) {
		this.idx = id; this.bgn = bgn; this.end = end; this.count = count;
	}
	public void Srl_save(BryWtr bfr) {
		bfr	.AddIntVariable(idx).AddBytePipe()
			.Add(bgn).AddBytePipe()
			.Add(end).AddBytePipe()
			.AddIntVariable(count).AddByteNl();
	} 
	public static Xowd_hive_regy_itm tmp_()	{return new Xowd_hive_regy_itm();}
	public static final Xowd_hive_regy_itm[] Ary_empty = new Xowd_hive_regy_itm[0];
}
