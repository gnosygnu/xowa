/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.xtns.scribunto.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.scribunto.*;
public class Keyval_find_ {
	public static Keyval Find(boolean fail, Keyval[] root, String... keys) {
		Keyval found = null;

		Keyval[] kvs = root;
		int keys_len = keys.length;
		for (int i = 0; i < keys_len; ++i) {
			String key = keys[i];
			int kvs_len = kvs.length;
			found = null;
			for (int j = 0; j < kvs_len; ++j) {
				Keyval kv = kvs[j];
				if (String_.Eq(kv.Key(), key)) {
					found = kv;
					break;
				}
			}
			if (found == null) {
				if (fail)
					throw Err_.new_wo_type("could not find key", "key", key);
				else
					break;
			}
			if (i == keys_len - 1)
				return found;
			else
				kvs = (Keyval[])found.Val();
		}
		return found;
	}
}
