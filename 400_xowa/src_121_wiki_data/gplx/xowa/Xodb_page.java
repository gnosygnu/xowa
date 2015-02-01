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
	public byte[]		Ttl_wo_ns() {return ttl_wo_ns;} public Xodb_page Ttl_wo_ns_(byte[] v) {ttl_wo_ns = v; return this;} private byte[] ttl_wo_ns;
	public boolean			Type_redirect() {return type_redirect;} public Xodb_page Type_redirect_(boolean v) {type_redirect = v; return this;} private boolean type_redirect;
	public DateAdp		Modified_on() {return modified_on;} public Xodb_page Modified_on_(DateAdp v) {modified_on = v; return this;} DateAdp modified_on;
	public int			Text_len() {return text_len;} public Xodb_page Text_len_(int v) {text_len = v; return this;} private int text_len;
	public int			Text_db_id() {return text_db_id;} public Xodb_page Text_db_id_(int v) {text_db_id = v; return this;} private int text_db_id;
	public int			Html_db_id() {return html_db_id;} public Xodb_page Html_db_id_(int v) {html_db_id = v; return this;} private int html_db_id;
	public int			Redirect_id() {return redirect_id;} public void Redirect_id_(int v) {redirect_id = v;} private int redirect_id;
	public byte[]		Text() {return text;} public Xodb_page Text_(byte[] v) {text = v; if (v != null) text_len = v.length; return this;} private byte[] text;
	public boolean			Exists() {return exists;} public Xodb_page Exists_(boolean v) {exists = v; return this;} private boolean exists;
	public int			Db_row_idx() {return db_row_idx;} public Xodb_page Db_row_idx_(int v) {db_row_idx = v; return this;} private int db_row_idx;
	public Xow_ns		Ns() {return ns;} private Xow_ns ns;
	public Object		Xtn() {return xtn;} public Xodb_page Xtn_(Object v) {this.xtn = v; return this;} Object xtn;
	public byte[]		Ttl_w_ns() {return ttl_w_ns;} private byte[] ttl_w_ns;
	public Xodb_page Ttl_(Xow_ns ns, byte[] ttl_wo_ns) {
		this.ns = ns;
		ns_id = ns.Id();
		this.ttl_wo_ns = ttl_wo_ns;
		this.ttl_w_ns = ns.Gen_ttl(ttl_wo_ns);
		return this;
	}
	public Xodb_page Ttl_(Xoa_ttl ttl) {
		ttl_w_ns = ttl.Full_txt();
		ttl_wo_ns = ttl.Page_db();
		ns = ttl.Ns();
		ns_id = ns.Id();
		return this;
	}
	public Xodb_page Ttl_(byte[] v, Xow_ns_mgr ns_mgr) {
		ttl_w_ns = v;
		Object o = ns_mgr.Names_get_w_colon(v, 0, v.length);
		if (o == null)	{
			ns = ns_mgr.Ns_main();
			ttl_wo_ns = v;
		}
		else			{
			ns = (Xow_ns)o;
			ttl_wo_ns = Bry_.Mid(v, ns.Name_txt_w_colon().length, v.length);	// EX: "Template:A" -> "Template:" + "A"
		}
		ns_id = ns.Id();
		return this;
	}
	public void Clear() {
		id = Id_null; text_len = 0;	// text_len should be 0 b/c text defaults to 0;
		text_db_id = db_row_idx = 0; // default to 0, b/c some tests do not set and will fail at -1
		ns_id = Int_.MinValue;
		ttl_w_ns = ttl_wo_ns = null; text = Bry_.Empty;	// default to Ary_empty for entries that have <text />
		ns = null;
		type_redirect = exists = false;
		modified_on = DateAdp_.MinValue;
		id_val = null;
		html_db_id = -1;
		redirect_id = -1;
	}
	public void Copy(Xodb_page orig) {
		this.id = orig.id;
		this.text_len = orig.text_len;
		this.text_db_id = orig.text_db_id;
		this.db_row_idx = orig.db_row_idx;
		this.ns_id = orig.ns_id;
		this.ttl_w_ns = orig.ttl_w_ns;
		this.ttl_wo_ns = orig.ttl_wo_ns;
		this.text = orig.text;
		this.ns = orig.ns;
		this.type_redirect = orig.type_redirect;
		this.exists = orig.exists;
		this.modified_on = orig.modified_on;
		this.id_val = null;
		this.html_db_id = orig.html_db_id;
	}
	public Xodb_page Set_all_(int id, int text_db_id, int db_row_idx, boolean redirect, int text_len, byte[] ttl_wo_ns) {
		this.id = id; this.text_db_id = text_db_id; this.db_row_idx = db_row_idx; this.type_redirect = redirect; this.text_len = text_len; this.ttl_wo_ns = ttl_wo_ns;
		id_val = null;
		return this;
	}
	public void Srl_save(Bry_bfr bfr) {Xodb_page_.Txt_id_save(bfr, this);}
	public static Xodb_page tmp_() {return new Xodb_page();}
	public static Xodb_page srch_(int id, int text_len) {return new Xodb_page().Id_(id).Text_len_(text_len);}
	public static final Xodb_page[] Ary_empty = new Xodb_page[0];
	public static final int Timestamp_null = 0;
	public static final int Id_null = -1;
	public static final Xodb_page Null = null;
}
