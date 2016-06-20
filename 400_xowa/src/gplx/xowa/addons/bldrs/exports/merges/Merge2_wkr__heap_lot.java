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
package gplx.xowa.addons.bldrs.exports.merges; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*;
import gplx.dbs.*; import gplx.xowa.addons.bldrs.exports.utls.*;
public class Merge2_wkr__heap_lot extends Merge2_wkr__heap_base {
	private final    Merge2_copy_wkr__lot copy_wkr;
	private final    Merge2_trg_mgr trg_mgr__heap;
	public Merge2_wkr__heap_lot(Split_tbl tbl) {super(tbl);
		this.copy_wkr = new Merge2_copy_wkr__lot(tbl);
		this.trg_mgr__heap = new Merge2_trg_mgr__heap(this.Heap_mgr());
	}
	@Override protected void Copy_to_heap(Merge_ctx ctx, Merge_prog_wkr prog_wkr, Merge2_heap_mgr heap_mgr, Split_tbl tbl) {
		copy_wkr.Copy_by_sql("merging " + tbl.Tbl_name(), ctx, prog_wkr, ctx.Pack_conn(), null, trg_mgr__heap, Bool_.Y);
		// copy_wkr.Copy_entire_src(ctx, prog_wkr, ctx.Pack_conn(), null, trg_mgr__heap, Bool_.Y);
	}
	@Override protected void Copy_to_wiki(Merge_ctx ctx, Merge_prog_wkr prog_wkr, Merge2_heap_mgr heap_mgr, Split_tbl tbl) {
		if (ctx.Heap__copy_to_wiki())
			heap_mgr.Copy_to_wiki(ctx, prog_wkr, this);
	}
	@Override public void Copy_to_wiki(Merge_ctx ctx, Merge_prog_wkr prog_wkr, String tbl_name, Dbmeta_fld_list trg_flds, Db_conn src_conn, Merge2_trg_itm trg_db, String[] fld_pkeys) {
		copy_wkr.Copy_by_sql("merging " + tbl_name, ctx, prog_wkr, src_conn, trg_db, trg_mgr__heap, Bool_.N);
		// copy_wkr.Copy_entire_src(ctx, prog_wkr, src_conn, trg_db, trg_mgr__heap, Bool_.N);
	}
}
