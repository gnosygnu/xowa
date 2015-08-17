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
package gplx.stores; import gplx.*;
import gplx.core.criterias.*;
import gplx.dbs.*; import gplx.dbs.qrys.*;
public class DbMaprWtr extends DataWtr_base implements DataWtr {
	public void InitWtr(String key, Object val) {}
	@Override public Object StoreRoot(SrlObj root, String key) {
		mgr = (DbMaprMgr)this.EnvVars().Get_by_or_fail(DbMaprWtr.Key_Mgr);
		DbMaprWtrUtl.PurgeObjTree(root, mgr, conn);
		WriteGfoObj(root, mgr.Root());
		mgr.Clear();
		return null;
	}
	@Override public void SrlList(String subPropKey, List_adp list, SrlObj subProto, String itmKey) {
		DbMaprItm ownerMapr = (DbMaprItm)mgr.MaprStack().Get_at_last();
		DbMaprItm subMapr = ownerMapr.Subs_get(subPropKey);

		for (Object subObj : list) {
			SrlObj sub = (SrlObj)subObj;
			WriteGfoObj(sub, subMapr);
		}
	}
	void WriteGfoObj(SrlObj gobj, DbMaprItm mapr) {
		mgr.EnvStack_add(mapr, gobj);
		this.WriteNodeBgn(mapr.TableName());
		this.WriteContextFlds();
		gobj.SrlObj_Srl(this);
		this.WriteNodeEnd();
		mgr.EnvStack_del(mapr, gobj);
	}
	void WriteContextFlds() {
		int maprStackCount = mgr.MaprStack().Count() - 1; // -1 b/c current is added to stack
		for (int i = 0; i < maprStackCount; i ++) {
			DbMaprItm mapr = (DbMaprItm)mgr.MaprStack().Get_at(i);
			SrlObj gobj = (SrlObj)mgr.OwnerStack().Get_at(i);
			for (Object argObj : mapr.ContextFlds()) {
				DbMaprArg arg = (DbMaprArg)argObj;
				Object argVal = GfoInvkAble_.InvkCmd((GfoInvkAble)gobj, arg.ObjProp());
				this.WriteDataVal(arg.DbFld(), argVal);
			}					
		}
	}
	@Override public void WriteNodeBgn(String v) {
		if (insertCmd != null) insertCmd.Exec_qry(conn);		// occurs for nodes; ex: new title starts; commit changes for own disc
		curTableName = v;
		insertCmd = null;
	}
	@Override public void WriteData(String name, Object val) {
		DbMaprItm ownerMapr = (DbMaprItm)mgr.MaprStack().Get_at_last();
		String fld = ""; try {fld = ownerMapr.Flds_get(name).DbFld();} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to fetch fld from mapr", "key", name);}
		WriteDataVal(fld, val);
	}
	void WriteDataVal(String fld, Object val) {
		if (insertCmd == null) insertCmd = Db_qry_.insert_(curTableName);
		if (ClassAdp_.Eq_typeSafe(val, String.class))
			insertCmd.Arg_obj_type_(fld, val, Db_val_type.Tid_varchar);
		else
			insertCmd.Arg_obj_(fld, val);
	}
	@Override public void WriteNodeEnd() {
		if (insertCmd != null) insertCmd.Exec_qry(conn);		// occurs for nodes and leaves; for nodes, insertCmd will be null (committed by last leaf)
		insertCmd = null;
	}
	public void WriteTableBgn(String name, GfoFldList fields) {}
	public void WriteLeafBgn(String leafName) {}
	public void WriteLeafEnd() {}
	public void Clear() {}
	public String To_str() {return "";}
	@Override public SrlMgr SrlMgr_new(Object o) {return new DbMaprWtr();}
	DbMaprMgr mgr; Db_conn conn; String curTableName; Db_qry_insert insertCmd;		
	public static DbMaprWtr new_by_url_(Db_conn_info url) {
		DbMaprWtr rv = new DbMaprWtr();
		rv.conn = Db_conn_pool.I.Get_or_new(url);
		return rv;
	}	DbMaprWtr() {}
	public static final String Key_Mgr = "DbMapr.mgr";
}
class DbMaprWtrUtl {
	public static void PurgeObjTree(SrlObj root, DbMaprMgr mgr, Db_conn conn) {
		Criteria crt = MakeCriteria(root, mgr.RootIndexFlds());
		PurgeObj(mgr.Root(), crt, conn);
	}
	static Criteria MakeCriteria(SrlObj root, DbMaprArg[] objRootIdxFlds) {
		Criteria rv = null;
		for (DbMaprArg arg : objRootIdxFlds) {
			Object argVal = GfoInvkAble_.InvkCmd((GfoInvkAble)root, arg.ObjProp());
			Criteria cur = Db_crt_.eq_(arg.DbFld(), argVal);
			rv = (rv == null) ? cur : Criteria_.And(rv, cur);
		}
		return rv;
	}
	static void PurgeObj(DbMaprItm mapr, Criteria crt, Db_conn conn) {
		Db_qry_.delete_(mapr.TableName(), crt).Exec_qry(conn);
		for (Object subObj : mapr.Subs()) {
			DbMaprItm sub = (DbMaprItm)subObj;
			PurgeObj(sub, crt, conn);
		}
	}
}
