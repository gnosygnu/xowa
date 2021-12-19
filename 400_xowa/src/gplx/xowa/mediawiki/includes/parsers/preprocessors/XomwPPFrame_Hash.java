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
package gplx.xowa.mediawiki.includes.parsers.preprocessors;
import gplx.frameworks.objects.New;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.utls.ClassUtl;
import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import gplx.xowa.mediawiki.includes.exception.*;
import gplx.core.bits.*;
/**
* An expansion frame, used as a context to expand the result of preprocessToObj()
* @ingroup Parser
*/
public class XomwPPFrame_Hash extends XomwPPFrame { 	/**
	* @var Parser
	*/
	public XomwParser parser;

	/**
	* @var Preprocessor
	*/
	public XomwPreprocessor preprocessor;

	/**
	* @var Title
	*/
	public XomwTitleOld title;
	public XophpArray titleCache;

	/**
	* Hashtable listing templates which are disallowed for expansion in this frame,
	* having been encountered previously in parent frames.
	*/
	public XophpArray loopCheckHash;

	/**
	* Recursion depth of this frame, top = 0
	* Note that this is NOT the same as expansion depth in expand()
	*/
	@New public int depth;

	private boolean volatile_bool;
	private int ttl;

	/**
	* @var array
	*/
	public XophpArray childExpansionCache;

	/**
	* Construct a new preprocessor frame.
	* @param Preprocessor preprocessor The parent preprocessor
	*/
	public XomwPPFrame_Hash(XomwPreprocessor preprocessor) {
		this.preprocessor = preprocessor;
		this.parser = preprocessor.Parser();
		this.title = this.parser.mTitle;
		this.titleCache = XophpArray.New(XophpObject_.is_true(this.title) ? this.title.getPrefixedDBkeyStr() : XophpString_.False);
		this.loopCheckHash = XophpArray.New();
		this.depth = 0;
		this.childExpansionCache = XophpArray.New();
	}

	/**
	* Create a new child frame
	* $args is optionally a multi-root PPNode or array containing the template arguments
	*
	* @param array|boolean|PPNode_Hash_Array $args
	* @param Title|boolean $title
	* @param int $indexOffset
	* @throws MWException
	* @return PPTemplateFrame_Hash
	*/
	@Override public XomwPPFrame newChild(Object argsObj, XomwTitleOld title, int indexOffset) {
		XophpArray namedArgs = XophpArray.New();
		XophpArray numberedArgs = XophpArray.New();
		if (!XophpObject_.is_true(title)) {
			title = this.title;
		}
		if (XophpObject_.is_true(argsObj)) {
			XophpArray args = null;
			if (ClassUtl.EqByObj(XomwPPNode_Hash_Array.class, argsObj)) {
				args = ((XomwPPNode_Hash_Array)argsObj).value;
			} else if (!XophpArray.is_array(argsObj)) {
				throw XomwMWException.New_by_method(XomwPPFrame_Hash.class, "newChild", ": args must be array or PPNode_Hash_Array");
			}
			else {
				args = (XophpArray)argsObj;
			}

			int argsLen = args.Len();
			for (int i = 0; i < argsLen; i++) {
				XomwPPNode arg = (XomwPPNode)args.Get_at(i);
				XophpArray bits = arg.splitArg();
				if (bits.Has("index")) {
					// Numbered parameter
					int index = bits.Get_by_int("index") - indexOffset;
					if (XophpArray.isset(namedArgs, index) || XophpArray.isset(numberedArgs, index)) {
					//	this.parser.getOutput().addWarning(wfMessage('duplicate-args-warning',
					//		wfEscapeWikiText(this.title),
					//		wfEscapeWikiText(title),
					//		wfEscapeWikiText(index)).text());
					//	this.parser.addTrackingCategory('duplicate-args-category');
					}
					numberedArgs.Set(index, bits.Get_by("value"));
					XophpArray.unset(namedArgs, index);
				} else {
					// Named parameter
					String name = StringUtl.Trim(this.expand(bits.Get_by("name"), XomwPPFrame.STRIP_COMMENTS));
					if (XophpArray.isset(namedArgs, name) || XophpArray.isset(numberedArgs, name)) {
					//	this.parser.getOutput().addWarning(wfMessage('duplicate-args-warning',
					//		wfEscapeWikiText(this.title),
					//		wfEscapeWikiText(title),
					//		wfEscapeWikiText(name)).text());
					//	this.parser.addTrackingCategory('duplicate-args-category');
					}
					namedArgs.Set(name, bits.Get_by("value"));
					XophpArray.unset(numberedArgs, name);
				}
			}
		}
		return new XomwPPTemplateFrame_Hash(this.preprocessor, this, numberedArgs, namedArgs, title);
	}

	/**
	* @throws MWException
	* @param String|int $key
	* @param String|PPNode $root
	* @param int $flags
	* @return String
	*/
	public String cachedExpand(String key, Object root, int flags) { // DEFAULT:flags=0
		// we don't have a parent, so we don't have a cache
		return this.expand(root, flags);
	}

	private static int expansionDepth = 0; // MW.GLOBAL:expand
	private static int expand_flags_default = 0;
	/**
	* @throws MWException
	* @param String|PPNode $root
	* @param int $flags
	* @return String
	*/
	@Override public String expand(Object root, int flags) {
		if (XophpString_.is_string(root)) {
			return (String)root;
		}

		if (++this.parser.mPPNodeCount > this.parser.mOptions.getMaxPPNodeCount()) {
			//	this.parser.limitationWarn('node-count-exceeded',
			//			this.parser.mPPNodeCount,
			//			this.parser.mOptions.getMaxPPNodeCount()
			//	);
			return "<span class=\"error\">Node-count limit exceeded</span>";
		}
		if (expansionDepth > this.parser.mOptions.getMaxPPExpandDepth()) {
			//	this.parser.limitationWarn('expansion-depth-exceeded',
			//			expansionDepth,
			//			this.parser.mOptions.getMaxPPExpandDepth()
			//	);
			return "<span class=\"error\">Expansion depth limit exceeded</span>";
		}
		++expansionDepth;
		if (expansionDepth > this.parser.mHighestExpansionDepth) {
			this.parser.mHighestExpansionDepth = expansionDepth;
		}

		XophpArray outStack = XophpArray.New("", "");
		XophpArray iteratorStack = XophpArray.New(XophpObject_.False, root);
		XophpArray indexStack = XophpArray.New(0, 0);

		while (XophpArray.count(iteratorStack) > 1) {
			int level = XophpArray.count(outStack) - 1;
			Object iteratorNode = iteratorStack.Get_at(level);
			String outItm = outStack.Get_at_str(level);
			int index = indexStack.Get_at_int(level);
			Object contextNode;
			if (XophpArray.is_array(iteratorNode)) {
				XophpArray iteratorNodeArray = (XophpArray)iteratorNode;
				if (index >= XophpArray.count(iteratorNodeArray)) {
					// All done with this iterator
					iteratorStack.Set(level, XophpObject_.False);
					contextNode = XophpObject_.False;
				} else {
					contextNode = iteratorNodeArray.Get_at(index);
					index++;
				}
			} else if (ClassUtl.EqByObj(XomwPPNode_Hash_Array.class, iteratorNode)) {
				XomwPPNode_Hash_Array iteratorNodeHashArray = (XomwPPNode_Hash_Array)iteratorNode;
				if (index >= iteratorNodeHashArray.getLength()) {
					// All done with this iterator
					iteratorStack.Set(level, XophpObject_.False);
					contextNode = XophpObject_.False;
				} else {
					contextNode = iteratorNodeHashArray.item(index);
					index++;
				}
			} else {
				// Copy to contextNode and then delete from iterator stack,
				// because this is not an iterator but we do have to execute it once
				contextNode = iteratorStack.Get_at(level);
				iteratorStack.Set(level, XophpObject_.False);
			}

			Object newIterator = XophpObject_.False;
			String contextName = XophpString_.False;
			XophpArray contextChildren = XophpArray.False;

			if (!XophpObject_.is_true(contextNode)) {
				// nothing to do
			} else if (XophpString_.is_string(contextNode)) {
				outItm += (String)contextNode;
			} else if (ClassUtl.EqByObj(XomwPPNode_Hash_Array.class, contextNode)) {
				newIterator = contextNode;
			} else if (ClassUtl.EqByObj(XomwPPNode_Hash_Attr.class, contextNode)) {
				// No output
			} else if (ClassUtl.EqByObj(XomwPPNode_Hash_Text.class, contextNode)) {
				outItm += ((XomwPPNode_Hash_Text)contextNode).value;
			} else if (ClassUtl.EqByObj(XomwPPNode_Hash_Tree.class, contextNode)) {
				XomwPPNode_Hash_Tree contextNodeHashTree = (XomwPPNode_Hash_Tree)contextNode;
				contextName = contextNodeHashTree.name;
				contextChildren = contextNodeHashTree.getRawChildren();
			} else if (XophpArray.is_array(contextNode)) {
				XophpArray contextNodeArray = (XophpArray)contextNode;
				// Node descriptor array
				if (XophpArray.count(contextNodeArray) != 2) {
					throw XomwMWException.New_by_method(XomwPPFrame_Hash.class, "expand", 
						": found an array where a node descriptor should be");
				}
				contextName = (String)contextNodeArray.Get_at(0);
				contextChildren = contextNodeArray.Get_at_ary(1);
			} else {
				throw XomwMWException.New_by_method(XomwPPFrame_Hash.class, "expand", ": Invalid parameter type");
			}

			// Handle node descriptor array or tree Object
			if (!XophpString_.is_true(contextName)) {
				// Not a node, already handled above
			} else if (StringUtl.CharAt(contextName, 0) == '@') {
				// Attribute: no output
			} else if (StringUtl.Eq(contextName, "template")) {
				// Double-brace expansion
				XophpArray bits = XomwPPNode_Hash_Tree.splitRawTemplate(contextChildren);
				if (Bitmask_.Has_int(flags, XomwPPFrame.NO_TEMPLATES)) {
					newIterator = this.virtualBracketedImplode(
						"{{", "|", "}}",
						bits.Get_by("title"),
						bits.Get_by("parts")
					);
				} else {
					XophpArray ret = this.parser.braceSubstitution(bits, this);
					if (XophpArray.isset(ret, ObjectUtl.ClsValName)) {// NOTE: using Cls_val_name b/c of transpilation and Object . Object
						newIterator = ret.Get_by(ObjectUtl.ClsValName);
					} else {
						outItm += ret.Get_by_str("text");
					}
				}
			} else if (StringUtl.Eq(contextName, "tplarg")) {
				// Triple-brace expansion
				XophpArray bits = XomwPPNode_Hash_Tree.splitRawTemplate(contextChildren);
				if (Bitmask_.Has_int(flags, XomwPPFrame.NO_ARGS)) {
					newIterator = this.virtualBracketedImplode(
						"{{{", "|", "}}}",
						bits.Get_by("title"),
						bits.Get_by("parts")
					);
				} else {
					XophpArray ret = this.parser.argSubstitution(bits, this);
					if (XophpArray.isset(ret, ObjectUtl.ClsValName)) {// NOTE: using Cls_val_name b/c of transpilation and Object . Object
						newIterator = ret.Get_by("Object");
					} else {
						outItm += ret.Get_by_str("text");
					}
				}
			} else if (StringUtl.Eq(contextName, "comment")) {
				// HTML-style comment
				// Remove it in HTML, pre+remove and STRIP_COMMENTS modes
				// Not in RECOVER_COMMENTS mode (msgnw) though.
				if ((this.parser.ot.Has("html"))
					|| (this.parser.ot.Has("pre") && this.parser.mOptions.getRemoveComments())
					|| (Bitmask_.Has_int(flags, XomwPPFrame.STRIP_COMMENTS))
					 && !(Bitmask_.Has_int(flags, XomwPPFrame.RECOVER_COMMENTS))
				) {
					outItm += ""; // XOWA: no purpose?
				} else if (this.parser.ot.Has("wiki") && !(Bitmask_.Has_int(flags, XomwPPFrame.RECOVER_COMMENTS))) {
					// Add a strip marker in PST mode so that pstPass2() can
					// run some old-fashioned regexes on the result.
					// Not in RECOVER_COMMENTS mode (extractSections) though.
					outItm += this.parser.insertStripItem(contextChildren.Get_at_str(0));
				} else {
					// Recover the literal comment in RECOVER_COMMENTS and pre+no-remove
					outItm += contextChildren.Get_at_str(0);
				}
			} else if (StringUtl.Eq(contextName, "ignore")) {
				// Output suppression used by <includeonly> etc.
				// OT_WIKI will only respect <ignore> in substed templates.
				// The other output types respect it unless NO_IGNORE is set.
				// extractSections() sets NO_IGNORE and so never respects it.
//					if ((!XophpObject_.isset(this.parent) && this.parser.ot.Has("wiki")) // this.parent doesn't exist?
				if ((this.parser.ot.Has("wiki"))
					|| (Bitmask_.Has_int(flags, XomwPPFrame.NO_IGNORE))
				) {
					outItm += contextChildren.Get_at_str(0);
				} else {
					// outItm .= '';
				}
			} else if (StringUtl.Eq(contextName, "ext")) {
				// Extension tag
				XophpArray bits = XomwPPNode_Hash_Tree.splitRawExt(contextChildren)
					.Add("attr", null).Add("inner", null).Add("close", null);
				if (Bitmask_.Has_int(flags, XomwPPFrame.NO_TAGS)) {
					String s = '<' + ((XomwPPNode_Hash_Text)((XomwPPNode)bits.Get_by("name")).getFirstChild()).value;
					if (bits.Has("attr")) {
						s += ((XomwPPNode_Hash_Text)((XomwPPNode)bits.Get_by("attr")).getFirstChild()).value;
					}
					if (bits.Has("inner")) {
						s += '>' + ((XomwPPNode_Hash_Text)((XomwPPNode)bits.Get_by("inner")).getFirstChild()).value;
						if (bits.Has("close")) {
							s += ((XomwPPNode_Hash_Text)((XomwPPNode)bits.Get_by("close")).getFirstChild()).value;
						}
					} else {
						s += "/>";
					}
					outItm += s;
				} else {
					outItm += this.parser.extensionSubstitution(bits, this);
				}
			} else if (StringUtl.Eq(contextName, "h")) {
				// Heading
				if (this.parser.ot.Has("html")) {
					// Expand immediately and insert heading index marker
					String s = this.expand(contextChildren, flags);
					XophpArray bits = XomwPPNode_Hash_Tree.splitRawHeading(contextChildren);
					String titleText = this.title.getPrefixedDBkeyStr();
					this.parser.mHeadings.Add(titleText, bits.Get_by("i"));
					int serial = XophpArray.count(this.parser.mHeadings) - 1;
					String marker = XomwParser.MARKER_PREFIX + "-h-" + IntUtl.ToStr(serial) + "-" + XomwParser.MARKER_SUFFIX;
					s = XophpString_.substr(s, 0, bits.Get_by_int("level")) + marker + XophpString_.substr(s, bits.Get_by_int("level"));
					this.parser.mStripState.addGeneral(marker, "");
					outItm += s;
				} else {
					// Expand in virtual stack
					newIterator = contextChildren;
				}
			} else {
				// Generic recursive expansion
				newIterator = contextChildren;
			}

			if (XophpObject_.is_true(newIterator)) {
				outStack.Add("");
				iteratorStack.Add(newIterator);
				indexStack.Add(0);
			} else if (!XophpObject_.is_true(iteratorStack.Get_at(level))) {
				// Return accumulated value to parent
				// With tail recursion
				while (!XophpObject_.is_true(iteratorStack.Get_at(level)) && level > 0) {
					outStack.Itm_str_concat_end(level - 1, outItm);
					XophpArray.array_pop(outStack);
					XophpArray.array_pop(iteratorStack);
					XophpArray.array_pop(indexStack);
					level--;
				}
			}
		}
		--expansionDepth;
		return outStack.Get_at_str(0);
	}

	/**
	* @param String $sep
	* @param int $flags
	* @param String|PPNode $args,...
	* @return String
	*/
	public String implodeWithFlags(String sep, int flags, XophpArray args) {
		// args = XophpArray.array_slice(func_get_args(), 2);

		boolean first = true;
		String s = "";
		int len = args.Len();
		for (int i = 0; i < len; i++) {
			Object root_obj = args.Get_at(i);
			XophpArray root = null;
			if (XophpType_.instance_of(root_obj, XomwPPNode_Hash_Array.class)) {
				root = (XophpArray)((XomwPPNode_Hash_Array)root_obj).value;
			}
			if (!XophpType_.is_array(root_obj)) {
				root = XophpArray.New(root_obj);
			}
			int root_len = root.Len();
			for (int j = 0; j < root_len; j++) {
				XomwPPNode node = (XomwPPNode)root.Get_at(j);
				if (first) {
					first = false;
				} else {
					s += sep;
				}
				s += this.expand(node, flags);
			}
		}
		return s;
	}

	/**
	* Implode with no flags specified
	* This previously called implodeWithFlags but has now been inlined to reduce stack depth
	* @param String $sep
	* @param String|PPNode $args,...
	* @return String
	*/
	@Override public String implode(String sep, Object... args) {
		boolean first = true;
		String s = "";
		for (Object rootObj : args) {
			XophpArray root = null;
			if (ClassUtl.EqByObj(XomwPPNode_Hash_Array.class, root)) {
				root = ((XomwPPNode_Hash_Array)rootObj).value;
			}
			if (!XophpArray.is_array(rootObj)) {
				root = XophpArray.New(root);
			}
			int rootLen = root.Len();
			for (int i = 0; i < rootLen; i++) {
				Object node = root.Get_at(i);
				if (first) {
					first = false;
				} else {
					s += sep;
				}
				s += this.expand(node, expand_flags_default);
			}
		}
		return s;
	}

    /**
	* Makes an Object that, when expand()ed, will be the same as one obtained
	* with implode()
	*
	* @param String $sep
	* @param String|PPNode $args,...
	* @return PPNode_Hash_Array
	*/
	@Override public XomwPPNode virtualImplode(String sep, Object... args) {
		XophpArray outItm = XophpArray.New();
		boolean first = true;

		for (Object rootObj : args) {
			XophpArray root = null;
			if (ClassUtl.EqByObj(XomwPPNode_Hash_Array.class, root)) {
				root = ((XomwPPNode_Hash_Array)rootObj).value;
			}
			if (!XophpArray.is_array(rootObj)) {
				root = XophpArray.New(root);
			}
			int rootLen = root.Len();
			for (int i = 0; i < rootLen; i++) {
				Object node = root.Get_at(i);
				if (first) {
					first = false;
				} else {
					outItm.Add(sep);
				}
				outItm.Add(node);
			}
		}
		return new XomwPPNode_Hash_Array(outItm);
	}

	/**
	* Virtual implode with brackets
	*
	* @param String $start
	* @param String $sep
	* @param String $end
	* @param String|PPNode $args,...
	* @return PPNode_Hash_Array
	*/
	@Override public XomwPPNode virtualBracketedImplode(String start, String sep, String end, Object... args) {
		XophpArray outItm = XophpArray.New(start);
		boolean first = true;

		for (Object rootObj : args) {
			XophpArray root = null;
			if (ClassUtl.EqByObj(XomwPPNode_Hash_Array.class, rootObj)) {
				root = ((XomwPPNode_Hash_Array)rootObj).value;
			}
			if (!XophpArray.is_array(rootObj)) {
				root = XophpArray.New((String)rootObj);
			}
			int root_len = root.Len();
			for (int i = 0; i < root_len; i++) {
				String node = root.Get_at_str(i);
				if (first) {
					first = false;
				} else {
					outItm.Add(sep);
				}
				outItm.Add(node);
			}
		}
		outItm.Add(end);
		return new XomwPPNode_Hash_Array(outItm);
	}

	@Override public String toString() {
		return "frame{}";
	}

	/**
	* @param boolean $level
	* @return array|boolean|String
	*/
	public String getPDBK(boolean level) { // DEFAULT:false
		if (level == false) {
			return this.title.getPrefixedDBkeyStr();
		} else {
			// return isset( $this->titleCache[$level] ) ? $this->titleCache[$level] : false;
			return XophpArray.count(this.titleCache) > 0 ? ((String)this.titleCache.Get_at(0)) : XophpString_.False;
		}
	}

	/**
	* @return array
	*/
	@Override public XophpArray getArguments() {
		return XophpArray.False;
	}

	/**
	* @return array
	*/
	@Override public XophpArray getNumberedArguments() {
		return XophpArray.False;
	}

	/**
	* @return array
	*/
	@Override public XophpArray getNamedArguments() {
		return XophpArray.False;
	}

	/**
	* Returns true if there are no arguments in this frame
	*
	* @return boolean
	*/
	@Override public boolean isEmpty() {
		return true;
	}

	/**
	* @param int|String $name
	* @return boolean Always false in this implementation.
	*/
	@Override public String getArgument(String name) {
		return XophpString_.False;
	}

	/**
	* Returns true if the infinite loop check is OK, false if a loop is detected
	*
	* @param Title $title
	*
	* @return boolean
	*/
	@Override public boolean loopCheck(XomwTitleOld title) {
		return !XophpArray.isset(this.loopCheckHash, title.getPrefixedDBkeyStr());
	}

	/**
	* Return true if the frame is a template frame
	*
	* @return boolean
	*/
	@Override public boolean isTemplate() {
		return false;
	}

	/**
	* Get a title of frame
	*
	* @return Title
	*/
	@Override public XomwTitleOld getTitle() {
		return this.title;
	}

	/**
	* Set the volatile_bool flag
	*
	* @param boolean $flag
	*/
	@Override public void setVolatile(boolean flag) { // DEFAULT: flag = true
		this.volatile_bool = flag;
	}

	/**
	* Get the volatile_bool flag
	*
	* @return boolean
	*/
	@Override public boolean isVolatile() {
		return this.volatile_bool;
	}

	/**
	* Set the TTL
	*
	* @param int ttl
	*/
	@Override public void setTTL(int val) {
		if (this.ttl == IntUtl.Null || val < this.ttl) {
			this.ttl = val;
		}
	}

	/**
	* Get the TTL
	*
	* @return int|null
	*/
	@Override public int getTTL() {
		return this.ttl;
	}
}
