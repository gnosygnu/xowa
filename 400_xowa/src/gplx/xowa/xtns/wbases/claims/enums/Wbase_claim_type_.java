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
	private static final int Ary__len = 13;
	private static final    Wbase_claim_type[] Ary = new Wbase_claim_type[Ary__len];
	private static final    Hash_adp_bry hash_by_bry = Hash_adp_bry.cs();
	private static final    Hash_adp hash_by_str = Hash_adp_.New();
	public static final    Wbase_claim_type 
	  Itm__unknown					= New(Tid__unknown			, "unknown")
	, Itm__bad						= New(Tid__bad				, "bad")				// NOTE: wikidata identifies several entries as "bad"; Q1615351|'s-Graveland, Q107538|Baco; DATE:2013-10-20
	, Itm__string					= New(Tid__string			, "string")			// EX:wd:Property:P1030
	, Itm__quantity					= New(Tid__quantity			, "quantity")
	, Itm__time						= New(Tid__time				, "time")
	, Itm__globecoordinate			= New(Tid__globecoordinate	, "globecoordinate"		, "globe-coordinate")
	, Itm__monolingualtext			= New(Tid__monolingualtext	, "monolingualtext")
	, Itm__entity					= New(Tid__entity			, "wikibase-entityid"	, "wikibase-item")
	;
	private static Wbase_claim_type New(byte tid, String key)					{return New(tid, key, key);}
	private static Wbase_claim_type New(byte tid, String key, String scrib)	{
		Wbase_claim_type rv = new Wbase_claim_type(tid, key, scrib);
		hash_by_bry.Add(rv.Key_bry(), rv);
		hash_by_str.Add(rv.Key_str(), rv);
		Ary[tid] = rv;
		return rv;
	}
	public static String To_key_or_unknown(byte tid)	{return tid < Ary__len ? Ary[tid].Key_str()			: Itm__unknown.Key_str();}
	public static String To_scrib_or_unknown(byte tid)	{return tid < Ary__len ? Ary[tid].Key_for_scrib()	: Itm__unknown.Key_str();}
	public static byte To_tid_or_unknown(byte[] src) {return To_tid_or_unknown(src, 0, src.length);}
	public static byte To_tid_or_unknown(byte[] src, int bgn, int end) {
		Object obj = hash_by_bry.Get_by_mid(src, bgn, end);
		return obj == null ? Tid__unknown : ((Wbase_claim_type)obj).Tid();
	}
	public static byte To_tid_or_unknown(String src) {
		Object obj = hash_by_str.Get_by(src);
		return obj == null ? Tid__unknown : ((Wbase_claim_type)obj).Tid();
	}
}