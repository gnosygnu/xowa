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
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.xowa.guis.cbks.js.*; import gplx.xowa.files.repos.*;
public interface Xof_file_itm {
	int					Lnki_exec_tid();
	byte[]				Lnki_wiki_abrv();
	byte[]				Lnki_ttl();
	byte 				Lnki_type();
	double				Lnki_upright();
	int					Lnki_w();
	int					Lnki_h();
	double				Lnki_time();
	int					Lnki_page();
	boolean				Orig_exists();
	byte				Orig_repo_id();
	byte[]				Orig_repo_name();
	byte[]				Orig_ttl();
	byte[]				Orig_ttl_md5();
	Xof_ext				Orig_ext();
	int					Orig_w();
	int					Orig_h();
	byte[]				Orig_redirect();
	boolean				File_is_orig();
	int					File_w();
	boolean				File_exists();
	boolean				File_exists_in_cache();
	int					Html_uid();
	byte				Html_elem_tid();
	int					Html_w();
	int					Html_h();
	Io_url				Html_view_url();
	Io_url				Html_orig_url();
	int					Html_gallery_mgr_h();
	Js_img_wkr			Html_img_wkr();
	int					Hdump_mode();

	void				File_exists_(boolean v);
	void				Html_orig_url_(Io_url v);
	void				Html_img_wkr_(Js_img_wkr v);
	void				Html_elem_tid_(byte v);
	void				Html_size_(int w, int h);
	void				Html_gallery_mgr_h_(int h);

	void				Init_at_lnki(int exec_tid, byte[] wiki_abrv, byte[] ttl, byte lnki_type, double upright, int w, int h, double time, int page, int lnki_upright_patch);
	void				Init_at_orig(byte orig_repo_id, byte[] orig_repo_name, byte[] orig_ttl, Xof_ext orig_ext, int orig_w, int orig_h, byte[] orig_redirect);
	void				Init_at_html(int exec_tid, Xof_img_size img_size, Xof_repo_itm repo, Xof_url_bldr url_bldr);
	void				Init_at_hdoc(int html_uid, byte html_elem_tid);
	void				Init_at_gallery_bgn(int html_w, int html_h, int file_w);
	void				Init_at_gallery_end(int html_w, int html_h, Io_url html_view_url, Io_url html_orig_url);

	boolean				Dbmeta_is_new();
}
