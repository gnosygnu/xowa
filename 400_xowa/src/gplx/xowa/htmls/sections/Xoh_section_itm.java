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
package gplx.xowa.htmls.sections; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
public class Xoh_section_itm {
	public Xoh_section_itm(int uid, int level, byte[] anchor, byte[] header) {
		this.uid = uid; this.level = level; this.anchor = anchor; this.header = header;
	}
	public int Uid() {return uid;} private final int uid;
	public int Level() {return level;} private final int level;
	public byte[] Anchor() {return anchor;} private final byte[] anchor;
	public byte[] Header() {return header;} private final byte[] header;
	public byte[] Content() {return content;} private byte[] content;
	public Xoh_section_itm Content_(byte[] v) {this.content = v; return this;}
	public int Content_bgn() {return content_bgn;} public Xoh_section_itm Content_bgn_(int v) {content_bgn = v; return this;} private int content_bgn;
	public void To_bfr(Bry_bfr bfr) {
		bfr.Add_int_variable(uid).Add_byte_pipe();
		bfr.Add_int_variable(level).Add_byte_pipe();
		bfr.Add(anchor).Add_byte_pipe();
		bfr.Add(header).Add_byte_pipe();
		bfr.Add_safe(content).Add_byte_nl();
	}
}
