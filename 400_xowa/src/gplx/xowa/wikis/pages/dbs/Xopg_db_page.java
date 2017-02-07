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
package gplx.xowa.wikis.pages.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.wikis.nss.*;
public class Xopg_db_page {
	public Xopg_db_page() {this.Clear();}
	// from page table
	public boolean		Exists()			{return exists;}			private boolean exists;
	public boolean		Exists_n()			{return !exists;}
	public int			Id()				{return id;}				private int id;
	public int			Ns_id()				{return ns_id;}				private int ns_id;
	public byte[]		Ttl_bry()			{return ttl_bry;}			private byte[] ttl_bry;
	public DateAdp		Modified_on()		{return modified_on;}		private DateAdp modified_on;
	public int			Text_len()			{return text_len;}			private int text_len;
	public int			Text_db_id()		{return text_db_id;}		private int text_db_id;
	public int			Html_db_id()		{return html_db_id;}		private int html_db_id;	
	public int			Redirect_to_id()	{return redirect_to_id;}	private int redirect_to_id;
	public int			Score()				{return score;}				private int score;

	public void Exists_y_()							{this.Exists_(Bool_.Y);}
	public void Exists_n_()							{this.Exists_(Bool_.N);}
	public void Exists_(boolean v)						{this.exists = v;}
	public Xopg_db_page Id_(int v)					{this.id = v; return this;}
	public Xopg_db_page Modified_on_(DateAdp v)		{this.modified_on = v; return this;}
	public Xopg_db_page Html_db_id_(int v)			{this.html_db_id = v; return this;}

	// wiki-related
	public Xow_ns		Ns()				{return ns;}				private Xow_ns ns;
	public Xoa_ttl		Ttl()				{return ttl;}				private Xoa_ttl ttl;

	// init methods
	public Xopg_db_page Init_by_db(int id, int ns_id, byte[] ttl_bry, DateAdp modified_on, int text_len, int text_db_id, int html_db_id, int redirect_to_id, int score) {
		this.id = id; this.ns_id = ns_id; this.ttl_bry = ttl_bry; this.modified_on = modified_on;
		this.text_len = text_len; this.text_db_id = text_db_id; this.html_db_id = html_db_id; this.redirect_to_id = redirect_to_id; this.score = score;
		this.ns = null; this.ttl = null;
		return this;
	}
	public Xopg_db_page Init_by_wiki(Xow_wiki wiki) {
		this.ns = wiki.Ns_mgr().Ids_get_or_null(ns_id);
		this.ttl = wiki.Ttl_parse(ns_id, ttl_bry);
		return this;
	}
	public void Clear() {
		this.exists = true;
		this.modified_on = DateAdp_.MinValue;	// NOTE: must set to MinValue else some tests will fail
		this.html_db_id = -1;	// NOTE: must set to -1 b/c code checks for -1 to indicate no html; DATE:2016-07-14
	}
}
