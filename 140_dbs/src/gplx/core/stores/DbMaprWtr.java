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
package gplx.core.stores; import gplx.*; import gplx.core.*;
import gplx.core.criterias.*; import gplx.core.gfo_ndes.*;
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
				Object argVal = Gfo_invk_.Invk_by_key((Gfo_invk)gobj, arg.ObjProp());
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
		if (Type_adp_.Eq_typeSafe(val, String.class))
			insertCmd.Val_obj_type(fld, val, Db_val_type.Tid_varchar);
		else
			insertCmd.Val_obj(fld, val);
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
		rv.conn = Db_conn_pool.Instance.Get_or_new(url);
		return rv;
	}	DbMaprWtr() {}
	public static final    String Key_Mgr = "DbMapr.mgr";
}
class DbMaprWtrUtl {
	public static void PurgeObjTree(SrlObj root, DbMaprMgr mgr, Db_conn conn) {
		Criteria crt = MakeCriteria(root, mgr.RootIndexFlds());
		PurgeObj(mgr.Root(), crt, conn);
	}
	static Criteria MakeCriteria(SrlObj root, DbMaprArg[] objRootIdxFlds) {
		Criteria rv = null;
		for (DbMaprArg arg : objRootIdxFlds) {
			Object argVal = Gfo_invk_.Invk_by_key((Gfo_invk)root, arg.ObjProp());
			Criteria cur = Db_crt_.New_eq(arg.DbFld(), argVal);
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
