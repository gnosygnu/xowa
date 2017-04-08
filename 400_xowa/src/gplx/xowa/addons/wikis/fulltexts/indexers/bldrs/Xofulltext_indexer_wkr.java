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
package gplx.xowa.addons.wikis.fulltexts.indexers.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.wikis.*; import gplx.xowa.addons.wikis.fulltexts.*; import gplx.xowa.addons.wikis.fulltexts.indexers.*;
import gplx.gflucene.core.*;
import gplx.gflucene.indexers.*;
import gplx.xowa.addons.wikis.fulltexts.core.*;
public class Xofulltext_indexer_wkr {
	private final    Gflucene_indexer_mgr index_wtr = new Gflucene_indexer_mgr();
	private final    Xofulltext_extractor extractor = new Xofulltext_extractor();
	public void Init(Xow_wiki wiki, String idx_opt) {
		// delete existing dir
		Io_url index_dir = Xosearch_fulltext_addon.Get_index_dir(wiki);
		Io_mgr.Instance.DeleteDirDeep(index_dir);

		// init index_dir
		index_wtr.Init(new Gflucene_index_data
		( Gflucene_analyzer_data.New_data_from_locale(wiki.Lang().Key_str())
		, index_dir.Xto_api())
		, idx_opt
		);
	}
	public void Index(Xoae_page wpg) {
		synchronized (index_wtr) {// NOTE:synchronized needed for mass_parse; don't launch separate indexer per mp_thread b/c (a) lucene may not handle it well; (b) everything needs to be serialized to the same lucene dir, so no real performance benefits; DATE:2017-04-08
			byte[] html = extractor.Extract(wpg.Db().Html().Html_bry());
			Index(wpg.Db().Page().Id(), wpg.Db().Page().Score(), wpg.Ttl().Page_txt(), html);
		}
	}
	public void Index(int page_id, int score, byte[] ttl, byte[] html) {
		Gflucene_doc_data data = new Gflucene_doc_data(page_id, score, String_.new_u8(ttl), String_.new_u8(html));
		index_wtr.Exec(data);
	}
	public void Term() {
		index_wtr.Term();
	}
}
