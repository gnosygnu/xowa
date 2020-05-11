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

package gplx.xowa.mediawiki.vendor.wikimedia.Assert.src;

import gplx.String_;
import gplx.xowa.mediawiki.XophpLogicException;

// MW.SRC:1.33.1
/**
 * Exception indicating that an invariant assertion failed.
 * This generally means an error in the internal logic of a function, or a serious problem
 * in the runtime environment.
 *
 * @license MIT
 * @author Daniel Kinzler
 * @copyright Wikimedia Deutschland e.V.
 */
public class XomwInvariantException extends XophpLogicException implements XomwAssertionException {
    public XomwInvariantException(String fmt, Object... args) {super(String_.Format(fmt, args));}
}
