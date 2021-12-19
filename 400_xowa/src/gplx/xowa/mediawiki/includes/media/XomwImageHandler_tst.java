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
package gplx.xowa.mediawiki.includes.media;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import org.junit.*;
import gplx.xowa.mediawiki.includes.parsers.lnkis.*;
import gplx.xowa.mediawiki.includes.filerepo.*; import gplx.xowa.mediawiki.includes.filerepo.file.*;
public class XomwImageHandler_tst {
	private final XomwImageHandler_fxt fxt = new XomwImageHandler_fxt();
	@Before public void init() {
		fxt.Init__file("A.png", 400, 200);
	}
	@Test public void normaliseParams() {
		// widthOnly; "Because thumbs are only referred to by width, the height always needs"
		fxt.Test__normaliseParams(fxt.Make__handlerParams(200), fxt.Make__handlerParams(200, 100, 200, 100));
	}
}
class XomwImageHandler_fxt {
	private final XomwImageHandler handler;
	private final XomwFileRepo repo = new XomwFileRepo(BryUtl.NewA7("/orig"), BryUtl.NewA7("/thumb"));
	private final XomwEnv env = XomwEnv_fxt.NewTest();
	private XomwFile file;
	public XomwImageHandler_fxt() {
		this.handler = new XomwTransformationalImageHandler(BryUtl.NewA7("test_handler"));
	}
	public Xomw_params_handler Make__handlerParams(int w) {return Make__handlerParams(w, XophpObject_.NULL_INT, XophpObject_.NULL_INT, XophpObject_.NULL_INT);}
	public Xomw_params_handler Make__handlerParams(int w, int h, int phys_w, int phys_h) {
		Xomw_params_handler rv = new Xomw_params_handler();
		rv.width = w;
		rv.height = h;
		rv.physicalWidth = phys_w;
		rv.physicalHeight = phys_h;
		return rv;
	}
	public void Init__file(String title, int w, int h) {
		this.file = new XomwLocalFile(env, XomwTitleOld.newFromText(env, BryUtl.NewU8(title)), repo, w, h, XomwMediaHandlerFactory.Mime__image__png);
	}
	public void Test__normaliseParams(Xomw_params_handler prms, Xomw_params_handler expd) {
		// exec
		handler.normaliseParams(file, prms);

		// test
            GfoTstr.Eq(expd.width, prms.width);
            GfoTstr.Eq(expd.height, prms.height);
            GfoTstr.Eq(expd.physicalWidth, prms.physicalWidth);
            GfoTstr.Eq(expd.physicalHeight, prms.physicalHeight);
	}
}
