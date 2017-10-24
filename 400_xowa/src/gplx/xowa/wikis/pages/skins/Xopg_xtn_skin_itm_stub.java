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
package gplx.xowa.wikis.pages.skins; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
public class Xopg_xtn_skin_itm_stub implements Xopg_xtn_skin_itm {
	private final byte[] val;
	public Xopg_xtn_skin_itm_stub(byte[] val) {this.val = val;}
	public byte Tid()		{return Xopg_xtn_skin_itm_tid.Tid_sidebar;}
	public byte[] Key()		{return Bry_.Empty;}
	public void Write(Bry_bfr bfr, Xoae_page page) {bfr.Add(val);}
}
