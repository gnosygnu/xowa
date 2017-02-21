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
package gplx.core.ios; import gplx.*; import gplx.core.*;
import gplx.core.envs.*;
import gplx.core.lists.*;
public class Io_sort {
	public Io_sort Memory_max_(int v) {memory_max = v; return this;} private int memory_max = Io_mgr.Len_kb;
	public Io_url[] Split(Gfo_usr_dlg usr_dlg, Io_url_gen src_fil_gen, Io_url_gen trg_fil_gen, Io_line_rdr_key_gen key_gen) {return Split(usr_dlg, src_fil_gen, trg_fil_gen, Io_sort_split_itm_sorter.Instance, key_gen);}
	public Io_url[] Split(Gfo_usr_dlg usr_dlg, Io_url_gen src_fil_gen, Io_url_gen trg_fil_gen, ComparerAble row_comparer, Io_line_rdr_key_gen key_gen) {
		Io_line_rdr rdr = new Io_line_rdr(usr_dlg, src_fil_gen.Prv_urls()).Load_len_(4 * Io_mgr.Len_kb).Key_gen_(key_gen);	// NOTE: do not set load_len to memory_max; only want to load in increments
		List_adp rv = List_adp_.New();
		Bry_bfr bfr = Bry_bfr_.Reset(Const_bfr_max); int size_cur = 0;
		List_adp row_list = List_adp_.New();
		while (true) {
			boolean reading = rdr.Read_next(); 
			int size_row = rdr.Itm_pos_end() - rdr.Itm_pos_bgn();
			int size_new = size_cur + size_row;
			if (size_new > memory_max || !reading) {
				usr_dlg.Prog_none(GRP_KEY, "sort", "sorting chunk");
				row_list.Sort_by(row_comparer);
				Io_url trg_url = trg_fil_gen.Nxt_url();
				usr_dlg.Prog_one(GRP_KEY, "write", "writing chunk: ~{0}", trg_url.Raw());
				Split_flush(trg_url, row_list, memory_max, bfr, rv);
				row_list.Resize_bounds(16);	// MEM: resize bounds manually; note that each Flush-set may have widely disparately #of rows (EX: 1 row with a million pages vs. 1 million rows with 1 page)
				size_new = size_row; System_.Garbage_collect();
				if (!reading) break;
			}
			row_list.Add(new Io_sort_split_itm(rdr));
			size_cur = size_new;
		}
		rdr.Rls(); bfr.Rls(); System_.Garbage_collect();
		return (Io_url[])rv.To_ary(Io_url.class);
	}
	public void Merge(Gfo_usr_dlg usr_dlg, Io_url[] src_ary, ComparerAble comparer, Io_line_rdr_key_gen key_gen, Io_sort_cmd cmd) {
		BinaryHeap_Io_line_rdr heap = load_(usr_dlg, src_ary, comparer, key_gen, memory_max); if (heap.Len() == 0) return;//throw Err_.new_wo_type(Array_.To_str(src_ary));
		Io_line_rdr stream = null;
		cmd.Sort_bgn();
		while (true) {
			if (stream != null) {								// stream found on previous iteration; try to put it back on heap
				boolean read = stream.Read_next();
				if (read)										// stream has line; add to heap
					heap.Add(stream);
				else {											// stream is empty; rls
					stream.Rls();
					if (heap.Len() == 0) break;					// heap is empty; stop;
				}
			}
			stream = heap.Pop();								// note that .Pop removes stream from heap
			cmd.Sort_do(stream);
		}
		cmd.Sort_end();
		heap.Rls();
	}
	private static void Split_flush(Io_url url, List_adp list, int max, Bry_bfr tmp, List_adp url_list) {
		int len = list.Count();
		for (int i = 0; i < len; i++) {
			Io_sort_split_itm itm = (Io_sort_split_itm)list.Get_at(i);
			int add_len = itm.Row_end() - itm.Row_bgn();
			if ((tmp.Len() + add_len) > Const_bfr_max) Io_mgr.Instance.AppendFilBfr(url, tmp);
			tmp.Add_mid(itm.Bfr(), itm.Row_bgn(), itm.Row_end());
			itm.Rls();
		}
		Io_mgr.Instance.AppendFilBfr(url, tmp);
		list.Clear();
		url_list.Add(url);
	}
	private static BinaryHeap_Io_line_rdr load_(Gfo_usr_dlg usr_dlg, Io_url[] urls, ComparerAble comparer, Io_line_rdr_key_gen key_gen, int memory_max) {
		BinaryHeap_Io_line_rdr rv = new BinaryHeap_Io_line_rdr(comparer);
		int urls_len = urls.length;
		int default_load_len = memory_max / urls_len + 1;
		for (int i = 0; i < urls_len; i++) {
			Io_url url = urls[i];
			int file_len = (int)Io_mgr.Instance.QueryFil(url).Size();
			int load_len = file_len < default_load_len ? file_len : default_load_len;	// PERF.NOTE: 32 MB is default, but if file is 1 MB (or else) only create a bfr for 1 MB; using 32 MB will throw OutOfMemory on -Xmx 64m; DATE:20130112
			Io_line_rdr stream_bfr = new Io_line_rdr(usr_dlg, url).Key_gen_(key_gen).Load_len_(load_len);
			boolean read = stream_bfr.Read_next();
			if (read)	// guard against empty files
				rv.Add(stream_bfr);
		}
		return rv;
	}
	static final int Const_bfr_max = Io_mgr.Len_mb;
	static final String GRP_KEY = "xowa.bldr.io_sort";
}
