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
package gplx.xowa.mediawiki.includes.parsers.preprocessors; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
// MW.FILE:Preprocessor_Hash
/**
* @ingroup Parser
*/
import gplx.xowa.mediawiki.includes.exception.*;
public class XomwPPNode_Hash_Text extends XomwPPNode {	public String value;
	private final    XophpArray store;
	private final    int index;

	/**
	* Construct an Object using the data from $store[$index]. The rest of the
	* store array can be accessed via getNextSibling().
	*
	* @param array $store
	* @param integer $index
	*/
	public XomwPPNode_Hash_Text(XophpArray store, int index) {
		Object value_obj = store.Get_at(index);
		if (!XophpType_.is_scalar(value_obj)) {
			throw XomwMWException.New_by_method(XomwPPNode_Hash_Text.class, "CTOR", "given Object instead of String");
		}
		this.value = Object_.Xto_str_strict_or_null(value_obj);
		this.store = store;
		this.index = index;
	}

	@Override public String toString() {
		return String_.new_u8(Bry_.Escape_html(Bry_.new_u8(this.value)));
	}

	@Override public XomwPPNode getNextSibling() {
		return XomwPPNode_Hash_Tree.factory(this.store, this.index + 1);
	}

	@Override public XomwPPNode_Hash_Array getChildren() {
		return null;
	}

	@Override public XomwPPNode getFirstChild() {
		return null;
	}

	@Override public XomwPPNode_Hash_Array getChildrenOfType(String name) {
		return null;
	}

	@Override public int getLength() {
		return XophpInt_.False;
	}

	@Override public XomwPPNode item(int i) {
		return null;
	}

	@Override public String getName() {
		return "#text";
	}

	@Override public XophpArray splitArg() {
		throw XomwMWException.New_by_method_obj(this, "splitArg", ": not supported");
	}

	@Override public XophpArray splitExt() {
		throw XomwMWException.New_by_method_obj(this, "splitExt", ": not supported");
	}

	@Override public XophpArray splitHeading() {
		throw XomwMWException.New_by_method_obj(this, "splitHeading", ": not supported");
	}
}
