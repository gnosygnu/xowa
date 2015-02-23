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
public class Fsd_thm_itm {
	protected Fsd_thm_itm() {}
	public void Ctor(int id, int owner_id, int w, double time, int page, int h, long size, String modified, String hash) {
		this.id = id; this.owner_id = owner_id; this.width = w; this.thumbtime = time; this.page = page; this.height = h; this.size = size; this.modified = modified; this.hash = hash;
	}
	public int Id() {return id;} public Fsd_thm_itm Id_(int v) {id = v; return this;} private int id;
	public Fsd_fil_itm Owner() {return owner;} public Fsd_thm_itm Owner_(Fsd_fil_itm v) {owner = v; return this;} private Fsd_fil_itm owner = Fsd_fil_itm.Null;
	public int Owner_id() {return owner_id;} public Fsd_thm_itm Owner_id_(int v) {owner_id = v; return this;} private int owner_id;
	public int Width() {return width;} public Fsd_thm_itm Width_(int v) {width = v; return this;} private int width;
	public double Thumbtime() {return thumbtime;} public Fsd_thm_itm Thumbtime_(double v) {thumbtime = v; return this;} private double thumbtime;
	public int Page() {return page;} public Fsd_thm_itm Page_(int v) {page = v; return this;} private int page;
	public int Height() {return height;} public Fsd_thm_itm Height_(int v) {height = v; return this;} private int height;
	public long Size() {return size;} public Fsd_thm_itm Size_(long v) {size = v; return this;} private long size;
	public String Modified() {return modified;} public Fsd_thm_itm Modified_(String v) {modified = v; return this;} private String modified;
	public String Hash() {return hash;} public Fsd_thm_itm Hash_(String v) {hash = v; return this;} private String hash;
	public int Dir_id() {return dir_id;} public Fsd_thm_itm Dir_id_(int v) {dir_id = v; return this;} private int dir_id;
	public int Db_bin_id() {return bin_db_id;} public Fsd_thm_itm Db_bin_id_(int v) {bin_db_id = v; return this;} private int bin_db_id;
	public int Mnt_id() {return mnt_id;} public Fsd_thm_itm Mnt_id_(int v) {mnt_id = v; return this;} private int mnt_id;
	public static Fsd_thm_itm new_() {return new Fsd_thm_itm();}								// NOTE: Owner is null by default
	public static Fsd_thm_itm load_() {return new Fsd_thm_itm().Owner_(new Fsd_fil_itm());}		// NOTE: Owner is new'd b/c load will use owner.Ext_id
	public static final Fsd_thm_itm Null = new Fsd_thm_itm();
	public static final Fsd_thm_itm[] Ary_empty = new Fsd_thm_itm[0];
}
