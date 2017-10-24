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
public class GfmlType implements GfmlScopeItm {
	public String Key() {return key;} private String key;				// key for typeRegy;		EX: itm/rect
	public String NdeName() {return ndeName;} private String ndeName;	// name for typeResolver;	EX: rect	
	public GfmlDocPos DocPos() {return docPos;} public GfmlType DocPos_(GfmlDocPos val) {docPos = val; return this;} GfmlDocPos docPos = GfmlDocPos_.Null;
	public GfmlFldList SubFlds() {return subFlds;} GfmlFldList subFlds = GfmlFldList.new_();
	public GfmlType Clone() {
		GfmlType rv = new GfmlType().ctor_GfmlType_(key, ndeName).DocPos_(docPos.NewClone());
		for (int i = 0; i < subFlds.Count(); i++) {
			GfmlFld subFld = (GfmlFld)subFlds.Get_at(i);
			rv.subFlds.Add(subFld.Clone());
		}
		return rv;
	}
	public boolean IsTypeAny() {return String_.Eq(key, GfmlType_.AnyKey);}
	public boolean IsTypeNull() {return String_.Eq(key, GfmlType_.NullKey);}
	@gplx.Internal protected GfmlType ctor_GfmlType_(String key, String ndeName) {
		this.key = key;
		this.ndeName = ndeName;
		return this;
	}
}
class GfmlType_ {
	public static final String 
		  AnyKey	= "gfml.any"
		, StringKey	= "gfml.String"
		, NullKey	= "gfml.null"
		;
	public static final GfmlType Root		= null;
	public static final GfmlType String		= new_(StringKey, StringKey);
	public static final GfmlType Null		= new_(NullKey, NullKey);

	@gplx.Internal protected static GfmlType new_(String key, String ndeName) {return new GfmlType().ctor_GfmlType_(key, ndeName);}
	@gplx.Internal protected static GfmlType new_any_() {return new_(AnyKey, AnyKey);}
	@gplx.Internal protected static String MakeKey(String ownerKey, String ndeName) {return String_.Concat(ownerKey, "/", ndeName);}
}
