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
package gplx.xowa.parsers.htmls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.brys.*;
public class Mwh_atr_mgr {
	private final int data_max_orig;				
	public Mwh_atr_mgr(int max) {
		this.data_max_orig = max * Idx__mult;
		this.Max_(max);
	}
	public int Len() {return itm_len;} private int itm_len;
	public int[] Data_ary() {return data_ary;} private int[] data_ary; private int data_max;
	public byte[][] Text_ary() {return text_ary;} private byte[][] text_ary;
	private void Max_(int len) {
		this.data_max = len * Idx__mult;
		this.data_ary = new int[data_max];
		this.text_ary = new byte[len * Text__mult][];
		this.itm_len = 0;
	}
	public void Clear() {
		if (data_max == data_max_orig)
			itm_len = 0;
		else
			Max_(data_max_orig / Idx__mult);
	}
	public int Add(int nde_uid, int nde_tid, boolean valid, boolean repeated, boolean key_exists, int atr_bgn, int atr_end, int key_bgn, int key_end, byte[] key_bry, int eql_pos, int qte_tid, int val_bgn, int val_end, byte[] val_bry) {
		int data_idx = itm_len * Idx__mult;
		if (data_idx == data_max) {
			int new_data_max = data_max == 0 ? Idx__mult : data_max * 2;
			int[] new_data_ary = new int[new_data_max];
			Int_.Ary_copy_to(data_ary, data_max, data_ary);
			this.data_ary = new_data_ary;

			int text_max = text_ary.length;
			int new_text_max = data_max == 0 ? Text__mult : text_max * 2;
			byte[][] new_text_ary = new byte[new_text_max][];
			for (int i = 0; i < text_max; ++i)
				new_text_ary[i] = text_ary[i];
			this.text_ary = new_text_ary;

			this.data_max = new_data_max;
		}
		boolean val_made = false;
		int text_idx = itm_len * Text__mult;
		text_ary[text_idx] = key_bry;
		if (val_bry != null) {
			text_ary[text_idx + 1] = val_bry;
			val_made = true;
		}
		data_ary[data_idx + Idx_nde_uid] = nde_uid;
		data_ary[data_idx + Idx_nde_tid] = nde_tid;
		data_ary[data_idx + Idx_atr_utl] = Mwh_atr_itm_.Calc_atr_utl(qte_tid, valid, repeated, key_exists, val_made);
		data_ary[data_idx + Idx_atr_bgn] = atr_bgn;
		data_ary[data_idx + Idx_atr_end] = atr_end;
		data_ary[data_idx + Idx_key_bgn] = key_bgn;
		data_ary[data_idx + Idx_key_end] = key_end;
		data_ary[data_idx + Idx_val_bgn] = val_bgn;
		data_ary[data_idx + Idx_val_end] = val_end;
		data_ary[data_idx + Idx_eql_pos] = eql_pos;
		return itm_len++;
	}
	public void Set_repeated(int atr_uid) {
		int atr_utl_idx = (atr_uid * Idx__mult) + Idx_atr_utl;
		int atr_utl = data_ary[atr_utl_idx];
		int val_bry_exists = atr_utl & Atr_utl__val_bry_exists; 
		data_ary[atr_utl_idx] = Mwh_atr_itm_.Atr_tid__repeat | val_bry_exists;
	}
	public static final int
	  Idx_nde_uid			=  0
	, Idx_nde_tid			=  1
	, Idx_atr_utl			=  2
	, Idx_atr_bgn			=  3
	, Idx_atr_end			=  4
	, Idx_key_bgn			=  5
	, Idx_key_end			=  6
	, Idx_val_bgn			=  7
	, Idx_val_end			=  8
	, Idx_eql_pos			=  9
	, Idx__mult				= 10
	;
	public static final int Text__mult = 2;
	public static final int Atr_utl__val_bry_exists = 16;
}
