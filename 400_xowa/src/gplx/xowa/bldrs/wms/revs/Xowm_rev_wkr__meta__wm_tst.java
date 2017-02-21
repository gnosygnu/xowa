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
package gplx.xowa.bldrs.wms.revs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
import org.junit.*; import gplx.langs.jsons.*; import gplx.core.net.*; import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.domains.*;
public class Xowm_rev_wkr__meta__wm_tst {
	private final Xowm_rev_wkr__meta__wm_fxt fxt = new Xowm_rev_wkr__meta__wm_fxt();
	@Before public void init() {Gfo_usr_dlg_.Instance = Gfo_usr_dlg_.Test_console();}
	@After public void term() {Gfo_usr_dlg_.Instance = Gfo_usr_dlg_.Noop;}
	@Test  public void Basic() {
		Wmapi_itm__pge[] expd = fxt.Make_pge_ary
		( fxt.Make_pge(Xow_ns_.Tid__main, "A", 1, 11, 100, "2015-01-01T01:01:01Z", "user1", "note1")
		, fxt.Make_pge(Xow_ns_.Tid__main, "B", 2, 22, 200, "2015-02-02T02:02:02Z", "user2", "note2")
		);
		fxt.Init_inet_upload(expd);
		fxt.Test_fetch(String_.Ary("A", "B"), expd);	// test get both
		fxt.Init_inet_upload(expd);
		fxt.Test_fetch(String_.Ary("A")		, expd[0]);	// test get 1
		fxt.Init_inet_upload(expd);
		fxt.Test_fetch(String_.Ary("C"), fxt.Make_pge_empty(0, "C")); // test get none
		fxt.Init_inet_upload(expd);
		fxt.Test_fetch(String_.Ary("A", "B", "C"), expd[0], expd[1], fxt.Make_pge_empty(0, "C")); // test get too many
	}
}
class Xowm_rev_wkr__meta__wm_fxt {
	private final String domain_str = Xow_domain_itm_.Str__enwiki;
	private final Ordered_hash rev_hash = Ordered_hash_.New_bry();
	private final Xowm_rev_wkr__meta__wm meta_wkr = new Xowm_rev_wkr__meta__wm();
	private final Wmapi_itm_json_wtr json_wtr = new Wmapi_itm_json_wtr();
	public Xowm_rev_wkr__meta__wm_fxt() {
		meta_wkr.Inet_conn_(Gfo_inet_conn_.new_mem_pile());
	}
	public Wmapi_itm__pge[] Make_pge_ary(Wmapi_itm__pge... ary) {return ary;}
	public Wmapi_itm__pge Make_pge_empty(int page_ns, String page_ttl) {
		Wmapi_itm__pge rv = Make_pge(page_ns, page_ttl, 0, 0, 0, null, null, null);
		rv.Rvn_ary_(Wmapi_itm__rvn.Ary_empty);
		return rv;
	}
	public Wmapi_itm__pge Make_pge(int page_ns, String page_ttl, int page_id, int rev_id, int rev_len, String rev_time, String rev_user, String rev_note) {
		Wmapi_itm__pge rv = new Wmapi_itm__pge();
		rv.Init_id(page_id);
		rv.Init_ttl(page_ns, Bry_.new_u8(page_ttl));
		Wmapi_itm__rvn rvn = new Wmapi_itm__rvn();
		rv.Rvn_ary_(rvn);
		rvn.Init(rev_id, rev_len, Bry_.new_a7(rev_time), Bry_.new_a7(rev_user), Bry_.new_a7(rev_note));
		return rv;
	}
	public void Init_inet_upload(Wmapi_itm__pge... ary) {
		Gfo_inet_conn inet_conn = meta_wkr.Inet_conn();
		byte[] page = json_wtr.To_bry(ary);
		inet_conn.Clear();
		inet_conn.Upload_by_bytes(domain_str, page);
	}
	public void Test_fetch(String[] ttl_ary, Wmapi_itm__pge... expd) {
		Init_rev_hash(ttl_ary);
		meta_wkr.Fetch_meta(domain_str, rev_hash, 0, rev_hash.Count());
		Tfds.Eq_str_lines(String_.new_u8(json_wtr.To_bry(expd)), String_.new_u8(json_wtr.To_bry((Wmapi_itm__pge[])rev_hash.To_ary_and_clear(Wmapi_itm__pge.class))));
	}
	private void Init_rev_hash(String[] ttl_ary) {
		rev_hash.Clear();
		int len = ttl_ary.length;
		for (int i = 0; i < len; ++i) {
			String ttl_str = ttl_ary[i];
			byte[] ttl_bry = Bry_.new_u8(ttl_str);
			rev_hash.Add(ttl_bry, new Wmapi_itm__pge().Init_ttl(Xow_ns_.Tid__main, ttl_bry));
		}
	}
}
