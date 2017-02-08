/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.mws.media; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
import org.junit.*; import gplx.core.tests.*;
import gplx.xowa.mws.utls.*;
import gplx.xowa.mws.parsers.*; import gplx.xowa.mws.parsers.lnkis.*;
import gplx.xowa.mws.filerepo.*; import gplx.xowa.mws.filerepo.file.*;
public class Xomw_ImageHandler__tst {
	private final    Xomw_ImageHandler__fxt fxt = new Xomw_ImageHandler__fxt();
	@Before public void init() {
		fxt.Init__file("A.png", 400, 200);
	}
	@Test   public void normaliseParams() {
		// widthOnly; "Because thumbs are only referred to by width, the height always needs"
		fxt.Test__normaliseParams(fxt.Make__handlerParams(200), fxt.Make__handlerParams(200, 100, 200, 100));
	}
}
class Xomw_ImageHandler__fxt {
	private final    Xomw_ImageHandler handler;
	private final    Xomw_FileRepo repo = new Xomw_FileRepo(Bry_.new_a7("/orig"), Bry_.new_a7("/thumb"));
	private final    Xomw_parser_env env = new Xomw_parser_env();
	private Xomw_File file;
	public Xomw_ImageHandler__fxt() {
		handler = new Xomw_TransformationalImageHandler(Bry_.new_a7("test_handler"));
	}
	public Xomw_params_handler Make__handlerParams(int w) {return Make__handlerParams(w, Php_utl_.Null_int, Php_utl_.Null_int, Php_utl_.Null_int);}
	public Xomw_params_handler Make__handlerParams(int w, int h, int phys_w, int phys_h) {
		Xomw_params_handler rv = new Xomw_params_handler();
		rv.width = w;
		rv.height = h;
		rv.physicalWidth = phys_w;
		rv.physicalHeight = phys_h;
		return rv;
	}
	public void Init__file(String title, int w, int h) {
		this.file = new Xomw_LocalFile(env, Bry_.new_u8(title), repo, w, h, Xomw_MediaHandlerFactory.Mime__image__png);
	}
	public void Test__normaliseParams(Xomw_params_handler prms, Xomw_params_handler expd) {
		// exec
		handler.normaliseParams(file, prms);

		// test
            Gftest.Eq__int(expd.width, prms.width);
            Gftest.Eq__int(expd.height, prms.height);
            Gftest.Eq__int(expd.physicalWidth, prms.physicalWidth);
            Gftest.Eq__int(expd.physicalHeight, prms.physicalHeight);
	}
}
