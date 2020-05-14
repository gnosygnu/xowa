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

import gplx.String_;
import gplx.xowa.mediawiki.XophpArray;
import gplx.xowa.mediawiki.includes.XomwGlobals;

// MW.SRC:1.33.1
/**
 * Accesses configuration settings from GLOBALS
 *
 * @since 1.23
 */
public class XomwGlobalVarConfig implements XomwConfig {
	/**
	 * Prefix to use for configuration variables
	 * @var string
	 */
	private String prefix;

	/**
	 * Default builder function
	 * @return GlobalVarConfig
	 */
	public static XomwGlobalVarConfig newInstance() {
		return new XomwGlobalVarConfig();
	}

	public XomwGlobalVarConfig() {this("wg");}
	public XomwGlobalVarConfig(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @inheritDoc
	 */
	public Object get(String name) {
		if (!this.has(name)) {
			throw new XomwConfigException(String_.Format("get: undefined option: '{0}'", name));
		}
		return this.getWithPrefix(this.prefix, name);
	}

	/**
	 * @inheritDoc
	 */
	public boolean has(String name) {
		return this.hasWithPrefix(this.prefix, name);
	}

	/**
	 * Get a variable with a given prefix, if not the defaults.
	 *
	 * @param string prefix Prefix to use on the variable, if one.
	 * @param string name Variable name without prefix
	 * @return mixed
	 */
	protected Object getWithPrefix(String prefix, String name) {
		return XomwGlobals.Instance.GLOBALS.Get_by(prefix + name);
	}

	/**
	 * Check if a variable with a given prefix is set
	 *
	 * @param string prefix Prefix to use on the variable
	 * @param string name Variable name without prefix
	 * @return bool
	 */
	protected boolean hasWithPrefix(String prefix, String name) {
		String var = prefix + name;
		return XophpArray.array_key_exists(var, XomwGlobals.Instance.GLOBALS);
	}
}
