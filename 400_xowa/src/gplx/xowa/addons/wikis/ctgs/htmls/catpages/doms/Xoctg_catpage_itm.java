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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.doms; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.ctgs.*; import gplx.xowa.addons.wikis.ctgs.htmls.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
import gplx.dbs.*; import gplx.xowa.wikis.nss.*;
public class Xoctg_catpage_itm {
	private byte version;
	Xoctg_catpage_itm(byte version, byte grp_tid, int page_id, byte[] sortkey_prefix, byte[] sortkey_binary) {
		this.version = version;
		this.grp_tid = grp_tid;
		this.page_id = page_id;
		this.page_ttl = Xoa_ttl.Null;
		this.sortkey_prefix = sortkey_prefix;
		this.sortkey_handle = sortkey_prefix;	// default handle to sortkey_prefix;
		this.sortkey_binary = sortkey_binary;
	}
	public byte					Grp_tid()			{return grp_tid;}			private final    byte grp_tid;		// v2-v4:cl_type_id; subc,page,file
	public int					Page_id()			{return page_id;}			private final    int page_id;		// v2-v4:cl_from
	public byte[]				Sortkey_prefix()	{return sortkey_prefix;}	private byte[] sortkey_prefix;		// v2-v3:cl_sortkey; v4:cl_sortkey_prefix
	public byte[]				Sortkey_handle()	{return sortkey_handle;}	private byte[] sortkey_handle;		// v2-v3:cl_sortkey; v4:cl_sortkey_prefix\nttl_txt; never "cl_sortkey" which is binary ICU value;
	public byte[]				Sortkey_binary()	{return sortkey_binary;}	private byte[] sortkey_binary;		// v2-v4:cl_sortkey; note that v4 is binary icu value
	public Xoa_ttl				Page_ttl()			{return page_ttl;}			private Xoa_ttl page_ttl;

	public void Page_ttl_(Xoa_ttl ttl) {
		this.page_ttl = ttl;
		// sortkey_prefix will be blank for v2; use page_ttl; PAGE:s.w:Category:Computer_science DATE:2015-11-22
		if (version == Version__2 && Bry_.Len_eq_0(sortkey_prefix))
			sortkey_prefix = page_ttl.Page_txt();

		// sortkey_binary will be blank for v2,v3; use page_ttl; PAGE:fr.w:Article_contenant_un_appel_à_traduction_en_anglais; DATE:2016-10-11
		if (version != Version__4 && Bry_.Len_eq_0(sortkey_binary)) sortkey_binary = page_ttl.Page_txt();
	}
	public byte[] Sortkey_handle_make(Bry_bfr tmp_bfr, byte[] prv_sortkey_handle) {
		// page.tbl missing even though in cat_link.tbl; happens for "User:" namespace pages;
		if (page_ttl == Xoa_ttl.Null) {
			// sortkey_prefix exists; happens for [[Category:A]] as opposed to [[Category:A|some_sortkey_prefix]]; also, {{DEFAULTSORTKEY:some_sortkey_prefix}}
			if (Bry_.Len_gt_0(sortkey_prefix)) {
				sortkey_handle = sortkey_prefix;
				return sortkey_handle;				// set sortkey_prefix as new prv_sortkey_handle;
			}
			else {
				// set sortkey_handle to "prv_sortkey" + "page_id"; needed for multiple "missing" catlinks which span 200 page boundaries
				tmp_bfr.Add(prv_sortkey_handle);
				tmp_bfr.Add_byte_nl();
				tmp_bfr.Add_int_variable(page_id);
				sortkey_handle = tmp_bfr.To_bry_and_clear();
				return prv_sortkey_handle;
			}
		}
		// page.tbl exists
		else {
			// "In UCA, tab is the only character that can sort above LF so we strip both of them from the original prefix."; Title.php|getCategorySortKey
			if (sortkey_prefix.length == 0) {
				sortkey_handle = page_ttl.Page_txt();
			}
			else {
				byte[] sortkey_normalized = Bry_.Replace(sortkey_prefix, Sortkey_prefix_replace__src, Sortkey_prefix_replace__trg);
				tmp_bfr.Add(sortkey_normalized);
				tmp_bfr.Add_byte_nl().Add(page_ttl.Page_txt());	// "$prefix\n$unprefixed";
				sortkey_handle = tmp_bfr.To_bry_and_clear();
			}
			return sortkey_handle;
		}
	}

	public static final    Xoctg_catpage_itm[] Ary_empty = new Xoctg_catpage_itm[0];
	public static Xoctg_catpage_itm New_by_rdr(Db_rdr rdr, byte version) {
		byte[] sortkey_binary = Bry_.Empty;
		byte[] sortkey_prefix = Bry_.Empty;
		if (version == Version__4) {
			sortkey_binary = rdr.Read_bry("cl_sortkey");
			sortkey_prefix = rdr.Read_bry_by_str("cl_sortkey_prefix");
		}
		else {
			sortkey_binary = Bry_.Empty;
			sortkey_prefix = rdr.Read_bry_by_str("cl_sortkey");
		}
		return new Xoctg_catpage_itm(version, rdr.Read_byte("cl_type_id"), rdr.Read_int("cl_from"), sortkey_prefix, sortkey_binary);
	}
	public static Xoctg_catpage_itm New_by_ttl(byte grp_tid, int page_id, Xoa_ttl ttl) {	// TEST
		Xoctg_catpage_itm rv = new Xoctg_catpage_itm(Version__4, grp_tid, page_id, ttl.Page_txt(), Bry_.Empty);
		rv.Page_ttl_(ttl);
		return rv;
	}
	private static final byte Version__2 = 2, Version__4 = 4;
	private static final    byte[] Sortkey_prefix_replace__src = Bry_.new_a7("\n\t"), Sortkey_prefix_replace__trg = Bry_.new_a7("  ");
}
