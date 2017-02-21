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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
class Xobc_download_itm {
	public Xobc_download_itm(int tid, String http_str, byte[] fsys_url) {this.tid = tid; this.http_str = http_str; this.fsys_url = fsys_url;}
	public int Tid() {return tid;} private final int tid;
	public String Http_str() {return http_str;} private final String http_str;
	public byte[] Fsys_url() {return fsys_url;} private final byte[] fsys_url;
	public static final int Tid_file = 1, Tid_html = 2, Tid_css = 3;
}
