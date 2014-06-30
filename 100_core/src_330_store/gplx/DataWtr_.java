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
package gplx;
import gplx.stores.*; /*DsvDataWtr_*/
public class DataWtr_ {
	public static final DataWtr Null = new DataWtr_null();
}
class DataWtr_null implements DataWtr {
	public boolean Type_rdr() {return false;}
	public HashAdp EnvVars() {return envVars;} HashAdp envVars = HashAdp_.new_();
	public void InitWtr(String key, Object val) {}
	public void WriteTableBgn(String name, GfoFldList fields) {}
	public void WriteNodeBgn(String nodeName) {}
	public void WriteLeafBgn(String leafName) {}
	public void WriteData(String name, Object val) {}
	public void WriteNodeEnd() {}
	public void WriteLeafEnd() {}
	public void Clear() {}
	public String XtoStr() {return "";}
	public Object StoreRoot(SrlObj root, String key) {return null;}
	public boolean SrlBoolOr(String key, boolean v) {return v;}
	public byte SrlByteOr(String key, byte v) {return v;}
	public int SrlIntOr(String key, int or) {return or;}
	public long SrlLongOr(String key, long or) {return or;}
	public String SrlStrOr(String key, String or) {return or;}
	public DateAdp SrlDateOr(String key, DateAdp or) {return or;}
	public DecimalAdp SrlDecimalOr(String key, DecimalAdp or) {return or;}
	public double SrlDoubleOr(String key, double or) {return or;}
	public Object SrlObjOr(String key, Object or) {return or;}
	public void SrlList(String key, ListAdp list, SrlObj proto, String itmKey) {}
	public void TypeKey_(String v) {}
	public SrlMgr SrlMgr_new(Object o) {return this;}
}
