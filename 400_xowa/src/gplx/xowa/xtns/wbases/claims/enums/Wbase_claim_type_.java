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
package gplx.xowa.xtns.wbases.claims.enums; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.claims.*;
public class Wbase_claim_type_ {
	public static final byte	// SERIALIZED:wbase_prop|datatype; REF:https://www.wikidata.org/wiki/Help:Data_type
	  Tid__unknown								=  0
	, Tid__value								=  1
	, Tid__bad									=  1
	, Tid__string								=  2
	, Tid__quantity								=  3
	, Tid__time									=  4
	, Tid__globecoordinate						=  5
	, Tid__monolingualtext						=  6
	, Tid__entity								=  7
	, Tid__property								=  8
	, Tid__math									=  9
	, Tid__url									= 10
	, Tid__externalid							= 11
	, Tid__commonsmedia							= 12
	, Tid__geo_shape							= 13
	;
	public static final    Wbase_enum_hash Reg = new Wbase_enum_hash("claim.data_type", 14);
	public static final    Wbase_enum_itm
	  Itm__unknown					= New(Tid__unknown			, "unknown")
	, Itm__bad						= New(Tid__bad				, "bad")				// NOTE: wikidata identifies several entries as "bad"; Q1615351|'s-Graveland, Q107538|Baco; DATE:2013-10-20
	, Itm__string					= New(Tid__string			, "string")			// EX:wd:Property:P1030
	, Itm__quantity					= New(Tid__quantity			, "quantity")
	, Itm__time						= New(Tid__time				, "time")
	, Itm__globecoordinate			= New(Tid__globecoordinate	, "globecoordinate"		, "globe-coordinate")
	, Itm__monolingualtext			= New(Tid__monolingualtext	, "monolingualtext")
	, Itm__entity					= New(Tid__entity			, "wikibase-entityid"	, "wikibase-item")
	, Itm__property					= New(Tid__property			, "wikibase-property")	// EX:wd:Property:P1646
	, Itm__url						= New(Tid__url				, "url")				// EX:wd:Property:P1019
	, Itm__commonsmedia				= New(Tid__commonsmedia		, "commonsMedia")		// EX:wd:Property:P14
	, Itm__externalid				= New(Tid__externalid		, "external-id")		// EX:wd:Property:P1003
	, Itm__math						= New(Tid__math				, "math")				// EX:wd:Property:P2534
	, Itm__geo_shape				= New(Tid__geo_shape		, "geo-shape")			// EX:wd:Property:P3896
	;
	private static Wbase_enum_itm New(byte tid, String key)						{return New(tid, key, key);}
	private static Wbase_enum_itm New(byte tid, String key, String scrib)		{return Reg.Add(new Wbase_claim_type(tid, key, scrib));}
	public static String Get_scrib_or_unknown(byte tid)	{return ((Wbase_claim_type)Reg.Get_itm_or(tid, Itm__unknown)).Key_for_scrib();}
	public static byte Get_tid_or_unknown(String key)					{return Get_tid_or_unknown(Bry_.new_u8(key));}
	public static byte Get_tid_or_unknown(byte[] key)					{return Get_tid_or_unknown(key, 0, key.length);}
	public static byte Get_tid_or_unknown(byte[] key, int bgn, int end) {return Reg.Get_tid_or(key, bgn, end, Tid__unknown);}
}