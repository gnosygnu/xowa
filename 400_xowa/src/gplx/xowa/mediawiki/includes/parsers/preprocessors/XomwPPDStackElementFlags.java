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
public class XomwPPDStackElementFlags {
	public XomwPPDStackElementFlags(boolean findPipe, boolean findEquals, boolean inHeading) {
		this.findPipe = findPipe;
		this.findEquals = findEquals;
		this.inHeading = inHeading;
	}
	public boolean FindPipe()   {return findPipe;}   private final    boolean findPipe;
	public boolean FindEquals() {return findEquals;} private final    boolean findEquals;
	public boolean InHeading()  {return inHeading;}  private final    boolean inHeading;

	public static final    XomwPPDStackElementFlags Empty = new XomwPPDStackElementFlags(false, false, false);
}