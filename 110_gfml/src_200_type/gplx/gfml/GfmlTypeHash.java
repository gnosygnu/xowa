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
package gplx.gfml; import gplx.*;
class GfmlTypeHash {
	public GfmlType Get_by(String key) {return (GfmlType)hash.Get_by(key);}
	public void Add(GfmlType type) {
		if (type.IsTypeNull()) throw Err_.new_wo_type("cannot add null type to GfmlTypeHash");
		if (hash.Has(type.Key())) throw Err_.new_wo_type("type key already exists", "key", type.Key());
		hash.Add(type.Key(), type);
	}
	Hash_adp hash = Hash_adp_.new_();
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