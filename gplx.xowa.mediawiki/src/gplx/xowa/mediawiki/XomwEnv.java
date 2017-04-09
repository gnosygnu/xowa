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
package gplx.xowa.mediawiki; import gplx.*; import gplx.xowa.*;
import gplx.xowa.mediawiki.includes.*;
import gplx.xowa.mediawiki.includes.interwiki.*;
import gplx.xowa.mediawiki.includes.filerepo.file.*; import gplx.xowa.mediawiki.includes.media.*;
import gplx.xowa.mediawiki.includes.site.*;
import gplx.xowa.mediawiki.languages.*;
import gplx.xowa.langs.*;
public class XomwEnv {
	public byte[] Lang__align_end = Bry_.new_a7("right");
	public int User__default__thumbsize = 220;

	public int Global__wgSVGMaxSize = 5120;
	public double Global__wgThumbUpright = .75d;
	public int[] Global__wgThumbLimits = new int[] {120, 150, 180, 200, 250, 300};

	public XomwEnv(Xol_lang_itm xoLang) {
		this.language = new XomwLanguage(xoLang);
		XomwSiteLookup siteLookup = new XomwXowaSiteLookup();
		XomwInterwikiLookup interwikiLookup = new XomwInterwikiLookupAdapter(siteLookup);
		byte[][] localInterwikis = new byte[0][]; // TODO.XO: pass in to XomwEnv or retrieve from datastore
		this.mediaWikiServices = new XomwMediaWikiServices(this, interwikiLookup, language, localInterwikis);
	}

	public XomwLanguage Language() {return language;} private final    XomwLanguage language;
	public XomwMagicWordMgr Magic_word_mgr() {return magic_word_mgr;} private final    XomwMagicWordMgr magic_word_mgr = new XomwMagicWordMgr();
	public XomwMessageMgr Message_mgr() {return message_mgr;} private final    XomwMessageMgr message_mgr = new XomwMessageMgr();
	public XomwFileFinder File_finder() {return file_finder;} private XomwFileFinder file_finder = new XomwFileFinderNoop();
	public XomwMediaHandlerFactory MediaHandlerFactory() {return mediaHandlerFactory;} private final    XomwMediaHandlerFactory mediaHandlerFactory = new XomwMediaHandlerFactory();
	public XomwMediaWikiServices MediaWikiServices() {return mediaWikiServices;} private final    XomwMediaWikiServices mediaWikiServices;

	public XomwEnv File_finder_(XomwFileFinder v) {file_finder = v; return this;} 

	public static XomwEnv NewTest() {return NewTestByApp(Xoa_app_fxt.Make__app__edit());}
	public static XomwEnv NewTestByApp(Xoae_app app) {
		return new XomwEnv(Xol_lang_itm_.Lang_en_make(app.Lang_mgr()));
	}
}
