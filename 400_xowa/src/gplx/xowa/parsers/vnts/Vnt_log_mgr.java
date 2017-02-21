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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.dbs.*;
import gplx.xowa.langs.vnts.*;
class Vnt_log_mgr {		
	private int uid;
	private int page_id, rule_idx;
	private Xol_vnt_regy vnt_regy;
	private int[] vnt_ary = new int[10];
	private Vnt_log_tbl tbl;
	public void Init_by_db(Db_conn conn, Xol_vnt_regy vnt_regy) {
		this.vnt_regy = vnt_regy;
		this.tbl = new Vnt_log_tbl(conn);
		tbl.Create_tbl();
		this.uid = 0;
		this.page_id = 0;
	}
	public void Init_by_page(int page_id) {
		this.page_id = page_id;
		this.rule_idx = -1;
		for (int i = 0; i < 10; ++i)
			vnt_ary[i] = 0;
	}
	public void Log_lang(byte[] vnt, int scope) {Log_lang(vnt_regy.Get_by(vnt), scope);}
	public void Log_lang(Xol_vnt_itm itm, int scope) {
		int idx = itm.Idx();
		int val = vnt_ary[idx];
		vnt_ary[idx] = val == 0 ? scope : val | scope;
	}
	public void Log_rule(int src_bgn, int src_end, byte[] src_txt, Vnt_flag_code_mgr flag_codes, Vnt_flag_lang_mgr flag_langs, Vnt_rule_undi_mgr rule_undis, Vnt_rule_bidi_mgr rule_bidis) {
		tbl.Insert(uid, page_id, ++rule_idx
		, flag_codes.Count(), flag_langs.Count(), rule_undis.Len(), rule_bidis.Len()
		, flag_codes.Get(Vnt_flag_code_.Tid_add), flag_codes.Get(Vnt_flag_code_.Tid_del), flag_codes.Get(Vnt_flag_code_.Tid_aout), flag_codes.Get(Vnt_flag_code_.Tid_hide), flag_codes.Get(Vnt_flag_code_.Tid_raw), flag_codes.Get(Vnt_flag_code_.Tid_show), flag_codes.Get(Vnt_flag_code_.Tid_descrip), flag_codes.Get(Vnt_flag_code_.Tid_name), flag_codes.Get(Vnt_flag_code_.Tid_title), flag_codes.Get(Vnt_flag_code_.Tid_err)
		, vnt_ary[0], vnt_ary[1], vnt_ary[2], vnt_ary[3], vnt_ary[4], vnt_ary[5], vnt_ary[6], vnt_ary[7], vnt_ary[8], vnt_ary[9]
		, src_bgn, src_end, src_txt
		);
	}
	public void Rls() {
		tbl.Rls();
	}
	public static final int Scope__lang = 1, Scope__undi = 2, Scope__bidi = 4;
}
