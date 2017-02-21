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
package gplx.gfml; import gplx.*;
class GfmlTypeHash {
	public GfmlType Get_by(String key) {return (GfmlType)hash.Get_by(key);}
	public void Add(GfmlType type) {
		if (type.IsTypeNull()) throw Err_.new_wo_type("cannot add null type to GfmlTypeHash");
		if (hash.Has(type.Key())) throw Err_.new_wo_type("type key already exists", "key", type.Key());
		hash.Add(type.Key(), type);
	}
	Hash_adp hash = Hash_adp_.New();
	public static GfmlTypeHash new_() {return new GfmlTypeHash();} GfmlTypeHash() {}
}
class GfmlTypRegy {
	public boolean Has(String typeKey) {return hash.Has(typeKey);}
	public GfmlType FetchOrNull(String typeKey) {return FetchOrNull(typeKey, GfmlDocPos_.Root);}
	public GfmlType FetchOrNull(String typeKey, GfmlDocPos pos) {
		if (typeKey == null) throw Err_.new_wo_type("typeKey cannot be null when added to typRegy");
		GfmlType rv = (GfmlType)hash.Get_by(typeKey, pos);
		return rv == null ? GfmlType_.Null : rv;
	}
	public GfmlTypRegy Add(GfmlType type) {
		hash.Del(type);	// always replace existing with most recent			
		hash.Add(type);
		return this;
	}
	public void Add_ary(GfmlType... ary) {
		for (GfmlType type : ary)
			this.Add(type);
	}
	public void Del(GfmlType type) {hash.Del(type);}
	GfmlScopeRegy hash = GfmlScopeRegy.new_();
	public static GfmlTypRegy new_() {return new GfmlTypRegy();}
}