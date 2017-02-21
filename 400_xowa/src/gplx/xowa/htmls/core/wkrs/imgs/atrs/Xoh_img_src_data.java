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
package gplx.xowa.htmls.core.wkrs.imgs.atrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
import gplx.core.brys.*; import gplx.core.btries.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
import gplx.xowa.wikis.domains.*;
public class Xoh_img_src_data implements Bfr_arg_clearable, Xoh_itm_parser {
	private final    Bry_rdr rdr = new Bry_rdr().Dflt_dlm_(Byte_ascii.Slash);
	private boolean fail_throws_err;
	public byte[] Src_bry() {return src_bry;} private byte[] src_bry;
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public int Repo_bgn() {return repo_bgn;} private int repo_bgn;
	public int Repo_end() {return repo_end;} private int repo_end;
	public boolean Repo_is_commons() {return repo_is_commons;} private boolean repo_is_commons;
	public byte Repo_tid() {return repo_tid;} private byte repo_tid;
	public int File_ttl_bgn() {return file_ttl_bgn;} private int file_ttl_bgn;
	public int File_ttl_end() {return file_ttl_end;} private int file_ttl_end;
	public boolean File_ttl_exists() {return file_ttl_end > file_ttl_bgn;}
	public byte[] File_ttl_bry() {return file_ttl_bry;} private byte[] file_ttl_bry;
	public boolean File_is_orig() {return file_is_orig;} private boolean file_is_orig;
	public int File_w() {return file_w;} private int file_w;
	public double File_time() {return file_time;} private double file_time;
	public int File_page() {return file_page;} private int file_page;
	public boolean File_time_exists() {return file_time != -1;}
	public boolean File_page_exists() {return file_page != -1;}
	public void Clear() {
		src_bry = null;
		src_bgn = src_end = repo_bgn = repo_end = file_ttl_bgn = file_ttl_end = file_w = file_page = -1;
		file_time = -1;
		repo_is_commons = file_is_orig = false;
		repo_tid = Xof_repo_tid_.Tid__null;
		file_ttl_bry = null;
	}
	public boolean Parse(Bry_err_wkr err_wkr, Xoh_hdoc_ctx hctx, byte[] domain_bry, Gfh_tag tag) {
		this.Clear();
		Gfh_atr atr = tag.Atrs__get_by_or_empty(Gfh_atr_.Bry__src);
		return Parse(err_wkr, hctx, domain_bry, atr.Val_bgn(), atr.Val_end());
	}
	public boolean Parse(Bry_err_wkr err_wkr, Xoh_hdoc_ctx hctx, byte[] domain_bry, int src_bgn, int src_end) { // EX: src="file:///C:/xowa/file/commons.wikimedia.org/thumb/7/0/1/2/A.png/220px.png"
		this.Clear();
		this.src_bry = err_wkr.Src();
		this.src_bgn = src_bgn; this.src_end = src_end;
		if (src_end == src_bgn) return true;						// empty src; just return true;

		// get repo_bgn; note that some <img> may be hiero / enlarge / magnify and should exit
		rdr.Init_by_wkr(err_wkr, "img.src.xowa", src_bgn, src_end).Fail_throws_err_(fail_throws_err);
		repo_bgn = rdr.Find_fwd_rr_or(Bry__file, -1);
		if (repo_bgn == -1) {
			repo_bgn = rdr.Find_fwd_rr(Bry__math);
			if (repo_bgn == Bry_find_.Not_found) return false;
            this.file_ttl_bry = Bry_.Mid(rdr.Src(), repo_bgn, src_end);
			this.repo_is_commons = true;
			this.repo_tid = Xof_repo_tid_.Tid__math;
			this.file_is_orig = true;
			return true;
		}

		rdr.Fail_throws_err_(Bool_.Y);

		// get repo
		repo_end = rdr.Find_fwd_lr();
		repo_is_commons = Bry_.Match(rdr.Src(), repo_bgn, repo_end, Xow_domain_itm_.Bry__commons);
		repo_tid = repo_is_commons ? Xof_repo_tid_.Tid__remote : Xof_repo_tid_.Tid__local;
		if (!repo_is_commons) {
			if (!Bry_.Match(rdr.Src(), repo_bgn, repo_end, domain_bry)) rdr.Err_wkr().Fail("repo must be commons or self", "repo", Bry_.Mid(rdr.Src(), repo_bgn, repo_end));
		}

		// get orig / thumb; md5; file_ttl
		file_is_orig = rdr.Chk(trie) == Tid__orig;					// check if 'orig/' or 'thumb/'
		file_ttl_bgn = Skip_md5();									// skip md5; EX: "0/1/2/3/"
		if (file_is_orig)
			file_ttl_end = rdr.Src_end();		
		else
			file_ttl_end = rdr.Find_fwd_lr();
		file_ttl_bry = Bry_.Mid(src_bry, file_ttl_bgn, file_ttl_end); 

		// get file_w; file_page; file_time
		if (!file_is_orig) {
			file_w = rdr.Read_int_to(Byte_ascii.Ltr_p);					// EX: "220px"
			rdr.Chk(Byte_ascii.Ltr_x);
			if		(rdr.Is(Byte_ascii.Dash)) {
				Xof_ext ext = Xof_ext_.new_by_ttl_(file_ttl_bry);
				if (ext.Id_supports_page())
					file_page = rdr.Read_int_to(Byte_ascii.Dot);		// EX: "220px-5.png"
				else {
					int time_bgn = rdr.Pos();
					int time_end = rdr.Find_fwd_lr(ext.Ext_view()) - 1;	// -1 to position b/c ext doesn't have "."; 
					file_time = Bry_.To_double_or(src_bry, time_bgn, time_end, -1);
				}
			}
		}
		return true;
	}
	public void Init_by_decode(byte[] src, boolean file_is_orig, byte[] file_ttl_bry, int file_w, double file_time, int file_page) {
		this.src_bry = src; this.src_bgn = 0; this.src_end = src.length;
		this.file_is_orig = file_is_orig;
		this.file_ttl_bry = file_ttl_bry; this.file_w = file_w; this.file_time = file_time; this.file_page = file_page;
	}
	public void Bfr_arg__clear() {this.Clear();}
	public boolean Bfr_arg__missing() {return src_bry == null;}
	public void Bfr_arg__add(Bry_bfr bfr) {bfr.Add_mid(src_bry, src_bgn, src_end);}
	private int Skip_md5() {
		byte[] src = rdr.Src();
		int pos = rdr.Pos();
		while (true) {
			if (	pos < src_end						// no more slashes; shouldn't happen; EX: "7/0/"
				&&	src[pos + 1] == Byte_ascii.Slash) {	// pos is slash; EX: "7/0/"
				pos += 2;
				continue;
			}
			else {										// pos is not slash; title done
				break;
			}
		}
		return rdr.Move_to(pos);
	}
	public void Fail_throws_err_(boolean v) {// TEST
		this.fail_throws_err = v;
		rdr.Fail_throws_err_(v);
	}

	private static final    byte[] Bry__file = Bry_.new_a7("/file/"), Bry__math = Bry_.new_a7("/math/"), Bry__orig = Bry_.new_a7("orig/"), Bry__thumb = Bry_.new_a7("thumb/");
	private static final byte Tid__orig = 1, Tid__thumb = 2;
	private static final    Btrie_slim_mgr trie = Btrie_slim_mgr.cs().Add_bry_byte(Bry__orig, Tid__orig).Add_bry_byte(Bry__thumb, Tid__thumb);
}
