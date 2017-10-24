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
import gplx.core.criterias.*; import gplx.dbs.*; import gplx.core.gfo_ndes.*;
public class DbMaprRdr extends DataRdr_base implements SrlMgr {
	@Override public String NameOfNode() {return "DbMaprRdr";}
	@Override public Object StoreRoot(SrlObj subProto, String key) {
		mgr = (DbMaprMgr)this.EnvVars().Get_by_or_fail(DbMaprWtr.Key_Mgr);
		DbMaprItm rootMapr = mgr.Root();

		GfoNde tbl = GetTbl(rootMapr, rootCrt); int subsCount = tbl.Subs().Count(); if (subsCount == 0) return null; if (subsCount > 1) throw Err_.new_wo_type("criteria returned > 1 row", "criteria", rootCrt.To_str(), "subsCount", subsCount);
		SrlObj root = subProto.SrlObj_New(null);
		mgr.EnvStack_add(rootMapr, root); RowStack_add(tbl, 0);
		root.SrlObj_Srl(this);
		mgr.Clear(); rowStack.Clear();
		return root;
	}
	@Override public void SrlList(String subPropKey, List_adp list, SrlObj subProto, String itmKey) {
		DbMaprItm curMapr = (DbMaprItm)mgr.MaprStack().Get_at_last();
		DbMaprItm subMapr = curMapr.Subs_get(subPropKey);
		list.Clear();

		Criteria crit = MakeCrt(mgr, subMapr);
		GfoNde tbl = GetTbl(subMapr, crit);
		int tblLen = tbl.Subs().Count();
		for (int i = 0; i < tblLen; i++) {
			SrlObj sub = (SrlObj)subProto.SrlObj_New(null);
			GfoNde subRow = tbl.Subs().FetchAt_asGfoNde(i);
			mgr.EnvStack_add(subMapr, sub); rowStack.Add(subRow);
			sub.SrlObj_Srl(this); list.Add(sub);
			mgr.EnvStack_del(subMapr, sub); List_adp_.Del_at_last(rowStack);
		}
	}
	Criteria MakeCrt(DbMaprMgr mgr, DbMaprItm mapr) {
		Criteria rv = null, cur = null;
		List_adp list = GetIdxFlds(mgr, mapr);
		for (Object kvObj : list) {
			Keyval kv = (Keyval)kvObj;
			cur = Db_crt_.New_eq(kv.Key(), kv.Val());
			rv = (rv == null) ? cur : Criteria_.And(rv, cur);
		}
		return rv;
	}
	List_adp GetIdxFlds(DbMaprMgr mgr, DbMaprItm curMapr) {
		List_adp rv = List_adp_.New();
		int maprStackCount = mgr.MaprStack().Count() - 0; // -1 b/c current is added to stack
		for (int i = 0; i < maprStackCount; i ++) {
			DbMaprItm mapr = (DbMaprItm)mgr.MaprStack().Get_at(i);
			SrlObj gobj = (SrlObj)mgr.OwnerStack().Get_at(i);
			for (Object argObj : mapr.ContextFlds()) {
				DbMaprArg arg = (DbMaprArg)argObj;
				Object propVal = Gfo_invk_.Invk_by_key((Gfo_invk)gobj, arg.ObjProp());
				rv.Add(Keyval_.new_(arg.DbFld(), propVal));
			}					
		}
		for (Object argObj : curMapr.ConstantFlds()) {
			Keyval arg = (Keyval)argObj;
			rv.Add(arg);
		}
		return rv;
	}
	GfoNde GetTbl(DbMaprItm mapr, Criteria crit) {
		String key = mapr.TableName();			
		GfoNde tblByRootCrt = GfoNde_.as_(tables.Get_by(key));
		if (tblByRootCrt == null) {
			DataRdr dbRdr = null;
			try {
				dbRdr = conn.Exec_qry_as_old_rdr(Db_qry_.select_().From_(mapr.TableName()).Where_(rootCrt));
				tblByRootCrt = GfoNde_.rdr_(dbRdr);
			}
			finally {dbRdr.Rls();}
			tables.Add(key, tblByRootCrt);
		}
		GfoNde rv = GfoNde_.tbl_(mapr.TableName(), tblByRootCrt.Flds());
		for (int i = 0; i < tblByRootCrt.Subs().Count(); i++) {
			GfoNde row = tblByRootCrt.Subs().FetchAt_asGfoNde(i);
			if (crit.Matches(row)) rv.Subs().Add(row);
		}
		return rv;
	}
	void RowStack_add(GfoNde tbl, int i) {
		GfoNdeList ndeList = tbl.Subs(); if (i >= ndeList.Count()) throw Err_.new_missing_idx(i, ndeList.Count());
		rowStack.Add(tbl.Subs().FetchAt_asGfoNde(i));
	}
	@Override public Object Read(String key) {
		DbMaprItm mapr = (DbMaprItm)mgr.MaprStack().Get_at_last();
		GfoNde row = (GfoNde)rowStack.Get_at_last();
		DbMaprArg arg = mapr.Flds_get(key);
		Object dbVal = null; try {dbVal = row.Read(arg.DbFld());} catch (Exception e) {throw Err_.new_exc(e, "db", "failed to read dbVal from row", "key", key, "fld", arg.DbFld());}
		return dbVal;
	}
	@Override public DataRdr Subs_byName_moveFirst(String name) {throw Err_.new_unimplemented();}
	@Override public DataRdr Subs() {throw Err_.new_unimplemented();}
	@Override public int FieldCount() {throw Err_.new_unimplemented();}
	@Override public String KeyAt(int i) {throw Err_.new_unimplemented();}
	@Override public Object ReadAt(int i) {throw Err_.new_unimplemented();}
	@Override public Keyval KeyValAt(int i) {throw Err_.new_unimplemented();}
	@Override public SrlMgr SrlMgr_new(Object o) {return new DbMaprRdr();}
	Hash_adp tables = Hash_adp_.New();
	Db_conn conn; Criteria rootCrt;
	DbMaprMgr mgr; List_adp rowStack = List_adp_.New();
	public static DbMaprRdr new_(Db_conn_info dbInfo, Criteria rootCrt) {
		DbMaprRdr rv = new DbMaprRdr();
		rv.conn = Db_conn_pool.Instance.Get_or_new(dbInfo); rv.rootCrt = rootCrt;
		return rv;
	}	DbMaprRdr() {}
}