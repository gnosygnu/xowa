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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.files.*;
import gplx.xowa.parsers.lnkis.*;
public class Gallery_mgr_packed_base__tst {
	private final    Gallery_mgr_packed_base__fxt fxt = new Gallery_mgr_packed_base__fxt();
	@Test public void Get_thumb_size_static() {
		fxt.Test__Get_thumb_size_static(Xof_ext_.Id_png, 300, 200, fxt.Make__lnki(4650, 450));
	}
	@Test public void Adjust_image_parameters() {// PURPOSE: use ceil; ISSUE#:640; DATE:2020-01-08
		fxt.Test__Adjust_image_parameters(fxt.Make__file(4, 4), 3, 3); // fails if 2, 2
	}
}
class Gallery_mgr_packed_base__fxt {
	public Xop_lnki_tkn Make__lnki(int w, int h) {
		return new Xop_lnki_tkn().W_(w).H_(h);
	}
	public Xof_file_itm Make__file(int w, int h) {
		Xof_fsdb_itm rv = new Xof_fsdb_itm();
		rv.Html_size_(w, h);
		return rv;
	}
	public void Test__Get_thumb_size_static(int ext_id, int itm_default_w, int itm_default_h, Xop_lnki_tkn expd) {
		Xof_ext ext = Xof_ext_.new_by_id_(ext_id);
		Xop_lnki_tkn actl = new Xop_lnki_tkn();
		Gallery_mgr_packed_base.Get_thumb_size_static(actl, ext, itm_default_w, itm_default_w);
		Gftest.Eq__int(expd.W(), actl.W());
		Gftest.Eq__int(expd.H(), actl.H());
	}
	public void Test__Adjust_image_parameters(Xof_file_itm xfer_itm, int expd_w, int expd_h) {
		Gallery_mgr_packed_base mgr = new Gallery_mgr_packed_base();
		mgr.Adjust_image_parameters(xfer_itm);
		Gftest.Eq__int(expd_w, xfer_itm.Html_w());
		Gftest.Eq__int(expd_h, xfer_itm.Html_h());
	}
}
