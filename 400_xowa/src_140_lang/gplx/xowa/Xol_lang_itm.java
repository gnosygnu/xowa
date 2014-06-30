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
public class Xol_lang_itm {
	public Xol_lang_itm(int id, byte[] key, byte[] canonical_name) {this.id = id; this.key = key; this.canonical_name = canonical_name; this.local_name = canonical_name;}
	public int Id() {return id;} private int id;
	public byte[] Key() {return key;} private byte[] key;
	public byte[] Canonical_name() {return canonical_name;} private byte[] canonical_name;
	public byte[] Local_name() {return local_name;} private byte[] local_name;
	public byte[] Local_grp() {return local_grp;} private byte[] local_grp = Bry_.Empty;
	public Xol_lang_itm Local_atrs_load(byte[] local_name, byte[] local_grp) {
		if (Bry_.Len_gt_0(local_name)) this.local_name = local_name;
		if (Bry_.Len_gt_0(local_grp)) this.local_grp = local_grp;
		return this;
	}
	public Xol_lang_itm Local_atrs_reset() {local_name = canonical_name; local_grp = Bry_.Empty; return this;}
}
