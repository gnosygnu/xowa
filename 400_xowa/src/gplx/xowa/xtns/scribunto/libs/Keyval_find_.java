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
package gplx.xowa.xtns.scribunto.libs;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.KeyVal;
import gplx.types.basics.utls.StringUtl;
public class Keyval_find_ {
	public static KeyVal Find(boolean fail, KeyVal[] root, String... keys) {
		KeyVal found = null;

		KeyVal[] kvs = root;
		int keys_len = keys.length;
		for (int i = 0; i < keys_len; ++i) {
			String key = keys[i];
			int kvs_len = kvs.length;
			found = null;
			for (int j = 0; j < kvs_len; ++j) {
				KeyVal kv = kvs[j];
				if (StringUtl.Eq(kv.KeyToStr(), key)) {
					found = kv;
					break;
				}
			}
			if (found == null) {
				if (fail)
					throw ErrUtl.NewArgs("could not find key", "key", key);
				else
					break;
			}
			if (i == keys_len - 1)
				return found;
			else
				kvs = (KeyVal[])found.Val();
		}
		return found;
	}
}
