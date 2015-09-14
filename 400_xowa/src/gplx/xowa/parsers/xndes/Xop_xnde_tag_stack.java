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
package gplx.xowa.parsers.xndes; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_xnde_tag_stack {
	public void Push()		{xmlTagsStack.Add(xmlTags); xmlTags = new int[Xop_xnde_tag_.Tid__len];}
	public void Pop()		{xmlTags = (int[])List_adp_.Pop(xmlTagsStack);}
	public boolean Has(int id) {return xmlTags[id] != 0;}
	public void Add(int id) {++xmlTags[id];}
	public void Del(int id) {
		int val = --xmlTags[id];
		if (val == -1) xmlTags[id] = 0;
	}
	public void Clear() {
		for (int i = 0; i < Xop_xnde_tag_.Tid__len; i++)
			xmlTags[i] = 0;
		xmlTagsStack.Clear();
	}
	List_adp xmlTagsStack = List_adp_.new_();
	int[] xmlTags = new int[Xop_xnde_tag_.Tid__len];
}
