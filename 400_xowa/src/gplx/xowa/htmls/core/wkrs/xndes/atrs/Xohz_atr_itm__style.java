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
package gplx.xowa.htmls.core.wkrs.xndes.atrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
import gplx.core.brys.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*; import gplx.langs.htmls.styles.*;
class Xohz_atr_itm__style implements Xohz_atr_itm {	// EX: style='width:20em;'
	private int flag_idx;
	private final    Ordered_hash zatr_hash;
	private final    Gfh_style_wkr__ary style_wkr = new Gfh_style_wkr__ary();
	public Xohz_atr_itm__style(int uid, byte[] key, Ordered_hash zatr_hash) {this.uid = uid; this.key = key; this.zatr_hash = zatr_hash;}
	public int Uid() {return uid;} private final    int uid;
	public byte[] Key() {return key;} private final    byte[] key;
	public void Ini_flag(int flag_idx, List_adp flag_bldr_list) {
		this.flag_idx = flag_idx;
		flag_bldr_list.Add(1);
	}
	public void Enc_flag(Xoh_hdoc_ctx hctx, byte[] src, Gfh_atr hatr, Int_flag_bldr flag_bldr) {
		flag_bldr.Set_as_bool(flag_idx, hatr.Val_dat_exists());
	}
	public void Enc_data	(Xoh_hdoc_ctx hctx, byte[] src, Gfh_atr hatr, Xoh_hzip_bfr bfr) {
		Gfh_style_itm[] itms = style_wkr.Parse(src, hatr.Val_bgn(), hatr.Val_end());
		int len = itms.length;
		bfr.Add_hzip_int(1, len);
		for (int i = 0; i < len; ++i) {
			Gfh_style_itm hitm = itms[i];
			Xohz_atr_itm zitm = (Xohz_atr_itm)zatr_hash.Get_by(hitm.Key());
			if (zitm == null) {
				// get dict key
				// write dict key
			}
			else
				bfr.Add_hzip_int(1, zitm.Uid());
			bfr.Add_hzip_bry(hitm.Val());
		}
	}
	public void Dec_all(Xoh_hdoc_ctx hctx, Bry_rdr rdr, Int_flag_bldr flag_bldr, Bry_bfr bfr) {
		boolean exists = flag_bldr.Get_as_bool(flag_idx);
		if (!exists) return;
		Xohz_atr_itm_.Dec__add__quote_bgn(bfr, key);
		int len = rdr.Read_hzip_int(1);
		for (int i = 0; i < len; ++i) {
			int key_uid = rdr.Read_hzip_int(1);
			Xohz_atr_itm zatr = (Xohz_atr_itm)zatr_hash.Get_by(key_uid);
			bfr.Add(zatr.Key());
			int val_bgn = rdr.Pos();
			int val_end = rdr.Find_fwd_lr();
			bfr.Add_mid(rdr.Src(), val_bgn, val_end);
		}
//			int val_id = rdr.Read_hzip_int(val_id_len);
//			Xoh_xnde_dict_itm itm = hctx.Hzip__xnde__dict().Get().Get_by_id_or_null(val_id);
//			bfr.Add(itm.Val());
		Xohz_atr_itm_.Dec__add__quote_end(bfr);
	}
}
