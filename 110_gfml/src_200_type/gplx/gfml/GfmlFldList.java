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
public class GfmlFldList {
	public int Count() {return hash.Count();}
	public GfmlFld Get_at(int index) {return (GfmlFld)hash.Get_at(index);}
	public GfmlFld Get_by(String id) {return (GfmlFld)hash.Get_by(id);}
	public void Add(GfmlFld fld) {
		if (String_.Len_eq_0(fld.Name())) throw Err_.new_wo_type("fld name cannot be null");
		if (hash.Has(fld.Name())) throw Err_.new_wo_type("key already exists", "key", fld.Name()); // FIXME: commented out to allow multiple types with same name; need "_type:invk"
		hash.Add_if_dupe_use_nth(fld.Name(), fld);
	}
	public void Del(GfmlFld fld) {
		hash.Del(fld);
		hash.Del(fld.Name());
	}
	Ordered_hash hash = Ordered_hash_.New();
	public static GfmlFldList new_() {return new GfmlFldList();} GfmlFldList() {}
}
