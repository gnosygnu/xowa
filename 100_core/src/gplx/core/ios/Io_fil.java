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
public class Io_fil implements gplx.CompareAble {
	public Io_fil(Io_url url, String data) {this.url = url; this.data = data;}
	public Io_url Url() {return url;} public Io_fil Url_(Io_url v) {url = v; return this;} Io_url url;
	public String Data() {return data;} public Io_fil Data_(String v) {data = v; return this;} private String data;
	public int compareTo(Object obj) {
		return gplx.CompareAble_.Compare(url.Raw(), ((Io_fil)obj).Url().Raw());
	}
	public static Io_fil[] new_ary_(Io_url[] url_ary) {
		int url_ary_len = url_ary.length;
		Io_fil[] rv = new Io_fil[url_ary_len];
		for (int i = 0; i < url_ary_len; i++) {
			Io_url url = url_ary[i];
			String data = Io_mgr.Instance.LoadFilStr(url);
			Io_fil fil = new Io_fil(url, data);
			rv[i] = fil;
		}
		return rv;
	}
	public static final Io_fil[] Ary_empty = new Io_fil[0];
}
