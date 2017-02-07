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
package gplx.xowa.users.wikis; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
class Xow_wiki_loader {
	public void Load(String mount) {
		/*
		load mount
		Xow_mount_links links = user.Load_links(mount_id);
		load data
		for (int i = 0; i < links_len; i++) {
			switch (link_tid) {
				case data:
				
					wiki.Db_mgr().Root_url() = Get_data(rel_id);
					break;
			}
		}
		*/
	}
}
class Xou_wiki_itm_source {
	public static final int Tid_user = 1, Tid_wmf = 2, Tid_wikia = 3;
}
class Xou_wiki_itm_path_layout {
	public static final int Tid_multiple = 1, Tid_root = 2;
}
class Xou_wiki_part {
	public int Id() {return id;} public void Id_(int v) {id = v;} private int id;
	public boolean Deleted() {return deleted;} public void Deleted_(boolean v) {deleted = v;} private boolean deleted;
	public Xofs_url_itm Url() {return url;} private Xofs_url_itm url = new Xofs_url_itm();
	public String Domain() {return domain;} public void Domain_(String v) {domain = v;} private String domain;
	public String Version() {return version;} public void Version_(String v) {version = v;} private String version;
	public String Source() {return source;} public void Source_(String v) {source = v;} private String source;
	public DateAdp Make_date() {return make_date;} public void Make_date_(DateAdp v) {make_date = v;} private DateAdp make_date;
	public String Misc() {return misc;} public void Misc_(String v) {misc = v;} private String misc;
}
