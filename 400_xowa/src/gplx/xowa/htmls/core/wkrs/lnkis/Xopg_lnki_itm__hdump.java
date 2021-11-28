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
package gplx.xowa.htmls.core.wkrs.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.xowa.wikis.pages.lnkis.*;
public class Xopg_lnki_itm__hdump implements Xopg_lnki_itm {
	public Xopg_lnki_itm__hdump(Xoa_ttl ttl) {this.ttl = ttl;}
	public Xoa_ttl	Ttl() {return ttl;} private final Xoa_ttl ttl;
	public int		Html_uid() {return html_uid;} private int html_uid; public void Html_uid_(int v) {html_uid = v;}
	@Override public String toString() {
		return ttl.Full_db_as_str();
	}
}
