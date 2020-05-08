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
package gplx.xowa.mediawiki.includes.libs.services;

import gplx.xowa.mediawiki.XophpException;
import gplx.xowa.mediawiki.XophpRuntimeException;

// MW.SRC:1.33.1
/**
 * Exception thrown when trying to access a service on a disabled container or factory.
 */
public class XomwContainerDisabledException extends XophpRuntimeException {

    /**
     * @param Exception|null $previous
     */
    public XomwContainerDisabledException(){this(null);}
    public XomwContainerDisabledException(XophpException previous) {
        super("Container disabled!", 0, previous);
    }

}
