/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.tdbs.hives; import gplx.*; import gplx.xowa.*; import gplx.xowa.tdbs.*;
import gplx.lists.*;
public class Xowd_regy_mgr {
	public static final int Not_found = -1;
	public Xowd_regy_mgr() {}
	public Xowd_regy_mgr(Io_url fil) {this.Init(fil);}
	public Io_url Fil() {return fil;} Io_url fil;
	public void Init(Io_url fil) {this.fil = fil; files_ary = Xowd_hive_regy_itm.parse_fil_(ByteAry_fil._.Ini_file(fil));}
	public Xowd_hive_regy_itm[] Files_ary() {return files_ary;} private Xowd_hive_regy_itm[] files_ary;
	public void Clear() {files_ary = Xowd_hive_regy_itm.Ary_empty;}
	public int Files_find(byte[] key) {
		if (files_ary.length == 0) return Xowd_regy_mgr.Regy_null;	// NOTE: FindSlot does not accept empty ary; returning 0, b/c Find returns likely file_idx; EX: regy of 0|B|D and 1|F|H; A returns 0; Z returns 1
		return CompareAble_.FindSlot(comparer, files_ary, comparer_itm.End_(key));
	}	ComparerAble comparer = Xowd_ttl_file_comparer_end._; Xowd_hive_regy_itm comparer_itm = Xowd_hive_regy_itm.tmp_().Count_(1);
	public Xowd_hive_regy_itm Create(byte[] key) {
		int itm_idx = files_ary.length;
		files_ary = (Xowd_hive_regy_itm[])Array_.Resize(files_ary, itm_idx + 1);
		Xowd_hive_regy_itm rv = new Xowd_hive_regy_itm(itm_idx).Bgn_(key).End_(key).Count_(1);
		files_ary[itm_idx] = rv;
		return rv;
	}
	public Xowd_hive_regy_itm Update_add(int fil_idx, byte[] key) {
		Xowd_hive_regy_itm rv = files_ary[fil_idx];
		rv.Count_(rv.Count() + 1);
		if 		(Bry_.Compare(key, rv.Bgn()) < CompareAble_.Same)
			rv.Bgn_(key);
		else if (Bry_.Compare(key, rv.End()) > CompareAble_.Same)
			rv.End_(key);
		return rv;
	}
	public boolean Update_change(int fil_idx, byte[] old_key, byte[] new_key) {
		Xowd_hive_regy_itm rv = files_ary[fil_idx];
		boolean changed = false;
		if 		(Bry_.Eq(old_key, rv.Bgn())) {
			rv.Bgn_(new_key);
			changed = true;
		}
		else if (Bry_.Eq(old_key, rv.End())) {
			rv.End_(new_key);
			changed = true;
		}
		return changed;
	}
	public Xowd_hive_regy_itm Update_del(int fil_idx, byte[] key) {
		Xowd_hive_regy_itm itm = files_ary[fil_idx];
		itm.Count_(itm.Count() - 1);
		throw Err_.new_unimplemented();	// FUTURE: note that deletes are harder; rng ends could be deleted, so would need to open file and get new rng end
	}
	public void Save() {
		Bry_bfr bfr = Bry_bfr.new_();
		int len = files_ary.length;
		for (int i = 0; i < len; i++) {
			Xowd_hive_regy_itm itm = files_ary[i];
			itm.Srl_save(bfr);
		}
		Io_mgr.I.SaveFilBfr(fil, bfr);
	}
	public static final int Regy_null = -1;
}
