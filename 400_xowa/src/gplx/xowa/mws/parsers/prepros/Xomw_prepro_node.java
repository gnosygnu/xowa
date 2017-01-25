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
package gplx.xowa.mws.parsers.prepros; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*; import gplx.xowa.mws.parsers.*;
public interface Xomw_prepro_node {
	int Subs__len();
	Xomw_prepro_node Subs__get_at(int i);
	void Subs__add(Xomw_prepro_node sub);
	void To_xml(Bry_bfr bfr);
}
class Xomw_prepro_node__text extends Xomw_prepro_node__base {
	public Xomw_prepro_node__text(byte[] bry) {
		this.bry = bry;
	}
	public byte[] Bry() {return bry;} protected final    byte[] bry;
	@Override public void To_xml(Bry_bfr bfr) {
		bfr.Add(bry);
	}
}
class Xomw_prepro_node__comment extends Xomw_prepro_node__base {
	public Xomw_prepro_node__comment(byte[] bry) {
		this.bry = bry;
	}
	public byte[] Bry() {return bry;} protected final    byte[] bry;
	@Override public void To_xml(Bry_bfr bfr) {
		bfr.Add_str_a7("<comment>");
		bfr.Add(bry);
		bfr.Add_str_a7("</comment>");
	}
}
class Xomw_prepro_node__ext extends Xomw_prepro_node__base {
	public Xomw_prepro_node__ext(byte[] name, byte[] attr, byte[] inner, byte[] close) {
		this.name = name;
		this.attr = attr;
		this.inner = inner;
		this.close = close;
	}
	public byte[] Name() {return name;} private final    byte[] name;
	public byte[] Attr() {return attr;} private final    byte[] attr;
	public byte[] Inner() {return inner;} private final    byte[] inner;
	public byte[] Close() {return close;} private final    byte[] close;
	@Override public void To_xml(Bry_bfr bfr) {
		bfr.Add_str_a7("<ext>");
		bfr.Add_str_a7("<name>").Add(name).Add_str_a7("</name>");
		bfr.Add_str_a7("<atr>").Add(attr).Add_str_a7("</atr>");
		bfr.Add_str_a7("<inner>").Add(inner).Add_str_a7("</inner>");
		bfr.Add_str_a7("<close>").Add(close).Add_str_a7("</close>");
		bfr.Add_str_a7("</ext>");
	}
}
class Xomw_prepro_node__heading extends Xomw_prepro_node__base {
	public Xomw_prepro_node__heading(int heading_index, int title_index, byte[] text) {
		this.heading_index = heading_index;
		this.title_index = title_index;
		this.text = text;
	}
	public int Heading_index() {return heading_index;} private final    int heading_index;
	public int Title_index() {return title_index;} private final    int title_index;
	public byte[] Text() {return text;} private final    byte[] text;
	@Override public void To_xml(Bry_bfr bfr) {
		bfr.Add_str_a7("<h ");
		bfr.Add_str_a7(" level=\"").Add_int_variable(heading_index);
		bfr.Add_str_a7("\" i=\"").Add_int_variable(title_index);
		bfr.Add_str_a7("\">");
		bfr.Add(text);
		bfr.Add_str_a7("</h>");
	}
}
class Xomw_prepro_node__tplarg extends Xomw_prepro_node__base {
	public Xomw_prepro_node__tplarg(byte[] title, Xomw_prepro_node__part[] parts) {
		this.title = title; this.parts = parts;
	}
	public byte[] Title() {return title;} private final    byte[] title;
	public Xomw_prepro_node__part[] Parts() {return parts;} private final    Xomw_prepro_node__part[] parts;
	@Override public void To_xml(Bry_bfr bfr) {
		bfr.Add_str_a7("<tplarg>");
		bfr.Add_str_a7("<title>").Add(title);
		bfr.Add_str_a7("</title>");
		for (Xomw_prepro_node__part part : parts)
			part.To_xml(bfr);

		bfr.Add_str_a7("</tplarg>");
	}
}
