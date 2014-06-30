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
package gplx;
import gplx.xowa.net.*;
public class Gfo_url {
	public byte[] Raw() {return raw;} private byte[] raw;
	public boolean Protocol_is_relative() {return protocol_is_relative;} public Gfo_url Protocol_is_relative_(boolean v) {protocol_is_relative = v; return this;} private boolean protocol_is_relative;
	public byte Protocol_tid() {return protocol_tid;} public Gfo_url Protocol_tid_(byte v) {protocol_tid = v; return this;} private byte protocol_tid;
	public byte[] Protocol_bry() {return protocol_bry;} public Gfo_url Protocol_bry_(byte[] v) {protocol_bry = v; return this;} private byte[] protocol_bry;
	public byte[] Site() {return site;} public Gfo_url Site_(byte[] v) {site = v; return this;} private byte[] site;
	public byte[] Site_sub() {return site_sub;} public Gfo_url Site_sub_(byte[] v) {site_sub = v; return this;} private byte[] site_sub;
	public byte[] Site_name() {return site_name;} public Gfo_url Site_name_(byte[] v) {site_name = v; return this;} private byte[] site_name;
	public byte[] Site_domain() {return site_domain;} public Gfo_url Site_domain_(byte[] v) {site_domain = v; return this;} private byte[] site_domain;
	public byte[] Page() {return page;} public Gfo_url Page_(byte[] v) {page = v; return this;} private byte[] page;
	public byte[] Anchor() {return anchor;} public Gfo_url Anchor_(byte[] v) {anchor = v; return this;} private byte[] anchor;
	public byte[][] Segs() {return segs;} public Gfo_url Segs_(byte[][] v) {segs = v; return this;} private byte[][] segs;
	public Gfo_url_arg[] Args() {return args;} public Gfo_url Args_(Gfo_url_arg[] v) {args = v; return this;} Gfo_url_arg[] args;
	public int Args_bgn() {return args_bgn;} public Gfo_url Args_bgn_(int v) {args_bgn = v; return this;} private int args_bgn = -1;
	public byte Err() {return err;} public Gfo_url Err_(byte v) {err = v; return this;} private byte err;
	public Gfo_url Ini_(byte[] v) {
		raw = v;
		protocol_tid = Xoo_protocol_itm.Tid_null; protocol_is_relative = false;
		protocol_bry = site = site_sub = site_name = site_domain = page = anchor = null;
		segs = Bry_.Ary_empty;
		args = Gfo_url_arg.Ary_empty;
		err = Err_none;
		args_bgn = -1;
		return this;
	}
	public static final byte Err_none = 0, Err_protocol_missing = 1, Err_site_missing = 2;
}
