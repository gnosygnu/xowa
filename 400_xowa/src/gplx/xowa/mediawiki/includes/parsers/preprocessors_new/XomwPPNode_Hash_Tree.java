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
package gplx.xowa.mediawiki.includes.parsers.preprocessors_new; import gplx.*;
import gplx.xowa.mediawiki.*;
// MW.FILE:Preprocessor_Hash
/**
* @ingroup Parser
*/
import gplx.xowa.mediawiki.includes.exception.*;
public class XomwPPNode_Hash_Tree extends XomwPPNode {	public final String name;
	/**
	* The store array for children of this node. It is "raw" in the sense that
	* nodes are two-element arrays ("descriptors") rather than PPNode_Hash_*
	* objects.
	*/
	private XophpArray rawChildren;

	/**
	* The store array for the siblings of this node, including this node itself.
	*/
	private final XophpArray store;

	/**
	* The index into this.store which contains the descriptor of this node.
	*/
	private final int index;

	/**
	* The offset of the name within descriptors, used in some places for
	* readability.
	*/
	public static final int NAME = 0;

	/**
	* The offset of the child list within descriptors, used in some places for
	* readability.
	*/
	public static final int CHILDREN = 1;

	/**
	* Construct an Object using the data from store[index]. The rest of the
	* store array can be accessed via getNextSibling().
	*
	* @param array store
	* @param integer index
	*/
	public XomwPPNode_Hash_Tree(XophpArray store, int index) {
		this.store = store;
		this.index = index;

		XophpArray list = this.store.Get_at_ary(index);
		this.name = list.Get_at_str(0);
		Object rawChildrenObj = list.Get_at(1);
		if (XophpType_.To_type_id(rawChildrenObj) == Type_ids_.Id__array) {
			this.rawChildren = (XophpArray)rawChildrenObj;
		}
		else {
//				this.rawChildren = ((Xomw_prepro_accum__hash)rawChildrenObj).Ary();
		}
	}

	/**
	* Construct an appropriate PPNode_Hash_* Object with a class that depends
	* on what is at the relevant store index.
	*
	* @param array store
	* @param integer index
	* @return PPNode_Hash_Tree|PPNode_Hash_Attr|PPNode_Hash_Text
	*/
	public static XomwPPNode factory(XophpArray store, int index) {
		Object descriptor = store.Get_at(index);
		if (!XophpObject_.isset_obj(descriptor)) {
			return null;
		}

		XophpType type = XophpType.New(descriptor);
		if (type.is_string()) {
			return new XomwPPNode_Hash_Text(store, index);
		}
		else if (type.is_array()) {
			XophpArray descriptor_array = (XophpArray)descriptor;
			String name = (String)(descriptor_array.Get_by(NAME));
			if (String_.CharAt(name, 0) == '@') {
				return new XomwPPNode_Hash_Attr(store, index);
			}
			else {
				return new XomwPPNode_Hash_Tree(store, index);
			}
		}
		else {
			throw XomwMWException.New_by_method(XomwPPNode_Hash_Tree.class, "factory", "invalid node descriptor");
		}
	}

	/**
	* Convert a node to XML, for debugging
	*/
	@Override public String toString() {
		String inner = "";
		String attribs = "";
		for (XomwPPNode node = this.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (Type_.Eq_by_obj(node, XomwPPNode_Hash_Attr.class)) {
				XomwPPNode_Hash_Attr node_attr = (XomwPPNode_Hash_Attr)node;
				attribs += " " + node_attr.name + "=\"" + String_.new_u8(Bry_.Escape_html(Bry_.new_u8(node_attr.value))) + "\"";
			} else {
				inner += node.toString();
			}
		}
		if (String_.Eq(inner, "") && String_.Eq(name, "name")) {
			return "<" + this.name + attribs + " />";
		} else {
			if (String_.Eq(name, "equals")) {
				return inner;
			}
			else {
				return "<" + this.name + attribs + ">" + inner + "</" + this.name + ">";
			}
		}
	}

	/**
	* @return PPNode_Hash_Array
	*/
	@Override public XomwPPNode_Hash_Array getChildren() {
		XophpArray children = XophpArray.New();
		int rawChildrenLen = rawChildren.Len();
		for (int i = 0; i < rawChildrenLen; i++) {
			XophpArrayItm itm = rawChildren.Get_at_itm(i);
			children.Add(XomwPPNode_Hash_Tree.factory(this.rawChildren, itm.KeyAsInt()));
		}
		return new XomwPPNode_Hash_Array(children);
	}

	/**
	* Get the first child, or false if there is none. Note that this will
	* return a temporary proxy Object: different instances will be returned
	* if this is called more than once on the same node.
	*
	* @return PPNode_Hash_Tree|PPNode_Hash_Attr|PPNode_Hash_Text|boolean
	*/
	@Override public XomwPPNode getFirstChild() {
		if (!XophpArray.isset(this.rawChildren, 0)) {
			return null;
		}
		else {
			return factory(this.rawChildren, 0);
		}
	}

	/**
	* Get the next sibling, or false if there is none. Note that this will
	* return a temporary proxy Object: different instances will be returned
	* if this is called more than once on the same node.
	*
	* @return PPNode_Hash_Tree|PPNode_Hash_Attr|PPNode_Hash_Text|boolean
	*/
	@Override public XomwPPNode getNextSibling() {
		return factory(this.store, this.index + 1);
	}

	/**
	* Get an array of the children with a given node name
	*
	* @param String name
	* @return PPNode_Hash_Array
	*/
	@Override public XomwPPNode_Hash_Array getChildrenOfType(String name) {
		XophpArray children = XophpArray.New();
		int rawChildren_len = this.rawChildren.Len();
		for (int idx = 0; idx < rawChildren_len; idx++) {
			XophpArrayItm itm = this.rawChildren.Get_at_itm(idx);
			Object child = itm.Val();
			if (XophpType_.is_array(child) && String_.Eq(((XophpArray)child).Get_at_str(XomwPPNode_Hash_Tree.NAME), name)) {
				children.Add(XomwPPNode_Hash_Tree.factory(this.rawChildren, itm.KeyAsInt()));
			}
		}
		return new XomwPPNode_Hash_Array(children);
	}

	/**
	* Get the raw child array. For @gplx.Internal protected use.
	* @return array
	*/
	public XophpArray getRawChildren() {
		return this.rawChildren;
	}

	/**
	* @return boolean
	*/
	@Override public int getLength() {
		return XophpInt_.False;
	}

	/**
	* @param int i
	* @return boolean
	*/
	@Override public XomwPPNode item(int i) {
		return null;
	}

	/**
	* @return String
	*/
	@Override public String getName() {
		return this.name;
	}

	/**
	* Split a "<part>" node into an associative array containing:
	*  - name          PPNode name
	*  - index         String index
	*  - value         PPNode value
	*
	* @throws MWException
	* @return array
	*/
	@Override public XophpArray splitArg() {
		return XomwPPNode_Hash_Tree.splitRawArg(this.rawChildren);
	}

	/**
	* Like splitArg() but for a raw child array. For @gplx.Internal protected use only.
	*/
	public static XophpArray splitRawArg(XophpArray children) {
		XophpArray bits = XophpArray.New();
		int childrenLen = children.Len();
		for (int j = 0; j < childrenLen; j++) {
			XophpArrayItm itm = children.Get_at_itm(j);
			Object childObj = itm.Val();
			if (!XophpType_.is_array(childObj)) {
				continue;
			}
			XophpArray child = (XophpArray)childObj;
			int i = itm.KeyAsInt();
			if (String_.Eq(child.Get_at_str(XomwPPNode_Hash_Tree.NAME), "name")) {
				bits.Set("name", new XomwPPNode_Hash_Tree(children, i));
				if (XophpObject_.isset_obj(child.Get_at_ary(XomwPPNode_Hash_Tree.CHILDREN).Get_at_ary(0).Get_at(XomwPPNode_Hash_Tree.NAME))
					&& String_.Eq(child.Get_at_ary(XomwPPNode_Hash_Tree.CHILDREN).Get_at_ary(0).Get_at_str(XomwPPNode_Hash_Tree.NAME), "@index")
				) {
					bits.Set("index", child.Get_at_ary(XomwPPNode_Hash_Tree.CHILDREN).Get_at_ary(0).Get_at_ary(XomwPPNode_Hash_Tree.CHILDREN).Get_at(0));
				}
			}
			else if (String_.Eq(child.Get_at_str(XomwPPNode_Hash_Tree.NAME), "value")) {
				bits.Set("value", new XomwPPNode_Hash_Tree(children, i));
			}
		}

		if (!XophpObject_.isset_obj(bits.Get_by("name"))) {
			throw XomwMWException.New_by_method(XomwPPNode_Hash_Tree.class, "splitRawArg", "Invalid brace node passed to " + "splitRawArg");
		}
		if (!XophpObject_.isset_obj(bits.Get_by("index"))) {
			bits.Set("index", "");
		}
		return bits;
	}

	/**
	* Split an "<ext>" node into an associative array containing name, attr, inner and close
	* All values in the resulting array are PPNodes. Inner and close are optional.
	*
	* @throws MWException
	* @return array
	*/
	@Override public XophpArray splitExt() {
		return XomwPPNode_Hash_Tree.splitRawExt(this.rawChildren);
	}

	/**
	* Like splitExt() but for a raw child array. For @gplx.Internal protected use only.
	*/
	public static XophpArray splitRawExt(XophpArray children) {
		XophpArray bits = XophpArray.New();
		int len = children.Len();
		for (int i = 0; i < len; i++) {
			Object childObj = children.Get_at(i);
			if (!XophpArray.is_array(childObj)) {
				continue;
			}
			XophpArray child = (XophpArray)childObj;
			String childName = child.Get_at_str(XomwPPNode_Hash_Tree.NAME);
			if (String_.Eq(childName, "name")) {
				bits.Add("name", new XomwPPNode_Hash_Tree(children, i));
			}
			else if (String_.Eq(childName, "attr")) {
				bits.Add("attr", new XomwPPNode_Hash_Tree(children, i));
			}
			else if (String_.Eq(childName, "inner")) {
				bits.Add("inner", new XomwPPNode_Hash_Tree(children, i));
			}
			else if (String_.Eq(childName, "close")) {
				bits.Add("close", new XomwPPNode_Hash_Tree(children, i));
			}
		}
		if (!XophpArray.isset(bits, "name")) {
			throw new XomwMWException("Invalid ext node passed to " + "splitRawExt");
		}
		return bits;
	}

	/**
	* Split an "<h>" node
	*
	* @throws MWException
	* @return array
	*/
	@Override public XophpArray splitHeading() {
		if (!String_.Eq(this.name, "h")) {
			throw new XomwMWException("Invalid h node passed to " + "splitHeading");
		}
		return XomwPPNode_Hash_Tree.splitRawHeading(this.rawChildren);
	}

	/**
	* Like splitHeading() but for a raw child array. For @gplx.Internal protected use only.
	*/
	public static XophpArray splitRawHeading(XophpArray children) {
		XophpArray bits = XophpArray.New();
		int len = children.Len();
		for (int i = 0; i < len; i++) {
			Object childObj = children.Get_at(i);
			if (!XophpArray.is_array(childObj)) {
				continue;
			}
			XophpArray child = (XophpArray)childObj;
			String childName = child.Get_at_str(XomwPPNode_Hash_Tree.NAME);
			XophpArray childChildren = child.Get_at_ary(XomwPPNode_Hash_Tree.CHILDREN);
			if (String_.Eq(childName, "@i")) {
				bits.Add("i", childChildren.Get_at(0));
			} else if (String_.Eq(childName, "@level")) {
				bits.Add("level", childChildren.Get_at(0));
			}
		}
		if (!XophpArray.isset(bits, "i")) {
			throw new XomwMWException("Invalid h node passed to " + "splitRawHeading");
		}
		return bits;
	}

	/**
	* Split a "<template>" or "<tplarg>" node
	*
	* @throws MWException
	* @return array
	*/
	public XophpArray splitTemplate() {
		return XomwPPNode_Hash_Tree.splitRawTemplate(this.rawChildren);
	}

	/**
	* Like splitTemplate() but for a raw child array. For @gplx.Internal protected use only.
	*/
	public static XophpArray splitRawTemplate(XophpArray children) {
		XophpArray parts = XophpArray.New();
		XophpArray bits = XophpArray.New("lineStart" , "");
		int len = children.Len();
		for (int i = 0; i < len; i++) {
			Object childObj = children.Get_at(i);
			if (!XophpArray.is_array(childObj)) {
				continue;
			}
			XophpArray child = (XophpArray)childObj;
			String childName = child.Get_at_str(XomwPPNode_Hash_Tree.NAME);
			XophpArray childChildren = child.Get_at_ary(XomwPPNode_Hash_Tree.CHILDREN);
			if (String_.Eq(childName, "title")) {
				bits.Add("title", new XomwPPNode_Hash_Tree(childChildren, i));
			} else if (String_.Eq(childName, "part")) {
				parts.Add(new XomwPPNode_Hash_Tree(childChildren, i));
			} else if (String_.Eq(childName, "@lineStart")) {
				bits.Add("lineStart", "1");
			}
		}
		if (!XophpArray.isset(bits, "title")) {
			throw new XomwMWException("Invalid node passed to " + "splitRawTemplate");
		}
		bits.Add("parts", new XomwPPNode_Hash_Array(parts));
		return bits;
	}
}
