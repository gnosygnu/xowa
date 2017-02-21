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
package gplx.xowa.wikis.tdbs.xdats; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.tdbs.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*;
import gplx.core.encoders.*;
public class Xob_xdat_file {
	public byte[] Src() {return src;} private byte[] src;
	public int Src_len() {return src_len;} public Xob_xdat_file Src_len_(int v) {src_len = v; return this;} private int src_len;	// NOTE: src_len can be different than src.length (occurs when reusing brys)
	public Xob_xdat_file Update(Bry_bfr bfr, Xob_xdat_itm itm, byte[] v) {
		int ary_len = itm_ends.length;
		int itm_idx = itm.Itm_idx(); 
		int prv = itm_idx == 0 ? 0 : itm_ends[itm_idx - 1];
		int old_end = itm_ends[itm_idx]; 
		int new_end = prv + v.length;
		int dif = new_end - old_end;
		itm_ends[itm_idx] = new_end;
		for (int i = itm_idx + 1; i < ary_len; i++) {
			itm_ends[i] += dif;
		}
		Src_rebuild_hdr(bfr, ary_len);
		bfr.Add_mid(src, itm_0_bgn, itm.Itm_bgn());
		bfr.Add(v);
		bfr.Add_mid(src, itm.Itm_end() + 1, src.length);	// NOTE: + 1 to skip nl
		src = bfr.To_bry_and_clear();
		return this;
	}
	byte[][] Src_extract_brys(int ary_len) {
		byte[][] rv = new byte[ary_len][];
		int itm_bgn = this.itm_0_bgn;
		for (int i = 0; i < ary_len; i++) {
			int itm_end = itm_ends[i] + itm_0_bgn;
			rv[i] = Bry_.Mid(src, itm_bgn, itm_end);
			itm_bgn = itm_end;
		}
		return rv;
	}
	public void Sort(Bry_bfr bfr, gplx.core.lists.ComparerAble comparer) {
		int ary_len = itm_ends.length;
		byte[][] brys = Src_extract_brys(ary_len);
		Array_.Sort(brys, comparer);
		Src_rebuild_hdr(bfr, ary_len);
		itm_0_bgn = (ary_len * Len_idx_itm) + Len_itm_dlm;
		int itm_bgn = 0;
		for (int i = 0; i < ary_len; i++) {
			byte[] bry = brys[i];
			int bry_len = bry.length;
			int itm_end = itm_bgn + bry_len;
			itm_ends[i] = itm_end;
			itm_bgn = itm_end;
			bfr.Add(bry);
		}
		src = bfr.To_bry_and_clear(); 		
	}
	public void Insert(Bry_bfr bfr, byte[] itm) {
		int ary_len = itm_ends.length;
		itm_ends = (int[])Array_.Resize(itm_ends, ary_len + 1);
		int prv_pos = ary_len == 0 ? 0 : itm_ends[ary_len - 1];
		itm_ends[ary_len] = prv_pos + itm.length;
		Src_rebuild(bfr, ary_len + 1, itm);
	}
	private void Src_rebuild_hdr(Bry_bfr bfr, int ary_len) {
		int bgn = 0;
		for (int i = 0; i < ary_len; i++) {
			int end = itm_ends[i];
			int len = end - bgn;
			bfr.Add_base85_len_5(len).Add_byte(Dlm_hdr_fld);
			bgn = end;
		}
		bfr.Add_byte(Dlm_row);		
	}
	private void Src_rebuild(Bry_bfr bfr, int ary_len, byte[] new_itm) {
		Src_rebuild_hdr(bfr, ary_len);
		Src_rebuild_brys(bfr, ary_len, new_itm);
	}
	private void Src_rebuild_brys(Bry_bfr bfr, int ary_len, byte[] new_itm) {
		int bgn = itm_0_bgn;
		boolean insert = new_itm != null;
		int ary_end = insert ? ary_len - 1 : ary_len;
		for (int i = 0; i < ary_end; i++) {
			int end = itm_ends[i] + itm_0_bgn;
			bfr.Add_mid(src, bgn, end);
			bgn = end;
		}
		if (insert) bfr.Add(new_itm);
		itm_0_bgn = (ary_len * Len_idx_itm) + Len_itm_dlm;
		src = bfr.To_bry_and_clear(); 
	}	private static final    byte Dlm_hdr_fld = Byte_ascii.Pipe, Dlm_row = Byte_ascii.Nl;		
	public void Save(Io_url url) {
		Bry_bfr bfr = Bry_bfr_.New();
		Srl_save_bry(bfr);
		Io_stream_wtr wtr = Io_stream_wtr_.New_by_url(url);
		try {
			wtr.Open();
			wtr.Write(bfr.Bfr(), 0, bfr.Len());
			wtr.Flush();
		}
		catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to save file", "url", url.Xto_api());}
		finally {
			wtr.Rls();
		}
	}
	public void Srl_save_bry(Bry_bfr bfr) {
		int itm_ends_len = itm_ends.length;
		int prv_bgn = 0;
		for (int i = 0; i < itm_ends_len; i++) {
			int itm_end = itm_ends[i];
			bfr.Add_base85_len_5(itm_end - prv_bgn).Add_byte(Dlm_hdr_fld);
			prv_bgn = itm_end;
		}
		bfr.Add_byte(Dlm_row);
		bfr.Add_mid(src, itm_0_bgn, src.length);
	}
	public byte[] Get_bry(int i) {
		int bgn = i == 0 ? itm_0_bgn : itm_0_bgn + itm_ends[i - 1];
		int end = itm_0_bgn + itm_ends[i];
		return Bry_.Mid(src, bgn, end);
	}
	public int Count() {return itm_ends.length;}
	public Xob_xdat_file GetAt(Xob_xdat_itm itm, int idx) {
		itm.Src_(src);
		itm.Itm_idx_(idx);
		itm.Itm_bgn_(itm_0_bgn + (idx == 0 ? 0 : itm_ends[idx - 1]));
		itm.Itm_end_(itm_0_bgn +                 itm_ends[idx] - Len_itm_dlm);
		return this;
	}
	public Xob_xdat_file Find(Xob_xdat_itm itm, byte[] lkp, int lkp_bgn, byte lkp_dlm, boolean exact) {
		itm.Clear();
		int itm_idx = Xob_xdat_file_.BinarySearch(itm_0_bgn, src, itm_ends, lkp, lkp_bgn, lkp_dlm, 1, exact, itm); if (itm_idx == String_.Find_none) {return this;}
		GetAt(itm, itm_idx);
		return this;
	}
	public Xob_xdat_file Clear() {src = null; itm_ends = Int_.Ary_empty; return this;}
	private int[] itm_ends = Int_.Ary_empty; private int itm_0_bgn;
	public Xob_xdat_file Parse(byte[] src, int src_len, Io_url url) {// SEE:NOTE_1;xdat format
		if (src_len == 0) throw Err_.new_wo_type("file cannot be empty for parse", "url", url.Raw());
		int itm_count = 0, tmp_len = Parse_tmp_len; int[] tmp = Parse_tmp;
		try {
			int slot_bgn = 0, slot_old = 0, slot_new = 0;
			while (true) {
				slot_bgn = itm_count * Len_idx_itm;
				if (slot_bgn >= src_len) break;
				if (src[slot_bgn] == Byte_ascii.Nl) break;
				int tmp_val = Base85_.To_int_by_bry(src, slot_bgn, slot_bgn + Offset_base85);
				slot_new = slot_old + tmp_val;
				int new_idx = itm_count + 1;
				if (tmp_len < new_idx) {
					tmp_len = new_idx * 2;
					tmp = (int[])Array_.Resize(tmp, tmp_len);
				}
				tmp[itm_count] = slot_new;
				itm_count = new_idx;
				slot_old = slot_new;
			}
			int itm_ends_last = slot_new;
			itm_ends = new int[itm_count];
			for (int i = 0; i < itm_count; i++)
				itm_ends[i] = tmp[i];
			itm_0_bgn = slot_bgn + Len_itm_dlm;
			this.src = Bry_.Mid(src, 0, itm_ends_last + itm_0_bgn);
		} catch (Exception e) {throw Err_.new_exc(e, "xo", "failed to parse idx", "itm_count", itm_count, "url", url.Raw());}
		return this;
	}	private static final int Parse_tmp_len = 8 * 1024; static int[] Parse_tmp = new int[Parse_tmp_len];
	static final int Len_itm_dlm = 1, Len_idx_itm = 6, Offset_base85 = 4;	// 6 = 5 (base85_int) + 1 (new_line/pipe)
	static final String GRP_KEY = "xowa.xdat_fil";
	public static byte[] Rebuid_header(byte[] orig, byte[] dlm) {
		byte[][] rows = Bry_split_.Split(orig, dlm);
		int rows_len = rows.length;
		Bry_bfr bfr = Bry_bfr_.New();
		int dlm_len = dlm.length;
		for (int i = 1; i < rows_len; i++) {	// i=1; skip 1st row (which is empty header)
			byte[] row = rows[i];
			int row_len = row.length + dlm_len;
			bfr.Add_base85_len_5(row_len).Add_byte(Byte_ascii.Pipe);
		}
		bfr.Add_byte(Byte_ascii.Nl);
		for (int i = 1; i < rows_len; i++) {	// i=1; skip 1st row (which is empty header)
			byte[] row = rows[i];
			bfr.Add(row);
			bfr.Add(dlm);
		}
		return bfr.To_bry_and_clear();
	}
}
class Xob_xdat_file_ {
	public static int BinarySearch(int itm_0_bgn, byte[] src, int[] itm_ends, byte[] lkp, int lkp_bgn, byte lkp_dlm, int itm_end_adj, boolean exact, Xob_xdat_itm xdat_itm) {if (lkp == null) throw Err_.new_null();
		int itm_ends_len = itm_ends.length; if (itm_ends_len == 0) throw Err_.new_wo_type("itm_ends_len cannot have 0 itms");
		int lo = -1, hi = itm_ends_len - 1; // NOTE: -1 is necessary; see test
		int itm_idx = (hi - lo) / 2;
		int lkp_len = lkp.length;
		int delta = 1;
		boolean flagged = false;
		while (true) {
			int itm_bgn = itm_0_bgn + (itm_idx == 0 ? 0 : itm_ends[itm_idx - 1]);
			int itm_end = itm_0_bgn +                     itm_ends[itm_idx] - itm_end_adj;	// itm_end_adj to handle ttl .xdat and trailing \n
			int fld_bgn = itm_bgn + lkp_bgn, lkp_pos = -1;
			int comp = CompareAble_.Same;
			for (int i = fld_bgn; i < itm_end; i++) { // see if current itm matches lkp; NOTE: that i < itm_end but will end much earlier (since itm_end includes page text)
				byte b = src[i];
				if (b == lkp_dlm) {	// fld is done
					if (lkp_pos != lkp_len - 1) comp = CompareAble_.More; // lkp has more chars than itm; lkp_dlm reached early
					break;
				}
				lkp_pos = i - fld_bgn;
				if (lkp_pos >= lkp_len) {
					comp = CompareAble_.Less;	// lkp has less chars than itm
					break;
				}
				comp = (lkp[lkp_pos] & 0xff) - (b & 0xff);	// subtract src[i] from lkp[lkp_pos] // PATCH.JAVA:need to convert to unsigned byte
				if (comp != CompareAble_.Same) break;		// if comp != 0 then not equal; break; otherwise if bytes are the same, then comp == 0;
			}
			if		(comp >  CompareAble_.Same || (comp == CompareAble_.Same && itm_end - fld_bgn < lkp_len)) {lo = itm_idx; delta =  1;}
			else if	(comp == CompareAble_.Same) {xdat_itm.Found_exact_y_(); return itm_idx;}
			else if	(comp <  CompareAble_.Same) {hi = itm_idx; delta = -1;}
			int itm_dif = hi - lo;
//				if (itm_end - 1 > fld_bgn) Tfds.Dbg(comp, itm_dif, String_.new_u8(src, fld_bgn, itm_end - 1));
			switch (itm_dif) {
				case 0:						return exact ? String_.Find_none : hi;	// NOTE: can be 0 when src.length == 1 || 2; also, sometimes 0 in some situations
				case -1:
					if (flagged)			return exact ? String_.Find_none : lo;
					else {
						itm_idx--;
						flagged = true;
					}
					break;
				case 1:
					if (flagged)			return exact ? String_.Find_none : hi;
					else {
						itm_idx++;	// ++ to always take higher value when !exact???; EX: "ab,ad,af"
						if (itm_idx >= itm_ends_len) return String_.Find_none;	// NOTE: occurs when there is only 1 item
						flagged = true;
					}
					break;
				default:
					itm_idx += ((itm_dif / 2) * delta);
					break;
			}
		}
	}
}
/*
NOTE_1:xdat format
line 0 : delimited String of article lengths; EX: "00012|00004|00005\n"
line 1+: articles

pseudo example: (note that ints/dates will be replaced with base85 variants)
== BOF ==
00025|00024|00026
2006-01-01	Ttl1	Abcd
2006-02-01	Ttl2	Abc
2006-03-01	Ttl3	Abcde
== EOF ==

other notes:
. itm_len is entire length of article including text, title, date and any other fields
. line 0 uses len instead of bgn or end b/c len is independent (single len can be changed without having to recalculate entire array)
. however, note that in memory, itm_end_ary will be stored; this will make article extraction quicker: getting nth article means getting nth item in array;
. Parse is written for speed, not correctness; if correctness is needed, write a separate method that validates and call it before calling parse
*/
