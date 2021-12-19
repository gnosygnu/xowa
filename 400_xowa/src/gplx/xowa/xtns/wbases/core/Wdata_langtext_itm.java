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
package gplx.xowa.xtns.wbases.core;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.utls.StringUtl;
public class Wdata_langtext_itm implements Wdata_lang_sortable {
	public Wdata_langtext_itm(byte[] lang, byte[] text) {this.lang = lang; this.text = text;}  
	public byte[] Lang() {return lang;} private final byte[] lang;
	public byte[] Text() {return text;} private final byte[] text;
	public byte[] Lang_code() {return lang;}
	public int Lang_sort() {return lang_sort;} public void Lang_sort_(int v) {lang_sort = v;} private int lang_sort = Wdata_lang_sorter.Sort_null;
	@Override public String toString() {// TEST:
		return StringUtl.ConcatWith("|", StringUtl.NewU8(lang), StringUtl.NewU8(text));
	}
	public static byte[] Get_text_or_empty(Ordered_hash list, byte[][] langs) {
		Wdata_langtext_itm itm = Get_itm_or_null(list, langs);
		return itm == null ? BryUtl.Empty : itm.Text();
	}
	public static Wdata_langtext_itm Get_itm_or_null(Ordered_hash list, byte[][] langs) {
		if (list == null) return null;
		int langs_len = langs.length;
		for (int i = 0; i < langs_len; ++i) {
			Object itm_obj = list.GetByOrNull(langs[i]);
			if (itm_obj != null) return (Wdata_langtext_itm)itm_obj;
		}
		return null;
	}
}
