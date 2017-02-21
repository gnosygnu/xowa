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
package gplx.core.btries; import gplx.*; import gplx.core.*;
import gplx.core.intls.*;
class Btrie_u8_itm {
	private Hash_adp_bry nxts;
	private byte[] asymmetric_bry;
	public Btrie_u8_itm(byte[] key, Object val) {this.key = key; this.val = val;}
	public byte[] Key() {return key;} private byte[] key;
	public Object Val() {return val;} public void Val_set(Object val) {this.val = val;} private Object val;
	public boolean Nxts_is_empty() {return nxts == null;}
	public void Clear() {
		val = null;
		nxts.Clear();
		nxts = null;
	}
	public Btrie_u8_itm Nxts_find(byte[] src, int c_bgn, int c_end, boolean called_by_match) {
		if (nxts == null) return null;
		Object rv_obj = nxts.Get_by_mid(src, c_bgn, c_end);
		if (rv_obj == null) return null;
		Btrie_u8_itm rv = (Btrie_u8_itm)rv_obj;
		byte[] asymmetric_bry = rv.asymmetric_bry;
		if (asymmetric_bry == null)													// itm doesn't have asymmetric_bry; note that this is the case for most items
			return rv;
		else {																		// itm has asymmetric_bry; EX: "İ" was added to trie, must match "İ" and "i"; 
			if (called_by_match) {													// called by mgr.Match
				return
					(	Bry_.Eq(src, c_bgn, c_end, rv.key)							// key matches src;				EX: "aİ"
					||	Bry_.Eq(src, c_bgn, c_end, rv.asymmetric_bry)				// asymmetric_bry matches src;	EX: "ai"; note that "aI" won't match
					)
					? rv : null;
			}
			else {																	// called by mgr.Add; this means that an asymmetric_itm was already added; happens when "İ" added first and then "I" added next
				rv.asymmetric_bry = null;											// always null out asymmetric_bry; note that this noops non-asymmetric itms, while making an asymmetric_itm case-insenstivie (matches İ,i,I); see tests
				return rv;
			}
		}
	}
	public Btrie_u8_itm Nxts_add(Gfo_case_mgr case_mgr, byte[] key, Object val) {
		Btrie_u8_itm rv = new Btrie_u8_itm(key, val);
		if (nxts == null) nxts = Hash_adp_bry.ci_u8(case_mgr);
		nxts.Add_bry_obj(key, rv);
		Gfo_case_itm case_itm = case_mgr.Get_or_null(key[0], key, 0, key.length);	// get case_item
		if (case_itm != null) {														// note that case_itm may be null; EX: "__TOC__" and "_"
			byte[] asymmetric_bry = case_itm.Asymmetric_bry();
			if (asymmetric_bry != null) {											// case_itm has asymmetry_bry; only itms in Xol_case_itm_ that are created with Tid_upper and Tid_lower will be non-null
				rv.asymmetric_bry = asymmetric_bry;									// set itm to asymmetric_bry; EX: for İ, asymmetric_bry = i
//					nxts.Add_bry_obj(asymmetric_bry, rv);								// add the asymmetric_bry to the hash; in above example, this allows "i" to match "İ"
			}
		}
		return rv;
	}
}
