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
package gplx.xowa.xtns.wbases.mediawiki.client.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*; import gplx.xowa.xtns.wbases.mediawiki.*; import gplx.xowa.xtns.wbases.mediawiki.client.*;
import gplx.xowa.mediawiki.*;
public class Wbase_client {
	private Wbase_repo_linker repoLinker;
	public Wbase_client(Wbase_settings settings) {
		this.repoLinker = new Wbase_repo_linker
			( settings.getSetting(Wbase_settings.Setting_repoUrl)
			, settings.getSetting(Wbase_settings.Setting_repoArticlePath)
			, settings.getSetting(Wbase_settings.Setting_repoScriptPath)
			);
	}
	public Wbase_repo_linker RepoLinker() {return repoLinker;}

	private static Wbase_client defaultInstance;
	public static Wbase_client getDefaultInstance() {
		if (defaultInstance == null) {
			defaultInstance = new Wbase_client(Wbase_settings.New_dflt());
		}
		return defaultInstance;
	}
}
