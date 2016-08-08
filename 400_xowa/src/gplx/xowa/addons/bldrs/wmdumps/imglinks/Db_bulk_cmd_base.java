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
package gplx.xowa.addons.bldrs.wmdumps.imglinks; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.wmdumps.*;
import gplx.dbs.*;
public abstract class Db_bulk_cmd_base {
	public void Exec() {
		int uid_max = Get_uid_max();
		int uid_rng = Get_uid_rng();
		int uid_bgn = -1, uid_end = uid_rng;
		this.Bulk_bgn();
		while (uid_bgn <= uid_max) {
			Bulk_run(uid_bgn, uid_end);
			uid_bgn += uid_rng;
			uid_end += uid_rng;
		}
		this.Bulk_end();
	}
	protected abstract int Get_uid_max();
	protected abstract int Get_uid_rng();
	protected abstract void Bulk_bgn();
	protected abstract void Bulk_end();
	protected abstract void Bulk_run(int uid_bgn, int uid_end);
}
class Imglnk_bulk_cmd__img_id extends Db_bulk_cmd_base {
	private final    Db_conn conn;
	private final    Db_attach_mgr attach_mgr;
	private final    int img_wiki;
	private String sql;
	public Imglnk_bulk_cmd__img_id(Db_conn conn, boolean wiki_is_local, Xowe_wiki wiki) {
		this.conn = conn;
		this.attach_mgr = new Db_attach_mgr(conn, new Db_attach_itm("page_db", wiki.Data__core_mgr().Db__core().Tbl__page().Conn()));
		this.img_wiki = wiki_is_local ? 0 : 1;
		// xowa.wiki.image.sqlite3
		// INSERT INTO img_link (img_name, img_wiki) SELECT img_name, Count(img_name) FROM img_link_tmp GROUP BY img_name
	}
	@Override protected int Get_uid_max() {
		return conn.Exec_select_as_int("SELECT Max(img_uid) FROM img_link_tmp", -1);
	}
	@Override protected int Get_uid_rng() {return 10000;}
	@Override protected void Bulk_bgn() {
		sql = String_.Concat_lines_nl_skip_last	// ANSI.Y
		( "UPDATE  img_link_tmp"
		, "SET     img_wiki = {0}"
		, ",       img_id = (SELECT p.page_id FROM <page_db>page p WHERE p.page_namespace = 6 AND p.page_title = img_link_tmp.img_name)"
		, "WHERE   img_uid > {1} AND img_uid <= {2}"
		, "AND     img_name IN (SELECT p.page_title FROM <page_db>page p WHERE p.page_namespace = 6 AND p.page_title = img_link_tmp.img_name)"
		); 
		sql = attach_mgr.Resolve_sql(sql);
		attach_mgr.Attach();
		conn.Txn_bgn("imglnk.bulk");
	}
	@Override protected void Bulk_end() {
		conn.Txn_end();
		attach_mgr.Detach();
	}
	@Override protected void Bulk_run(int uid_bgn, int uid_end) {
		conn.Exec_sql(String_.Format("updating img_link_tmp; wiki={0} uid={1}", img_wiki, uid_bgn), String_.Format(sql, img_wiki, uid_bgn, uid_end));
	}
	public static void Bulk_exec(Db_conn conn, boolean wiki_is_local, Xowe_wiki wiki) {
		new Imglnk_bulk_cmd__img_id(conn, wiki_is_local, wiki).Exec();
	}
}
