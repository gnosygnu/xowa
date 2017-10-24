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
package gplx.xowa.xtns.wbases.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.xowa.wikis.data.*; import gplx.dbs.*; import gplx.xowa.wikis.dbs.*; import gplx.xowa.wikis.data.tbls.*;
import gplx.xowa.xtns.wbases.dbs.*; import gplx.xowa.xtns.wbases.claims.enums.*;
public class Xob_wdata_pid_sql extends Xob_wdata_pid_base {
	private Xowd_wbase_pid_tbl tbl__pid;
	private Xowb_prop_tbl tbl__prop;
	private final    Ordered_hash datatype_hash = Ordered_hash_.New_bry();
	@Override public String Page_wkr__key() {return gplx.xowa.bldrs.Xob_cmd_keys.Key_wbase_pid;}
	@Override public void Pid_bgn() {
		Xow_db_mgr db_mgr = wiki.Data__core_mgr();

		// init datatype_hash
		Wbase_enum_hash enum_hash = Wbase_claim_type_.Reg;
		byte len = (byte)enum_hash.Len();
		for (byte i = 0; i < len; i++) {
			Wbase_claim_type claim_type = (Wbase_claim_type)enum_hash.Get_itm_or(i, null);
			datatype_hash.Add(Bry_.new_u8(claim_type.Key_for_scrib()), claim_type);
		}

		// init wbase_pid
		tbl__pid = db_mgr.Db__wbase().Tbl__wbase_pid();
		tbl__pid.Create_tbl();
		tbl__pid.Insert_bgn();

		// init wbase_prop
		tbl__prop = db_mgr.Db__wbase().Tbl__wbase_prop();
		tbl__prop.Create_tbl();
		tbl__prop.Insert_bgn();
	}
	@Override public void Pid_add(byte[] lang_key, byte[] ttl, byte[] pid) {
		tbl__pid.Insert_cmd_by_batch(lang_key, ttl, pid);
	}
	@Override public void Pid_datatype(byte[] pid, byte[] datatype_bry) {
		Wbase_claim_type claim_type = (Wbase_claim_type)datatype_hash.Get_by_or_fail(datatype_bry);
		tbl__prop.Insert_cmd_by_batch(pid, claim_type.Tid());
	}
	@Override public void Pid_end() {
		tbl__pid.Insert_end();
		tbl__pid.Create_idx();
		tbl__prop.Insert_end();
	}
}
