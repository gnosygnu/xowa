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
public class XomwPPNode_Hash_Array extends XomwPPNode { 	public XophpArray value;

	public XomwPPNode_Hash_Array(XophpArray value) {
		this.value = value;
	}

	@Override public String toString() {
//			return var_export( $this, true );
		return null;
	}

	public int getLength() {
		return -1;
//			return count( this.value );
	}

	public Object item(int i) {
		return null;
//			return this.value[$i];
	}

	@Override public String getName() {
		return "#nodelist";
	}

	@Override public XomwPPNode getNextSibling() {
		return null;
	}

//		public function getChildren() {
//			return false;
//		}

	@Override public XomwPPNode getFirstChild() {
		return null;
	}

//		public function getChildrenOfType( $name ) {
//			return false;
//		}
//
//		public function splitArg() {
//			throw new MWException( __METHOD__ . ': not supported' );
//		}
//
//		public function splitExt() {
//			throw new MWException( __METHOD__ . ': not supported' );
//		}
//
//		public function splitHeading() {
//			throw new MWException( __METHOD__ . ': not supported' );
//		}
}
