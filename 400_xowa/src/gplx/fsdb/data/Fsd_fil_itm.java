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
	public int Id() {return id;} private int id;
	public int Owner() {return owner;} private int owner;
	public int Ext_id() {return ext_id;} private int ext_id;
	public String Name() {return name;} private String name;
	public int Db_bin_id() {return bin_db_id;} private int bin_db_id;
	public int Mnt_id() {return mnt_id;} public Fsd_fil_itm Mnt_id_(int v) {mnt_id = v; return this;} private int mnt_id;
	public Fsd_fil_itm Init(int id, int owner, int ext_id, String name, int bin_db_id) {this.id = id; this.owner = owner; this.ext_id = ext_id; this.name = name; this.bin_db_id = bin_db_id; return this;}
	public void Init_for_insert(int bin_db_id, int dir_id, int fil_id) {
		this.bin_db_id = bin_db_id; this.owner = dir_id; this.id = fil_id;
	}
	public static final int Null_id = 0;
	public static final Fsd_fil_itm Null = new Fsd_fil_itm().Init(Null_id, 0, 0, "", Fsd_bin_tbl.Null_db_bin_id);
}
