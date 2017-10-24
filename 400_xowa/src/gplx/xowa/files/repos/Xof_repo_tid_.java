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
package gplx.xowa.files.repos; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
public class Xof_repo_tid_ {
	public static final byte
	  Tid__remote	= 0		// EX: "https://commons.wikimedia.org/wiki/File:A.png"
	, Tid__local	= 1		// EX: "https://en.wikipedia.org/wiki/File:A.png"
	, Tid__xtns		= 2		// EX: "https://en.wikipedia.org/w/extensions/ImageMap/desc-20.png?15600"
	, Tid__math		= 3		// EX: "https://wikimedia.org/api/rest_v1/media/math/render/svg/596f8baf206a81478afd4194b44138715dc1a05c"
	, Tid__null		= Byte_.Max_value_127
	;
	public static byte By_str(String v) {
		if		(String_.Eq(v, "self")) return Tid__local;
		else if	(String_.Eq(v, "comm")) return Tid__remote;
		else if	(String_.Eq(v, "math")) return Tid__math;
		else							throw Err_.new_unhandled_default(v);
	}
	public static final    byte[] 
	  Bry__math		= Bry_.new_a7("wikimedia.org/math")	// EX: https://wikimedia.org/api/rest_v1/media/math/render/svg/596f8baf206a81478afd4194b44138715dc1a05c
	;
}
