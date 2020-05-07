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


// MW.SRC:1.33.1
/**
 * DestructibleService defines a standard interface for shutting down a service instance.
 * The intended use is for a service container to be able to shut down services that should
 * no longer be used, and allow such services to release any system resources.
 *
 * @note There is no expectation that services will be destroyed when the process (or web request)
 * terminates.
 */
public interface XomwDestructibleService {

    /**
     * Notifies the service object that it should expect to no longer be used, and should release
     * any system resources it may own. The behavior of all service methods becomes undefined after
     * destroy() has been called. It is recommended that implementing classes should throw an
     * exception when service methods are accessed after destroy() has been called.
     */
    public void destroy();

}
