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
public class DbMaprItm {
	public String TableName() {return tableName;} public DbMaprItm TableName_(String val) {tableName = val; return this;} private String tableName;
	public Ordered_hash Flds() {return flds;} Ordered_hash flds = Ordered_hash_.New();
	public Hash_adp ContextFlds() {return contextFlds;} Hash_adp contextFlds = Hash_adp_.New();
	public Hash_adp ConstantFlds() {return constantFlds;} Hash_adp constantFlds = Hash_adp_.New();
	public List_adp Subs() {return subs;}

	public DbMaprItm Flds_add(String objProp, String dbFld) {flds.Add(objProp, DbMaprArg.new_(objProp, dbFld)); return this;}
	public DbMaprItm ContextFlds_add(String s) {
		DbMaprArg arg = (DbMaprArg)flds.Get_by(s);
		contextFlds.Add(arg.ObjProp(), arg);
		return this;
	}
	public DbMaprItm ConstantFlds_add(String dbFld, Object dbVal) {constantFlds.Add(dbFld, Keyval_.new_(dbFld, dbVal)); return this;}
	public DbMaprItm Subs_add(DbMaprItm... ary) {
		for (DbMaprItm itm : ary)
			subs.Add(itm);
		return this;
	}
	public DbMaprItm Subs_get(String find) {
		for (Object itmObj : subs) {
			DbMaprItm itm = (DbMaprItm)itmObj;
			if (String_.Eq(find, itm.key)) return itm;
		}
		throw Err_.new_missing_key(find);
	}
	public DbMaprArg Flds_get(String key) {return (DbMaprArg)flds.Get_by(key);}
	SrlObj proto; String key; List_adp subs = List_adp_.New();
	public static DbMaprItm proto_(SrlObj proto, String key, String tableName) {
		DbMaprItm rv = new DbMaprItm();
		rv.proto = proto; rv.key = key; rv.tableName = tableName;
		return rv;
	}	DbMaprItm() {}
}
