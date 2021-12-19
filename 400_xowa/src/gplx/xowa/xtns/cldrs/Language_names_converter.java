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
package gplx.xowa.xtns.cldrs;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.libs.files.Io_mgr;
import gplx.libs.files.Io_url;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.wrappers.ByteRef;
import gplx.langs.jsons.Json_doc_wtr;
import gplx.langs.phps.Php_evaluator;
import gplx.langs.phps.Php_itm_ary;
import gplx.langs.phps.Php_itm_kv;
import gplx.langs.phps.Php_line;
import gplx.langs.phps.Php_line_assign;
import gplx.langs.phps.Php_parser;
import gplx.langs.phps.Php_text_itm_parser;
import gplx.langs.phps.Php_tkn_comment;
import gplx.types.basics.utls.BoolUtl;
// REF.MW: /languages/data/Names.php
class Language_names_converter {
	private final Php_parser parser = new Php_parser();
	private final Php_evaluator eval = new Php_evaluator(new gplx.core.log_msgs.Gfo_msg_log("test")).Comments_for_kv_();
	private final Php_text_itm_parser text_itm_parser = new Php_text_itm_parser().Quote_is_single_(true);
	private final List_adp tmp_list = List_adp_.New();
	private final ByteRef tmp_result = ByteRef.NewZero();
	private final BryWtr tmp_bfr = BryWtr.New();

	public Language_name[] Parse_fil(Io_url url) {
		byte[] src = Io_mgr.Instance.LoadFilBry(url);
		int bgn = BryFind.FindFwd(src, BryUtl.NewA7("$names"));
		int end = BryFind.FindBwd(src, BryUtl.NewA7("];"), src.length);
		return Parse(BryLni.Mid(src, bgn, end));
	}
	public Language_name[] Parse(byte[] src) {
		parser.Parse_tkns(src, eval);
		Php_line[] lines = (Php_line[])eval.List().ToAry(Php_line.class);
		
		List_adp rv = List_adp_.New();
		int lines_len = lines.length;
		for (int i = 0; i < lines_len; i++) {
			Php_line line = lines[i];
			Php_line_assign assign_line = (Php_line_assign)line;
			
			byte[][][] ary = Parse_ary_kvs(src, assign_line);
			for (byte[][] itm : ary) {
				rv.Add(new Language_name(itm[0], itm[1], itm[2]));
			}
		}
		eval.Clear();
		return (Language_name[])rv.ToAryAndClear(Language_name.class);
	}
	private byte[][][] Parse_ary_kvs(byte[] src, Php_line_assign assign) {
		List_adp list = List_adp_.New();
		Php_itm_ary ary = (Php_itm_ary)assign.Val();
		int ary_len = ary.Subs_len();
		for (int i = 0; i < ary_len; i++) {
			Php_itm_kv kv = (Php_itm_kv)ary.Subs_get(i);
			byte[] key = kv.Key().Val_obj_bry();
			byte[] val = text_itm_parser.Parse_as_bry(tmp_list, kv.Val().Val_obj_bry(), tmp_result, tmp_bfr);

			int comments_len = kv.Comments__len();
			for (int j = 0; j < comments_len; j++) {
				if (j != 0) tmp_bfr.AddByteSpace();
				Php_tkn_comment tkn = kv.Comments__get_at__or_null(j);
				tkn.To_bfr(tmp_bfr, src, true);
			}
			byte[] comm = tmp_bfr.ToBryAndClear();
                list.Add(new byte[][] {key, val, comm});
		}
		return (byte[][][])list.ToAryAndClear(byte[][].class);
	}
	public String To_json(Language_name[] ary) {
		Json_doc_wtr doc_wtr = new Json_doc_wtr();
		doc_wtr.Opt_unicode_y_();
		doc_wtr.Ary_bgn();
		int len = ary.length;
		byte[] key_code = BryUtl.NewA7("code");
		byte[] key_name = BryUtl.NewA7("name");
		byte[] key_note = BryUtl.NewA7("note");
		for (int i = 0; i < len; i++) {
			if (i != 0) doc_wtr.Comma();
			doc_wtr.Nde_bgn();
			Language_name itm = ary[i];
			doc_wtr.Kv(BoolUtl.N, key_code, itm.Code());
			doc_wtr.Kv(BoolUtl.Y, key_name, itm.Name());
			doc_wtr.Kv(BoolUtl.Y, key_note, itm.Note());
			doc_wtr.Nde_end();
		}
		doc_wtr.Ary_end();
		return doc_wtr.Bld_as_str();
	}
}
