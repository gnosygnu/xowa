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
package gplx.xowa.wikis.xwikis.sitelinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.xwikis.*;
import gplx.xowa.wikis.domains.*;
public class Xoa_sitelink_itm {
	public Xoa_sitelink_itm(Xoa_sitelink_grp grp, byte[] key, byte[] name) {
		this.grp = grp; this.key = key; this.name = name;
		grp.Add(this);
	}
	public byte[]				Key()					{return key;}					private final byte[] key;			// EX: "en", "c"; NOTE: usually lang_key, but can be xwiki key
	public byte[]				Name()					{return name;}					private byte[] name;					// EX: "English", "Commons"
	public Xoa_sitelink_grp		Grp()					{return grp;}					private Xoa_sitelink_grp grp;			// EX: "Tier 1"
	public Xow_domain_itm		Site_domain()			{return site_domain;}			private Xow_domain_itm site_domain;		// EX: "en.wikipedia.org"; "commons.wikimedia.org"
	public byte[]				Page_name()				{return page_name;}				private byte[] page_name;				// EX: "Earth", "Terre"
	public boolean				Page_name_is_empty()	{return page_name_is_empty;}	private boolean   page_name_is_empty;		// EX: [[fr:]]
	public byte[][]				Page_badges()			{return page_badges;}			private byte[][] page_badges;

	public void Init_by_page(Xow_domain_itm site_domain, byte[] page_name, boolean page_name_is_empty, byte[][] page_badges) {
		this.site_domain = site_domain;
		this.page_name = page_name; this.page_name_is_empty = page_name_is_empty; this.page_badges = page_badges;
		grp.Active_len__add();
	}
	public void Move_to(Xoa_sitelink_grp new_grp) {
		if (Bry_.Eq(new_grp.Name(), grp.Name())) return; // same grp
		grp.Del(key);
		new_grp.Add(this);
		this.grp = new_grp;
	}
	public void Grp_(Xoa_sitelink_grp v) {this.grp = v;}
	public void Site_name_(byte[] v) {this.name = v;}
	public void To_bfr(Bry_bfr bfr) {
		bfr.Add_int_digits(1, Xoa_sitelink_mgr_parser.Tid__itm).Add_byte_pipe();
		bfr.Add(key).Add_byte_pipe();
		bfr.Add(name).Add_byte_nl();
	}
}
