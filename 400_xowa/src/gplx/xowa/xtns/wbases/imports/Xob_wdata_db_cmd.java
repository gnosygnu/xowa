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
package gplx.xowa.xtns.wbases.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.dbs.engines.sqlite.*; import gplx.xowa.bldrs.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.origs.*;
import gplx.xowa.bldrs.wkrs.*;
import gplx.langs.jsons.*;
import gplx.xowa.langs.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.bldrs.cmds.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.core.*; import gplx.xowa.xtns.wbases.claims.*; import gplx.xowa.xtns.wbases.claims.itms.*;
public class Xob_wdata_db_cmd extends Xob_dump_mgr_base implements Xob_cmd {
	private Wdata_tbl_mgr tbl_mgr = new Wdata_tbl_mgr();
	private Wdata_wiki_mgr wdata_mgr; private Json_parser json_parser;
	private byte[] lang_key = Xol_lang_itm_.Key_en;
	public Xob_wdata_db_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	@Override public String Cmd_key() {return Xob_cmd_keys.Key_wbase_db;}
	@Override public byte Init_redirect() {return Bool_.N_byte;}	// json will never be found in a redirect
	@Override public int[] Init_ns_ary() {return Int_.Ary(Xow_ns_.Tid__main, Wdata_wiki_mgr.Ns_property);}
	@Override protected void Init_reset(Db_conn conn) {
		Db_cfg_tbl cfg_tbl = gplx.xowa.wikis.data.Xowd_cfg_tbl_.New(conn);
		cfg_tbl.Delete_all();
	}
	@Override protected Db_conn Init_db_file() {
		Xob_db_file tbl_file = Xob_db_file.New(wiki.Fsys_mgr().Root_dir(), "wdata_db.sqlite3");
		Db_conn conn = tbl_file.Conn();
		tbl_mgr.Init(conn);
		return conn;
	}
	@Override protected void Cmd_bgn_end() {
		wdata_mgr = bldr.App().Wiki_mgr().Wdata_mgr();
		json_parser = wdata_mgr.Jdoc_parser();
		tbl_mgr.Conn().Txn_bgn("bldr__wdata_db");
	}
	@Override public void Exec_pg_itm_hook(int ns_ord, Xow_ns ns, Xowd_page_itm page, byte[] page_src) {
		Json_doc jdoc = json_parser.Parse(page_src); if (jdoc == null) return; // not a json document
		Wdata_doc wdoc = new Wdata_doc(page.Ttl_page_db(), wdata_mgr, jdoc);
		tbl_mgr.Exec_insert_by_wdoc(lang_key, wdata_mgr, page.Id(), wdoc);
	}
	@Override public void Exec_commit_hook() {
		tbl_mgr.Conn().Txn_sav();
	}
	@Override public void Exec_end_hook() {
		tbl_mgr.Term(usr_dlg);
	}
}
class Wdata_tbl_mgr {
	private Wdata_tbl_base[] tbls; private int tbls_len;
	public Wdata_tbl_mgr() {
		tbls = new Wdata_tbl_base[] {label_tbl, alias_tbl, description_tbl, link_tbl, claim_tbl, claim_time_tbl, claim_geo_tbl};
		tbls_len = tbls.length;
	}
	public Db_conn Conn() {return conn;} private Db_conn conn;
	public Wdata_label_tbl Label_tbl() {return label_tbl;} private Wdata_label_tbl label_tbl = new Wdata_label_tbl();
	public Wdata_alias_tbl Alias_tbl() {return alias_tbl;} private Wdata_alias_tbl alias_tbl = new Wdata_alias_tbl();
	public Wdata_description_tbl Description_tbl() {return description_tbl;} private Wdata_description_tbl description_tbl = new Wdata_description_tbl();
	public Wdata_link_tbl Link_tbl() {return link_tbl;} private Wdata_link_tbl link_tbl = new Wdata_link_tbl();
	public Wbase_claim_tbl Claim_tbl() {return claim_tbl;} private Wbase_claim_tbl claim_tbl = new Wbase_claim_tbl();
	public Wbase_claim_time_tbl Claim_time_tbl() {return claim_time_tbl;} private Wbase_claim_time_tbl claim_time_tbl = new Wbase_claim_time_tbl();
	public Wbase_claim_geo_tbl Claim_geo_tbl() {return claim_geo_tbl;} private Wbase_claim_geo_tbl claim_geo_tbl = new Wbase_claim_geo_tbl();
	public void Init(Db_conn conn) {
		this.conn = conn;
		for (int i = 0; i < tbls_len; i++)
			tbls[i].Init(conn);
	}
	public void Exec_insert_by_wdoc(byte[] lang_key, Wdata_wiki_mgr wdata_mgr, int page_id, Wdata_doc wdoc) {
		for (int i = 0; i < tbls_len; i++)
			tbls[i].Exec_insert_by_wdoc(lang_key, wdata_mgr, page_id, wdoc);
	}
	public void Term(Gfo_usr_dlg usr_dlg) {
		conn.Txn_end();
		for (int i = 0; i < tbls_len; i++)
			tbls[i].Make_idxs(usr_dlg, conn);
	}
}
abstract class Wdata_tbl_base {
	public abstract String Tbl_name();
	public abstract String Tbl_create_sql();
	public abstract Db_idx_itm[] Idx_ary();
	public abstract String[] Fld_ary();
	@gplx.Virtual public void Exec_insert_by_wdoc(byte[] lang_key, Wdata_wiki_mgr wdata_mgr, int page_id, Wdata_doc wdoc) {}
	public void Make_tbl(Db_conn p) {Sqlite_engine_.Tbl_create(p, this.Tbl_name(), this.Tbl_create_sql());}
	public void Make_idxs(Gfo_usr_dlg usr_dlg, Db_conn p) {
		Sqlite_engine_.Idx_create(usr_dlg, p, this.Tbl_name(), this.Idx_ary());
	}
	public Db_stmt Make_insert_stmt(Db_conn p) {return Db_stmt_.new_insert_(p, this.Tbl_name(), this.Fld_ary());}
	public Db_stmt Insert_stmt() {return insert_stmt;} private Db_stmt insert_stmt;
	public void Init(Db_conn conn) {
		this.Make_tbl(conn);
		insert_stmt = this.Make_insert_stmt(conn);
	}
	public static void Exec_insert_kvs(Db_stmt stmt, int page_id, Ordered_hash hash) {
		int len = hash.Count();
		for (int i = 0; i < len; i++) {
			Json_kv kv = (Json_kv)hash.Get_at(i);
			stmt.Clear()
			.Val_int(page_id)
			.Val_bry_as_str(kv.Key().Data_bry())
			.Val_bry_as_str(kv.Val().Data_bry())
			.Exec_insert();
		}
	}
}
class Wdata_label_tbl extends Wdata_tbl_base {
	@Override public String Tbl_name() {return "wdata_label";}
	@Override public String Tbl_create_sql() {
		return String_.Concat_lines_nl
		(	"CREATE TABLE IF NOT EXISTS wdata_label"
		,	"( page_id             integer             NOT NULL"
		,	", lang_key            varchar(16)         NOT NULL"
		,	", val                 varchar(255)        NOT NULL"
		,	");"
		);
	}
	@Override public Db_idx_itm[] Idx_ary() {return new Db_idx_itm[] {Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS wdata_label__main ON wdata_label (page_id, lang_key);")};}
	@Override public String[] Fld_ary() {return new String[] {Fld_page_id, Fld_lang_key, Fld_val};}
	@Override public void Exec_insert_by_wdoc(byte[] lang_key, Wdata_wiki_mgr wdata_mgr, int page_id, Wdata_doc wdoc) {Exec_insert_kvs(this.Insert_stmt(), page_id, wdoc.Label_list());}
	private static final    String Fld_page_id = "page_id", Fld_lang_key  = "lang_key", Fld_val = "val";
}
class Wdata_alias_tbl extends Wdata_tbl_base {
	@Override public String Tbl_name() {return "wdata_alias";}
	@Override public String Tbl_create_sql() {
		return String_.Concat_lines_nl
		(	"CREATE TABLE IF NOT EXISTS wdata_alias"
		,	"( page_id             integer             NOT NULL"
		,	", lang_key            varchar(16)         NOT NULL"
		,	", val                 varchar(255)        NOT NULL"
		,	");"
		);
	}
	@Override public Db_idx_itm[] Idx_ary() {return new Db_idx_itm[] {Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS wdata_alias__main ON wdata_alias (page_id, lang_key);")};}
	@Override public String[] Fld_ary() {return new String[] {Fld_page_id, Fld_lang_key, Fld_val};}
	@Override public void Exec_insert_by_wdoc(byte[] lang_key, Wdata_wiki_mgr wdata_mgr, int page_id, Wdata_doc wdoc) {
		Ordered_hash hash = wdoc.Alias_list();
		int len = hash.Count();
		Db_stmt insert_stmt = this.Insert_stmt();
		for (int i = 0; i < len; i++) {
			Json_kv kv = (Json_kv)hash.Get_at(i);
			byte[] key = kv.Key().Data_bry();
			Json_grp val_grp = (Json_grp)kv.Val();
			int val_grp_len = val_grp.Len();
			for (int j = 0; j < val_grp_len; j++) {
				Json_itm val_itm = val_grp.Get_at(j);
				byte[] val = Bry_.Empty;
				if		(val_itm.Tid() == Json_itm_.Tid__str)
					val = val_itm.Data_bry();
				else if (val_itm.Tid() == Json_itm_.Tid__kv) {	// EX: q80 and de aliases
					val = ((Json_kv)val_itm).Val().Data_bry();
				}
				insert_stmt.Clear()
				.Val_int(page_id)
				.Val_bry_as_str(key)
				.Val_bry_as_str(val)
				.Exec_insert();
			}
		}
	}
	private static final    String Fld_page_id = "page_id", Fld_lang_key  = "lang_key", Fld_val = "val";
}
class Wdata_description_tbl extends Wdata_tbl_base {
	@Override public String Tbl_name() {return "wdata_description";}
	@Override public String Tbl_create_sql() {
		return String_.Concat_lines_nl
		(	"CREATE TABLE IF NOT EXISTS wdata_description"
		,	"( page_id             integer             NOT NULL"
		,	", lang_key            varchar(16)         NOT NULL"
		,	", val                 varchar(255)        NOT NULL"
		,	");"
		);
	}
	@Override public Db_idx_itm[] Idx_ary() {return new Db_idx_itm[] {Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS wdata_description__main ON wdata_description (page_id, lang_key);")};}
	@Override public void Exec_insert_by_wdoc(byte[] lang_key, Wdata_wiki_mgr wdata_mgr, int page_id, Wdata_doc wdoc) {Exec_insert_kvs(this.Insert_stmt(), page_id, wdoc.Descr_list());}
	@Override public String[] Fld_ary() {return new String[] {Fld_page_id, Fld_lang_key, Fld_val};}
	private static final    String Fld_page_id = "page_id", Fld_lang_key  = "lang_key", Fld_val = "val";
}
class Wdata_link_tbl extends Wdata_tbl_base {
	@Override public String Tbl_name() {return "wdata_link";}
	@Override public String Tbl_create_sql() {
		return String_.Concat_lines_nl
		(	"CREATE TABLE IF NOT EXISTS wdata_link"
		,	"( page_id             integer             NOT NULL"
		,	", wiki_key            varchar(255)        NOT NULL"
		,	", val                 varchar(255)        NOT NULL"
		,	");"
		);
	}
	@Override public Db_idx_itm[] Idx_ary() {return new Db_idx_itm[] {Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS wdata_link__main ON wdata_link (page_id, wiki_key);")};}
	@Override public String[] Fld_ary() {return new String[] {Fld_page_id, Fld_wiki_key, Fld_val};}
	@Override public void Exec_insert_by_wdoc(byte[] lang_key, Wdata_wiki_mgr wdata_mgr, int page_id, Wdata_doc wdoc) {
		Ordered_hash hash = wdoc.Slink_list();
		int len = hash.Count();
		Db_stmt insert_stmt = this.Insert_stmt();
		for (int i = 0; i < len; i++) {
			Json_kv kv = (Json_kv)hash.Get_at(i);
			byte[] key = kv.Key().Data_bry();
			Json_itm kv_val = kv.Val();
			byte[] val = Bry_.Empty;
			if (kv_val.Tid() == Json_itm_.Tid__str)
				val = kv_val.Data_bry();
			else {
				Json_nde val_nde = (Json_nde)kv.Val();
				Json_kv val_name_kv = (Json_kv)val_nde.Get_at(0);	// ASSUME: 1st item is always "name" kv; EX: "name":"Earth"
				val = val_name_kv.Val().Data_bry();
			}
			insert_stmt.Clear()
			.Val_int(page_id)
			.Val_bry_as_str(key)
			.Val_bry_as_str(val)
			.Exec_insert();
		}
	}
	private static final    String Fld_page_id = "page_id", Fld_wiki_key  = "wiki_key", Fld_val = "val";
}
class Wbase_claim_tbl extends Wdata_tbl_base {
	@Override public String Tbl_name() {return "wdata_claim";}
	@Override public String Tbl_create_sql() {
		return String_.Concat_lines_nl
			(	"CREATE TABLE IF NOT EXISTS wdata_claim"
			,	"( claim_id            integer             NOT NULL"
			,	", page_id             integer             NOT NULL"
			,	", prop_id             integer             NOT NULL"  // 60; P60
			,	", val_tid             smallint            NOT NULL"  // String;wikibase-entity-id;time;globecoordinate
			,	", entity_tid          smallint            NOT NULL"  // null;item
			,	", entity_id           integer             NOT NULL"  // null;123
			,	", val_text            varchar(255)        NOT NULL"
			,	", guid                varchar(64)         NOT NULL"
			,	", rank                integer             NOT NULL"
			,	", ref_count           integer             NOT NULL"
			,	", qual_count          integer             NOT NULL"
			,	");"
			);
	}
	@Override public Db_idx_itm[] Idx_ary() {
		return new Db_idx_itm[] 
		{ Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS wdata_claim__main ON wdata_claim (page_id, prop_id, val_tid, entity_tid);")
		};
	}
	@Override public String[] Fld_ary() {return new String[] {Fld_claim_id, Fld_page_id, Fld_prop_id, Fld_val_tid, Fld_entity_tid, Fld_entity_id, Fld_val_text, Fld_guid, Fld_rank, Fld_ref_count, Fld_qual_count};}
	private int next_claim_id = 0;
	private Xob_wdata_db_visitor visitor;
	@Override public void Exec_insert_by_wdoc(byte[] lang_key, Wdata_wiki_mgr wdata_mgr, int page_id, Wdata_doc wdoc) {
		if (visitor == null) visitor = new Xob_wdata_db_visitor(wdata_mgr);
		visitor.Init(lang_key);
		Ordered_hash list = wdoc.Claim_list();
		int list_len = list.Count();
		for (int i = 0; i < list_len; i++) {
			Wbase_claim_grp claim_grp = (Wbase_claim_grp)list.Get_at(i);
			int itms_len = claim_grp.Len();
			int entity_id = -1;
			byte[] claim_val = Bry_.Empty;
			for (int j = 0; j < itms_len; j++) {
				Wbase_claim_base claim = claim_grp.Get_at(j);
				claim.Welcome(visitor);
				claim_val = visitor.Rv();
				Exec_insert(++next_claim_id, page_id, claim_grp.Id(), claim.Val_tid(), claim.Snak_tid(), entity_id, claim_val, claim.Wguid(), claim.Rank_tid(), 0, 0);
			}
		}
	}
	public void Exec_insert(int claim_id, int page_id, int prop_id, byte val_tid, byte entity_tid, int entity_id, byte[] val_text, byte[] guid, int rank, int ref_count, int qual_count) {
		if (val_text == null) val_text = Bry_.Empty;
		if (guid == null) guid = Bry_.Empty;
		this.Insert_stmt().Clear()
		.Val_int(claim_id)
		.Val_int(page_id)
		.Val_int(prop_id)
		.Val_byte(val_tid)
		.Val_byte(entity_tid)
		.Val_int(entity_id)
		.Val_bry_as_str(val_text)
		.Val_bry_as_str(guid)
		.Val_int(rank)
		.Val_int(ref_count)
		.Val_int(qual_count)
		.Exec_insert();
	}
	private static final    String Fld_claim_id = "claim_id", Fld_page_id = "page_id", Fld_prop_id = "prop_id", Fld_val_tid = "val_tid", Fld_entity_tid = "entity_tid", Fld_entity_id = "entity_id", Fld_val_text = "val_text"
	, Fld_guid = "guid", Fld_rank = "rank", Fld_ref_count = "ref_count", Fld_qual_count = "qual_count"
	;
}
class Wbase_claim_time_tbl extends Wdata_tbl_base {
	@Override public String Tbl_name() {return "wdata_claim_time";}
	@Override public String Tbl_create_sql() {
		return String_.Concat_lines_nl
		(	"CREATE TABLE IF NOT EXISTS wdata_claim_time"
		,	"( claim_id            integer             NOT NULL"
		,	", time_val            varchar(64)         NOT NULL"  // -04540000000-01-01T00:00:00Z
		,	", time_tz             integer             NOT NULL"  // 0
		,	", time_before         integer             NOT NULL"  // 0
		,	", time_after          integer             NOT NULL"  // 0
		,	", time_precision      integer             NOT NULL"  // 2; number of digits
		,	", time_model          varchar(64)         NOT NULL"  // http:\/\/www.wikidata.org\/entity\/Q1985727
		,	");"
		);
	}
	@Override public Db_idx_itm[] Idx_ary() {
		return new Db_idx_itm[] { 
		  Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS wdata_claim_time__main ON wdata_claim_time (claim_id);")
		};
	}
	@Override public String[] Fld_ary() {return new String[] {Fld_claim_id, Fld_time_val, Fld_time_tz, Fld_time_before, Fld_time_after, Fld_time_precision, Fld_time_model};}
	public void Insert(Db_stmt stmt, int claim_id, byte[] time_val, int tz, int before, int after, int precision, byte[] model) {
		stmt.Clear()
		.Val_int(claim_id)
		.Val_bry_as_str(time_val)
		.Val_int(tz)
		.Val_int(before)
		.Val_int(after)
		.Val_int(precision)
		.Val_bry_as_str(model)
		.Exec_insert();
	}
	private static final    String Fld_claim_id = "claim_id", Fld_time_val = "time_val", Fld_time_tz = "time_tz", Fld_time_before = "time_before", Fld_time_after = "time_after", Fld_time_precision = "time_precision", Fld_time_model = "time_model";
}
class Wbase_claim_geo_tbl extends Wdata_tbl_base {
	@Override public String Tbl_name() {return "wdata_claim_geo";}
	@Override public String Tbl_create_sql() {
		return String_.Concat_lines_nl
			(	"CREATE TABLE IF NOT EXISTS wdata_claim_geo"
			,	"( claim_id            integer             NOT NULL"
			,	", geo_latitude        double              NOT NULL"  // 41.590833333333
			,	", geo_longitude       double              NOT NULL"  // -93.620833333333
			,	", geo_altitude        varchar(255)        NOT NULL"  // null
			,	", geo_precision       double              NOT NULL"  // 0.00027777777777778
			,	", geo_globe           integer             NOT NULL"  // http:\/\/www.wikidata.org\/entity\/Q2
			,	");"
			);
	}
	@Override public Db_idx_itm[] Idx_ary() {
		return new Db_idx_itm[]
		{ Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS wdata_claim_geo__main ON wdata_claim_geo (claim_id);")
		};
	}
	public void Insert(Db_stmt stmt, int claim_id, double latitude, double longitude, byte[] altitude, double precision, byte[] globe) {
		stmt.Clear()
		.Val_int(claim_id)
		.Val_double(latitude)
		.Val_double(longitude)
		.Val_bry_as_str(altitude)
		.Val_double(precision)
		.Val_bry_as_str(globe)
		.Exec_insert();
	}
	@Override public String[] Fld_ary() {return new String[] {Fld_claim_id, Fld_geo_latitude, Fld_geo_longitude, Fld_geo_altitude, Fld_geo_precision, Fld_geo_globe};}
	private static final    String Fld_claim_id = "claim_id", Fld_geo_latitude = "geo_latitude", Fld_geo_longitude = "geo_longitude", Fld_geo_altitude = "geo_altitude", Fld_geo_precision = "geo_precision", Fld_geo_globe = "geo_globe";
}
class Wdata_ref_tbl extends Wdata_tbl_base {
	@Override public String Tbl_name() {return "wdata_ref";}
	@Override public String Tbl_create_sql() {
		return String_.Concat_lines_nl
			(	"CREATE TABLE IF NOT EXISTS wdata_ref"
			,	"( ref_id              integer             NOT NULL"
			,	", page_id             integer             NOT NULL"
			,	", prop_id             integer             NOT NULL"  // 60; P60
			,	", val_tid             smallint            NOT NULL"  // String;wikibase-entity-id;time;globecoordinate
			,	", entity_tid          smallint            NOT NULL"  // null;item
			,	", entity_id           integer             NOT NULL"  // null;123
			,	", val_text            varchar(255)        NOT NULL"
			,	");"
			);
	}
	@Override public Db_idx_itm[] Idx_ary() {
		return new Db_idx_itm[] { 
		  Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS wdata_ref__main ON wdata_ref (page_id, prop_id, val_tid, entity_tid);")
		};
	}
	@Override public String[] Fld_ary() {return new String[] {Fld_ref_id, Fld_page_id, Fld_prop_id, Fld_val_tid, Fld_entity_tid, Fld_entity_id, Fld_val_text};}
	private static final    String Fld_ref_id = "ref_id", Fld_page_id = "page_id", Fld_prop_id = "prop_id", Fld_val_tid = "val_tid", Fld_entity_tid = "entity_tid", Fld_entity_id = "entity_id", Fld_val_text = "val_ext";
}
class Wdata_qual_tbl extends Wdata_tbl_base {
	@Override public String Tbl_name() {return "wdata_qual";}
	@Override public String Tbl_create_sql() {
		return String_.Concat_lines_nl
			(	"CREATE TABLE IF NOT EXISTS wdata_qual"
			,	"( qual_id             integer             NOT NULL"
			,	", page_id             integer             NOT NULL"
			,	", val_text            varchar(4096)       NOT NULL"
			,	");"
			);
	}
	@Override public Db_idx_itm[] Idx_ary() {
		return new Db_idx_itm[] { 
			Db_idx_itm.sql_("CREATE INDEX IF NOT EXISTS wdata_qual__main ON wdata_ref (qual_id, page_id);")
		};
	}
	@Override public String[] Fld_ary() {return new String[] {Fld_qual_id, Fld_page_id, Fld_val_text};}
	public void Insert(Db_stmt stmt, int qual_id, int page_id, byte[] val_text) {
		stmt.Clear()
		.Val_int(qual_id)
		.Val_int(page_id)
		.Val_bry_as_str(val_text)
		.Exec_insert();
	}
	private static final    String Fld_qual_id = "qual_id", Fld_page_id = "page_id", Fld_val_text = "val_text";
}
class Xob_wdata_db_visitor implements Wbase_claim_visitor {
	private final    Wdata_wiki_mgr wdata_mgr; private byte[] lang_key;
	public Xob_wdata_db_visitor(Wdata_wiki_mgr wdata_mgr) {this.wdata_mgr = wdata_mgr;}
	public void Init(byte[] lang_key) {this.lang_key = lang_key;}
	public byte[] Rv() {return rv;} private byte[] rv;
	public void Visit_str(Wbase_claim_string itm)						{rv = itm.Val_bry();}
	public void Visit_monolingualtext(Wbase_claim_monolingualtext itm)	{rv = Bry_.Add_w_dlm(Byte_ascii.Pipe, itm.Lang(), itm.Text());}
	public void Visit_quantity(Wbase_claim_quantity itm)				{rv = itm.Amount();}
	public void Visit_time(Wbase_claim_time itm)						{rv = itm.Time();}
	public void Visit_globecoordinate(Wbase_claim_globecoordinate itm)	{rv = Bry_.Add_w_dlm(Byte_ascii.Comma, itm.Lat(), itm.Lng());}
	public void Visit_system(Wbase_claim_value itm)					{rv = Bry_.Empty;}
	public void Visit_entity(Wbase_claim_entity itm) {
		Wdata_doc entity_doc = wdata_mgr.Doc_mgr.Get_by_xid_or_null(itm.Page_ttl_db());
		rv = entity_doc == null ? Bry_.Empty : entity_doc.Label_list__get(lang_key);
	}
}
