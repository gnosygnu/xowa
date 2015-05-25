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
	public void Ctor(int mnt_id, int dir_id, int fil_id, int thm_id, int bin_db_id, int w, int h, double time, int page, long size, String modified, String hash) {
		this.mnt_id = mnt_id; this.dir_id = dir_id; this.fil_id = fil_id; this.thm_id = thm_id; this.bin_db_id = bin_db_id;
		this.w = w; this.h = h; this.time = time; this.page = page;
		this.size = size; this.modified = modified; this.hash = hash;
	}
	public int		Mnt_id() {return mnt_id;} private int mnt_id;
	public int		Dir_id() {return dir_id;} private int dir_id;
	public int		Fil_id() {return fil_id;} private int fil_id;
	public int		Thm_id() {return thm_id;} private int thm_id;
	public int		Bin_db_id() {return bin_db_id;} private int bin_db_id;
	public int		W() {return w;} private int w;
	public int		H() {return h;} private int h;
	public double	Time() {return time;} private double time;
	public int		Page() {return page;} private int page;
	public long		Size() {return size;} private long size;
	public String	Modified() {return modified;} private String modified;
	public String	Hash() {return hash;} private String hash;
	public int		Req_w() {return req_w;} private int req_w;
	public double	Req_time() {return req_time;} private double req_time;
	public int		Req_page() {return req_page;} private int req_page;

	public void Init_by_req(int w, double time, int page) {this.w = w; this.time = time; this.page = page;}
	public void Init_by_match(Fsd_thm_itm comp) {
		this.req_w = w; this.req_time = time; this.req_page = page;
		this.mnt_id = comp.mnt_id; this.dir_id = comp.dir_id; this.fil_id = comp.fil_id; this.thm_id = comp.thm_id; this.bin_db_id = comp.bin_db_id;
		this.w = comp.w; this.h = comp.h; this.time = comp.time; this.page = comp.page;
		this.size = comp.size; this.modified = comp.modified; this.hash = comp.hash;			
	}
	public static final Fsd_thm_itm Null = null;
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
