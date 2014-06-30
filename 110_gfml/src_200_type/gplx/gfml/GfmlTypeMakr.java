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
class GfmlTypeMakr {
	public GfmlType Owner() {return owner;}
	public GfmlType MakeRootType(String key, String name, String... flds) {
		GfmlType rv = MakeType(key, name);
		for (String fld : flds)
			AddSubFld_imp(rv, GfmlFld.new_(true, fld, GfmlType_.String.Key()));
		owner = rv;
		return rv;
	}
	public GfmlType MakeSubType(String name, String... flds) {
		String key = Key_make(name);			
		GfmlType rv = MakeType(key, name);
		for (String fld : flds)
			AddSubFld_imp(rv, GfmlFld.new_(true, fld, GfmlType_.StringKey));
		return rv;
	}
	public GfmlType MakeSubTypeAsOwner(String name, String... flds) {
		GfmlType rv = MakeSubType(name, flds);
		owner = rv;
		return rv;
	}
	public void AddSubFld(GfmlFld subFld) {AddSubFld_imp(owner, subFld);}
	public GfmlType[] XtoAry() {
		GfmlType[] rv = (GfmlType[])list.XtoAry(GfmlType.class);
		list.Clear();
		owner = null;
		return rv;
	}

	String Key_make(String name) {
		return (owner == null)
			? name
			: GfmlType_.MakeKey(owner.Key(), name);
	}
	GfmlType MakeType(String key, String name) {
		GfmlType rv = GfmlType_.new_(key, name);
		if (owner != null) {
			GfmlFld fld = GfmlFld.new_(false, name, key);
			AddSubFld_imp(owner, fld);
		}
		list.Add(rv);
		return rv;
	}
	void AddSubFld_imp(GfmlType ownerType, GfmlFld subFld) {ownerType.SubFlds().Add(subFld);}

	GfmlType owner;
	ListAdp list = ListAdp_.new_();
	public static GfmlTypeMakr new_() {return new GfmlTypeMakr();}
}