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
package gplx.xowa.hdumps.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
public class Xodb_wiki_page_html_row {
	public Xodb_wiki_page_html_row(int page_id, int tid, byte[] data) {this.page_id = page_id; this.tid = tid; this.data = data;}
	public int		Page_id()	{return page_id;} private final int page_id;
	public int		Tid()		{return tid;} private final int tid;
	public byte[]	Data()		{return data;} private final byte[] data;
	public static final int Tid_page = 0, Tid_data = 1;	// SERIALIZED
}
