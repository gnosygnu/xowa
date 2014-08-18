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
package gplx.xowa.dbs; import gplx.*; import gplx.xowa.*;
public class Xodb_file_tid {
	public static final byte Tid_core = 1, Tid_text = 2, Tid_category = 3, Tid_search = 4, Tid_wikidata = 5, Tid_temp = 6, Tid_html = 7;	// SERIALIZED
	public static final String Key_core = "core", Key_text = "text", Key_category = "category", Key_wikidata = "wikidata", Key_temp = "temp", Key_search = "search", Key_html = "html";
	public static String Xto_key(byte v) {
		switch (v) {
			case Tid_core:			return Key_core;
			case Tid_text:			return Key_text;
			case Tid_category:		return Key_category;
			case Tid_wikidata:		return Key_wikidata;
			case Tid_temp:			return Key_temp;
			case Tid_search:		return Key_search;
			case Tid_html:			return Key_html;
			default:				throw Err_.unhandled(v);
		}
	}
}
