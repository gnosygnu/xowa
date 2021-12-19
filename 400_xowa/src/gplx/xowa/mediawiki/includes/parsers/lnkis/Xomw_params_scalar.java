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
package gplx.xowa.mediawiki.includes.parsers.lnkis; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
public class Xomw_params_scalar {
	public int physicalWidth;
	public int physicalHeight;
	public byte[] physicalDimensions;
	public int clientWidth;
	public int clientHeight;
	public byte[] comment;
	public int srcWidth;
	public int srcHeight;
	public byte[] mimeType;
	public byte[] dstPath;
	public byte[] dstUrl;
	public byte[] interlace;
	public Xomw_params_scalar() {
		physicalWidth = physicalHeight = clientWidth = clientHeight = srcWidth = srcHeight = XophpObject_.NULL_INT;
	}
}
