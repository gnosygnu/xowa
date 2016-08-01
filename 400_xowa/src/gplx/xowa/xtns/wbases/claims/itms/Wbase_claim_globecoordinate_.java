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
package gplx.xowa.xtns.wbases.claims.itms; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.claims.*;
import gplx.xowa.xtns.wbases.claims.enums.*;
public class Wbase_claim_globecoordinate_ {
	public static final byte
	  Tid__latitude								= 0
	, Tid__longitude							= 1
	, Tid__altitude								= 2
	, Tid__precision							= 3
	, Tid__globe								= 4
	;
	private static final int Ary__len = 5;
	private static final    Wbase_claim_enum[] Ary = new Wbase_claim_enum[Ary__len];
	private static final    Hash_adp_bry hash_by_bry = Hash_adp_bry.cs();
	private static final    Hash_adp hash_by_str = Hash_adp_.New();
	public static final    Wbase_claim_enum
	  Itm__latitude					= New(Tid__latitude			, "latitude")
	, Itm__longitude				= New(Tid__longitude		, "longitude")
	, Itm__altitude					= New(Tid__altitude			, "altitude")
	, Itm__precision				= New(Tid__precision		, "precision")
	, Itm__globe					= New(Tid__globe			, "globe")
	;
	private static Wbase_claim_enum New(byte tid, String key) {
		Wbase_claim_enum rv = new Wbase_claim_enum(tid, key);
		hash_by_bry.Add(rv.Key_bry(), rv);
		hash_by_str.Add(rv.Key_str(), rv);
		Ary[tid] = rv;
		return rv;
	}

	public static String To_str_or_invalid(byte tid) {return Ary[tid].Key_str();}
	public static byte[] To_bry_or_fail(byte tid) {return Ary[tid].Key_bry();}
	public static byte To_tid_or_invalid(byte[] page_url, String key) {return Wbase_claim_enum_.To_tid_or_invalid(hash_by_str, page_url, key);}
	public static byte To_tid_or_invalid(byte[] page_url, byte[] key) {return Wbase_claim_enum_.To_tid_or_invalid(hash_by_bry, page_url, key);}

	public static String
	  Val_globe_dflt_str						= "http://www.wikidata.org/entity/Q2"
	;
	public static byte[]
	  Val_globe_dflt_bry						= Bry_.new_a7(Val_globe_dflt_str)
	;
}
