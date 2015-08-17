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
class GfmlFld_mok {
	public String Name() {return name;} public GfmlFld_mok Name_(String v) {name = v; return this;} private String name;
	public String TypeKey() {return typeKey;} public GfmlFld_mok TypeKey_(String v) {typeKey = v; return this;} private String typeKey;
	public GfmlObj DefaultTkn() {return defaultTkn;}
	public GfmlFld_mok DefaultTkn_(GfmlObj v) {defaultTkn = v; return this;} GfmlObj defaultTkn = GfmlTkn_.Null;
	public GfmlFld_mok Default_(String v) {defaultTkn = GfmlTkn_.raw_(v); return this;}
	public String DefaultTknRaw() {return GfmlFld_mok.XtoRaw(defaultTkn);}
	public boolean KeyedSubObj() {return keyed;} private boolean keyed = false;
	public GfmlFld XtoGfmlFld() {
		GfmlFld rv = GfmlFld.new_(keyed, name, typeKey);
		rv.DefaultTkn_(defaultTkn);			
		return rv;
	}
	public GfmlFld_mok ini_atr_(String name) {this.name = name; this.keyed = true; return this;}
	public GfmlFld_mok ini_ndk_(String name, String typKey) {this.name = name; this.typeKey = typKey; this.keyed = true; return this;}
	public static GfmlFld_mok new_() {return new GfmlFld_mok();} GfmlFld_mok() {}
	public static String XtoRaw(GfmlObj gobj) {
		if (gobj == null) return String_.Null_mark;
		GfmlTkn tkn = GfmlTkn_.as_(gobj);
		if (tkn != null) return tkn.Raw();
		GfmlNde nde = GfmlNde.as_(gobj);
		return nde.To_str();
	}
}
class GfmlTyp_mok {
	public String Name() {return name;} public GfmlTyp_mok Name_(String v) {name = v; return this;} private String name;
	public String Key() {return key;} public GfmlTyp_mok Key_(String v) {key = v; return this;} private String key;
	public List_adp Subs() {return subFlds;} List_adp subFlds = List_adp_.new_();
	public GfmlTyp_mok Atrs_(String... ary) {
		for (String itm : ary)
			subFlds.Add(GfmlFld_mok.new_().ini_atr_(itm));
		return this;
	}
	public GfmlTyp_mok Subs_(GfmlFld_mok... ary) {
		for (GfmlFld_mok itm : ary)
			subFlds.Add(itm);
		return this;
	}
	public GfmlType XtoGfmlType() {
		GfmlType rv = GfmlType_.new_(key, name); // all types in tests are top-level
		for (int i = 0; i < subFlds.Count(); i++) {
			GfmlFld_mok fld = (GfmlFld_mok)subFlds.Get_at(i);
			rv.SubFlds().Add(fld.XtoGfmlFld());
		}
		return rv;
	}
        public static GfmlTyp_mok new_() {return new GfmlTyp_mok();} GfmlTyp_mok() {}
	public static GfmlTyp_mok simple_(String name, String... flds) {
		GfmlTyp_mok rv = new GfmlTyp_mok();
		rv.key = name; rv.name = name;
		for (String fld : flds)
			rv.subFlds.Add(GfmlFld_mok.new_().ini_atr_(fld));
		return rv;
	}
	public static GfmlTyp_mok type_(GfmlType typ) {
		GfmlTyp_mok rv = new GfmlTyp_mok();
		rv.key = typ.Key(); rv.name = typ.NdeName();
		for (int i = 0; i < typ.SubFlds().Count(); i++) {
			GfmlFld fld = (GfmlFld)typ.SubFlds().Get_at(i);
			GfmlFld_mok mkFld = GfmlFld_mok.new_().ini_ndk_(fld.Name(), fld.TypeKey()).DefaultTkn_(fld.DefaultTkn());
			rv.subFlds.Add(mkFld);
		}
		return rv;
	}
}
