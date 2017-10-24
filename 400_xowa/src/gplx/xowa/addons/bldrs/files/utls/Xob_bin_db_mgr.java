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
package gplx.xowa.addons.bldrs.files.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.core.primitives.*; import gplx.core.ios.*;
import gplx.fsdb.meta.*;
public class Xob_bin_db_mgr {
	private final    int[] ns_ids; private final    int ns_ids_len;
	private final    Ordered_hash nth_hash = Ordered_hash_.New(); private final    Int_obj_ref tier_key = Int_obj_ref.New_neg1();
	public Xob_bin_db_mgr(int[] ns_ids) {
		this.ns_ids = ns_ids; this.ns_ids_len = ns_ids.length;
	}
	public boolean Schema_is_1() {return schema_is_1;} private boolean schema_is_1;
	public void Init_by_mnt_mgr(Fsm_mnt_mgr trg_mnt_mgr) {
		Fsm_mnt_itm trg_mnt_itm = trg_mnt_mgr.Mnts__get_main(); 
		this.schema_is_1 = trg_mnt_itm.Db_mgr().File__schema_is_1();
		Fsm_bin_mgr bin_db_mgr = trg_mnt_itm.Bin_mgr();
		int len = ns_ids_len;
		for (int i = 0; i < len; ++i) {	// iterate ns_ids and add default nth
			int ns_id = ns_ids[i];
			Xob_bin_db_itm nth = new Xob_bin_db_itm(-1, null, ns_id, 0);
			nth_hash.Add(Int_obj_ref.New(ns_ids[i]), nth);
		}
		len = bin_db_mgr.Dbs__len();
		for (int i = 0; i < len; ++i) {	// iterate bin_dbs to find max pt_id for each ns
			Fsm_bin_fil fil = bin_db_mgr.Dbs__get_at(i);
			Xob_bin_db_itm itm = schema_is_1 ? Xob_bin_db_itm.new_v1(fil) : Xob_bin_db_itm.new_v2(fil);
			int ns_id = itm.Ns_id();
			Xob_bin_db_itm nth = (Xob_bin_db_itm)nth_hash.Get_by(tier_key.Val_(ns_id));
			if (	nth != null					// occurs when existing fsdb_dbb has "file-ns.014-db.001", but 14 no longer specified in fsdb_make; DATE:2016-09-23
				&&	itm.Pt_id() > nth.Pt_id())	// update max pt_id
				nth.Set(itm.Id(), itm.Pt_id(), itm.Db_url()); // note that ns_id is same
		}
		len = nth_hash.Count();
		for (int i = 0; i < len; ++i) {	// iterated tiers to calculate max_size
			Xob_bin_db_itm nth = (Xob_bin_db_itm)nth_hash.Get_at(i);
			if (nth.Id() == -1) continue;	// ignore default nth
			IoItmFil nth_itm = Io_mgr.Instance.QueryFil(nth.Db_url());
			nth.Db_len_(nth_itm.Size());
		}
	}
	public boolean Tier_id_is_last(int tier_id) {return tier_id >= ns_ids_len;}	// assumes tier_id is 0 based; EX: 0,1,2 for 
	public int Get_ns_id(int tier_id) {return ns_ids[tier_id];}
	public int Increment_pt_id(Xob_bin_db_itm itm) {
		itm.Set(-1, itm.Pt_id() + 1, null);
		itm.Db_len_(0);
		return itm.Pt_id();
	}
	public String Gen_name(String domain_str, int ns_id, int pt_id) {
		return schema_is_1 ? Xob_bin_db_itm.Gen_name_v1(pt_id) : Xob_bin_db_itm.Gen_name_v2(domain_str, ns_id, pt_id);
	}
	public Xob_bin_db_itm Get_nth_by_tier(int tier_id) {
		if (schema_is_1) return (Xob_bin_db_itm)nth_hash.Get_by(tier_key.Val_(0)); // v1 is always in ns_0
		if (tier_id >= ns_ids_len) throw Err_.new_wo_type("tier out of range", "tier_id", tier_id, "len", ns_ids_len);
		int ns_id = ns_ids[tier_id];
		return (Xob_bin_db_itm)nth_hash.Get_by(tier_key.Val_(ns_id));
	}
}
