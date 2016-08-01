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
public class Wbase_claim_rank_ {
	public static final byte	// SERIALIZED.MW
	  Tid__preferred							=  2
	, Tid__normal								=  1
	, Tid__deprecated							=  0
	, Tid__unknown								=  3
	;
	private static final int Ary__len = 4;
	private static final    Wbase_claim_enum[] Ary = new Wbase_claim_enum[Ary__len];
	private static final    Hash_adp_bry hash_by_bry = Hash_adp_bry.cs();
	public static final    Wbase_claim_enum
	  Itm__preferred				= New(Tid__preferred		, "preferred")
	, Itm__normal					= New(Tid__normal			, "normal")
	, Itm__deprecated				= New(Tid__deprecated		, "deprecated")
	, Itm__unknown					= New(Tid__unknown			, "unknown")
	;
	private static Wbase_claim_enum New(byte tid, String key) {
		Wbase_claim_enum rv = new Wbase_claim_enum(tid, key);
		hash_by_bry.Add(rv.Key_bry(), rv);
		Ary[tid] = rv;
		return rv;
	}
	public static byte To_tid_or_unknown(byte[] src) {
		Object obj = hash_by_bry.Get_by_bry(src);
		return obj == null ? Tid__unknown : ((Wbase_claim_enum)obj).Tid();
	}
	public static String To_str_or_fail(byte tid) {return Ary[tid].Key_str();}
}