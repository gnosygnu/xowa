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
package gplx.xowa.mediawiki.includes.config;

// MW.SRC:1.33.1
/**
 * Interface for configuration instances
 *
 * @since 1.23
 */
public interface XomwConfig {

    /**
     * Get a configuration variable such as "Sitename" or "UploadMaintenance."
     *
     * @param string $name Name of configuration option
     * @return mixed Value configured
     * @throws ConfigException
     */
    public Object get(String name);

    /**
     * Check whether a configuration option is set for the given name
     *
     * @param string $name Name of configuration option
     * @return bool
     * @since 1.24
     */
    public boolean has(String name);
}
