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
package gplx.xowa.files.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.xowa.wikis.data.tbls.*;
class Xofc_cfg_mgr {
	private Db_cfg_tbl tbl;
	public int Next_id() {return next_id++;} public void Next_id_(int v) {next_id = v;} private int next_id;		
	public long Cache_len() {return cache_len;} public void Cache_len_(long v) {cache_len = v;} private long cache_len = 0;
	public void Cache_len_add(long v) {cache_len += v;}
	public long Cache_min() {return cache_min;} public void Cache_min_(long v) {cache_min = v;} private long cache_min = Io_mgr.Len_mb * 75;
	public long Cache_max() {return cache_max;} public void Cache_max_(long v) {cache_max = v;} private long cache_max = Io_mgr.Len_mb * 100;
	public void Conn_(Db_conn v, boolean created, boolean schema_is_1) {
		tbl = new Db_cfg_tbl(v, schema_is_1 ? gplx.xowa.wikis.data.Xowd_cfg_tbl_.Tbl_name : "file_cache_cfg");
		if (created) {
			tbl.Create_tbl();
			tbl.Insert_int(Cfg_grp, Cfg_key__next_id, 1);
			tbl.Insert_int(Cfg_grp, Cfg_key__cache_len, 0);
			tbl.Insert_long(Cfg_grp, Cfg_key__cache_min, cache_min);
			tbl.Insert_long(Cfg_grp, Cfg_key__cache_max, cache_max);
		}
		else {
			next_id = tbl.Select_int(Cfg_grp, Cfg_key__next_id);
			cache_len = tbl.Select_int(Cfg_grp, Cfg_key__cache_len);
			cache_max = tbl.Select_int(Cfg_grp, Cfg_key__cache_max);
		}
	}
	public void Save_all() {
		tbl.Update_int(Cfg_grp, Cfg_key__next_id, next_id);
		tbl.Update_long(Cfg_grp, Cfg_key__cache_len, cache_len);
		tbl.Update_long(Cfg_grp, Cfg_key__cache_min, cache_min);
		tbl.Update_long(Cfg_grp, Cfg_key__cache_max, cache_max);
	}
	public void Cleanup() {}
	private static final String Cfg_grp = "fsdb.cache", Cfg_key__next_id = "next_id", Cfg_key__cache_min = "cache_min", Cfg_key__cache_max = "cache_max", Cfg_key__cache_len = "cache_len";
}
