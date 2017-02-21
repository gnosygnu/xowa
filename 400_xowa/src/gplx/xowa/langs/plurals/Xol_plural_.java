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
public class Xol_plural_ {
	public static Xol_plural new_by_lang_id(int lang_id) {
		switch (lang_id) {
			case Xol_lang_stub_.Id_ru:	return Xol_plural_ru.Instance;
			default:					return Xol_plural__default.Instance;
		}
	}
	public static byte[][] Fill_ary(byte[][] words, int words_len, int reqd_len) {// convert words to an ary of at least reqd_len where new entries are filled with last item; EX: {"a", "b"}, 3 -> {"a", "b", "b"}
		byte[][] rv = new byte[reqd_len][];
		byte[] last = words[words_len - 1];
		for (int i = 0; i < reqd_len; i++)
			rv[i] = i < words_len ? words[i] : last;
		return rv;
	}
}
class Xol_plural__default implements Xol_plural {
	public byte[] Plural_eval(Xol_lang_itm lang, int count, byte[][] forms) {
		int forms_len = forms.length;
		switch (forms_len) {
			case 0:		return Bry_.Empty;		// forms is empty; do nothing
			case 1:		return forms[0];			// only one word specified; use it;	REF.MW:$pluralForm = min( $pluralForm, count( $forms ) - 1 );
			default:	return count == 1 ? forms[0] : forms[1]; // TODO_OLD: incorporate plurals.xml logic
		}
	}
	public static final    Xol_plural__default Instance = new Xol_plural__default(); Xol_plural__default() {}
}
