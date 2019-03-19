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
package gplx.xowa.xtns.cites; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.data_stores.*;
class Cite_xtn_data implements Gfo_data_itm {
	public String Key() {return KEY;}
	public void Clear() {
		link_labels.Clear();
	}
	public Cite_link_label_mgr Link_labels() {return link_labels;} private final    Cite_link_label_mgr link_labels = new Cite_link_label_mgr();

	private static final String KEY = "xtn.Cite";
	public static Cite_xtn_data Get_or_make(Gfo_data_store data_store) {
		Cite_xtn_data rv = (Cite_xtn_data)data_store.Get_or_null(KEY);
		if (rv == null) {
			rv = new Cite_xtn_data();
			data_store.Set(rv);
		}
		return rv;
	}
}
