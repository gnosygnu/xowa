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
package gplx.fsdb.data; import gplx.*; import gplx.fsdb.*;
public class Fsd_fil_itm {
	public Fsd_fil_itm Ctor(int mnt_id, int dir_id, int fil_id, int bin_db_id, byte[] name, int ext_id) {
		this.mnt_id = mnt_id; this.dir_id = dir_id; this.fil_id = fil_id; this.bin_db_id = bin_db_id; this.name = name; this.ext_id = ext_id;
		return this;
	}
	public int Mnt_id() {return mnt_id;} private int mnt_id;
	public int Fil_id() {return fil_id;} private int fil_id;
	public int Dir_id() {return dir_id;} private int dir_id;
	public int Bin_db_id() {return bin_db_id;} private int bin_db_id;
	public byte[] Name() {return name;} private byte[] name;
	public int Ext_id() {return ext_id;} private int ext_id;
	public static final Fsd_fil_itm Null = null;
	public static byte[] Gen_cache_key(Bry_bfr bfr, int dir_id, byte[] name) {
		return bfr.Add_int_variable(dir_id).Add_byte_pipe().Add(name).To_bry_and_clear();
	}
}
