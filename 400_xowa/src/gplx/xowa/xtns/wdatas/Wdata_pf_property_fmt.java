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
package gplx.xowa.xtns.wdatas; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Wdata_pf_property_fmt {
	public byte[] Separator() {return separator;} private byte[] separator;
	public byte[] Value_template() {return value_template;} private byte[] value_template;
	public byte[] Qualifier_template() {return qualifier_template;} private byte[] qualifier_template;
	public byte[] Qualifier_separator() {return qualifier_separator;} private byte[] qualifier_separator;
	public byte[] Qualifiers_wrapper() {return qualifiers_wrapper;} private byte[] qualifiers_wrapper;
	public byte[] Qualifiers_template() {return qualifiers_template;} private byte[] qualifiers_template;
	public byte[] Qualifiers_value_separator() {return qualifiers_value_separator;} private byte[] qualifiers_value_separator;
	public byte[] Reference_keyvalue_template() {return reference_keyvalue_template;} private byte[] reference_keyvalue_template;
	public byte[] Reference_wrapper() {return reference_wrapper;} private byte[] reference_wrapper;
	public byte[] References_wrapper() {return references_wrapper;} private byte[] references_wrapper;
	public byte[] Reference_keyvalue_separator() {return reference_keyvalue_separator;} private byte[] reference_keyvalue_separator;
	public byte[] Reference_template() {return reference_template;} private byte[] reference_template;
	public byte[] Reference_value_separator() {return reference_value_separator;} private byte[] reference_value_separator;
	public byte[] Reference_separator() {return reference_separator;} private byte[] reference_separator;
	public void Init() {
		byte[] comma = new byte[] {Byte_ascii.Comma};
		separator = comma;
		value_template = Bry_.new_ascii_("{{{value}}} {{{qualifiers}}}{{{references}}}");
		qualifier_template = Bry_.new_ascii_("{{{1}}} {{{2}}}");
		qualifier_separator = comma;
		qualifiers_wrapper = Bry_.new_ascii_("{{{1}}}");
		qualifiers_template = Bry_.Empty;
		qualifiers_value_separator = comma;
		reference_keyvalue_template = Bry_.new_ascii_("{{{1}}} {{{2}}}");
		reference_wrapper = Bry_.new_ascii_("<ref>{{{1}}}</ref>");
		references_wrapper = Bry_.new_ascii_("{{{1}}}");
		reference_keyvalue_separator = comma;
		reference_template = Bry_.Empty;
		reference_value_separator = comma;
		reference_separator = Bry_.Empty;	// "a separator to use between each reference"
	}
}
