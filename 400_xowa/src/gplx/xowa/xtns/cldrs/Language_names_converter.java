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
package gplx.xowa.xtns.cldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*;
import gplx.langs.phps.*;
import gplx.langs.jsons.*;

// REF.MW: /languages/data/Names.php
class Language_names_converter {
	private final Php_parser parser = new Php_parser();
	private final Php_evaluator eval = new Php_evaluator(new gplx.core.log_msgs.Gfo_msg_log("test")).Comments_for_kv_();
	private final Php_text_itm_parser text_itm_parser = new Php_text_itm_parser().Quote_is_single_(true);
	private final List_adp tmp_list = List_adp_.New();
	private final Byte_obj_ref tmp_result = Byte_obj_ref.zero_();
	private final Bry_bfr tmp_bfr = Bry_bfr_.New();

	public Language_name[] Parse_fil(Io_url url) {
		byte[] src = Io_mgr.Instance.LoadFilBry(url);
		int bgn = Bry_find_.Find_fwd(src, Bry_.new_a7("$names"));
		int end = Bry_find_.Find_bwd(src, Bry_.new_a7("];"), src.length);
		return Parse(Bry_.Mid(src, bgn, end));
	}
	public Language_name[] Parse(byte[] src) {
		parser.Parse_tkns(src, eval);
		Php_line[] lines = (Php_line[])eval.List().To_ary(Php_line.class);
		
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
		return (Language_name[])rv.To_ary_and_clear(Language_name.class);
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
				if (j != 0) tmp_bfr.Add_byte_space();
				Php_tkn_comment tkn = kv.Comments__get_at__or_null(j);
				tkn.To_bfr(tmp_bfr, src, true);
			}
			byte[] comm = tmp_bfr.To_bry_and_clear();
                list.Add(new byte[][] {key, val, comm});
		}
		return (byte[][][])list.To_ary_and_clear(byte[][].class);
	}
	public String To_json(Language_name[] ary) {
		Json_doc_wtr doc_wtr = new Json_doc_wtr();
		doc_wtr.Opt_unicode_y_();
		doc_wtr.Ary_bgn();
		int len = ary.length;
		byte[] key_code = Bry_.new_a7("code");
		byte[] key_name = Bry_.new_a7("name");
		byte[] key_note = Bry_.new_a7("note");
		for (int i = 0; i < len; i++) {
			if (i != 0) doc_wtr.Comma();
			doc_wtr.Nde_bgn();
			Language_name itm = ary[i];
			doc_wtr.Kv(Bool_.N, key_code, itm.Code());
			doc_wtr.Kv(Bool_.Y, key_name, itm.Name());
			doc_wtr.Kv(Bool_.Y, key_note, itm.Note());
			doc_wtr.Nde_end();
		}
		doc_wtr.Ary_end();
		return doc_wtr.Bld_as_str();
	}
}
