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
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
public class Xowd_search_word_row {
	public Xowd_search_word_row(int id, byte[] text, int page_count) {this.id = id; this.text = text; this.page_count = page_count;}
	public int Id() {return id;} private final int id;
	public byte[] Text() {return text;} private final byte[] text;
	public int Page_count() {return page_count;} private final int page_count;
        public static final Xowd_search_word_row Null = null;
}
