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
package gplx.xowa.htmls.core.htmls.tidy; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.htmls.*;
public class Xoh_tidy_wkr_ {
	public static final byte Tid_null = 0, Tid_tidy = 1, Tid_jtidy = 2;
	public static final    Xoh_tidy_wkr Wkr_null = new Xoh_tidy_wkr_null();
}
class Xoh_tidy_wkr_null implements Xoh_tidy_wkr {
	public byte Tid() {return Xoh_tidy_wkr_.Tid_null;}
	public void Indent_(boolean v) {}
	public void Init_by_app(Xoae_app app) {}
	public void Exec_tidy(Bry_bfr bfr, byte[] page_url) {}
}
