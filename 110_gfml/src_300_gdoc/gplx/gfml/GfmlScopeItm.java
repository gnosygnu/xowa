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
interface GfmlScopeItm {
	String Key();
	GfmlDocPos DocPos();
}
class GfmlScopeRegy {
	public boolean Has(String key) {
		GfmlScopeList list = (GfmlScopeList)hash.Fetch(key); if (list == null) return false;
		return list.Count() > 0;
	}
	public void Add(GfmlScopeItm itm) {
		GfmlScopeList list = ItmOrNew(itm.Key());
		list.Add(itm);
	}
	public void Del(GfmlScopeItm itm) {
		GfmlScopeList list = (GfmlScopeList)hash.Fetch(itm.Key()); if (list == null) return;
		list.Del(itm);
		if (list.Count() == 0) hash.Del(itm.Key());
	}
	public GfmlScopeItm Fetch(String key, GfmlDocPos pos) {
		GfmlScopeList list = (GfmlScopeList)hash.Fetch(key); if (list == null) return null;
		return list.Fetch(pos);
	}
	GfmlScopeList ItmOrNew(String key) {
		GfmlScopeList rv = (GfmlScopeList)hash.Fetch(key);
		if (rv == null) {
			rv = GfmlScopeList.new_(key);
			hash.Add(key, rv);
		}
		return rv;
	}
	HashAdp hash = HashAdp_.new_();
	public static GfmlScopeRegy new_() {return new GfmlScopeRegy();}
}
class GfmlScopeList {
	public String Key() {return key;} private String key;
	public int Count() {return list.Count();}
	public void Add(GfmlScopeItm itm) {list.Add(itm);}
	public void Del(GfmlScopeItm itm) {list.Del(itm);}
	public GfmlScopeItm Fetch(GfmlDocPos pos) {
		if (list.Count() == 0) return null;
		GfmlScopeItm rv = null;
		for (Object itemObj : list) {
			GfmlScopeItm itm = (GfmlScopeItm)itemObj;
			if (CompareAble_.Is_moreOrSame(pos, itm.DocPos()))
				rv = itm;
			else
				break;	// ASSUME: insertion is done in order; first lessThan means rest will also be lessThan
		}
		return rv;
	}
	ListAdp list = ListAdp_.new_();
	public static GfmlScopeList new_(String key) {
		GfmlScopeList rv = new GfmlScopeList(); rv.key = key; return rv;
	}	GfmlScopeList() {}
}
