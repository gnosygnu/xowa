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
package gplx.fsdb.meta; import gplx.*; import gplx.fsdb.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.xowa.*;
import gplx.fsdb.meta.*;
public class Fsm_cfg_mgr {		
	private final Db_cfg_tbl cfg_tbl = new Db_cfg_tbl();
	private final HashAdp grps = HashAdp_.new_();
	public int Next_id()			{return next_id++;} private int next_id = 1;
	public boolean Schema_thm_page()	{return schema_thm_page;} private boolean schema_thm_page = true;
	public boolean Patch_next_id()		{return patch_next_id;} private boolean patch_next_id = true;
	public void Patch_next_id_exec(int last_id) {
		if (last_id >= next_id)
			next_id = last_id + 1;
		cfg_tbl.Insert(Grp_core, Key_patch_next_id, "y");
	}
	public void Txn_save() {
		this.Update_next_id();
	}
	public void Rls() {cfg_tbl.Rls();}
	private void Update_next_id()	{cfg_tbl.Update("core", "next_id", Int_.Xto_str(next_id));}
	public Fsm_cfg_mgr Update(String grp, String key, String new_val) {
		String cur_val = cfg_tbl.Select_as_str_or(grp, key, null);
		if (cur_val == null)
			cfg_tbl.Insert(grp, key, new_val);
		else
			cfg_tbl.Update(grp, key, new_val);
		return this;
	}
	public Db_cfg_grp Grps_get_or_load(String grp_key) {
		Db_cfg_grp grp = (Db_cfg_grp)grps.Fetch(grp_key);
		if (grp == null) {
			grp = cfg_tbl.Select_as_grp(grp_key);
			grps.Add(grp_key, grp);
		}
		return grp;
	}
	public Db_cfg_grp Grps_get_or_add(String grp_key) {	// TEST:
		Db_cfg_grp grp = (Db_cfg_grp)grps.Fetch(grp_key);
		if (grp == null) {
			grp = new Db_cfg_grp(grp_key);
			grps.Add(grp_key, grp);
		}
		return grp;
	}
	public static Fsm_cfg_mgr new_() {return new Fsm_cfg_mgr();}
	public void Init_for_db(Db_conn conn, boolean created, boolean schema_is_1) {
		cfg_tbl.Conn_(conn, created, schema_is_1, Fsm_abc_mgr.Cfg_tbl_v1, Fsm_abc_mgr.Cfg_tbl_v2);
		if (created) {
			cfg_tbl.Insert(Fsm_cfg_mgr.Grp_core, Fsm_cfg_mgr.Key_next_id				, "1");	// start next_id at 1
			cfg_tbl.Insert(Fsm_cfg_mgr.Grp_core, Fsm_cfg_mgr.Key_schema_thm_page		, "y");	// new dbs automatically have page and time in fsdb_xtn_tm
			cfg_tbl.Insert(Fsm_cfg_mgr.Grp_core, Fsm_cfg_mgr.Key_patch_next_id			, "y");	// new dbs automatically have correct next_id
		}
		else {
			Db_cfg_grp core_grp = Grps_get_or_load(Grp_core);
			this.next_id			= core_grp.Get_int_or(Key_next_id, -1); if (next_id == -1) throw Err_.new_("next_id not found in fsdb_cfg");
			this.schema_thm_page	= core_grp.Get_yn_or_n(Key_schema_thm_page);
			this.patch_next_id		= core_grp.Get_yn_or_n(Key_patch_next_id);
		}
	}
	public static final String Grp_core = "core";
	public static final String Key_next_id = "next_id", Key_schema_thm_page = "schema.thm.page", Key_patch_next_id = "patch.next_id";
}
