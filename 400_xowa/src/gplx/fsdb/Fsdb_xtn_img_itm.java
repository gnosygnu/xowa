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
package gplx.fsdb; import gplx.*;
public class Fsdb_xtn_img_itm {
	public int Id() {return id;} public void Id_(int v) {this.id = v;} private int id;
	public int W() {return w;} public void W_(int v) {this.w = v;} private int w;
	public int H() {return h;} public void H_(int v) {this.h = v;} private int h;
	public int Db_bin_id() {return bin_db_id;} public Fsdb_xtn_img_itm Db_bin_id_(int v) {bin_db_id = v; return this;} private int bin_db_id;
	public Fsdb_xtn_img_itm Init_by_load(int id, int w, int h) {this.id = id; this.w = w; this.h = h; return this;}
	public static final Fsdb_xtn_img_itm Null = new Fsdb_xtn_img_itm();
	public static final int Bits_default = 8;
}
