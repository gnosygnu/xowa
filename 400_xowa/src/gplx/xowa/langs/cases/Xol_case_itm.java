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
package gplx.xowa.langs.cases; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
import gplx.core.primitives.*;
import gplx.core.intls.*;
public interface Xol_case_itm extends Gfo_case_itm {
	byte Tid();
	byte[] Src_ary();
	byte[] Trg_ary();
	void Case_build_upper(Bry_bfr bfr);
	void Case_build_lower(Bry_bfr bfr);
	void Case_reuse_upper(byte[] ary, int bgn, int len);
	void Case_reuse_lower(byte[] ary, int bgn, int len);
	Xol_case_itm Clone();
}
class Xol_case_itm_byt implements Xol_case_itm {
	public Xol_case_itm_byt(byte tid, byte src_byte, byte trg_byte) {
		this.tid = tid; this.src_byte = src_byte; this.trg_byte = trg_byte; this.src_ary = new byte[] {src_byte}; this.trg_ary = new byte[] {trg_byte};
		switch (tid) {
			case Xol_case_itm_.Tid_both:
			case Xol_case_itm_.Tid_upper:		upper_byte = trg_byte; lower_byte = src_byte; break;
			case Xol_case_itm_.Tid_lower:		upper_byte = src_byte; lower_byte = trg_byte; break;
		}
	}
	public byte Tid() {return tid;} private byte tid;
	public boolean Is_single_byte() {return true;}
	public byte[] Src_ary() {return src_ary;} private byte[] src_ary;
	public byte[] Trg_ary() {return trg_ary;} private byte[] trg_ary;
	public byte Src_byte() {return src_byte;} private byte src_byte;
	public byte Trg_byte() {return trg_byte;} private byte trg_byte;
	public void Case_build_upper(Bry_bfr bfr) {bfr.Add_byte(upper_byte);} private byte upper_byte;
	public void Case_build_lower(Bry_bfr bfr) {bfr.Add_byte(lower_byte);} private byte lower_byte;
	public void Case_reuse_upper(byte[] ary, int bgn, int len) {ary[bgn] = upper_byte;}
	public void Case_reuse_lower(byte[] ary, int bgn, int len) {ary[bgn] = lower_byte;}
	public Xol_case_itm Clone() {return new Xol_case_itm_byt(tid, src_byte, trg_byte);}
	public int Utf8_id_lo() {return lower_byte;}
	public int Hashcode_lo() {return lower_byte;}
	public int Len_lo() {return 1;}
	public byte[] Asymmetric_bry() {return null;}
}
class Xol_case_itm_bry implements Xol_case_itm {
	public Xol_case_itm_bry(byte tid, byte[] src_ary, byte[] trg_ary) {
		this.tid = tid; this.src_ary = src_ary; this.trg_ary = trg_ary;
		switch (tid) {
			case Xol_case_itm_.Tid_both:		upper_ary = trg_ary; lower_ary = src_ary; break;
			case Xol_case_itm_.Tid_upper:		upper_ary = trg_ary; lower_ary = src_ary; asymmetric_bry = src_ary; break;
			case Xol_case_itm_.Tid_lower:		upper_ary = src_ary; lower_ary = trg_ary; asymmetric_bry = trg_ary; break;
		}
		len_lo = lower_ary.length;
		utf8_id_lo = Utf16_.Decode_to_int(lower_ary, 0);
		hashcode_ci_lo = Bry_obj_ref.CalcHashCode(lower_ary, 0, len_lo);
	}
	public byte Tid() {return tid;} public Xol_case_itm_bry Tid_(byte v) {tid = v; return this;} private byte tid;
	public boolean Is_single_byte() {return false;}
	public byte[] Src_ary() {return src_ary;} private byte[] src_ary;
	public byte[] Trg_ary() {return trg_ary;} private byte[] trg_ary;
	public void Case_build_upper(Bry_bfr bfr) {bfr.Add(upper_ary);} private byte[] upper_ary;
	public void Case_build_lower(Bry_bfr bfr) {bfr.Add(lower_ary);} private byte[] lower_ary;
	public void Case_reuse_upper(byte[] ary, int bgn, int len) {	// ASSUME: upper/lower have same width; i.e.: upper'ing a character doesn't go from a 2-width byte to a 3-width byte
		for (int i = 0; i < len; i++)
			ary[i + bgn] = upper_ary[i];
	}
	public void Case_reuse_lower(byte[] ary, int bgn, int len) {	// ASSUME: upper/lower have same width; i.e.: upper'ing a character doesn't go from a 2-width byte to a 3-width byte
		for (int i = 0; i < len; i++)
			ary[i + bgn] = lower_ary[i];
	}
	public Xol_case_itm Clone() {return new Xol_case_itm_bry(tid, src_ary, trg_ary);}
	public int Len_lo() {return len_lo;} private int len_lo;
	public int Utf8_id_lo() {return utf8_id_lo;} private int utf8_id_lo;
	public byte[] Asymmetric_bry() {return asymmetric_bry;} private byte[] asymmetric_bry;
	public int Hashcode_lo() {return hashcode_ci_lo;} private int hashcode_ci_lo;
}
