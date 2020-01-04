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
public class XomwPPNode_Hash_Attr extends XomwPPNode { 	public String name, value;
	private final    XophpArray store;
	private final    int index;

	/**
	* Construct an Object using the data from $store[$index]. The rest of the
	* store array can be accessed via getNextSibling().
	*
	* @param array $store
	* @param integer $index
	*/
	public XomwPPNode_Hash_Attr(XophpArray store, int index) {
		XophpArray descriptor = (XophpArray)store.Get_at(index);
		String descriptor_name = descriptor.Get_at_str(XomwPPNode_Hash_Tree.NAME);
		if (!(String_.CharAt(descriptor_name, 0) ==  '@')) {
			throw Err_.new_wo_type("XomwPPNode_Hash_Attr.CTOR: invalid name in attribute descriptor");
		}
		this.name = String_.new_u8(XophpString_.substr(Bry_.new_u8(descriptor_name), 1));
		XophpArray descriptor_children = (XophpArray)descriptor.Get_at(XomwPPNode_Hash_Tree.CHILDREN);
		Object value_obj = descriptor_children.Get_at(0);
		this.value = Type_.Eq_by_obj(value_obj, byte[].class) ? String_.new_u8((byte[])value_obj): value_obj.toString();
		this.store = store;
		this.index = index;
	}

	@Override public String toString() {
		return String_.Format("<@{0}>{1}</@{0}>", this.name, Bry_.Escape_html(Bry_.new_u8(this.value))); 
	}

	@Override public String getName() {
		return this.name;
	}

	@Override public XomwPPNode getNextSibling() {
		return XomwPPNode_Hash_Tree.factory(this.store, this.index + 1);
	}

//		public function getChildren() {
//			return false;
//		}
//
	@Override public XomwPPNode getFirstChild() {
		return null;
	}

//		public function getChildrenOfType($name) {
//			return false;
//		}
//
//		public function getLength() {
//			return false;
//		}
//
//		public function item($i) {
//			return false;
//		}
//
//		public function splitArg() {
//			throw new MWException(__METHOD__ . ': not supported');
//		}
//
//		public function splitExt() {
//			throw new MWException(__METHOD__ . ': not supported');
//		}
//
//		public function splitHeading() {
//			throw new MWException(__METHOD__ . ': not supported');
//		}
}
