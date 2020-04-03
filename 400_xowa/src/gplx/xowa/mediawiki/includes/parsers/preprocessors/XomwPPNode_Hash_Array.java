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
import gplx.xowa.mediawiki.includes.exception.*;
// MW.FILE:Preprocessor_Hash
/**
* @ingroup Parser
*/
public class XomwPPNode_Hash_Array extends XomwPPNode { 	public XophpArray value;

	public XomwPPNode_Hash_Array(XophpArray value) {
		this.value = value;
	}

	@Override public String toString() {
		// return var_export($this, true);
		return value.To_str();
	}

	@Override public int getLength() {
		return XophpArray_.count(this.value);
	}

	@Override public XomwPPNode item(int i) {
		return (XomwPPNode)this.value.Get_at(i);
	}

	@Override public String getName() {
		return "#nodelist";
	}

	@Override public XomwPPNode getNextSibling() {
		return null;
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
