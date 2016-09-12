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
package gplx.xowa.xtns.wbases.claims.enums; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.claims.*;
public class Wbase_claim_type_ {
	public static final byte	// NOT_SERIALIZED
	  Tid__unknown								=  0
	, Tid__value								=  1
	, Tid__bad									=  1
	, Tid__string								=  2
	, Tid__quantity								=  3
	, Tid__time									=  4
	, Tid__globecoordinate						=  5
	, Tid__monolingualtext						=  6
	, Tid__entity								=  7
	;
	public static final    Wbase_enum_hash Reg = new Wbase_enum_hash("claim.data_type", 8);
	public static final    Wbase_enum_itm
	  Itm__unknown					= New(Tid__unknown			, "unknown")
	, Itm__bad						= New(Tid__bad				, "bad")				// NOTE: wikidata identifies several entries as "bad"; Q1615351|'s-Graveland, Q107538|Baco; DATE:2013-10-20
	, Itm__string					= New(Tid__string			, "string")			// EX:wd:Property:P1030
	, Itm__quantity					= New(Tid__quantity			, "quantity")
	, Itm__time						= New(Tid__time				, "time")
	, Itm__globecoordinate			= New(Tid__globecoordinate	, "globecoordinate"		, "globe-coordinate")
	, Itm__monolingualtext			= New(Tid__monolingualtext	, "monolingualtext")
	, Itm__entity					= New(Tid__entity			, "wikibase-entityid"	, "wikibase-item")
	;
	private static Wbase_enum_itm New(byte tid, String key)						{return New(tid, key, key);}
	private static Wbase_enum_itm New(byte tid, String key, String scrib)		{return Reg.Add(new Wbase_claim_type(tid, key, scrib));}
	public static String Get_scrib_or_unknown(byte tid)	{return ((Wbase_claim_type)Reg.Get_itm_or(tid, Itm__unknown)).Key_for_scrib();}
	public static byte Get_tid_or_unknown(String key)					{return Get_tid_or_unknown(Bry_.new_u8(key));}
	public static byte Get_tid_or_unknown(byte[] key)					{return Get_tid_or_unknown(key, 0, key.length);}
	public static byte Get_tid_or_unknown(byte[] key, int bgn, int end) {return Reg.Get_tid_or(key, bgn, end, Tid__unknown);}
}