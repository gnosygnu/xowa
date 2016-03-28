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
package gplx.xowa.addons.searchs.searchers.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.searchs.*; import gplx.xowa.addons.searchs.searchers.*;
import gplx.dbs.*; import gplx.dbs.stmts.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.addons.searchs.searchers.crts.*; import gplx.xowa.addons.searchs.dbs.*; import gplx.xowa.addons.searchs.searchers.rslts.*;	
public class Srch_link_wkr_sql {
	private final    Db_stmt_mgr stmt_mgr = new Db_stmt_mgr(); 
	public void Clear() {stmt_mgr.Clear();}
	public String Dbg(Srch_search_ctx ctx, Db_attach_mgr attach_mgr, Srch_crt_itm sql_root) {
		stmt_mgr.Mode_is_stmt_(Bool_.N);
		Init(ctx, attach_mgr, sql_root);
		stmt_mgr.Mode_is_stmt_(Bool_.Y);
		String rv = Write(ctx, attach_mgr);
		stmt_mgr.Clear();
		return rv;
	}
	public void Init(Srch_search_ctx ctx, Db_attach_mgr attach_mgr, Srch_crt_itm sql_root) {
		synchronized (Fmt__link) {	// THREAD:must synchronized on static Object, else 2 wikis with simultaneous search commands will write to same fmtr
			stmt_mgr.Bfr().Add(Bry__page__bgn);
			Bld_where(ctx, sql_root);
			stmt_mgr.Bfr().Add(Bry__page__end);
		}
	}
	public String Write(Srch_search_ctx ctx, Db_attach_mgr attach_mgr) {
		String sql = stmt_mgr.Bfr().To_str_and_clear();
		try {
			// gplx.core.consoles.Console_adp__sys.Instance.Write_str_w_nl("attaching: " + String_.new_u8(ctx.Qry.Phrase.Lcase_wild) + " " + Int_.To_str(ctx.Score_rng.Score_bgn()) + " " + Int_.To_str(ctx.Score_rng.Score_end()) + " " + attach_mgr.List__to_str());
			sql = attach_mgr.Resolve_sql(sql);
		}
		catch (Exception e) {
			gplx.core.consoles.Console_adp__sys.Instance.Write_str_w_nl("attaching err: " + String_.new_u8(ctx.Qry.Phrase.Orig) + " " + Int_.To_str(ctx.Score_rng.Score_bgn()) + " " + Int_.To_str(ctx.Score_rng.Score_end())  + Err_.Message_lang(e));
		}
		return sql;
	}
	public Db_stmt Make(Srch_search_ctx ctx, Db_attach_mgr attach_mgr, Db_conn cur_link_conn) {
		String sql = Write(ctx, attach_mgr);
		attach_mgr.Attach();
		return cur_link_conn.Stmt_sql(sql);
	}
	public void Fill(Db_stmt stmt) {
		// gplx.core.consoles.Console_adp__sys.Instance.Write_str_w_nl(String_.new_u8(ctx.Qry.Phrase.Orig) + " " + Int_.To_str(ctx.Score_rng.Score_bgn()) + " " + Int_.To_str(ctx.Score_rng.Score_end()));
		stmt_mgr.Fill_stmt_and_clear(stmt);
	}
	private void Bld_where(Srch_search_ctx ctx, Srch_crt_itm node) {
		switch (node.Tid) {
			case Srch_crt_itm.Tid__word:
			case Srch_crt_itm.Tid__word_quote:		// NOTE: quoted word is treated as Eq, except Eq_id is set to "lowest" word_id
				Bld_leaf(ctx, node);
				break;
			case Srch_crt_itm.Tid__or:	
			case Srch_crt_itm.Tid__and:
				Srch_crt_itm[] subs = node.Subs;					
				int subs_len = subs.length;
				for (int i = 0; i < subs_len; ++i) {
					Srch_crt_itm sub = subs[i];
					if (sub.Tid == Srch_crt_itm.Tid__not) continue;	// do not build sql for NOT itms; EX: a + (b, c) + -d
					if (i != 0)
						stmt_mgr.Bfr().Add_str_a7(node.Tid == Srch_crt_itm.Tid__and ? "INTERSECT\n" : "UNION\n");
					Bld_where(ctx, sub);
				}
				break;
			case Srch_crt_itm.Tid__not:			break;		// never check database for NOT node
			case Srch_crt_itm.Tid__invalid:		break;		// should not happen
			default:							throw Err_.new_unhandled_default(node.Tid);
		}
	}
	private void Bld_leaf(Srch_search_ctx ctx, Srch_crt_itm node) {
		int node_idx = node.Idx;
		int node_sql_tid = node.Sql_data.Tid;
		int score_bgn = ctx.Score_rng.Score_bgn();
		int score_end = ctx.Score_rng.Score_end();
		Srch_word_tbl word_tbl = ctx.Tbl__word;
		stmt_mgr.Bfr().Add(Bry__link__bgn);
		switch (node_sql_tid) {
			case Srch_crt_sql.Tid__eq:		// EX: "earth"
				stmt_mgr.Add_crt_int(word_tbl.fld_id, node.Sql_data.Eq_id);
				stmt_mgr.Write_fmt(Fmt__word_id);
				break;
			case Srch_crt_sql.Tid__rng:		// EX: "earth*"
				stmt_mgr.Add_var_many(node_idx, "AND   ", "search_word__word_text__link_score_max__link_score_min");
				stmt_mgr.Add_crt_str(word_tbl.fld_text, node.Sql_data.Rng_bgn);
				stmt_mgr.Add_crt_str(word_tbl.fld_text, node.Sql_data.Rng_end);
				stmt_mgr.Add_crt_int(word_tbl.fld_link_score_max, score_bgn);
				stmt_mgr.Add_crt_int(word_tbl.fld_link_score_min, score_end);
				stmt_mgr.Write_fmt(Fmt__word_text__rng);
				break;
			case Srch_crt_sql.Tid__like:	// EX: "*earth"
				stmt_mgr.Add_var_many(node_idx, "WHERE ", "search_word__link_score_max__link_score_min");
				stmt_mgr.Add_crt_int(word_tbl.fld_link_score_max, score_bgn);
				stmt_mgr.Add_crt_int(word_tbl.fld_link_score_min, score_end);
				stmt_mgr.Add_crt_str(word_tbl.fld_text, node.Sql_data.Like);
				stmt_mgr.Write_fmt(Fmt__word_text__like);
				break;
		}
		Srch_link_tbl link_tbl = ctx.Tbl__link__ary[0];
		stmt_mgr.Add_crt_int(link_tbl.fld_link_score, score_bgn);
		stmt_mgr.Add_crt_int(link_tbl.fld_link_score, score_end);
		stmt_mgr.Write_fmt(Fmt__link);
	}
	private static final    byte[]
	  Bry__page__bgn = Bry_.new_a7(String_.Concat_lines_nl_skip_last
	( "SELECT  p.page_id, p.page_namespace, p.page_title, p.page_len, p.page_score, p.page_redirect_id"
	, "FROM    <page_db>page p"
	, "WHERE   p.page_id IN"
	, "("
	, ""
	))
	, Bry__page__end = Bry_.new_a7(")\n")
	, Bry__link__bgn = Bry_.new_a7(String_.Concat_lines_nl_skip_last
	( "SELECT  l.page_id"
	, "FROM    search_link l INDEXED BY search_link__word_id__link_score"
	, "WHERE   "
	))
	;
	private static final    String
	  Str__link__end = String_.Concat_lines_nl_skip_last
	( "AND     l.link_score >= ~{score_bgn}"
	, "AND     l.link_score <  ~{score_end}"
	, ""
	)
	, Str__word__text__bgn	= String_.Concat_lines_nl_skip_last
	( "l.word_id IN"
	, "("
	, "SELECT  w~{uid}.word_id"
	, "FROM    <word_db>search_word w~{uid} INDEXED BY ~{index}"
	, ""
	)
	, Str__word__text__rng	= "WHERE   w~{uid}.word_text >= ~{word_bgn} AND w~{uid}.word_text < ~{word_end}\n"
	, Str__word__text__like	= "AND     w~{uid}.word_text LIKE ~{word_like} ESCAPE '|'\n"
	, Str__word__text__mnx = String_.Concat_lines_nl_skip_last
	( "~{and}  w~{uid}.link_score_max >= ~{score_bgn}"
	, "AND     w~{uid}.link_score_min <  ~{score_end}"
	, ""
	);
	private static final    Bry_fmt 
	  Fmt__link				= Bry_fmt.Auto(Str__link__end)
	, Fmt__word_id			= Bry_fmt.Auto("l.word_id = ~{word_uid}\n")
	, Fmt__word_text__rng	= Bry_fmt.New(Str__word__text__bgn + Str__word__text__rng + Str__word__text__mnx  + ")\n", "uid", "and", "index", "word_bgn", "word_end", "score_bgn", "score_end")
	, Fmt__word_text__like	= Bry_fmt.New(Str__word__text__bgn + Str__word__text__mnx + Str__word__text__like + ")\n", "uid", "and", "index", "score_bgn", "score_end", "word_like")
	;
	public static final byte Like_escape_byte = Byte_ascii.Pipe;
}
