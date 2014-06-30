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
package gplx.xowa; import gplx.*;
public class Xop_lnki_type {
	public static final byte Id_null = 0, Id_none = 1, Id_frameless = 2, Id_frame = 4, Id_thumb = 8;
	public static boolean Id_is_thumbable(byte id) {
		switch (id) {
			case Id_thumb: case Id_frame:	// for purposes of displaying images on page, thumb and frame both create a thumb box
				return true;
			default:
				return false;
		}
	}
	public static boolean Id_defaults_to_thumb(byte id) {
		switch (id) {			// assuming original of 400,200
			case Id_thumb:		// [[File:A.png|thumb]]     -> 220,-1
			case Id_frameless:	// [[File:A.png|frameless]] -> 220,-1
				return true;
			case Id_null:		// [[File:A.png]]           -> 400,200 (default to original size)
			case Id_frame:		// [[File:A.png|frame]]     -> 400,200 (frame is always default size)
			case Id_none:		// TODO: deprecate
				return false;
			default:			// TODO: deprecate
				return Enm_.HasInt(id, Xop_lnki_type.Id_thumb) || Enm_.HasInt(id, Xop_lnki_type.Id_frameless);
		}
	}
	public static boolean Id_limits_large_size(byte id) {// Linker.php|makeThumbLink2|Do not present an image bigger than the source, for bitmap-style images
		switch (id) {			// assuming original of 400,200
			case Id_thumb:		// [[File:A.png|600px|thumb]]      -> 400,200
			case Id_frameless:	// [[File:A.png|600px|frameless]]  -> 400,200
			case Id_frame:		// [[File:A.png|600px|frame]]      -> 400,200 (frame is always default size)
				return true;
			case Id_null:		// [[File:A.png|600px]]            -> 600,400; uses orig file of 400,200, but <img> tag src_width / src_height set to 600,400
			case Id_none:		// TODO: deprecate
				return false;
			default:			// TODO: deprecate
				return !Enm_.HasInt(id, Xop_lnki_type.Id_none);
		}
	}
	public static boolean Id_supports_upright(byte id) {// REF:Linker.php|makeImageLink;if ( isset( $fp['thumbnail'] ) || isset( $fp['manualthumb'] ) || isset( $fp['framed'] ) || isset( $fp['frameless'] ) || !$hp['width'] )  DATE:2014-05-22
		switch (id) {
			case Id_thumb:
			case Id_frame:
			case Id_frameless:
				return true;
			case Id_null:
			case Id_none:
				return false;
			default:			// TODO: deprecate
				return Enm_.HasInt(id, Xop_lnki_type.Id_thumb) || Enm_.HasInt(id, Xop_lnki_type.Id_frameless);
		}
	}
}
