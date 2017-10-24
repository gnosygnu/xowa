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
	public GfmlType[] Xto_bry() {
		GfmlType[] rv = (GfmlType[])list.To_ary(GfmlType.class);
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
	List_adp list = List_adp_.New();
	public static GfmlTypeMakr new_() {return new GfmlTypeMakr();}
}