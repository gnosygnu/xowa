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
package gplx.xowa.bldrs.installs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.threads.*;
class Xoi_cmd_dumpfile {
	public byte[] Domain() {return domain;} private byte[] domain;
	public Io_url Bz2_url() {return bz2_url;} Io_url bz2_url;
	public Io_url Xml_url() {return xml_url;} Io_url xml_url;
	public boolean Bz2_unzip() {return bz2_unzip;} private boolean bz2_unzip;
	public void Clear() {domain = null; bz2_url = xml_url = null; bz2_unzip = false;}
	public Xoi_cmd_dumpfile Parse_msg(GfoMsg m) {
		Io_url dump_url = m.ReadIoUrl("url");
		domain = m.ReadBry("domain");
		if (Bry_.Len_eq_0(domain)) domain = Bry_.new_u8(dump_url.OwnerDir().NameOnly());
		bz2_unzip = String_.Eq(m.ReadStr("args"), "unzip");
		String dump_ext = dump_url.Ext();
		if		(String_.Eq(dump_ext, ".bz2")) {
			bz2_url = dump_url;
			if (bz2_unzip) {
				xml_url = bz2_url.GenNewExt("");	// remove .bz2 extension (new file path should be .xml)
				if (!String_.Eq(xml_url.Ext(), ".xml"))
					xml_url = xml_url.GenNewExt(".xml");
			}
		}
		else if	(String_.Eq(dump_ext, ".xml")) {	// user selected xml file; 
			bz2_url = null;
			xml_url = dump_url;
			bz2_unzip = false;	// ignore unzip arge
		}
		return this;
	}
	public Gfo_thread_cmd Exec(Xoi_cmd_mgr cmd_mgr) {
		Xowe_wiki wiki = cmd_mgr.App().Wiki_mgr().Get_by_or_make(domain);
		if (bz2_unzip) {	// unzip requested; add unzip cmd
			GfoMsg unzip_msg = GfoMsg_.new_parse_(Gfo_thread_cmd_unzip.KEY).Add("v", Gfo_thread_cmd_unzip.KEY).Add("src", bz2_url.Raw()).Add("trg", xml_url.Raw());
			Gfo_thread_cmd_unzip unzip_cmd = (Gfo_thread_cmd_unzip)cmd_mgr.Cmd_add(unzip_msg);
			unzip_cmd.Term_cmd_for_src_(Gfo_thread_cmd_unzip.Term_cmd_for_src_noop);	// don't do anything with bz2 after unzip
		}
		if (xml_url == null)
			wiki.Import_cfg().Src_fil_bz2_(bz2_url);
		else
			wiki.Import_cfg().Src_fil_xml_(xml_url);
		return cmd_mgr.Dump_add_many_custom(String_.new_u8(domain), "", "", true);
	}
}
