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
package gplx.xowa.mediawiki.includes.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.xowa.mediawiki.includes.parsers.lnkis.*;
public class XomwParserCtx {
	public XomwTitle Page_title() {return page_title;} private XomwTitle page_title;
	public Xomw_image_params         Lnki_wkr__make_image__img_params = new Xomw_image_params();
	public byte[][]                  Lnki_wkr__make_image__match_magic_word = new byte[2][];
	public int[]                     Lnki_wkr__make_image__img_size = new int[2];
	public Xomw_params_mto           Linker__makeImageLink__prms = new Xomw_params_mto();

	public void Init_by_page(XomwTitle page_title) {
		this.page_title = page_title;
	}

	public static final int Pos__bos = -1;
}
