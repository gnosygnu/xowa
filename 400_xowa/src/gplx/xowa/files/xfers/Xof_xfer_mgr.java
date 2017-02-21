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
package gplx.xowa.files.xfers; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.primitives.*; import gplx.gfui.*;
import gplx.xowa.files.*; import gplx.xowa.files.repos.*; import gplx.xowa.files.exts.*; import gplx.xowa.files.downloads.*; import gplx.xowa.files.imgs.*;
import gplx.xowa.bldrs.wms.*; import gplx.xowa.apps.wms.apis.*; import gplx.xowa.apps.wms.apis.origs.*;
import gplx.xowa.wikis.tdbs.metas.*;
public class Xof_xfer_mgr {
	public Xof_xfer_mgr(Xof_file_mgr file_mgr, Xowmf_mgr wmf_mgr) {this.file_mgr = file_mgr; this.wmf_mgr = wmf_mgr;} private final    Xof_file_mgr file_mgr; private final    Xowmf_mgr wmf_mgr;
	public Xof_xfer_rslt Rslt() {return rslt;} private Xof_xfer_rslt rslt = new Xof_xfer_rslt();
	public boolean Force_orig() {return force_orig;} public Xof_xfer_mgr Force_orig_(boolean v) {force_orig = v; return this;} private boolean force_orig;
	public Xof_xfer_mgr Force_orig_y_() {return Force_orig_(Bool_.Y);} public Xof_xfer_mgr Force_orig_n_() {return Force_orig_(Bool_.N);}
	public void Atrs_by_itm(Xof_xfer_itm xfer_itm, Xof_repo_itm src_repo, Xof_repo_itm trg_repo) {
		this.xfer_itm = xfer_itm;
		this.lnki_w = xfer_itm.Lnki_w(); this.lnki_h = xfer_itm.Lnki_h(); this.lnki_thumbable = !xfer_itm.File_is_orig(); this.lnki_thumbtime = xfer_itm.Lnki_time(); this.lnki_page = xfer_itm.Lnki_page();
		this.lnki_type = xfer_itm.Lnki_type();
		lnki_upright = xfer_itm.Lnki_upright();
		this.orig_ttl = xfer_itm.Orig_ttl(); this.orig_ttl_md5 = xfer_itm.Orig_ttl_md5(); this.orig_ext = xfer_itm.Orig_ext();
		this.orig_file_len = xfer_itm.Orig_file_len();
		this.src_repo = src_repo; src_repo_is_wmf = src_repo.Wmf_fsys();
		this.trg_repo = trg_repo;
		this.meta_itm = xfer_itm.Dbmeta_itm();
		ext_rule = src_repo.Ext_rules().Get_or_null(orig_ext.Ext());
		orig_w = 0; orig_h = 0; file_w = 0; file_h = 0;
	}	private byte lnki_type;
	private Xof_xfer_itm xfer_itm; private double lnki_thumbtime = Xof_lnki_time.Null; private boolean lnki_thumbable; private int lnki_w, lnki_h, file_w, file_h; private double lnki_upright;
	private Xof_ext orig_ext; private Xof_rule_itm ext_rule; private Xof_repo_itm src_repo, trg_repo; private boolean src_repo_is_wmf; private byte[] orig_ttl, orig_ttl_md5; private int orig_w, orig_h; private long orig_file_len; 
	private int lnki_page = Xof_lnki_page.Null;
	public Xof_meta_itm Dbmeta_itm() {return meta_itm;} private Xof_meta_itm meta_itm;
	public boolean Download_allowed_by_ext() {return orig_file_len < ext_rule.Make_max();}
	public Xof_xfer_mgr Check_file_exists_before_xfer_n_() {check_file_exists_before_xfer = false; return this;} private boolean check_file_exists_before_xfer = true;
	public boolean Make_file(Xowe_wiki wiki) {
		rslt.Clear(); this.wiki = wiki;
		if		(	src_repo.Wmf_api()												// make sure wmf_api enabled
					&&	(	(	orig_ext.Id() == Xof_ext_.Id_ogg							// file is ogg; could be audio; DATE:2013-08-03
							&&	!meta_itm.Thumbs_indicates_oga())					// check to make sure it hasn't been called before
						||	xfer_itm.Html_elem_tid() == Xof_html_elem.Tid_imap		// file is imap
						)
				)
			Call_wmf_api();
		if 		(orig_ext.Id_is_thumbable_img())								Make_img();
		else if (orig_ext.Id_is_video()&& !meta_itm.Thumbs_indicates_oga())	Make_vid();
		else															Make_other();
		return rslt.Pass();
	}	private Xowe_wiki wiki; Xoapi_orig_rslts rslts = new Xoapi_orig_rslts();
	private boolean Make_img() {
		String src_str; Io_url trg_url;
		// BLOCK: thumb
		if (lnki_thumbable) {									// lnki is thumb with known width >>> try to do thumb
			if (lnki_w < 1 && lnki_h < 1) {						// NOTE: only give default thumb if both w and h are < 1; if h > 0, then skip; EX:Paris;[[File:IMA-Ile-St-Louis.jpg|thumb|x220]]
				// wiki.File_mgr().Init_file_mgr_by_load(wiki);	// commented out; causes tests to fail and (a) should never have been needed by v0; (b) v0 will not be run any more; DATE:2015-05-20
				lnki_w = Xof_img_size.Upright_calc(wiki.File_mgr().Patch_upright(), lnki_upright, lnki_w, lnki_w, lnki_h, lnki_type);
				if (lnki_w < 1)
					lnki_w = wiki.Html_mgr().Img_thumb_width();		// NOTE: used to be src_repo.Thumb_w()
			}
			
			Make_img_qry();	// NOTE: used to be "if (!Make_img_qry()) return false;" however some images are present, but don't return <iinfo> node; for now, try to proceed; DATE:2013-02-03
			if (Make_img_thumb()) return true;
		}

		// BLOCK: orig; get orig for convert; note that Img_download will not download file again if src exists
		src_str = this.Src_url(src_repo, Xof_img_mode_.Tid__orig, Xof_img_size.Size__neg1);
		trg_url = this.Trg_url(trg_repo, Xof_img_mode_.Tid__orig, Xof_img_size.Size__neg1);
		if (!Img_download(src_str, trg_url, false)) return false;
		trg_url = rslt.Trg();

		// BLOCK: convert thumb
		if (lnki_thumbable || orig_ext.Id_is_svg() || orig_ext.Id_is_djvu()) {
			rslt.Clear();	// clear error from failed thumb
			Io_url src_url = trg_url;
			if (orig_ext.Id_is_djvu()) {	// NOTE: this block converts djvu -> tiff b/c vanilla imageMagick cannot do djvu -> jpeg
				trg_url = this.Trg_url(trg_repo, Xof_img_mode_.Tid__thumb, lnki_w).GenNewExt(".tiff");	// NOTE: manually change orig_ext to tiff; note that djvu has view type of jpeg
				wiki.Appe().File_mgr().Img_mgr().Wkr_convert_djvu_to_tiff().Exec(src_url, trg_url);
				if (!Cmd_query_size(trg_url)) return false;
//						meta_itm.Update_orig_size(file_w, file_h);	// NOTE that thumb size is always orig size
				src_url = trg_url;
			}
			boolean limit = !orig_ext.Id_is_svg();	// do not limit if svg
			Xof_xfer_itm_.Calc_xfer_size(calc_size, xfer_itm.Lnki_type(), wiki.Html_mgr().Img_thumb_width(), file_w, file_h, lnki_w, lnki_h, lnki_thumbable, xfer_itm.Lnki_upright(), limit);	// NOTE: always recalc w/h; needed for (a) when width < 1 and (b) when w/h are wrong; xfer=160,160, lnki=65,50, actl should be 50,50; PAGE:en.w:[[Image:Gnome-mime-audio-openclipart.svg|65x50px|center|link=|alt=]]
			lnki_w = calc_size.Val_0(); lnki_h = calc_size.Val_1();
			trg_url = this.Trg_url(trg_repo, Xof_img_mode_.Tid__thumb, lnki_w);
			if (!Img_convert(src_url, trg_url)) return false;	// convert failed; exit
			if (orig_ext.Id_is_djvu()) Io_mgr.Instance.DeleteFil(src_url);	// convert passed; if djvu, delete intermediary .tiff file;
		}
		return true;
	}	Int_2_ref calc_size = new Int_2_ref();
	boolean Call_wmf_api() {
		Xof_download_wkr download_wkr = wiki.App().Wmf_mgr().Download_wkr(); Xowe_repo_mgr repo_mgr = wiki.File_mgr().Repo_mgr();
		boolean found = wiki.App().Wmf_mgr().Api_mgr().Api_orig().Api_query_size(rslts, download_wkr, repo_mgr, orig_ttl, lnki_w, lnki_h);
		if (found) {
			if (rslts.Orig_wiki() != null) {
				src_repo = wiki.Appe().File_mgr().Repo_mgr().Get_by_wmf_fsys(rslts.Orig_wiki());
				trg_repo = wiki.Appe().File_mgr().Repo_mgr().Get_by_primary(rslts.Orig_wiki());
				if (Bry_.Eq(rslts.Orig_wiki(), wiki.Domain_bry()))	// wmf returned same wiki as current
					xfer_itm.Orig_repo_id_(Xof_meta_itm.Repo_same);	// set repo to "same"
				else {												// wmf returned other wiki (which is 99% likely to be commons)
					Xof_repo_pair trg_repo_pair = wiki.File_mgr().Repo_mgr().Repos_get_by_wiki(rslts.Orig_wiki());	// need to do this b/c commons is not always first; see wikinews; DATE:2013-12-04					
					int trg_repo_idx = trg_repo_pair == null ? 0 : (int)trg_repo_pair.Id();	// 0=commons
					xfer_itm.Orig_repo_id_(trg_repo_idx);
				}
				if (!Bry_.Eq(rslts.Orig_page(), orig_ttl)) {
					orig_ttl = rslts.Orig_page();
					orig_ttl_md5 = Xof_file_wkr_.Md5(orig_ttl);
					meta_itm.Ptr_ttl_(orig_ttl);
				}
				meta_itm.Vrtl_repo_(xfer_itm.Orig_repo_id());
				if (orig_ext.Id_is_ogg() && rslts.Orig_w() == 0 && rslts.Orig_h() == 0)	// file is ogg, but thumb has size of 0,0; assume audio and mark as oga
					meta_itm.Update_thumb_oga_();
			}
			meta_itm.Load_orig_(rslts.Orig_w(), rslts.Orig_h());
			return true;
		}
		else {
			meta_itm.Orig_exists_(Xof_meta_itm.Exists_n);	// not found; mark no
			meta_itm.Vrtl_repo_(Xof_meta_itm.Repo_missing);	// not found; mark missing
			rslt.Fail("api failed");
			return false;
		}
	}
	boolean Make_img_qry() {
		if (meta_itm.Orig_w() < 1) {
			if (src_repo.Wmf_api()) {	// api_enabled
				boolean wmf_api_found = Call_wmf_api();
				if (!wmf_api_found) return false;	// not found in wmf_api; exit now
			}
			else if (src_repo.Tarball()) {
				String src_str = this.Src_url(src_repo, Xof_img_mode_.Tid__orig, Xof_img_size.Size__neg1);
				meta_itm.Orig_exists_(Xof_meta_itm.Exists_unknown);	// mark exists unknown; note need to assertively mark unknown b/c it may have been marked n in previous pass through multiple repos; DATE:20121227
				meta_itm.Vrtl_repo_(Xof_meta_itm.Repo_unknown);		// mark repo unknown;
				if (!Cmd_query_size(Io_url_.new_fil_(src_str))) {
					meta_itm.Orig_exists_(Xof_meta_itm.Exists_n);	// not found; mark no
					meta_itm.Vrtl_repo_(Xof_meta_itm.Repo_missing);	// not found; mark missing
					rslt.Fail("img not found");
					return false;
				}
				meta_itm.Vrtl_repo_(xfer_itm.Orig_repo_id());
				meta_itm.Load_orig_(file_w, file_h);
			}
		}
		return true;
	}
	private boolean Make_img_thumb(){
		String src_str; Io_url trg_url;
		boolean limit = !orig_ext.Id_is_svg();	// do not limit if svg
		if (lnki_w > 0) {								// if width is -1, don't bother (wmf only has > 0 width); PAGE:en.w:Paris;[[File:IMA-Ile-St-Louis.jpg|thumb|x220]]   
			for (int i = 0; i < 2; i++) {
				Xof_xfer_itm_.Calc_xfer_size(calc_size, xfer_itm.Lnki_type(), wiki.Html_mgr().Img_thumb_width(), meta_itm.Orig_w(), meta_itm.Orig_h(), lnki_w, lnki_h, lnki_thumbable, lnki_upright, limit);
				lnki_w = calc_size.Val_0(); 
				if (lnki_h != -1) lnki_h = calc_size.Val_1(); // NOTE: if -1 (no height specified) do not set height; EX:Tokage_2011-07-15.jpg; DATE:2013-06-03

				src_str = src_repo.Tarball() ? this.Src_url(src_repo, Xof_img_mode_.Tid__orig, Xof_img_size.Size_null) : this.Src_url(src_repo, Xof_img_mode_.Tid__thumb, lnki_w);
				trg_url = this.Trg_url(trg_repo, Xof_img_mode_.Tid__thumb, lnki_w);
				if (Make_img_exec(src_str, trg_url)) {		// download passed
					trg_url = rslt.Trg();
					if (lnki_w > 0 && lnki_h > 0) {			// lnki specified width and height; check against xfer; needed when w/h are wrong; lnki=65,50 but xfer=160,160; actl should be 50,50; PAGE:en.w:[[Image:Gnome-mime-audio-openclipart.svg|65x50px|center|link=|alt=]]; SEE:NOTE_1
						Xof_xfer_itm_.Calc_xfer_size(calc_size, xfer_itm.Lnki_type(), wiki.Html_mgr().Img_thumb_width(), file_w, file_h, lnki_w, lnki_h, lnki_thumbable, -1, limit);	// NOTE: do not use lnki_upright; already applied above to generate new lnki_w; using it again will double-apply it 
						if (Int_.Between(lnki_w, calc_size.Val_0() - 1, calc_size.Val_0() + 1))	// width matches; done
							return true;
						else {								// width fails; cleanup invalid thumb
							trg_url = rslt.Trg();			// NOTE: update url b/c size may have changed; PAGE:en.w:commons/Image:Tempesta.djvu which is 800px, but resized to 799px
							Io_mgr.Instance.DeleteFil(trg_url);	// delete file
							meta_itm.Thumbs_del(lnki_w);	// delete thumb
							lnki_w = calc_size.Val_0(); lnki_h = calc_size.Val_1();
						}
					}
					else									// xfer found that matches lnki; exit;
						return true;
				}
				else
					break;
			}
		}
		else {		// only height specified
			if (meta_itm.Orig_w() > 0) {	// query discovered orig_w; note: not the same as orig_exists b/c flag may not be set yet
				Xof_xfer_itm_.Calc_xfer_size(calc_size, xfer_itm.Lnki_type(), wiki.Html_mgr().Img_thumb_width(), file_w, file_h, lnki_w, lnki_h, lnki_thumbable, lnki_upright);// calculate again using width and height
				Xof_xfer_itm_.Calc_xfer_size(calc_size, xfer_itm.Lnki_type(), wiki.Html_mgr().Img_thumb_width(), meta_itm.Orig_w(), meta_itm.Orig_h(), lnki_w, lnki_h, lnki_thumbable, lnki_upright, limit);
				lnki_w = calc_size.Val_0(); lnki_h = calc_size.Val_1();

				src_str = src_repo.Tarball() ? this.Src_url(src_repo, Xof_img_mode_.Tid__orig, Xof_img_size.Size_null) : this.Src_url(src_repo, Xof_img_mode_.Tid__thumb, lnki_w);
				trg_url = this.Trg_url(trg_repo, Xof_img_mode_.Tid__thumb, lnki_w);
				return Make_img_exec(src_str, trg_url);
			}
			else {	// no orig dimensions; do download
				if (lnki_w == Xof_img_size.Null)
					lnki_w = wiki.Html_mgr().Img_thumb_width();	// set lnki_w to default thumb_width (220)
				src_str = src_repo.Tarball() ? this.Src_url(src_repo, Xof_img_mode_.Tid__orig, Xof_img_size.Size_null) : this.Src_url(src_repo, Xof_img_mode_.Tid__thumb, lnki_w);
				trg_url = this.Trg_url(trg_repo, Xof_img_mode_.Tid__thumb, lnki_w);
				if (Make_img_exec(src_str, trg_url)) {		// download
					if (src_repo.Tarball()) return true;	// convert worked; no need to download again;
					int old_lnki_w = lnki_w;
					lnki_w = (file_w * lnki_h) / file_h;	// calculate correct width for specified height;
					lnki_w = Xof_xfer_itm_.Calc_w(file_w, file_h, lnki_h);
					if (lnki_w == old_lnki_w) return true;	// download at 220 actually worked; this will probably occur very infrequently, but if so, exit
					src_str = this.Src_url(src_repo, Xof_img_mode_.Tid__thumb, lnki_w);
					trg_url = this.Trg_url(trg_repo, Xof_img_mode_.Tid__thumb, lnki_w);
					if (Make_img_exec(src_str, trg_url)) {	// download again
						trg_url = rslt.Trg();
						Xof_xfer_itm_.Calc_xfer_size(calc_size, xfer_itm.Lnki_type(), wiki.Html_mgr().Img_thumb_width(), file_w, file_h, lnki_w, lnki_h, lnki_thumbable, lnki_upright);// calculate again using width and height
						if (Int_.Between(lnki_w, calc_size.Val_0() - 1, calc_size.Val_0() + 1))	// width matches; done
							return true;
						else {								// width fails; cleanup invalid thumb; EX:w:[[File:Upper and Middle Manhattan.jpg|x120px]]
							trg_url = rslt.Trg();			// NOTE: update url b/c size may have changed; PAGE:en.w:commons/Image:Tempesta.djvu which is 800px, but resized to 799px
							Io_mgr.Instance.DeleteFil(trg_url);	// delete file
							meta_itm.Thumbs_del(lnki_w);	// delete thumb
							lnki_w = calc_size.Val_0(); lnki_h = calc_size.Val_1();
						}
					}
				}
			}
		}
		return false;
	}
	boolean Make_img_exec(String src_str, Io_url trg_url) {
		if (src_repo_is_wmf)
			return Img_download(src_str, trg_url, true);
		else
			return Img_convert(Io_url_.new_fil_(src_str), trg_url);
	}
	private void Make_vid() {
		boolean thumb_pass = false;
		Make_other();													// NOTE: must go before thumb b/c rslt.Pass() is modified by both
		if (src_repo_is_wmf) {											// src is wmf >>> copy down thumb; NOTE: thumb not available in tar
			String src_str = this.Src_url(src_repo, Xof_img_mode_.Tid__thumb, lnki_w);
			Io_url trg_url = this.Trg_url(trg_repo, Xof_img_mode_.Tid__thumb, lnki_w);
			thumb_pass = Cmd_download(src_str, trg_url, false);			// NOTE: ogg audios may sometimes have thumb, but 0 size; thumb_pass will be true, but will fail on thumb_rename; PAGE:en.w:Beethoven; [[File:Ludwig van Beethoven - Symphonie 5 c-moll - 1. Allegro con brio.ogg]]
			if (thumb_pass) {
				thumb_pass = Img_rename_by_size(trg_url);					// NOTE: lnki cites view_w which will rarely match file_w; PAGE:en.w:Earth;Northwest coast of United States to Central South America at Night.ogv|250px; which is atually 640
				if (thumb_pass) {
					Xof_meta_thumb thumb = meta_itm.Update_thumb_add(file_w, file_h); // NOTE: only store 1 width; depend on browser to resize to other widths; this matches MW's behavior
					if (Xof_lnki_time.Null_n(lnki_thumbtime)) {		// lnki specified seek
						thumb.Seeks_add(Xof_lnki_time.X_int(lnki_thumbtime));
						meta_itm.Owner_fil().Dirty_();
					}
					rslt.Clear();						
				}
				else													// something failed; delete file
					Io_mgr.Instance.DeleteFil(trg_url);
			}
			if (!thumb_pass)	// NOTE: thumb failed; mark itm as oga
				meta_itm.Update_thumb_oga_();
		}
	}
	boolean Make_other() {
		if (!Orig_max_download() && !force_orig) return false;
		String src_str = this.Src_url(src_repo, Xof_img_mode_.Tid__orig, Xof_img_size.Size__neg1);
		Io_url trg_url = this.Trg_url(trg_repo, Xof_img_mode_.Tid__orig, Xof_img_size.Size__neg1);
		return Cmd_download(src_str, trg_url, true);
	}
	boolean Orig_max_download() {
		long ext_max = ext_rule.View_max();
		return (orig_file_len < ext_max)				// file_len is less than defined max
			|| (ext_max == Xof_rule_itm.Max_wildcard);	// max is defined as wildcard
//			return !(orig_file_len < ext_max || (ext_max == Xof_rule_itm.Max_wildcard && orig_file_len < 1));
	}
	boolean Img_download(String src_str, Io_url trg_url, boolean cur_is_thumb) {
		rslt.Atrs_src_trg_(src_str, trg_url);	// NOTE: must be set at start; Img_rename_by_size may overwrite trg
		if (!Cmd_download(src_str, trg_url, !cur_is_thumb)) return false;
		if (cur_is_thumb) {
			if (orig_w < 1 || orig_h < 1 || lnki_w < 1 || lnki_h < 1) {	// NOTE: if orig is unknown, calc will be based on lnki size which may be incorrect; PAGE:en.w:{{Olympic Summer Games Host Cities}};[[File:Flag of the United States.svg|22x20px]] which is really 22x12px
				if (!Img_rename_by_size(trg_url)) return false;
				trg_url = rslt.Trg();	// NOTE: update url b/c size may have changed
			}
			else {
				file_w = lnki_w; file_h = lnki_h;
			}
			meta_itm.Update_thumb_add(file_w, file_h);
		}
		else {
			if ((orig_w < 1 || orig_h < 1) && !orig_ext.Id_is_djvu()) {	// NOTE: imageMagick cannot size djvu xfer_itm so ignore
				if (!Cmd_query_size(trg_url)) return false;
				meta_itm.Update_orig_size(file_w, file_h);
			}
			meta_itm.Orig_exists_(Xof_meta_itm.Exists_y);
		}
		return true;
	}	String_obj_ref img_convert_rslt = String_obj_ref.null_();
	private boolean Img_convert(Io_url src_url, Io_url trg_url) {
		rslt.Atrs_src_trg_(src_url.Xto_api(), trg_url);	// NOTE: must be set at start; Img_rename_by_size may overwrite trg
		if (Io_mgr.Instance.ExistsFil(trg_url)) return true; // NOTE: already converted; occurs when same image used twice on same page (EX: flags)
		if (!file_mgr.Img_mgr().Wkr_resize_img().Resize_exec(src_url, trg_url, lnki_w, lnki_h, orig_ext.Id(), img_convert_rslt)) {
			return rslt.Fail("convert failed|" + src_url.Raw() + "|" + img_convert_rslt.Val());
		}
		if (lnki_w < 1 || lnki_h < 1) {	// lnki_w or lnki_h is invalid >>> get real size for thumb
			if (!Img_rename_by_size(trg_url)) return false;
			trg_url = rslt.Trg(); // NOTE: update url b/c size may have changed
		}
		else {
			file_w = lnki_w; file_h = lnki_h;
		}
		meta_itm.Vrtl_repo_(xfer_itm.Orig_repo_id());
		meta_itm.Orig_exists_(Xof_meta_itm.Exists_y);
		meta_itm.Update_thumb_add(file_w, file_h);
		return true;
	}
	private boolean Img_rename_by_size(Io_url trg_url) {
		if (!Cmd_query_size(trg_url)) return false;
		if (file_w != lnki_w) {	// NOTE: only rename if file_w is different; this proc can be called if file_w is same, but file_h < 1; EX: A.svg|thumb|30px will call this proc to get size of thumb
			String new_name = Xof_lnki_time.Null_y(lnki_thumbtime) ? file_w + "px" : file_w + "px" + Xof_meta_thumb_parser.Dlm_seek_str + Xof_lnki_time.X_str(lnki_thumbtime);
			Io_url new_trg = trg_url.GenNewNameOnly(new_name);
			if (trg_url.Eq(new_trg)) return true;	// HACK: io will delete file if moving unto itself; (i.e.: mv A.png A.png is same as del A.png); problem is that this proc is being called too many times
			try {Io_mgr.Instance.MoveFil_args(trg_url, new_trg, true).Exec();}
			catch (Exception exc) {Err_.Noop(exc); return rslt.Fail("move failed");}
			rslt.Trg_(new_trg);
		}
		return true;
	}
	private boolean Cmd_download(String src_str, Io_url trg_url, boolean cur_is_orig) {
		boolean exists = false;
		if (check_file_exists_before_xfer) {
			gplx.core.ios.IoItmFil fil_itm = Io_mgr.Instance.QueryFil(trg_url);
			exists = fil_itm.Exists() && fil_itm.Size() > 0;
		}
		boolean pass = false;
		if (exists) 
			pass = true;
		else {
			byte download_rslt = wmf_mgr.Download_wkr().Download(src_repo_is_wmf, src_str, trg_url, wmf_mgr.Download_wkr().Download_xrg().Prog_fmt_hdr());
			if (download_rslt == gplx.core.ios.IoEngine_xrg_downloadFil.Rslt_fail_host_not_found) {
				wiki.File_mgr().Cfg_download().Enabled_(false);
				throw Err_.new_wo_type("download_failed: host not found", "src", src_str, "trg", trg_url.Raw());
			}
			pass = download_rslt == gplx.core.ios.IoEngine_xrg_downloadFil.Rslt_pass;
		}
		// update meta attributes; placed here b/c Cmd_download is called by 3 procs; note that thumb meta is handled by calling procs as the logic is more specific
		if (cur_is_orig) {
			if (pass)	meta_itm.Orig_exists_(Xof_meta_itm.Exists_y);
			else		meta_itm.Orig_exists_(Xof_meta_itm.Exists_n);
		}
		if (pass)
			meta_itm.Vrtl_repo_(xfer_itm.Orig_repo_id());
		else
			rslt.Fail("download failed|" + src_str);
		return pass;
	}
	private boolean Cmd_query_size(Io_url trg_url) {
		SizeAdp file_size = file_mgr.Img_mgr().Wkr_query_img_size().Exec(trg_url);
		if (file_size == SizeAdp_.Zero) return rslt.Fail("query size failed");
		file_w = file_size.Width(); file_h = file_size.Height();
		return true;
	}
	String Src_url(Xof_repo_itm repo, byte mode, int lnki_w)	{return url_bldr.Init_for_src_file(repo, mode, orig_ttl, orig_ttl_md5, orig_ext, lnki_w, lnki_thumbtime, lnki_page).Xto_str();}
	Io_url Trg_url(Xof_repo_itm repo, byte mode, int lnki_w)	{return url_bldr.Init_for_trg_file(repo, mode, orig_ttl, orig_ttl_md5, orig_ext, lnki_w, lnki_thumbtime, lnki_page).Xto_url();}
	private Xof_url_bldr url_bldr = new Xof_url_bldr();
}
/*
NOTE_1:always recalc w/h
[[Image:Gnome-mime-audio-openclipart.svg|65x50px|center|link=|alt=]]
. orig size is 160,160
. lnki size is 65,50
. actl size should be 50,50
. however, WP actually happens to have a 65,65 on server

The problem is that WP always knows orig size info (160,160) and can correct 65,50 to 50,50
XO does not know orig size info (image.sql needs to be downloaded) and needs to somehow decide that 65,50 is wrong even though 65 is on server

So, do the following
. assume that dimensions of 65,65 are correctly scaled from 160,160
. calc 65,50 for the 65w image
.. if 65,50 is correct, then we should get back 65,50
.. if not, then the lnki is wrong. just download orig and rescale
... note that we can redownload thumb at 50, but simply easier to "fall-through" to orig processing
*/