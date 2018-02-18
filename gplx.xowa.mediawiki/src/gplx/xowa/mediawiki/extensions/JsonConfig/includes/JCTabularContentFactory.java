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
package gplx.xowa.mediawiki.extensions.JsonConfig.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.extensions.*; import gplx.xowa.mediawiki.extensions.JsonConfig.*;
import gplx.xowa.mediawiki.*;
public class JCTabularContentFactory implements XophpClassBldr {
	public String Id() {return JCTabularContent.Model_id;}
	public Object Make(Object... args) {
		JCTabularContent rv = new JCTabularContent();
		byte[] text = (byte[])args[0];
		String modelId = (String)args[1];
		boolean thorough = Bool_.Cast(args[2]);
		rv.__construct(text, modelId, thorough);
		return rv;
	}
}
