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
package gplx.xowa.wikis.pages.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
public class Xopg_db_html {
	public Xopg_db_html() {this.Clear();}
	public byte[]		Html_bry()			{return html_bry;}	private byte[] html_bry;
	public void			Html_bry_(byte[] v) {this.html_bry = v;}
	public int			Zip_tid()			{return zip_tid;}	private int zip_tid;
	public int			Hzip_tid()			{return hzip_tid;}	private int hzip_tid;
	public void			Zip_tids_(int zip_tid, int hzip_tid) {this.zip_tid = zip_tid; this.hzip_tid = hzip_tid;}
	public void Clear() {
		html_bry = Bry_.Empty;	// NOTE: if null, will cause NullPointer exception on Special pages like Special:XowaDownloadCentral; DATE:2016-07-05
		zip_tid = 0;
		hzip_tid = 0;
	}
}
