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
	public int Id() {return id;} private int id;
	public int Owner_id() {return owner_id;} private int owner_id;
	public int W() {return w;} private int w;
	public double Time() {return time;} private double time;
	public int Page() {return page;} private int page;
	public int H() {return h;} private int h;
	public long Size() {return size;} private long size;
	public String Modified() {return modified;} private String modified;
	public String Hash() {return hash;} private String hash;
	public int Dir_id() {return dir_id;} private int dir_id;
	public int Db_bin_id() {return bin_db_id;} private int bin_db_id;
	public int Mnt_id() {return mnt_id;} private int mnt_id;
	public int Req_w() {return req_w;} private int req_w;
	public double Req_time() {return req_time;} private double req_time;
	public int Req_page() {return req_page;} private int req_page;
	public void Init_by_load(int bin_db_id, int id, int owner_id, int w, double time, int page, int h, long size, String modified, String hash) {
		this.bin_db_id = bin_db_id; this.id = id; this.owner_id = owner_id; 
		this.w = w; this.time = time; this.page = page; this.h = h; this.size = size; this.modified = modified; this.hash = hash;
	}
	public void Init_by_insert(int bin_db_id, int dir_id, int fil_id, int thm_id) {this.bin_db_id = bin_db_id; this.dir_id = dir_id; this.owner_id = fil_id; this.id = thm_id;}
	public void Init_by_req(int w, double time, int page) {this.w = w; this.time = time; this.page = page;}
	public void Init_by_match(Fsd_thm_itm comp) {
		this.req_w = w;
		this.req_time = time;
		this.req_page = page;
		this.id = comp.id;
		this.owner_id = comp.owner_id;
		this.w = comp.w;
		this.time = comp.time;
		this.page = comp.page;
		this.h = comp.h;
		this.size = comp.size;
		this.modified = comp.modified;
		this.hash = comp.hash;
		this.dir_id = comp.dir_id;
		this.bin_db_id = comp.bin_db_id;
		this.mnt_id = comp.mnt_id;
	}
	public Fsd_thm_itm Mnt_id_(int v) {mnt_id = v; return this;}
	public static final Fsd_thm_itm Null = new Fsd_thm_itm();
	public static final Fsd_thm_itm[] Ary_empty = new Fsd_thm_itm[0];
	public static Fsd_thm_itm new_() {return new Fsd_thm_itm();} Fsd_thm_itm() {}
}
class Fsdb_thm_itm_sorter implements gplx.lists.ComparerAble {
	public int compare(Object lhsObj, Object rhsObj) {
		Fsd_thm_itm lhs = (Fsd_thm_itm)lhsObj;
		Fsd_thm_itm rhs = (Fsd_thm_itm)rhsObj;
		int comp =	Int_.Compare	(lhs.W()	, rhs.W());		if (comp != CompareAble_.Same) return -comp;	// sort by decreasing width
			comp =	Double_.Compare	(lhs.Time()	, rhs.Time());	if (comp != CompareAble_.Same) return  comp;	// sort by increasing time
		return		Int_.Compare	(lhs.Page()	, rhs.Page());													// sort by increasing page
	}
	public static final Fsdb_thm_itm_sorter I = new Fsdb_thm_itm_sorter(); Fsdb_thm_itm_sorter() {}
}
