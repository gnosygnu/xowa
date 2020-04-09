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
package gplx.xowa.mediawiki.includes.parsers.preprocessors_new; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
// MW.SRC:1.33
import gplx.xowa.mediawiki.includes.exception.*;
/**
* Stack class to help Preprocessor::preprocessToObj()
* @ingroup Parser
*/
public class XomwPPDStack {
	public XophpArray stack;
	public XophpArray rootAccum;
	public XophpArray accum;

	/**
	* @var PPDStack
	*/
	public XomwPPDStackElement top;
//		public out;
	public XomwPPDStackElement elementClass;

	// public static false = false;
	private final    XomwPPDPart partClass;

	public XomwPPDStack(XomwPPDPart partClass) {
		this.stack = XophpArray.New();
		this.top = null;
		this.rootAccum = XophpArray.New("");
		this.accum = this.rootAccum; // =&

		this.partClass = partClass;
	}

	/**
	* @return int
	*/
	public int count() {
		return XophpArray_.count(this.stack);
	}

	public XophpArray getAccum() { // &getAccum
		return this.accum;
	}

	/**
	* @return boolean|PPDPart
	*/
	public XomwPPDPart getCurrentPart() {
		if (XophpObject_.is_false(this.top)) {
			return null;
		} else {
			return this.top.getCurrentPart();
		}
	}

	public void push(Object data) {
		if (XophpType_.instance_of(data, XomwPPDStackElement.class)) {
			this.stack.Add(data);
		} else {
			XophpArray array = (XophpArray)data;
			this.stack.Add(elementClass.New(partClass, array.Get_by_str("open"), array.Get_by_str("close"), array.Get_by_ary_or("parts", null), array.Get_by_int_or("count", 0), array.Get_by_bool_or("lineStart", false), array.Get_by_int_or("startPos", 0)));
		}
		this.top = (XomwPPDStackElement)this.stack.Get_at(XophpArray_.count(this.stack) - 1);
		this.accum = this.top.getAccum(); //=&
	}

	public XomwPPDStackElement pop() {
		if (this.stack.Eq_to_new()) {
			throw XomwMWException.New_by_method_obj(this, "pop", ": no elements remaining");
		}
		XomwPPDStackElement temp = (XomwPPDStackElement)XophpArray_.array_pop(this.stack);

		if (XophpArray_.count_bool(this.stack)) {
			this.top = (XomwPPDStackElement)this.stack.Get_at(XophpArray_.count(this.stack) - 1);
			this.accum = this.top.getAccum(); // =&
		} else {
			this.top = null;
			this.accum = this.rootAccum; // =&
		}
		return temp;
	}

	public void addPart() {addPart("");}
	public void addPart(String s) {
		this.top.addPart(s);
		this.accum = this.top.getAccum(); // =&
	}

	/**
	* @return array
	*/
	public XophpArray getFlags() {
		if (this.stack.Eq_to_new()) {
			return XophpArray.New()
				.Add("findEquals", false)
				.Add("findPipe", false)
				.Add("inHeading", false)
			;
		} else {
			return this.top.getFlags();
		}
	}
}
