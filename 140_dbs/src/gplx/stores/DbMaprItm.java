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
public class DbMaprItm {
	public String TableName() {return tableName;} public DbMaprItm TableName_(String val) {tableName = val; return this;} private String tableName;
	public OrderedHash Flds() {return flds;} OrderedHash flds = OrderedHash_.new_();
	public HashAdp ContextFlds() {return contextFlds;} HashAdp contextFlds = HashAdp_.new_();
	public HashAdp ConstantFlds() {return constantFlds;} HashAdp constantFlds = HashAdp_.new_();
	public ListAdp Subs() {return subs;}

	public DbMaprItm Flds_add(String objProp, String dbFld) {flds.Add(objProp, DbMaprArg.new_(objProp, dbFld)); return this;}
	public DbMaprItm ContextFlds_add(String s) {
		DbMaprArg arg = (DbMaprArg)flds.Fetch(s);
		contextFlds.Add(arg.ObjProp(), arg);
		return this;
	}
	public DbMaprItm ConstantFlds_add(String dbFld, Object dbVal) {constantFlds.Add(dbFld, KeyVal_.new_(dbFld, dbVal)); return this;}
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
		throw Err_arg.notFound_key_("find", find);
	}
	public DbMaprArg Flds_get(String key) {return (DbMaprArg)flds.Fetch(key);}
	SrlObj proto; String key; ListAdp subs = ListAdp_.new_();
	public static DbMaprItm proto_(SrlObj proto, String key, String tableName) {
		DbMaprItm rv = new DbMaprItm();
		rv.proto = proto; rv.key = key; rv.tableName = tableName;
		return rv;
	}	DbMaprItm() {}
}
