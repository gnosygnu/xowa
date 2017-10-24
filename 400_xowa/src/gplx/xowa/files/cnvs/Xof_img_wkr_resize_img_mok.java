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
package gplx.xowa.files.cnvs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.primitives.*;
import gplx.gfui.*; import gplx.gfui.imgs.*;
import gplx.xowa.files.cnvs.*;
public class Xof_img_wkr_resize_img_mok implements Xof_img_wkr_resize_img {
	public boolean Resize_exec(Io_url src, Io_url trg, int trg_w, int trg_h, int ext_id, String_obj_ref rslt_val) {
		SizeAdp src_size = ImageAdp_.txt_fil_(src).Size();
		int src_w = src_size.Width(), src_h = src_size.Height();
		if (trg_w < 1) throw Err_.new_wo_type("trg_w must be > 0", "trg_w", trg_w);
		if (trg_h < 1) trg_h = Xof_xfer_itm_.Scale_h(src_w, src_h, trg_w);
		Io_mgr.Instance.SaveFilStr(trg, SizeAdp_.new_(trg_w, trg_h).To_str());
		return true;
	}
	public static final    Xof_img_wkr_resize_img_mok Instance = new Xof_img_wkr_resize_img_mok(); Xof_img_wkr_resize_img_mok() {}
}
