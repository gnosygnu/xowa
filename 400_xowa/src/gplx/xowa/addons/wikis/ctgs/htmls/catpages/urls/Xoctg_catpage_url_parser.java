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
package gplx.xowa.addons.wikis.ctgs.htmls.catpages.urls;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.core.net.qargs.Gfo_qarg_itm;
import gplx.langs.htmls.encoders.Gfo_url_encoder_;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.Xoa_url;
import gplx.xowa.addons.wikis.ctgs.Xoa_ctg_mgr;
public class Xoctg_catpage_url_parser {
	public static Xoctg_catpage_url Parse(Xoa_url url) {
		Gfo_qarg_itm[] args = url.Qargs_ary();
		if (args == null) return Xoctg_catpage_url.New__blank();
		int len = args.length;
		if (len == 0) return Xoctg_catpage_url.New__blank();

		// init caturl structs
		byte[][] keys = new byte[Xoa_ctg_mgr.Tid___max][];
		boolean[] fwds = new boolean[Xoa_ctg_mgr.Tid___max];
		for (int i = 0; i < Xoa_ctg_mgr.Tid___max; ++i) {
			fwds[i] = BoolUtl.Y;
			keys[i] = BryUtl.Empty;
		}
		BryWtr tmp_bfr = BryWtr.New();

		// loop qargs; EX: "?subcatfrom=B&filefrom=C&pagefrom=D"
		for (int i = 0; i < len; ++i) {
			Gfo_qarg_itm arg = args[i];

			// get tid from arg; EX: "pagefrom" -> Tid__page_bgn
			byte[] key = arg.Key_bry();
			byte tid = Key_hash.Get_as_byte_or(key, AsciiByte.Max7Bit);
			if (tid == AsciiByte.Max7Bit) {	// if invalid, warn and skip
				Gfo_usr_dlg_.Instance.Warn_many("", "", "catpage:unknown url arg: raw~={0} key=~{1}", url.To_bry(true, true), key);
				continue;
			}

			// set val
			byte[] val = arg.Val_bry();
			Gfo_url_encoder_.Http_url.Decode(tmp_bfr, BoolUtl.N, val, 0, val.length);
			val = tmp_bfr.ToBryAndClear();

			// set struct
			switch (tid) {
				case Tid__each_bgn:	Set_grp(keys, fwds, val, BoolUtl.Y, Xoa_ctg_mgr.Tid__subc, Xoa_ctg_mgr.Tid__file, Xoa_ctg_mgr.Tid__page); break;	// if "from", default all grps to val; DATE:2014-02-05
				case Tid__each_end:	Set_grp(keys, fwds, val, BoolUtl.N, Xoa_ctg_mgr.Tid__subc, Xoa_ctg_mgr.Tid__file, Xoa_ctg_mgr.Tid__page); break;
				case Tid__subc_bgn: Set_grp(keys, fwds, val, BoolUtl.Y, Xoa_ctg_mgr.Tid__subc); break;
				case Tid__subc_end:	Set_grp(keys, fwds, val, BoolUtl.N, Xoa_ctg_mgr.Tid__subc); break;
				case Tid__file_bgn:	Set_grp(keys, fwds, val, BoolUtl.Y, Xoa_ctg_mgr.Tid__file); break;
				case Tid__file_end:	Set_grp(keys, fwds, val, BoolUtl.N, Xoa_ctg_mgr.Tid__file); break;
				case Tid__page_bgn:	Set_grp(keys, fwds, val, BoolUtl.Y, Xoa_ctg_mgr.Tid__page); break;
				case Tid__page_end:	Set_grp(keys, fwds, val, BoolUtl.N, Xoa_ctg_mgr.Tid__page); break;
			}
		}
		return new Xoctg_catpage_url(keys, fwds);
	}
	private static void Set_grp(byte[][] keys, boolean[] fwds, byte[] key, boolean fwd, byte... tids) {
		int len = tids.length;
		for (int i = 0; i < len; ++i) {
			byte tid = tids[i];
			keys[tid] = key;
			fwds[tid] = fwd;
		}
	}

	private static final byte 
	  Tid__each_bgn = 0, Tid__each_end = 1
	, Tid__subc_bgn = 2, Tid__subc_end = 3
	, Tid__file_bgn = 4, Tid__file_end = 5
	, Tid__page_bgn = 6, Tid__page_end = 7
	;
	public static final byte[]
	  Bry__arg_each_bgn		= BryUtl.NewA7("from")			, Bry__arg_each_end 	= BryUtl.NewA7("until")
	, Bry__arg_subc_bgn 	= BryUtl.NewA7("subcatfrom")		, Bry__arg_subc_end		= BryUtl.NewA7("subcatuntil")
	, Bry__arg_page_bgn 	= BryUtl.NewA7("pagefrom")		, Bry__arg_page_end 	= BryUtl.NewA7("pageuntil")
	, Bry__arg_file_bgn 	= BryUtl.NewA7("filefrom")		, Bry__arg_file_end		= BryUtl.NewA7("fileuntil")
	;
	private static final Hash_adp_bry Key_hash = Hash_adp_bry.ci_a7()
	.Add_bry_byte(Bry__arg_each_bgn	, Tid__each_bgn)	.Add_bry_byte(Bry__arg_each_end	, Tid__each_end)
	.Add_bry_byte(Bry__arg_subc_bgn	, Tid__subc_bgn)	.Add_bry_byte(Bry__arg_subc_end , Tid__subc_end)
	.Add_bry_byte(Bry__arg_page_bgn	, Tid__page_bgn)	.Add_bry_byte(Bry__arg_page_end	, Tid__page_end)
	.Add_bry_byte(Bry__arg_file_bgn	, Tid__file_bgn)	.Add_bry_byte(Bry__arg_file_end	, Tid__file_end)
	;
}
