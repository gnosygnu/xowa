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
package gplx.xowa.addons.bldrs.exports.splits.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.splits.*;
class Bin_meta_itm {
	public Bin_meta_itm(byte bin_type, int bin_owner_id, int bin_id, int bin_db_id) {
		this.bin_type = bin_type; this.bin_owner_id = bin_owner_id; this.bin_id = bin_id; this.bin_db_id = bin_db_id;
	}
	public byte Bin_type() {return bin_type;} private final    byte bin_type;
	public int Bin_owner_id() {return bin_owner_id;} private final    int bin_owner_id;
	public int Bin_id() {return bin_id;} private final    int bin_id;
	public int Bin_db_id() {return bin_db_id;} private final    int bin_db_id;
}
