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
package gplx.xowa.wikis.caches; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.xowa.wikis.data.tbls.*;
public class Xow_page_cache_itm implements Xowd_text_bry_owner {
	private long cache_len;
	public Xow_page_cache_itm(boolean cache_permanently, int page_id, Xoa_ttl ttl, byte[] wtxt__direct, byte[] wtxt__redirect) {
		this.cache_permanently = cache_permanently;
		this.page_id = page_id; this.ttl = ttl; this.wtxt__redirect = wtxt__redirect;
		Set_text_bry_by_db(wtxt__direct);
	}
	public Xoa_ttl Ttl() {return ttl;} private Xoa_ttl ttl;
	public byte[] Wtxt__direct()	{return wtxt__direct;} private byte[] wtxt__direct;
	public byte[] Wtxt__redirect()	{return wtxt__redirect;} private byte[] wtxt__redirect;
	public byte[] Wtxt__redirect_or_direct() {
		return wtxt__redirect == null ? wtxt__direct : wtxt__redirect;
	}
	public boolean   Cache_permanently() {return cache_permanently;} private final    boolean cache_permanently;
	public long Cache_len() {return cache_len;}

	// used by xomp
	public int Page_id() {return page_id;} private int page_id;
	public int Redirect_id() {return redirect_id;} private int redirect_id;
	public void Set_text_bry_by_db(byte[] v) {
		this.wtxt__direct = v;
		this.cache_len = wtxt__direct == null ? 0 : wtxt__direct.length;
	}
	public void Redirect_id_(int v) {this.redirect_id = v;}
	public void Set_redirect(Xoa_ttl ttl, byte[] trg_wtxt) {
		this.ttl = ttl;
		Set_text_bry_by_db(trg_wtxt);
		this.wtxt__redirect = wtxt__direct;
	}

	public static final    Xow_page_cache_itm Null = null;
	public static final    Xow_page_cache_itm Missing = new Xow_page_cache_itm(false, -1, null, null, null);
}
