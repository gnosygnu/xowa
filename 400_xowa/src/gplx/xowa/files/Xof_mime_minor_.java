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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*;
public class Xof_mime_minor_ {
	public static Xof_ext ext_(byte[] minor_mime) {
		Int_obj_val id_obj = (Int_obj_val)mime_hash.Get_by(minor_mime);
		int id = id_obj == null ? Xof_ext_.Id_unknown : id_obj.Val();
		return Xof_ext_.new_by_id_(id);
	}
	private static final    byte[] 
	  Mime_svg = Bry_.new_a7("svg+xml"), Mime_djvu = Bry_.new_a7("vnd.djvu"), Mime_midi = Bry_.new_a7("midi")
	, Mime_xcf = Bry_.new_a7("x-xcf"), Mime_flac = Bry_.new_a7("x-flac")
	, Mime_bmp = Bry_.new_a7("x-bmp"), Mime_bmp_2 = Bry_.new_a7("x-ms-bmp");
	private static final    Hash_adp mime_hash = mime_hash_();
	private static Hash_adp mime_hash_() {
		Hash_adp rv = Hash_adp_bry.cs();
		mime_hash_itm_(rv, Xof_ext_.Bry_png		, Xof_ext_.Id_png);
		mime_hash_itm_(rv, Xof_ext_.Bry_jpg		, Xof_ext_.Id_jpg);
		mime_hash_itm_(rv, Xof_ext_.Bry_jpeg	, Xof_ext_.Id_jpeg);
		mime_hash_itm_(rv, Xof_ext_.Bry_gif		, Xof_ext_.Id_gif);
		mime_hash_itm_(rv, Xof_ext_.Bry_tif		, Xof_ext_.Id_tif);
		mime_hash_itm_(rv, Xof_ext_.Bry_tiff	, Xof_ext_.Id_tiff);
		mime_hash_itm_(rv, Mime_svg				, Xof_ext_.Id_svg);
		mime_hash_itm_(rv, Mime_djvu			, Xof_ext_.Id_djvu);
		mime_hash_itm_(rv, Xof_ext_.Bry_pdf		, Xof_ext_.Id_pdf);
		mime_hash_itm_(rv, Mime_midi			, Xof_ext_.Id_mid);
		mime_hash_itm_(rv, Xof_ext_.Bry_ogg		, Xof_ext_.Id_ogg);
		mime_hash_itm_(rv, Xof_ext_.Bry_oga		, Xof_ext_.Id_oga);
		mime_hash_itm_(rv, Xof_ext_.Bry_ogv		, Xof_ext_.Id_ogv);
		mime_hash_itm_(rv, Xof_ext_.Bry_webm	, Xof_ext_.Id_webm);
		mime_hash_itm_(rv, Mime_flac			, Xof_ext_.Id_flac);
		mime_hash_itm_(rv, Mime_bmp				, Xof_ext_.Id_bmp);
		mime_hash_itm_(rv, Mime_bmp_2			, Xof_ext_.Id_bmp);
		mime_hash_itm_(rv, Mime_xcf				, Xof_ext_.Id_xcf);
		mime_hash_itm_(rv, Xof_ext_.Bry_wav		, Xof_ext_.Id_wav);
		return rv;
	}
	private static void mime_hash_itm_(Hash_adp hash, byte[] key, int val) {hash.Add(key, new Int_obj_val(val));}
}
