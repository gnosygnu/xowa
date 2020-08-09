/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.xtns.wbases.claims.enums;

import gplx.Bry_;
import gplx.Byte_ascii;
import gplx.Err_;

// NOTE: could not find definitive list, so using these links for now
// REF.MW:https://github.com/Wikidata/Wikidata-Toolkit/blob/master/wdtk-datamodel/src/main/java/org/wikidata/wdtk/datamodel/implementation/EntityIdValueImpl.java
// REF.MW:https://github.com/wikimedia/wikibase-property-suggester-scripts/blob/1d25e76f894796bfd57dd107102cf39088885138/propertysuggester/parser/JsonReader.py
public class Wbase_claim_entity_type_ {
	public static final byte
	  Tid__item         =  0
	, Tid__property     =  1
	, Tid__lexeme       =  2
	, Tid__sense        =  3
	, Tid__form         =  4
	;
	public static final Wbase_enum_hash Reg = new Wbase_enum_hash("claim.entity_type", 5);
	public static final Wbase_enum_itm
	  Itm__item        = Reg.Add(Tid__item        , "item")
	, Itm__property    = Reg.Add(Tid__property	  , "property")
	, Itm__lexeme      = Reg.Add(Tid__lexeme      , "lexeme")             // EX:wd:Lexeme:L2
	, Itm__sense       = Reg.Add(Tid__sense       , "sense")              // EX:wd:Lexeme:L2 P5830
	, Itm__form        = Reg.Add(Tid__form        , "form")               // EX:wd:Lexeme:L2 P6072
	;

	public static Wbase_enum_itm ToTid(byte[] id) {
		// fail if null or 0-length
		if (Bry_.Len_eq_0(id)) {
			throw Err_.new_unhandled_default(id);
		}

		// get 1st byte and uppercase it
		byte b0 = id[0];
		if (b0 > Byte_ascii.Ltr_Z) {
			b0 -= 32; // uppercases
		}

		// return item; NOTE: only doing types which have namespaces (i.e.: there is no Sense:S1 or Form:F1)
		switch (b0) {
			case Byte_ascii.Ltr_Q:
				return Wbase_claim_entity_type_.Itm__item;
			case Byte_ascii.Ltr_P:
				return Wbase_claim_entity_type_.Itm__property;
			case Byte_ascii.Ltr_L:
				return Wbase_claim_entity_type_.Itm__lexeme;
			default:
				throw Err_.new_unhandled_default(id);
		}
	}
}