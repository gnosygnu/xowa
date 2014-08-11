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
package gplx.xowa.dbs.hdumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
class Hdump_text_row {
	public int Id() {return id;} private int id;
	public int Page_id() {return page_id;} private int page_id;
	public int Tid() {return tid;} private int tid;
	public int Version() {return version;} private int version;
	public byte[] Meta() {return meta;} private byte[] meta;
	public byte[] Data() {return data;} private byte[] data;
	public int Sub_id() {return sub_id;} public void Sub_id_(int v) {sub_id = v;} private int sub_id;
	public Hdump_text_row Init(int id, int page_id, int tid, int version, byte[] meta, byte[] data) {
		this.id = id; this.page_id = page_id; this.tid = tid; this.version = version; this.meta = meta; this.data = data;
		return this;
	}
}
class Hdump_text_row_tid {
	public static final int Tid_body = 0, Tid_img = 1, Tid_display_ttl = 2, Tid_content_sub = 3, Tid_sidebar_div = 4;
}
