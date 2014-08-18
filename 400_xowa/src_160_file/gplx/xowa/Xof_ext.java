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
public class Xof_ext {
	public Xof_ext(int id, byte[] ext) {this.id = id; this.ext = ext;}
	public int		Id()					{return id;} private int id;
	public byte[]	Ext()					{return ext;} private byte[] ext;
	public byte[]	Ext_view()				{return Xof_ext_.Bry__ary[Id_view()];}
	public byte[]	Mime_type()				{return Xof_ext_.Mime_type__ary[id];}
	public boolean		Id_is_unknown()			{return id == Xof_ext_.Id_unknown;}
	public boolean		Id_is_svg()				{return id == Xof_ext_.Id_svg;}
	public boolean		Id_is_ogg()				{return id == Xof_ext_.Id_ogg;}
	public boolean		Id_is_oga()				{return id == Xof_ext_.Id_oga;}
	public boolean		Id_is_ogv()				{return id == Xof_ext_.Id_ogv;}
	public boolean		Id_is_djvu()			{return id == Xof_ext_.Id_djvu;}
	public boolean		Id_is_pdf()				{return id == Xof_ext_.Id_pdf;}
	public boolean		Id_is_image()			{return Xof_ext_.Id_is_image(id);}
	public boolean		Id_is_media()			{return Xof_ext_.Id_is_media(id);}
	public boolean		Id_is_audio()			{return Xof_ext_.Id_is_audio(id);}
	public boolean		Id_is_audio_strict()	{return Xof_ext_.Id_is_audio_strict(id);}
	public boolean		Id_is_video()			{return Xof_ext_.Id_is_video(id);}
	public boolean		Id_is_thumbable_img()	{return Xof_ext_.Id_is_thumbable_img(id);}
	public boolean		Id_supports_page()		{return Xof_ext_.Id_supports_page(id);}
	public boolean		Id_needs_convert()		{return Xof_ext_.Id_needs_convert(id);}
	public int		Id_view()				{return Xof_ext_.Id_view(id);}
}
