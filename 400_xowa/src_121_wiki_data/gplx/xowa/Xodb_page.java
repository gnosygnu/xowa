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
import gplx.core.primitives.*;
public class Xodb_page implements Xobl_data_itm {
	public Xodb_page() {this.Clear();}
	public int			Id() {return id;} public Xodb_page Id_(int v) {id = v; id_val = null; return this;} private int id;
	public Int_obj_val	Id_val() {if (id_val == null) id_val = Int_obj_val.new_(id); return id_val;} private Int_obj_val id_val;
	public int			Ns_id() {return ns_id;} public Xodb_page Ns_id_(int v) {ns_id = v; return this;} private int ns_id;
	public byte[]		Ttl_page_db() {return ttl_page_db;} public Xodb_page Ttl_page_db_(byte[] v) {ttl_page_db = v; return this;} private byte[] ttl_page_db;	// EX: Category1
	public byte[]		Ttl_full_db() {return ttl_full_db;} private byte[] ttl_full_db;	// EX: Category:Category1
	public boolean			Redirected() {return redirected;} public Xodb_page Redirected_(boolean v) {redirected = v; return this;} private boolean redirected;
	public int			Wtxt_len() {return wtxt_len;} public Xodb_page Wtxt_len_(int v) {wtxt_len = v; return this;} private int wtxt_len;
	public int			Wtxt_db_id() {return wtxt_db_id;} public Xodb_page Wtxt_db_id_(int v) {wtxt_db_id = v; return this;} private int wtxt_db_id;
	public byte[]		Wtxt() {return wtxt;} public Xodb_page Wtxt_(byte[] v) {wtxt = v; if (v != null) wtxt_len = v.length; return this;} private byte[] wtxt;
	public int			Html_db_id() {return html_db_id;} private int html_db_id;
	public int			Redirect_id() {return redirect_id;} private int redirect_id;
	public DateAdp		Modified_on() {return modified_on;} public Xodb_page Modified_on_(DateAdp v) {modified_on = v; return this;} private DateAdp modified_on;
	public boolean			Exists() {return exists;} public Xodb_page Exists_(boolean v) {exists = v; return this;} private boolean exists;
	public Xow_ns		Ns() {return ns;} private Xow_ns ns;
	public Object		Xtn() {return xtn;} public Xodb_page Xtn_(Object v) {this.xtn = v; return this;} private Object xtn;
	public int			Tdb_row_idx() {return tdb_row_idx;} public void Tdb_row_idx_(int v) {tdb_row_idx = v;} private int tdb_row_idx;
	public Xodb_page	Init(int id, byte[] ttl_page_db, boolean redirected, int wtxt_len, int wtxt_db_id, int tdb_row_idx) {
		this.id = id; this.ttl_page_db = ttl_page_db; this.redirected = redirected;
		this.wtxt_len = wtxt_len; this.wtxt_db_id = wtxt_db_id; this.tdb_row_idx = tdb_row_idx; 
		id_val = null;
		return this;
	}
	public void	Init_by_sql(int id, int ns_id, byte[] ttl_page_db, DateAdp modified_on, boolean redirected, int wtxt_len, int wtxt_db_id, int html_db_id, int redirect_id) {
		this.id = id;
		this.ns_id = ns_id;
		this.ttl_page_db = ttl_page_db;
		this.modified_on = modified_on;
		this.redirected = redirected;
		this.wtxt_len = wtxt_len;
		this.wtxt_db_id = wtxt_db_id;
		this.html_db_id = html_db_id;
		this.redirect_id = redirect_id;
	}
	public void	Init_by_tdb(int id, int wtxt_db_id, int tdb_row_idx, boolean redirected, int wtxt_len, int ns_id, byte[] ttl_page_db) {
		this.id = id;
		this.wtxt_db_id = wtxt_db_id;
		this.tdb_row_idx = tdb_row_idx;
		this.redirected = redirected;
		this.wtxt_len = wtxt_len;
		this.ns_id = ns_id;
		this.ttl_page_db = ttl_page_db;
	}
	public Xodb_page Ttl_(Xow_ns ns, byte[] ttl_page_db) {
		this.ns = ns;
		ns_id = ns.Id();
		this.ttl_page_db = ttl_page_db;
		this.ttl_full_db = ns.Gen_ttl(ttl_page_db);
		return this;
	}
	public Xodb_page Ttl_(Xoa_ttl ttl) {
		ttl_full_db = ttl.Full_txt();
		ttl_page_db = ttl.Page_db();
		ns = ttl.Ns();
		ns_id = ns.Id();
		return this;
	}
	public Xodb_page Ttl_(byte[] v, Xow_ns_mgr ns_mgr) {
		ttl_full_db = v;
		Object o = ns_mgr.Names_get_w_colon(v, 0, v.length);
		if (o == null)	{
			ns = ns_mgr.Ns_main();
			ttl_page_db = v;
		}
		else			{
			ns = (Xow_ns)o;
			ttl_page_db = Bry_.Mid(v, ns.Name_txt_w_colon().length, v.length);	// EX: "Template:A" -> "Template:" + "A"
		}
		ns_id = ns.Id();
		return this;
	}
	public void Clear() {
		id = Id_null; wtxt_len = 0;	// wtxt_len should be 0 b/c wtxt defaults to 0;
		wtxt_db_id = tdb_row_idx = 0; // default to 0, b/c some tests do not set and will fail at -1
		ns_id = Int_.MinValue;
		ttl_full_db = ttl_page_db = null; wtxt = Bry_.Empty;	// default to Ary_empty for entries that have <wtxt />
		ns = null;
		redirected = exists = false;
		modified_on = DateAdp_.MinValue;
		id_val = null;
		html_db_id = -1;
		redirect_id = -1;
	}
	public void Copy(Xodb_page orig) {
		this.id = orig.id;
		this.wtxt_len = orig.wtxt_len;
		this.wtxt_db_id = orig.wtxt_db_id;
		this.tdb_row_idx = orig.tdb_row_idx;
		this.ns_id = orig.ns_id;
		this.ttl_full_db = orig.ttl_full_db;
		this.ttl_page_db = orig.ttl_page_db;
		this.wtxt = orig.wtxt;
		this.ns = orig.ns;
		this.redirected = orig.redirected;
		this.exists = orig.exists;
		this.modified_on = orig.modified_on;
		this.id_val = null;
		this.html_db_id = orig.html_db_id;
	}
	public void Srl_save(Bry_bfr bfr) {Xodb_page_.Txt_id_save(bfr, this);}
	public static final int Id_null = -1, Modified_on_null_int = 0;
	public static final Xodb_page[] Ary_empty = new Xodb_page[0];
	public static final Xodb_page Null = null;
	public static Xodb_page new_tmp()							{return new Xodb_page();}
	public static Xodb_page new_srch(int id, int wtxt_len)		{return new Xodb_page().Id_(id).Wtxt_len_(wtxt_len);}
}
