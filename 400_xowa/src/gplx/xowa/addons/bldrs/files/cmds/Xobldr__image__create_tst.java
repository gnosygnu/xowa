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
package gplx.xowa.addons.bldrs.files.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import org.junit.*; import gplx.core.ios.*; import gplx.xowa.files.*;
public class Xobldr__image__create_tst {
	private Xobldr__image__create_fxt fxt = new Xobldr__image__create_fxt();
	@Test  public void Basic()					{fxt.Init_("A.png"	, Xof_media_type.Name_bitmap	, Xof_ext_.Bry_png, 220, 110)		.Test(Xof_ext_.Id_png);}	// A.png -> png
	@Test  public void Ogg_VIDEO()				{fxt.Init_("A.ogg"	, Xof_media_type.Name_video		, Xof_ext_.Bry_ogg, 220, 110)		.Test(Xof_ext_.Id_ogv);}	// A.ogg and VIDEO -> ogv
	@Test  public void Ogg_VIDEO_null_size()	{fxt.Init_("A.ogg"	, Xof_media_type.Name_video		, Xof_ext_.Bry_ogg,   0,   0)		.Test(Xof_ext_.Id_ogg);}	// A.ogg but 0,0 -> ogg (not ogv)
	@Test  public void Png_is_jpg()				{fxt.Init_("A.png"	, Xof_media_type.Name_bitmap	, Xof_ext_.Bry_jpg, 220, 110)		.Test(Xof_ext_.Id_jpg);}	// A.png and jpg -> jpg
	@Test  public void Jpeg_is_jpeg()			{fxt.Init_("A.jpeg"	, Xof_media_type.Name_bitmap	, Xof_ext_.Bry_jpg, 220, 110)		.Test(Xof_ext_.Id_jpeg);}	// A.jpeg and jpg -> jpeg (unchanged)
}
class Xobldr__image__create_fxt {
	private byte[] name, media_type, minor_mime; int w, h;
	public Xobldr__image__create_fxt Init_png() {Name_("A.png").Media_type_(Xof_media_type.Name_bitmap).Minor_mime_(Xof_ext_.Bry_png).W_(220).H_(110);
		return this;
	}
	public Xobldr__image__create_fxt Init_(String name, String media_type, byte[] minor_mime, int w, int h) {
		Name_(name);
		Media_type_(media_type); 
		Minor_mime_(minor_mime);
		W_(w);
		H_(h);
		return this;
	}
	public Xobldr__image__create_fxt Name_(String v) {name = Bry_.new_a7(v); return this;}
	public Xobldr__image__create_fxt Media_type_(String v) {media_type = Bry_.new_a7(v); return this;}
	public Xobldr__image__create_fxt Minor_mime_(byte[] v) {minor_mime = v; return this;}
	public Xobldr__image__create_fxt Minor_mime_(String v) {return Minor_mime_(Bry_.new_a7(v));}
	public Xobldr__image__create_fxt W_(int v) {w = v; return this;}
	public Xobldr__image__create_fxt H_(int v) {h = v; return this;}
	public Xobldr__image__create_fxt Test(int expd) {
		Tfds.Eq(expd, Xobldr__image__create.Calc_ext_id(Gfo_usr_dlg_.Noop, name, media_type, minor_mime, w, h));
		return this;
	}
}
