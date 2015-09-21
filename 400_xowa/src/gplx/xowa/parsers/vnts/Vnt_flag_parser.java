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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
class Vnt_flag_parser implements gplx.core.brys.Bry_split_wkr {
	private final Hash_adp_bry flag_regy = Vnt_flag_itm_.Regy;
	private final Hash_adp_bry vnt_regy = Hash_adp_bry.cs();
	private final boolean[] flag_ary = new boolean[Vnt_flag_itm_.Tid__max];
	private int count = 0;
	public int Count() {return count;}
	public boolean Get(int tid) {return flag_ary[tid];}
	public void Set_y(int tid) {flag_ary[tid] = Bool_.Y;}
	public void Set_y_many(int... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i)
			flag_ary[ary[i]] = Bool_.Y;
	}
	public void Set_n(int tid) {flag_ary[tid] = Bool_.N;}
	public void Limit(int tid) {
		for (int i = 0; i < Vnt_flag_itm_.Tid__max; ++i) {
			if (i != tid) flag_ary[i] = false;
		}
	}
	public boolean Limit_if_exists(int tid) {
		boolean exists = flag_ary[tid]; if (!exists) return false;
		for (int i = 0; i < Vnt_flag_itm_.Tid__max; ++i) {
			if (i != tid) flag_ary[i] = false;
		}
		return true;
	}
	public boolean Limit_if_exists_vnts() {
		return false;
	}
	public void Clear() {
		count = 0;
		for (int i = 0; i < Vnt_flag_itm_.Tid__max; ++i)
			flag_ary[i] = false;
	}
	public void Parse(byte[] src, int src_bgn, int src_end) {
		this.Clear();
		Bry_split_.Split(src, Byte_ascii.Semic, true, this);
	}
	public int Split(byte[] src, int itm_bgn, int itm_end) {
		int flag_tid = flag_regy.Get_as_int_or(src, itm_bgn, itm_end, -1);
		if (flag_tid == -1) {
			int vnt_tid = vnt_regy.Get_as_int_or(src, itm_bgn, itm_end, -1);
			if (vnt_tid == -1) return Bry_split_.Rv__ok; // unknown flag; ignore
		}
		flag_ary[flag_tid] = true;
		++count;
		return Bry_split_.Rv__ok;
	}
}
