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
package gplx.xowa.xtns.wdatas.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.json.*;
public class Wdata_langtext_itm implements Wdata_lang_sortable {
	public Wdata_langtext_itm(byte[] lang, byte[] text) {this.lang = lang; this.text = text;}  
	public byte[] Lang() {return lang;} private final byte[] lang;
	public byte[] Text() {return text;} private final byte[] text;
	public byte[] Lang_code() {return lang;}
	public int Lang_sort() {return lang_sort;} public void Lang_sort_(int v) {lang_sort = v;} private int lang_sort = Wdata_lang_sorter.Sort_null;
	@Override public String toString() {// TEST:
		return String_.Concat_with_str("|", String_.new_utf8_(lang), String_.new_utf8_(text));
	}
	public static byte[] Get_text_or_empty(OrderedHash list, byte[][] langs) {
		Wdata_langtext_itm itm = Get_itm_or_null(list, langs);
		return itm == null ? Bry_.Empty : itm.Text();
	}
	public static Wdata_langtext_itm Get_itm_or_null(OrderedHash list, byte[][] langs) {
		if (list == null) return null;
		int langs_len = langs.length;
		for (int i = 0; i < langs_len; ++i) {
			Object itm_obj = list.Fetch(langs[i]);
			if (itm_obj != null) return (Wdata_langtext_itm)itm_obj;
		}
		return null;
	}
}
