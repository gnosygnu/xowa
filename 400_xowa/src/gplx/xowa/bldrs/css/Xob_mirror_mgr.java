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
package gplx.xowa.bldrs.css; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.ios.*; import gplx.xowa.files.downloads.*;
public class Xob_mirror_mgr {
	private final Xof_download_wkr download_wkr; private final Xob_css_parser css_parser;
	private final byte[] page_url; private final Io_url fsys_root;
	public Xob_mirror_mgr(Gfo_usr_dlg usr_dlg, Xof_download_wkr download_wkr, byte[] site_url, byte[] page_url, Io_url fsys_root) {
		this.usr_dlg = usr_dlg; this.download_wkr = download_wkr;
		this.site_url = site_url; this.page_url = page_url; this.fsys_root = fsys_root;
		this.css_parser  = new Xob_css_parser(this);
	}
	public Gfo_usr_dlg Usr_dlg() {return usr_dlg;} private final Gfo_usr_dlg usr_dlg;
	public byte[] Site_url() {return site_url;} private final byte[] site_url;
	public void Code_add(byte[] src_url) {
		byte[] trg_url = Xob_css_tkn__url.To_fsys(src_url);
		code_hash.Add_if_dupe_use_1st(src_url, new Xobc_download_itm(Xobc_download_itm.Tid_css, String_.new_u8(src_url), trg_url));
	}
	public Ordered_hash Code_hash() {return code_hash;} private final Ordered_hash code_hash = Ordered_hash_.New();
	public Ordered_hash File_hash() {return file_hash;} private final Ordered_hash file_hash = Ordered_hash_.New();
	public void Exec() {
		usr_dlg.Plog_many("", "", "html_mirror:download.root_page; url=~{0}", page_url);
		IoEngine_xrg_downloadFil download_xrg = download_wkr.Download_xrg();
		css_parser.Parse(download_xrg.Exec_as_bry(String_.new_u8(page_url)));
		while (true) {
			Xobc_download_itm[] code_ary = (Xobc_download_itm[])code_hash.To_ary_and_clear(Xobc_download_itm.class);
			int code_ary_len = code_ary.length;
			if (code_ary_len == 0) break;
			for (int i = 0; i < code_ary_len; ++i) {
				Xobc_download_itm code = code_ary[i];
				byte[] code_src = download_xrg.Exec_as_bry(code.Http_str());
				Io_mgr.Instance.SaveFilBry(fsys_root.Gen_sub_path_for_os(String_.new_u8(code.Fsys_url())), code_src);
				css_parser.Parse(code_src);
			}
		}
		Xobc_download_itm[] file_ary = (Xobc_download_itm[])file_hash.To_ary_and_clear(Xobc_download_itm.class);
		int file_ary_len = file_ary.length;
		for (int i = 0; i < file_ary_len; ++i) {
			Xobc_download_itm file = file_ary[i];
			download_xrg.Init(file.Http_str(), Io_url_.new_fil_(fsys_root.Gen_sub_path_for_os(String_.new_u8(file.Fsys_url()))));
			download_xrg.Exec();
		}
	}
}
