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
package gplx.xowa.mediawiki.includes.filerepo.file; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.filerepo.*;
import gplx.xowa.mediawiki.includes.parsers.*;
public class XomwFileFinderMock implements XomwFileFinder {
	private final    XomwEnv env;
	public XomwFileFinderMock(XomwEnv env) {this.env = env;}
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	public void Clear() {hash.Clear();}
	public XomwFile Find_file(XomwTitle ttl) {
		return (XomwFile)hash.Get_by(ttl.getPrefixedDBkey());
	}
	public void Add(String title, XomwFileRepo repo, int w, int h, byte[] mime) {
		byte[] title_bry = Bry_.new_u8(title);
		XomwLocalFile file = new XomwLocalFile(env, XomwTitle.newFromText(env, title_bry), repo, w, h, mime);
		hash.Add_if_dupe_use_nth(title_bry, file);
	}
}
