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
package gplx.xowa.addons.wikis.searchs.searchers.rslts; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.searchs.*; import gplx.xowa.addons.wikis.searchs.searchers.*;
public class Srch_rslt_row {
	public Srch_rslt_row(byte[] key, byte[] wiki_bry, Xoa_ttl page_ttl, int page_ns, byte[] page_ttl_wo_ns, int page_id, int page_len, int page_score, int page_redirect_id) {
		this.Key = key;
		this.Wiki_bry = wiki_bry;
		this.Page_id = page_id;
		this.Page_ttl = page_ttl;
		this.Page_ns = page_ns;
		this.Page_ttl_wo_ns = page_ttl_wo_ns;
		this.Page_len = page_len;
		this.Page_redirect_id = page_redirect_id;
		this.Page_score = page_score;
	}
	public final    byte[]		Key;
	public final    byte[]		Wiki_bry;
	public final    int			Page_id;
	public final    Xoa_ttl		Page_ttl;
	public final    int			Page_ns;
	public final    byte[]		Page_ttl_wo_ns;
	public final    int			Page_len;
	public int			        Page_redirect_id;
	public final    int			Page_score;
	public byte[]				Page_redirect_ttl;
	public byte[]               Page_ttl_highlight;

	public byte[] To_display(int display_type) {
		// NOTE: use Highlight for suggest only; note that url_bar also sets Highlight
		byte[] rv
			= display_type == Srch_rslt_row.Display_type__suggest
			? this.Page_ttl_highlight
			: Page_ttl.Full_txt_w_ttl_case();

		// no redirect; just return it; EX: "Actual page"
		if (Page_redirect_id == Page_redirect_id_null)
			return rv;
		// redirect exists; add it to display; EX: "Redirect -> Actual page"
		else {
			// NOTE: "→" does not work for SWT GUI
			byte[] redirect_dlm
				= display_type == Srch_rslt_row.Display_type__url_bar
				? Bry__redirect__text
				: Bry__redirect__html;
			return Bry_.Add(rv, redirect_dlm, Page_redirect_ttl);
		}
	}

	public static byte[] Bld_key(byte[] wiki_domain, int page_id) {return Bry_.Add(wiki_domain, Byte_ascii.Pipe_bry, Int_.To_bry(page_id));}
	public static Srch_rslt_row New(byte[] wiki_bry, Xoa_ttl page_ttl, int page_id, int page_len, int page_score, int redirect_id) {
		return new Srch_rslt_row(Bld_key(wiki_bry, page_id), wiki_bry, page_ttl, page_ttl.Ns().Id(), page_ttl.Page_db(), page_id, page_len, page_score, redirect_id);
	}
	public static final int Page_redirect_id_null = gplx.xowa.wikis.data.tbls.Xowd_page_itm.Redirect_id_null;
	public static final String Str__redirect__text = "  ->  ";
	private static final    byte[] 
	  Bry__redirect__html = Bry_.new_u8(" → ")	// 8592; 8594
	, Bry__redirect__text = Bry_.new_a7(Str__redirect__text);
	public static final int
	  Display_type__url_bar      = 1
	, Display_type__suggest      = 2
	, Display_type__special_page = 3
	;
}
