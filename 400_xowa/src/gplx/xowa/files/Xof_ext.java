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
	public boolean		Is_not_viewable(int exec_tid) {
		return	exec_tid != Xof_exec_tid.Tid_viewer_app		// only apply logic if !Tid_viewer_app; note that if Tid_viewer_app, then user clicked on file, so return true;
				&&	(	this.Id_is_audio()					// NOTE: was audio_strict, but v2 always redefines .ogg as .ogv; DATE:2014-02-02
					||	id == Xof_ext_.Id_unknown			// ignore unknown exts, else will download needlessly when viewing page; EX: .wav before .wav was registered; PAGE:pl.s:Spiaca_kr�lewna_(Oppman); DATE:2014-08-17
					);
	}
}
