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
