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
package gplx.xowa.files.bins; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.ios.*; import gplx.core.ios.streams.*;
import gplx.fsdb.meta.*; import gplx.xowa.files.fsdb.*;
public class Xof_bin_skip_mgr {
	private Xof_bin_skip_wkr[] wkrs = new Xof_bin_skip_wkr[0]; private int wkrs_len;
	public Xof_bin_skip_mgr(Fsm_cfg_mgr cfg_mgr, String[] wkr_keys) {
		List_adp list = List_adp_.New();
		for (int i = 0; i < wkrs_len; ++i) {
			Xof_bin_skip_wkr wkr = New_wkr(cfg_mgr, wkr_keys[i]);
			if (wkr != null) list.Add(wkr);
		}
		this.wkrs = (Xof_bin_skip_wkr[])list.To_ary_and_clear(Xof_bin_skip_wkr.class);
		this.wkrs_len = wkrs.length;
	}
	public boolean Skip(Xof_fsdb_itm fsdb, Io_stream_rdr src_rdr) {
		for (int i = 0; i < wkrs_len; ++i) {
			if (wkrs[i].Skip_exec(fsdb, src_rdr)) return true;
		}
		return false;
	}
	private Xof_bin_skip_wkr New_wkr(Fsm_cfg_mgr cfg_mgr, String key) {
		Xof_bin_skip_wkr rv = null;
		if		(String_.Eq(key, Xof_bin_skip_wkr_.Key__page_gt_1))		rv = Xof_bin_skip_wkr__page_gt_1.Instance;
		else if	(String_.Eq(key, Xof_bin_skip_wkr_.Key__small_size))	rv = Xof_bin_skip_wkr__small_size.Instance;
		else															throw Err_.new_unhandled(key);
		if (!rv.Skip_init(cfg_mgr)) return null;
		return rv;
	}
	public void Skip_term(Fsm_cfg_mgr cfg_mgr) {
		for (int i = 0; i < wkrs_len; ++i)
			wkrs[i].Skip_term(cfg_mgr);
	}
}
interface Xof_bin_skip_wkr {
	String Key();
	boolean Skip_init(Fsm_cfg_mgr cfg_mgr);
	boolean Skip_exec(Xof_fsdb_itm fsdb, Io_stream_rdr src_rdr);
	void Skip_term(Fsm_cfg_mgr cfg_mgr);
}
class Xof_bin_skip_wkr_ {
	public static final String Key__page_gt_1 = "page_gt_1", Key__small_size = "small_size";
}
class Xof_bin_skip_wkr__page_gt_1 implements Xof_bin_skip_wkr {	// prior to v2.4.3; lnkis with page > 1 was mistakenly bringing down page 1; EX: [[A.pdf|page=5]] -> page1; DATE:2015-04-21
	public String Key() {return Xof_bin_skip_wkr_.Key__page_gt_1;}
	public boolean Skip_init(Fsm_cfg_mgr cfg_mgr) {return !cfg_mgr.Patch__page_gt_1();}
	public boolean Skip_exec(Xof_fsdb_itm fsdb, Io_stream_rdr src_rdr) {
		boolean rv = fsdb.Lnki_page() > 1;
		if (rv)
			Xoa_app_.Usr_dlg().Note_many("", "", "src_bin_mgr:skip page gt 1: file=~{0} width=~{1} page=~{2}", fsdb.Orig_ttl(), fsdb.Html_w(), fsdb.Lnki_page());
		return rv;
	}
	public void Skip_term(Fsm_cfg_mgr cfg_mgr) {
		cfg_mgr.Patch__save(Fsm_cfg_mgr.Key_patch__page_gt_1);
	}
        public static final    Xof_bin_skip_wkr__page_gt_1 Instance = new Xof_bin_skip_wkr__page_gt_1(); Xof_bin_skip_wkr__page_gt_1() {}
}
class Xof_bin_skip_wkr__small_size implements Xof_bin_skip_wkr {// downloads can randomly be broken; assume that anything with a small size is broken and redownload again; DATE:2015-04-21
	public String Key() {return Xof_bin_skip_wkr_.Key__small_size;}
	public boolean Skip_init(Fsm_cfg_mgr cfg_mgr) {return true;}
	public boolean Skip_exec(Xof_fsdb_itm fsdb, Io_stream_rdr src_rdr) {
		boolean rv = 
			src_rdr.Len() < 500									// file is small (< 500 bytes)
		&&	fsdb.Html_w() > 50									// only apply to images larger than 50 px (arbitrarily chosen); needed to ignore 1x1 images as well as icon-sized images
		&&	Xof_ext_.Id_is_image_wo_svg(fsdb.Orig_ext().Id())	// only consider images; needed b/c of file_w check; note:ignore svg which can be small
		;
		if (rv)
			Xoa_app_.Usr_dlg().Note_many("", "", "src_bin_mgr:skip small file: file=~{0} width=~{1} ext=~{2} len=~{3}", fsdb.Orig_ttl(), fsdb.Lnki_w(), fsdb.Orig_ext(), src_rdr.Len());
		return rv;
	}
	public void Skip_term(Fsm_cfg_mgr cfg_mgr) {}
        public static final    Xof_bin_skip_wkr__small_size Instance = new Xof_bin_skip_wkr__small_size(); Xof_bin_skip_wkr__small_size() {}
}
