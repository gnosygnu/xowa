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
package gplx.xowa.dbs.hdumps; import gplx.*; import gplx.xowa.*; import gplx.xowa.dbs.*;
class Hdump_data_mgr {
//		private Xow_wiki wiki;
//		public void Init_by_wiki(Xow_wiki wiki) {this.wiki = wiki;}
	public void Select(int ns_id, Hdump_page_row page, int page_id) {
		Hdump_db db = Get_db(ns_id, page_id);
		// int reg = 
		db.Page_tbl().Select(null, page, page_id);
		// db.Frag_tbl().Select(list, 1);
		// page.Frags_(list.XtoAry());
	}
	public void Insert(int ns_id, Hdump_page_row page) {
		byte[] page_html = page.Page_html();
		int page_html_len = page_html.length;
		Hdump_db db = Get_db(ns_id, page_html_len);
		Hdump_frag_row[] frags = page.Frags();
		int frags_len = frags.length;
		db.Page_tbl().Insert(page.Page_id(), page.Page_html(), frags_len, 1);
		Hdump_frag_tbl frag_tbl = db.Frag_tbl();
		for (int i = 0; i < frags_len; ++i) {
			Hdump_frag_row frag = frags[i];
			frag_tbl.Insert(null, frag.Frag_id(), page.Page_id(), frag.Frag_tid(), frag.Frag_key(), frag.Frag_text());
		}
	}
	public void Update() {
	}
	private Hdump_db Get_db(int ns_id, int len) {return null;}
}
class Hdump_db {
	public Io_url Db_url() {return db_url;} private Io_url db_url = Io_url_.Null;
	public void Txn_bgn() {}
	public void Txn_end() {}
	public void Rls() {}
	public Hdump_page_tbl Page_tbl() {return page_tbl;} private Hdump_page_tbl page_tbl = new Hdump_page_tbl();
	public Hdump_frag_tbl Frag_tbl() {return frag_tbl;} private Hdump_frag_tbl frag_tbl = new Hdump_frag_tbl();
}
class Hdump_bldr {
	public byte[] Do_this() {
		// SELECT * FROM page_id
		return null;
	}
}
class Hdump_rdr {
	public void Read(Hdump_page page, Hdump_text_itm[] itms) {
		int len = itms.length;
		for (int i = 0; i < len; ++i) {
			Hdump_text_itm itm = itms[i];
			switch (itm.Tid()) {
				case Hdump_text_tid.Tid_page: Read_page(page, itm); break;
				case Hdump_text_tid.Tid_file: break;
			}
		}
	}
	public void Read_page(Hdump_page page, Hdump_text_itm itm) {
		page.Version_id_(Bry_.Xto_int(itm.Args()));
		page.Html_(itm.Text());
	}
	public void Read_file(Hdump_page page, Hdump_text_itm file) {
		//byte[][] args = null;
	}
}
class Hdump_page {
	public int Version_id() {return version_id;} public void Version_id_(int v) {version_id = v;} private int version_id;
	public byte[] Html() {return html;} public void Html_(byte[] v) {html = v;} private byte[] html;
	public void Generate() {
		// swap in each file
		// add skin
		// swap in display title, content_sub, menu
	}
}
class Hdump_itm_file {
	public byte[] Ttl() {return ttl;} private byte[] ttl;
	public void Init(byte[][] ary) {
		ttl = ary[0];
	}
}
class Hdump_text_itm {
	public int Tid() {return tid;} public void Tid_(int v) {tid = v;} private int tid;
	public byte[] Args() {return args;} public void Args_(byte[] v) {args = v;} private byte[] args;
	public byte[] Text() {return text;} public void Text_(byte[] v) {text = v;} private byte[] text;
}
class Hdump_text_tid {
	public static final int Tid_page = 0, Tid_file = 1, Tid_display = 2, Tid_content_sub = 3, Tid_sidebar = 4;
}