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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
import gplx.dbs.*; import gplx.xowa.users.data.*;
public class Xoud_site_mgr {
	private Xoud_site_tbl tbl;
	private final    Xoud_id_mgr id_mgr;
	public Xoud_site_mgr(Xoud_id_mgr id_mgr) {this.id_mgr = id_mgr;}
	public void Conn_(Db_conn conn, boolean created) {
		tbl = new Xoud_site_tbl(conn);
		if (created) tbl.Create_tbl();
	}
	public Xoud_site_row[]	Get_all() {return tbl.Select_all();}
	public Xoud_site_row	Select_by_domain(byte[] domain) {return tbl.Select_by_domain(domain);}
	public void				Delete_by_domain(byte[] domain) {tbl.Delete_by_domain(domain);}
	public void Import(String domain, String name, String path, String date, String xtn) {	// insert or update wiki
		Xoud_site_row itm = tbl.Select_by_domain(Bry_.new_u8(domain));
		if (itm == null)
			tbl.Insert(id_mgr.Get_next_and_save("xowa.user.site"), 0, domain, name, path, date, xtn);
		else
			tbl.Update(itm.Id(), 0, domain, name, path, date, xtn);
	}
	public void Update(Xoud_site_row row) {
		tbl.Update(row.Id(), row.Priority(), row.Domain(), row.Name(), row.Path(), row.Date(), row.Xtn());
	}
}
