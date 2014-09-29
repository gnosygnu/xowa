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
package gplx.xowa.xtns.wdatas.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
public class Wdata_dict_val_tid {
	public static final byte 
	  Tid_unknown								= 0
	, Tid_value									= 1
	, Tid_bad									= 1
	, Tid_string								= 2
	, Tid_entity								= 3
	, Tid_time									= 4
	, Tid_globecoordinate						= 5
	, Tid_quantity								= 6
	, Tid_monolingualtext						= 7
	;
	public static final String 
	  Str_bad									= "bad"
	, Str_string								= "string"
	, Str_entity								= "wikibase-entityid"
	, Str_time									= "time"
	, Str_globecoordinate						= "globecoordinate"
	, Str_quantity								= "quantity"
	, Str_monolingualtext						= "monolingualtext"
	, Str_unknown								= "unknown"
	;
	public static final byte[]
	  Bry_bad									= Bry_.new_ascii_(Str_bad)
	, Bry_string								= Bry_.new_ascii_(Str_string)
	, Bry_entity								= Bry_.new_ascii_(Str_entity)
	, Bry_time									= Bry_.new_ascii_(Str_time)
	, Bry_globecoordinate						= Bry_.new_ascii_(Str_globecoordinate)
	, Bry_quantity								= Bry_.new_ascii_(Str_quantity)
	, Bry_monolingualtext						= Bry_.new_ascii_(Str_monolingualtext)
	, Bry_unknown								= Bry_.new_ascii_(Str_unknown)
	;
	private static final Hash_adp_bry Dict = Hash_adp_bry.cs_()
	.Add_bry_byte(Bry_string					, Tid_string)
	.Add_bry_byte(Bry_entity					, Tid_entity)
	.Add_bry_byte(Bry_time						, Tid_time)
	.Add_bry_byte(Bry_globecoordinate			, Tid_globecoordinate)
	.Add_bry_byte(Bry_quantity					, Tid_quantity)
	.Add_bry_byte(Bry_monolingualtext			, Tid_monolingualtext)
	.Add_bry_byte(Bry_bad						, Tid_bad)
	;
	public static String Xto_str(byte tid) {
		switch (tid) {
			case Tid_string						: return Str_string;
			case Tid_entity						: return Str_entity;
			case Tid_time						: return Str_time;
			case Tid_globecoordinate			: return Str_globecoordinate;
			case Tid_quantity					: return Str_quantity;
			case Tid_monolingualtext			: return Str_monolingualtext;
			case Tid_bad						: return Str_bad;	// NOTE: wikidata identifies several entries as "bad"; Q1615351|'s-Graveland, Q107538|Baco; DATE:2013-10-20
			default								: return Str_unknown;
		} 
	}
	public static byte Xto_tid(byte[] src) {return Xto_tid(src, 0, src.length);}
	public static byte Xto_tid(byte[] src, int bgn, int end) {
		Object bval_obj = Dict.Get_by_mid(src, bgn, end);
		if	(bval_obj == null) return Tid_unknown;		
		return ((Byte_obj_val)bval_obj).Val();
	}
}
