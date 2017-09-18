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
import org.junit.*; import gplx.core.envs.*; import gplx.xowa.htmls.core.hzips.*;
public class Xoh_img_bare_hzip__tst {
	private final    Xoh_hzip_fxt fxt = new Xoh_hzip_fxt().Init_mode_diff_y_();
	private int prv_os_tid = -1;
	@Before public void init() {
		fxt.Clear();
		prv_os_tid = Op_sys.Cur().Tid();
		Op_sys.Cur_(Op_sys.Drd.Tid());		// force drd mode; needed for img_bare
	}
	@After  public void term() {
		Op_sys.Cur_(prv_os_tid);			// revert back to previous mode; otherwise global Op_sys is set to Drd which will cause other tests to fail (notably tidy)
	}
	@Test   public void Hiero() {
		fxt.Test__bicode
		( "~(!<img style=\"margin: 1px; height: 11px;\" src=\"~X1.png\" title=\"X1 [t]\" alt=\"t\">~"
		, "<img style='margin: 1px; height: 11px;' src='file:///android_asset/xowa/bin/any/xowa/xtns/Wikihiero/img/hiero_X1.png' title='X1 [t]' alt='t'>"
		);
	}
	@Test   public void Imap_btn() {
		fxt.Test__bicode
		( "~(\"<img alt=\"About this image\" src=\"~desc-20.png\" style=\"border: none;\">~"
		, "<img alt=\"About this image\" src=\"file:///android_asset/xowa/bin/any/xowa/xtns/ImageMap/imgs/desc-20.png\" style=\"border: none;\">"
		);
	}
	@Test   public void Imap_map() {
		fxt.Test__bicode
		( "~%}*BhtA.png~#$m#T\"A.png~"
		, "<img src=\"file:///mem/xowa/file/commons.wikimedia.org/thumb/7/0/A.png/330px.png\" width=\"330\" height=\"220\" class=\"thumbimage\" alt=\"\" usemap=\"#imagemap_1_1\">"
		);
	}
}
