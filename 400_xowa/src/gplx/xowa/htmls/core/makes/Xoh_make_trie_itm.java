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
package gplx.xowa.htmls.core.makes; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
class Xoh_make_trie_itm {
	public Xoh_make_trie_itm(byte tid, boolean elem_is_xnde, byte subst_end_byte, byte[] key) {this.tid = tid; this.key = key; this.elem_is_xnde = elem_is_xnde; this.subst_end_byte = subst_end_byte;}
	public byte Tid() {return tid;} private final byte tid;
	public byte[] Key() {return key;} private final byte[] key;
	public boolean Elem_is_xnde() {return elem_is_xnde;} private final boolean elem_is_xnde;
	public byte Subst_end_byte() {return subst_end_byte;} private final byte subst_end_byte;
}
