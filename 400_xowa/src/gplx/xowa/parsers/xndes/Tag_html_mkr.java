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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public interface Tag_html_mkr {
	Tag_html_wkr Tag__create(Xowe_wiki wiki, Xop_ctx ctx);
}
class Tag_html_mkr_noop implements Tag_html_mkr {
	public Tag_html_wkr Tag__create(Xowe_wiki wiki, Xop_ctx ctx) {return Tag_html_wkr_noop.Instance;}
}
class Tag_html_mkr_basic implements Tag_html_mkr {
	private final    boolean atrs_encode;
	public Tag_html_mkr_basic(boolean atrs_encode) {
		this.atrs_encode = atrs_encode;
	}
	public Tag_html_wkr Tag__create(Xowe_wiki wiki, Xop_ctx ctx) {
		return new Tag_html_wkr_basic(wiki.Utl__bfr_mkr().Get_b512(), atrs_encode);
	}
}
