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
//namespace gplx.xowa.addons.apps.cfgs.dbs {
//	using gplx.dbs; using gplx.dbs.utls;
//	class Xocfg_data_itm : Db_tbl {
//		private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
//		private final    String fld_itm_id, fld_itm_val, fld_itm_date;
//		private final    Db_conn conn;
//		public Xocfg_data_itm(Db_conn conn) {
//			this.conn = conn;
//			this.tbl_name				= "cfg_data_itm";
//			this.fld_itm_id				= flds.Add_int("itm_id");			// EX: '1'
//			// ctx_id; app,wiki,ns
//			this.fld_itm_date			= flds.Add_str("itm_date", 32);		// EX: '20160901_010203'
//			this.fld_itm_val			= flds.Add_str("itm_val", 4096);	// EX: 'abc'
//			conn.Rls_reg(this);
//		}
//		public String Tbl_name() {return tbl_name;} private final    String tbl_name;
//		public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
//		public void Upsert(int itm_id, String itm_date, String itm_val) {
//			Db_tbl__crud_.Upsert(conn, tbl_name, flds, String_.Ary(fld_itm_id), itm_id, itm_date, itm_val);
//		}
//		public void Rls() {}
//	}
//	class Xocfg_meta_itm : Db_tbl {
//		private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
//		private final    String fld_grp_id, fld_itm_id, fld_itm_sort, fld_itm_key, fld_scope_id, fld_type_id, fld_itm_dflt;
//		private final    Db_conn conn;
//		public Xocfg_meta_itm(Db_conn conn) {
//			this.conn = conn;
//			this.tbl_name				= "cfg_meta_itm";
//			this.fld_grp_id				= flds.Add_int("grp_id");			// EX: '1'
//			this.fld_itm_id				= flds.Add_int("itm_id");			// EX: '2'
//			this.fld_itm_sort			= flds.Add_int("itm_sort");			// EX: '3'
//			this.fld_scope_id			= flds.Add_int("itm_scope_id");		// EX: '1'; REF: cfg_meta_scope; ENUM: app-only, wiki-only, ...
//			this.fld_type_id			= flds.Add_int("itm_type_id");		// EX: '1'; REF: cfg_meta_type; ENUM: int, String, ...
//			this.fld_itm_key			= flds.Add_str("itm_key", 255);		// EX: 'cfg_1'
//			this.fld_itm_dflt			= flds.Add_str("itm_dflt", 4096);	// EX: 'abc'
//			// display_name
//			// help_text
//			// locale
//			conn.Rls_reg(this);
//		}
//		public String Tbl_name() {return tbl_name;} private final    String tbl_name;
//		public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
//		public void Upsert(int grp_id, int itm_id, int itm_sort, int scope_id, int type_id, String itm_key, String itm_dflt) {
//			Db_tbl__crud_.Upsert(conn, tbl_name, flds, String_.Ary(fld_grp_id, fld_itm_id), grp_id, itm_id, itm_sort, scope_id, type_id, itm_key, itm_dflt);
//		}
//		public void Rls() {}
//	}
//	class Xocfg_meta_grp : Db_tbl {
//		private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
//		private final    String fld_grp_id, fld_grp_key;
//		private final    Db_conn conn;
//		public Xocfg_meta_grp(Db_conn conn) {
//			this.conn = conn;
//			this.tbl_name				= "cfg_meta_grp";
//			this.fld_grp_id				= flds.Add_int("grp_id");
//			this.fld_grp_key			= flds.Add_str("grp_key", 255);
//			// display
//			conn.Rls_reg(this);
//		}
//		public String Tbl_name() {return tbl_name;} private final    String tbl_name;
//		public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
//		public void Upsert(int grp_id, String grp_key) {
//			Db_tbl__crud_.Upsert(conn, tbl_name, flds, String_.Ary(fld_grp_id), grp_id, grp_key);
//		}
//		public void Rls() {}
//	}
//	class Xocfg_meta_grp_map : Db_tbl {
//		private final    Dbmeta_fld_list flds = new Dbmeta_fld_list();
//		private final    String fld_map_src, fld_map_trg, fld_map_sort;
//		private final    Db_conn conn;
//		public Xocfg_meta_grp_map(Db_conn conn) {
//			this.conn = conn;
//			this.tbl_name				= "cfg_meta_grp_map";
//			this.fld_map_src			= flds.Add_int("map_src");
//			this.fld_map_trg			= flds.Add_int("map_trg");
//			this.fld_map_sort			= flds.Add_int("map_sort");
//			conn.Rls_reg(this);
//		}
//		public String Tbl_name() {return tbl_name;} private final    String tbl_name;
//		public void Create_tbl() {conn.Meta_tbl_create(Dbmeta_tbl_itm.New(tbl_name, flds));}
//		public void Upsert(int map_src, int map_trg, int map_sort) {
//			Db_tbl__crud_.Upsert(conn, tbl_name, flds, String_.Ary(fld_map_src), map_src, map_trg, map_sort);
//		}
//		public void Rls() {}
//	}
///*
//EXEC cfg_select 'xowa.wiki.page.sync', 'scope'
//
//SELECT @grp_id = grp_id FROM grp WHERE key = 'xowa.wiki.page.sync';
//@aboe = SELECT * FROM grp_map WHERE owner_id = @grp_id ORDER BY sort
//SELECT * FROM meta_itm WHERE grp_id IN (@above)
//
//{{#grp}}
//  {{#itm_key}}
//  Coalesce({{#itm_val}}, {{#itm_dflt}})
//  {{#itm_dirty}}
//  {{#itm_notes}}
//{{#grp}}
//
//grp_0 -> Name of page
//grp_1 -> H2
//grp_2 -> H3
//...
//*/
//}
