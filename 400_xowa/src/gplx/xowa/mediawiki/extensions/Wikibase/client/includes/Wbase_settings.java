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
package gplx.xowa.mediawiki.extensions.Wikibase.client.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.extensions.*; import gplx.xowa.mediawiki.extensions.Wikibase.*; import gplx.xowa.mediawiki.extensions.Wikibase.client.*;
public class Wbase_settings {
	private static final Hash_adp_bry hash = Hash_adp_bry.cs();
	public byte[] getSetting(String key) {return getSetting(Bry_.new_u8(key));}
	public byte[] getSetting(byte[] key) {
		return (byte[])hash.Get_by_bry(key);
	}
	public void setSetting(String key, String val) {setSetting(Bry_.new_u8(key), Bry_.new_u8(val));}
	public void setSetting(byte[] key, byte[] val) {
		hash.Add(key, val);
	}

	public static Wbase_settings New_dflt() {
		// https://www.mediawiki.org/wiki/Wikibase/Installation/Advanced_configuration
		return New
		( "https://www.wikidata.org" // NOTE: should be "//wikidata.org", but "//wikidata.org" will open page in browser, not XOWA
		, "/wiki/$1"
		, "/w"
		);
	}

	public static Wbase_settings New(String repoUrl, String repoArticlePath, String repoScriptPath) {
		Wbase_settings rv = new Wbase_settings();
		rv.setSetting(Setting_repoUrl, repoUrl);
		rv.setSetting(Setting_repoArticlePath, repoArticlePath);
		rv.setSetting(Setting_repoScriptPath, repoScriptPath);
		return rv;
	}
	public static final String 
	  Setting_repoUrl           = "repoUrl"
	, Setting_repoArticlePath   = "repoArticlePath"
	, Setting_repoScriptPath    = "repoScriptPath"
	;
}
