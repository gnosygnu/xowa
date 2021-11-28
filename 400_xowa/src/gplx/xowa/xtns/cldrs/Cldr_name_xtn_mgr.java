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
package gplx.xowa.xtns.cldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Cldr_name_xtn_mgr extends Xox_mgr_base {
//		private Cldr_name_loader loader;
	@Override public byte[] Xtn_key() {return XTN_KEY;} public static final byte[] XTN_KEY = Bry_.new_a7("CldrNames");
	@Override public Xox_mgr Xtn_clone_new() {return new Cldr_name_xtn_mgr();}
	@Override public void Xtn_init_by_wiki(Xowe_wiki wiki) {
//			loader = new Cldr_name_loader(wiki.App().Fsys_mgr().Bin_xtns_dir());
	}
}
