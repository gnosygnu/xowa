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
// MW.FILE:Preprocessor
/**
* Stack clazz to help Preprocessor::preprocessToObj()
* @ingroup Parser
*/
public class XomwPPDStack {
	public XophpArray stack;
	public Xomw_prepro_accum rootAccum;
	protected Xomw_prepro_accum accum;

	/**
	* @var PPDStack
	*/
	public XomwPPDStackElement top;
//		public $out;
//		public $elementClass = 'PPDStackElement';

	public XomwPPDStack (Xomw_prepro_accum prototype) {
		this.stack = XophpArray.New();
		this.top = null;
		this.rootAccum = prototype.Make_new();
		this.accum = rootAccum;
	}

	/**
	* @return int
	*/
	public int count() {
		return this.stack.Count();
	}

	public Xomw_prepro_accum getAccum() {
		return this.accum;
	}

	public XomwPPDPart getCurrentPart() {
		if (this.top == null) {
			return null;
		} else {
			return this.top.getCurrentPart();
		}
	}

	public void push(XomwPPDStackElement data) {
		this.stack.Add(data);
		// PHP.IGNORE:strong-typing
//			else {
//				$class = this.elementClass;
//				this.stack[] = new $class($data);
//			}
		this.top = (XomwPPDStackElement)this.stack.Get_at(this.stack.Count() - 1);
		this.accum = this.top.getAccum();
	}

	public XomwPPDStackElement pop() {
		if (this.stack.Count() == 0) {
			throw XomwMWException.New_by_method(XomwPPDStack.class, "pop", "no elements remaining");
		}
		XomwPPDStackElement temp = (XomwPPDStackElement)XophpArrayUtl.pop_obj(this.stack);
		if (this.stack.Count()> 0) {
			this.top = (XomwPPDStackElement)this.stack.Get_at(this.stack.Count() - 1);
			this.accum = this.top.getAccum();
		} else {
			this.top = null;
			this.accum = this.rootAccum;
		}
		return temp;
	}

	public void addPart(String s) {
		this.top.addPart(s);
		this.accum = this.top.getAccum();
	}

	/**
	* @return array
	*/
	public XomwPPDStackElementFlags getFlags() {
		if (this.stack.Count() == 0) {
			return XomwPPDStackElementFlags.Empty;
		}
		else {
			return this.top.getFlags();
		}
	}

	public void Clear() {
		stack.Clear();
		accum.Clear();
		top = null;
	}
	public Xomw_prepro_accum Get_root_accum() {return this.rootAccum;}
}
