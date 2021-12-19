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
package gplx.xowa.htmls.core.wkrs.imgs.atrs;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
import gplx.types.custom.brys.wtrs.args.BryBfrArgClearable;
import gplx.types.custom.brys.rdrs.BryRdrErrWkr;
import gplx.types.custom.brys.rdrs.BryRdr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
public class Xoh_anch_cls_data implements BryBfrArgClearable {
	private final BryRdr rdr = new BryRdr();
	public byte Tid() {return tid;} private byte tid;
	public void Clear() {
		tid = Xoh_anch_cls_.Tid__none;
	}
	public boolean Parse(BryRdrErrWkr err_wkr, byte[] src, Gfh_tag tag) {
		Gfh_atr atr = tag.Atrs__get_by_or_empty(Gfh_atr_.Bry__class);		// EX: class='image'
		int src_bgn = atr.Val_bgn(); int src_end = atr.Val_end();
		if (src_bgn == -1) return false;
		rdr.InitByWkr(err_wkr, "anch.cls", src_bgn, src_end);
		this.tid = rdr.ChkOr(Xoh_anch_cls_.Trie, AsciiByte.Max7Bit);
		return tid != AsciiByte.Max7Bit;
	}
	public void Init_by_decode(int tid) {this.tid = (byte)tid;}
	public void BfrArgClear()	{this.Clear();}
	public boolean BfrArgIsMissing()	{return false;}
	public void AddToBfr(BryWtr bfr) {
		if (BfrArgIsMissing()) return;
		bfr.Add(Xoh_anch_cls_.To_val(tid));
	}
}
