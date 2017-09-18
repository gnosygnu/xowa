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
package gplx.xowa.addons.bldrs.exports.packs.files; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.exports.*; import gplx.xowa.addons.bldrs.exports.packs.*;
import org.junit.*; import gplx.core.tests.*;
public class Pack_zip_name_bldr__tst {
	private Pack_zip_name_bldr__fxt fxt = new Pack_zip_name_bldr__fxt();
	@Test   public void Basic() {
		fxt.Test__to_wiki_url("mem/wiki/en.wikipedia.org/", "mem/wiki/en.wikipedia.org/tmp/Xowa_enwiki_2016-09_file_deletion_2016.09/", "mem/wiki/en.wikipedia.org/en.wikipedia.org-file-deletion-2016.09.xowa");
	}
	@Test   public void Bld_by_suffix() {
		Pack_zip_name_bldr bldr = fxt.Make__bldr("mem/wiki/en.wikipedia.org/tmp/pack/", "en.wikipedia.org", "enwiki", "2017-03", null);
		fxt.Test__bld_by_suffix(bldr, "xtn.fulltext_search", 1, "mem/wiki/en.wikipedia.org/tmp/pack/Xowa_enwiki_2017-03_xtn.fulltext_search.002.zip");
	}
}
class Pack_zip_name_bldr__fxt {
	public void Test__to_wiki_url(String wiki_dir, String zip_fil, String expd) {
		Gftest.Eq__str(expd, Pack_zip_name_bldr.To_wiki_url(Io_url_.mem_fil_(wiki_dir), Io_url_.mem_dir_(zip_fil)).Raw(), "wiki_url");
	}

	public Pack_zip_name_bldr Make__bldr(String wiki_dir, String domain, String wiki_abrv, String wiki_date, String custom_name) {
		return new Pack_zip_name_bldr(Io_url_.new_dir_(wiki_dir), domain, wiki_abrv, wiki_date, custom_name);
	}
	public void Test__bld_by_suffix(Pack_zip_name_bldr bldr, String suffix, int pack_num, String expd) {
		Gftest.Eq__str(expd, bldr.Bld_by_suffix(suffix, pack_num).Xto_api());
	}
}
