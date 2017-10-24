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
package gplx.xowa.htmls.core.wkrs.xndes.tags; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
import gplx.core.brys.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
import gplx.xowa.htmls.core.wkrs.xndes.atrs.*;
public class Xohz_tag {
	private final    byte[] key; private final    int uid;
	private final    Xohz_atr_itm[] zatr_ary; private final    int zatr_ary_len; private final    Ordered_hash zatr_hash = Ordered_hash_.New_bry();
	private final    Int_flag_bldr flag_bldr = new Int_flag_bldr(); private final    int flag_len; private int flag__tag_is_inline, flag__unknown_atrs_exist;
	private static final    List_adp tmp_pow_ary_list = List_adp_.New();
	public Xohz_tag(int uid, byte[] key, int flag_len, Xohz_atr_itm... zatr_ary) {
		this.uid = uid; this.key = key;
		this.flag_len = flag_len;
		this.zatr_ary = zatr_ary; this.zatr_ary_len = zatr_ary.length;
		Init_flag_bldr();
	}
	public int Uid() {return uid;}
	private void Init_flag_bldr() {
		for (int i = 0; i < zatr_ary_len; ++i) {
			Xohz_atr_itm zatr = zatr_ary[i];
			zatr.Ini_flag(tmp_pow_ary_list.Count(), tmp_pow_ary_list);
			zatr_hash.Add(zatr.Key(), zatr);
		}
		tmp_pow_ary_list.Add(1);	// 0=no unknown	; 1=unknown atrs
		tmp_pow_ary_list.Add(1);	// 0=head		; 1=inline
		int tmp_pow_ary_len = tmp_pow_ary_list.Count();
		flag__tag_is_inline			= tmp_pow_ary_len - 2;
		flag__unknown_atrs_exist	= tmp_pow_ary_len - 1;
		flag_bldr.Pow_ary_bld_((int[])tmp_pow_ary_list.To_ary_and_clear(int.class));
	}
	public boolean Encode(Xoh_hdoc_ctx hctx, Xoh_hzip_bfr bfr, byte[] src, Xoh_xnde_parser parser) {
		Ordered_hash hatrs = parser.Atrs();
		int hatrs_len = hatrs.Count();
		int unknown_atrs = 0;
		for (int i = 0; i < hatrs_len; ++i) {
			Gfh_atr hatr = (Gfh_atr)hatrs.Get_at(i);
			Xohz_atr_itm zatr = (Xohz_atr_itm)zatr_hash.Get_by(hatr.Key());
			if (zatr == null)	++unknown_atrs;
			else				zatr.Enc_flag(hctx, src, hatr, flag_bldr);
		}
		flag_bldr.Set(flag__tag_is_inline		, parser.Tag_is_inline());
		flag_bldr.Set(flag__unknown_atrs_exist	, unknown_atrs);
		bfr.Add_hzip_int(flag_len, flag_bldr.Encode());
		if (unknown_atrs > 0) bfr.Add_hzip_int(1, unknown_atrs);
		for (int i = 0; i < zatr_ary_len; ++i) {
			Gfh_atr hatr = (Gfh_atr)hatrs.Get_at(i);
			Xohz_atr_itm zatr = (Xohz_atr_itm)zatr_hash.Get_by(hatr.Key());
			if (zatr != null)	zatr.Enc_data(hctx, src, hatr, bfr);	// zatr should be String
//				else				zatr.Enc_data(hctx, src, hatr, bfr);
		}			
		return true;
	}
	public boolean Decode(Xoh_hdoc_ctx hctx, Bry_bfr bfr, Bry_rdr rdr, byte[] src, int src_bgn, int src_end) {
		int flag = rdr.Read_hzip_int(flag_len); flag_bldr.Decode(flag);
		bfr.Add_byte(Byte_ascii.Angle_bgn).Add(key);
		boolean tag_is_inline			= flag_bldr.Get_as_bool(flag__tag_is_inline);
		boolean unknown_atrs_exist		= flag_bldr.Get_as_bool(flag__unknown_atrs_exist);
		int unknown_atrs = unknown_atrs_exist ? rdr.Read_int_to() : 0;
		for (int i = 0; i < zatr_ary_len; ++i) {
			Xohz_atr_itm meta_atr = zatr_ary[i];
			meta_atr.Dec_all(hctx, rdr, flag_bldr, bfr);
		}
		for (int i = 0; i < unknown_atrs; ++i) {
		}
		bfr.Add(tag_is_inline ? Gfh_bldr_.Bry__lhs_end_inline : Gfh_bldr_.Bry__lhs_end_head);
		return true;
	}
}
