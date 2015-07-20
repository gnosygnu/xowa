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
package gplx.xmls; import gplx.*;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
public class XmlAtrList {
	public int Count() {return list == null ? 0 : list.getLength();}
	public String FetchValOr(String key, String or) {
		Node xatr = list.getNamedItem(key);
		return (xatr == null) ? or : xatr.getNodeValue();
	}
	public XmlAtr Fetch(String key) {
		Node xatr = list.getNamedItem(key); if (xatr == null) throw Err_.new_missing_key(key);
		return new XmlAtr(xatr);
	}
	public XmlAtr Fetch_or_null(String key) {
		Node xatr = list.getNamedItem(key); if (xatr == null) return null;
		return new XmlAtr(xatr);
	}
	public XmlAtr Get_at(int i) {return list == null ? null : new XmlAtr(list.item(i));}
	@gplx.Internal protected XmlAtrList(NamedNodeMap list) {this.list = list;} NamedNodeMap list;
}
