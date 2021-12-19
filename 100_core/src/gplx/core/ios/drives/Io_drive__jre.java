/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.ios.drives;
import gplx.libs.files.Io_url;
public class Io_drive__jre implements Io_drive {
	public long Get_space_total(Io_url drive) {return new java.io.File(drive.Xto_api()).getTotalSpace();}
	public long Get_space_free (Io_url drive) {return new java.io.File(drive.Xto_api()).getFreeSpace();}    
}
