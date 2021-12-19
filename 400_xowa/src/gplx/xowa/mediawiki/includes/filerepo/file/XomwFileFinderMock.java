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
package gplx.xowa.mediawiki.includes.filerepo.file;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.filerepo.*;
public class XomwFileFinderMock implements XomwFileFinder {
	private final XomwEnv env;
	public XomwFileFinderMock(XomwEnv env) {this.env = env;}
	private final Hash_adp_bry hash = Hash_adp_bry.cs();
	public void Clear() {hash.Clear();}
	public XomwFile Find_file(XomwTitleOld ttl) {
		return (XomwFile)hash.GetByOrNull(ttl.getPrefixedDBkey());
	}
	public void Add(String title, XomwFileRepo repo, int w, int h, byte[] mime) {
		byte[] title_bry = BryUtl.NewU8(title);
		XomwLocalFile file = new XomwLocalFile(env, XomwTitleOld.newFromText(env, title_bry), repo, w, h, mime);
		hash.AddIfDupeUseNth(title_bry, file);
	}
}
