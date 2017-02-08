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
package gplx.xowa.mediawiki.includes.parsers.prepros; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
public class Xomw_prepro_node__template extends Xomw_prepro_node__base {
	public Xomw_prepro_node__template(byte[] title, Xomw_prepro_node__part[] parts, int line_start) {
		this.title = title; this.parts = parts; this.line_start = line_start;
	}
	public byte[] Title() {return title;} private final    byte[] title;
	public Xomw_prepro_node__part[] Parts() {return parts;} private final    Xomw_prepro_node__part[] parts;
	public int Line_start() {return line_start;} private final    int line_start;
	@Override public void To_xml(Bry_bfr bfr) {
		bfr.Add_str_a7("<template");
		if (line_start > 0) bfr.Add_str_a7(" lineStart=\"").Add_int_variable(line_start).Add_byte_quote();
		bfr.Add_byte(Byte_ascii.Angle_end);
		bfr.Add_str_a7("<title>").Add(title);
		bfr.Add_str_a7("</title>");
		for (Xomw_prepro_node__part part : parts)
			part.To_xml(bfr);
		bfr.Add_str_a7("</template>");
	}
}
