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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public class IoItm_fxt {
	public IoItmFil fil_wnt_(String s) {return fil_(Io_url_.wnt_fil_(s));}
	public IoItmFil fil_(Io_url url) {return IoItmFil_.new_(url, 1, DateAdp_.parse_gplx("2001-01-01"), DateAdp_.parse_gplx("2001-01-01"));}
	public IoItmDir dir_wnt_(String s) {return dir_(Io_url_.wnt_dir_(s));}
	public IoItmDir dir_(Io_url url, IoItm_base... ary) {
		IoItmDir rv = IoItmDir_.top_(url);
		for (IoItm_base itm : ary) {
			if (itm.Type_dir())
				rv.SubDirs().Add(itm);
			else
				rv.SubFils().Add(itm);
		}
		return rv;
	}
	public static IoItm_fxt new_() {return new IoItm_fxt();} IoItm_fxt() {}
}
