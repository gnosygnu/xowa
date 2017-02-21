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
package gplx.xowa.langs.plurals; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xol_plural_ru implements Xol_plural {
	public byte[] Plural_eval(Xol_lang_itm lang, int count, byte[][] forms) {
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
	public static final Xol_plural_ru Instance = new Xol_plural_ru(); Xol_plural_ru() {}
}
