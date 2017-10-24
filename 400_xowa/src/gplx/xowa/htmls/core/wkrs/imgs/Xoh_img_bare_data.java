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
package gplx.xowa.htmls.core.wkrs.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.btries.*; import gplx.core.threads.poolables.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.core.wkrs.imgs.atrs.*;
public class Xoh_img_bare_data implements Xoh_data_itm {
	public int						Tid() {return Xoh_hzip_dict_.Tid__img_bare;}
	public int						Src_bgn() {return src_bgn;} private int src_bgn;
	public int						Src_end() {return src_end;} private int src_end;
	public int						Img_tid() {return img_tid;} private int img_tid;
	public int						Dir_bgn() {return dir_bgn;} private int dir_bgn;
	public int						Dir_end() {return dir_end;} private int dir_end;
	public void Clear() {
		this.src_bgn = src_end = img_tid = dir_bgn = dir_end = -1;
	}
	public boolean Init_by_parse(Xoh_hdoc_wkr hdoc_wkr, Xoh_hdoc_ctx hctx, Gfh_tag_rdr tag_rdr, byte[] src, Gfh_tag img_head, Gfh_tag unused) {
		// get src attribute
		this.src_bgn = img_head.Src_bgn(); this.src_end = img_head.Src_end();
		Gfh_atr img_src_atr = img_head.Atrs__get_by_or_empty(Gfh_atr_.Bry__src); if (img_src_atr.Val_dat_missing()) return false;

		// check if it begins with the xowa root dir; EX: src='file:///C:/xowa/...'
		byte[] root_dir_bry = hctx.Fsys__res();	// NOTE: Fsys_res == Fsys_root on all machines except drd; note that hdump builds are not done on drd
		int root_dir_bgn = img_src_atr.Val_bgn();
		int root_dir_end = root_dir_bgn + root_dir_bry.length;
		if (Bry_.Match(src, root_dir_bgn, root_dir_end, root_dir_bry)) {	// begins with XOWA root dir
			byte trie_tid = trie.Match_byte_or(trv, src, root_dir_end, src_end, Byte_.Max_value_127);
			if (trie_tid == Byte_.Max_value_127) return false;
			img_tid = trie_tid;
			dir_bgn = root_dir_bgn;
			dir_end = trv.Pos();
			return true;
		}
		return false;
	}
	public void Init_by_decode(int img_tid, int src_bgn, int src_end, int dir_bgn, int dir_end) {
		this.img_tid = img_tid; this.src_bgn = src_bgn; this.src_end = src_end; this.dir_bgn = dir_bgn; this.dir_end = dir_end;
	}
	public void				Pool__rls	() {pool_mgr.Rls_fast(pool_idx);} private Gfo_poolable_mgr pool_mgr; private int pool_idx;
	public Gfo_poolable_itm	Pool__make	(Gfo_poolable_mgr mgr, int idx, Object[] args) {Xoh_img_bare_data rv = new Xoh_img_bare_data(); rv.pool_mgr = mgr; rv.pool_idx = idx; return rv;}

	public static final byte Img_tid__hiero = 0, Img_tid__imap_btn = 1;
	public static final    byte[] 
	  Url__hiero = Bry_.new_a7("bin/any/xowa/xtns/Wikihiero/img/hiero_")
	, Url__imap  = Bry_.new_a7("bin/any/xowa/xtns/ImageMap/imgs/")
	;
	private final    Btrie_rv trv = new Btrie_rv();
	private static final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs()
	.Add_bry_byte(Url__hiero, Img_tid__hiero)
	.Add_bry_byte(Url__imap	, Img_tid__imap_btn)
	;
}
