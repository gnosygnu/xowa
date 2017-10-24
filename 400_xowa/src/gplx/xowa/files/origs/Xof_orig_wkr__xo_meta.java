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
package gplx.xowa.files.origs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.flds.*;
import gplx.dbs.*; import gplx.xowa.files.fsdb.*;
import gplx.xowa.wikis.tdbs.metas.*;
public class Xof_orig_wkr__xo_meta implements Xof_orig_wkr {
	private final    Io_url wiki_meta_dir; private final    byte dir_spr_byte;  private final    Bry_bfr url_bfr = Bry_bfr_.New_w_size(255);
	private final    Gfo_fld_rdr meta_rdr = Gfo_fld_rdr.xowa_(); private final    Xof_meta_thumb_parser parser = new Xof_meta_thumb_parser();
	public Xof_orig_wkr__xo_meta(Io_url wiki_meta_dir) {this.wiki_meta_dir = wiki_meta_dir; this.dir_spr_byte = wiki_meta_dir.Info().DirSpr_byte();}
	public byte				Tid() {return Xof_orig_wkr_.Tid_xowa_meta;}
	public void				Find_by_list(Ordered_hash rv, List_adp itms) {Xof_orig_wkr_.Find_by_list(this, rv, itms);}
	public Xof_orig_itm		Find_as_itm(byte[] ttl, int list_idx, int list_len) {
		byte[] md5 = Xof_file_wkr_.Md5(ttl);
		url_bfr.Add(wiki_meta_dir.RawBry())					// /xowa/file/#meta/simple.wikipedia.org/
			.Add_byte(md5[0]).Add_byte(dir_spr_byte)		// 0/
			.Add_byte(md5[1]).Add_byte(dir_spr_byte)		// 6/
			.Add_mid(md5, 0, 3).Add_str_a7(".csv")			// 061.csv
			;
		Io_url meta_url = Io_url_.new_fil_(url_bfr.To_str_and_clear());
		byte[] meta_src = Io_mgr.Instance.LoadFilBry(meta_url); if (meta_src.length == 0) return Xof_orig_itm.Null;
		meta_rdr.Ini(meta_src, 0);
		Xof_meta_fil meta_fil = new Xof_meta_fil(null, md5);	// NOTE: need to register file before loading it; defect wherein 2 files with same hash prefix would skip one b/c Loaded file was not registered; EX.WS: en.wikiquote.org/The Hitchhiker's Guide to the Galaxy; NMMP_dolphin_with_locator.jpeg, da6f95736ed249f371f30bf5f1205fbd; Hoags_object.jpg, daed4a54e48e4266bd2f2763b7c4018c
		meta_fil.Load(meta_rdr, parser);
		Xof_meta_itm meta_itm = meta_fil.Get_or_null(ttl); if (meta_itm == null) return Xof_orig_itm.Null;
		Xof_orig_itm rv = new Xof_orig_itm((byte)meta_itm.Vrtl_repo(), ttl, Xof_ext_.new_by_ttl_(ttl).Id(), meta_itm.Orig_w(), meta_itm.Orig_h(), meta_itm.Ptr_ttl());
		rv.Insert_new_y_();
		return rv;
	}
	public boolean				Add_orig(byte repo, byte[] page, int ext_id, int w, int h, byte[] redirect) {
		return false;
	}
	public void				Db_txn_save() {}
	public void				Db_rls() {}
}
