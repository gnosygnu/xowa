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
package gplx.xowa.langs.plurals; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xol_plural_ru implements Xol_plural {
	public byte[] Plural_eval(Xol_lang lang, int count, byte[][] forms) {
		int forms_len = forms.length;
		switch (forms_len) {
			case 0:	return null;				// forms is empty; do nothing
			case 2:	return count == 1 ? forms[0] : forms[1];
			default: {	// either 1, 3, or >3;
				if (forms_len == 1) forms = Xol_plural_.Fill_ary(forms, forms_len, 3);
				if (count > 10 && ((count % 100) / 10) == 1) 
					return forms[2];
				else {
					switch (count % 10) {
						case 1:					return forms[0];
						case 2: case 3: case 4: return forms[1];
						default:				return forms[2];
					}
				}
			}
		}
	}
	public static final Xol_plural_ru _ = new Xol_plural_ru(); Xol_plural_ru() {}
}
