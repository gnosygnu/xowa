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
import gplx.langs.htmls.docs.*; import gplx.xowa.htmls.core.wkrs.xndes.dicts.*;
class Xohz_atr_itm_ {
	public static void Dec__add__quote_bgn(Bry_bfr bfr, byte[] key) {
		bfr.Add_byte_space().Add(key).Add_byte_eq().Add_byte_quote();
	}
	public static void Dec__add__quote_end(Bry_bfr bfr) {
		bfr.Add_byte_quote();
	}
}
class Xohz_atr_itm__str implements Xohz_atr_itm {	// EX: id='some_string'		
	private int flag_idx;
	private int val_id_len = 2;
	public Xohz_atr_itm__str(int uid, byte[] key) {this.uid = uid; this.key = key;}
	public int Uid() {return uid;} private final int uid;
	public byte[] Key() {return key;} private final byte[] key;
	public void Ini_flag(int flag_idx, List_adp flag_bldr_list) {
		this.flag_idx = flag_idx;
		flag_bldr_list.Add(1);
	}
	public void Enc_flag(Xoh_hdoc_ctx hctx, byte[] src, Gfh_atr hatr, Int_flag_bldr flag_bldr) {
		flag_bldr.Set_as_bool(flag_idx, hatr.Val_dat_exists());
	}
	public void Enc_data	(Xoh_hdoc_ctx hctx, byte[] src, Gfh_atr hatr, Xoh_hzip_bfr bfr) {
		Xoh_xnde_dict_itm itm = hctx.Hzip__xnde__dict().Get().Get_by_key_or_new(src, hatr.Val_bgn(), hatr.Val_end());
		bfr.Add_hzip_int(val_id_len, itm.Id());
	}
	public void Dec_all(Xoh_hdoc_ctx hctx, Bry_rdr rdr, Int_flag_bldr flag_bldr, Bry_bfr bfr) {
		boolean exists = flag_bldr.Get_as_bool(flag_idx);
		if (!exists) return;
		Xohz_atr_itm_.Dec__add__quote_bgn(bfr, key);
		int val_id = rdr.Read_hzip_int(val_id_len);
		Xoh_xnde_dict_itm itm = hctx.Hzip__xnde__dict().Get().Get_by_id_or_null(val_id);
		bfr.Add(itm.Val());
		Xohz_atr_itm_.Dec__add__quote_end(bfr);
	}
}
class Xohz_atr_itm__int implements Xohz_atr_itm {	// EX: colspan='5'; rowspan='2'
	private final int val_len;
	private int flag_idx;
	public Xohz_atr_itm__int(int uid, byte[] key, int val_len) {this.uid = uid; this.key = key; this.val_len = val_len;}
	public int Uid() {return uid;} private final int uid;
	public byte[] Key() {return key;} private final byte[] key; 
	public void Ini_flag(int flag_idx, List_adp flag_bldr_list) {
		this.flag_idx = flag_idx;
		flag_bldr_list.Add(1);
	}
	public void Enc_flag(Xoh_hdoc_ctx hctx, byte[] src, Gfh_atr hatr, Int_flag_bldr flag_bldr) {
		flag_bldr.Set_as_bool(flag_idx, hatr.Val_dat_exists());
	}
	public void Enc_data	(Xoh_hdoc_ctx hctx, byte[] src, Gfh_atr hatr, Xoh_hzip_bfr bfr) {
		int val = Bry_.To_int_or(src, hatr.Val_bgn(), hatr.Val_end(), Int_.Min_value);
		if (val == Int_.Min_value)
			bfr.Add_hzip_mid(src, hatr.Val_bgn(), hatr.Val_end());
		else
			bfr.Add_hzip_int(val_len, val);
	}
	public void Dec_all(Xoh_hdoc_ctx hctx, Bry_rdr rdr, Int_flag_bldr flag_bldr, Bry_bfr bfr) {
		if (!flag_bldr.Get_as_bool(flag_idx)) return;
		Xohz_atr_itm_.Dec__add__quote_bgn(bfr, key);
		bfr.Add_int_variable(rdr.Read_hzip_int(val_len));
		Xohz_atr_itm_.Dec__add__quote_end(bfr);
	}
}
class Xohz_atr_itm__enm implements Xohz_atr_itm {	// EX: scope='col','row'
	private int flag_idx;
	private final byte[][] val_ary;
	private final Hash_adp_bry val_hash = Hash_adp_bry.ci_a7();
	private int val_id_len;
	private int tmp_val_id, tmp_src_bgn, tmp_src_end;
	public Xohz_atr_itm__enm(int uid, byte[] key, byte[][] val_ary) {
		this.uid = uid;
		this.key = key;
		this.val_ary = val_ary;
		int val_ary_len = val_ary.length;
		this.val_id_len = Log2(val_ary_len);
		for (int i = 0; i < val_ary_len; ++i)
			val_hash.Add_bry_int(val_ary[i], i + 1);
	}
	public byte[] Key() {return key;} private final byte[] key;
	public int Uid() {return uid;} private final int uid;
	public void Ini_flag(int flag_idx, List_adp flag_bldr_list) {
		this.flag_idx = flag_idx;
		flag_bldr_list.Add(val_id_len);
	}
	public void Enc_flag(Xoh_hdoc_ctx hctx, byte[] src, Gfh_atr hatr, Int_flag_bldr flag_bldr) {
		this.tmp_val_id = val_hash.Get_as_int_or(src, hatr.Val_bgn(), hatr.Val_end(), 0);
		flag_bldr.Set_as_int(flag_idx, tmp_val_id);
		if (tmp_val_id == 0) {
			this.tmp_src_bgn = hatr.Val_bgn();
			this.tmp_src_end = hatr.Val_end();
		}
	}
	public void Enc_data	(Xoh_hdoc_ctx hctx, byte[] src, Gfh_atr hatr, Xoh_hzip_bfr bfr) {
		if (tmp_val_id == 0)
			bfr.Add_hzip_mid(src, tmp_src_bgn, tmp_src_end);
	}
	public void Dec_all(Xoh_hdoc_ctx hctx, Bry_rdr rdr, Int_flag_bldr flag_bldr, Bry_bfr bfr) {
		Xohz_atr_itm_.Dec__add__quote_bgn(bfr, key);
		int enm_val = flag_bldr.Get_as_int(flag_idx);
		if (enm_val == 0)
			bfr.Add(rdr.Read_bry_to(Byte_ascii.Escape));
		else
			bfr.Add(val_ary[enm_val - 1]);
		Xohz_atr_itm_.Dec__add__quote_end(bfr);
	}
	private static int Log2(int v) {
		if		(v <   2)	return  1;
		else if	(v <   4)	return  2;
		else if	(v <   8)	return  3;
		else if	(v <  16)	return  4;
		else if	(v <  32)	return  5;
		else if	(v <  64)	return  6;
		else if	(v < 128)	return  7;
		else if	(v < 256)	return  8;
		throw Err_.new_("hzip", "unknown log2", "val", v);
	}
}
