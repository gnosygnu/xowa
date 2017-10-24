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
package gplx.xowa.drds.pages; import gplx.*; import gplx.xowa.*; import gplx.xowa.drds.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.sections.*;
import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.wikis.pages.*; import gplx.xowa.wikis.pages.tags.*;
public class Xod_page_itm {
	public int Page_id() {return page_id;} private int page_id;
	public long Rev_id() {return rev_id;} private long rev_id;
	public String Ttl_text() {return ttl_text;} private String ttl_text;
	public String Ttl_db() {return ttl_db;} private String ttl_db;
	public String Ttl_special() {return ttl_special;} public void Ttl_special_(String v) {ttl_special = v;} private String ttl_special;
	public String Redirected() {return redirected;} private String redirected;
	public String Description() {return description;} private String description;
	public String Modified_on() {return modified_on;} private String modified_on;
	public boolean Is_editable() {return is_editable;} private boolean is_editable;
	public boolean Is_main_page() {return is_main_page;} private boolean is_main_page;
	public boolean Is_disambiguation() {return is_disambiguation;} private boolean is_disambiguation;
	public int Lang_count() {return lang_count;} private int lang_count;
	public String Head_url() {return head_url;} private String head_url;
	public String Head_name() {return head_ttl;} private String head_ttl;
	public String First_allowed_editor_role() {return first_allowed_editor_role;} private String first_allowed_editor_role;
	public List_adp Section_list() {return section_list;} private List_adp section_list = List_adp_.New();
	public Xoh_page Hpg() {return hpg;} private Xoh_page hpg;
	public Xopg_tag_mgr Head_tags() {return head_tags;} private final    Xopg_tag_mgr head_tags = new Xopg_tag_mgr(Bool_.Y);
	public Xopg_tag_mgr Tail_tags() {return tail_tags;} private final    Xopg_tag_mgr tail_tags = new Xopg_tag_mgr(Bool_.N);
	public void Init(int page_id, int rev_id
		, String ttl_text, String ttl_db, String redirected, String description, String modified_on
		, boolean is_editable, boolean is_main_page, boolean is_disambiguation, int lang_count
		, String head_url, String head_ttl
		, String first_allowed_editor_role
		) {
		this.page_id = page_id; this.rev_id = rev_id;
		this.ttl_text = ttl_text; this.ttl_db = ttl_db; this.redirected = redirected; this.description = description; this.modified_on = modified_on;
		this.is_editable = is_editable; this.is_main_page = is_main_page; this.is_disambiguation = is_disambiguation; this.lang_count = lang_count;
		this.head_url = head_url; this.head_ttl= head_ttl; this.first_allowed_editor_role = first_allowed_editor_role;
	}
	public void Init_by_dbpg(Xoa_ttl ttl, Xowd_page_itm db_page) {
		this.page_id = db_page.Id();
		this.rev_id = page_id;
		this.ttl_text = String_.new_u8(ttl.Page_txt());
		this.ttl_db = ttl.Page_db_as_str();
		this.modified_on = db_page.Modified_on().XtoStr_fmt_iso_8561_w_tz();
		this.lang_count = 1;
		this.redirected = null;
		this.description = null;
		this.is_editable = false;
		this.is_main_page = false;
		this.is_disambiguation = false;
		this.head_url = null;
		this.head_ttl = null;
		this.first_allowed_editor_role = null;
	}
	public void Init_by_hpg(Xoh_page hpg) {
		this.hpg = hpg;
	}
	public String To_str() {
		Bry_bfr bfr = Bry_bfr_.New();
		bfr	.Add_int_variable(page_id).Add_byte_pipe()
			.Add_long_variable(rev_id).Add_byte_pipe()
			.Add_str_u8(ttl_text).Add_byte_pipe()
			.Add_str_u8(ttl_db).Add_byte_pipe()
			.Add_str_a7_null(modified_on).Add_byte_pipe()
			.Add_int_variable(lang_count).Add_byte_pipe()
			.Add_str_a7_null(redirected).Add_byte_pipe()
			.Add_str_a7_null(description).Add_byte_pipe()
			.Add_yn(is_editable).Add_byte_pipe()
			.Add_yn(is_main_page).Add_byte_pipe()
			.Add_yn(is_disambiguation).Add_byte_pipe()
			.Add_str_a7_null(head_url).Add_byte_pipe()
			.Add_str_a7_null(head_ttl).Add_byte_pipe()
			.Add_str_a7_null(first_allowed_editor_role).Add_byte_nl()
			;
		int len = section_list.Count();
		for (int i = 0; i < len; ++i) {
			Xoh_section_itm section = (Xoh_section_itm)section_list.Get_at(i);
			section.To_bfr(bfr);
		}
		return bfr.To_str_and_clear();
	}
}
