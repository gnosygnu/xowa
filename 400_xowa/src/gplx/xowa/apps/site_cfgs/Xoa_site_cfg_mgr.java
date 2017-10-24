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
package gplx.xowa.apps.site_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.xowa.bldrs.wms.sites.*;
public class Xoa_site_cfg_mgr {
	private final    Xoa_site_cfg_loader__db loader__db = new Xoa_site_cfg_loader__db();		
	public Xoa_site_cfg_mgr(Xoa_app app) {
		this.itm_ary = new Xoa_site_cfg_itm__base[]
		{ new Xoa_site_cfg_itm__extensiontags()
		, new Xoa_site_cfg_itm__interwikimap()
		};
		this.loader_ary = new Xoa_site_cfg_loader[] 
		{ loader__db
		, Xoa_site_cfg_loader__fsys.new_(app)
		, new Xoa_site_cfg_loader__inet(app.Utl__inet_conn(), app.Utl__json_parser())
		, new Xoa_site_cfg_loader__fallback()
		};
	}
	public Xoa_site_cfg_loader[]		Loader_ary() {return loader_ary;} private final    Xoa_site_cfg_loader[] loader_ary;
	public Xoa_site_cfg_itm__base[]		Itm_ary() {return itm_ary;} private final    Xoa_site_cfg_itm__base[] itm_ary;
	public Hash_adp_bry					Data_hash() {return data_hash;} private final    Hash_adp_bry data_hash = Hash_adp_bry.cs();
	public void Init_loader_bgn(Xow_wiki wiki) {
		data_hash.Clear();	
		int loader_len = loader_ary.length;
		for (int i = 0; i < loader_len; ++i)
			loader_ary[i].Load_csv__bgn(this, wiki);
	}
	public void Load(Xow_wiki wiki) {
		if (wiki.Data__core_mgr() == null) return;	// TEST:
		synchronized (loader__db) {	// THREAD: data_hash; loader_ary
			this.Init_loader_bgn(wiki);
			int len = itm_ary.length;
			for (int i = 0; i < len; ++i) {
				Xoa_site_cfg_itm__base itm = itm_ary[i];
				Load_by_loader(wiki, itm);
			}
		}
	}
	private void Load_by_loader(Xow_wiki wiki, Xoa_site_cfg_itm__base itm) {
		int len = loader_ary.length;
		for (int i = 0; i < len; ++i) {
			Xoa_site_cfg_loader loader = loader_ary[i];
			try {// guard against individual loader failing, particularly inet
				byte[] data = loader.Load_csv(this, wiki, itm);
				if (data != null) {
					if (loader.Tid() != Xoa_site_cfg_loader_.Tid__cfg) loader__db.Save_bry(loader.Tid(), itm.Key_str(), data);
					Xoa_app_.Usr_dlg().Log_many("", "", "site_cfg loaded; wiki=~{0} itm=~{1} tid=~{2}", wiki.Domain_str(), itm.Key_bry(), Xoa_site_cfg_loader_.Get_key(loader.Tid()));
					itm.Exec_csv(wiki, loader.Tid(), data);
					break;
				}
			} catch (Exception e) {Xoa_app_.Usr_dlg().Warn_many("", "", "error while loading site_cfg; wiki=~{0} itm=~{1} err=~{2}", wiki.Domain_str(), itm.Key_bry(), Err_.Message_gplx_log(e));}
		}
	}
}
