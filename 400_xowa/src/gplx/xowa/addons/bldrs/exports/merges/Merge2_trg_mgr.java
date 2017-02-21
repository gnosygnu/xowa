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
package gplx.xowa.addons.bldrs.exports.merges; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*;
import gplx.dbs.*; import gplx.xowa.addons.bldrs.exports.utls.*;
interface Merge2_trg_mgr {
	Merge2_trg_itm Cur();
	Merge2_trg_itm Cur_or_new(Merge_ctx ctx, Merge_prog_wkr prog_wkr, Xow_wiki wiki, int trg_db_id);
}
class Merge2_trg_mgr__heap implements Merge2_trg_mgr {
	private final    Merge2_heap_mgr heap_mgr;
	public Merge2_trg_mgr__heap(Merge2_heap_mgr heap_mgr) {this.heap_mgr = heap_mgr;}
	public Merge2_trg_itm Cur() {return heap_mgr.Cur();}
	public Merge2_trg_itm Cur_or_new(Merge_ctx ctx, Merge_prog_wkr prog_wkr, Xow_wiki wiki, int trg_db_id) {
		Merge2_heap_db cur = heap_mgr.Cur();
		if (cur == null || cur.Idx() != trg_db_id) {
			cur = heap_mgr.Make_itm(ctx.Wiki(), trg_db_id);
		}
		return cur;
	}
}
class Merge2_trg_mgr__wiki implements Merge2_trg_mgr {
	private final    Split_tbl tbl;		
	public Merge2_trg_mgr__wiki(Split_tbl tbl) {this.tbl = tbl;}
	public Merge2_trg_itm Cur() {return cur;} private Merge2_trg_itm__wiki cur;
	public Merge2_trg_itm Cur_or_new(Merge_ctx ctx, Merge_prog_wkr prog_wkr, Xow_wiki wiki, int trg_db_id) {
		if (cur == null || cur.Idx() != trg_db_id) {
			Db_conn conn = tbl.Wiki_conn__get_or_new(wiki, trg_db_id);
			cur = new Merge2_trg_itm__wiki(trg_db_id, conn);
		}
		return cur;
	}
}
