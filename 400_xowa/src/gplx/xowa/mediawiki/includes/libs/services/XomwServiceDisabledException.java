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

import gplx.types.basics.utls.StringUtl;
import gplx.xowa.mediawiki.XophpException;
import gplx.xowa.mediawiki.XophpRuntimeException;

// MW.SRC:1.33.1
/**
 * Exception thrown when trying to access a disabled service.
 */
public class XomwServiceDisabledException extends XophpRuntimeException {

    /**
     * @param string $serviceName
     * @param Exception|null $previous
     */
    public XomwServiceDisabledException(String serviceName) {this(serviceName, null);}
    public XomwServiceDisabledException(String serviceName, XophpException previous) {
        super(StringUtl.Format("Service disabled: {0}", serviceName), 0, previous);
    }

}
