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
package gplx.xowa.addons.wikis.ctgs.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*;
public class Xodb_cat_link_row {
	public Xodb_cat_link_row(int page_id, int cat_id, byte type_id, long timestamp_unix, byte[] sortkey, byte[] sortkey_prefix) {
		this.page_id = page_id;
		this.cat_id = cat_id;
		this.type_id = type_id;
		this.timestamp_unix = timestamp_unix;
		this.sortkey = sortkey;
		this.sortkey_prefix = sortkey_prefix;
	}
	public int Page_id() {return page_id;} private final    int page_id;
	public int Cat_id() {return cat_id;} private final    int cat_id;
	public byte Type_id() {return type_id;} private final    byte type_id;
	public long Timestamp_unix() {return timestamp_unix;} private final    long timestamp_unix;
	public byte[] Sortkey() {return sortkey;} private final    byte[] sortkey;
	public byte[] Sortkey_prefix() {return sortkey_prefix;} private final    byte[] sortkey_prefix;
}