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
package gplx.xowa.mediawiki.includes;

import gplx.xowa.mediawiki.XophpArray;

// Any MW-specific globals which aren't specific to a class go here
// For now, just use it for PHP $GLOBALS
public class XomwGlobals {
    public static final XomwGlobals Instance = new XomwGlobals();
    public XophpArray<Object> GLOBALS = new XophpArray<>();
    public void Add(String key, Object val) {
        GLOBALS.Add(key, val);
    }
}
