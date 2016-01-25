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
package gplx.langs.mustaches; import gplx.*; import gplx.langs.*;
class Mustache_render_ctx {
	private Mustache_doc_itm doc;
	public void Init_dom_doc(Mustache_doc_itm doc) {this.doc = doc;}
	public byte[] Render_variable(byte[] key) {
		byte[] rv = Mustache_doc_itm_.Null_val;
		Mustache_doc_itm cur = doc;
		while (cur != Mustache_doc_itm_.Null_itm) {
			rv = doc.Get_by_key(key);
			if (rv != Mustache_doc_itm_.Null_val) break;
			cur = cur.Get_owner();
		}
		return rv;
	}
}
