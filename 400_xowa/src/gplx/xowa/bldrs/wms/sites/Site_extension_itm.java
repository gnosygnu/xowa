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
package gplx.xowa.bldrs.wms.sites; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.wms.*;
class Site_extension_itm implements To_str_able {
	public Site_extension_itm(byte[] type, byte[] name, byte[] namemsg, byte[] description, byte[] descriptionmsg, byte[] author, byte[] url, byte[] version
		, byte[] vcs_system, byte[] vcs_version, byte[] vcs_url, byte[] vcs_date, byte[] license_name, byte[] license, byte[] credits) {
		this.type = type; this.name = name; this.namemsg = namemsg; this.description = description; this.descriptionmsg = descriptionmsg; this.author = author; this.url = url; this.version = version;
		this.vcs_system = vcs_system; this.vcs_version = vcs_version; this.vcs_url = vcs_url; this.vcs_date = vcs_date; this.license_name = license_name; this.license = license; this.credits = credits;
		this.key = Bry_.Add_w_dlm(Byte_ascii.Pipe, type, name);
	}
	public byte[] Key() {return key;} private final byte[] key;
	public byte[] Type() {return type;} private final byte[] type;
	public byte[] Name() {return name;} private final byte[] name;
	public byte[] Namemsg() {return namemsg;} private final byte[] namemsg;
	public byte[] Description() {return description;} private final byte[] description;
	public byte[] Descriptionmsg() {return descriptionmsg;} private final byte[] descriptionmsg;
	public byte[] Author() {return author;} private final byte[] author;
	public byte[] Url() {return url;} private final byte[] url;
	public byte[] Version() {return version;} private final byte[] version;
	public byte[] Vcs_system() {return vcs_system;} private final byte[] vcs_system;
	public byte[] Vcs_version() {return vcs_version;} private final byte[] vcs_version;
	public byte[] Vcs_url() {return vcs_url;} private final byte[] vcs_url;
	public byte[] Vcs_date() {return vcs_date;} private final byte[] vcs_date;
	public byte[] License_name() {return license_name;} private final byte[] license_name;
	public byte[] License() {return license;} private final byte[] license;
	public byte[] Credits() {return credits;} private final byte[] credits;
	public String To_str() {return String_.Concat_with_obj("|", type, name, namemsg, description, descriptionmsg, author, url, version, vcs_system, vcs_version, vcs_url, vcs_date, license_name, license, credits);}
}
