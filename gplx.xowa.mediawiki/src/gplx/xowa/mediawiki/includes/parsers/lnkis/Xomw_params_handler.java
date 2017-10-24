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
package gplx.xowa.mediawiki.includes.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
public class Xomw_params_handler {
	public int width;
	public int height;
	public int page;
	public int physicalWidth;
	public int physicalHeight;
	public Xomw_params_handler Clear() {
		width = height = page
		= physicalWidth = physicalHeight = XophpUtility.NULL_INT;
		return this;
	}
	public void Copy_to(Xomw_params_handler src) {
		this.width = src.width;
		this.height = src.height;
		this.page = src.page;
		this.physicalWidth = src.physicalWidth;
		this.physicalHeight = src.physicalHeight;
	}
	public void Set(int uid, byte[] val_bry, int val_int) {
		switch (uid) {
			case Xomw_param_itm.Name__width: width = val_int; break;
			case Xomw_param_itm.Name__height: height = val_int; break;
			default: throw Err_.new_unhandled_default(uid);
		}
	}
}
