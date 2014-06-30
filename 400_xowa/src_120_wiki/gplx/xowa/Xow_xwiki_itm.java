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
package gplx.xowa; import gplx.*;
public class Xow_xwiki_itm implements gplx.CompareAble {
	public Xow_xwiki_itm(byte[] key, byte[] fmt, byte wiki_tid, int lang_id, byte[] domain) {this.key = key; this.fmt = fmt; this.wiki_tid = wiki_tid; this.lang_id = lang_id; this.domain = domain; this.key_str = String_.new_utf8_(key);}
	public byte[] Key() {return key;} private byte[] key;
	public String Key_str() {return key_str;} private String key_str;
	public byte[] Fmt() {return fmt;} private byte[] fmt;
	public byte[] Domain() {return domain;} private byte[] domain;
	public byte Wiki_tid() {return wiki_tid;} private byte wiki_tid;
	public int Lang_id() {return lang_id;} private int lang_id;
	public boolean Type_is_lang(int cur_lang_id) {return lang_id != Xol_lang_itm_.Id__unknown && cur_lang_id != lang_id && Bry_.Len_gt_0(fmt);}
	public boolean Offline() {return offline;} public Xow_xwiki_itm Offline_(boolean v) {offline = v; return this;} private boolean offline;
	public int compareTo(Object obj) {Xow_xwiki_itm comp = (Xow_xwiki_itm)obj; return Bry_.Compare(key, comp.key);}		
}
