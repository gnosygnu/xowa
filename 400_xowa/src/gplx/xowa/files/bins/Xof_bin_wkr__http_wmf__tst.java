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
import org.junit.*; import gplx.core.ios.*; import gplx.core.ios.streams.*; import gplx.xowa.files.repos.*;
public class Xof_bin_wkr__http_wmf__tst {
	private final    Xof_bin_wkr__http_wmf__fxt fxt = new Xof_bin_wkr__http_wmf__fxt();
	@Before public void init() {fxt.Clear();}
	@Test   public void Basic() {
		fxt.Init__Http("mem/http/commons.wikimedia.org/thumb/7/70/A.png/220px-A.png", "test_data");
		fxt.Exec__Get_as_rdr(fxt.Fsdb_itm_mkr().Lnki__en_w("A.png").Orig__commons__lnki(), Bool_.Y, 220);
		fxt.Test__Get_as_rdr__rdr("test_data");
	}
	@Test   public void Enwiki_fails__fallback_to_commons() {
		fxt.Init__Http("mem/http/commons.wikimedia.org/thumb/7/70/A.png/220px-A.png", "test_data");			// put file in commons
		Xof_fsdb_itm fsdb_itm = fxt.Fsdb_itm_mkr().Lnki__en_w("A.png").Orig__enwiki__lnki().Make();
		fxt.Exec__Get_as_rdr(fsdb_itm, Bool_.Y, 220);														// look in enwiki
		fxt.Test__Get_as_rdr__rdr("test_data");																// test that enwiki tries commons again
		Tfds.Eq_str("commons.wikimedia.org", fsdb_itm.Orig_repo_name(), "repo_name");						// test that it's now commons
		Tfds.Eq_byte(Xof_repo_tid_.Tid__remote, fsdb_itm.Orig_repo_id(), "repo_tid");						// test that it's now commons
	}
	@Test   public void Long_filename_becomes_thumbnail() {
		String filename = String_.Repeat("A", 200) + ".png";
		fxt.Init__Http("mem/http/commons.wikimedia.org/thumb/1/14/" + filename + "/220px-thumbnail.png", "test_data");	// add file as "thumbnail.png"
		Xof_fsdb_itm fsdb_itm = fxt.Fsdb_itm_mkr().Lnki__en_w(filename).Orig__enwiki__lnki().Make();
		fxt.Exec__Get_as_rdr(fsdb_itm, Bool_.Y, 220);														// look in enwiki
		fxt.Test__Get_as_rdr__rdr("test_data");																// test that file is there
	}
}
class Xof_bin_wkr__http_wmf__fxt {
	private final    Xof_bin_wkr__http_wmf wkr;
	private final    Io_download_mgr__memory download_mgr;
	private Io_stream_rdr get_as_rdr__rdr;
	public Xof_fsdb_itm_fxt Fsdb_itm_mkr() {return fsdb_itm_mkr;} private final    Xof_fsdb_itm_fxt fsdb_itm_mkr = new Xof_fsdb_itm_fxt();
	public Xof_bin_wkr__http_wmf__fxt() {
		Xoae_app app = Xoa_app_fxt.Make__app__edit();
		Xowe_wiki wiki = Xoa_app_fxt.Make__wiki__edit(app);
		Xoa_app_fxt.repo2_(app, wiki);
		this.download_mgr = Io_download_mgr_.new_memory();
		this.wkr = Xof_bin_wkr__http_wmf.new_(wiki, download_mgr);
	}
	public void Clear() {
		download_mgr.Clear();
	}
	public void Init__Http(String url, String data) {download_mgr.Upload_data(url, Bry_.new_u8(data));}
	public void Exec__Get_as_rdr(Xof_fsdb_itm_fxt fsdb_itm_mkr, boolean is_thumb, int w) {Exec__Get_as_rdr(fsdb_itm_mkr.Make(), is_thumb, w);}
	public void Exec__Get_as_rdr(Xof_fsdb_itm fsdb_itm , boolean is_thumb, int w) {
		this.get_as_rdr__rdr = wkr.Get_as_rdr(fsdb_itm, is_thumb, w);
	}
	public void Test__Get_as_rdr__rdr(String expd) {
		Tfds.Eq_str(expd, Io_stream_rdr_.Load_all_as_str(get_as_rdr__rdr), "rdr_contents");
	}
}
