/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2020 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.gfui.kits.swts;

import gplx.String_;

public class Swt_html_utl {
    private static final String URL_PREFIX_ABOUT = "about:";
    private static final String URL_PREFIX_BLANK = "blank";

    public static String NormalizeSwtUrl(String url) {
        String rv = url;

        // 2020-09-19|ISSUE#:799|strip "about:" from url due to SWT 4.16
        rv = String_.Has_at_bgn(rv, URL_PREFIX_ABOUT)
            ? String_.Mid(rv, URL_PREFIX_ABOUT.length())
            : rv;

        // 2015-06-09|webkit prefixes "about:blank" to anchors; causes TOC to fail when clicking on links; EX:about:blank#TOC1
        // 2020-09-22|removed webkit check due to SWT 4.16; `html_box.Browser_tid() == Swt_html.Browser_tid_webkit`
        // still strip "blank"; note that SWT 4.16 changes anchors from "file:///#anchor" to "en.w/wiki/page/#anchor"
        rv = String_.Has_at_bgn(rv, URL_PREFIX_BLANK)
            ? String_.Mid(rv, URL_PREFIX_BLANK.length())
            : rv;
        return rv;
    }
}
