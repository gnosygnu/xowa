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
package gplx.langs.htmls.parsers; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import gplx.xowa.*;
import gplx.xowa.parsers.xndes.*;
interface Gfo_html_tkn {
	int Tid();
	byte[] Key();
	void Process(byte[] src, Xop_xatr_hash hash);
}
class Gfo_html_tkn_ {
	public static final int Tid_link = 1;
	public static final byte[] Key_link = Bry_.new_a7("link");
}
class Gfo_html_tkn__link implements Gfo_html_tkn {
	public int Tid() {return Gfo_html_tkn_.Tid_link;}
	public byte[] Key() {return Gfo_html_tkn_.Key_link;}
	@gplx.Virtual public void Process(byte[] src, Xop_xatr_hash hash) {}
}
