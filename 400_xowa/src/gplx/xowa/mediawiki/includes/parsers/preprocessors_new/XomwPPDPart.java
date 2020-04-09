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
public abstract class XomwPPDPart {
	/**
	* @var String Output accumulator String
	*/
	public XophpArray output;

	// REF.MW:https://manual.phpdoc.org/HTMLSmartyConverter/PHP/phpDocumentor/tutorial_tags.property.pkg.html
	// Optional member variables:
	//   eqpos        Position of equals sign in output accumulator
	//   commentEnd   Past-the-end input pointer for the last comment encountered
	//   visualEnd    Past-the-end input pointer for the end of the accumulator minus comments
	public int eqpos;
	public int commentEnd;
	public int visualEnd;

	public void ctor_XomwPPDPart(XophpArray output) {
		this.output = output;
	}

	public XomwPPDPart New() {return New("");}
	public abstract XomwPPDPart New(String val);
}
