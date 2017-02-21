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
package gplx.xowa.files.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.xowa.apps.progs.*; import gplx.xowa.bldrs.wms.*; import gplx.xowa.files.cnvs.*;	
public class Xof_img_mgr {
	public Xof_img_wkr_resize_img				Wkr_resize_img() {return wkr_resize_img;} public void Wkr_resize_img_(Xof_img_wkr_resize_img v) {wkr_resize_img = v;} private Xof_img_wkr_resize_img wkr_resize_img;
	public Xof_img_wkr_query_img_size			Wkr_query_img_size() {return wkr_query_img_size;} public void Wkr_query_img_size_(Xof_img_wkr_query_img_size v) {wkr_query_img_size = v;} private Xof_img_wkr_query_img_size wkr_query_img_size;
	public Xof_img_wkr_convert_djvu_to_tiff		Wkr_convert_djvu_to_tiff() {return wkr_convert_djvu_to_tiff;} public Xof_img_mgr Wkr_convert_djvu_to_tiff_(Xof_img_wkr_convert_djvu_to_tiff v) {wkr_convert_djvu_to_tiff = v; return this;} private Xof_img_wkr_convert_djvu_to_tiff wkr_convert_djvu_to_tiff;
	public void Init_by_app(Xowmf_mgr wmf_mgr, Xoa_prog_mgr prog_mgr) {
		wkr_resize_img				= new Xof_img_wkr_resize_img_imageMagick(wmf_mgr, prog_mgr.App_resize_img(), prog_mgr.App_convert_svg_to_png());
		wkr_query_img_size			= new Xof_img_wkr_query_img_size_imageMagick(wmf_mgr, prog_mgr.App_query_img_size());
		wkr_convert_djvu_to_tiff	= new Xof_img_wkr_convert_djvu_to_tiff_app(prog_mgr.App_convert_djvu_to_tiff());
	}
}
