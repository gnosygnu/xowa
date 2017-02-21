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
package gplx.xowa.apps.wms.apis.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.wms.*; import gplx.xowa.apps.wms.apis.*;
public class Xoapi_orig_rslts {
	public byte[] Orig_wiki() {return orig_wiki;} private byte[] orig_wiki;
	public byte[] Orig_page() {return orig_page;} private byte[] orig_page;
	public int Orig_w() {return orig_w;} private int orig_w;
	public int Orig_h() {return orig_h;} private int orig_h;
	public void Init_all(byte[] wiki, byte[] page, int w, int h) {
		this.orig_wiki = wiki; this.orig_page = page; this.orig_w = w; this.orig_h = h;
	}
	public void Clear() {
		orig_wiki = orig_page = null;
		orig_w = orig_h = 0;
	}
}
