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
package gplx.xowa.bldrs.cmds.texts.xmls;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.xowa.wikis.nss.*;
public class Xob_siteinfo_nde {
	public Xob_siteinfo_nde(String site_name, String db_name, byte[] main_page, String generator, String case_dflt, Xow_ns_mgr ns_mgr) {
		this.site_name = site_name;
		this.db_name = db_name;
		this.main_page = main_page;
		this.generator = generator;
		this.case_dflt = case_dflt;
		this.ns_mgr = ns_mgr;
	}
	public String		Site_name() {return site_name;} private final String site_name;
	public String		Db_name() {return db_name;} private final String db_name;
	public byte[]		Main_page() {return main_page;} private final byte[] main_page;
	public String		Generator() {return generator;} private final String generator;
	public String		Case_dflt() {return case_dflt;} private final String case_dflt;
	public Xow_ns_mgr	Ns_mgr() {return ns_mgr;} private final Xow_ns_mgr ns_mgr;
	public void To_bfr(BryWtr bfr) {
		bfr.Add       (main_page).AddBytePipe();
		bfr.AddStrU8(case_dflt).AddBytePipe();
		bfr.AddStrU8(site_name).AddBytePipe();
		bfr.AddStrU8(db_name).AddBytePipe();
		bfr.AddStrU8(generator).AddByteNl();
		int len = ns_mgr.Count();
		for (int i = 0; i < len; ++i) {
			Xow_ns ns = ns_mgr.Ords_get_at(i);
			bfr.AddIntVariable(ns.Id()).AddBytePipe();
			bfr.AddStrU8(Xow_ns_case_.To_str(ns.Case_match())).AddBytePipe();
			bfr.Add(ns.Name_ui()).AddByteNl();
		}
	}
}
