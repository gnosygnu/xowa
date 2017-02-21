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
package gplx.xowa.htmls.core.wkrs.imgs.atrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
import gplx.core.brys.*; import gplx.core.encoders.*;
import gplx.xowa.htmls.core.hzips.*;
import gplx.xowa.parsers.lnkis.*; import gplx.xowa.files.*;
public class Xoh_img_xoimg_hzip {
	public void Encode(Bry_bfr bfr, Xoh_stat_itm stat_itm, byte[] src, Xoh_img_xoimg_data arg) {
		boolean page_exists = arg.Lnki_page() != Xof_lnki_page.Null;
		boolean time_exists = arg.Lnki_time() != Xof_lnki_time.Null;
		boolean upright_exists = arg.Lnki_upright() != Xof_img_size.Upright_null;
		boolean height_exists = arg.Lnki_h() != Xof_img_size.Size__neg1;
		boolean width_exists = arg.Lnki_w() != Xof_img_size.Size__neg1;
		flag_bldr.Set(Flag__page_exists			, page_exists);
		flag_bldr.Set(Flag__time_exists			, time_exists);
		flag_bldr.Set(Flag__upright_exists		, upright_exists);
		flag_bldr.Set(Flag__height_exists		, height_exists);
		flag_bldr.Set(Flag__width_exists		, width_exists);
		flag_bldr.Set(Flag__lnki_type			, arg.Lnki_type());
		Gfo_hzip_int_.Encode(1, bfr, flag_bldr.Encode());
		if (width_exists)		Gfo_hzip_int_.Encode(2, bfr, arg.Lnki_w());
		if (height_exists)		Gfo_hzip_int_.Encode(2, bfr, arg.Lnki_h());
		if (upright_exists)		bfr.Add_double(arg.Lnki_upright()).Add_byte(Xoh_hzip_dict_.Escape);
		if (time_exists)		bfr.Add_double(arg.Lnki_time()).Add_byte(Xoh_hzip_dict_.Escape);
		if (page_exists)		Gfo_hzip_int_.Encode(2, bfr, arg.Lnki_page());
	}
	public void Decode(Bry_bfr bfr, Xoh_hdoc_ctx hctx, Xoh_page hpg, Bry_rdr rdr, byte[] src, Xoh_img_xoimg_data arg) {
		int flag = rdr.Read_hzip_int(1);
		flag_bldr.Decode(flag);
		boolean page_exists	= flag_bldr.Get_as_bool(Flag__page_exists);
		boolean time_exists	= flag_bldr.Get_as_bool(Flag__time_exists);
		boolean upright_exists	= flag_bldr.Get_as_bool(Flag__upright_exists);
		boolean height_exists	= flag_bldr.Get_as_bool(Flag__height_exists);
		boolean width_exists	= flag_bldr.Get_as_bool(Flag__width_exists);
		byte tid			= flag_bldr.Get_as_byte(Flag__lnki_type);
		int w				= width_exists		? rdr.Read_hzip_int(2)					: Xof_img_size.Size__neg1;
		int h				= height_exists		? rdr.Read_hzip_int(2)					: Xof_img_size.Size__neg1;
		double upright		= upright_exists	? rdr.Read_double_to(Xoh_hzip_dict_.Escape) : Xof_img_size.Upright_null;
		double time			= time_exists		? rdr.Read_double_to(Xoh_hzip_dict_.Escape) : Xof_lnki_time.Null;
		int page			= page_exists		? rdr.Read_hzip_int(2)					: Xof_lnki_page.Null;
		arg.Set(tid, w, h, upright, time, page);
	}
	private final    Int_flag_bldr flag_bldr = new Int_flag_bldr().Pow_ary_bld_( 1, 1	, 1, 1, 1, 3);	
	private static final int // SERIALIZED
	  Flag__page_exists						=  0
	, Flag__time_exists						=  1
	, Flag__upright_exists					=  2
	, Flag__height_exists					=  3
	, Flag__width_exists					=  4	// none, thumbimage, thumbborder
	, Flag__lnki_type						=  5	// null, none, frameless, frame, thumb; gplx.xowa.parsers.lnkis.Xop_lnki_type
	;
}
