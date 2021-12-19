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
package gplx.xowa.bldrs.filters.dansguardians;
import gplx.dbs.*;
import gplx.frameworks.objects.Rls_able;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.libs.files.Io_url;
class Dg_log_mgr {
	private Db_conn conn;
	private final Dg_file_tbl		tbl_file = new Dg_file_tbl();
	private final Dg_rule_tbl		tbl_rule = new Dg_rule_tbl();
	private final Dg_page_score_tbl	tbl_page_score = new Dg_page_score_tbl();
	private final Dg_page_rule_tbl	tbl_page_rule = new Dg_page_rule_tbl();
	private final BryWtr tmp_bfr = BryWtr.NewAndReset(16);
	public void Init(Io_url db_url) {
		Db_conn_bldr_data conn_data = Db_conn_bldr.Instance.Get_or_new(db_url);
		conn = conn_data.Conn(); boolean created = conn_data.Created();
		tbl_file.Conn_(conn, created);
		tbl_rule.Conn_(conn, created);
		tbl_page_score.Conn_(conn, created);
		tbl_page_rule.Conn_(conn, created);
		conn.Txn_bgn("dansguardian");
	}
	public void Insert_file(Dg_file file) {tbl_file.Insert(file.Id(), file.Rel_path(), file.Lines().length);}
	public void Insert_rule(Dg_rule rule) {tbl_rule.Insert(rule.File_id(), rule.Id(), rule.Idx(), rule.Score(), Dg_word.Ary_concat(rule.Words(), tmp_bfr, AsciiByte.Tilde));}
	public void Insert_page_score(int log_tid, int page_id, int page_ns, byte[] page_ttl, int page_len, int page_score, int page_rule_count, int clude_type) {
		tbl_page_score.Insert(log_tid, page_id, page_ns, page_ttl, page_len, page_score, page_rule_count, clude_type);
	}
	public void Insert_page_rule(int log_tid, int page_id, int rule_id, int rule_score_total) {tbl_page_rule.Insert(log_tid, page_id, rule_id, rule_score_total);}
	public void Commit()	{conn.Txn_sav();}
	public void Rls()		{conn.Txn_end();}
}
class Dg_file_tbl {
	private String tbl_name = "dg_file"; private final DbmetaFldList flds = new DbmetaFldList();
	private String fld_file_id, fld_file_path, fld_rule_count;
	private Db_conn conn; private Db_stmt stmt_insert;
	public void Conn_(Db_conn new_conn, boolean created) {
		this.conn = new_conn; flds.Clear();
		fld_file_id			= flds.AddInt("file_id");
		fld_file_path		= flds.AddStr("file_path", 512);
		fld_rule_count		= flds.AddInt("rule_count");
		if (created) {
			Dbmeta_tbl_itm meta = Dbmeta_tbl_itm.New(tbl_name, flds
			, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "file_id", fld_file_id)
			);
			conn.Meta_tbl_create(meta);
		}
		stmt_insert = null;
	}
	public void Insert(int file_id, String file_path, int rule_count) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
		.Val_int(fld_file_id	, file_id)
		.Val_str(fld_file_path	, file_path)
		.Val_int(fld_rule_count	, rule_count)
		.Exec_insert();
	}
}
class Dg_rule_tbl implements Rls_able {
	private String tbl_name = "dg_rule"; private final DbmetaFldList flds = new DbmetaFldList();
	private String fld_file_id, fld_rule_id, fld_rule_idx, fld_rule_score, fld_rule_text;
	private Db_conn conn; private Db_stmt stmt_insert;
	public void Conn_(Db_conn new_conn, boolean created) {
		this.conn = new_conn; flds.Clear();
		fld_file_id			= flds.AddInt("file_id");
		fld_rule_id			= flds.AddInt("rule_id");
		fld_rule_idx		= flds.AddInt("rule_idx");
		fld_rule_score		= flds.AddInt("rule_score");
		fld_rule_text		= flds.AddStr("rule_text", 1024);
		if (created) {
			Dbmeta_tbl_itm meta = Dbmeta_tbl_itm.New(tbl_name, flds
			, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "pkey", fld_rule_id)
			);
			conn.Meta_tbl_create(meta);
		}
		conn.Rls_reg(this);
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	public void Insert(int file_id, int rule_id, int rule_idx, int rule_score, String rule_text) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
		.Val_int(fld_file_id		, file_id)
		.Val_int(fld_rule_id		, rule_id)
		.Val_int(fld_rule_idx		, rule_idx)
		.Val_int(fld_rule_score		, rule_score)
		.Val_str(fld_rule_text		, rule_text)
		.Exec_insert();
	}
}
class Dg_page_score_tbl implements Rls_able {
	private String tbl_name = "dg_page_score"; private final DbmetaFldList flds = new DbmetaFldList();
	private String fld_log_tid, fld_page_id, fld_page_ns, fld_page_ttl, fld_page_len, fld_page_score, fld_page_rule_count, fld_clude_type;
	private Db_conn conn; private Db_stmt stmt_insert;
	public void Conn_(Db_conn new_conn, boolean created) {
		this.conn = new_conn; flds.Clear();
		fld_log_tid			= flds.AddInt("log_tid");	// title or text
		fld_page_id			= flds.AddInt("page_id");
		fld_page_ns			= flds.AddInt("page_ns");
		fld_page_ttl		= flds.AddInt("page_ttl");
		fld_page_len		= flds.AddInt("page_len");
		fld_page_score		= flds.AddInt("page_score");
		fld_page_rule_count	= flds.AddInt("page_rule_count");
		fld_clude_type		= flds.AddInt("page_clude_type");
		if (created) {
			Dbmeta_tbl_itm meta = Dbmeta_tbl_itm.New(tbl_name, flds
			, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "pkey", fld_log_tid, fld_page_id)
			);
			conn.Meta_tbl_create(meta);
		}
		stmt_insert = null;
		conn.Rls_reg(this);
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	public void Insert(int log_tid, int page_id, int page_ns, byte[] page_ttl, int page_len, int page_score, int page_rule_count, int clude_type) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
		.Val_int(fld_log_tid		, log_tid)
		.Val_int(fld_page_id		, page_id)
		.Val_int(fld_page_ns		, page_ns)
		.Val_bry_as_str(fld_page_ttl, page_ttl)
		.Val_int(fld_page_len		, page_len)
		.Val_int(fld_page_score		, page_score)
		.Val_int(fld_page_rule_count, page_rule_count)
		.Val_int(fld_clude_type		, clude_type)
		.Exec_insert();
	}
}
class Dg_page_rule_tbl implements Rls_able {
	private String tbl_name = "dg_page_rule"; private final DbmetaFldList flds = new DbmetaFldList();
	private String fld_log_tid, fld_page_id, fld_rule_id, fld_rule_score_total;
	private Db_conn conn; private Db_stmt stmt_insert;
	public void Conn_(Db_conn new_conn, boolean created) {
		this.conn = new_conn; flds.Clear();
		fld_log_tid				= flds.AddInt("log_tid");	// title or text
		fld_page_id				= flds.AddInt("page_id");
		fld_rule_id				= flds.AddInt("rule_id");
		fld_rule_score_total	= flds.AddInt("rule_score_total");
		if (created) {
			Dbmeta_tbl_itm meta = Dbmeta_tbl_itm.New(tbl_name, flds
			, Dbmeta_idx_itm.new_unique_by_tbl(tbl_name, "pkey", fld_log_tid, fld_page_id, fld_rule_id)
			);
			conn.Meta_tbl_create(meta);
		}
		stmt_insert = null;
		conn.Rls_reg(this);
	}
	public void Rls() {
		stmt_insert = Db_stmt_.Rls(stmt_insert);
	}
	public void Insert(int log_tid, int page_id, int rule_id, int rule_score_total) {
		if (stmt_insert == null) stmt_insert = conn.Stmt_insert(tbl_name, flds);
		stmt_insert.Clear()
		.Val_int(fld_log_tid			, log_tid)
		.Val_int(fld_page_id			, page_id)
		.Val_int(fld_rule_id			, rule_id)
		.Val_int(fld_rule_score_total	, rule_score_total)
		.Exec_insert();
	}
}
