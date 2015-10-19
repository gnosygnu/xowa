/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
