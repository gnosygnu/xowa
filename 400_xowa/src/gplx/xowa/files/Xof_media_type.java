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
public class Xof_media_type {
	public static final byte Tid_null = 0, Tid_audio = 1, Tid_bitmap = 2, Tid_drawing = 2, Tid_office = 3, Tid_video = 4;
	public static final String Name_null = "", Name_audio = "AUDIO", Name_bitmap = "BITMAP", Name_drawing = "DRAWING", Name_office = "OFFICE", Name_video = "VIDEO";
	public static byte Xto_byte(String v) {
		if		(String_.Eq(v, Name_audio))		return Tid_audio;
		else if	(String_.Eq(v, Name_bitmap))	return Tid_bitmap;
		else if	(String_.Eq(v, Name_drawing))	return Tid_drawing;
		else if	(String_.Eq(v, Name_office))	return Tid_office;
		else if	(String_.Eq(v, Name_video))		return Tid_video;
		else									return Tid_null;
	}
}
