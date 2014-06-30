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
import org.w3c.dom.NodeList;
public interface XmlNdeList {
	int Count();
	XmlNde FetchAt(int i);
}
class XmlNdeList_cls_xml implements XmlNdeList {
	public int Count() {return list.getLength();}
	public XmlNde FetchAt(int i) {return new XmlNde(list.item(i));}		
	@gplx.Internal protected XmlNdeList_cls_xml(NodeList list) {this.list = list;} NodeList list;
}
class XmlNdeList_cls_list implements XmlNdeList {
	public int Count() {return list.Count();}
	public XmlNde FetchAt(int i) {return (XmlNde)list.FetchAt(i);}
	public void Add(XmlNde xnde) {list.Add(xnde);}
	@gplx.Internal protected XmlNdeList_cls_list(int count) {list = ListAdp_.new_(); list.ResizeBounds(count);} ListAdp list;
}
//#}