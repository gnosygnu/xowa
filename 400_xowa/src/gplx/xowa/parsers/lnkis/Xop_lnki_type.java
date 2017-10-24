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
package gplx.xowa.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.bits.*;
public class Xop_lnki_type {
	public static final byte Id_null = 0, Id_none = 1, Id_frameless = 2, Id_frame = 4, Id_thumb = 8;
	public static final byte Tid_null = 0, Tid_none = 1, Tid_frameless = 2, Tid_frame = 3, Tid_thumb = 4, Tid_orig_known = 64;	// SERIALIZED
	public static byte To_tid(byte flag) {
		switch (flag) {
			case Xop_lnki_type.Id_null:				return Xop_lnki_type.Tid_null;
			case Xop_lnki_type.Id_none:				return Xop_lnki_type.Tid_none;
			case Xop_lnki_type.Id_frameless:		return Xop_lnki_type.Tid_frameless;
			case Xop_lnki_type.Id_frame:			return Xop_lnki_type.Tid_frame;
			case Xop_lnki_type.Id_thumb:			return Xop_lnki_type.Tid_thumb;
			default:								throw Err_.new_unhandled(flag);
		}
	}
	public static byte To_flag(byte tid) {
		switch (tid) {
			case Xop_lnki_type.Tid_null:			return Xop_lnki_type.Id_null;
			case Xop_lnki_type.Tid_none:			return Xop_lnki_type.Id_none;
			case Xop_lnki_type.Tid_frameless:		return Xop_lnki_type.Id_frameless;
			case Xop_lnki_type.Tid_frame:			return Xop_lnki_type.Id_frame;
			case Xop_lnki_type.Tid_thumb:			return Xop_lnki_type.Id_thumb;
			default:								throw Err_.new_unhandled(tid);
		}
	}
	public static boolean Id_is_thumbable(byte id) {
		return	(	Bitmask_.Has_int(id, Id_thumb)	// for purposes of displaying images on page, thumb and frame both create a thumb box
				||	Bitmask_.Has_int(id, Id_frame)
				);
	}
	public static boolean Id_defaults_to_thumb(byte id) {	// assuming original of 400,200
		if		(	Bitmask_.Has_int(id, Id_thumb)		// [[File:A.png|thumb]]     -> 220,-1
				||	Bitmask_.Has_int(id, Id_frameless)	// [[File:A.png|frameless]] -> 220,-1
				)
			return true;
		else if (	Bitmask_.Has_int(id, Id_frame)		// [[File:A.png|frame]]     -> 400,200 (frame is always default size)
				||	id == Id_null						// [[File:A.png]]           -> 400,200 (default to original size)
				||	Bitmask_.Has_int(id, Id_none)		// TODO_OLD: deprecate; NOTE: still used by one test; DATE:2015-08-03
				)
			return false;
		else											// should not happen
			throw Err_.new_unhandled(id);
	}
	public static boolean Id_limits_large_size(byte id) {	// Linker.php|makeThumbLink2|Do not present an image bigger than the source, for bitmap-style images; assuming original of 400,200
		if		(	Bitmask_.Has_int(id, Id_thumb)		// [[File:A.png|600px|thumb]]      -> 400,200
				||	Bitmask_.Has_int(id, Id_frameless)	// [[File:A.png|600px|frameless]]  -> 400,200
				||	Bitmask_.Has_int(id, Id_frame)		// [[File:A.png|600px|frame]]      -> 400,200 (frame is always default size)
				||	id == Tid_orig_known				// for hdump
				)
			return true;
		else if (	id == Id_null						// [[File:A.png|600px]]            -> 600,400; uses orig file of 400,200, but <img> tag src_width / src_height set to 600,400
				||	Bitmask_.Has_int(id, Id_none)		// TODO_OLD: deprecate; NOTE: leaving in b/c of above failed-deprecate; DATE:2015-08-03
				)
			return false;
		else											// should not happen;
			throw Err_.new_unhandled(id);
	}
	public static boolean Id_supports_upright(byte id) {	// REF:Linker.php|makeImageLink;if ( isset( $fp['thumbnail'] ) || isset( $fp['manualthumb'] ) || isset( $fp['framed'] ) || isset( $fp['frameless'] ) || !$hp['width'] )  DATE:2014-05-22
		if		(	Bitmask_.Has_int(id, Id_thumb)
				||	Bitmask_.Has_int(id, Id_frameless)
				||	Bitmask_.Has_int(id, Id_frame)
				)
			return true;
		else if (	id == Id_null
				||	Bitmask_.Has_int(id, Id_none)
				)
				return false;
		else											// should not happen;
			throw Err_.new_unhandled(id);
	}
}
