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
package gplx.xowa.addons.bldrs.exports.packs.files;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
public class Pack_zip_name_bldr {	// en.wikipedia.org-file-ns.000-db.001.xowa -> Xowa_enwiki_2016-05_file_ns.000_db.001.zip
	private final Io_url pack_dir;
	private final byte[] wiki_domain, zip_name_prefix;

	public Pack_zip_name_bldr(Io_url pack_dir, String wiki_domain_str, String wiki_abrv, String wiki_date, String custom_name) {
		this.pack_dir = pack_dir;
		this.wiki_domain = BryUtl.NewU8(wiki_domain_str);
		
		String zip_name_suffix = custom_name == null ? StringUtl.Replace(wiki_date, ".", "-") : custom_name;	// "Xowa_enwiki_2016-10_core.zip" vs "Xowa_enwiki_custom_core.zip"
		this.zip_name_prefix = BryUtl.NewU8("Xowa_" + wiki_abrv + "_" + zip_name_suffix);
	}
	public Io_url Bld(Io_url orig_url) {
		// get name and add .zip; EX: "en.wikipedia.org-file-core.xowa" -> "en.wikipedia.org-file-core.zip"
		byte[] orig_bry = BryUtl.NewU8(orig_url.NameOnly() + ".zip");

		// swap dashes with unders; EX: "en.wikipedia.org-file-core.xowa" -> "en.wikipedia.org_file_core.zip"
		orig_bry = BryUtl.Replace(orig_bry, AsciiByte.Dash, AsciiByte.Underline);

		// swap domain with xobc-style-prefix; EX: "en.wikipedia.org_file_core.zip" -> "Xowa_enwiki_2017-03_file_core.zip"
		orig_bry = BryUtlByWtr.Replace(orig_bry, wiki_domain, zip_name_prefix);

		return pack_dir.GenSubFil(StringUtl.NewU8(orig_bry));
	}
	public Io_url Bld_by_suffix(String suffix, int pack_num) {
		// make fil_name EX: "Xowa_enwiki_2017-03" + "_" + "xtn.fulltext_search.001" + .zip
		String fil_name = StringUtl.NewU8(zip_name_prefix) + "_" + suffix + "." + IntUtl.ToStrPadBgnZero(pack_num + List_adp_.Base1, 3) + ".zip";
		return pack_dir.GenSubFil(fil_name);
	}
	public static Io_url To_wiki_url(Io_url wiki_dir, Io_url zip_dir) {	
		// get wiki_url based on wiki_dir and xobc_zip_fil; EX: "/wiki/en.wikipedia.org/", "/wiki/tmp/Xowa_enwiki_2016-09_file_core_deletion_2016-09/" -> "/wiki/en.wikipedia.org-file-core-deletion-2016.09.zip" 
		String name_str = zip_dir.NameOnly() + ".xowa";
		byte[] name_bry = BryUtl.NewU8(name_str);
		int pos = Bry_find__Find_fwd_idx(name_bry, AsciiByte.Underline, 2);
		name_bry = BryLni.Mid(name_bry, pos, name_bry.length);
		name_bry = BryUtl.Add(BryUtl.NewU8(wiki_dir.NameOnly()), name_bry);
		name_bry = BryUtl.Replace(name_bry, AsciiByte.Underline, AsciiByte.Dash);
		return wiki_dir.GenSubFil(StringUtl.NewU8(name_bry));
	}
	private static int Bry_find__Find_fwd_idx(byte[] src, byte val, int find_max) {
		int src_len = src.length;
		int find_cur = 0;
		int cur_pos = 0;
		while (true) {
			int new_pos = BryFind.FindFwd(src, val, cur_pos, src_len);
			if (new_pos == -1) throw ErrUtl.NewFmt("failed to find value; src={0} val={1}", src, val);
			if (find_cur++ == find_max) return new_pos;
			cur_pos = new_pos + 1;
		}
	}
}
