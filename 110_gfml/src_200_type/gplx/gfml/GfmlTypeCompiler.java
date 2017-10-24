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
class GfmlTypeCompiler {
	@gplx.Internal protected static GfmlType Compile(GfmlNde nde, GfmlType owner, GfmlTypRegy typeRegy, Ordered_hash results) {return Compile(nde, owner, true, typeRegy, results);}
	static GfmlType Compile(GfmlNde nde, GfmlType owner, boolean isTopLevel, GfmlTypRegy typeRegy, Ordered_hash results) {
		String name = nde.SubKeys().FetchDataOrFail("name");
		String typeKey = FetchTypeKey(nde, owner, isTopLevel, name);
		GfmlType rv = FetchTypeOrNew(name, typeKey, typeRegy, results);
		for (int i = 0; i < nde.SubHnds().Count(); i++) {
			GfmlNde subNde = (GfmlNde)nde.SubHnds().Get_at(i);
			GfmlFld fld = CompileFld(subNde, rv, typeRegy, results);
			rv.SubFlds().Add(fld);
		}
		return rv;
	}
	static GfmlFld CompileFld(GfmlNde nde, GfmlType ownerType, GfmlTypRegy typeRegy, Ordered_hash results) {
		String name = nde.SubKeys().FetchDataOrFail("name");
		String typeKey = nde.SubKeys().FetchDataOrNull("type");
		GfmlObj defaultTkn = FetchDefaultTkn(nde, name); boolean isDefaultTknNde = defaultTkn.ObjType() == GfmlObj_.Type_nde;
		boolean isKeyed = false;

		if (nde.SubHnds().Count() == 0) {									// is either (a) simple (ex: String) or (b) reference (ex: gfml.point); in either case, TypeFld is same
			if (isDefaultTknNde) {
				typeKey = GfmlType_.AnyKey;
				isKeyed = true;											// implicit: default to isKeyed if is nde token
			}
			if (typeKey == null) {										// implicit: no typeKey defined; assume String
				typeKey = GfmlType_.String.Key();
				isKeyed = true;
			}
		}
		else {															// is inlineType
			GfmlType type = Compile(nde, ownerType, false, typeRegy, results);
			name = type.NdeName();
			typeKey = type.Key();
		}
		return GfmlFld.new_(isKeyed, name, typeKey).DefaultTkn_(defaultTkn);
	}
	static String FetchTypeKey(GfmlNde nde, GfmlType owner, boolean isTopLevel, String name) {
		String atrKey = isTopLevel ? "key" : "type";
		String typeKey = nde.SubKeys().FetchDataOrNull(atrKey);
		if (typeKey == null) {											// implicit
			typeKey = (owner == GfmlType_.Root)
				? name													// root type; assume typeKey is same as name; ex: point {x; y;} -> point is name; use as typeKey
				: GfmlType_.MakeKey(owner.Key(), name);					// nested type; build typeKey based on owner; ex: rect {pos {x; y;}} -> rect.pos is typeKey
		}
		return typeKey;
	}
	static GfmlType FetchTypeOrNew(String name, String typeKey, GfmlTypRegy typeRegy, Ordered_hash results) {
		GfmlType rv = typeRegy.FetchOrNull(typeKey);					// look for type in regy to see if it was declared earlier
		if (rv == GfmlType_.Null) {
			rv = (GfmlType)results.Get_by(rv.Key());						// look for type in current pragma's results
			if (rv == null) {											// nothing found; create and add
				rv = GfmlType_.new_(typeKey, name);
				results.Add(typeKey, rv);
			}
		}
		return rv;
	}
	static GfmlObj FetchDefaultTkn(GfmlNde nde, String name) {
		GfmlItm defaultTkn = nde.SubKeys().Get_by("default"); if (defaultTkn == null) return GfmlTkn_.Null;
		GfmlItm itm = GfmlItm_.as_(defaultTkn); if (itm.ObjType() == GfmlObj_.Type_atr) return GfmlAtr.as_(itm).DatTkn();
		GfmlNde rv = GfmlNde.new_(GfmlTkn_.val_(name), GfmlType_.new_any_(), true);
		for (int i = 0; i < itm.SubObjs_Count(); i++) {
			GfmlObj sub = (GfmlObj)itm.SubObjs_GetAt(i);
			rv.SubObjs_Add(sub);
		}
		return rv;
	}
	@gplx.Internal protected static void AddDefaultAtrs(GfmlNde nde, GfmlType type, GfmlTypRegy regy) { 
		if (type.IsTypeAny()) return;
		for (int i = 0; i < type.SubFlds().Count(); i++) {
			GfmlFld subFld = (GfmlFld)type.SubFlds().Get_at(i);
			if (subFld.DefaultTkn() == GfmlTkn_.Null) continue;
			if (nde.SubKeys().Has(subFld.Name())) continue;
			GfmlNde defaultNde = GfmlNde.as_(subFld.DefaultTkn());
			if (defaultNde != null) {
				nde.SubObjs_Add(defaultNde);
				continue;
			}
			GfmlType atrType = regy.FetchOrNull(subFld.TypeKey());
			GfmlTkn nameTkn = GfmlTkn_.new_(GfmlTkn_.NullRaw, subFld.Name());
			GfmlTkn subFldDefault = (GfmlTkn)subFld.DefaultTkn();
			GfmlTkn valTkn = GfmlTkn_.new_(GfmlTkn_.NullRaw, subFldDefault.Val());
			GfmlAtr atr = GfmlAtr.new_(nameTkn, valTkn, atrType);
			nde.SubObjs_Add(atr);
		}
	}
}
