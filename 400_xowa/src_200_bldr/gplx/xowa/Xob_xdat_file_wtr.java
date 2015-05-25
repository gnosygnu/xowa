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
package gplx.xowa; import gplx.*;
import gplx.ios.*; import gplx.xowa.tdbs.*;
public class Xob_xdat_file_wtr {
	public static Xob_xdat_file_wtr new_file_(int fil_max, Io_url root_dir)				{return new Xob_xdat_file_wtr(fil_max, root_dir, Io_stream_.Tid_raw);}
	public static Xob_xdat_file_wtr new_by_tid_(int fil_max, Io_url root_dir, byte dir_tid, byte tid) {return new Xob_xdat_file_wtr(fil_max, root_dir.GenSubDir(Xotdb_dir_info_.Tid_name(dir_tid) + Xotdb_dir_info.Wtr_dir(tid)), tid);}
	Xob_xdat_file_wtr(int fil_max, Io_url root_dir, byte wtr_tid) {
		this.fil_max = fil_max; 
		this.root_dir = root_dir;
		fil_ext = Xotdb_dir_info.Wtr_ext(wtr_tid);
		bfr = Bry_bfr.new_(fil_max);
		idx = new int[fil_max / 8];	// ASSUME: any given row must at least be 8 bytes long
		Url_gen(fil_idx);	// set 1st url
		wtr = Io_stream_wtr_.new_by_tid_(wtr_tid);
	}	int fil_max; Io_stream_wtr wtr; byte[] fil_ext;
	public int Fil_idx() {return fil_idx;} public Xob_xdat_file_wtr Fil_idx_(int v) {fil_idx = v; return this;} private int fil_idx;
	public int Ns_ord_idx() {return ns_ord_idx;} public Xob_xdat_file_wtr Ns_ord_idx_(int v) {ns_ord_idx = v; return this;} private int ns_ord_idx;	// NOTE: optional; needed for page cmd which will flush all wtrs, but needs ns_idx for stats
	@gplx.Internal protected Io_url Fil_url() {return fil_url;}
	@gplx.Internal protected int[] Idx() {return idx;} private int[] idx;
	public int Idx_pos() {return idx_pos;} private int idx_pos;
	public Bry_bfr Bfr() {return bfr;} Bry_bfr bfr;
	public Xob_xdat_file_wtr Add_idx(byte data_dlm) {return Add_idx_direct(bfr.Len(), data_dlm);}
	public Xob_xdat_file_wtr Add_idx_direct(int itm_len, byte data_dlm) {
		if (data_dlm != Byte_ascii.Nil) {	// write closing dlm for data_eny, unless Byte_.Null passed in
			bfr.Add_byte(data_dlm);
			++itm_len;
		}
		if (idx_pos + 1 > idx.length) Idx_resize(idx.length * 2);	// resize hdr if necessary
		idx[idx_pos++] = itm_len;
		return this;
	}
	public int Fil_len()					{return ((idx_pos    ) * Len_idx_itm) + bfr.Len();}
	public boolean FlushNeeded(int writeLen)	{return ((idx_pos + 1) * Len_idx_itm) + bfr.Len() + writeLen > fil_max;}	// +1: pending entry will create new idx_itm
	public void Flush(Gfo_usr_dlg usr_dlg) {
		if (bfr.Len() == 0) return;		// nothing to flush
		if (this.Fil_len() > fil_max)		// NOTE: data can exceed proscribed len; EX: wikt:Category for Italian nouns is 1 MB+
			usr_dlg.Log_many(GRP_KEY, "flush_err", "--ctg exceeds len: expd_len=~{0} actl_len=~{1} url=~{2}", this.Fil_len(), fil_max, fil_url.Xto_api());
		try {
			wtr.Url_(fil_url).Open();
			if (idx_pos > 0)				// write idx; NOTE: if idx written, then .xdat; else .csv
				FlushIdx(wtr);
			wtr.Write(bfr.Bfr(), 0, bfr.Len());	// write data;
			wtr.Flush();
		}
		finally {wtr.Rls();}
		Clear();
		this.Url_gen(++fil_idx);
	}
	public void FlushIdx(Io_stream_wtr wtr) {
		int idx_bry_len = (idx_pos * Len_idx_itm) + 1;	// 1=\n.length
		byte[] idx_bry = new byte[idx_bry_len];
		int prv_pos = 0;	// NOTE: prv_pos needed b/c idx[] stores data_end, not data_len
		for (int i = 0; i < idx_pos; i++) {
			int idx_bry_pos = i * Len_idx_itm;
			int cur_pos = idx[i];
			Base85_utl.XtoStrByAry(cur_pos - prv_pos, idx_bry, idx_bry_pos, Len_base85);
			idx_bry[idx_bry_pos + Len_base85] = Dlm_fld;
			prv_pos = cur_pos;
		}
		idx_bry[idx_bry_len - 1] = Byte_ascii.NewLine;
		wtr.Write(idx_bry, 0, idx_bry_len);
	}
//		public void Flush(Gfo_usr_dlg usr_dlg) {
//			if (bfr.Len() == 0) return;		// nothing to flush
//			if (this.Fil_len() > fil_max)		// NOTE: data can exceed proscribed len; EX: wikt:Category for Italian nouns is 1 MB+
//				usr_dlg.Log_many(GRP_KEY, "flush_err", "--ctg exceeds len: expd_len=~{0} actl_len=~{1} url=~{2}", this.Fil_len(), fil_max, fil_url.Xto_api());
//			IoStream stream = IoStream_.Null;
//			try {
//				stream = Io_mgr.I.OpenStreamWrite(fil_url);
//				if (idx_pos > 0)				// write idx; NOTE: if idx written, then .xdat; else .csv
//					FlushIdx(stream);
//				stream.Write(bfr.Bry(), 0, bfr.Len());	// write data;
//			}
//			finally {stream.Rls();}
//			Clear();
//			this.Url_gen(++fil_idx);
//		}
//		public void FlushIdx(IoStream stream) {
//			int idx_bry_len = (idx_pos * Len_idx_itm) + 1;	// 1=\n.length
//			byte[] idx_bry = new byte[idx_bry_len];
//			int prv_pos = 0;	// NOTE: prv_pos needed b/c idx[] stores data_end, not data_len
//			for (int i = 0; i < idx_pos; i++) {
//				int idx_bry_pos = i * Len_idx_itm;
//				int cur_pos = idx[i];
//				Base85_utl.XtoStrByAry(cur_pos - prv_pos, idx_bry, idx_bry_pos, Len_base85);
//				idx_bry[idx_bry_pos + Len_base85] = Dlm_idx;
//				prv_pos = cur_pos;
//			}
//			idx_bry[idx_bry_len - 1] = Byte_ascii.NewLine;
//			stream.Write(idx_bry, 0, idx_bry_len);
//		}
	static final int Len_idx_itm = 6, Len_base85 = 5;
	public void Clear() {idx_pos = 0; bfr.Clear();}
	public void Rls() {bfr.Rls(); idx = null;}
	public void Url_gen_add() {Url_gen(++fil_idx);}
	private void Url_gen(int newIdx) {fil_url = Xotdb_fsys_mgr.Url_fil(root_dir, newIdx, fil_ext);} Io_url fil_url; Io_url root_dir;
	private void Idx_resize(int newLen) {idx = (int[])Array_.Resize(idx, newLen);}
	static final String GRP_KEY = "xowa.bldr.xdat_wtr";
	private static final byte Dlm_fld = Byte_ascii.Pipe;		
}
class SortAlgo_quick {// quicksort
	Object[] ary; int ary_len; gplx.lists.ComparerAble comparer;
	public void Sort(Object[] ary, int ary_len, gplx.lists.ComparerAble comparer) {
		if (ary == null || ary_len < 2) return;
		this.ary = ary; this.ary_len = ary_len; this.comparer = comparer;
		Sort_recurse(0, ary_len - 1);
	}
	private void Sort_recurse(int lo, int hi) {
		int i = lo, j = hi;
		int mid_idx = lo + (hi-lo)/2;
		Object mid = ary[mid_idx];										// get mid itm
		while (i <= j) {												// divide into two lists
			while (comparer.compare(ary[i], mid) == CompareAble_.Less)	// if lhs.cur < mid, then get next from lhs	
				i++;				
			while (comparer.compare(ary[j], mid) == CompareAble_.More)	// if rhs.cur > mid, then get next from rhs	
				j--;

			// lhs.cur > mid && rhs.cur < mid; switch lhs.cur and rhs.cur; increase i and j
			if (i <= j) {
				Object tmp = ary[i];
				ary[i] = ary[j];
				ary[j] = tmp;
				i++;
				j--;
			}
		}
		if (lo < j) Sort_recurse(lo, j);
		if (i < hi) Sort_recurse(i, hi);
	}
	public static final SortAlgo_quick _ = new SortAlgo_quick(); SortAlgo_quick() {}
}

