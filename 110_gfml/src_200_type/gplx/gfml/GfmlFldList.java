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
public class GfmlFldList {
	public int Count() {return hash.Count();}
	public GfmlFld FetchAt(int index) {return (GfmlFld)hash.FetchAt(index);}
	public GfmlFld Fetch(String id) {return (GfmlFld)hash.Fetch(id);}
	public void Add(GfmlFld fld) {
		if (String_.Len_eq_0(fld.Name())) throw Err_.new_("fld name cannot be null");
		if (hash.Has(fld.Name())) throw Err_.new_("key already exists").Add("key", fld.Name()); // FIXME: commented out to allow multiple types with same name; need "_type:invk"
		hash.AddReplace(fld.Name(), fld);
	}
	public void Del(GfmlFld fld) {
		hash.Del(fld);
		hash.Del(fld.Name());
	}
	OrderedHash hash = OrderedHash_.new_();
	public static GfmlFldList new_() {return new GfmlFldList();} GfmlFldList() {}
}
