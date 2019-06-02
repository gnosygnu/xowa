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
package gplx.xowa.htmls.hxtns.wikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.hxtns.*;
public class Hxtn_wiki_itm {
	public Hxtn_wiki_itm(int tid, String domain) {
		this.tid = tid;
		this.domain = domain;
	}
	public int Tid() {return tid;} private final    int tid;
	public String Domain() {return domain;} private final    String domain;

	public static final int Tid__self = 0, Tid__commons = 1, Tid__wikidata = 2, Tid__next_id = 32;
}
