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
package gplx.core.gfo_ndes; import gplx.*; import gplx.core.*;
import gplx.core.strings.*; import gplx.core.type_xtns.*;
public class GfoFldList_ {
	public static final    GfoFldList Null = new GfoFldList_null();
	public static GfoFldList new_() {return new GfoFldList_base();}
	public static GfoFldList str_(String... names) {
		GfoFldList rv = new GfoFldList_base();
		for (String name : names)
			rv.Add(name, StringClassXtn.Instance);
		return rv;
	}
}
class GfoFldList_base implements GfoFldList {
	public int Count() {return hash.Count();}
	public boolean Has(String key) {return hash.Has(key);}
	public int Idx_of(String key) {			
		Object rv = idxs.Get_by(key);
		return rv == null ? List_adp_.Not_found : Int_.cast(rv);
	}
	public GfoFld Get_at(int i) {return (GfoFld)hash.Get_at(i);}
	public GfoFld FetchOrNull(String key) {return (GfoFld)hash.Get_by(key);}
	public GfoFldList Add(String key, ClassXtn c) {
		GfoFld fld = GfoFld.new_(key, c);
		hash.Add(key, fld);
		idxs.Add(key, idxs.Count());
		return this;
	}
	public String To_str() {
		String_bldr sb = String_bldr_.new_();
		for (int i = 0; i < hash.Count(); i++) {
			GfoFld fld = this.Get_at(i);
			sb.Add(fld.Key()).Add("|");
		}
		return sb.To_str();
	}
	Ordered_hash hash = Ordered_hash_.New(); Hash_adp idxs = Hash_adp_.New(); // PERF: idxs used for Idx_of; need to recalc if Del ever added 
}
class GfoFldList_null implements GfoFldList {
	public int Count() {return 0;}
	public boolean Has(String key) {return false;}
	public int Idx_of(String key) {return List_adp_.Not_found;}
	public GfoFld Get_at(int i) {return GfoFld.Null;}
	public GfoFld FetchOrNull(String key) {return null;}
	public GfoFldList Add(String key, ClassXtn typx) {return this;}
	public String To_str() {return "<<GfoFldList_.Null>>";}
}