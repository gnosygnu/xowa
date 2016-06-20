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
public class Merge2_mgr {
	public final    Merge_ctx ctx;
	private final    Ordered_hash wkr_hash;
	
	public Merge2_mgr() {
		this.ctx = new Merge_ctx();
		this.wkr_hash = Make_wkrs
		( new Merge2_wkr__heap_one(Split_tbl_.Page)
		, new Merge2_wkr__heap_lot(Split_tbl_.Html)
		, new Merge2_wkr__heap_one(Split_tbl_.Srch_word)
		, new Merge2_wkr__heap_lot(Split_tbl_.Srch_link)
		, new Merge2_wkr__heap_one(Split_tbl_.Fsdb_fil)
		, new Merge2_wkr__heap_one(Split_tbl_.Fsdb_thm)
		, new Merge2_wkr__heap_one(Split_tbl_.Fsdb_org)
		, new Merge2_wkr__heap_lot(Split_tbl_.Fsdb_bin)
		);
	}
	public void Prog_wkr_(Merge_prog_wkr prog_wkr) {this.prog_wkr = prog_wkr;}	private Merge_prog_wkr prog_wkr;
	public void Merge_core(Xow_wiki wiki, Io_url src_url) {
		Db_conn src_conn = Db_conn_bldr.Instance.Get_or_autocreate(false, src_url);
		new Merge_wkr__core().Copy_to_temp(null, wiki, src_conn);
		Gfo_invk_.Invk_by_val(wiki.App().Wiki_mgri(), gplx.xowa.wikis.Xoa_wiki_mgr_.Invk__import_by_url, src_url);
		src_conn.Rls_conn();	// NOTE: must close conn else pack_conn will stay open
		// fails b/c no Main_Page; Gfo_invk_.Invk_by_msg(wiki.App().Gui__tab_mgr(), gplx.xowa.guis.tabs.Xog_tab_mgr_.Invk__new_tab, GfoMsg_.new_cast_("").Add("focus", true).Add("site", wiki.Domain_str()).Add("page", String_.new_u8(wiki.Props().Main_page())));
	}
	public void Merge_data(Xow_wiki wiki, Io_url src_url, int idx_cur) {
		long all_time_bgn = gplx.core.envs.Env_.TickCount();
		wiki.Init_by_wiki();
		Db_conn src_conn = Db_conn_bldr.Instance.Get_or_autocreate(false, src_url);
		ctx.Init(wiki, src_conn);

		// merge data
		int hash_len = wkr_hash.Len();
		for (int i = 0; i < hash_len; ++i) {
			if (prog_wkr.Canceled()) break;
			Merge2_wkr wkr = (Merge2_wkr)wkr_hash.Get_at(i);
			// if (prog_wkr.Checkpoint__skip_wkr(src_url, wkr.Tbl_name())) continue;
			long wkr_time_bgn = gplx.core.envs.Env_.TickCount();
			wkr.Merge_data(ctx, prog_wkr);
			Gfo_log_.Instance.Info("merge.wkr.done", "data", src_url.NameAndExt() + "|" + wkr.Tbl().Tbl_name() + "|" + gplx.core.envs.Env_.TickCount_elapsed_in_frac(wkr_time_bgn));
		}
		if (ctx.Heap__copy_to_wiki()) ctx.Heap__increment_nxt();
		Gfo_log_.Instance.Info("merge.wkr.done", "data", src_url.NameAndExt() + "|-1|" + gplx.core.envs.Env_.TickCount_elapsed_in_frac(all_time_bgn));
		src_conn.Rls_conn();	// NOTE: must close conn else pack_conn will stay open
	}
	private static Ordered_hash Make_wkrs(Merge2_wkr... wkrs) {
		Ordered_hash rv = Ordered_hash_.New();
		for (Merge2_wkr wkr : wkrs)
			rv.Add(wkr.Tbl().Tbl_name(), wkr);
		return rv;
	}
}
