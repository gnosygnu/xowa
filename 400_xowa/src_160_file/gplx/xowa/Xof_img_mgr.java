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
import gplx.xowa.apps.fsys.*; import gplx.xowa.files.cnvs.*; import gplx.xowa.files.*;
public class Xof_img_mgr {
	public Xof_img_wkr_query_img_size			Wkr_query_img_size() {return wkr_query_img_size;} public Xof_img_mgr Wkr_query_img_size_(Xof_img_wkr_query_img_size v) {wkr_query_img_size = v; return this;} private Xof_img_wkr_query_img_size wkr_query_img_size;
	public Xof_img_wkr_resize_img				Wkr_resize_img() {return wkr_resize_img;} public Xof_img_mgr Wkr_resize_img_(Xof_img_wkr_resize_img v) {wkr_resize_img = v; return this;} private Xof_img_wkr_resize_img wkr_resize_img;
	public Xof_img_wkr_convert_djvu_to_tiff		Wkr_convert_djvu_to_tiff() {return wkr_convert_djvu_to_tiff;} public Xof_img_mgr Wkr_convert_djvu_to_tiff_(Xof_img_wkr_convert_djvu_to_tiff v) {wkr_convert_djvu_to_tiff = v; return this;} private Xof_img_wkr_convert_djvu_to_tiff wkr_convert_djvu_to_tiff;
	public int Thumb_w_img() {return thumb_w_img;} private int thumb_w_img = Xof_img_size.Thumb_width_img;
	public int Thumb_w_ogv() {return thumb_w_ogv;} private int thumb_w_ogv = Xof_img_size.Thumb_width_ogv;
	public void Init_app(Xoa_app app) {
		Launcher_app_mgr app_mgr = app.Fsys_mgr().App_mgr();
		wkr_query_img_size = new Xof_img_wkr_query_img_size_imageMagick(app, app_mgr.App_query_img_size());
		wkr_resize_img = new Xof_img_wkr_resize_img_imageMagick(app, app_mgr.App_resize_img(), app_mgr.App_convert_svg_to_png());
		wkr_convert_djvu_to_tiff = new Xof_img_wkr_convert_djvu_to_tiff_app(app_mgr.App_convert_djvu_to_tiff());
	}
}
