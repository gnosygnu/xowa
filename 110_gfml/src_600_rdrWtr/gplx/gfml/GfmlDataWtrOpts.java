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
package gplx.gfml; import gplx.*;
public class GfmlDataWtrOpts {
	public static final String Key_const = "GfmlDataWtrOpts";
	public String KeyedSpr() {return keyedSeparator;} public GfmlDataWtrOpts KeyedSeparator_(String val) {keyedSeparator = val; return this;} private String keyedSeparator = " ";
	public boolean IndentNodes() {return indentNodes;} public GfmlDataWtrOpts IndentNodesOn_() {indentNodes = true; return this;} private boolean indentNodes;
	public boolean IgnoreNullNames() {return ignoreNullNames;} public GfmlDataWtrOpts IgnoreNullNamesOn_() {ignoreNullNames = true; return this;} private boolean ignoreNullNames;
        public static final GfmlDataWtrOpts Instance = new GfmlDataWtrOpts();
	public static GfmlDataWtrOpts new_() {return new GfmlDataWtrOpts();} GfmlDataWtrOpts() {}
	public static GfmlDataWtrOpts cast(Object obj) {try {return (GfmlDataWtrOpts)obj;} catch(Exception exc) {throw Err_.new_type_mismatch_w_exc(exc, GfmlDataWtrOpts.class, obj);}}
}
