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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import org.junit.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.wikis.nss.*;
public class Xoh_img_hzip__dump__pgbnr__tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	@Before public void Clear() {fxt.Clear();}
	@Test   public void Basic() {
		fxt.Test__bicode
		( "~%}$DP@A.png~\"A~~wpb-banner-image ~00"
		, "<a href=\"/wiki/File:A.png\" class=\"image\" title=\"A\" xowa_title=\"A.png\"><img data-xowa-title='A.png' data-xoimg='1|-1|-1|-1|-1|-1' src='' width='0' height='0' class='wpb-banner-image ' alt='' srcset='' data-pos-x='0' data-pos-y='0' style='max-width:-1px'></a>"
		);
	}
	@Test   public void Data_pos_x() {	// PAGE:en.v:Antartica; DATE:2016-07-12
		fxt.Test__bicode
		( "~%}$DP@A.png~\"A~~wpb-banner-image ~1.20"
		, "<a href=\"/wiki/File:A.png\" class=\"image\" title=\"A\" xowa_title=\"A.png\"><img data-xowa-title='A.png' data-xoimg='1|-1|-1|-1|-1|-1' src='' width='0' height='0' class='wpb-banner-image ' alt='' srcset='' data-pos-x='1.2' data-pos-y='0' style='max-width:-1px'></a>"
		);
	}
}
