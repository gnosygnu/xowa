/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.wikis.data.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.data.*;
import gplx.core.primitives.*;
import gplx.xowa.wikis.nss.*;
public class Xowd_page_itm {
	public Xowd_page_itm() {this.Clear();}
	public int			Id() {return id;} public Xowd_page_itm Id_(int v) {id = v; id_val = null; return this;} private int id;
	public Int_obj_val	Id_val() {if (id_val == null) id_val = new Int_obj_val(id); return id_val;} private Int_obj_val id_val;
	public int			Ns_id() {return ns_id;} public Xowd_page_itm Ns_id_(int v) {ns_id = v; return this;} private int ns_id;
	public byte[]		Ttl_page_db() {return ttl_page_db;} public Xowd_page_itm Ttl_page_db_(byte[] v) {ttl_page_db = v; return this;} private byte[] ttl_page_db;	// EX: Category1
	public byte[]		Ttl_full_db() {return ttl_full_db;} private byte[] ttl_full_db;	// EX: Category:Category1
	public boolean			Redirected() {return redirected;} public Xowd_page_itm Redirected_(boolean v) {redirected = v; return this;} private boolean redirected;
	public int			Text_len() {return text_len;} public Xowd_page_itm Text_len_(int v) {text_len = v; return this;} private int text_len;
	public int			Text_db_id() {return text_db_id;} public Xowd_page_itm Text_db_id_(int v) {text_db_id = v; return this;} private int text_db_id;
	public int			Html_db_id() {return html_db_id;} private int html_db_id;
	public int			Cat_db_id()  {return cat_db_id;} private int cat_db_id = -1; // NOTE: cannot be 0, else catpage loader will try to load cat_links from db.id=0
	public byte[]		Text() {return text;} public Xowd_page_itm Text_(byte[] v) {text = v; if (v != null) text_len = v.length; return this;} private byte[] text;
	public int			Random_int() {return random_int;} private int random_int;
	public int			Redirect_id() {return redirect_id;} private int redirect_id;
	public DateAdp		Modified_on() {return modified_on;} public Xowd_page_itm Modified_on_(DateAdp v) {modified_on = v; return this;} private DateAdp modified_on;
	public boolean			Exists() {return exists;} private boolean exists;
	public int			Score() {return score;} private int score;
	public Xow_ns		Ns() {return ns;} private Xow_ns ns;
	public Object		Xtn() {return xtn;} public Xowd_page_itm Xtn_(Object v) {this.xtn = v; return this;} private Object xtn;
	public int			Tdb_row_idx() {return tdb_row_idx;} public void Tdb_row_idx_(int v) {tdb_row_idx = v;} private int tdb_row_idx;
	public int			Rank() {return text_len;}
	public Xowd_page_itm	Init(int id, byte[] ttl_page_db, boolean redirected, int text_len, int text_db_id, int tdb_row_idx) {
		this.id = id; this.ttl_page_db = ttl_page_db; this.redirected = redirected;
		this.text_len = text_len; this.text_db_id = text_db_id; this.tdb_row_idx = tdb_row_idx; 
		id_val = null;
		return this;
	}
	public void Init_by_load__idx(int id, int ns_id, byte[] ttl_page_db, int text_len) {
		this.exists			= true;	// COMMENT: DATE:2016-08-28
		this.id				= id;
		this.id_val			= null;
		this.ns_id			= ns_id;
		this.ttl_page_db	= ttl_page_db;
		this.text_len		= text_len;
	}
	public void	Init_by_load__all(int id, int ns_id, byte[] ttl_page_db, DateAdp modified_on, boolean redirected, int text_len, int random_int, int text_db_id, int html_db_id, int redirect_id, int score, int cat_db_id) {
		// same as Init_by_load__idx; COMMENT: DATE:2016-08-28
		this.exists			= true;
		this.id				= id;
		this.id_val			= null;
		this.ns_id			= ns_id;
		this.ttl_page_db	= ttl_page_db;
		this.text_len		= text_len;

		// other props
		this.redirected		= redirected;
		this.text_db_id		= text_db_id;
		this.modified_on	= modified_on;
		this.random_int		= random_int;
		this.html_db_id		= html_db_id;
		this.redirect_id	= redirect_id;
		this.score			= score;
		this.cat_db_id		= cat_db_id;
	}
	public void	Init_by_tdb(int id, int text_db_id, int tdb_row_idx, boolean redirected, int text_len, int ns_id, byte[] ttl_page_db) {
		// same as Init_by_load__idx; COMMENT: DATE:2016-08-28
		this.exists			= true;
		this.id				= id;
		this.id_val			= null;
		this.ns_id			= ns_id;
		this.ttl_page_db	= ttl_page_db;
		this.text_len		= text_len;

		// other props
		this.redirected		= redirected;
		this.text_db_id		= text_db_id;

		this.tdb_row_idx	= tdb_row_idx;
	}
	public Xowd_page_itm Ttl_(Xow_ns ns, byte[] ttl_page_db) {
		this.ns = ns;
		ns_id = ns.Id();
		this.ttl_page_db = ttl_page_db;
		this.ttl_full_db = ns.Gen_ttl(ttl_page_db);
		return this;
	}
	public Xowd_page_itm Ttl_(Xoa_ttl ttl) {
		ttl_full_db = ttl.Full_txt_w_ttl_case();
		ttl_page_db = ttl.Page_db();
		ns = ttl.Ns();
		ns_id = ns.Id();
		return this;
	}
	public Xowd_page_itm Ttl_(byte[] v, Xow_ns_mgr ns_mgr) {
		ttl_full_db = v;
		Object o = ns_mgr.Names_get_w_colon(v, 0, v.length);
		if (o == null)	{
			ns = ns_mgr.Ns_main();
			ttl_page_db = v;
		}
		else {
			ns = (Xow_ns)o;
			ttl_page_db = Bry_.Mid(v, ns.Name_ui_w_colon().length, v.length);	// EX: "Template:A" -> "Template:" + "A"
		}
		ns_id = ns.Id();
		return this;
	}
	public Xowd_page_itm Clear() {
		id = Id_null; text_len = 0;	// text_len should be 0 b/c text defaults to 0;
		text_db_id = tdb_row_idx = 0; // default to 0, b/c some tests do not set and will fail at -1
		ns_id = Int_.Min_value;
		ttl_full_db = ttl_page_db = null; text = Bry_.Empty;	// default to Ary_empty for entries that have <text />
		ns = null;
		redirected = exists = false;
		modified_on = DateAdp_.MinValue;
		id_val = null;
		html_db_id = cat_db_id = -1;
		redirect_id = -1;
		return this;
	}
	public void Copy(Xowd_page_itm orig) {
		this.id = orig.id;
		this.text_len = orig.text_len;
		this.text_db_id = orig.text_db_id;
		this.tdb_row_idx = orig.tdb_row_idx;
		this.ns_id = orig.ns_id;
		this.ttl_full_db = orig.ttl_full_db;
		this.ttl_page_db = orig.ttl_page_db;
		this.text = orig.text;
		this.ns = orig.ns;
		this.redirected = orig.redirected;
		this.exists = orig.exists;
		this.modified_on = orig.modified_on;
		this.id_val = null;
		this.html_db_id = orig.html_db_id;
		this.cat_db_id = orig.cat_db_id;
	}
	public void Srl_save(Bry_bfr bfr) {gplx.xowa.wikis.tdbs.Xotdb_page_itm_.Txt_id_save(bfr, this);}
	public static final int Id_null = -1, Modified_on_null_int = 0, Redirect_id_null = -1;
	public static final    Xowd_page_itm[] Ary_empty = new Xowd_page_itm[0];
	public static final    Xowd_page_itm Null = null;
	public static Xowd_page_itm new_tmp()							{return new Xowd_page_itm();}
	public static Xowd_page_itm new_srch(int id, int text_len)		{return new Xowd_page_itm().Id_(id).Text_len_(text_len);}

	public static final int Db_row_size_fixed = 
		  (8 * 4)	// page_id, page_namespace, page_random_int, page_text_db_id, page_html_db_id, page_redirect_id, page_score, page_is_redirect (assume byte saved as int in SQLITE)
		+ 14		// page_touched
	;
}
