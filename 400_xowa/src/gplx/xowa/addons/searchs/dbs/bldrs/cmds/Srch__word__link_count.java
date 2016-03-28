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
package gplx.xowa.addons.searchs.dbs.bldrs.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.searchs.*; import gplx.xowa.addons.searchs.dbs.*; import gplx.xowa.addons.searchs.dbs.bldrs.*;
import gplx.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.addons.sqlite_utils.bldrs.*;
import gplx.xowa.addons.searchs.dbs.*;
public class Srch__word__link_count extends Xob_cmd__base implements Xob_cmd {
	private int score_multiplier = Srch_search_addon.Score_max;
	public Srch__word__link_count(Xob_bldr bldr, Xowe_wiki wiki) {super(bldr, wiki);}
	@Override public String Cmd_key() {return Xob_cmd_keys.Key_search__word__link_count;}
	@Override public void Cmd_run() {
		wiki.Init_assert();

		Srch_db_mgr search_db_mgr = Srch_search_addon.Get(wiki).Db_mgr();
		Srch_word_tbl word_tbl = search_db_mgr.Tbl__word();
		Db_conn word_conn = word_tbl.conn;
		String percentile_tbl = "search_word__link_count";
		Sqlite_percentile_cmd percentile_cmd = new Sqlite_percentile_cmd(bldr, wiki).Init_by_conn(word_conn, percentile_tbl, score_multiplier, String_.Concat_lines_nl_skip_last
		( "SELECT   sw.word_id, sw.link_count"
		, "FROM     search_word sw"
		, "ORDER BY sw.link_count"	// NOTE: ORDER BY is needed b/c INSERT into AUTO INCREMENT table
		));
		percentile_cmd.Cmd_run();

		word_conn.Exec_sql("finalizing search_word.link_count_score", String_.Concat_lines_nl_skip_last
		( "UPDATE   search_word"
		, "SET      link_count_score = (SELECT tpl.row_score FROM search_word__link_count tpl WHERE tpl.row_key = search_word.word_id)"
		));
		word_conn.Meta_tbl_drop(percentile_tbl);
		word_conn.Meta_idx_create(Dbmeta_idx_itm.new_normal_by_tbl(word_tbl.tbl_name, "link_count_score__word_text", word_tbl.fld_link_count_score, word_tbl.fld_text));

		int link_count_score_cutoff = word_conn.Exec_select_as_int("SELECT Cast(Min(link_count_score) AS int) AS val FROM search_word WHERE link_count > " + Int_.To_str(Srch_db_cfg_.Link_count_score_cutoff), 0);
		Srch_db_cfg_.Update__bldr__word(search_db_mgr.Tbl__cfg(), search_db_mgr.Cfg(), percentile_cmd.count, score_multiplier, link_count_score_cutoff);
		word_conn.Env_vacuum();
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {return this;}
}
