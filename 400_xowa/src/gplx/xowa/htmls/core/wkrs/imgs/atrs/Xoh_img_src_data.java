/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.htmls.core.wkrs.imgs.atrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.imgs.*;
import gplx.core.brys.*; import gplx.core.btries.*;
import gplx.langs.htmls.*; import gplx.langs.htmls.docs.*;
import gplx.xowa.wikis.domains.*;
public class Xoh_img_src_data implements Bfr_arg_clearable, Xoh_itm_parser {
	private final Bry_rdr rdr = new Bry_rdr().Dflt_dlm_(Byte_ascii.Slash);
	public void Fail_throws_err_(boolean v) {rdr.Fail_throws_err_(v);}// TEST
	public byte[] Src_bry() {return src_bry;} private byte[] src_bry;
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} private int src_end;
	public int Repo_bgn() {return repo_bgn;} private int repo_bgn;
	public int Repo_end() {return repo_end;} private int repo_end;
	public boolean Repo_is_commons() {return repo_is_commons;} private boolean repo_is_commons;
	public int File_ttl_bgn() {return file_ttl_bgn;} private int file_ttl_bgn;
	public int File_ttl_end() {return file_ttl_end;} private int file_ttl_end;
	public boolean File_ttl_exists() {return file_ttl_end > file_ttl_bgn;}
	public byte[] File_ttl_bry() {return file_ttl_bry;} private byte[] file_ttl_bry;
	public boolean File_is_orig() {return file_is_orig;} private boolean file_is_orig;
	public int File_w() {return file_w;} private int file_w;
	public int File_time() {return file_time;} private int file_time;
	public int File_page() {return file_page;} private int file_page;
	public boolean File_time_exists() {return file_time != -1;}
	public boolean File_page_exists() {return file_page != -1;}
	public void Clear() {
		src_bry = null;
		src_bgn = src_end = repo_bgn = repo_end = file_ttl_bgn = file_ttl_end = file_w = file_time = file_page = -1;
		repo_is_commons = file_is_orig = false;
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
		file_w = file_time = file_page = -1;
		rdr.Init_by_wkr(err_wkr, "img.src.xowa", src_bgn, src_end).Fail_throws_err_(Bool_.N);
		repo_bgn = rdr.Find_fwd_rr(Bry__file);						// skip past /file/; EX: "file:///J:/xowa/file/commons.wikimedia.org/"
		if (repo_bgn == -1) return false;
		rdr.Fail_throws_err_(Bool_.Y);
		repo_end = rdr.Find_fwd_lr();
		repo_is_commons = Bry_.Match(rdr.Src(), repo_bgn, repo_end, Xow_domain_itm_.Bry__commons);
		if (!repo_is_commons) {
			if (!Bry_.Match(rdr.Src(), repo_bgn, repo_end, domain_bry)) return false; // rdr.Fail("repo must be commons or self", "repo", Bry_.Mid(rdr.Src(), repo_bgn, repo_end));
//				if (!Bry_.Match(rdr.Src(), repo_bgn, repo_end, domain_bry)) rdr.Fail("repo must be commons or self", "repo", Bry_.Mid(rdr.Src(), repo_bgn, repo_end));
		}
		file_is_orig = rdr.Chk(trie) == Tid__orig;					// check if 'orig/' or 'thumb/'
		file_ttl_bgn = Skip_md5();									// skip md5; EX: "0/1/2/3/"
		if (file_is_orig)
			file_ttl_end = rdr.Src_end();		
		else {
			file_ttl_end = rdr.Find_fwd_lr();
			file_w = rdr.Read_int_to(Byte_ascii.Ltr_p);				// EX: "220px"
			rdr.Chk(Byte_ascii.Ltr_x);
			if		(rdr.Is(Byte_ascii.At))
				file_time = rdr.Read_int_to(Byte_ascii.Dot);		// EX: "220px@5.png"
			else if	(rdr.Is(Byte_ascii.Dash))
				file_page = rdr.Read_int_to(Byte_ascii.Dot);		// EX: "220px-5.png"
		}
		file_ttl_bry = Bry_.Mid(src_bry, file_ttl_bgn, file_ttl_end); 
		return true;
	}
	public void Init_by_decode(byte[] file_ttl_bry, byte[] src) {
		this.file_ttl_bry = file_ttl_bry;
		this.src_bry = src; this.src_bgn = 0; this.src_end = src.length;
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
	private static final byte[] Bry__file = Bry_.new_a7("/file/"), Bry__orig = Bry_.new_a7("orig/"), Bry__thumb = Bry_.new_a7("thumb/");
	private static final byte Tid__orig = 1, Tid__thumb = 2;
	private static final Btrie_slim_mgr trie = Btrie_slim_mgr.cs().Add_bry_byte(Bry__orig, Tid__orig).Add_bry_byte(Bry__thumb, Tid__thumb);
}
