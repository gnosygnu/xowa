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
package gplx.xowa.wikis.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
class Xow_db_file_hash {
	private final    Ordered_hash hash = Ordered_hash_.New();
	public int Count_total() {return count_total;} private int count_total;
	public void Clear() {hash.Clear(); count_total = 0;}
	public Ordered_hash Get_by_tid_or_null(byte tid) {return (Ordered_hash)hash.Get_by(tid);}
	public void Add_or_new(Xow_db_file file) {
		byte tid = file.Tid();
		Ordered_hash tids = (Ordered_hash)hash.Get_by(tid);
		if (tids == null) {
			tids = Ordered_hash_.New();
			hash.Add(tid, tids);
		}
		tids.Add(file.Id(), file);
		++count_total;
	}
	public void Del(Xow_db_file file) {
		Ordered_hash tids = (Ordered_hash)hash.Get_by(file.Tid());
		if (tids == null) throw Err_.new_wo_type("unknown file.tid", "url", file.Url());
		if (!tids.Has(file.Id())) throw Err_.new_wo_type("unknown file.id", "url", file.Url());
		tids.Del(file.Id());
		--count_total;
	}
	public int Count_of_tid(byte tid) {
		Ordered_hash tids = (Ordered_hash)hash.Get_by(tid);
		return tids == null ? 0 : tids.Count();
	}
}
