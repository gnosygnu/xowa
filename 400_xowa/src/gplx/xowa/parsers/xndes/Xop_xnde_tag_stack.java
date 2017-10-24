/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
	List_adp xmlTagsStack = List_adp_.New();
	int[] xmlTags = new int[Xop_xnde_tag_.Tid__len];
}
