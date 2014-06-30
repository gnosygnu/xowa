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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
public class Xow_wiki_abrv {
	public Xol_lang_itm Lang_itm() {return lang_itm;} private Xol_lang_itm lang_itm;
	public byte Domain_tid() {return domain_tid;} private byte domain_tid;
	public void Ctor_by_parse(Xol_lang_itm lang_itm, byte domain_tid) {
		this.lang_itm = lang_itm; this.domain_tid = domain_tid;
	}
	public void Clear() {
		lang_itm = null;
		domain_tid = Xow_wiki_abrv_.Tid_null;
	}
}
