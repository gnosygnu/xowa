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
import gplx.core.primitives.*;
public class Xof_mime_minor_ {
	public static Xof_ext ext_(byte[] minor_mime) {
		Int_obj_val id_obj = (Int_obj_val)mime_hash.Fetch(minor_mime);
		int id = id_obj == null ? Xof_ext_.Id_unknown : id_obj.Val();
		return Xof_ext_.new_by_id_(id);
	}
	private static final byte[] 
	  Mime_svg = Bry_.new_ascii_("svg+xml"), Mime_djvu = Bry_.new_ascii_("vnd.djvu"), Mime_midi = Bry_.new_ascii_("midi")
	, Mime_xcf = Bry_.new_ascii_("x-xcf"), Mime_flac = Bry_.new_ascii_("x-flac")
	, Mime_bmp = Bry_.new_ascii_("x-bmp"), Mime_bmp_2 = Bry_.new_ascii_("x-ms-bmp");
	private static final HashAdp mime_hash = mime_hash_();
	private static HashAdp mime_hash_() {
		HashAdp rv = HashAdp_.new_bry_();
		mime_hash_itm_(rv, Xof_ext_.Bry_png, Xof_ext_.Id_png);
		mime_hash_itm_(rv, Xof_ext_.Bry_jpg, Xof_ext_.Id_jpg);
		mime_hash_itm_(rv, Xof_ext_.Bry_jpeg, Xof_ext_.Id_jpeg);
		mime_hash_itm_(rv, Xof_ext_.Bry_gif, Xof_ext_.Id_gif);
		mime_hash_itm_(rv, Xof_ext_.Bry_tif, Xof_ext_.Id_tif);
		mime_hash_itm_(rv, Xof_ext_.Bry_tiff, Xof_ext_.Id_tiff);
		mime_hash_itm_(rv, Mime_svg, Xof_ext_.Id_svg);
		mime_hash_itm_(rv, Mime_djvu, Xof_ext_.Id_djvu);
		mime_hash_itm_(rv, Xof_ext_.Bry_pdf, Xof_ext_.Id_pdf);
		mime_hash_itm_(rv, Mime_midi, Xof_ext_.Id_mid);
		mime_hash_itm_(rv, Xof_ext_.Bry_ogg, Xof_ext_.Id_ogg);
		mime_hash_itm_(rv, Xof_ext_.Bry_oga, Xof_ext_.Id_oga);
		mime_hash_itm_(rv, Xof_ext_.Bry_ogv, Xof_ext_.Id_ogv);
		mime_hash_itm_(rv, Xof_ext_.Bry_webm, Xof_ext_.Id_webm);
		mime_hash_itm_(rv, Mime_flac, Xof_ext_.Id_flac);
		mime_hash_itm_(rv, Mime_bmp, Xof_ext_.Id_bmp);
		mime_hash_itm_(rv, Mime_bmp_2, Xof_ext_.Id_bmp);
		mime_hash_itm_(rv, Mime_xcf, Xof_ext_.Id_xcf);
		return rv;
	}
	private static void mime_hash_itm_(HashAdp hash, byte[] key, int val) {hash.Add(key, Int_obj_val.new_(val));}
}
