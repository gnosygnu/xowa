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
package gplx.xowa.mediawiki.includes.cache.localisation;
import gplx.libs.files.Io_url;
import gplx.xowa.mediawiki.*;
public class XomwLocalisationCacheForXowa extends XomwLocalisationCache { 	private static boolean init;
	public static void Init_ip(Io_url val) {
		init = false;
		IP = val.Raw();
	}
	public XophpArray getItem_ary(String code, String key) {
		if (!init) {
			init = true;
			this.loadPluralFiles();
		}

		XophpArray rv = getCompiledPluralRules(code);
		return rv;
	}
}