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
package gplx.xowa.wmfs.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
public class Xowmf_api_mgr {
	public Xoapi_orig_base	Api_orig() {return api_orig;} public void Api_orig_(Xoapi_orig_base v) {api_orig = v;} private Xoapi_orig_base api_orig = new Xoapi_orig_wmf();
}
