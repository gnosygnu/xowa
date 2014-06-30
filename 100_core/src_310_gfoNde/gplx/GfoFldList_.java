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
public class GfoFldList_ {
	public static final GfoFldList Null = new GfoFldList_null();
	public static GfoFldList new_() {return new GfoFldList_base();}
	public static GfoFldList str_(String... names) {
		GfoFldList rv = new GfoFldList_base();
		for (String name : names)
			rv.Add(name, StringClassXtn._);
		return rv;
	}
}
class GfoFldList_base implements GfoFldList {
	public int Count() {return hash.Count();}
	public boolean Has(String key) {return hash.Has(key);}
	public int IndexOf(String key) {			
		Object rv = idxs.Fetch(key);
		return rv == null ? ListAdp_.NotFound : Int_.cast_(rv);
	}
	public GfoFld FetchAt(int i) {return (GfoFld)hash.FetchAt(i);}
	public GfoFld FetchOrNull(String key) {return (GfoFld)hash.Fetch(key);}
	public GfoFldList Add(String key, ClassXtn c) {
		GfoFld fld = GfoFld.new_(key, c);
		hash.Add(key, fld);
		idxs.Add(key, idxs.Count());
		return this;
	}
	public String XtoStr() {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < hash.Count(); i++) {
			GfoFld fld = this.FetchAt(i);
			sb.Add(fld.Key()).Add("|");
		}
		return sb.XtoStr();
	}
	OrderedHash hash = OrderedHash_.new_(); HashAdp idxs = HashAdp_.new_(); // PERF: idxs used for IndexOf; need to recalc if Del ever added 
}
class GfoFldList_null implements GfoFldList {
	public int Count() {return 0;}
	public boolean Has(String key) {return false;}
	public int IndexOf(String key) {return ListAdp_.NotFound;}
	public GfoFld FetchAt(int i) {return GfoFld.Null;}
	public GfoFld FetchOrNull(String key) {return null;}
	public GfoFldList Add(String key, ClassXtn typx) {return this;}
	public String XtoStr() {return "<<GfoFldList_.Null>>";}
}